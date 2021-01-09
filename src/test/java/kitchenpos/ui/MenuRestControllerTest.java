package kitchenpos.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.TestDomainConstructor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayName("메뉴 Controller 테스트")
public class MenuRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("메뉴를 등록할 수 있다 - 메뉴 등록 후, 등록된 메뉴의 아이디를 포함한 정보를 반환한다.")
	void create() throws Exception {
		//given
		String name = "후라이드-양념 콤보";
		int price = 10000;
		MenuProduct menuProduct1 = TestDomainConstructor.menuProduct(null, 1L, 2);
		MenuProduct menuProduct2 = TestDomainConstructor.menuProduct(null, 2L, 1);
		Menu menu = TestDomainConstructor.menu(name, price, 1L, Arrays.asList(menuProduct1, menuProduct2));

		//when-then
		mockMvc.perform(post("/api/menus")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(menu)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.price").value(price));
	}

	@Test
	@DisplayName("메뉴의 목록을 조회할 수 있다.")
	void list() throws Exception {
		//given
		create();

		//when-then
		mockMvc.perform(get("/api/menus"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$..id").isNotEmpty())
			.andExpect(jsonPath("$..name").isNotEmpty())
			.andExpect(jsonPath("$..menuGroupId").isNotEmpty())
			.andExpect(jsonPath("$..menuProducts").isNotEmpty())
			.andExpect(jsonPath("$..price").isNotEmpty());
	}
}

