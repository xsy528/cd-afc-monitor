package com.insigma.afc.monitor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: xuzhemin
 * 2018/9/25 11:23
 */
public class InsigmaStatementProxy implements InvocationHandler {

	private static final Logger logger = LoggerFactory.getLogger(InsigmaStatementProxy.class);

	protected Statement statement;

	public InsigmaStatementProxy(Statement statement) {
		this.statement = statement;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		List<String> needRecordMethods = Arrays.asList("executeQuery", "executeUpdate", "execute");
		Object result;
		if (needRecordMethods.contains(methodName)) {
			String sql = args[0].toString();
			result = addMetric(method, args, sql);
		} else {
			result = method.invoke(statement, args);
		}
		return result;
	}

	protected Object addMetric(Method method, Object[] args, String sql) throws Throwable {
		Object result;
		long start = System.currentTimeMillis();
		long cost;
		try {
			result = method.invoke(statement, args);
			cost = System.currentTimeMillis() - start;
			logger.info("sql:{},cost:{},error:{}", sql, cost, false);
		} catch (Throwable e) {
			cost = System.currentTimeMillis() - start;
			logger.info("sql:{},cost:{},error:{}", sql, cost, true);
			throw e;
		}
		return result;
	}

	public static Statement getProxy(Statement statement) {
		Class<?>[] interfaces = { Statement.class };
		return (Statement) Proxy.newProxyInstance(InsigmaStatementProxy.class.getClassLoader(), interfaces,
				new InsigmaStatementProxy(statement));
	}
}
