package kitchenpos.domain;

import java.math.BigDecimal;

public class TestDomainConstructor {
	public static Product product(String name, Integer price) {
		Product product = new Product();
		product.setName(name);
		if (price != null) {
			product.setPrice(BigDecimal.valueOf(price));
		}
		return product;
	}

	public static Product productWithId(String name, int price, long id) {
		Product product = product(name, price);
		product.setId(id);
		return product;
	}
}
