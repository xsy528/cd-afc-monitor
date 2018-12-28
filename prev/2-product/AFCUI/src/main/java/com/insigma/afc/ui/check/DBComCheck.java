/* 
 * 日期：2011-10-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.ui.check;

import org.eclipse.swt.SWT;

import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.ui.status.DatabaseCheckThread;
import com.insigma.commons.application.Application;
import com.insigma.commons.ui.MessageForm;

/**
 * Ticket: 数据库、通信检测类
 * 
 * @author Zhou-Xiaowei
 */
public class DBComCheck {

	private final String DB_INFO = "数据库连接中断。";

	private final String COM_INFO = "通讯连接中断。";

	private final String BOTH_INFO = "通讯、数据库连接中断。";

	private boolean checkDBExist() {
		Object data = Application.getData(ApplicationKey.STATUS_DATABASE);

		Integer value = getData(data);

		if (value != null && value == DatabaseCheckThread.NORMAL) {
			return true;
		}

		return false;
	}

	private boolean checkComExist() {
		Object data = Application.getData(ApplicationKey.STATUS_COMMUNICATION);

		Integer value = getData(data);

		//if (value != null && value == WorkbenchPingServerThread.ON_LINE) {
		//	return true;
		//}

		return false;
	}

	public boolean check() {
		if (!checkDBExist() && !checkComExist()) {
			MessageForm.Message("错误信息", BOTH_INFO, SWT.ICON_ERROR);
			return false;
		} else if (!checkComExist()) {
			MessageForm.Message("错误信息", COM_INFO, SWT.ICON_ERROR);
			return false;
		} else if (!checkDBExist()) {
			MessageForm.Message("错误信息", DB_INFO, SWT.ICON_ERROR);
			return false;
		}

		return true;
	}

	public boolean checkDB() {
		if (!checkDBExist()) {
			MessageForm.Message("错误信息", DB_INFO, SWT.ICON_ERROR);
			return false;
		}

		return true;
	}

	public boolean checkCom() {
		if (!checkComExist()) {
			MessageForm.Message("错误信息", COM_INFO, SWT.ICON_ERROR);
			return false;
		}

		return true;
	}

	private Integer getData(Object data) {
		if (data != null) {
			if (data instanceof Integer) {
				return (Integer) data;
			} else if (data instanceof Long) {
				return ((Long) data).intValue();
			} else if (data instanceof Short) {
				return ((Short) data).intValue();
			}
		}

		return null;
	}
}
