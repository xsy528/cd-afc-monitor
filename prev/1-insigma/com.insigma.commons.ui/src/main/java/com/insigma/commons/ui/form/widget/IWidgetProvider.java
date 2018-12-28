/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.IInputControl;

public interface IWidgetProvider {

	public String getName();

	//	public IInputControl create(Composite parent, Field field, Object value);

	public IInputControl create(Composite parent, BeanField beanField);
}
