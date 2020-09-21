package xyz.tenohira.magazinemanager.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MagazineListForm {

	/** 雑誌リスト */
	private List<MagazineListForm.Magazine> magazineList;
	
	// 雑誌リストへ項目を追加
	public void addList(xyz.tenohira.magazinemanager.domain.model.Magazine magazine) {
		
		if (magazineList == null) {
			magazineList = new ArrayList<>();
		}
		
		MagazineListForm.Magazine item = new MagazineListForm.Magazine();
		item.name = magazine.getName();
		item.number = magazine.getNumber();
		item.id = magazine.getMagazineId();
		
		magazineList.add(item);
	}
	
	@Data
	public class Magazine {
		/** 雑誌ID */
		private int id;
		/** 雑誌名 */
		private String name;
		/** 号数 */
		private String number;
	}
}
