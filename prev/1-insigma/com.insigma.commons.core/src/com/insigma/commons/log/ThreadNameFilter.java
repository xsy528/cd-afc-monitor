package com.insigma.commons.log;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 根据线程名字过滤
 */
public class ThreadNameFilter extends AbstractLog4jFilter {

	@Override
	public int decide(LoggingEvent loggingEvent) {
		// 日志重要程度超过指定级别，直接返回，不根据线程名称过滤。
		if (levelPass != null && loggingEvent.getLevel().toInt() >= levelPass.toInt()) {
			return NEUTRAL;
		}

		String threadName = loggingEvent.getThreadName();
		// 白名单过滤
		if (whiteList != null) {
			for (String name : whiteList) {
				if (threadName.indexOf(name) != -1) {
					return Filter.ACCEPT;
				}
			}
		}

		// 黑名单过滤
		if (blackList != null) {
			for (String name : blackList) {
				if (threadName.indexOf(name) != -1) {
					return Filter.DENY;
				}
			}
		}

		// 默认方式
		return defaultAction;
	}
}
