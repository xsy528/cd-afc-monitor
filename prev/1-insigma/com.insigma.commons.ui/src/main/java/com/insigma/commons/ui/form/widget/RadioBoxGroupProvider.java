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

import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.ButtonGroup;
import com.insigma.commons.ui.widgets.IInputControl;

public class RadioBoxGroupProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		DataSourceData ds = beanField.getAnnotationData(DataSourceData.class);
		//		DataSource ds = field.getAnnotation(DataSource.class);
		String[] keylist = null;
		Object[] valuelist = null;
		if (ds == null) {
			keylist = new String[] { "undefinition" };
			valuelist = new String[] { "undefinition" };
		} else {
			keylist = ds.list();
			valuelist = ds.values();
		}
		ButtonGroup control = new ButtonGroup(parent, SWT.RADIO);
		control.setLabels(keylist);

		if (valuelist != null) {
			control.setValues(valuelist);
		}
		control.reset();
		if (value != null) {
			control.setObjectValue(value);
		}
		return control;

	}

	public String getName() {
		return "RadioBox";
	}

}
