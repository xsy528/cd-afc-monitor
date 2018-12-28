/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.dic.IDefinition;
import com.insigma.commons.ui.anotation.ButtonGroupView;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.provider.IComboDataSource;
import com.insigma.commons.ui.widgets.ButtonGroup;
import com.insigma.commons.ui.widgets.IInputControl;

public class CheckBoxGroupProvider implements IWidgetProvider {

	private static final Log logger = LogFactory.getLog(ComboProvider.class);

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		ButtonGroup control = new ButtonGroup(parent, SWT.CHECK);
		DataSource ds = field.getAnnotation(DataSource.class);

		if (ds != null) {
			try {
				String[] valuelist = ds.list();
				control.setLabels(valuelist);
				if (ds.values() != null) {
					control.setValues(ds.values());
				}
				ButtonGroupView bgv = field.getAnnotation(ButtonGroupView.class);
				if (bgv != null) {
					control.getLayout().numColumns = bgv.numColumns();
					control.layout();
				}

				field.setAccessible(true);

				if (IComboDataSource.class.isAssignableFrom(ds.provider())) {
					IComboDataSource<?> provider = (IComboDataSource<?>) ds.provider().newInstance();
					String[] items = provider.getText();
					Object[] values = provider.getValue();
					if (!ArrayUtils.isEmpty(items)) {
						control.setLabels(items);
					}
					if (!ArrayUtils.isEmpty(values)) {
						control.setValues(values);
					}
				}

				if (IDefinition.class.isAssignableFrom(ds.provider())) {
					// IDefinition provider = (IDefinition) ds
					// .provider().newInstance();
					Method getInstance = ds.provider().getMethod("getInstance");
					IDefinition provider = (IDefinition) getInstance.invoke(null);

					String[] items = provider.getNameList();
					Object[] values = provider.getValueList();
					if (!ArrayUtils.isEmpty(items)) {
						control.setLabels(items);
					}
					if (!ArrayUtils.isEmpty(values)) {
						control.setValues(values);
					}
				}

			} catch (Exception e) {
				logger.error("根据DataSource标签设置Combo列表异常 VIEW=" + view.label(), e);
			}
		}

		if (null != value) {
			control.setObjectValue(value);
		}

		return control;

	}

	public String getName() {
		return "CheckBox";
	}

}
