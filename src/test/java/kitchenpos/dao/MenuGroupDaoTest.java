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
	void createWithoutName() {
		//given
		MenuGroup nameNullMenuGroup = TestDomainConstructor.menuGroup(null);

		//when-then
		assertThatThrownBy(() -> menuGroupDao.save(nameNullMenuGroup))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
}
