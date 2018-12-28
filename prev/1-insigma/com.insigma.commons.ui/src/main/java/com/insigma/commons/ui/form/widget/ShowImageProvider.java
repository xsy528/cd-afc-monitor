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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;
import com.swtdesigner.SWTResourceManager;

public class ShowImageProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		ShowLabel text = null;
		if (view.wrap()) {
			text = new ShowLabel(parent, SWT.BORDER | SWT.WRAP);
		} else if (view.style() != SWT.DEFAULT) {
			text = new ShowLabel(parent, view.style());
		} else {
			text = new ShowLabel(parent, SWT.BORDER);
		}
		String showText = value == null ? view.nullvalue() : value.toString();

		//setObjectValue
		text.setObjectValue(showText, view.heightHint());
		return text;
	}

	public String getName() {
		return "Image";
	}

	static final class ShowLabel extends EnhanceComposite implements IInputControl {
		String path;

		/**
		 * @param arg0
		 * @param arg1
		 */
		public ShowLabel(Composite arg0, int arg1) {
			super(arg0, arg1);
		}

		@Override
		public void reset() {

		}

		@Override
		public void setObjectValue(Object value) {

		}

		public void setObjectValue(Object value, int h) {
			path = value.toString();
			Image image = SWTResourceManager.getImage(ShowImageProvider.class, path, -1, h);
			setBackgroundImage(image);
			GridData gridData = new GridData();
			gridData.heightHint = image.getBounds().height;
			gridData.widthHint = image.getBounds().width;
			setLayoutData(gridData);
			layout();
		}

		@Override
		public Object getObjectValue() {
			return path;
		}

	}
}
