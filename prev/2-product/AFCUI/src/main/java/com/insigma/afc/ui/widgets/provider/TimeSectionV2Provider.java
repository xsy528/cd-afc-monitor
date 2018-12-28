/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets.provider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.ui.widgets.TimeSectionSelectionV2;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.widgets.IInputControl;

public class TimeSectionV2Provider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		TimeSectionSelectionV2 control = new TimeSectionSelectionV2(parent, SWT.NONE);
		if (!view.format().equals(""))
			control.setTimePeriod(Integer.parseInt(view.format().substring(0, 1)));
		if (view.format().length() == 3) {
			control.setTimePeriodCount(Integer.parseInt(view.format().substring(2, 3)));
			control.setObjectValue(control.getCurrentTimePeriod(control.getTimePeriod(), control.getTimePeriodCount()));
			control.setMaxTimePeriod(control.getTimePeriod() * control.getTimePeriodCount());
		}
		if (value != null) {
			control.setObjectValue(value);
		}
		return control;
	}

	public String getName() {
		return "TimeSectionV2";
	}

}
