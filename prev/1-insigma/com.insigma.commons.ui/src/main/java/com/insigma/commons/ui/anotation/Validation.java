package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

	/**
	 * 是否允许为空，默认允许
	 * @return
	 */
	boolean nullable() default true;

	boolean rangeable() default false;

	int length() default 10;

	String regexp() default "";

	long maxValue() default Long.MAX_VALUE;

	long minValue() default 0;

	boolean key() default false;

	String describ() default "";

}
