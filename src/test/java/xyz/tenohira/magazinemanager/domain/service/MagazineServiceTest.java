package xyz.tenohira.magazinemanager.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
class MagazineServiceTest {

	@Autowired
	MagazineService service;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 存在チェックメソッド（雑誌ID）
	@Test
	@Sql("/repository/magazine-1.sql")
	public void checkTest1() {
		
		// テスト対象メソッドの実行
		boolean result = service.isExistById(1);
		
		// 検証
		assertThat(result).isTrue();
	}
	
	// 存在チェックメソッド（雑誌名、号数）
	@Test
	@Sql("/repository/magazine-2.sql")
	public void checkTest2() {
		
		// テスト対象メソッドの実行
		boolean result = service.isExistByData("テスト雑誌2-1", "テスト号数2-1");
		
		// 検証
		assertThat(result).isTrue();
	}
	
	// 単一レコード取得メソッド
	@Test
	@Sql("/repository/magazine-3.sql")
	public void getDataTest() {
		
		// テスト対象メソッドの実行
		Magazine actual = service.getData(1);
		
		// 検証
		assertThat(actual.getName()).isEqualTo("テスト雑誌3-1");
		assertThat(actual.getNumber()).isEqualTo("テスト号数3-1");
		assertThat(actual.getPublisher()).isEqualTo("テスト出版社3-1");
		assertThat(actual.getIssueDate()).hasSameTimeAs("2020-01-01");
	}
	
	// 複数レコード取得メソッド
	@Test
	@Sql("/repository/magazine-4.sql")
	public void getListTest() {
		
		// テスト対象メソッドの実行
		List<Magazine> actual = service.getList();
		
		// 検証
		assertThat(actual.size()).isEqualTo(3);
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
	@Sql("/repository/magazine-5.sql")
	public void deleteTest() {
		
		// 削除前に削除対象のレコード数を検証する
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(1);
		
		// テスト対象メソッドの実行
		service.delete(1);
		
		// 検証
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(0);
	}
	
	// 登録メソッド
	@Test
	public void addTest() {
		
		// 登録前のテーブル内レコード数取得
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine", Integer.class);
		assertThat(before).isEqualTo(0);
		
		// テスト対象メソッドの実行
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌6-1");
		magazine.setNumber("テスト号数6-1");
		service.add(magazine);
		
		// 登録後のテーブル内レコード数取得
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine", Integer.class);
		assertThat(after).isEqualTo(1);
				
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT name, number FROM magazine");
		assertThat(actual.get("name")).isEqualTo("テスト雑誌6-1");
		assertThat(actual.get("number")).isEqualTo("テスト号数6-1");
	}
	
	// 更新メソッド
	@Test
	@Sql("/repository/magazine-7.sql")
	public void updateTest() {
		
		// 更新前の対象レコード数取得
		int before = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine WHERE magazine_id = 1", Integer.class);
		assertThat(before).isEqualTo(1);
				
		// テスト対象メソッドの実行
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌7-2");
		magazine.setNumber("テスト号数7-2");
		magazine.setPublisher("テスト出版社7-2");
		magazine.setIssueDate(java.sql.Date.valueOf("2019-12-31"));
		service.update(magazine);
		
		// 更新前の対象レコード数取得
		int after = jdbcTemplate.queryForObject("SELECT count(*) FROM magazine WHERE magazine_id = 1", Integer.class);
		assertThat(after).isEqualTo(1);
				
		// 検証
		Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT magazine_id, name, number, publisher, issue_date FROM magazine WHERE magazine_id = 1");
		assertThat(actual.get("magazine_id")).isEqualTo(1);
		assertThat(actual.get("name")).isEqualTo("テスト雑誌7-2");
		assertThat(actual.get("number")).isEqualTo("テスト号数7-2");
		assertThat(actual.get("publisher")).isEqualTo("テスト出版社7-2");
		assertThat((Date)actual.get("issue_date")).hasSameTimeAs("2019-12-31");
	}
}
