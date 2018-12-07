/* 
 * 项目:      AFC LC系统
 * 版本:      1.0
 * 日期:      2007-10-8 下午01:08:39
 * 作者：      姜旭锋
 * Email:    jiangxufeng@zdwxjd.com

 * 版权所有：  浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.task;

import java.util.TimerTask;

import org.eclipse.swt.widgets.Display;

public abstract class PeriodicalTask extends TimerTask {

	protected int interval = 10000;

	protected boolean enable = true;

	public void start() {
		Display.getCurrent().timerExec(0, this);
	}

	public void next() {
		if (enable) {
			Display.getCurrent().timerExec(interval, this);
		}
	}

	public abstract void execute();

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void run() {
		if (enable) {

			/**
			 * 调用用户制定逻辑
			 */

			execute();
			next();
		}
	}
}
