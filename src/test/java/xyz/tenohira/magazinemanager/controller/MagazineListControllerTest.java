package xyz.tenohira.magazinemanager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
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
import java.util.ArrayList;
import java.util.List;

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

import xyz.tenohira.magazinemanager.MagazineManagerApplication;
import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MagazineManagerApplication.class)
@AutoConfigureMockMvc
class MagazineListControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MagazineService service;
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_0件の場合() throws Exception {
		
		// モックの戻り値の設定
		when(service.getList()).thenReturn(new ArrayList<Magazine>());
		
		mockMvc.perform(get("/list"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("magazineList"))	// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/login\" role=\"presentation\">ログイン</a>")))
			.andExpect(content().string(containsString("<h1>雑誌一覧</h1>")))
			.andExpect(content().string(containsString("<th>雑誌名</th>")))
			.andExpect(content().string(containsString("<th>号数</th>")))
			.andExpect(content().string(containsString("<th>詳細</th>")))
			.andExpect(content().string(containsString("<th>目次</th>")))
			.andExpect(content().string(containsString("<th>索引</th>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).getList();
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_1件以上の場合() throws Exception {
		
		// モックの戻り値の設定
		List<Magazine> list = new ArrayList<Magazine>();
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		list.add(magazine);
		when(service.getList()).thenReturn(list);
		
		mockMvc.perform(get("/list"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("magazineList"))	// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/login\" role=\"presentation\">ログイン</a>")))
			.andExpect(content().string(containsString("<h1>雑誌一覧</h1>")))
			.andExpect(content().string(containsString("<th>雑誌名</th>")))
			.andExpect(content().string(containsString("<th>号数</th>")))
			.andExpect(content().string(containsString("<th>詳細</th>")))
			.andExpect(content().string(containsString("<th>目次</th>")))
			.andExpect(content().string(containsString("<th>索引</th>")))
			.andExpect(content().string(containsString("<td>テスト雑誌</td>")))
			.andExpect(content().string(containsString("<td>No.123</td>")))
			.andExpect(content().string(containsString("<a href=\"/detail/1\">詳細</a>")))
			.andExpect(content().string(containsString("<a href=\"/contents/1\">目次</a>")))
			.andExpect(content().string(containsString("<a href=\"/index/1\">索引</a>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).getList();
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で削除() throws Exception {
		
		mockMvc.perform(post(new URI("/list/delete/1")))
			.andExpect(status().isForbidden());	// アクセス可否を検証
		
		// サービスの実行回数の検証
		verify(service, times(0)).isExistById(1);
		verify(service, times(0)).delete(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_0件の場合() throws Exception {
		
		// モックの戻り値の設定
		when(service.getList()).thenReturn(new ArrayList<Magazine>());
		
		mockMvc.perform(get("/list"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("magazineList"))	// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("<h1>雑誌一覧</h1>")))
			.andExpect(content().string(containsString("<a href=\"/register\" role=\"presentation\">雑誌登録</a>")))
			.andExpect(content().string(containsString("<th>雑誌名</th>")))
			.andExpect(content().string(containsString("<th>号数</th>")))
			.andExpect(content().string(containsString("<th>詳細</th>")))
			.andExpect(content().string(containsString("<th>目次</th>")))
			.andExpect(content().string(containsString("<th>索引</th>")))
			.andExpect(content().string(containsString("<th  role=\"presentation\">削除</th>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).getList();
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_1件以上の場合() throws Exception {
		
		// モックの戻り値の設定
		List<Magazine> list = new ArrayList<Magazine>();
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		list.add(magazine);
		when(service.getList()).thenReturn(list);
		
		mockMvc.perform(get("/list"))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("magazineList"))	// 遷移先を検証
			
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("<h1>雑誌一覧</h1>")))
			.andExpect(content().string(containsString("<a href=\"/register\" role=\"presentation\">雑誌登録</a>")))
			.andExpect(content().string(containsString("<th>雑誌名</th>")))
			.andExpect(content().string(containsString("<th>号数</th>")))
			.andExpect(content().string(containsString("<th>詳細</th>")))
			.andExpect(content().string(containsString("<th>目次</th>")))
			.andExpect(content().string(containsString("<th>索引</th>")))
			.andExpect(content().string(containsString("<th  role=\"presentation\">削除</th>")))
			.andExpect(content().string(containsString("<td>テスト雑誌</td>")))
			.andExpect(content().string(containsString("<td>No.123</td>")))
			.andExpect(content().string(containsString("<a href=\"/detail/1\">詳細</a>")))
			.andExpect(content().string(containsString("<a href=\"/contents/1\">目次</a>")))
			.andExpect(content().string(containsString("<a href=\"/index/1\">索引</a>")))
			.andExpect(content().string(containsString("<button type=\"submit\" formaction=\"/list/delete/1\">削除</button>")));
		
		// サービスの実行回数の検証
		verify(service, times(1)).getList();
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で削除_成功() throws Exception {
		
		// モックの戻り値の設定
		when(service.isExistById(1)).thenReturn(true);
		when(service.delete(1)).thenReturn(true);
				
		mockMvc.perform(post(new URI("/list/delete/1")))
			.andExpect(status().isFound())	// アクセス可否を検証
			.andExpect(view().name("redirect:/list"));	// 遷移先を検証
		
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).delete(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で削除_失敗() throws Exception {
		
		// モックの戻り値の設定
		when(service.isExistById(1)).thenReturn(true);
		when(service.delete(1)).thenReturn(false);
		
		mockMvc.perform(post(new URI("/list/delete/1")))
			.andExpect(status().isOk())	// アクセス可否を検証
			.andExpect(view().name("error"));	// 遷移先を検証
	
		// サービスの実行回数の検証
		verify(service, times(1)).isExistById(1);
		verify(service, times(1)).delete(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で削除_例外() throws Exception {
		
		// モックの戻り値の設定
		when(service.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(post(new URI("/list/delete/1")))
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
		verify(service, times(0)).delete(1);
	}
	
}
