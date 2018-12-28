/* 
 * 项目:      AFC通信组件
 * 代码文件:   Thread.java
 * 版本: 1.0
 * 日期: 2007-11-14 上午09:02:55
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jiangxf
 */
public class EnhancedThread extends java.lang.Thread {

	private static Log logger = LogFactory.getLog(EnhancedThread.class);

	protected boolean canStop = true;

	protected boolean immune = false;

	protected long timeout;

	protected long startTime;

	protected long interval;

	protected Runnable runnable;

	protected List<Date> timers;

	protected List<Long> timePoints;

	protected final Object waitObject = new Object();

	protected final List<EnhancedThread> priorFinishedJobs = new LinkedList<EnhancedThread>();

	public void addPriorFinishedJob(EnhancedThread enhancedThread) {
		priorFinishedJobs.add(enhancedThread);
	}

	public List<EnhancedThread> getPriorFinishedJobs() {
		return priorFinishedJobs;
	}

	public EnhancedThread(String name, Runnable runnable) {
		super(name);
		this.runnable = runnable;
		ThreadManager.getInstance().addThread(this);
	}

	public EnhancedThread() {
		this("匿名线程", null);
	}

	public EnhancedThread(String name) {
		this(name, null);
	}

	public EnhancedThread(Runnable runnable) {
		this("匿名线程", runnable);
	}

	@Override
	public synchronized void start() {
		if (this.getState() != State.NEW) {
			return;
		}
		if (this.getTimers() != null || this.getTimePoint() != null) {
			return;
		}
		logger.info(String.format("线程 [%s] 启动成功", getName()));
		super.start();
	}

	public synchronized void startThread() {
		super.start();
	}

	@Override
	public final void run() {

		while (true) {

			startTime = System.currentTimeMillis();

			try {
				if (runnable == null) {
					execute();
				} else {
					runnable.run();
				}
			} catch (Exception e) {
				error(e);
				Throwable cause = e;
				while (cause != null) {
					if (cause instanceof ThreadDeath) {
						logger.info("ThreadDeath is caught. Exits.");
						return;
					}
					cause = cause.getCause();
				}
			}

			long currentTime = System.currentTimeMillis();

			if (interval != 0) {
				// 多次运行
				long time = currentTime - startTime;

				long waittime = 0;
				if (time <= 0) {
					waittime = interval;
				} else if (time > 0 && time < interval) {
					waittime = interval - time;
				} else if (time >= interval) {
					waittime = 0;
				}

				try {
					if (waittime > 0 && waittime <= interval) {
						synchronized (waitObject) {
							waitObject.wait(waittime);
						}
					}
				} catch (InterruptedException e) {
					error(e);
				}

			} else {
				// 单次运行
				finish();
				ThreadManager.getInstance().removeThread(this);
				break;
			}
		}

	}

	public void wake() {
		synchronized (waitObject) {
			waitObject.notify();
		}
	}

	public void error(Exception e) {
	}

	public void execute() {
	}

	public void finish() {

	}

	// 用户自定义超时处理函数
	public void timeout() {
	}

	// 停止线程
	@SuppressWarnings("deprecation")
	public void stopThread() {

		if (getState() == State.TERMINATED) {
			return;
		}

		for (EnhancedThread thread : getPriorFinishedJobs()) {
			thread.stopThread();
		}

		while (!this.canStop) {
			if (getState() == State.TIMED_WAITING || getState() == State.WAITING) {
				this.canStop = true;
			} else {
				logger.debug("线程 " + this.getName() + " 不允许关闭，等待100ms");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		ThreadManager.getInstance().removeThread(this);

		stop();

	}

	public Long getTimeout() {
		return this.timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isCanStop() {
		return this.canStop;
	}

	public void setCanStop(boolean canStop) {
		this.canStop = canStop;
	}

	public long getInterval() {
		return this.interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public boolean isImmune() {
		return immune;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public List<Date> getTimers() {
		return timers;
	}

	public void setTimers(List<Date> timers) {
		this.timers = timers;
	}

	public void addTimer(Date timer) {
		if (timers == null) {
			timers = new ArrayList<Date>();
		}
		timers.add(timer);
	}

	public List<Long> getTimePoint() {
		return timePoints;
	}

	public void setTimePoint(List<Long> timePoint) {
		this.timePoints = timePoint;
	}

	public void addTimePoint(Long timePoint) {
		if (timePoints == null) {
			timePoints = new ArrayList<Long>();
		}
		timePoints.add(timePoint);
	}

	public void clearTimePoint() {
		if (timePoints != null) {
			timePoints.clear();
		}
	}
}
