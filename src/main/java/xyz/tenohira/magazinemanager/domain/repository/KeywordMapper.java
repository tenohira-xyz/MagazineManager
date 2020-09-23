package xyz.tenohira.magazinemanager.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import xyz.tenohira.magazinemanager.domain.model.Keyword;

@Mapper
public interface KeywordMapper {

	// 複数レコード取得メソッド
	public List<Keyword> selectList(int magazineId) throws DataAccessException;
	
	// 削除メソッド
	public int delete(int magazineId) throws DataAccessException;
	
	// 登録メソッド
	public int insert(Keyword keyword) throws DataAccessException;
}
