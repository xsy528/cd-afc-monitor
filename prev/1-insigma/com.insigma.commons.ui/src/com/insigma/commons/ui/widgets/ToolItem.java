/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.ToolBar;

import com.insigma.commons.ui.manager.FunctionManager;

public class ToolItem extends org.eclipse.swt.widgets.ToolItem implements IControl {

	private SelectionListener selectionListener;

	private String functionID;

	private int accelerator;

	private boolean effective;

	public ToolItem(ToolBar parent, int style) {
		super(parent, style);
	}

	@Override
	public void dispose() {
		super.dispose();
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
		effective = FunctionManager.validateMenu(functionID);
		setEnabled(effective);

	}

	public int getAccelerator() {
		return accelerator;
	}

	public void setAccelerator(int accelerator) {
		effective = true;
		this.accelerator = accelerator;
	}

	@Override
	public void addSelectionListener(SelectionListener arg0) {
		selectionListener = arg0;
		super.addSelectionListener(arg0);
	}

	public void notifySelection() {
		if (selectionListener != null) {
			selectionListener.widgetSelected(null);
		}
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}

	public boolean isEffective() {
		return effective;
	}
}
