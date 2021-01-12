package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import kitchenpos.dto.MenuGroupRequest;
import kitchenpos.dto.MenuGroupResponse;
import kitchenpos.dto.MenuProductRequest;
import kitchenpos.dto.MenuProductResponse;
import kitchenpos.dto.MenuRequest;
import kitchenpos.dto.MenuResponse;
import kitchenpos.dto.ProductRequest;
import kitchenpos.dto.ProductResponse;

public class TestFixture {
	//Product
	public static final Long 상품_신규_ID = 10L;
	public static final String 상품_신규_NAME = "청양마요치킨";
	public static final BigDecimal 상품_신규_PRICE = BigDecimal.valueOf(18000);
	public static final Long 상품_등록되어있음_ID_1 = 1L;
	public static final String 상품_등록되어있음_NAME_1 = "후라이드";
	public static final BigDecimal 상품_등록되어있음_PRICE_1 = BigDecimal.valueOf(16000);
	public static final Long 상품_등록되어있음_ID_2 = 2L;
	public static final String 상품_등록되어있음_NAME_2 = "양념";
	public static final BigDecimal 상품_등록되어있음_PRICE_2 = BigDecimal.valueOf(16000);

	public static final ProductRequest 상품_등록_REQUEST = new ProductRequest(상품_신규_NAME, 상품_신규_PRICE);
	public static final Product 상품_등록됨 = new Product(상품_신규_ID, 상품_신규_NAME, 상품_신규_PRICE);
	public static final Product 상품_등록되어있음_후라이드_1L = new Product(상품_등록되어있음_ID_1, 상품_등록되어있음_NAME_1, 상품_등록되어있음_PRICE_1);
	public static final Product 상품_등록되어있음_양념_2L = new Product(상품_등록되어있음_ID_2, 상품_등록되어있음_NAME_2, 상품_등록되어있음_PRICE_2);
	public static final ProductRequest 상품_등록_가격없음_REQUEST = new ProductRequest(상품_신규_NAME, null);
	public static final ProductRequest 상품_등록_가격음수_REQUEST = new ProductRequest(상품_신규_NAME, BigDecimal.valueOf(-200));

	//MenuGroup
	public static final Long 메뉴그룹_신규_ID = 10L;
	public static final String 메뉴그룹_신규_NAME = "콤보메뉴";
	public static final Long 메뉴그룹_등록되어있음_ID_1 = 1L;
	public static final String 메뉴그룹_등록되어있음_NAME_1 = "두마리메뉴";
	public static final Long 메뉴그룹_등록되어있음_ID_2 = 2L;
	public static final String 메뉴그룹_등록되어있음_NAME_2 = "한마리메뉴";

