package xyz.tenohira.magazinemanager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class NotAllBlankValidator implements ConstraintValidator<NotAllBlank, Object> {

	private String[] fields;
	private String message;
	
	// バリデータ初期化
	public void initialize(NotAllBlank constraintAnnotation) {
		this.fields = constraintAnnotation.fields();
		this.message = constraintAnnotation.message();
	}
	
	// 検証処理
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		for (String string : fields) {
			Object fieldValue = beanWrapper.getPropertyValue(string);
			if (StringUtils.hasText(fieldValue.toString())) {
				return true;
			}
		}
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
			.addPropertyNode(fields[0]).addConstraintViolation();
		return false;
	}
	
	
}
