/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.widgets.Menu;

import com.insigma.commons.ui.manager.FunctionManager;

public class MenuItem extends org.eclipse.swt.widgets.MenuItem implements IControl {

	private String functionID;

	public MenuItem(Menu parent, int style) {
		super(parent, style);
	}

	@Override
	protected void checkSubclass() {
	}

	public void reset() {
	}

	public String getFunctionID() {
		return this.functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
		setEnabled(FunctionManager.validateMenu(functionID));
	}

}
