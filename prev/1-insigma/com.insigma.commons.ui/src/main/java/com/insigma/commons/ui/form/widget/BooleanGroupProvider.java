/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.BooleanGroup;
import com.insigma.commons.ui.widgets.IInputControl;

public class BooleanGroupProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		//		Field field = beanField.field;
		Object value = beanField.fieldValue;
		DataSourceData ds = beanField.getAnnotationData(DataSourceData.class);
		//		DataSource ds = field.getAnnotation(DataSource.class);
		String[] valuelist = null;
		if (ds == null) {
			valuelist = new String[] { "undefinition" };
		} else {
			valuelist = ds.values();
		}
		BooleanGroup control = new BooleanGroup(parent, SWT.CHECK);
		control.setOptions(valuelist);

		if (value != null && value instanceof List<?>) {
			control.setObjectValue((List<?>) value);
		}

		return control;
	}

	public String getName() {
		return "BooleanGroup";
	}
}
