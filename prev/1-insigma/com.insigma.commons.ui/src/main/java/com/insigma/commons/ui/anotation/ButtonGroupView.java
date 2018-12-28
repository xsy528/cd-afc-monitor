/**
 * 
 */
package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.swt.SWT;

/**
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ButtonGroupView {
	/**
	 * 
	 * 一行显示的控件数量
	 * @return
	 */
	int numColumns() default 4;

	/**
	 * ButtonGroup的样式，SWT.CHECK，SWT.RADIO
	 * @return
	 */
	int style() default SWT.CHECK;

	/**
	 * 返回唯一结果int值（即多个结果或值）；默认false，即返回List结果集
	 */
	boolean uniqueIntResult() default false;

}
