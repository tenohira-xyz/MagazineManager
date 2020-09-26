package xyz.tenohira.magazinemanager.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
class ArticleMapperTest {

	@Autowired
	ArticleMapper mapper;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 複数レコード取得メソッドのテスト
	@Test
	@Sql("/repository/article-1.sql")
	public void selectTest() {
		
		// テスト対象メソッドの実行
		List<Article> actual = mapper.selectList(1);
		
		// 実行結果と想定結果の比較
		assertThat(actual.get(0).getSection()).isEqualTo("テストセクション1-1");
		assertThat(actual.get(0).getTitle()).isEqualTo("テストタイトル1-1");
		assertThat(actual.get(0).getStartPage()).isEqualTo(1);
		assertThat(actual.get(1).getSection()).isEqualTo("テストセクション1-2");
		assertThat(actual.get(1).getTitle()).isEqualTo("テストタイトル1-2");
		assertThat(actual.get(1).getStartPage()).isEqualTo(10);
		assertThat(actual.get(2).getSection()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		assertThat(actual.get(2).getTitle()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		assertThat(actual.get(2).getStartPage()).isEqualTo(999);
	}
	
	// 削除メソッドのテスト
	@Test
	@Sql("/repository/article-2.sql")
	public void deleteTest() {
		
		// 削除前の対象テストデータ件数を検証
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(1);
		
		// テスト対象メソッドの実行
		mapper.delete(1);
		
		// 削除後の対象テストデータ件数を検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(0);
	}
	
	// 登録メソッドのテスト
	@Test
	@Sql("/repository/article-3.sql")
	public void insertTest() {
		
		// 登録前のテーブル内レコード件数を検証
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM article", Integer.class);
		assertThat(before).isEqualTo(0);
		
		// 登録対象
		Article article = new Article();
		article.setMagazineId(1);
		article.setSection("テストセクション3-1");
		article.setTitle("テストタイトル3-1");
		article.setStartPage(123);
				
		// テスト対象メソッドの実行
		mapper.insert(article);
		
		// 登録後のテーブル内レコード件数を検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM article", Integer.class);
		assertThat(after).isEqualTo(1);
		
		// 登録データを取得する
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, section, title, start_page FROM article WHERE magazine_id = 1");
		
		// 結果比較
		assertThat(actual.get("magazine_id")).isEqualTo(1);
		assertThat(actual.get("section")).isEqualTo("テストセクション3-1");
		assertThat(actual.get("title")).isEqualTo("テストタイトル3-1");
		assertThat(actual.get("start_page")).isEqualTo(123);
	}

}
