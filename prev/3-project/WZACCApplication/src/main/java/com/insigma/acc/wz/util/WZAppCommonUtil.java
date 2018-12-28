package com.insigma.acc.wz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.acc.wz.dialog.MainAppShell;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.IService;

public class WZAppCommonUtil {

	private static final Log logger = LogFactory.getLog(WZAppCommonUtil.class);


	public static IService getService(Class<? extends IService> clazz) {
		IService service = null;

		try {
			service = (IService) Application.getService(clazz);
		} catch (Exception e) {
			logger.error(" 获取" + clazz.getName() + "失败 : ", e);
		}

		return service;
	}

	/**
	 * 用于控制台红色打印输出，便于调试查看
	 *
	 * @param content ：打印字符串
	 */
	public static void redPrint(String content) {
		if (content == null) {
			logger.error("ERROR：传入字符串为空");
			return;
		}

		logger.info(content);
	}

	public static Object convertListToArray(List<?> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		if (list.get(0) instanceof Short) {
			Short[] rst = new Short[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Short) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof Integer) {
			Integer[] rst = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Integer) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof Long) {
			Long[] rst = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Long) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof String) {
			String[] rst = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (String) list.get(i);
			}

			return rst;
		}

		return null;
	}


}
