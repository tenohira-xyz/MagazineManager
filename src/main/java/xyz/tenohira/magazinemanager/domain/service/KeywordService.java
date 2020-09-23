package xyz.tenohira.magazinemanager.domain.service;

import java.util.List;

import xyz.tenohira.magazinemanager.domain.model.Keyword;

public interface KeywordService {

	// 複数レコード取得メソッド
	public List<Keyword> getList(int magazineId);
	
	// 削除メソッド
	public boolean delete(int magazineId);
	
	// 登録メソッド
	public int add(List<Keyword> list);
	
	// 更新メソッド
	public boolean update(int magazineId, List<Keyword> list);
}
