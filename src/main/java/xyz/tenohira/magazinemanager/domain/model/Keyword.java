package xyz.tenohira.magazinemanager.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class Keyword {
	
	/** 雑誌ID */
	private int magazineId;
	
	/** キーワード */
	private String word;
	
	/** 開始ページ */
	private int startPage;
	
	/** 更新日付 */
	private Date updateTime;
}
