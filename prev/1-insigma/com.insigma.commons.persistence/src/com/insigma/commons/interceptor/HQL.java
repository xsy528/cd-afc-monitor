package com.insigma.commons.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * @author hsy541
 * 
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HQL {

	/**
	 * 需要执行的hql语句
	 * 
	 * @return
	 */
	String value();

	/**
	 * 数据库错误时返回的错误String
	 * 
	 * @return
	 */
	String eMsg() default "获取数据库数据失败";

}
