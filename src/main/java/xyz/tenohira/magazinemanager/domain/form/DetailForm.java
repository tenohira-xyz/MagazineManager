package xyz.tenohira.magazinemanager.domain.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DetailForm {

	/** 雑誌名 */
	@NotBlank
	@Length(max = 30)
	private String name;
	
	/** 号数 */
	@NotBlank
	@Length(max = 20)
	private String number;
	
	/** 出版社 */
	@NotBlank
	@Length(max = 20)
	private String publisher;
	
	/** 発行日 */
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date issueDate;
	
	/** 雑誌ID */
	private int magazineId;
}
