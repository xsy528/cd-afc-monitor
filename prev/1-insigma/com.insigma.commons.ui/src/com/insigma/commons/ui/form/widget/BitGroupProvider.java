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
import com.insigma.commons.ui.widgets.BitGroup;
import com.insigma.commons.ui.widgets.IInputControl;

public class BitGroupProvider implements IWidgetProvider {

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
			valuelist = ds.list();
		}

		BitGroup control = new BitGroup(parent, SWT.CHECK);
		control.setOptions(valuelist);

		if (value != null) {
			if (value instanceof Byte) {
				control.setObjectValue(new Integer((Byte) value));
			}
			if (value instanceof Short) {
				control.setObjectValue(new Integer((Short) value));
			}
			if (value instanceof Integer) {
				control.setObjectValue(new Integer((Integer) value));
			}
		}

		return control;
	}

	public String getName() {
		return "BitGroup";
	}

}
