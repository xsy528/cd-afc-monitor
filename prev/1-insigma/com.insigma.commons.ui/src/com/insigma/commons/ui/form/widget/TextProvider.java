/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.format.TextFormater;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Text;

public class TextProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		Text text = null;
		if (view.wrap()) {
			if (view.heightHint() != SWT.DEFAULT) {
				text = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
			} else {
				text = new Text(parent, SWT.BORDER | SWT.WRAP);
			}
		} else if (view.style() != SWT.DEFAULT) {
			text = new Text(parent, view.style());
		} else {
			text = new Text(parent, SWT.WRAP | SWT.BORDER);
		}
		//setFieldType
		ListType listType = field.getAnnotation(ListType.class);
		if (listType == null) {
			text.setFieldType(field.getType());
		} else {
			text.setFieldType(listType.type());
		}

		if (field.getType().isPrimitive() || Number.class.isAssignableFrom(field.getType())) {
			text.setDigitalOnly(true);
		}
		String format = view.format();
		if (StringUtils.isNotEmpty(format)) {
			TextFormater textFormater = new TextFormater(format);
			textFormater.setNullValue(view.nullvalue());
			text.setTextFormater(textFormater);
		}

		if (view.regexp() != null && !view.regexp().equals("")) {
			text.setExpression(view.regexp());
		}
		if (view.nullvalue() != null && !view.nullvalue().equals("")) {
			text.setDefaultText(view.nullvalue());
		}

		text.setFormatLength(view.formatLength());

		if (view.zero()) {
			text.setZero(true);
		}

		if (view.isEditable() == false) {
			text.setEditable(false);
		}

		//		if (!view.modify()) {
		//			text.setEnabled(view.modify());
		//		}

		if (view.isHasMD5Format()) {
			text.setMd5Format(true);
		}
		if (view.radix() != -1) {
			text.setRadix(view.radix());
		}

		//setObjectValue
		text.setObjectValue(value);

		return text;
	}

	public String getName() {
		return "Text";
	}
}
