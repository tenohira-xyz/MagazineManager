package xyz.tenohira.magazinemanager.domain.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示() throws Exception {
		
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("login"))	// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("ログイン")))
			.andExpect(content().string(containsString("ユーザ名")))
			.andExpect(content().string(containsString("<input type=\"text\" name=\"userName\" />")))
			.andExpect(content().string(containsString("パスワード")))
			.andExpect(content().string(containsString("<input type=\"password\" name=\"password\" />")))
			.andExpect(content().string(containsString("<input type=\"submit\" value=\"ログイン\" />")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示() throws Exception {
		
		mockMvc.perform(get("/login"))
			.andExpect(status().isForbidden());	// アクセス可否を検証
	}
}
