package xyz.tenohira.magazinemanager.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {NotAllBlankValidator.class})
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotAllBlank {

	String message() default "{xyz.tenohira.magazinemanager.validation.NotAllBlank.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	// バリデータの属性定義
	String[] fields();
	
	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
		NotAllBlank[] value();
	}
}
