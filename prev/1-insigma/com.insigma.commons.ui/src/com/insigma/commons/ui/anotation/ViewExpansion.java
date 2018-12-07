/**
 * 
 */
package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DLF
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewExpansion {

	String viewClass() default "";

}
