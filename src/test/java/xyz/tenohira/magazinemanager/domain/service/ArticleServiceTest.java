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

import xyz.tenohira.magazinemanager.domain.model.Article;
import xyz.tenohira.magazinemanager.domain.repository.ArticleMapper;
import xyz.tenohira.magazinemanager.domain.repository.MagazineMapper;
import xyz.tenohira.magazinemanager.domain.service.impl.ArticleServiceImpl;

@ExtendWith(SpringExtension.class)

class ArticleServiceTest {

	@InjectMocks
	ArticleServiceImpl service;
	
	@Mock
	MagazineMapper magazineMapper;
	
	@Mock
	ArticleMapper articleMapper;
	
	@Test
	public void 複数レコード取得_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		List<Article> list = new ArrayList<>();
		Article article1 = new Article();
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		list.add(article1);
		Article article2 = new Article();
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		list.add(article2);
		Article article3 = new Article();
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		list.add(article3);
		when(articleMapper.selectList(1)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> result = service.getList(1);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).selectList(1);
	}
	
	@Test
	public void 複数レコード取得_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		List<Article> list = new ArrayList<>();
		when(articleMapper.selectList(0)).thenReturn(list);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> result = service.getList(0);
		assertThat(result).isEqualTo(list);
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).selectList(0);
	}
	
	@Test
	public void 更新_雑誌が存在する() {
		
		// リポジトリ層のモックを作成する
		Article article1 = new Article();
		article1.setMagazineId(1);
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		Article article2 = new Article();
		article2.setMagazineId(1);
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		Article article3 = new Article();
		article3.setMagazineId(1);
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		when(articleMapper.delete(1)).thenReturn(2);
		when(articleMapper.insert(article1)).thenReturn(1);
		when(articleMapper.insert(article2)).thenReturn(1);
		when(articleMapper.insert(article3)).thenReturn(1);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> list = new ArrayList<>();
		list.add(article1);
		list.add(article2);
		list.add(article3);
		boolean result = service.update(1, list);
		assertThat(result).isTrue();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).delete(1);
		verify(articleMapper, times(1)).insert(article1);
		verify(articleMapper, times(1)).insert(article2);
		verify(articleMapper, times(1)).insert(article3);
	}
	
	@Test
	public void 更新_雑誌が存在しない() {
		
		// リポジトリ層のモックを作成する
		Article article1 = new Article();
		article1.setMagazineId(0);
		article1.setSection("テストセクション１");
		article1.setTitle("テストタイトル１");
		article1.setStartPage(10);
		Article article2 = new Article();
		article2.setMagazineId(0);
		article2.setSection("テストセクション２");
		article2.setTitle("テストタイトル２");
		article2.setStartPage(20);
		Article article3 = new Article();
		article3.setMagazineId(0);
		article3.setSection("テストセクション３");
		article3.setTitle("テストタイトル３");
		article3.setStartPage(30);
		when(articleMapper.delete(0)).thenReturn(0);
		when(articleMapper.insert(article1)).thenReturn(0);
		when(articleMapper.insert(article2)).thenReturn(0);
		when(articleMapper.insert(article3)).thenReturn(0);
		
		// テスト対象メソッドの戻り値を検証
		List<Article> list = new ArrayList<>();
		list.add(article1);
		list.add(article2);
		list.add(article3);
		boolean result = service.update(0, list);
		assertThat(result).isFalse();
		
		// リポジトリ層のメソッドの呼び出しを検証する
		verify(articleMapper, times(1)).delete(0);
		verify(articleMapper, times(1)).insert(article1);
		verify(articleMapper, times(1)).insert(article2);
		verify(articleMapper, times(1)).insert(article3);
	}
}
