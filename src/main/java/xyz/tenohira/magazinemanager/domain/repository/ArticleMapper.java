package xyz.tenohira.magazinemanager.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import xyz.tenohira.magazinemanager.domain.model.Article;

@Mapper
public interface ArticleMapper {

	// 複数レコード取得メソッド
	public List<Article> selectList(int magazineId) throws DataAccessException;
	
	// 削除メソッド
	public int delete(int magazineId) throws DataAccessException;
	
	// 登録メソッド
	public int insert(Article article) throws DataAccessException;
}
