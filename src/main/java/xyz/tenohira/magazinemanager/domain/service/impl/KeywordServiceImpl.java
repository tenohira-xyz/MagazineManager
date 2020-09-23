package xyz.tenohira.magazinemanager.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean delete(int magazineId) {
		
		int result = mapper.delete(magazineId);
		return result > 0 ? true : false;
	}

	@Override
	public int add(List<Keyword> list) {
		
		int result = 0;
		for (Keyword keyword : list) {
			result += mapper.insert(keyword);
		}
		return result;
	}

	@Override
	public boolean update(int magazineId, List<Keyword> list) {
		
		this.delete(magazineId);
		int result = this.add(list);
		return result == list.size() ? true : false;
	}

}
