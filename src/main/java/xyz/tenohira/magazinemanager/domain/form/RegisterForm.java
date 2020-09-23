package xyz.tenohira.magazinemanager.domain.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class RegisterForm {

	/** 雑誌名 */
	@NotBlank
	@Length(max = 30)
	private String name;
	
	/** 号数 */
	@NotBlank
	@Length(max = 20)
	private String number;
}
