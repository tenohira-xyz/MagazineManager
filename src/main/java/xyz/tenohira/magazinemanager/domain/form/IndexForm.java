package xyz.tenohira.magazinemanager.domain.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class IndexForm {

	/** キーワードリスト */
	@Valid
	private List<Keyword> keywordList;
	
	/** 雑誌ID */
	private int magazineId;
	
	// キーワードリストに項目を追加
	public void addList(xyz.tenohira.magazinemanager.domain.model.Keyword keyword) {
		
		if (keywordList == null) {
			keywordList = new ArrayList<>();
		}
		
		Keyword item = new Keyword();
		item.setWord(keyword.getWord());
		item.setStartPage(keyword.getStartPage());
		
		keywordList.add(item);
	}
	
	// キーワードリストに枠（入力欄）を追加
	public void addList() {
		
		Keyword item = new Keyword();
		keywordList.add(item);
	}
	
	// キーワードリストから枠（入力欄）を削除
	public void removeList(int index) {
		
		if (keywordList.size() > 1) {
			keywordList.remove(index);
		}
	}
}
