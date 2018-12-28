/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 
 * Ticket: 日期时间控件和字符串间的转换
 * @author  hexingyue
 *
 */
public class DateTimeStrPicker extends EnhanceComposite implements IInputControl {

	protected TimeStrPicker timePicker;

	protected DateStrPicker datePicker;

	protected Date defaultDate = new Date();

	protected int listMode = TimePicker.HOUR | TimePicker.MINITE | TimePicker.SECOND;

	public DateTimeStrPicker(Composite parent, int style) {
		super(parent, style);
		this.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		createContants();

	}

	public DateTimeStrPicker(Composite parent, int style, int model) {
		super(parent, style);
		this.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		this.listMode = model;
		createContants();
	}

	private void createContants() {
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.horizontalSpacing = 0;
		gridLayout_1.numColumns = 3;
		setLayout(gridLayout_1);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 3;
		super.setLayout(gridLayout);

		datePicker = new DateStrPicker(this, SWT.NONE);
		datePicker.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		datePicker.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		final Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(10, SWT.DEFAULT));
		label.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		timePicker = new TimeStrPicker(this, SWT.NONE);
		timePicker.setLayoutData(new GridData());
		timePicker.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		setTime(defaultDate);
	}

	@SuppressWarnings("deprecation")
	public Date getTime() {
		Object objDate = datePicker.getObjectValue();
		Object objTime = timePicker.getObjectValue();
		Calendar startCalendar = Calendar.getInstance();
		Date date = DateTimeUtil.stringToDate(objDate.toString(), "yyyyMMdd");
		Date time = DateTimeUtil.stringToDate(objTime.toString(), "hh24miss");
		startCalendar.setTime(date);
		startCalendar.set(Calendar.HOUR_OF_DAY, time.getHours());
		startCalendar.set(Calendar.MINUTE, time.getMinutes());
		startCalendar.set(Calendar.SECOND, time.getSeconds());
		return startCalendar.getTime();
	}

	public void setTime(Date date) {
		datePicker.setObjectValue(date);
		timePicker.setObjectValue(date);
	}

	public void reset() {
		setTime(defaultDate);
	}

	public void addListener(int arg0, Listener arg1) {
		if (arg0 == SWT.FocusOut) {
			datePicker.addListener(arg0, arg1);
			timePicker.addListener(arg0, arg1);
		} else {
			super.addListener(arg0, arg1);
		}
	}

	public void setObjectValue(Object objectValue) {
		if (objectValue instanceof Date) {
			setTime((Date) objectValue);
		} else if (objectValue instanceof String) {
			setTime(DateTimeUtil.stringToDate(objectValue.toString(), "yyyyMMddhh24miss"));
		}
	}

	public Object getObjectValue() {
		return DateTimeUtil.formatDate(getTime(), "yyyyMMddHHmmss");
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
		datePicker.setDefaultDate(defaultDate);
		timePicker.setDefaultValue(defaultDate);
		reset();
	}
}
