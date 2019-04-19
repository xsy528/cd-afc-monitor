package com.insigma.afc.monitor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * author: xuzhemin
 * 2018/9/25 11:23
 */
public class InsigmaConnectorProxy implements InvocationHandler {

	private Connection connection;

	public InsigmaConnectorProxy(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		switch (methodName) {
		case "createStatement": {
			return InsigmaStatementProxy.getProxy((Statement) method.invoke(connection, args));
		}
		case "prepareStatement": {
			return InsigmaPreparedStatementProxy.getProxy((PreparedStatement) method.invoke(connection, args),
					args[0].toString());
		}
		case "prepareCall": {
			return InsigmaCallableStatementProxy.getProxy((CallableStatement) method.invoke(connection, args),
					args[0].toString());
		}
		default: {
			return method.invoke(connection, args);
		}
		}
	}

	public static Connection getProxy(Connection connection) {
		Class<?>[] interfaces = { Connection.class };
		return (Connection) Proxy.newProxyInstance(InsigmaConnectorProxy.class.getClassLoader(), interfaces,
				new InsigmaConnectorProxy(connection));
	}
}
