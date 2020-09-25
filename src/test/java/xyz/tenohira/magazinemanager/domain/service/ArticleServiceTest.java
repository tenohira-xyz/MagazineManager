package xyz.tenohira.magazinemanager.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Article;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleServiceTest {

	@Autowired
	ArticleService service;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 複数レコード取得メソッド
	@Test
	@Sql("/repository/article-1.sql")
	public void getListTest() {
		
		// テスト対象メソッドの実行
		List<Article> actual = service.getList(1);
		
		// 検証
		assertThat(actual.size()).isEqualTo(3);
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
	
	// 更新メソッド
	@Test
	@Sql("/service/article-2.sql")
	public void updateTest() {
		
		// 更新前の対象レコード数取得
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(2);
		
		// 登録対象
		List<Article> list = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setMagazineId(1);
		article1.setSection("テストセクション2-1-update");
		article1.setTitle("テストタイトル2-1-update");
		article1.setStartPage(10);
		list.add(article1);
		Article article2 = new Article();
		article2.setMagazineId(1);
		article2.setSection("テストセクション2-2-update");
		article2.setTitle("テストタイトル2-2-update");
		article2.setStartPage(20);
		list.add(article2);
		Article article3 = new Article();
		article3.setMagazineId(1);
		article3.setSection("テストセクション2-3-update");
		article3.setTitle("テストタイトル2-3-update");
		article3.setStartPage(30);
		list.add(article3);
		
		// テスト対象メソッドの実行
		service.update(1, list);
		
		// 更新後の対象レコード数取得
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM article WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(3);
		
		// 検証
		List<Map<String, Object>> actual = jdbcTemplate.queryForList("SELECT magazine_id, section, title, start_page FROM article WHERE magazine_id = 1");
		assertThat(actual.get(0).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(0).get("section")).isEqualTo("テストセクション2-1-update");
		assertThat(actual.get(0).get("title")).isEqualTo("テストタイトル2-1-update");
		assertThat(actual.get(0).get("start_page")).isEqualTo(10);
		assertThat(actual.get(1).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(1).get("section")).isEqualTo("テストセクション2-2-update");
		assertThat(actual.get(1).get("title")).isEqualTo("テストタイトル2-2-update");
		assertThat(actual.get(1).get("start_page")).isEqualTo(20);
		assertThat(actual.get(2).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(2).get("section")).isEqualTo("テストセクション2-3-update");
		assertThat(actual.get(2).get("title")).isEqualTo("テストタイトル2-3-update");
		assertThat(actual.get(2).get("start_page")).isEqualTo(30);
	}
}
