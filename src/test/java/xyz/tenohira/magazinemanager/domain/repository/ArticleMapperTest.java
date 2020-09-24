package xyz.tenohira.magazinemanager.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Article;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleMapperTest {

	@Autowired
	ArticleMapper mapper;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 複数レコード取得メソッドのテスト
	@Test
	@Sql("/article-1.sql")
	@Order(1)
	public void selectTest() {
		
		// 想定結果
		List<Article> expected = new ArrayList<>();
		Article article1 = new Article();
		article1.setSection("テストセクション1-1");
		article1.setTitle("テストタイトル1-1");
		article1.setStartPage(1);
		expected.add(article1);
		Article article2 = new Article();
		article2.setSection("テストセクション1-2");
		article2.setTitle("テストタイトル1-2");
		article2.setStartPage(10);
		expected.add(article2);
		Article article3 = new Article();
		article3.setSection("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		article3.setTitle("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		article3.setStartPage(999);
		expected.add(article3);
		
		// テスト対象メソッドの実行
		int magazineId = 1;
		List<Article> actual = mapper.selectList(magazineId);
		
		// 実行結果と想定結果の比較
		assertIterableEquals(expected, actual);
	}
	
	// 削除メソッドのテスト
	@Test
	@Sql("/article-2.sql")
	@Order(2)
	public void deleteTest() {
		
		// テスト対象メソッドの実行
		int magazineId = 2;
		mapper.delete(magazineId);
		
		// テストデータの検索
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 2", Integer.class);
		
		assertEquals(0, count);
	}
	
	// 登録メソッドのテスト
	@Test
	@Sql("/article-3.sql")
	@Order(3)
	public void insertTest() {
		
		// 想定結果
		Article expected = new Article();
		expected.setMagazineId(3);
		expected.setSection("テストセクション3-1");
		expected.setTitle("テストタイトル3-1");
		expected.setStartPage(123);
				
		// テスト対象メソッドの実行
		mapper.insert(expected);
		
		// 登録データを取得する
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, section, title, start_page FROM article WHERE magazine_id = 3");
		
		// 結果比較
		assertEquals(actual.get("magazine_id"), 3);
		assertEquals(actual.get("section"), "テストセクション3-1");
		assertEquals(actual.get("title"), "テストタイトル3-1");
		assertEquals(actual.get("start_page"), 123);
	}

}
