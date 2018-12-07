package com.insigma.commons.spring.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

import com.insigma.commons.exception.ApplicationException;

/**
 * 网络中断、数据库异常应用程序捕捉异常 #1331
 * 
 * @author Dingluofeng
 * 
 */
public class ThrowAdvice implements ThrowsAdvice {

	private static Log log = LogFactory.getLog(ThrowAdvice.class);

	/**
	 * 捕捉commit以及getTransaction方法所有抛的异常
	 * <p>
	 * 分两类处理，一类：数据库链接异常
	 * <p>
	 * 一类：数据库操作异常
	 * 
	 * @param m
	 *            抛异常方法
	 * @param args
	 *            该方法参数
	 * @param target
	 *            目标调用
	 * @param subclass
	 *            异常
	 */
	public void afterThrowing(Method m, Object[] args, Object target, Throwable subclass) {
		log.info(target.getClass().getName() + "正在进行日志登记： that a " + subclass + " Exception was throw in "
				+ m.getName());

		if (m.getName().equals("getTransaction")) {
			throw new ApplicationException("数据库操作异常。", subclass);
		}
		if (m.getName().equals("commit")) {
			throw new ApplicationException("数据库操作异常。", subclass);
		}

	}

}
