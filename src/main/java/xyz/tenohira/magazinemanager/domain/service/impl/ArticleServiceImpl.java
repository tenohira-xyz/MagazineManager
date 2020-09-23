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
	public boolean delete(int magazineId) {
		
		int result = mapper.delete(magazineId);
		return result > 0 ? true : false;
	}

	@Override
	public int add(List<Article> list) {
		
		int result = 0;
		for (Article article : list) {
			result += mapper.insert(article);
		}
		return result;
	}

	@Override
	public boolean update(int magazineId, List<Article> list) {
		
		this.delete(magazineId);
		int result = this.add(list);
		return result ==list.size() ? true : false;
	}
}
