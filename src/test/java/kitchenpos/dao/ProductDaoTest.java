package kitchenpos.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import kitchenpos.domain.Product;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest
public class ProductDaoTest {

	@Autowired
	private ProductDao productDao;

	@Test
	@DisplayName("상품을 정상적으로 등록하면 ID가 생성된다.")
	void saveReturnId() {
		//given
		String name = "상품1";
		int price = 1000;
		Product product = TestDomainConstructor.product(name, price);

		//when
		Product savedProduct = productDao.save(product);

		//then
		assertThat(savedProduct.getId()).isNotNull();
		assertThat(savedProduct.getName()).isEqualTo(name);
		assertThat(savedProduct.getPrice().intValue()).isEqualTo(price);
	}

	@Test
	@DisplayName("상품 등록 시, 이름이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void createWithoutName() {
		//given
		Product nameNullProduct = TestDomainConstructor.product(null, 1000);

		//when-then
		assertThatThrownBy(() -> productDao.save(nameNullProduct))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("상품 등록 시, 가격이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void createWithoutPrice() {
		//given
		Product emptyPriceProduct = TestDomainConstructor.product("상품1", null);

		//when-then
		assertThatThrownBy(() -> productDao.save(emptyPriceProduct))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
}
