package com.insigma.afc.monitor.aspect;

import java.sql.Connection;

import com.insigma.afc.monitor.proxy.InsigmaConnectorProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * author: xuzhemin
 * 2018/10/18 17:14
 * 通过代理数据源来记录sql执行情况
 */
@Aspect
@Component
public class DataSourceAspect {

	@Pointcut("bean(dataSource)")
	public void pointcut() {

	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String name = joinPoint.getSignature().getName();
		if (name.equals("getConnection")) {
			return InsigmaConnectorProxy.getProxy((Connection) joinPoint.proceed());
		}
		return joinPoint.proceed();
	}
}
