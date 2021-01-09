package kitchenpos.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest
public class OrderTableDaoTest {

	@Autowired
	private OrderTableDao orderTableDao;

	@Test
	@DisplayName("주문 테이블을 정상적으로 등록하면 ID가 생성된다.")
	void saveReturnId() {
		//given
		OrderTable orderTable = TestDomainConstructor.orderTable(null, 0, true);

		//when
		OrderTable savedOrderTable = orderTableDao.save(orderTable);

		//then
		assertThat(savedOrderTable.getId()).isNotNull();
		assertThat(savedOrderTable.getTableGroupId()).isNull();
		assertThat(savedOrderTable.getNumberOfGuests()).isZero();
		assertThat(savedOrderTable.isEmpty()).isTrue();
	}
}
