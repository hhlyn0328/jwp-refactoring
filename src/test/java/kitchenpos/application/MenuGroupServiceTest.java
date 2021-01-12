package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchenpos.dao.MenuGroupDao;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.TestFixture;
import kitchenpos.dto.MenuGroupRequest;
import kitchenpos.dto.MenuGroupResponse;

@ExtendWith(MockitoExtension.class)
public class MenuGroupServiceTest {
	@Mock
	private MenuGroupDao menuGroupDao;
	@InjectMocks
	private MenuGroupService menuGroupService;

	@Test
	@DisplayName("메뉴그룹을 등록할 수 있다.")
	void create() {
		//given
		when(menuGroupDao.save(any(MenuGroup.class))).thenReturn(TestFixture.메뉴그룹_등록됨);

		//when
		MenuGroupResponse result = menuGroupService.create(TestFixture.메뉴그룹_등록_REQUEST);

		//then
		assertThat(result.getId()).isEqualTo(TestFixture.메뉴그룹_신규_ID);
		assertThat(result.getName()).isEqualTo(TestFixture.메뉴그룹_신규_NAME);
	}
}
