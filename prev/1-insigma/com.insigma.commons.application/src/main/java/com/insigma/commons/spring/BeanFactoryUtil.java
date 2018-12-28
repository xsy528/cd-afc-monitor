package com.insigma.commons.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.insigma.commons.exception.ApplicationException;

/**
 * Title: Description: Copyright: Copyright (c) 2004-9-16 Company: DOIT
 * 
 * @author jensol
 * @version 1.0
 */

public class BeanFactoryUtil {

	private static ApplicationContext s_applicationContext;

	private static String[] contextStrings = new String[] { "spring-config.xml" };

	/**
	 * 初始bean
	 * 
	 * @param strs
	 *            configLocations eg：spring-config.xml(默认)
	 */
	public static void initBean(String... strs) {
		try {
			if (strs == null || strs.length == 0) {
				contextStrings = new String[] { "spring-config.xml" };
			} else {
				contextStrings = strs;
			}
			s_applicationContext = new ClassPathXmlApplicationContext(contextStrings);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static Object getBean(String beanName) throws ApplicationException {
		try {
			return s_applicationContext.getBean(beanName);
		} catch (RuntimeException ex) {
			throw new ApplicationException("bean:" + beanName + " 在配置文件spring-config中不存在。\n", ex);
		}
	}

	/**
	 * 设置ApplicationContext
	 * 
	 * @param context
	 *            the s_applicationContext to set
	 */
	public static void setApplicationContext(ApplicationContext context) {
		s_applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return s_applicationContext;
	}

}
