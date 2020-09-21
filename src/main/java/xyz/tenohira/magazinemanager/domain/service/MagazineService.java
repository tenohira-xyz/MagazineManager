package xyz.tenohira.magazinemanager.domain.service;

import java.util.List;

import xyz.tenohira.magazinemanager.domain.model.Magazine;

public interface MagazineService {

	// 存在チェックメソッド（雑誌ID）
	public boolean isExistById(int magazineId);
	
	// 存在チェックメソッド（雑誌名、号数）
	public boolean isExistByData(String name, String number);
	
	// 単一レコード取得メソッド
	public Magazine getData(int magazineId);
	
	// 複数レコード取得メソッド
	public List<Magazine> getList();
	
	// 削除メソッド
	public boolean delete(int magazineId);
	
	// 登録メソッド
	public boolean add(Magazine magazine);
	
	// 更新メソッド
	public boolean update(Magazine magazine);
}
