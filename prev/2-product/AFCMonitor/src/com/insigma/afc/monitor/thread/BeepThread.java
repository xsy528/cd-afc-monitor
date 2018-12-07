/* 
 * 日期：2008-4-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.thread.EnhancedThread;

/**
 * beep线程
 * 
 * @author 廖红自
 */
public class BeepThread extends EnhancedThread {

	private static Log logger = LogFactory.getLog(BeepThread.class);

	private volatile boolean isBeep = true;

	private boolean odWarn = false;

	private int interval = 2;

	public BeepThread() {
		super.setName("Beep线程");
		super.setInterval(1000 * interval);
	}

	public void error(Exception e) {
		logger.error("Beep线程异常。", e);
	}

	public void execute() {
		if (isBeep && odWarn) {
			beep();
		}
	}

	/**
	 * 
	 */
	public void beep() {
		java.awt.Toolkit.getDefaultToolkit().beep();
		// jfq, todo
		// Kernel32Utils.Beep(700, 500);
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setBeep(boolean isBeep) {
		this.isBeep = isBeep;
		logger.debug(isBeep ? "启用客流告警声音" : "取消客流告警声音");
	}

	public boolean isBeep() {
		return isBeep;
	}

	public boolean isOdWarn() {
		return odWarn;
	}

	public void setOdWarn(boolean odWarn) {
		this.odWarn = odWarn;
	}
}
