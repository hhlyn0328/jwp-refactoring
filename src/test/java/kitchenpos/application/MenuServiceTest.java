package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	private final Long savedMenuGroupId = 1L;
	private final Long savedProductId = 1L;
	private final Long savedProduct2Id = 2L;
	private final Product savedProduct = TestDomainConstructor.productWithId("후라이드치킨", 16000, savedProductId);
	private final Product savedProduct2 = TestDomainConstructor.productWithId("양념치킨", 16000, savedProduct2Id);
	private final MenuProduct menuProduct = TestDomainConstructor.menuProduct(null, savedProductId, 1);
	private final MenuProduct menuProduct2 = TestDomainConstructor.menuProduct(null, savedProduct2Id, 1);
	private final List<MenuProduct> menuProducts = Arrays.asList(menuProduct, menuProduct2);

	@Test
	@DisplayName("메뉴를 등록할 수 있다.")
	void create() {
		//given
		Long newMenuId = 1L;
		String name = "후라이드-양념 콤보";
		int price = 10000;
		Menu menu = TestDomainConstructor.menu(name, price, savedMenuGroupId, menuProducts);
		Menu savedMenu = TestDomainConstructor.menuWithId(name, price, savedMenuGroupId, menuProducts, newMenuId);
		MenuProduct savedMenuProduct = TestDomainConstructor.menuProductWithSeq(newMenuId, savedProductId, 1, 1L);
		MenuProduct savedMenuProduct2 = TestDomainConstructor.menuProductWithSeq(newMenuId, savedProduct2Id, 1, 2L);

		when(menuGroupDao.existsById(savedMenuGroupId)).thenReturn(true);
		when(productDao.findById(anyLong())).thenReturn(Optional.of(savedProduct), Optional.of(savedProduct2));
		when(menuDao.save(menu)).thenReturn(savedMenu);
		when(menuProductDao.save(any())).thenReturn(savedMenuProduct, savedMenuProduct2);

		//when
		Menu result = menuService.create(menu);

		//then
		assertThat(result.getId()).isEqualTo(newMenuId);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPrice().intValue()).isEqualTo(price);
		assertThat(result.getMenuGroupId()).isEqualTo(savedMenuGroupId);
		assertThat(result.getMenuProducts()).containsExactlyInAnyOrder(savedMenuProduct, savedMenuProduct2);
	}

	@Test
	@DisplayName("메뉴 등록 시, 메뉴의 가격이 없으면 IllegalArgumentException을 throw 해야한다.")
	void createPriceNull() {
		Menu emptyPriceMenu = TestDomainConstructor.menu("메뉴1", null, savedMenuGroupId, menuProducts);

		//when-then
		assertThatThrownBy(() -> menuService.create(emptyPriceMenu))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴 등록 시, 메뉴 그룹이 등록되어있지 않으면 IllegalArgumentException을 throw 해야한다.")
	void createNotExistMenuGroup() {
		//given
		Menu notExistMenuGroupMenu = TestDomainConstructor.menu("메뉴1", 10000, 100L, menuProducts);
		when(menuGroupDao.existsById(any())).thenReturn(false);

		//when-then
		assertThatThrownBy(() -> menuService.create(notExistMenuGroupMenu))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("메뉴 등록 시, 상품이 등록되어있지 않으면 IllegalArgumentException을 throw 해야한다.")
	void createNotExistProduct() {
		//given
		Menu notExistProductMenu = TestDomainConstructor.menu("메뉴1", 10000, savedMenuGroupId, Arrays.asList(mock(MenuProduct.class)));
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
		Menu greaterThanSumOfProductPriceMenu = TestDomainConstructor.menu("메뉴1", 1000000, savedMenuGroupId, menuProducts);
		when(menuGroupDao.existsById(savedMenuGroupId)).thenReturn(true);
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
		Menu menuWithTwoProducts = TestDomainConstructor.menuWithId("메뉴1", 1000, savedMenuGroupId
			, Arrays.asList(mockMenuProduct, mockMenuProduct), 1L);
		Menu menuWithThreeProducts = TestDomainConstructor.menuWithId("메뉴2", 3000, savedMenuGroupId
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
