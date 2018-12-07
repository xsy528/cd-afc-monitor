/* 
 * 日期：2010-9-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.insigma.commons.query.QueryFilter.ConditionType;

/**
 * Ticket: <br/>
 * 条件
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
	/**
	 * 需要查询的字段，如果没有设置，则采用属性名
	 * 
	 * @return
	 */
	String column() default "";

	/**
	 * 逻辑比较类型，and，or
	 * 
	 * @return
	 */
	ConditionType type() default ConditionType.AND;

	/**
	 * 比较类型,默认=，可以是<,>,<>,like,in
	 * 
	 * @return
	 */
	String compareType() default "=";
}
