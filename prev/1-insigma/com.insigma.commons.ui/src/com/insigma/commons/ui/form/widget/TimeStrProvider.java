/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.TimeStrPicker;

/**
 * 
 * Ticket: 时间控件和字符串间的转换
 * @author  hexingyue
 *
 */
public class TimeStrProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;

		TimeStrPicker control = new TimeStrPicker(parent, SWT.NONE);
		if (null != value) {
			control.setObjectValue(value);
		}
		return control;

	}

	public String getName() {
		return "TimeStr";
	}

}
