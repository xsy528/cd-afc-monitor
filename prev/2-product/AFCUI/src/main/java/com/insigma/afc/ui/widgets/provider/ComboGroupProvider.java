/* 
 * 项目:    用户界面组件
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets.provider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.ui.widgets.ComboGroup;
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 
 * @author wangxinhao
 *
 */
public class ComboGroupProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		DataSourceData dataSource = beanField.getAnnotationData(DataSourceData.class);

		ComboGroup control = new ComboGroup(parent, SWT.NONE);
		if (null != value) {
			control.setObjectValue(value);
		}
		//		DataSource dataSource = field.getAnnotation(DataSource.class);
		if (dataSource != null) {
			try {
				Object obj = dataSource.provider().newInstance();
				if (obj instanceof ITreeProvider) {
					int depth = ((ITreeProvider) obj).getTree().getChilds().size();
					control.setDepth(depth);
					control.setTreeProvider((ITreeProvider) obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return control;

	}

	public String getName() {
		return "ComboGroup";
	}
}
