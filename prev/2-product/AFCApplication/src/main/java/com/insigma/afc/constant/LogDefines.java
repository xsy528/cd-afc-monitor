package com.insigma.afc.constant;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Color;

/**
 * 统一提供记录程序日志和系统日志的方法 Ticket:238
 *
 * @author 厉蒋
 */
public class LogDefines {
	/**
	 * logType:登陆日志
	 */
	public final static short LOGIN_LOG_TYPE = 0;

	/**
	 * logType:操作日志
	 */
	public final static short OPERATE_LOG_TYPE = 1;

	/**
	 * logType:退出日志
	 */
	public final static short LOGOUT_LOG_TYPE = 2;

	/**
	 * logType:启动日志
	 */
	public final static short LAUNCH_LOG_TYPE = 3;

	/**
	 * logType:日始日终日志
	 */
	public final static short DAILY_LOG_TYPE = 4;

	/**
	 * LOGTYPE:文件流水号审计
	 */
	public final static short FILE_AUDIT_TYPE = 5;

	/**
	 * logType:收益管理
	 */
	public final static short PROFIT_LOG_TYPE = 6;

	/**
	 * logType:数据库日志
	 */
	public final static short DB_LOG_TYPE = 7;

	public static final short NORMAL_LOG = 1;

	public static final short WARNING_LOG = 2;

	public static final short ERROR_LOG = 3;

	private static final long serialVersionUID = 1L;

	private final Log log;

	public static final String[] logClassName = { "正常", "警告", "错误" };

	public static final Short[] logClassValue = { NORMAL_LOG, WARNING_LOG, ERROR_LOG };

	public static final String[] logTypeStrings = { "登录日志", "操作日志", "退出日志", "启动日志", "日始日终日志", "文件流水号审计日志", "收益管理日志",
			"数据库日志" };

	public static final Short[] logTypeValue = { LOGIN_LOG_TYPE, OPERATE_LOG_TYPE, LOGOUT_LOG_TYPE, LAUNCH_LOG_TYPE,
			DAILY_LOG_TYPE, FILE_AUDIT_TYPE, PROFIT_LOG_TYPE, DB_LOG_TYPE };

	/**
	 * 日志构建器
	 *
	 * @param clazz
	 */
	public LogDefines(Class<?> clazz) {
		log = LogFactory.getLog(clazz);
	}

	public static String getLogTypeStr(short type) {
		if (type >= 0 && type < logTypeStrings.length) {
			return logTypeStrings[type];
		} else {
			return "未知";
		}
	}

	public static String getLogClassName(short logClass) {
		String logLev = "未知";
		if (logClass >= ERROR_LOG) {
			logLev = logClassName[2];
		} else if (logClass >= WARNING_LOG) {
			logLev = logClassName[1];
		} else if (logClass >= NORMAL_LOG) {
			logLev = logClassName[0];
		}
		return logLev;
	}

	public void dump(Object obj) {
		dump(obj.toString(), obj);
	}

	public void dump(String title, Object obj) {
		log.info(title);
		Field[] f = obj.getClass().getDeclaredFields();
		for (Field element : f) {
			String str = element.getName();
			Object o = null;
			try {
				element.setAccessible(true);
				o = element.get(obj);
			} catch (Exception e) {
				log.error("对象转化错误。", e);
			}
			log.debug(str + " = " + o);
		}
	}

	public static Color getLogStatusColor(String statusLevel) {
		if (statusLevel.equals(logClassName[0])) {
			return ColorConstants.COLOR_NORMAL;

		} else if (statusLevel.equals(logClassName[1])) {
			return ColorConstants.COLOR_WARN;
		} else if (statusLevel.equals(logClassName[2])) {
			return ColorConstants.COLOR_ERROR;
		} else {
			return ColorConstants.COLOR_NORMAL;
		}
	}

}
