package xyz.tenohira.magazinemanager.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Article;
import xyz.tenohira.magazinemanager.domain.repository.ArticleMapper;
import xyz.tenohira.magazinemanager.domain.service.ArticleService;

@Transactional
@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper mapper;
	
	@Override
	public List<Article> getList(int magazineId) {
		
		List<Article> list = mapper.selectList(magazineId);
		return list;
	}

	@Override
	public boolean update(int magazineId, List<Article> list) {
		
		// 指定した雑誌IDのレコードを削除
		mapper.delete(magazineId);
		
		int result = 0;
		try {
			// 記事を1件ずつ登録
			for (Article article : list) {
				result += mapper.insert(article);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		
		return result == list.size() ? true : false;
	}
}
