package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.ButtonGroupView;
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.provider.IButtonGroupDataSource;
import com.insigma.commons.ui.widgets.ButtonGroup;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 
 * @author DLF
 *
 */
public class ButtonGroupProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		ButtonGroup buttonGroup = null;
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ButtonGroupView buttonGroupView = field.getAnnotation(ButtonGroupView.class);

		if (buttonGroupView == null) {
			buttonGroup = new ButtonGroup(parent, SWT.CHECK);
		} else {
			buttonGroup = new ButtonGroup(parent, buttonGroupView.numColumns(), buttonGroupView.uniqueIntResult(),
					buttonGroupView.style());
			buttonGroup.setBackground(parent.getBackground());
			buttonGroup.setBackground(parent.getBackground());
		}
		DataSourceData ds = beanField.getAnnotationData(DataSourceData.class);
		//		DataSource ds = field.getAnnotation(DataSource.class);

		if (ds != null && ds.provider() != Object.class) {
			Class<?> cls = ds.provider();
			try {
				Object obj = cls.newInstance();
				if (obj instanceof IButtonGroupDataSource) {
					IButtonGroupDataSource dataSource = (IButtonGroupDataSource) obj;
					if (dataSource.getLabels() != null) {
						buttonGroup.setLabels(dataSource.getLabels());
					}
					if (dataSource.getValues() != null) {
						buttonGroup.setValues(dataSource.getValues());
					}
					//设置默认值
					buttonGroup.setObjectValue(dataSource.getDefault());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (ds != null) {
			if (ds.list() != null && ds.list().length != 0) {
				buttonGroup.setLabels(ds.list());
			}
			if (ds.values() != null && ds.values().length != 0) {
				buttonGroup.setValues(ds.values());
			}
		}
		buttonGroup.setObjectValue(value);

		return buttonGroup;
	}

	@Override
	public String getName() {
		return "ButtonGroup";
	}

}