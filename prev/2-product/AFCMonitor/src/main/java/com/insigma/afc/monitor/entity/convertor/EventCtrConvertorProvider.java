package com.insigma.afc.monitor.entity.convertor;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;

public class EventCtrConvertorProvider {

	private static final short NORMAL = 0;

	private static final short WARN = 1;

	private static final short ERROR = 2;

	public static String getEventStatusWord(short statusLevel) {
		switch (statusLevel) {
		case NORMAL: {
			return "正常";
		}
		case WARN: {
			return "警告";
		}
		case ERROR: {
			return "报警";
		}
		default:
			return "报警";
		}
	}

	public static Color getEventStatusColor(short statusLevel) {
		switch (statusLevel) {
		case NORMAL: {
			return ColorConstants.COLOR_NORMAL;
		}
		case WARN: {
			return ColorConstants.COLOR_WARN;
		}
		case ERROR: {
			return ColorConstants.COLOR_ERROR;
		}
		default:
			return ColorConstants.COLOR_ERROR;
		}
	}

}
