package xyz.tenohira.magazinemanager.domain.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import xyz.tenohira.magazinemanager.validation.NotAllBlank;

@Data
@NotAllBlank(fields = {"section", "title"})
public class Article {

	/** セクション */
	@Length(max = 30)
	private String section;
	
	/** タイトル */
	@Length(max = 50)
	private String title;
	
	/** 開始ページ */
	@NotNull
	@Min(0)
	@Max(999)
	private Integer startPage;
}
