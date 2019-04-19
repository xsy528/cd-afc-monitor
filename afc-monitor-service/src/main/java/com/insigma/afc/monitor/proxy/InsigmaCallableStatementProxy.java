package com.insigma.afc.monitor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;

/**
 * author: xuzhemin
 * 2018/9/25 11:23
 */
public class InsigmaCallableStatementProxy extends InsigmaPreparedStatementProxy implements InvocationHandler {

	public InsigmaCallableStatementProxy(CallableStatement callableStatement, String sql) {
		super(callableStatement, sql);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return super.invoke(proxy, method, args);
	}

	public static CallableStatement getProxy(CallableStatement callableStatement, String sql) {
		Class<?>[] interfaces = { CallableStatement.class };
		return (CallableStatement) Proxy.newProxyInstance(InsigmaCallableStatementProxy.class.getClassLoader(),
				interfaces, new InsigmaCallableStatementProxy(callableStatement, sql));
	}
}
