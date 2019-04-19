package com.insigma.afc.monitor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * author: xuzhemin
 * 2018/9/25 11:23
 */
public class InsigmaPreparedStatementProxy extends InsigmaStatementProxy implements InvocationHandler {

	protected String sql;

	public InsigmaPreparedStatementProxy(PreparedStatement preparedStatement, String sql) {
		super(preparedStatement);
		this.sql = sql;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		List<String> needRecordMethods = Arrays.asList("executeQuery", "executeUpdate", "execute");
		Object result;
		if (needRecordMethods.contains(methodName)) {
			result = addMetric(method, args, sql);
		} else {
			result = method.invoke(statement, args);
		}
		return result;
	}

	public static PreparedStatement getProxy(PreparedStatement preparedStatement, String sql) {
		Class<?>[] interfaces = { PreparedStatement.class };
		return (PreparedStatement) Proxy.newProxyInstance(InsigmaPreparedStatementProxy.class.getClassLoader(),
				interfaces, new InsigmaPreparedStatementProxy(preparedStatement, sql));
	}
}
