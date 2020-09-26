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

import xyz.tenohira.magazinemanager.domain.model.Keyword;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class KeywordServiceTest {

	@Autowired
	KeywordService service;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 複数レコード取得メソッド
	// 指定した雑誌が存在する場合
	@Test
	@Sql("/repository/keyword-1.sql")
	public void getListTest() {
		
		// テスト対象メソッドの実行
		List<Keyword> actual = service.getList(1);
		
		// 検証
		assertThat(actual.size()).isEqualTo(3);
		assertThat(actual.get(0).getWord()).isEqualTo("テスト単語1-1");
		assertThat(actual.get(0).getStartPage()).isEqualTo(1);
		assertThat(actual.get(1).getWord()).isEqualTo("テスト単語1-2");
		assertThat(actual.get(1).getStartPage()).isEqualTo(10);
		assertThat(actual.get(2).getWord()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		assertThat(actual.get(2).getStartPage()).isEqualTo(999);
	}
	
	// 指定した雑誌が存在しない場合
	@Test
	@Sql("/repository/keyword-1.sql")
	public void getListFailedTest() {
		
		// テスト対象メソッドの実行
		List<Keyword> actual = service.getList(2);
		
		assertThat(actual.size()).isEqualTo(0);
	}
	
	// 更新メソッド
	// 指定した雑誌が存在する場合
	@Test
	@Sql("/service/keyword-2.sql")
	public void updateTest() {
		
		// 更新前の対象レコード数取得
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(2);
		
		// 登録対象
		List<Keyword> list = new ArrayList<Keyword>();
		Keyword keyword1 = new Keyword();
		keyword1.setMagazineId(1);
		keyword1.setWord("テスト単語2-1-update");
		keyword1.setStartPage(10);
		list.add(keyword1);
		Keyword keyword2 = new Keyword();
		keyword2.setMagazineId(1);
		keyword2.setWord("テスト単語2-2-update");
		keyword2.setStartPage(20);
		list.add(keyword2);
		Keyword keyword3 = new Keyword();
		keyword3.setMagazineId(1);
		keyword3.setWord("テスト単語2-3-update");
		keyword3.setStartPage(30);
		list.add(keyword3);
		
		// テスト対象メソッドの実行
		boolean result = service.update(1, list);
		
		// メソッドの戻り値を検証
		assertThat(result).isTrue();
		
		// 更新後の対象レコード数取得
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(3);
		
		// 検証
		List<Map<String, Object>> actual = jdbcTemplate.queryForList("SELECT magazine_id, word, start_page FROM keyword WHERE magazine_id = 1");
		assertThat(actual.get(0).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(0).get("word")).isEqualTo("テスト単語2-1-update");
		assertThat(actual.get(0).get("start_page")).isEqualTo(10);
		assertThat(actual.get(1).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(1).get("word")).isEqualTo("テスト単語2-2-update");
		assertThat(actual.get(1).get("start_page")).isEqualTo(20);
		assertThat(actual.get(2).get("magazine_id")).isEqualTo(1);
		assertThat(actual.get(2).get("word")).isEqualTo("テスト単語2-3-update");
		assertThat(actual.get(2).get("start_page")).isEqualTo(30);
	}
	
	// 指定した雑誌が存在しない場合
	@Test
	@Sql("/service/keyword-2.sql")
	public void updateFailedTest() {
		
		// 更新前の対象レコード数取得
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(2);
		
		// 登録対象
		List<Keyword> list = new ArrayList<Keyword>();
		Keyword keyword1 = new Keyword();
		keyword1.setMagazineId(2);
		keyword1.setWord("テスト単語2-1-update");
		keyword1.setStartPage(10);
		list.add(keyword1);
		Keyword keyword2 = new Keyword();
		keyword2.setMagazineId(2);
		keyword2.setWord("テスト単語2-2-update");
		keyword2.setStartPage(20);
		list.add(keyword2);
		Keyword keyword3 = new Keyword();
		keyword3.setMagazineId(2);
		keyword3.setWord("テスト単語2-3-update");
		keyword3.setStartPage(30);
		list.add(keyword3);
		
		// テスト対象メソッドの実行
		boolean result = service.update(2, list);
		
		// メソッドの戻り値を検証
		assertThat(result).isFalse();
		
		// 更新後の対象レコード数取得
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(2);
	}
}