	public static final MenuGroupRequest 메뉴그룹_등록_REQUEST = new MenuGroupRequest(메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록됨 = new MenuGroup(메뉴그룹_신규_ID, 메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록되어있음_1L = new MenuGroup(메뉴그룹_등록되어있음_ID_1, 메뉴그룹_등록되어있음_NAME_1);

	//MenuProduct
	public static final Long 메뉴상품_신규_SEQ_1 = 11L;
	public static final Long 메뉴상품_신규_SEQ_2 = 12L;
	public static final int 메뉴상품_신규_QUANTITY_1 = 1;
	public static final int 메뉴상품_신규_QUANTITY_2 = 2;
	public static final Long 메뉴상품_등록되어있음_SEQ_1 = 1L;
	public static final Long 메뉴상품_등록되어있음_메뉴_ID_1 = 1L;
	public static final Long 메뉴상품_등록되어있음_상품_ID_1 = 1L;
	public static final int 메뉴상품_등록되어있음_QUANTITY_1 = 1;
	public static final Long 메뉴상품_등록되어있음_SEQ_2 = 2L;
	public static final Long 메뉴상품_등록되어있음_메뉴_ID_2 = 2L;
	public static final Long 메뉴상품_등록되어있음_상품_ID_2 = 2L;
	public static final int 메뉴상품_등록되어있음_QUANTITY_2 = 1;
	public static final Long 메뉴상품_등록되어있지않은_상품_ID_100 = 100L;

	public static final MenuProductRequest 메뉴상품_등록_REQUEST_1 = new MenuProductRequest(상품_등록되어있음_ID_1,
		메뉴상품_신규_QUANTITY_1);
	public static final MenuProductRequest 메뉴상품_등록_REQUEST_2 = new MenuProductRequest(상품_등록되어있음_ID_1,
		메뉴상품_신규_QUANTITY_2);
	public static final MenuProduct 메뉴상품_저장됨_1 = new MenuProduct(메뉴상품_신규_SEQ_1, 메뉴_신규_ID, 메뉴_신규_메뉴그룹_ID,
		메뉴상품_신규_QUANTITY_1);
	public static final MenuProduct 메뉴상품_저장됨_2 = new MenuProduct(메뉴상품_신규_SEQ_2, 메뉴_신규_ID, 메뉴_신규_메뉴그룹_ID,
		메뉴상품_신규_QUANTITY_2);
	public static final MenuProduct 메뉴상품_등록되어있음_1L = new MenuProduct(메뉴상품_등록되어있음_SEQ_1
		, 메뉴상품_등록되어있음_메뉴_ID_1
		, 메뉴상품_등록되어있음_상품_ID_1
		, 메뉴상품_등록되어있음_QUANTITY_1);
	public static final MenuProduct 메뉴상품_등록되어있음_2L = new MenuProduct(메뉴상품_등록되어있음_SEQ_2
		, 메뉴상품_등록되어있음_메뉴_ID_2
		, 메뉴상품_등록되어있음_상품_ID_2
		, 메뉴상품_등록되어있음_QUANTITY_2);
	public static final List<MenuProductRequest> 메뉴상품_목록_등록_REQUEST_1_2 = Arrays.asList(메뉴상품_등록_REQUEST_1, 메뉴상품_등록_REQUEST_2);
	public static final List<MenuProduct> 메뉴상품_목록_등록됨_1_2 = Arrays.asList(메뉴상품_등록되어있음_1L, 메뉴상품_등록되어있음_2L);
	public static final List<MenuProduct> 메뉴상품_목록_등록되어있음_1 = Arrays.asList(메뉴상품_등록되어있음_1L);
	public static final MenuProduct 메뉴상품_등록되어있지않음_2 = new MenuProduct(메뉴상품_신규_SEQ_2, 메뉴상품_등록되어있지않은_상품_ID_100, 메뉴_신규_메뉴그룹_ID,
		메뉴상품_신규_QUANTITY_2);

	//Menu
	public static final Long 메뉴_신규_ID = 7L;
	public static final String 메뉴_신규_NAME = "마늘치킨";
	public static final BigDecimal 메뉴_신규_PRICE = BigDecimal.valueOf(18000);
	public static final Long 메뉴_신규_메뉴그룹_ID = 4L;
	public static final Long 메뉴_등록되어있음_ID_1 = 1L;
	public static final String 메뉴_등록되어있음_NAME_1 = "후라이드치킨";
	public static final BigDecimal 메뉴_등록되어있음_PRICE_1 = BigDecimal.valueOf(16000);
	public static final Long 메뉴_등록되어있음_메뉴그룹_1 = 2L;
	public static final Long 메뉴_등록되어있지않은_메뉴그룹_ID_100 = 100L;

	public static final MenuRequest 메뉴_등록_REQUEST = new MenuRequest(메뉴_신규_NAME, 메뉴_신규_PRICE, 메뉴_신규_메뉴그룹_ID, 메뉴상품_목록_등록_REQUEST_1_2);
	public static final Menu 메뉴_등록됨 = new Menu(메뉴_신규_ID, 메뉴_신규_NAME, 메뉴_신규_PRICE, 메뉴_신규_메뉴그룹_ID, 메뉴상품_목록_등록됨_1_2);
	public static final Menu 메뉴_등록되어있음_1L = new Menu(메뉴_등록되어있음_ID_1
		, 메뉴_등록되어있음_NAME_1
		, 메뉴_등록되어있음_PRICE_1
		, 메뉴_등록되어있음_메뉴그룹_1
		, 메뉴상품_목록_등록되어있음_1);
	public static final MenuRequest 메뉴_등록_가격없음_REQUEST = new MenuRequest(메뉴_신규_NAME, null, 메뉴_신규_메뉴그룹_ID, 메뉴상품_목록_등록_REQUEST_1_2);
	public static final MenuRequest 메뉴_등록_존재하지않는_메뉴그룹_REQUEST = new MenuRequest(메뉴_신규_NAME, 메뉴_신규_PRICE, 메뉴_등록되어있지않은_메뉴그룹_ID_100, 메뉴상품_목록_등록_REQUEST_1_2);
	public static final MenuRequest 메뉴_등록_존재하지않는_메뉴그룹_REQUEST = new MenuRequest(메뉴_신규_NAME, 메뉴_신규_PRICE, 등록되어있지않은_메뉴그룹_ID_100, 메뉴상품_목록_등록_REQUEST_1_2);

}
