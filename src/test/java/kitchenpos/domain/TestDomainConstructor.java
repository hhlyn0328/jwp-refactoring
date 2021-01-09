package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.List;

public class TestDomainConstructor {
	public static Product product(String name, Integer price) {
		Product product = new Product();
		product.setName(name);
		if (price != null) {
			product.setPrice(BigDecimal.valueOf(price));
		}
		return product;
	}

	public static Product productWithId(String name, int price, Long id) {
		Product product = product(name, price);
		product.setId(id);
		return product;
	}

	public static MenuGroup menuGroup(String name) {
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setName(name);
		return menuGroup;
	}

	public static MenuGroup menuGroupWithId(String name, Long Id) {
		MenuGroup menuGroup = menuGroup(name);
		menuGroup.setId(Id);
		return menuGroup;
	}

	public static Menu menu(String name, Integer price, Long menuGroupId, List<MenuProduct> menuProducts) {
		Menu menu = new Menu();
		menu.setName(name);
		menu.setMenuGroupId(menuGroupId);
		menu.setMenuProducts(menuProducts);
		if (price != null) {
			menu.setPrice(BigDecimal.valueOf(price));
		}
		return menu;
	}

	public static Menu menuWithId(String name, Integer price, Long menuGroupId, List<MenuProduct> menuProducts, Long Id) {
		Menu menu = menu(name, price, menuGroupId, menuProducts);
		menu.setId(Id);
		return menu;
	}

	public static MenuProduct menuProduct(Long menuId, Long productId, long quantity) {
		MenuProduct menuProduct = new MenuProduct();
		menuProduct.setMenuId(menuId);
		menuProduct.setProductId(productId);
		menuProduct.setQuantity(quantity);
		return menuProduct;
	}

	public static MenuProduct menuProductWithId(Long menuId, Long productId, Long quantity, Long seq) {
		MenuProduct menuProduct = menuProduct(menuId, productId, quantity);
		menuProduct.setSeq(seq);
		return menuProduct;
	}
}
