package kitchenpos.domain;

import kitchenpos.dto.MenuGroupRequest;
import kitchenpos.dto.MenuGroupResponse;

public class TestFixture {
	//MenuGroup
	public static final Long 메뉴그룹_신규_ID = 10L;
	public static final String 메뉴그룹_신규_NAME = "콤보메뉴";
	public static final Long 메뉴그룹_등록되어있음1_ID = 10L;
	public static final String 메뉴그룹_등록되어있음1_NAME = "두마리메뉴";

	public static final MenuGroupRequest 메뉴그룹_등록_REQUEST = new MenuGroupRequest(메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록됨 = new MenuGroup(메뉴그룹_신규_ID, 메뉴그룹_신규_NAME);
	public static final MenuGroupResponse 메뉴그룹_등록_RESPONSE = new MenuGroupResponse(메뉴그룹_신규_ID, 메뉴그룹_신규_NAME);
	public static final MenuGroup 메뉴그룹_등록되어있음_ID_1 = new MenuGroup(메뉴그룹_등록되어있음1_ID, 메뉴그룹_등록되어있음1_NAME);
}
