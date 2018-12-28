/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.log.Log;
import com.insigma.commons.log.Logs;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.widget.BiTextBoxProvider;
import com.insigma.commons.ui.form.widget.BitGroupProvider;
import com.insigma.commons.ui.form.widget.BooleanGroupProvider;
import com.insigma.commons.ui.form.widget.ButtonGroupNewProvider;
import com.insigma.commons.ui.form.widget.ButtonGroupProvider;
import com.insigma.commons.ui.form.widget.ButtonProvider;
import com.insigma.commons.ui.form.widget.ComboProvider;
import com.insigma.commons.ui.form.widget.DateProvider;
import com.insigma.commons.ui.form.widget.DateSpanProvider;
import com.insigma.commons.ui.form.widget.DateStrProvider;
import com.insigma.commons.ui.form.widget.DateTimeProvider;
import com.insigma.commons.ui.form.widget.DateTimeSpanProvider;
import com.insigma.commons.ui.form.widget.DateTimeStrProvider;
import com.insigma.commons.ui.form.widget.FileProvider;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.form.widget.LabelProvider;
import com.insigma.commons.ui.form.widget.MultiChoiceComboProvider;
import com.insigma.commons.ui.form.widget.MultiComboProvider;
import com.insigma.commons.ui.form.widget.RadioBoxGroupProvider;
import com.insigma.commons.ui.form.widget.ShowImageProvider;
import com.insigma.commons.ui.form.widget.SpinnerProvider;
import com.insigma.commons.ui.form.widget.TextProvider;
import com.insigma.commons.ui.form.widget.TimeProvider;
import com.insigma.commons.ui.form.widget.TimeStampProvider;
import com.insigma.commons.ui.form.widget.TimeStrProvider;
import com.insigma.commons.ui.form.widget.TreeComboProvider;
import com.insigma.commons.ui.widgets.IInputControl;

public class WidgetsFactory {

	private static WidgetsFactory instance = new WidgetsFactory();

	private List<IWidgetProvider> widgetProviders;

	private Log logger = Logs.getLog(WidgetsFactory.class);

	public static WidgetsFactory getInstance() {
		return instance;
	}

	private WidgetsFactory() {
		addWidgetProvider(new TextProvider());
		addWidgetProvider(new BitGroupProvider());
		addWidgetProvider(new ButtonProvider());
		addWidgetProvider(new ComboProvider());
		addWidgetProvider(new DateProvider());
		addWidgetProvider(new FileProvider());
		addWidgetProvider(new RadioBoxGroupProvider());
		addWidgetProvider(new SpinnerProvider());
		addWidgetProvider(new TimeProvider());
		addWidgetProvider(new DateTimeProvider());
		addWidgetProvider(new DateSpanProvider());
		addWidgetProvider(new DateTimeSpanProvider());
		addWidgetProvider(new TreeComboProvider());
		addWidgetProvider(new MultiComboProvider());
		addWidgetProvider(new BooleanGroupProvider());
		addWidgetProvider(new BiTextBoxProvider());
		addWidgetProvider(new ButtonGroupProvider());
		addWidgetProvider(new LabelProvider());
		addWidgetProvider(new ShowImageProvider());

		addWidgetProvider(new TimeStampProvider());

		addWidgetProvider(new TimeStrProvider());
		addWidgetProvider(new DateStrProvider());
		addWidgetProvider(new DateTimeStrProvider());
		addWidgetProvider(new ButtonGroupNewProvider());

		addWidgetProvider(new MultiChoiceComboProvider());

	}

	//	public IInputControl create(Composite parent, Field field, Object value, View view) {
	//		return create(parent, field, value, new ViewData(view));
	//	}

	public IInputControl create(Composite parent, BeanField beanField) {
		if (widgetProviders != null) {
			ViewData view = beanField.getAnnotationData(ViewData.class);
			for (IWidgetProvider widgetProvider : widgetProviders) {
				if (view.type().trim().equals(widgetProvider.getName())) {
					return widgetProvider.create(parent, beanField);
				}
			}
		}
		return null;
	}

	public IInputControl create(Composite parent, Field field, Object value) {
		BeanField beanField = null;
		if (value instanceof BeanField) {
			beanField = new BeanField(field, BeanUtil.getValue(((BeanField) value).fieldValue, field.getName()),
					(BeanField) value);
		} else {
			beanField = new BeanField(field, value, null);

		}
		Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
		beanField.setFieldAnnotations(fieldAnnotations);
		return create(parent, beanField);
	}

	public List<IWidgetProvider> getWidgetProviders() {
		return widgetProviders;
	}

	public void setWidgetProviders(List<IWidgetProvider> widgetProviders) {
		this.widgetProviders = widgetProviders;
	}

	public void addWidgetProvider(IWidgetProvider widgetProvider) {
		if (widgetProviders == null) {
			widgetProviders = new ArrayList<IWidgetProvider>();
		}
		for (IWidgetProvider provider : widgetProviders) {
			if (provider.getName().equals(widgetProvider.getName())) {
				logger.warn("widgetProvider已经存在，替换原来的widgetProvider,控件：" + widgetProvider.getName());
				widgetProviders.remove(provider);
				break;
			}
		}
		widgetProviders.add(widgetProvider);
	}
}
