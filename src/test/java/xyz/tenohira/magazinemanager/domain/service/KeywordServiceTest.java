package xyz.tenohira.magazinemanager.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import xyz.tenohira.magazinemanager.domain.model.Keyword;
import xyz.tenohira.magazinemanager.domain.repository.KeywordMapper;
import xyz.tenohira.magazinemanager.domain.repository.MagazineMapper;
import xyz.tenohira.magazinemanager.domain.service.impl.KeywordServiceImpl;

@ExtendWith(SpringExtension.class)
class KeywordServiceTest {

	@InjectMocks
	KeywordServiceImpl service;
	
	@Mock
	MagazineMapper magazineMapper;
	
	@Mock
	KeywordMapper keywordMapper;
	
	@Test
	public void 複数レコード取得_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		List<Keyword> list = new ArrayList<>();
		Keyword keyword1 = new Keyword();
		keyword1.setWord("テスト単語１");
		keyword1.setStartPage(10);
		list.add(keyword1);
		Keyword keyword2 = new Keyword();
		keyword2.setWord("テスト単語２");
		keyword2.setStartPage(20);
		list.add(keyword2);
		Keyword keyword3 = new Keyword();
		keyword3.setWord("テスト単語３");
		keyword3.setStartPage(30);
		list.add(keyword3);
		when(keywordMapper.selectList(1)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Keyword> result = service.getList(1);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(keywordMapper, times(1)).selectList(1);
	}
	
	@Test
	public void 複数レコード取得_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		List<Keyword> list = new ArrayList<>();
		when(keywordMapper.selectList(0)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Keyword> result = service.getList(0);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(keywordMapper, times(1)).selectList(0);
	}
	
	@Test
	public void 更新_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Keyword keyword1 = new Keyword();
		keyword1.setMagazineId(1);
		keyword1.setWord("テスト単語１");
		keyword1.setStartPage(10);
		Keyword keyword2 = new Keyword();
		keyword2.setMagazineId(1);
		keyword2.setWord("テスト単語２");
		keyword2.setStartPage(20);
		Keyword keyword3 = new Keyword();
		keyword3.setMagazineId(1);
		keyword3.setWord("テスト単語３");
		keyword3.setStartPage(30);
		when(keywordMapper.delete(1)).thenReturn(2);
		when(keywordMapper.insert(keyword1)).thenReturn(1);
		when(keywordMapper.insert(keyword2)).thenReturn(1);
		when(keywordMapper.insert(keyword3)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		List<Keyword> list = new ArrayList<>();
		list.add(keyword1);
		list.add(keyword2);
		list.add(keyword3);
		boolean result = service.update(1, list);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(keywordMapper, times(1)).delete(1);
		verify(keywordMapper, times(1)).insert(keyword1);
		verify(keywordMapper, times(1)).insert(keyword2);
		verify(keywordMapper, times(1)).insert(keyword3);
	}
	
	@Test
	public void 更新_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Keyword keyword1 = new Keyword();
		keyword1.setMagazineId(0);
		keyword1.setWord("テスト単語１");
		keyword1.setStartPage(10);
		Keyword keyword2 = new Keyword();
		keyword2.setMagazineId(0);
		keyword2.setWord("テスト単語２");
		keyword2.setStartPage(20);
		Keyword keyword3 = new Keyword();
		keyword3.setMagazineId(0);
		keyword3.setWord("テスト単語３");
		keyword3.setStartPage(30);
		when(keywordMapper.delete(0)).thenReturn(0);
		when(keywordMapper.insert(keyword1)).thenReturn(0);
		when(keywordMapper.insert(keyword2)).thenReturn(0);
		when(keywordMapper.insert(keyword3)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		List<Keyword> list = new ArrayList<>();
		list.add(keyword1);
		list.add(keyword2);
		list.add(keyword3);
		boolean result = service.update(0, list);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(keywordMapper, times(1)).delete(0);
		verify(keywordMapper, times(1)).insert(keyword1);
		verify(keywordMapper, times(1)).insert(keyword2);
		verify(keywordMapper, times(1)).insert(keyword3);
	}
}
