/* 
 * 日期：2018-8-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.MultiChoiceCombo;

/**
 * Ticket:多选下拉框定义
 * 
 * @author chenhangwen
 */
public class MultiChoiceComboProvider implements IWidgetProvider {

	public IInputControl create(Composite parent, BeanField beanField) {

		MultiChoiceCombo control = new MultiChoiceCombo(parent, SWT.NONE);
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		DataSource dataSource = field.getAnnotation(DataSource.class);
		if (dataSource != null) {
			try {
				Object obj = dataSource.provider().newInstance();
				if (obj instanceof ITreeProvider) {
					control.setTreeProvider((ITreeProvider) obj);
				}

				if (null != value) {
					control.setObjectValue(value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return control;

	}

	public String getName() {
		return "MultiChoiceText";
	}

}
