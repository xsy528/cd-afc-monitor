/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Spinner;

public class SpinnerProvider implements IWidgetProvider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Class type = field.getType();
		if (List.class.isAssignableFrom(type)) {
			ListType listType = field.getAnnotation(ListType.class);
			if (null != listType) {
				type = listType.type();
			}
		} else if (type.isArray()) {
			type = field.getType().getComponentType();
		}
		if ((null == value) && Number.class.isAssignableFrom(type)) {
			try {
				Constructor<? extends Class> c = type.getConstructor(String.class);
				value = c.newInstance("0");
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
			spinner.setMaximum(Byte.MAX_VALUE);
			spinner.setSelection(((Byte) value).intValue());
		}
		if (type.equals(Short.class) || type.equals(Short.TYPE)) {
			spinner.setMaximum(Short.MAX_VALUE);
			spinner.setSelection(((Short) value).intValue());
		}
		if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
			spinner.setMaximum(Integer.MAX_VALUE);
			spinner.setSelection(((Integer) value).intValue());
		}
		if (type.equals(Long.class) || type.equals(Long.TYPE)) {
			spinner.setMaximum(Integer.MAX_VALUE);
			spinner.setSelection(((Long) value).intValue());
		}

		if (type.equals(Double.class) || type.equals(Double.TYPE)) {
			spinner.setMaximum(Integer.MAX_VALUE);
			spinner.setSelection(((Double) value).intValue());
		}

		if (view.regexp() != null && !view.regexp().equals("")) {
			spinner.setExpression(view.regexp());
		}

		Validation validate = field.getAnnotation(Validation.class);
		if (null != validate) {

			spinner.setMaximum((int) validate.maxValue());

			spinner.setMinimum((int) validate.minValue());

		}
		return spinner;
	}

	public String getName() {
		return "Spinner";
	}

}
