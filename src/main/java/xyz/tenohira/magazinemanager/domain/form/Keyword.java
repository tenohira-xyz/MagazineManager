package xyz.tenohira.magazinemanager.domain.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class Keyword {

	/** キーワード */
	@NotBlank
	@Length(max = 50)
	private String word;
	
	/** 開始ページ */
	@NotNull
	@Min(0)
	@Max(999)
	private int startPage;
}
