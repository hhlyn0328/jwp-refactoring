package kitchenpos.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import kitchenpos.BaseDaoTest;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest
public class OrderDaoTest extends BaseDaoTest {

	@Autowired
	private OrderDao orderDao;

	private static final Long NOT_EMPTY_TABLE_ID = 9L;
	private static final String NEW_ORDER_STATUS = OrderStatus.COOKING.name();

	@Test
	@DisplayName("주문을 정상적으로 등록하면 ID가 생성된다.")
	void saveReturnId() {
		//given
		Order order = TestDomainConstructor.order(NOT_EMPTY_TABLE_ID, NEW_ORDER_STATUS, LocalDateTime.now(), new ArrayList<>());

		//when
		Order savedOrder = orderDao.save(order);

		//then
		assertThat(savedOrder.getId()).isNotNull();
		assertThat(savedOrder.getOrderStatus()).isEqualTo(NEW_ORDER_STATUS);
		assertThat(savedOrder.getOrderedTime()).isNotNull();
	}

	@Test
	@DisplayName("주문 등록 시, 주문 테이블이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutOrderTable() {
		//given
		Order withoutOrderTableOrder = TestDomainConstructor.order(null, NEW_ORDER_STATUS, LocalDateTime.now(), new ArrayList<>());

		//when-then
		assertThatThrownBy(() -> orderDao.save(withoutOrderTableOrder))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("주문 등록 시, 주문 상태가 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutStatus() {
		//given
		Order withoutStatusOrder = TestDomainConstructor.order(NOT_EMPTY_TABLE_ID, null, LocalDateTime.now(), new ArrayList<>());

		//when-then
		assertThatThrownBy(() -> orderDao.save(withoutStatusOrder))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("주문 등록 시, 주문 시각이 없으면 DataIntegrityViolationException 을 throw 해야한다.")
	void saveWithoutOrderedTime() {
		//given
		Order withoutOrderedTimeOrder = TestDomainConstructor.order(NOT_EMPTY_TABLE_ID, NEW_ORDER_STATUS, null, new ArrayList<>());

		//when-then
		assertThatThrownBy(() -> orderDao.save(withoutOrderedTimeOrder))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
}
