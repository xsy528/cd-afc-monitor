package com.insigma.commons.spring.aop;

import java.sql.SQLException;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
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
public class TransactionInterceptor implements MethodInterceptor {

	private static Log log = LogFactory.getLog(TransactionInterceptor.class);

	/**
	 * 可以用作权限管理//TODO
	 * 
	 * @param l
	 * @param i
	 * @return
	 */
	public boolean findPower(List<?> l, Long i) {
		return false;
	}

	public static boolean dbConnected = true;

	public static boolean isDbConnected() {
		return dbConnected;
	}

	public static void setDbConnected(boolean dbConnected) {
		TransactionInterceptor.dbConnected = dbConnected;
	}

	private static IDBStatus dbStatus;

	/**
	 * 拦截以Service结尾的方法的所有类的方法，捕捉所有异常重新抛ApplicationException
	 * <p>
	 * 分三类处理，一类：数据库链接异常
	 * <p>
	 * 一类：数据库操作异常
	 * <p>
	 * 一类：业务层面的异常
	 */
	public Object invoke(MethodInvocation invo) throws Throwable {
		Object obj = null;
		try {
			obj = invo.proceed();
			if (dbStatus != null) {
				dbStatus.setDBStatus("数据库连接：正常", true);
				dbConnected = true;
			}
		} catch (Exception ex) {
			Throwable t = ex;
			while (t != null) {
				log.error("AOP Catch 异常 class .............>>>>>>>> ：" + t.getClass());
				processEx(t);
				t = t.getCause();
			}
		}
		return obj;
	}

	private void processEx(Throwable ex) {
		if (ex.getClass() == CannotCreateTransactionException.class || ex.getClass() == TransactionSystemException.class
				|| ex.getClass() == SQLException.class) {
			if (dbStatus != null) {
				dbStatus.setDBStatus("数据库连接：异常", false);
				dbConnected = false;
			}
			throw new DBConnectException("数据库链接异常，请检查数据库链接，" + "\n" + "       检查链接正常后再重试。 ", ex);
		} else {
			throw new ApplicationException("数据库操作异常。", ex);
		}
	}

	public static IDBStatus getDbStatus() {
		return dbStatus;
	}

	public static void setDbStatus(IDBStatus dbStatus) {
		TransactionInterceptor.dbStatus = dbStatus;
	}

}
