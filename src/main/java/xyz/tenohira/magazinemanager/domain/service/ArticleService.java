package xyz.tenohira.magazinemanager.domain.service;

import java.util.List;

import xyz.tenohira.magazinemanager.domain.model.Article;

public interface ArticleService {

	// 複数レコード取得メソッド
	public List<Article> getList(int magazineId);
	
	// 更新メソッド
	public boolean update(int magazineId, List<Article> list);
}
