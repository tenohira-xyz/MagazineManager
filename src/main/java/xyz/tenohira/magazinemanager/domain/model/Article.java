package xyz.tenohira.magazinemanager.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class Article {

	/** 雑誌ID */
	private int magazineId;
	
	/** セクション */
	private String section;
	
	/** タイトル */
	private String title;
	
	/** 開始ページ */
	private int startPage;
	
	/** 更新日付 */
	private Date updateTime;
}
