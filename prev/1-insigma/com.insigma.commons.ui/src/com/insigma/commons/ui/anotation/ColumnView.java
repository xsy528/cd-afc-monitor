/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.swt.SWT;

import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.convert.ConvertorAdapter;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnView {

	String name();

	int width() default 100;

	int alignment() default SWT.LEFT;

	/**
	 * 排序
	 * @return
	 */
	boolean sortAble() default true;

	/**
	 * @return
	 */
	String nullvalue() default "";

	String el() default "";

	Class<? extends ColumnConvertor> convertor() default ConvertorAdapter.class;
}