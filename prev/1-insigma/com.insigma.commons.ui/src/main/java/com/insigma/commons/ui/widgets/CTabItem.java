/* 
 * 日期：Dec 20, 2007
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.custom.CTabFolder;

import com.insigma.commons.ui.manager.FunctionManager;

public class CTabItem extends org.eclipse.swt.custom.CTabItem implements IControl {

	private String functionID;

	public CTabItem(CTabFolder arg0, int arg1) {
		super(arg0, arg1);
	}

	protected void checkSubclass() {
	}

	public String getFunctionID() {
		return this.functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
		if (!FunctionManager.validateMenu(functionID) && !isDisposed()) {
			dispose();
		}
	}

	public void reset() {
	}

	public void setEnabled(boolean enable) {

	}

	public boolean isEnabled() {
		return false;
	}

	private boolean isInited = false;

	/**
	 * @return the isInited
	 */
	public boolean isInited() {
		return isInited;
	}

	/**
	 * @param isInited
	 *            the isInited to set
	 */
	public void setInited(boolean isInited) {
		this.isInited = isInited;
	}

}
