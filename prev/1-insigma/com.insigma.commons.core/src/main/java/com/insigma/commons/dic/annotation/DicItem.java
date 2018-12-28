/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典项 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DicItem {
	/**
	 * 字典项名称，可选，默认是字段名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 字典项key，可选，默认是字段名称，在同一个字典组件内不允许重复
	 * 
	 * @return
	 */
	String key() default "";

	/**
	 * 分组，可选，默认是字段名称
	 * 
	 * @return
	 */
	String group() default "";

	/**
	 * 字典项引用，可选
	 * 
	 * @return
	 */
	String ref() default "";

	/**
	 * 索引号(用于排序用)
	 * 
	 * @return
	 */
	int index() default 0;

	/**
	 * 字典项引用和本字典项的关系，可选
	 * 
	 * @return
	 */
	String relation() default "";

	/**
	 * 描述，可选
	 * 
	 * @return
	 */
	public String desc() default "";
}
