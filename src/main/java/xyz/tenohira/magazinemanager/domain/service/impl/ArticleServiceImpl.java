package xyz.tenohira.magazinemanager.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		// 記事を1件ずつ登録
		int result = 0;
		for (Article article : list) {
			result += mapper.insert(article);
		}
		
		return result == list.size() ? true : false;
	}
}
