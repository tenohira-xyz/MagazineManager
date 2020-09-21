package xyz.tenohira.magazinemanager.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import xyz.tenohira.magazinemanager.domain.model.Magazine;

@Mapper
public interface MagazineMapper {

	// 存在チェックメソッド（雑誌ID）
	public int selectById(int magazineId) throws DataAccessException;
	
	// 存在チェックメソッド（雑誌名、号数）
	public int selectByData(Magazine magazine) throws DataAccessException;
	
	// 単一レコード取得メソッド
	public Magazine select(int magazineId) throws DataAccessException;
	
	// 複数レコード取得メソッド
	public List<Magazine> selectList() throws DataAccessException;
	
	// 削除メソッド
	public int delete(int magazineId) throws DataAccessException;
	
	// 登録メソッド
	public int insert(Magazine magazine) throws DataAccessException;
	
	// 更新メソッド
	public int update(Magazine magazine) throws DataAccessException;
}
