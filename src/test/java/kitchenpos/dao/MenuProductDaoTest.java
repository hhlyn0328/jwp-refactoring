package kitchenpos.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest
public class MenuProductDaoTest {

	@Autowired
	private MenuProductDao menuProductDao;

	@Test
	@DisplayName("메뉴상품을 정상적으로 등록하면 SEQ 가 생성된다.")
	void saveReturnSeq() {
		//given
		Long menuId = 1L;
		Long productId = 3L;
		long quantity = 2;
		MenuProduct menuProduct = TestDomainConstructor.menuProduct(menuId, productId, quantity);

		//when
		MenuProduct savedMenuProduct = menuProductDao.save(menuProduct);

		//then
		assertThat(savedMenuProduct.getSeq()).isNotNull();
		assertThat(savedMenuProduct.getMenuId()).isEqualTo(menuId);
		assertThat(savedMenuProduct.getProductId()).isEqualTo(productId);
		assertThat(savedMenuProduct.getQuantity()).isEqualTo(quantity);
	}

	@Test
	@DisplayName("메뉴상품 등록 시, 메뉴가 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutMenu() {
		//given
		MenuProduct emptyMenu = TestDomainConstructor.menuProduct(null, 1L, 1);

		//when-then
		assertThatThrownBy(() -> menuProductDao.save(emptyMenu))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("메뉴상품 등록 시, 상품이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutProduct() {
		//given
		MenuProduct emptyProduct = TestDomainConstructor.menuProduct(1L, null, 1);

		//when-then
		assertThatThrownBy(() -> menuProductDao.save(emptyProduct))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
}
