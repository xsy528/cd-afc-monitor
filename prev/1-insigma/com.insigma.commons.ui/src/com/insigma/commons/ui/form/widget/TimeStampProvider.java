/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.DateTimePicker;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.util.lang.DateTimeUtil;

public class TimeStampProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		DateTimePicker control = new DateTimePicker(parent, SWT.NONE);
		control.setValueType(DateTimePicker.ValueType.TIMESTAMP_TYPE);
		if (value != null) {
			control.setObjectValue(value);
			control.setDefaultDate((Date) value);
		}
		if (view.nullvalue().equals("currentBeginDate")) {
			control.setObjectValue(DateTimeUtil.getCurrentBeginDate());
		}
		return control;
	}

	public String getName() {
		return "TimeStamp";
	}

}
