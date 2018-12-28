/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets.provider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.ui.widgets.EquipmentTypeComposite;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.widgets.IInputControl;

public class EquTypeProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		EquipmentTypeComposite control = new EquipmentTypeComposite(parent, SWT.NONE);
		return control;
	}

	public String getName() {
		return "EquType";
	}

}
