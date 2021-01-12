package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import kitchenpos.domain.TestFixture;
import kitchenpos.dto.ProductResponse;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	@Mock
	private ProductDao productDao;
	@InjectMocks
	private ProductService productService;

	@Test
	@DisplayName("상품을 등록할 수 있다.")
	void create() {
		//given
		when(productDao.save(any(Product.class))).thenReturn(TestFixture.상품_등록됨);

		//when
		ProductResponse result = productService.create(TestFixture.상품_등록_REQUEST);

		//then
		assertThat(result.getId()).isEqualTo(TestFixture.상품_신규_ID);
		assertThat(result.getName()).isEqualTo(TestFixture.상품_신규_NAME);
		assertThat(result.getPrice()).isEqualTo(TestFixture.상품_신규_PRICE);
	}

	@Test
	@DisplayName("상품 등록 시, 상품의 가격이 없으면 IllegalArgumentException을 throw 해야한다.")
	void createPriceNull() {
		//when-then
		assertThatThrownBy(() -> productService.create(TestFixture.상품_등록_가격없음_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("상품 등록 시, 상품의 가격이 0 원 미만이면 IllegalArgumentException을 throw 해야한다.")
	void createPriceLessThanZero() {
		//when-then
		assertThatThrownBy(() -> productService.create(TestFixture.상품_등록_가격음수_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
