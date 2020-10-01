package xyz.tenohira.magazinemanager.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.repository.ArticleMapper;
import xyz.tenohira.magazinemanager.domain.repository.KeywordMapper;
import xyz.tenohira.magazinemanager.domain.repository.MagazineMapper;
import xyz.tenohira.magazinemanager.domain.service.impl.MagazineServiceImpl;

@ExtendWith(SpringExtension.class)
class MagazineServiceTest {

	@InjectMocks
	MagazineServiceImpl service;
	
	@Mock
	MagazineMapper magazineMapper;
	
	@Mock
	ArticleMapper articleMapper;
	
	@Mock
	KeywordMapper keywordMapper;
	
	@Test
	public void 存在チェック_雑誌ID_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		when(magazineMapper.selectById(1)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.isExistById(1);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectById(1);
	}
	
	@Test
	public void 存在チェック_雑誌ID_誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		when(magazineMapper.selectById(0)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.isExistById(0);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectById(0);
	}
	
	@Test
	public void 存在チェック_雑誌名と号数_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setName("存在する雑誌");
		magazine.setNumber("No.123");
		when(magazineMapper.selectByData(magazine)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.isExistByData("存在する雑誌", "No.123");
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectByData(magazine);
	}
	
	@Test
	public void 存在チェック_雑誌名と号数_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setName("存在しない雑誌");
		magazine.setNumber("No.0");
		when(magazineMapper.selectByData(magazine)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.isExistByData("存在しない雑誌", "No.0");
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectByData(magazine);
	}
	
	@Test
	public void 単一レコード取得_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setName("存在する雑誌");
		magazine.setNumber("No.123");
		magazine.setPublisher("テスト出版社");
		magazine.setIssueDate(java.sql.Date.valueOf("2020-01-01"));
		when(magazineMapper.select(1)).thenReturn(magazine);
		
		// テスト対象メソッドの戻り値を検証
		Magazine result = service.getData(1);
		assertThat(result).isEqualTo(magazine);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).select(1);
	}
	
	@Test
	public void 単一レコード取得_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		when(magazineMapper.select(0)).thenReturn(magazine);
		
		// テスト対象メソッドの戻り値を検証
		Magazine result = service.getData(0);
		assertThat(result).isEqualTo(magazine);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).select(0);
	}
	
	@Test
	public void 複数レコード取得_1件以上() {
		
		// リポジトリ層のモックを作成する
		List<Magazine> list = new ArrayList<>();
		Magazine magazine1 = new Magazine();
		magazine1.setMagazineId(1);
		magazine1.setName("テスト雑誌１");
		magazine1.setNumber("No.123");
		list.add(magazine1);
		Magazine magazine2 = new Magazine();
		magazine2.setMagazineId(1);
		magazine2.setName("テスト雑誌１");
		magazine2.setNumber("No.123");
		list.add(magazine2);
		Magazine magazine3 = new Magazine();
		magazine3.setMagazineId(1);
		magazine3.setName("テスト雑誌１");
		magazine3.setNumber("No.123");
		list.add(magazine3);
		when(magazineMapper.selectList()).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Magazine> result = service.getList();
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectList();
	}
	
	@Test
	public void 複数レコード取得_0件() {
		
		// リポジトリ層のモックを作成する
		List<Magazine> list = new ArrayList<>();
		when(magazineMapper.selectList()).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Magazine> result = service.getList();
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).selectList();
	}
	
	@Test
	public void 削除_雑誌が存在する_雑誌レコードのみ() {
		
		// リポジトリ層のモックを作成する
		when(magazineMapper.delete(1)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.delete(1);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).delete(1);
	}
	
	@Test
	public void 削除_雑誌が存在する_記事とキーワードあり() {
		
		// リポジトリ層のモックを作成する
		when(magazineMapper.delete(1)).thenReturn(1);
		when(articleMapper.delete(1)).thenReturn(1);
		when(keywordMapper.delete(1)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.delete(1);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).delete(1);
		verify(articleMapper, times(1)).delete(1);
		verify(keywordMapper, times(1)).delete(1);
	}
	
	@Test
	public void 削除_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		when(magazineMapper.delete(0)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.delete(0);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).delete(0);
	}
	
	@Test
	public void 登録_成功() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		when(magazineMapper.insert(magazine)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.add(magazine);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).insert(magazine);
	}
	
	@Test
	public void 登録_失敗() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		when(magazineMapper.insert(magazine)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.add(magazine);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).insert(magazine);
	}
	
	@Test
	public void 更新_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		magazine.setPublisher("テスト出版社");
		magazine.setIssueDate(java.sql.Date.valueOf("2020-01-01"));
		when(magazineMapper.update(magazine)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.update(magazine);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).update(magazine);
	}
	
	@Test
	public void 更新_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Magazine magazine = new Magazine();
		magazine.setMagazineId(1);
		magazine.setName("テスト雑誌");
		magazine.setNumber("No.123");
		magazine.setPublisher("テスト出版社");
		magazine.setIssueDate(java.sql.Date.valueOf("2020-01-01"));
		when(magazineMapper.update(magazine)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		boolean result = service.update(magazine);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(magazineMapper, times(1)).update(magazine);
	}
	
}
