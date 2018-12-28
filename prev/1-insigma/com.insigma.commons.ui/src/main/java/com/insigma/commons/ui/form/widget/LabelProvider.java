/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.format.TextFormater;
import com.insigma.commons.ui.widgets.IInputControl;

public class LabelProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		ShowLabel text = null;
		if (view.wrap()) {
			text = new ShowLabel(parent, SWT.BORDER | SWT.WRAP);
		} else if (view.style() != SWT.DEFAULT) {
			text = new ShowLabel(parent, view.style());
		} else {
			text = new ShowLabel(parent, SWT.BORDER);
		}
		String showText = view.nullvalue();
		String format = view.format();
		if (StringUtils.isNotEmpty(format)) {
			TextFormater textFormater = new TextFormater(format);
			textFormater.setNullValue(view.nullvalue());
			showText = textFormater.format(value);
		}

		//setObjectValue
		text.setObjectValue(showText);
		return text;
	}

	public String getName() {
		return "Label";
	}

	static final class ShowLabel extends com.insigma.commons.ui.widgets.Label implements IInputControl {

		/**
		 * @param arg0
		 * @param arg1
		 */
		public ShowLabel(Composite arg0, int arg1) {
			super(arg0, arg1);
		}

		@Override
		public void reset() {

		}

		@Override
		public void setObjectValue(Object value) {
			setText(value.toString());
		}

		@Override
		public Object getObjectValue() {
			return null;
		}

	}
}
