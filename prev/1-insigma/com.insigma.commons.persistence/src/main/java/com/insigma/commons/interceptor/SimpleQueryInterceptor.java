package com.insigma.commons.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.BaseHibernateDao;
import com.insigma.commons.op.OPException;

/**
 * 
 * This method interceptor will cut the point upon all the method with @HQL
 * annotation, execute the hql string automatically and return the value
 * 
 * @author hsy541
 * 
 */
public class SimpleQueryInterceptor extends BaseHibernateDao implements MethodInterceptor {

	public static Log logger = LogFactory.getLog(SimpleQueryInterceptor.class);

	@SuppressWarnings("unchecked")
	public Object invoke(MethodInvocation invo) throws Throwable {
		logger.debug(
				"try to find @HQL annotation from the interface declaration if this method belongs to an interface");
		HQL hql = invo.getMethod().getAnnotation(HQL.class);
		Method m = invo.getMethod();
		if (hql == null) {
			logger.debug(
					"find @HQL annotation from the implementation declaration if @HQL is not found in the interface");
			m = invo.getMethod();
			hql = invo.getThis().getClass().getMethod(m.getName(), m.getParameterTypes()).getAnnotation(HQL.class);
		}
		if (hql != null) {
			logger.debug("find @HQL annotation upon the method and hql " + hql + " will be automatically executed");
			String hqlString = hql.value();
			List list;
			try {
				list = getEntityListHQL(hqlString, invo.getArguments());
			} catch (OPException e) {
				throw new ApplicationException(hql.eMsg(), e);
			}

			if (List.class.isAssignableFrom(invo.getMethod().getReturnType())) {
				return list;
			} else {
				if (!list.isEmpty()) {
					return list.get(0);
				} else {
					return null;
				}
			}
		} else {
			logger.debug("@HQL is not found in both interface and concrete class");
			return invo.proceed();
		}
	}
}
