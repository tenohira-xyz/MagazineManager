package xyz.tenohira.magazinemanager.domain.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.sym.Name;

import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;
import xyz.tenohira.magazinemanager.exception.MagazineRegisteredException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MagazineService service;
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示() throws Exception {
		
		mockMvc.perform(post(new URI("/register")))
			.andExpect(status().isForbidden());	// アクセス可否を検証
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で登録() throws Exception {
		
		mockMvc.perform(post(new URI("/register/create")))
			.andExpect(status().isForbidden());	// アクセス可否を検証
		
		// サービスの実行回数の検証
		verify(service, times(0)).isExistByData("テスト雑誌", "No.123");
		verify(service, times(0)).add(new Magazine());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示() throws Exception {
		
		mockMvc.perform(get("/register"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("register"))		// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\">ログアウト</a>")))
			.andExpect(content().string(containsString("雑誌新規登録")))
			.andExpect(content().string(containsString("雑誌名")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"name\" name=\"name\" value=\"\" />")))
			.andExpect(content().string(containsString("号数")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"number\" name=\"number\" value=\"\" />")))
			.andExpect(content().string(containsString("<input type=\"submit\" value=\"登録\" />")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_必須入力() throws Exception {
		
		final String name = "";
		final String number = "";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(false);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(view().name("register"))		// 遷移先を検証
			.andExpect(model().errorCount(2))	// エラーメッセージ数の検証
			// エラーメッセージの検証
			.andExpect(content().string(containsString("雑誌名が入力されていません。")))
			.andExpect(content().string(containsString("号数が入力されていません。")));
		
		// サービスの実行回数の検証
		verify(service, times(0)).isExistByData(name, number);
		verify(service, times(0)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_1文字() throws Exception {
		
		final String name = "あ";
		final String number = "い";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(false);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(view().name("error"))		// 遷移先を検証
			.andExpect(model().errorCount(0));	// エラーメッセージ数の検証
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistByData(name, number);
		verify(service, times(1)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_最大数() throws Exception {
		
		final String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほ";
		final String number = "あいうえおかきくけこさしすせそたちつてと";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(false);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(view().name("error"))		// 遷移先を検証
			.andExpect(model().errorCount(0));	// エラーメッセージ数の検証
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistByData(name, number);
		verify(service, times(1)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_超過() throws Exception {
		
		final String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま";
		final String number = "あいうえおかきくけこさしすせそたちつてとな";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(false);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(view().name("register"))		// 遷移先を検証
			.andExpect(model().errorCount(2))	// エラーメッセージ数の検証
			// エラーメッセージの検証
			.andExpect(content().string(containsString("雑誌名は30文字以内で入力してください。")))
			.andExpect(content().string(containsString("号数は20文字以内で入力してください。")));
		
		// サービスの実行回数の検証
		verify(service, times(0)).isExistByData(name, number);
		verify(service, times(0)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_成功() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(true);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(status().isFound())	// アクセス可否を検証
			.andExpect(view().name("redirect:/list"));		// 遷移先を検証
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistByData(name, number);
		verify(service, times(1)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_失敗() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(false);
		when(service.add(magazine)).thenReturn(false);
		
		mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("error"));		// 遷移先を検証
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistByData(name, number);
		verify(service, times(1)).add(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_例外() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		
		// モックの戻り値の設定
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		when(service.isExistByData(name, number)).thenReturn(true);
		when(service.add(magazine)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(post("/register/create").param("name", name).param("number", number))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// エラーメッセージ設定の検証
			.andExpect(model().attribute("status", is(HttpStatus.INTERNAL_SERVER_ERROR)))
			.andExpect(model().attribute("error", is("エラー")))
			.andExpect(model().attribute("message", is("指定した内容の雑誌は既に登録されています。")))
			// 遷移先を検証
			.andExpect(view().name("error"))
			.andReturn();
		
		// 発生した例外の検証
		Exception exception = result.getResolvedException();
		assertThat(exception.getClass()).isEqualTo(MagazineRegisteredException.class);
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistByData(name, number);
		verify(service, times(0)).add(magazine);
	}

}
