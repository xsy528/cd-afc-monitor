/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.MenuItem;

import com.insigma.commons.ui.manager.FunctionManager;

public class Menu extends org.eclipse.swt.widgets.Menu implements IControl {

	private String functionID;

	public Menu(Control parent) {
		super(parent);
	}

	public Menu(Decorations parent, int style) {
		super(parent, style);
	}

	@Override
	protected void checkSubclass() {
	}

	public void reset() {
	}

	private void setEnable(boolean enabled) {
		MenuItem[] childrens = this.getItems();
		for (MenuItem menuItem : childrens) {
			menuItem.setEnabled(enabled);
		}
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
		setEnable(FunctionManager.validateMenu(functionID));
	}

}
