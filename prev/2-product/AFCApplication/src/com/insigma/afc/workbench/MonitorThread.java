/* 
 * 日期：2010-2-27
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.workbench;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 监控 系统关闭线程,如果超时则强制执行关闭系统 Ticket:
 * 
 * @author fenghong
 */
public class MonitorThread extends Thread {

	private static Log logger = LogFactory.getLog(CommLaunch.class);

	private String componentName;

	Thread thread;

	public MonitorThread(long timeout, String componentName) {
		super("系统关闭监控线程");
		this.timeout = timeout;
		this.componentName = componentName;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	long timeout;

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - startTime >= timeout) {
				logger.error(componentName + "关闭时间超过" + timeout + "毫秒，执行强制关闭。", null);
				thread.stop();
				System.exit(0);
			} else {
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
