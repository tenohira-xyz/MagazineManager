package xyz.tenohira.magazinemanager.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Article;
import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.repository.ArticleMapper;
import xyz.tenohira.magazinemanager.domain.repository.MagazineMapper;
import xyz.tenohira.magazinemanager.domain.service.impl.ArticleServiceImpl;

@ExtendWith(SpringExtension.class)

class ArticleServiceTest {

	@InjectMocks
	ArticleServiceImpl service;
	
	@Mock
	MagazineMapper magazineMapper;
	
	@Mock
	ArticleMapper articleMapper;
	
	@Test
	public void 複数レコード取得_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		List<Article> list = new ArrayList<>();
		Article article1 = new Article();
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		list.add(article1);
		Article article2 = new Article();
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		list.add(article2);
		Article article3 = new Article();
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		list.add(article3);
		when(articleMapper.selectList(1)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> result = service.getList(1);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).selectList(1);
	}
	
	@Test
	public void 複数レコード取得_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		List<Article> list = new ArrayList<>();
		when(articleMapper.selectList(0)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> result = service.getList(0);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).selectList(0);
	}
	
	@Test
	public void 更新_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Article article1 = new Article();
		article1.setMagazineId(1);
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		Article article2 = new Article();
		article2.setMagazineId(1);
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		Article article3 = new Article();
		article3.setMagazineId(1);
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		when(articleMapper.delete(1)).thenReturn(2);
		when(articleMapper.insert(article1)).thenReturn(1);
		when(articleMapper.insert(article2)).thenReturn(1);
		when(articleMapper.insert(article3)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> list = new ArrayList<>();
		list.add(article1);
		list.add(article2);
		list.add(article3);
		boolean result = service.update(1, list);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).delete(1);
		verify(articleMapper, times(1)).insert(article1);
		verify(articleMapper, times(1)).insert(article2);
		verify(articleMapper, times(1)).insert(article3);
	}
	
	@Test
	public void 更新_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Article article1 = new Article();
		article1.setMagazineId(0);
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		Article article2 = new Article();
		article2.setMagazineId(0);
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		Article article3 = new Article();
		article3.setMagazineId(0);
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		when(articleMapper.delete(0)).thenReturn(0);
		when(articleMapper.insert(article1)).thenReturn(0);
		when(articleMapper.insert(article2)).thenReturn(0);
		when(articleMapper.insert(article3)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> list = new ArrayList<>();
		list.add(article1);
		list.add(article2);
		list.add(article3);
		boolean result = service.update(0, list);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).delete(0);
		verify(articleMapper, times(1)).insert(article1);
		verify(articleMapper, times(1)).insert(article2);
		verify(articleMapper, times(1)).insert(article3);
	}
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	
//	// 複数レコード取得メソッド
//	// 指定した雑誌が存在する場合
//	@Test
//	@Sql("/repository/article-1.sql")
//	public void getListTest() {
//		
//		// テスト対象メソッドの実行
//		List<Article> actual = service.getList(1);
//		
//		// 検証
//		assertThat(actual.size()).isEqualTo(3);
//		assertThat(actual.get(0).getSection()).isEqualTo("テストセクション1-1");
//		assertThat(actual.get(0).getTitle()).isEqualTo("テストタイトル1-1");
//		assertThat(actual.get(0).getStartPage()).isEqualTo(1);
//		assertThat(actual.get(1).getSection()).isEqualTo("テストセクション1-2");
//		assertThat(actual.get(1).getTitle()).isEqualTo("テストタイトル1-2");
//		assertThat(actual.get(1).getStartPage()).isEqualTo(10);
//		assertThat(actual.get(2).getSection()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
//		assertThat(actual.get(2).getTitle()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
//		assertThat(actual.get(2).getStartPage()).isEqualTo(999);
//	}
//	
//	// 指定した雑誌が存在しない場合
//	@Test
//	@Sql("/repository/article-1.sql")
//	public void getListFailedTest() {
//		
//		// テスト対象メソッドの実行
//		List<Article> actual = service.getList(2);
//		
//		assertThat(actual.size()).isEqualTo(0);
//	}
//	
//	// 更新メソッド
//	// 指定した雑誌が存在する場合
//	@Test
//	@Sql("/service/article-2.sql")
//	public void updateTest() {
//		
//		// 更新前の対象レコード数取得
//		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
//		assertThat(before).isEqualTo(2);
//		
//		// 登録対象
//		List<Article> list = new ArrayList<Article>();
//		Article article1 = new Article();
//		article1.setMagazineId(1);
//		article1.setSection("テストセクション2-1-update");
//		article1.setTitle("テストタイトル2-1-update");
//		article1.setStartPage(10);
//		list.add(article1);
//		Article article2 = new Article();
//		article2.setMagazineId(1);
//		article2.setSection("テストセクション2-2-update");
//		article2.setTitle("テストタイトル2-2-update");
//		article2.setStartPage(20);
//		list.add(article2);
//		Article article3 = new Article();
//		article3.setMagazineId(1);
//		article3.setSection("テストセクション2-3-update");
//		article3.setTitle("テストタイトル2-3-update");
//		article3.setStartPage(30);
//		list.add(article3);
//		
//		// テスト対象メソッドの実行
//		boolean result = service.update(1, list);
//		
//		// メソッドの戻り値を検証
//		assertThat(result).isTrue();
//		
//		// 更新後の対象レコード数取得
//		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
//		assertThat(after).isEqualTo(3);
//		
//		// 検証
//		List<Map<String, Object>> actual = jdbcTemplate.queryForList("SELECT magazine_id, section, title, start_page FROM article WHERE magazine_id = 1");
//		assertThat(actual.get(0).get("magazine_id")).isEqualTo(1);
//		assertThat(actual.get(0).get("section")).isEqualTo("テストセクション2-1-update");
//		assertThat(actual.get(0).get("title")).isEqualTo("テストタイトル2-1-update");
//		assertThat(actual.get(0).get("start_page")).isEqualTo(10);
//		assertThat(actual.get(1).get("magazine_id")).isEqualTo(1);
//		assertThat(actual.get(1).get("section")).isEqualTo("テストセクション2-2-update");
//		assertThat(actual.get(1).get("title")).isEqualTo("テストタイトル2-2-update");
//		assertThat(actual.get(1).get("start_page")).isEqualTo(20);
//		assertThat(actual.get(2).get("magazine_id")).isEqualTo(1);
//		assertThat(actual.get(2).get("section")).isEqualTo("テストセクション2-3-update");
//		assertThat(actual.get(2).get("title")).isEqualTo("テストタイトル2-3-update");
//		assertThat(actual.get(2).get("start_page")).isEqualTo(30);
//	}
//	
//	// 指定した雑誌が存在しない場合
//	@Test
//	@Sql("/service/article-2.sql")
//	public void updateFailedTest() {
//		
//		// 更新前の対象レコード数取得
//		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
//		assertThat(before).isEqualTo(2);
//		
//		// 登録対象
//		List<Article> list = new ArrayList<Article>();
//		Article article1 = new Article();
//		article1.setMagazineId(2);
//		article1.setSection("テストセクション2-1-update");
//		article1.setTitle("テストタイトル2-1-update");
//		article1.setStartPage(10);
//		list.add(article1);
//		Article article2 = new Article();
//		article2.setMagazineId(2);
//		article2.setSection("テストセクション2-2-update");
//		article2.setTitle("テストタイトル2-2-update");
//		article2.setStartPage(20);
//		list.add(article2);
//		Article article3 = new Article();
//		article3.setMagazineId(2);
//		article3.setSection("テストセクション2-3-update");
//		article3.setTitle("テストタイトル2-3-update");
//		article3.setStartPage(30);
//		list.add(article3);
//		
//		// テスト対象メソッドの実行
//		boolean result = service.update(2, list);
//		
//		// メソッドの戻り値を検証
//		assertThat(result).isFalse();
//		
//		// 更新後の対象レコード数取得
//		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
//		assertThat(after).isEqualTo(2);
//	}
}
