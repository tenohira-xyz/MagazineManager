package xyz.tenohira.magazinemanager.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.repository.ArticleMapper;
import xyz.tenohira.magazinemanager.domain.repository.KeywordMapper;
import xyz.tenohira.magazinemanager.domain.repository.MagazineMapper;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;

@Transactional
@Service("MagazineServiceImpl")
public class MagazineServiceImpl implements MagazineService {

	@Autowired
	MagazineMapper magazineMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	KeywordMapper keywordMapper;
	
	@Override
	public boolean isExistById(int magazineId) {
		
		// レコード数取得
		int count = magazineMapper.selectById(magazineId);
		return count > 0 ? true : false;
	}

	@Override
	public boolean isExistByData(String name, String number) {
		
		Magazine magazine = new Magazine();
		magazine.setName(name);
		magazine.setNumber(number);
		
		// レコード数取得
		int count = magazineMapper.selectByData(magazine);
		return count > 0 ? true : false;
	}

	@Override
	public Magazine getData(int magazineId) {
		
		// レコード取得
		Magazine magazine = magazineMapper.select(magazineId);
		return magazine;
	}

	@Override
	public List<Magazine> getList() {
		
		// レコードリスト取得
		List<Magazine> list = magazineMapper.selectList();
		return list;
	}

	@Override
	public boolean delete(int magazineId) {
		
		int articleResult = articleMapper.delete(magazineId);
		int keywordResult = keywordMapper.delete(magazineId);
		
		int magazineResult = magazineMapper.delete(magazineId);
		return magazineResult > 0 ? true : false;
	}

	@Override
	public boolean add(Magazine magazine) {
		
		int result = magazineMapper.insert(magazine);
		return result > 0 ? true : false;
	}

	@Override
	public boolean update(Magazine magazine) {
		
		int result = magazineMapper.update(magazine);
		return result > 0 ? true : false;
	}

}
