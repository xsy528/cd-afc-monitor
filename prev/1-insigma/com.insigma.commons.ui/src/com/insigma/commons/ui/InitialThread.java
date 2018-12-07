/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui;

import org.eclipse.swt.widgets.Display;

public abstract class InitialThread extends Thread {

	protected int process;

	protected String message = "系统正在初始化...";

	protected IStatusChangeListener statusChangeListener;

	protected RefreshUI refresh = new RefreshUI();

	class RefreshUI implements Runnable {
		public void run() {

			statusChangeListener.StatusChanged();
		}
	}

	public int getProgress() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
		if (statusChangeListener != null) {
			Display.getDefault().asyncExec(refresh);
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		if (statusChangeListener != null) {
			Display.getDefault().asyncExec(refresh);
		}
	}

	public IStatusChangeListener getStatusChangeListener() {
		return statusChangeListener;
	}

	public void setStatusChangeListener(IStatusChangeListener statusChangeListener) {
		this.statusChangeListener = statusChangeListener;
	}

}
