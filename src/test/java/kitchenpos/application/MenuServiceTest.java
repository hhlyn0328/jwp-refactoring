package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchenpos.dao.MenuDao;
import kitchenpos.dao.MenuGroupDao;
import kitchenpos.dao.MenuProductDao;
import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import kitchenpos.domain.TestDomainConstructor;
import kitchenpos.domain.TestFixture;
import kitchenpos.dto.MenuProductResponse;
import kitchenpos.dto.MenuRequest;
import kitchenpos.dto.MenuResponse;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {
	@Mock
	private ProductDao productDao;
	@Mock
	private MenuProductDao menuProductDao;
	@Mock
	private MenuGroupDao menuGroupDao;
	@Mock
	private MenuDao menuDao;
	@InjectMocks
	private MenuService menuService;

	private static final Long NEW_MENU_ID = 1L;
	private static final Long SAVED_MENU_GROUP_ID = 1L;
	private static final Long SAVED_PRODUCT_ID = 1L;
	private static final Long SAVED_PRODUCT2_ID = 2L;
	private Product savedProduct;
	private Product savedProduct2;
	private MenuProduct menuProduct;
	private MenuProduct menuProduct2;
	private List<MenuProduct> menuProducts;

	@BeforeEach
	void setUp() {
		savedProduct = new Product(SAVED_PRODUCT_ID,"후라이드치킨", BigDecimal.valueOf(16000));
		savedProduct2 = new Product(SAVED_PRODUCT2_ID,"양념치킨", BigDecimal.valueOf(16000));
		menuProduct = TestDomainConstructor.menuProduct(null, SAVED_PRODUCT_ID, 1);
		menuProduct2 = TestDomainConstructor.menuProduct(null, SAVED_PRODUCT2_ID, 1);
		menuProducts = Arrays.asList(menuProduct, menuProduct2);
	}

	@Test
	@DisplayName("메뉴를 등록할 수 있다.")
	void create() {
		//given
		// String name = "후라이드-양념 콤보";
		// int price = 10000;
		// Menu menu = TestDomainConstructor.menu(name, price, SAVED_MENU_GROUP_ID, menuProducts);
		// Menu savedMenu = TestDomainConstructor.menuWithId(name, price, SAVED_MENU_GROUP_ID, menuProducts, NEW_MENU_ID);
		// MenuProduct savedMenuProduct = TestDomainConstructor.menuProductWithSeq(NEW_MENU_ID, SAVED_PRODUCT_ID, 1, 1L);
		// MenuProduct savedMenuProduct2 = TestDomainConstructor.menuProductWithSeq(NEW_MENU_ID, SAVED_PRODUCT2_ID, 1, 2L);

		MenuRequest menuRequest = TestFixture.메뉴_등록_REQUEST;

		when(menuGroupDao.existsById(menuRequest.getMenuGroupId())).thenReturn(true);
		when(productDao.findById(anyLong())).thenReturn(Optional.of(TestFixture.상품_등록되어있음_후라이드_1L)
			, Optional.of(TestFixture.상품_등록되어있음_양념_2L));
		when(menuDao.save(any(Menu.class))).thenReturn(TestFixture.메뉴_등록됨);
		when(menuProductDao.save(any())).thenReturn(TestFixture.메뉴상품_저장됨_1, TestFixture.메뉴상품_저장됨_2);

		//when
		MenuResponse result = menuService.create(menuRequest);

		//then
		assertThat(result.getId()).isEqualTo(TestFixture.메뉴_신규_ID);
		assertThat(result.getName()).isEqualTo(TestFixture.메뉴_신규_NAME);
		assertThat(result.getPrice().intValue()).isEqualTo(TestFixture.메뉴_신규_PRICE);
		assertThat(result.getMenuGroupId()).isEqualTo(TestFixture.메뉴_신규_메뉴그룹_ID);
		assertThat(result.getMenuProducts()).containsExactlyInAnyOrder(MenuProductResponse.of(TestFixture.메뉴상품_저장됨_1)
			, MenuProductResponse.of(TestFixture.메뉴상품_저장됨_2));
	}

	@Test
	@DisplayName("메뉴 등록 시, 메뉴의 가격이 없으면 IllegalArgumentException을 throw 해야한다.")
	void createPriceNull() {
		//when-then
		assertThatThrownBy(() -> menuService.create(TestFixture.메뉴_등록_가격없음_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴 등록 시, 메뉴 그룹이 등록되어있지 않으면 IllegalArgumentException을 throw 해야한다.")
	void createNotExistMenuGroup() {
		//given
		when(menuGroupDao.existsById(any())).thenReturn(false);

		//when-then
		assertThatThrownBy(() -> menuService.create(TestFixture.메뉴_등록_존재하지않는_메뉴그룹_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴 등록 시, 상품이 등록되어있지 않으면 IllegalArgumentException을 throw 해야한다.")
	void createNotExistProduct() {
		//given
		Menu notExistProductMenu = TestDomainConstructor.menu("메뉴1", 10000, SAVED_MENU_GROUP_ID, Arrays.asList(mock(MenuProduct.class)));
		when(menuGroupDao.existsById(any())).thenReturn(true);
		when(productDao.findById(any())).thenReturn(Optional.empty());

		//when-then
		assertThatThrownBy(() -> menuService.create(notExistProductMenu))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴 등록 시, 메뉴의 가격이 상품 가격의 합보다 크면 IllegalArgumentException을 throw 해야한다.")
	void createPriceLessThanZero() {
		//given
		Menu greaterThanSumOfProductPriceMenu = TestDomainConstructor.menu("메뉴1", 1000000, SAVED_MENU_GROUP_ID, menuProducts);
		when(menuGroupDao.existsById(SAVED_MENU_GROUP_ID)).thenReturn(true);
		when(productDao.findById(anyLong())).thenReturn(Optional.of(savedProduct), Optional.of(savedProduct2));

		//when-then
		assertThatThrownBy(() -> menuService.create(greaterThanSumOfProductPriceMenu))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴의 목록을 메뉴의 상품목록과 함께 조회할 수 있다.")
	void list() {
		//given
		MenuProduct mockMenuProduct = mock(MenuProduct.class);
		Menu menuWithTwoProducts = TestDomainConstructor.menuWithId("메뉴1", 1000, SAVED_MENU_GROUP_ID
			, Arrays.asList(mockMenuProduct, mockMenuProduct), 1L);
		Menu menuWithThreeProducts = TestDomainConstructor.menuWithId("메뉴2", 3000, SAVED_MENU_GROUP_ID
			, Arrays.asList(mockMenuProduct, mockMenuProduct, mockMenuProduct), 2L);

		when(menuDao.findAll()).thenReturn(Arrays.asList(menuWithTwoProducts, menuWithThreeProducts));
		when(menuProductDao.findAllByMenuId(anyLong())).thenReturn(menuWithTwoProducts.getMenuProducts(), menuWithThreeProducts.getMenuProducts());

		//when
		List<Menu> results = menuService.list();

		//then
		assertThat(results.size()).isEqualTo(2);
		assertThat(results.get(0).getMenuProducts().size()).isEqualTo(2);
		assertThat(results.get(1).getMenuProducts().size()).isEqualTo(3);
	}
}
