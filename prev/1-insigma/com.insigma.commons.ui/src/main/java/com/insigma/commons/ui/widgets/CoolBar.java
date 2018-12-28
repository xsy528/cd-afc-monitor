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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;

public class CoolBar extends org.eclipse.swt.widgets.CoolBar implements IControl {

	public CoolBar(Composite parent, int style) {
		super(parent, style);
	}

	public void packSize(boolean locked) {
		this.setLocked(locked);
		CoolItem[] ctrl = this.getItems();
		if (ctrl != null) {
			for (CoolItem coolItem : ctrl) {
				calcSize(coolItem);
			}
		}
	}

	private void calcSize(CoolItem item) {
		Control control = item.getControl();
		if (control != null) {
			Point pt = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			pt = item.computeSize(pt.x, pt.y);
			item.setSize(pt);
		}
	}

	public void checkSelection(org.eclipse.swt.widgets.ToolItem item) {
		CoolItem[] coolItems = this.getItems();
		for (CoolItem coolItem : coolItems) {
			Control control = coolItem.getControl();
			if (control instanceof ToolBar) {
				ToolBar toolBar = (ToolBar) control;
				org.eclipse.swt.widgets.ToolItem[] items = toolBar.getItems();
				for (org.eclipse.swt.widgets.ToolItem toolItem : items) {
					if (item.getText().equals(toolItem.getText())) {
						toolItem.setSelection(true);
					} else {
						toolItem.setSelection(false);
					}
				}
			}
		}

	}

	@Override
	protected void checkSubclass() {
	}

	public void reset() {
	}

}
