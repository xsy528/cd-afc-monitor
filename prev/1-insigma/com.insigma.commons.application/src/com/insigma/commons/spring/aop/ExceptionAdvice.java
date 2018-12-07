/*
 * 日期：May 8, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.spring.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import com.insigma.commons.database.DBConnectException;
import com.insigma.commons.exception.ApplicationException;

/**
 * 网络中断、数据库异常应用程序捕捉异常 #1331
 * 
 * @author Dingluofeng
 */
public class ExceptionAdvice {

	private static Log log = LogFactory.getLog(ExceptionAdvice.class);

	/**
	 * 捕捉service包以及子包下的所有方法抛的异常
	 * <p>
	 * 分三类处理，一类：数据库链接异常
	 * <p>
	 * 一类：数据库操作异常
	 * <p>
	 * 一类：业务层面的异常
	 * 
	 * @param ex
	 * @throws Throwable
	 */
	public void catchException(Exception ex) throws Throwable {
		log.error(ex.getMessage(), ex);
		ex.printStackTrace();
		if (ex.getClass() == ApplicationException.class) {
			throw ex;
		} else if (ex.getClass() == CannotCreateTransactionException.class
				|| ex.getClass() == TransactionSystemException.class) {
			throw new DBConnectException("数据库链接异常，请检查数据库链接，" + "\n" + "       检查链接正常后再重试。 ", ex);
		} else {
			throw new ApplicationException("数据库操作异常。", ex);
		}

	}
}
