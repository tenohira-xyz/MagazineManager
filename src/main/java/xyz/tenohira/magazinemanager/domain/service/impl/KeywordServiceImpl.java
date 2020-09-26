package xyz.tenohira.magazinemanager.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Keyword;
import xyz.tenohira.magazinemanager.domain.repository.KeywordMapper;
import xyz.tenohira.magazinemanager.domain.service.KeywordService;

@Transactional
@Service("KeywordServiceImpl")
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	KeywordMapper mapper;
	
	@Override
	public List<Keyword> getList(int magazineId) {
		
		List<Keyword> list = mapper.selectList(magazineId);
		return list;
	}

	@Override
	public boolean update(int magazineId, List<Keyword> list) {
		
		// 指定した雑誌IDのレコードを削除
		mapper.delete(magazineId);
		
		int result = 0;
		try {
			// 記事を1件ずつ登録
			for (Keyword keyword : list) {
				result += mapper.insert(keyword);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		
		return result == list.size() ? true : false;
	}
}
