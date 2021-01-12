package kitchenpos.domain;

import java.math.BigDecimal;

import kitchenpos.dto.MenuGroupRequest;
import kitchenpos.dto.MenuGroupResponse;
import kitchenpos.dto.ProductRequest;
import kitchenpos.dto.ProductResponse;

public class TestFixture {
	//Product
	public static final Long 상품_신규_ID = 10L;
	public static final String 상품_신규_NAME = "청양마요치킨";
	public static final BigDecimal 상품_신규_PRICE = BigDecimal.valueOf(18000);
	public static final Long 상품_등록되어있음1_ID = 1L;
	public static final String 상품_등록되어있음1_NAME = "후라이드";
	public static final BigDecimal 상품_등록되어있음1_PRICE = BigDecimal.valueOf(16000);

	public static final ProductRequest 상품_등록_REQUEST = new ProductRequest(상품_신규_NAME, 상품_신규_PRICE);
	public static final Product 상품_등록됨 = new Product(상품_신규_ID, 상품_신규_NAME, 상품_신규_PRICE);
	public static final ProductResponse 상품_등록_RESPONSE = new ProductResponse(상품_신규_ID, 상품_신규_NAME, 상품_신규_PRICE);
	public static final Product 상품_등록되어있음_ID_1 = new Product(상품_등록되어있음1_ID, 상품_등록되어있음1_NAME, 상품_등록되어있음1_PRICE);
	public static final ProductRequest 상품_등록_가격없음_REQUEST = new ProductRequest(상품_신규_NAME, null);
	public static final ProductRequest 상품_등록_가격음수_REQUEST = new ProductRequest(상품_신규_NAME, BigDecimal.valueOf(-200));



	//MenuGroup
	public static final Long 메뉴그룹_신규_ID = 10L;
	public static final String 메뉴그룹_신규_NAME = "콤보메뉴";
	public static final Long 메뉴그룹_등록되어있음1_ID = 1L;
	public static final String 메뉴그룹_등록되어있음1_NAME = "두마리메뉴";

	public static final MenuGroupRequest 메뉴그룹_등록_REQUEST = new MenuGroupRequest(메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록됨 = new MenuGroup(메뉴그룹_신규_ID, 메뉴그룹_신규_NAME);
	public static final MenuGroupResponse 메뉴그룹_등록_RESPONSE = new MenuGroupResponse(메뉴그룹_신규_ID, 메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록되어있음_ID_1 = new MenuGroup(메뉴그룹_등록되어있음1_ID, 메뉴그룹_등록되어있음1_NAME);


}
