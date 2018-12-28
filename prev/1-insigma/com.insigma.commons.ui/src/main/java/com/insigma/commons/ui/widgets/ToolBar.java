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
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ToolBar extends org.eclipse.swt.widgets.ToolBar implements IControl {

	private KeyListener keylistener;

	private boolean enableAccelerator = false;

	private class KeyListener implements Listener {

		public void handleEvent(Event arg0) {
			if (getShell().equals(Display.getDefault().getActiveShell())) {
				org.eclipse.swt.widgets.ToolItem[] items = getItems();
				for (org.eclipse.swt.widgets.ToolItem item : items) {
					if (item instanceof ToolItem) {
						ToolItem afcItem = (ToolItem) item;
						if (arg0.stateMask == 0 && afcItem.isEffective() && afcItem.getAccelerator() == arg0.keyCode) {
							afcItem.notifySelection();
							return;
						}
					}
				}

			}
		}

	}

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public ToolBar(Composite parent, int style) {
		super(parent, style);

		keylistener = new KeyListener();

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				if (enableAccelerator == true) {
					Display.getDefault().removeFilter(SWT.KeyUp, keylistener);
				}
			}
		});
	}

	public void changeStatus(SelectionEvent e) {
		org.eclipse.swt.widgets.ToolItem[] items = getItems();
		for (org.eclipse.swt.widgets.ToolItem item : items) {
			item.setEnabled(true);
		}
		((org.eclipse.swt.widgets.ToolItem) e.getSource()).setEnabled(false);
	}

	@Override
	protected void checkSubclass() {

	}

	public void reset() {
	}

	public boolean isEnableAccelerator() {
		return enableAccelerator;
	}

	public void setEnableAccelerator(boolean enableAccelerator) {
		if (this.enableAccelerator == enableAccelerator) {
			return;
		}
		this.enableAccelerator = enableAccelerator;
		if (enableAccelerator) {
			Display.getDefault().addFilter(SWT.KeyUp, keylistener);
		} else {
			Display.getDefault().removeFilter(SWT.KeyUp, keylistener);
		}
	}

}
