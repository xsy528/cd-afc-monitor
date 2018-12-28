/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import com.insigma.commons.ui.manager.FunctionManager;

public class Button extends org.eclipse.swt.widgets.Button implements IInputControl {

	private String functionID;

	private Object objectValue;

	private Object selectedValue;

	private Object unSelectedValue;

	private boolean defaultValue = true;

	public Button(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	protected void checkSubclass() {
	}

	public void reset() {
		if ((getStyle() & SWT.CHECK) != 0 || (getStyle() & SWT.RADIO) != 0) {
			setSelection(defaultValue);
		}
	}

	public Object getObjectValue() {
		return this.objectValue;
	}

	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;
	}

	public Object getSelectedValue() {
		return this.selectedValue;
	}

	public void setSelectedValue(Object selectedValue) {
		this.selectedValue = selectedValue;
	}

	public Object getUnSelectedValue() {
		return this.unSelectedValue;
	}

	public void setUnSelectedValue(Object unSelectedValue) {
		this.unSelectedValue = unSelectedValue;
	}

	public String getFunctionID() {
		return this.functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
		setEnabled(FunctionManager.validateMenu(functionID));
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void setSelection(boolean arg0) {
		boolean oldstatus = super.getSelection();
		super.setSelection(arg0);
		if (oldstatus != arg0 && arg0) {
			Event event1 = new Event();
			event1.type = SWT.Selection;
			event1.widget = this;
			notifyListeners(SWT.Selection, event1);
		}
	}
}
