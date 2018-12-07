/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class DatePickListener implements Listener {

	private Display display;

	private Text text;

	protected Date date;

	public DatePickListener(Display display, Text text) {
		this.display = display;
		this.text = text;
	}

	public void handleEvent(Event event) {

		if (event.widget instanceof Button) {

			final SWTCalendarDialog cal = new SWTCalendarDialog(display);

			final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			if (text.getText() != null && text.getText().length() > 0) {
				try {
					Date d = formatter.parse(text.getText());
					cal.setDate(d);
				} catch (ParseException pe) {
				}
			}

			if (date != null) {
				cal.setDate(date);
			}

			Point pos = text.toDisplay(0, 0);
			pos.y = pos.y + text.getBounds().height;
			pos.y = pos.y - 2;
			pos.x = pos.x - 2;
			cal.setLocation(pos);
			cal.open();
		}
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
