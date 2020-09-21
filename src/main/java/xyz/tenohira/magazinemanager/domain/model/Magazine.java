package xyz.tenohira.magazinemanager.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class Magazine {

	/** 雑誌ID */
	private int magazineId;
	/** 雑誌名 */
	private String name;
	/** 号数 */
	private String number;
	/** 出版社 */
	private String publisher;
	/** 発行日 */
	private Date issueDate;
	/** 更新日付 */
	private Date updateTime;
}
