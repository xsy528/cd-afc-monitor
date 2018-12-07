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

import com.insigma.commons.dic.Definition;

/**
 * 字典组件 注释 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dic {
	/**
	 * 字典组件名称,可选，默认是类名(全名)
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * 描述，可选
	 * 
	 * @return
	 */
	public String desc() default "";

	/**
	 * 字典类型 ，可选
	 * 
	 * @return
	 */
	public String type() default "insigma.dic.defaultType";

	/**
	 * 可以是自己
	 * 
	 * @return
	 */
	public Class<? extends Definition> overClass();
}
