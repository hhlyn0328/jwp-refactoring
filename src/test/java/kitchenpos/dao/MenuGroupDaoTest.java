package kitchenpos.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest
public class MenuGroupDaoTest {

	@Autowired
	private MenuGroupDao menuGroupDao;

	@Test
	@DisplayName("메뉴그룹을 정상적으로 등록하면 ID가 생성된다.")
	void saveReturnId() {
		//given
		String name = "메뉴그룹1";
		MenuGroup menuGroup = TestDomainConstructor.menuGroup(name);

		//when
		MenuGroup savedMenuGroup = menuGroupDao.save(menuGroup);

		//then
		assertThat(savedMenuGroup.getId()).isNotNull();
		assertThat(savedMenuGroup.getName()).isEqualTo(name);
	}

	@Test
	@DisplayName("메뉴그룹 등록 시, 이름이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutName() {
		//given
		MenuGroup emptyNameMenuGroup = TestDomainConstructor.menuGroup(null);

		//when-then
		assertThatThrownBy(() -> menuGroupDao.save(emptyNameMenuGroup))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("등록된 메뉴그룹 Id로, 등록여부를 확인하면 true 를 반환한다.")
	void existsByExistId() {
		//given
		Long existId = 1L;

		//when-then
		assertThat(menuGroupDao.existsById(existId)).isTrue();
	}

	@Test
	@DisplayName("등록되지 않은 메뉴그룹 Id로, 등록여부를 확인하면 true 를 반환한다.")
	void existsByNotExistId() {
		//given
		Long notExistId = 100L;

		//when-then
		assertThat(menuGroupDao.existsById(notExistId)).isFalse();
	}
}
