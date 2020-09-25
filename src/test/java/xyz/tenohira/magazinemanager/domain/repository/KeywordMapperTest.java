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
	@Test
	@Sql("/keyword-1.sql")
	public void selectTest() {
		
		// テスト対象メソッドの実行
		List<Keyword> actual = mapper.selectList(1);
		
		// 検証
		assertThat(actual.get(0).getWord()).isEqualTo("テスト単語1-1");
		assertThat(actual.get(0).getStartPage()).isEqualTo(1);
		assertThat(actual.get(1).getWord()).isEqualTo("テスト単語1-2");
		assertThat(actual.get(1).getStartPage()).isEqualTo(10);
		assertThat(actual.get(2).getWord()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		assertThat(actual.get(2).getStartPage()).isEqualTo(999);
	}
	
	// 削除メソッド
	@Test
	@Sql("/keyword-2.sql")
	public void deleteTest() {
		
		// テスト対象メソッドの実行
		mapper.delete(1);
		
		// 検証
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM keyword WHERE magazine_id = 1", Integer.class);
		assertThat(count).isEqualTo(0);
	}
	
	
	// 登録メソッド
	@Test
	@Sql("/keyword-3.sql")
	public void insertTest() {
		
		// 登録対象
		Keyword keyword = new Keyword();
		keyword.setMagazineId(1);
		keyword.setWord("テスト単語3-1");
		keyword.setStartPage(123);
		
		// テスト対象メソッドの実行
		mapper.insert(keyword);
		
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, word, start_page FROM keyword WHERE magazine_id = 1");
		assertThat(actual.get("magazine_id")).isEqualTo(1);
		assertThat(actual.get("word")).isEqualTo("テスト単語3-1");
		assertThat(actual.get("start_page")).isEqualTo(123);
	}
}
