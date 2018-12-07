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
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.provider.IComboDataSource;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.IInputControl;

public class ComboProvider implements IWidgetProvider {

	private static final Log logger = LogFactory.getLog(ComboProvider.class);

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		DataSourceData ds = beanField.getAnnotationData(DataSourceData.class);
		ViewData view = beanField.getAnnotationData(ViewData.class);

		Combo combo = new Combo(parent, SWT.NONE | SWT.READ_ONLY);
		//		DataSource ds = field.getAnnotation(DataSource.class);

		if (ds != null) {
			try {
				if (!ArrayUtils.isEmpty(ds.list())) {
					combo.setItems(ds.list());
				}
				if (!ArrayUtils.isEmpty(ds.values())) {
					combo.setValues(ds.values());
				}
				field.setAccessible(true);
				if (IComboDataSource.class.isAssignableFrom(ds.provider())) {
					IComboDataSource<?> provider = (IComboDataSource<?>) ds.provider().newInstance();
					String[] items = provider.getText();
					Object[] values = provider.getValue();
					if (!ArrayUtils.isEmpty(items)) {
						combo.setItems(items);
					}
					if (!ArrayUtils.isEmpty(values)) {
						combo.setValues(values);
					}
				}

				if (IDefinition.class.isAssignableFrom(ds.provider())) {
					Method getInstance = ds.provider().getMethod("getInstance");
					IDefinition provider = (IDefinition) getInstance.invoke(null);
					String[] items = provider.getNameList();
					Object[] values = provider.getValueList();
					if (!ArrayUtils.isEmpty(items)) {
						combo.setItems(items);
					}
					if (!ArrayUtils.isEmpty(values)) {
						combo.setValues(values);
					}
				}
			} catch (Exception e) {
				logger.error("根据DataSource标签设置Combo列表异常 VIEW=" + view.label(), e);
			}
		}

		if (value != null) {
			combo.setObjectValue(value);
		} else {
			combo.setDefaultValue(0);
		}
		return combo;

	}

	public String getName() {
		return "Combo";
	}

}
