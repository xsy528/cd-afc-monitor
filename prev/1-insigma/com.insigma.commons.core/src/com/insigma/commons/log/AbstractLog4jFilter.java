package com.insigma.commons.log;

import org.apache.log4j.Level;
import org.apache.log4j.spi.Filter;

/**
 * User: Jiang Fuqiang
 * Date: 2015/12/2
 */
public abstract class AbstractLog4jFilter extends Filter {
	/**
	 * 级别在此之上的（包含），不参与过滤计算。
	 */
	protected Level levelPass;

	/**
	 * 白名单：允许输出日志的线程名称(实际上是采用indexof判断的）。
	 */
	protected String[] whiteList;
	/**
	 * 黑名单：禁止输出日志的线程名称(实际上是采用indexof判断的）。。
	 */
	protected String[] blackList;

	/**
	 * 既没有出现在白名单，也没有出现在和名单中的日志，处理方式。
	 */
	protected int defaultAction = Filter.NEUTRAL;

	public String getDefault() {
		if (defaultAction == ACCEPT) {
			return "ACCEPT";
		} else if (defaultAction == DENY) {
			return "DENY";
		} else {
			return "NEUTRAL";
		}
	}

	public void setDefault(String action) {
		if ("ACCEPT".equalsIgnoreCase(action)) {
			defaultAction = ACCEPT;
		} else if ("DENY".equalsIgnoreCase(action)) {
			defaultAction = DENY;
		} else if ("NEUTRAL".equalsIgnoreCase(action)) {
			defaultAction = NEUTRAL;
		} else {
			// fail silently
		}
	}

	public String getAccept() {
		return arrayToString(whiteList);
	}

	public void setAccept(String text) {
		whiteList = stringToArray(text);
	}

	public String getDeny() {
		return arrayToString(blackList);
	}

	public void setDeny(String text) {
		blackList = stringToArray(text);
	}

	public Level getLevelPass() {
		return this.levelPass;
	}

	public void setLevelPass(Level levelMax) {
		this.levelPass = levelMax;
	}

	private static String[] stringToArray(String text) {
		return text.split(",");
	}

	private static String arrayToString(String[] arrays) {
		if (arrays == null || arrays.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrays.length; i++) {
			sb.append(arrays[i]);
			if (i != arrays.length) {
				sb.append(",");
			}
		}
		return sb.toString();

	}
}
