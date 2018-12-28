/* 
 * 项目:      AFC通信组件
 * 代码文件:   ThreadManager.java
 * 版本: 1.0
 * 日期: 2007-11-14 上午09:04:48
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.thread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jiangxf
 */
public class ThreadManager extends java.lang.Thread {

	public static ThreadManager threadManager = new ThreadManager();

	private static Log logger = LogFactory.getLog(ThreadManager.class);

	public synchronized static ThreadManager getInstance() {
		if (threadManager.getState() == State.NEW) {
			threadManager.start();
		}
		return threadManager;
	}

	protected ArrayList<EnhancedThread> threadPool;

	private ThreadManager() {
		threadPool = new ArrayList<EnhancedThread>();
		this.setDaemon(true);
	}

	public void addThread(EnhancedThread thread) {
		synchronized (threadPool) {
			threadPool.add(thread);
		}

	}

	public void removeThread(EnhancedThread thread) {
		synchronized (threadPool) {
			threadPool.remove(thread);
		}
	}

	@SuppressWarnings("unchecked")
	public EnhancedThread getThread(Class cls) {
		return null;
	}

	/**
	 * 关闭所有线程
	 */
	public void stopAll() {
		synchronized (threadPool) {
			for (Object iterable_element : threadPool.toArray()) {
				EnhancedThread thread = (EnhancedThread) iterable_element;
				if (!thread.isImmune()) {
					logger.debug("尝试关闭 " + thread.getName() + " 线程。");
					thread.stopThread();
					logger.debug("线程:" + thread.getName() + "关闭成功。");
				}
			}
			;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void run() {
		long previousS = 0;
		while (true) {
			try {
				long currentTime = System.currentTimeMillis();
				long currentSS = System.currentTimeMillis() % (24 * 60 * 60 * 1000)
						+ (23 - Calendar.ZONE_OFFSET) * 60 * 60 * 1000;

				synchronized (threadPool) {

					Iterator<EnhancedThread> iterator = threadPool.iterator();
					while (iterator.hasNext()) {
						// for (EnhancedThread thread : threadPool) {
						EnhancedThread thread = iterator.next();
						if (thread.getTimers() != null) {
							List<Date> timers = thread.getTimers();
							for (Date date : timers) {
								// 判断改进
								if ((date.getTime() >= currentTime - 1000) && date.getTime() < currentTime) {
									if (thread.getState() == State.NEW) {
										thread.startThread();
									} else {
										thread.wake();
									}
								}
							}
						}

						if (thread.getTimePoint() != null) {
							List<Long> timepoints = thread.getTimePoint();

							// long currentS = date.getHours() * 60 * 60 * 1000
							// + date.getMinutes()
							// * 60 * 1000 + date.getSeconds() * 1000;

							for (Long timepoint : timepoints) {
								// 判断改进
								if ((timepoint >= previousS) && timepoint < currentSS && previousS != 0) {

									if (thread.getState() == State.NEW) {
										thread.startThread();
									} else {
										thread.wake();
									}
								}

							}

						}
						if (thread.getTimeout() > 0 && thread.getStartTime() != 0
								&& currentTime > thread.getStartTime() + thread.getTimeout()) {
							thread.stop();
							thread.timeout();
							// threadPool.remove(thread);
							iterator.remove();
						}
					}

				}
				previousS = currentSS;
				long now = System.currentTimeMillis();
				if (now - currentTime > 0 && now - currentTime < 1000) {
					sleep(1000 - (now - currentTime));
				} else {
					sleep(1000);
				}
			} catch (Exception e) {
				logger.error("ThreadManager 异常。", e);
			}
		}
	}
}
