package xyz.tenohira.magazinemanager.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
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

import xyz.tenohira.magazinemanager.domain.model.Magazine;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MagazineMapperTest {

	@Autowired
	MagazineMapper mapper;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 存在チェックメソッド（雑誌ID）
	@Test
	@Sql("/magazine-1.sql")
	public void checkTest1() {
		
		// テスト対象メソッドの実行
		int result = mapper.selectById(1);
		
		assertThat(result).isEqualTo(1);
	}
	
	// 存在チェックメソッド（雑誌名、号数）
	@Test
	@Sql("/magazine-2.sql")
	public void checkTest2() {
		
		// テスト対象メソッドの実行
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌2-1");
		magazine.setNumber("テスト号数2-1");
		int result = mapper.selectByData(magazine);
		
		assertThat(result).isEqualTo(1);
	}
	
	// 単一レコード取得メソッド
	@Test
	@Sql("/magazine-3.sql")
	public void select() {
		
		// テスト対象メソッドの実行
		Magazine actual = mapper.select(1);
		
		assertThat(actual.getName()).isEqualTo("テスト雑誌3-1");
		assertThat(actual.getNumber()).isEqualTo("テスト号数3-1");
		assertThat(actual.getPublisher()).isEqualTo("テスト出版社3-1");
		assertThat(actual.getIssueDate()).isEqualTo("2020-01-01");
	}
	
	// 複数レコード取得メソッド
	@Test
	@Sql("/magazine-4.sql")
	public void selectList() {
		
		// テスト対象メソッドの実行
		List<Magazine> actual = mapper.selectList();
		
		assertThat(actual.get(0).getMagazineId()).isEqualTo(1);
		assertThat(actual.get(0).getName()).isEqualTo("テスト雑誌4-1");
		assertThat(actual.get(0).getNumber()).isEqualTo("テスト号数4-1");
		assertThat(actual.get(1).getMagazineId()).isEqualTo(2);
		assertThat(actual.get(1).getName()).isEqualTo("テスト雑誌4-2");
		assertThat(actual.get(1).getNumber()).isEqualTo("テスト号数4-2");
		assertThat(actual.get(2).getMagazineId()).isEqualTo(3);
		assertThat(actual.get(2).getName()).isEqualTo("□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■");
		assertThat(actual.get(2).getNumber()).isEqualTo("□□□□□□□□□■□□□□□□□□□■");
	}
	
	// 削除メソッド
	@Test
	@Sql("/magazine-5.sql")
	public void delete() {
		
		// テスト対象メソッドの実行
		mapper.delete(1);
		
		// 検証
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine WHERE magazine_id = 1", Integer.class);
		assertThat(count).isEqualTo(0);
	}
	
	// 登録メソッド
	@Test
	public void insert() {
		
		// 登録対象
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌6-1");
		magazine.setNumber("テスト号数6-1");
		
		// テスト対象メソッドの実行
		mapper.insert(magazine);
		
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT name, number, publisher, issue_date FROM magazine");
		assertThat(actual.get("name")).isEqualTo("テスト雑誌6-1");
		assertThat(actual.get("number")).isEqualTo("テスト号数6-1");
	}
	
	// 更新メソッド
	@Test
	@Sql("/magazine-7.sql")
	public void update() {
		
		// 登録対象
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌7-2");
		magazine.setNumber("テスト号数7-2");
		magazine.setPublisher("テスト出版社7-2");
		magazine.setIssueDate(java.sql.Date.valueOf("2019-12-31"));
		
		// テスト対象メソッドの実行
		mapper.update(magazine);
		
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, name, number, publisher, issue_date FROM magazine WHERE magazine_id = 1");
		assertThat(actual.get("magazine_id")).isEqualTo(1);
		assertThat(actual.get("name")).isEqualTo("テスト雑誌7-2");
		assertThat(actual.get("number")).isEqualTo("テスト号数7-2");
		assertThat(actual.get("publisher")).isEqualTo("テスト出版社7-2");
		assertThat((Date)actual.get("issue_date")).hasSameTimeAs("2019-12-31");
	}
	
}
