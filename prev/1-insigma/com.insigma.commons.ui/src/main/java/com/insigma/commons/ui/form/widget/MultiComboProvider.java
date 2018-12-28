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
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.MultiCombo;

public class MultiComboProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		DataSourceData dataSource = beanField.getAnnotationData(DataSourceData.class);
		MultiCombo control = new MultiCombo(parent, SWT.NONE);
		//		DataSource dataSource = field.getAnnotation(DataSource.class);
		if (dataSource != null) {
			try {
				Object obj = dataSource.provider().newInstance();
				if (obj instanceof ITreeProvider) {
					control.setDepth(2);
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
		return "MultiCombo";
	}
}
