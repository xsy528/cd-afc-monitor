/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.task.PeriodicalTask;
import com.insigma.commons.ui.widgets.Label;
import com.insigma.commons.util.lang.DateTimeUtil;

public class DateLabel extends Label {

	private TimeClock timeClock = new TimeClock();

	class TimeClock extends PeriodicalTask {
		public void execute() {
			if (!isDisposed()) {
				setText(DateTimeUtil.getChineseDate(new java.util.Date()) + " " + DateTimeUtil.getCurrentWeekDay());
			}
		}
	}

	public DateLabel(Composite arg0, int arg1) {
		super(arg0, arg1);
		start();
	}

	public void start() {
		timeClock.setInterval(1000);
		timeClock.start();
	}

	protected void checkSubclass() {
	}
}
