package xyz.tenohira.magazinemanager.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
class KeywordMapperTest {

	@Autowired
	KeywordMapper mapper;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 複数レコード取得メソッド
	// 指定した雑誌が存在する場合
	@Test
	@Sql("/repository/keyword-1.sql")
	public void selectTest() {
		
		// テスト対象メソッドの実行
		List<Keyword> actual = mapper.selectList(1);
		
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
	public void selectFailedTest() {
		
		// テスト対象メソッドの実行
		List<Keyword> actual = mapper.selectList(2);
		
		assertThat(actual.size()).isEqualTo(0);
	}
	
	// 削除メソッド
	// 指定した雑誌が存在する場合
	@Test
	@Sql("/repository/keyword-2.sql")
	public void deleteTest() {
		
		// 削除前の対象テストデータ件数を検証
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(1);
		
		// テスト対象メソッドの実行
		int result = mapper.delete(1);
		
		// メソッドの戻り値を検証
		assertThat(result).isEqualTo(1);
		
		// 削除後の対象テストデータ件数を検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(0);
	}
	
	// 指定した雑誌が存在しない場合
	@Test
	@Sql("/repository/keyword-2.sql")
	public void deleteFailedTest() {
		
		// 削除前の対象テストデータ件数を検証
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(1);
		
		// テスト対象メソッドの実行
		int result = mapper.delete(2);
		
		// メソッドの戻り値を検証
		assertThat(result).isEqualTo(0);
		
		// 削除後の対象テストデータ件数を検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(1);
	}
	
	// 登録メソッド
	@Test
	@Sql("/repository/keyword-3.sql")
	public void insertTest() {
		
		// 登録前のテーブル内レコード件数を検証
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword", Integer.class);
		assertThat(before).isEqualTo(0);
		
		// 登録対象
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語3-1");
		keyword.setStartPage(123);
		
		// テスト対象メソッドの実行
		mapper.insert(keyword);
		
		// 登録後のテーブル内レコード件数を検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword", Integer.class);
		assertThat(after).isEqualTo(1);
		
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, word, start_page FROM keyword WHERE magazine_id = 1");
		assertThat(actual.get("magazine_id")).isEqualTo(1);
		assertThat(actual.get("word")).isEqualTo("テスト単語3-1");
		assertThat(actual.get("start_page")).isEqualTo(123);
	}
}
