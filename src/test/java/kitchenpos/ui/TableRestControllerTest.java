package kitchenpos.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayName("메뉴 Controller 테스트")
public class TableRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("주문 테이블을 등록할 수 있다 - 테이블 등록 후, 등록된 테이블의 아이디를 포함한 정보를 반환한다.")
	void create() throws Exception {
		//given
		int numberOfGuests = 0;
		OrderTable orderTable = TestDomainConstructor.orderTable(null, numberOfGuests, true);

		//when-then
		mockMvc.perform(post("/api/tables")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(orderTable)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.tableGroupId").isEmpty())
			.andExpect(jsonPath("$.numberOfGuests").value(numberOfGuests))
			.andExpect(jsonPath("$.empty").value(true));
	}

	@Test
	@DisplayName("메뉴의 목록을 조회할 수 있다.")
	void list() throws Exception {
		//when-then
		mockMvc.perform(get("/api/tables"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$..id").isNotEmpty())
			.andExpect(jsonPath("$..numberOfGuests").isNotEmpty())
			.andExpect(jsonPath("$..empty").isNotEmpty());
	}

	@Test
	@DisplayName("빈 테이블 설정을 해제 할 수 있다 - 상태 변경 후, 변경된 테이블 정보를 반환한다.")
	void changeToNotEmpty() throws Exception {
		//given
		Long emptyOrderTableId = 1L;
		OrderTable orderTable = TestDomainConstructor.orderTableWithId(null, 0, false, emptyOrderTableId);

		//when-then
		mockMvc.perform(put(String.format("/api/tables/%d/empty", emptyOrderTableId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(orderTable)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(emptyOrderTableId))
			.andExpect(jsonPath("$.tableGroupId").isEmpty())
			.andExpect(jsonPath("$.empty").value(false));
	}

	@Test
	@DisplayName("빈 테이블 설정 할 수 있다 - 상태 변경 후, 변경된 테이블 정보를 반환한다.")
	void changeToEmpty() throws Exception {
		//given
		Long notEmptyOrderTableId = 1L;
		OrderTable orderTable = TestDomainConstructor.orderTableWithId(null, 0, true, notEmptyOrderTableId);

		//when-then
		mockMvc.perform(put(String.format("/api/tables/%d/empty", notEmptyOrderTableId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(orderTable)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(notEmptyOrderTableId))
			.andExpect(jsonPath("$.tableGroupId").isEmpty())
			.andExpect(jsonPath("$.empty").value(true));
	}

	@Test
	@DisplayName("방문한 손님 수를 입력할 수 있다 - 손님 수 변경 후, 변경된 테이블 정보를 반환한다.")
	void changeNumberOfGuests() throws Exception {
		//given
		changeToNotEmpty();
		Long orderTableId = 1L;
		int numberOfGuests = 4;
		OrderTable orderTable = TestDomainConstructor.orderTableWithId(null, numberOfGuests, false, orderTableId);

		//when-then
		mockMvc.perform(put(String.format("/api/tables/%d/number-of-guests", orderTableId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(orderTable)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(orderTableId))
			.andExpect(jsonPath("$.tableGroupId").isEmpty())
			.andExpect(jsonPath("$.numberOfGuests").value(numberOfGuests))
			.andExpect(jsonPath("$.empty").value(false));
	}
}

