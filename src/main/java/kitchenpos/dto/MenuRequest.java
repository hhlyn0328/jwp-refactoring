package kitchenpos.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;

public class MenuRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<MenuProductRequest> menuProducts;

    public MenuRequest(Long id, String name, BigDecimal price, Long menuGroupId,
        List<MenuProductRequest> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public MenuRequest(String name, BigDecimal price, Long menuGroupId,
        List<MenuProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public Menu toMenu() {
        List<MenuProduct> menuProducts = this.menuProducts.stream()
            .map(MenuProductRequest::toMenuProduct)
            .collect(Collectors.toList());
        return new Menu(this.id, this.name, this.price, this.menuGroupId, menuProducts);
    }
}
