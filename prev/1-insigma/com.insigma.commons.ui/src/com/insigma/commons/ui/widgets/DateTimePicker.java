/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class DateTimePicker extends EnhanceComposite implements IInputControl {

	public static enum ValueType {
		DATE_TYPE, TIMESTAMP_TYPE
	}

	protected TimePicker timePicker;

	protected DatePicker datePicker;

	protected Date defaultDate = new Date();

	protected int listMode = TimePicker.HOUR | TimePicker.MINITE | TimePicker.SECOND;

	private ValueType valueType = ValueType.DATE_TYPE;

	public DateTimePicker(Composite parent, int style) {
		super(parent, style);
		createContants();
	}

	public DateTimePicker(Composite parent, int style, int model) {
		super(parent, style);
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

		datePicker = new DatePicker(this, SWT.NONE);
		datePicker.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(10, SWT.DEFAULT));

		timePicker = new TimePicker(this, SWT.NONE);
		timePicker.setLayoutData(new GridData());
		setTime(defaultDate);
	}

	@SuppressWarnings("deprecation")
	public Date getTime() {
		Date date = (Date) datePicker.getObjectValue();
		Date time = (Date) timePicker.getObjectValue();

		Calendar startCalendar = Calendar.getInstance();
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
		} else if (objectValue instanceof Timestamp) {
			Timestamp t = (Timestamp) objectValue;
			setTime(new Date(t.getTime()));
		}
	}

	public Object getObjectValue() {
		Date date = (Date) datePicker.getObjectValue();
		Date time = (Date) timePicker.getObjectValue();

		if (ValueType.TIMESTAMP_TYPE == valueType) {
			return new Timestamp(date.getYear(), date.getMonth(), date.getDay(), time.getHours(), time.getMinutes(),
					time.getSeconds(), 0);
		} else {

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(date);
			startCalendar.set(Calendar.HOUR_OF_DAY, time.getHours());
			startCalendar.set(Calendar.MINUTE, time.getMinutes());
			startCalendar.set(Calendar.SECOND, time.getSeconds());
			return startCalendar.getTime();
		}
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
		datePicker.setDefaultDate(defaultDate);
		timePicker.setDefaultValue(defaultDate);
		reset();
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

}
