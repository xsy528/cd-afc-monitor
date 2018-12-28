/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.ui.form.BeanTableViewer;

public class PropertyView extends FrameWorkView {

	private BeanTableViewer beanTableViewer;

	public PropertyView(Composite arg0, int numColumns, int arg1) {
		super(arg0, arg1);
		setText("属性");
		setLayout(new FillLayout());
		setIcon("/com/insigma/commons/ui/shape/property.png");
		beanTableViewer = new BeanTableViewer(this, numColumns, SWT.NONE);
		beanTableViewer.layout();
	}

	public void setBeanData(Object beanData) {
		//		table.setObject(form);
		beanTableViewer.setBeanData(beanData);
	}

	/**
	 * @return the beanTableViewer
	 */
	public BeanTableViewer getBeanTableViewer() {
		return beanTableViewer;
	}

}
