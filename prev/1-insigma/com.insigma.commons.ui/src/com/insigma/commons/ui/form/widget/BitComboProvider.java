package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.ButtonGroupView;
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.provider.IButtonGroupDataSource;
import com.insigma.commons.ui.widgets.BitCombo;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 
 * @author DLF
 *
 */
public class BitComboProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {

		Field field = beanField.field;
		Object fieldValue = beanField.fieldValue;
		ButtonGroupView buttonGroupView = field.getAnnotation(ButtonGroupView.class);

		if (buttonGroupView == null) {
			BitCombo bitList = new BitCombo(parent, SWT.CHECK);
			return bitList;
		}

		BitCombo buttonGroup = new BitCombo(parent, buttonGroupView.numColumns(), buttonGroupView.style());
		buttonGroup.setBackground(parent.getBackground());

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
		}
		buttonGroup.setObjectValue(fieldValue);

		return buttonGroup;
	}

	@Override
	public String getName() {
		return "BitCombo";
	}

}