package com.insigma.commons.log;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 根据MDC进行过滤。<br>
 * 写日志的线程，第一步调用MDC.put(key, value)，最后一步调用MDC.remove(key)，中间输出的所有日志，都会参与到该过滤器。<br>
 */
public class MDCFilter extends AbstractLog4jFilter {

	@Override
	public int decide(LoggingEvent loggingEvent) {
		// 日志重要程度超过指定级别，直接返回，不根据线程名称过滤。
		if (levelPass != null && loggingEvent.getLevel().toInt() >= levelPass.toInt()) {
			return NEUTRAL;
		}
		// 白名单过滤
		if (whiteList != null) {
			for (String name : whiteList) {
				if (loggingEvent.getMDC(name) != null) {
					return Filter.ACCEPT;
				}
			}
		}

		// 黑名单过滤
		if (blackList != null) {
			for (String name : blackList) {
				if (loggingEvent.getMDC(name) != null) {
					return Filter.DENY;
				}
			}
		}

		// 默认方式
		return defaultAction;
	}

}
