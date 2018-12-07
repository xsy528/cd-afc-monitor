/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.ui.InitialThread;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public abstract class SystemInitor {
	protected final Log logger = LogFactory.getLog(getClass());

	public abstract String getName();

	public abstract boolean init();

	public boolean nextIfFail() {
		return true;
	}

	public void notifyView(String message) {
		InitialThread initThread = (InitialThread) Thread.currentThread();
		initThread.setMessage(message);
	}

	public boolean isDaemon() {
		return false;
	}
}
