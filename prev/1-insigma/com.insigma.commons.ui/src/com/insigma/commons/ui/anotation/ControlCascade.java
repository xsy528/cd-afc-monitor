/**
 * 
 */
package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.insigma.commons.ui.casecade.CasecadeListener;

/**
 * @author DLF
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ControlCascade {

	String changedField();

	/**
	 * 获取级联接口的类型： byName：通过listenerName（）从Application中获取--现有问题;
	 * 					   byType：通过casecadeListener()从Application中获取
	 * @return
	 */
	String type() default "byType";

	/**
	 *  Application中获取级联接口类型
	 * @return
	 */
	Class<? extends CasecadeListener> casecadeListener() default CasecadeListener.class;

	/**
	 *  Application中获取级联接口名称
	 * @return
	 */
	String listenerName() default "";
}
