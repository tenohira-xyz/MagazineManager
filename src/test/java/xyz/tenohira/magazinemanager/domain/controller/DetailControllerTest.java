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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;
import xyz.tenohira.magazinemanager.exception.MagazineRegisteredException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DetailControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MagazineService service;
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_雑誌が存在する() throws Exception {
		
		// モック作成
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		when(service.isExistById(1)).thenReturn(true);
		when(service.getData(1)).thenReturn(magazine);
		
		mockMvc.perform(get("/detail/1"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("detail"))		// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/login\" role=\"presentation\">ログイン</a>")))
			.andExpect(content().string(containsString("雑誌詳細")))
			.andExpect(content().string(containsString("雑誌名")))
			.andExpect(content().string(containsString("<span>テスト雑誌</span>")))
			.andExpect(content().string(containsString("号数")))
			.andExpect(content().string(containsString("<span>No.123</span>")))
			.andExpect(content().string(containsString("出版社")))
			.andExpect(content().string(containsString("<span></span>")))
			.andExpect(content().string(containsString("発行日")))
			.andExpect(content().string(containsString("<span></span>")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).getData(1);
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_雑誌が存在しない() throws Exception {
		
		// モック作成
		when(service.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(get("/detail/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// エラーメッセージ設定の検証
			.andExpect(model().attribute("status", is(HttpStatus.INTERNAL_SERVER_ERROR)))
			.andExpect(model().attribute("error", is("エラー")))
			.andExpect(model().attribute("message", is("指定した雑誌が存在しません。")))
			// 遷移先を検証
			.andExpect(view().name("error"))
			.andReturn();
		
		// 発生した例外の検証
		Exception exception = result.getResolvedException();
		assertThat(exception.getClass()).isEqualTo(MagazineNotExistException.class);
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).getData(1);
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で更新() throws Exception {
		
		mockMvc.perform(post("/detail/1/update").param("name", ""))
			.andExpect(status().isForbidden());	// アクセス可否を検証
		
		// サービスの実行回数の検証
		verify(service, times(0)).isExistById(1);
		verify(service, times(0)).getData(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_雑誌が存在する() throws Exception {
		
		// モック作成
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		magazine.setPublisher("テスト出版社");
		magazine.setIssueDate(java.sql.Date.valueOf("2020-01-01"));
		magazine.setMagazineId(1);
		when(service.isExistById(1)).thenReturn(true);
		when(service.getData(1)).thenReturn(magazine);
		
		mockMvc.perform(get("/detail/1"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("detail"))		// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("雑誌詳細")))
			.andExpect(content().string(containsString("雑誌名")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"name\" name=\"name\" value=\"テスト雑誌\" />")))
			.andExpect(content().string(containsString("号数")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"number\" name=\"number\" value=\"No.123\" />")))
			.andExpect(content().string(containsString("出版社")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"publisher\" name=\"publisher\" value=\"テスト出版社\" />")))
			.andExpect(content().string(containsString("発行日")))
			.andExpect(content().string(containsString("<input type=\"date\" id=\"issueDate\" name=\"issueDate\" value=\"2020-01-01\" />")))
			.andExpect(content().string(containsString("<input type=\"hidden\" id=\"magazineId\" name=\"magazineId\" value=\"1\" />")))
			.andExpect(content().string(containsString("<input type=\"submit\" value=\"登録\" />")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).getData(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_雑誌が存在しない() throws Exception {
		
		// モック作成
		when(service.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(get("/detail/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// エラーメッセージ設定の検証
			.andExpect(model().attribute("status", is(HttpStatus.INTERNAL_SERVER_ERROR)))
			.andExpect(model().attribute("error", is("エラー")))
			.andExpect(model().attribute("message", is("指定した雑誌が存在しません。")))
			// 遷移先を検証
			.andExpect(view().name("error"))
			.andReturn();
		
		// 発生した例外の検証
		Exception exception = result.getResolvedException();
		assertThat(exception.getClass()).isEqualTo(MagazineNotExistException.class);
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).getData(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_必須入力() throws Exception {
		
		final String name = "";
		final String number = "";
		final String publisher = "";
		final String issueDate = "";
		
		Magazine magazine = new Magazine();
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// 遷移先を検証
			.andExpect(view().name("detail"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(4))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("雑誌名が入力されていません。")))
			.andExpect(content().string(containsString("号数が入力されていません。")))
			.andExpect(content().string(containsString("出版社が入力されていません。")))
			.andExpect(content().string(containsString("発行日が入力されていません。")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_1文字() throws Exception {
		
		final String name = "あ";
		final String number = "い";
		final String publisher = "う";
		final String issueDate = "2020-01-01";
		
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName(name);
		magazine.setNumber(number);
		magazine.setPublisher(publisher);
		magazine.setIssueDate(java.sql.Date.valueOf(issueDate));
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_最大数() throws Exception {
		
		final String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほ";
		final String number = "あいうえおかきくけこさしすせそたちつてと";
		final String publisher = "あいうえおかきくけこさしすせそたちつてと";
		final String issueDate = "2020-01-01";
		
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName(name);
		magazine.setNumber(number);
		magazine.setPublisher(publisher);
		magazine.setIssueDate(java.sql.Date.valueOf(issueDate));
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_超過() throws Exception {
		
		final String name = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま";
		final String number = "あいうえおかきくけこさしすせそたちつてとな";
		final String publisher = "あいうえおかきくけこさしすせそたちつてとな";
		final String issueDate = "2020-01-01";
		
		Magazine magazine = new Magazine();
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// 遷移先を検証
			.andExpect(view().name("detail"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(3))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("雑誌名は30文字以内で入力してください。")))
			.andExpect(content().string(containsString("号数は20文字以内で入力してください。")))
			.andExpect(content().string(containsString("出版社は20文字以内で入力してください。")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_日付() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		final String publisher = "テスト出版社";
		final String issueDate = "123";
		
		Magazine magazine = new Magazine();
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// 遷移先を検証
			.andExpect(view().name("detail"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(1))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("発行日はyyyy/MM/dd形式で入力してください。")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で更新_成功() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		final String publisher = "テスト出版社";
		final String issueDate = "2020-01-01";
		
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName(name);
		magazine.setNumber(number);
		magazine.setPublisher(publisher);
		magazine.setIssueDate(java.sql.Date.valueOf(issueDate));
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(true);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// アクセス可否を検証
			.andExpect(status().isFound())
			// 遷移先を検証
			.andExpect(view().name("redirect:/list"));

		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で更新_失敗() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		final String publisher = "テスト出版社";
		final String issueDate = "2020-01-01";
		
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName(name);
		magazine.setNumber(number);
		magazine.setPublisher(publisher);
		magazine.setIssueDate(java.sql.Date.valueOf(issueDate));
		when(service.isExistById(1)).thenReturn(true);
		when(service.update(magazine)).thenReturn(false);
		
		mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("error"));

		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).update(magazine);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で更新_例外() throws Exception {
		
		final String name = "テスト雑誌";
		final String number = "No.123";
		final String publisher = "テスト出版社";
		final String issueDate = "2020-01-01";
		
		when(service.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(
				post("/detail/1/update")
					.param("magazineId", "1")
					.param("name", name)
					.param("number", number)
					.param("publisher", publisher)
					.param("issueDate", issueDate))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// エラーメッセージ設定の検証
			.andExpect(model().attribute("status", is(HttpStatus.INTERNAL_SERVER_ERROR)))
			.andExpect(model().attribute("error", is("エラー")))
			.andExpect(model().attribute("message", is("指定した雑誌が存在しません。")))
			// 遷移先を検証
			.andExpect(view().name("error"))
			.andReturn();
		
		// 発生した例外の検証
		Exception exception = result.getResolvedException();
		assertThat(exception.getClass()).isEqualTo(MagazineNotExistException.class);
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(0)).update(new Magazine());
	}
}
