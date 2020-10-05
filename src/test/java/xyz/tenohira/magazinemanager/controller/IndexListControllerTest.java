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
import xyz.tenohira.magazinemanager.domain.model.Keyword;
import xyz.tenohira.magazinemanager.domain.service.KeywordService;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MagazineManagerApplication.class)
@AutoConfigureMockMvc
class IndexListControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MagazineService magazineService;
	
	@MockBean
	KeywordService keywordService;
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_雑誌が存在する_リスト0件() throws Exception {
		
		// モックの戻り値の設定
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.getList(1)).thenReturn(new ArrayList<Keyword>());
		
		mockMvc.perform(get("/index/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/login\" role=\"presentation\">ログイン</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).getList(1);
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_雑誌が存在する_リスト1件以上() throws Exception {
		
		final String word = "テスト単語";
		final int startPage = 123;
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setWord(word);
		keyword.setStartPage(startPage);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.getList(1)).thenReturn(list);
		
		mockMvc.perform(get("/index/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/login\" role=\"presentation\">ログイン</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString(word)))
			.andExpect(content().string(containsString(String.valueOf(startPage))))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).getList(1);
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で初期表示_雑誌が存在しない() throws Exception {
		
		// モックの戻り値の設定
		when(magazineService.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(get("/index/1"))
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
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).getList(1);
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で削除() throws Exception {
		
		mockMvc.perform(post("/index/1/edit").param("remove", "1"))
			.andExpect(status().isForbidden());	// アクセス可否を検証
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で追加() throws Exception {
		
		mockMvc.perform(post("/index/1/edit").param("add", ""))
			.andExpect(status().isForbidden());	// アクセス可否を検証
	}
	
	@Test
	@WithAnonymousUser
	public void 非ログイン状態で登録() throws Exception {
		
		mockMvc.perform(post("/index/1/update"))
			.andExpect(status().isForbidden());	// アクセス可否を検証
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_雑誌が存在する_リスト0件() throws Exception {
		
		// モックの戻り値の設定
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.getList(1)).thenReturn(new ArrayList<Keyword>());
		
		mockMvc.perform(get("/index/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString("削除")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).getList(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_雑誌が存在する_リスト1件以上() throws Exception {
		
		final String word = "テスト単語";
		final int startPage = 123;
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setWord(word);
		keyword.setStartPage(startPage);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.getList(1)).thenReturn(list);
		
		mockMvc.perform(get("/index/1"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString("削除")))
			.andExpect(content().string(containsString(word)))
			.andExpect(content().string(containsString(String.valueOf(startPage))))
			.andExpect(content().string(containsString("<button type=\"submit\" name=\"remove\" value=\"0\" formaction=\"/index/1/edit\">削除</button>")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).getList(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で初期表示_雑誌が存在しない() throws Exception {
		
		// モックの戻り値の設定
		when(magazineService.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(get("/index/1"))
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
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).getList(1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で削除() throws Exception {
		
		mockMvc.perform(post("/index/1/edit")
				.param("magazineId", "1")
				.param("keywordList[0].word", "単語１")
				.param("keywordList[0].startPage", "10")
				.param("keywordList[1].word", "単語２")
				.param("keywordList[1].startPage", "20")
				.param("remove", "1"))
			.andExpect(status().isOk())	// アクセス可否を検証
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString("削除")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"keywordList0.word\" name=\"keywordList[0].word\" value=\"単語１\" />")))
			.andExpect(content().string(containsString("<input type=\"number\" id=\"keywordList0.startPage\" name=\"keywordList[0].startPage\" value=\"10\" />")))
			.andExpect(content().string(containsString("<button type=\"submit\" name=\"remove\" value=\"0\" formaction=\"/index/1/edit\">削除</button>")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で追加() throws Exception {
		
		mockMvc.perform(post("/index/1/edit")
				.param("magazineId", "1")
				.param("keywordList[0].word", "単語１")
				.param("keywordList[0].startPage", "10")
				.param("add", ""))
			.andExpect(status().isOk())	// アクセス可否を検証
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// 表示内容を検証
			.andExpect(content().string(containsString("<a href=\"/\">MagazineManager</a>")))
			.andExpect(content().string(containsString("<a href=\"/logout\" role=\"presentation\">ログアウト</a>")))
			.andExpect(content().string(containsString("索引")))
			.andExpect(content().string(containsString("キーワード")))
			.andExpect(content().string(containsString("開始ページ")))
			.andExpect(content().string(containsString("削除")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"keywordList0.word\" name=\"keywordList[0].word\" value=\"単語１\" />")))
			.andExpect(content().string(containsString("<input type=\"number\" id=\"keywordList0.startPage\" name=\"keywordList[0].startPage\" value=\"10\" />")))
			.andExpect(content().string(containsString("<button type=\"submit\" name=\"remove\" value=\"0\" formaction=\"/index/1/edit\">削除</button>")))
			.andExpect(content().string(containsString("<input type=\"text\" id=\"keywordList1.word\" name=\"keywordList[1].word\" value=\"\" />")))
			.andExpect(content().string(containsString("<input type=\"number\" id=\"keywordList1.startPage\" name=\"keywordList[1].startPage\" value=\"\" />")))
			.andExpect(content().string(containsString("<button type=\"submit\" name=\"remove\" value=\"1\" formaction=\"/index/1/edit\">削除</button>")))
			.andExpect(content().string(containsString("<a href=\"/list\">戻る</a>")));
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_必須入力() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "")
				.param("keywordList[0].startPage", ""))
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(2))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("キーワードが入力されていません。")))
			.andExpect(content().string(containsString("開始ページが入力されていません。")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_1文字() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("□");
		keyword.setStartPage(123);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "□")
				.param("keywordList[0].startPage", "123"))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_最大数() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("□□□□□□□□□①□□□□□□□□□②□□□□□□□□□③□□□□□□□□□④□□□□□□□□□⑤");
		keyword.setStartPage(123);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "□□□□□□□□□①□□□□□□□□□②□□□□□□□□□③□□□□□□□□□④□□□□□□□□□⑤")
				.param("keywordList[0].startPage", "123"))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_文字列長_超過() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "□□□□□□□□□①□□□□□□□□□②□□□□□□□□□③□□□□□□□□□④□□□□□□□□□⑤□")
				.param("keywordList[0].startPage", "123"))
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(1))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("キーワードは50文字以内で入力してください。")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_数値型か() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "abc"))
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(1))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("開始ページは数値を入力してください。")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_数値_マイナス1() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "-1"))
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(1))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("開始ページには0以上の数値を入力してください。")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_数値_0() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語");
		keyword.setStartPage(0);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "0"))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_数値_999() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語");
		keyword.setStartPage(999);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "999"))
			// 遷移先を検証
			.andExpect(view().name("error"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(0));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 入力チェック_数値_1000() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "1000"))
			// 遷移先を検証
			.andExpect(view().name("indexList"))
			// エラーメッセージ数の検証
			.andExpect(model().errorCount(1))
			// エラーメッセージの検証
			.andExpect(content().string(containsString("開始ページには999以下の数値を入力してください。")));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_成功() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語");
		keyword.setStartPage(123);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(true);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "123"))
			// アクセス可否を検証
			.andExpect(status().isFound())
			// 遷移先を検証
			.andExpect(view().name("redirect:/list"));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_失敗() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語");
		keyword.setStartPage(123);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(true);
		when(keywordService.update(1, list)).thenReturn(false);
		
		mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "123"))
			// アクセス可否を検証
			.andExpect(status().isOk())
			// 遷移先を検証
			.andExpect(view().name("error"));
		
		// サービスの実行回数の検証
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(1)).update(1, list);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void 管理者権限で登録_例外() throws Exception {
		
		// モックの戻り値の設定
		List<Keyword> list = new ArrayList<>();
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語");
		keyword.setStartPage(123);
		list.add(keyword);
		when(magazineService.isExistById(1)).thenReturn(false);
		
		MvcResult result = mockMvc.perform(post("/index/1/update")
				.param("magazineId", "1")
				.param("keywordList[0].word", "テスト単語")
				.param("keywordList[0].startPage", "123"))
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
		verify(magazineService, times(1)).isExistById(1);
		verify(keywordService, times(0)).update(1, list);
	}
	
}
