package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.ButtonGroupView;
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.provider.IButtonGroupDataSource;
import com.insigma.commons.ui.widgets.ButtonGroupNew;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 
 * Ticket: ButtonGroup可转换成多种类型（字符串，int等）
 * @author  hexingyue
 *
 */
public class ButtonGroupNewProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		ButtonGroupNew buttonGroup = null;
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ButtonGroupView buttonGroupView = field.getAnnotation(ButtonGroupView.class);

		if (buttonGroupView == null) {
			buttonGroup = new ButtonGroupNew(parent, SWT.CHECK);
		} else {
			Class type = field.getType();
			buttonGroup = new ButtonGroupNew(parent, buttonGroupView.numColumns(), buttonGroupView.uniqueIntResult(),
					type, buttonGroupView.style());
			buttonGroup.setBackground(parent.getBackground());
			buttonGroup.setBackground(parent.getBackground());
		}
		DataSourceData ds = beanField.getAnnotationData(DataSourceData.class);
		//		DataSource ds = field.getAnnotation(DataSource.class);
		ViewData view = beanField.getAnnotationData(ViewData.class);
		buttonGroup.setFormatLength(view.formatLength());
		if (view.zero() == true) {
			buttonGroup.setZero(true);
		}

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
		return "ButtonGroupNew";
	}

}