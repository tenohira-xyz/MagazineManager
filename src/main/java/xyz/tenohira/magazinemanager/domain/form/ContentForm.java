package xyz.tenohira.magazinemanager.domain.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;
import xyz.tenohira.magazinemanager.validation.NotAllBlank;

@Data
public class ContentForm {

	/** 記事リスト */
	@Valid
	private List<Article> articleList;
	
	/** 雑誌ID */
	private int magazineId;
	
	// 記事リストに項目を追加
	public void addList(xyz.tenohira.magazinemanager.domain.model.Article article) {
		
		if (articleList == null) {
			articleList = new ArrayList<>();
		}
		
		Article item = new Article();
		item.setSection(article.getSection());
		item.setTitle(article.getTitle());
		item.setStartPage(article.getStartPage());
		
		articleList.add(item);
	}
	
	// 記事リストに枠（入力欄）を追加
	public void addList() {
		
		Article item = new Article();
		articleList.add(item);
	}
	
	// 記事リストから枠（入力欄）を削除
	public void removeList(int index) {
		
		if (articleList.size() > 1) {
			articleList.remove(index);
		}
	}
}
