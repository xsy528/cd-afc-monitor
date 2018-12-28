package com.insigma.commons.op;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class OPUtil {
	public static OPException handleException(Exception e) {
		if (e instanceof JDBCException)
			return new OPException(((JDBCException) e).getSQLException());
		if (e instanceof HibernateException)
			return new OPException((HibernateException) e);
		else
			return new OPException(e);
	}
}
