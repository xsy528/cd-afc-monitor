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
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author DingLuofeng
 *
 */
public class CTabFolder extends org.eclipse.swt.custom.CTabFolder implements IControl {

	private MinimizeButtonContainer buttonContainer;

	public CTabFolder(Composite parent, int style) {
		super(parent, style);
		Color[] color = new Color[4];
		color[0] = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
		color[1] = new Color(Display.getDefault(), 176, 201, 246);
		color[2] = new Color(Display.getDefault(), 198, 216, 249);
		color[3] = new Color(Display.getDefault(), 198, 216, 249);
		int[] intArray = new int[] { 25, 45, 100 };
		setSelectionBackground(color, intArray, true);
		addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (e.item instanceof org.eclipse.swt.custom.CTabItem) {
				}
			}
		});

		addCTabFolder2Listener(new AFCCTabFolder2Adapter());

		if (parent instanceof SashForm) {
			((SashForm) parent).listShowControl();
		}
	}

	public void reset() {
	}

	private class AFCCTabFolder2Adapter extends CTabFolder2Adapter {

		@Override
		public void minimize(CTabFolderEvent e) {
			Widget widget = e.widget;
			if (widget != null && widget instanceof CTabFolder) {
				CTabFolder tabFolder = (CTabFolder) widget;
				tabFolder.setMinimized(true);
				Composite parent = tabFolder.getParent();

				windowMinimize(parent, tabFolder);
				// 改变选项卡的布局，呈现最小化状态
				windowLayout(parent);// 刷新布局，否则重新设置的布局将不起作用
			}
		}

		@Override
		public void maximize(CTabFolderEvent e) {
			Widget widget = e.widget;
			if (widget != null && widget instanceof CTabFolder) {
				CTabFolder tabFolder = (CTabFolder) widget;
				tabFolder.setMaximized(true);
				Composite parent = tabFolder.getParent();

				windowMaximize(parent, tabFolder);
				// 改变选项卡的布局，呈现最小化状态
				tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				windowLayout(parent);// 刷新布局，否则重新设置的布局将不起作用
			}
		}

		@Override
		public void restore(CTabFolderEvent e) {
			Widget widget = e.widget;
			if (widget != null && widget instanceof CTabFolder) {
				restoreCTabFolder((CTabFolder) widget);
			}

		}

	}

	protected void windowMaximize(Composite parent, Control control) {
		if (parent != null) {
			if (parent instanceof SashForm) {
				((SashForm) parent).setMaximizedControl(control);
				windowMaximize(parent.getParent(), parent);
			}
		}
	}

	public void restoreCTabFolder(CTabFolder tabFolder) {
		tabFolder.setMinimized(false);
		tabFolder.setMaximized(false);
		Composite parent = tabFolder.getParent();

		windowRestore(parent, tabFolder);
		// 改变选项卡的布局，呈现最小化状态
		windowLayout(parent);// 刷新布局，否则重新设置的布局将不起作用
	}

	protected void windowRestore(Composite parent, Control control) {
		if (parent != null) {
			if (parent instanceof SashForm) {
				SashForm sashForm = (SashForm) parent;
				sashForm.restore(control);
			}
		}
	}

	protected void windowMinimize(Composite parent, CTabFolder control) {
		if (parent != null) {
			if (parent instanceof SashForm) {
				if (buttonContainer != null) {
					buttonContainer.createButton(control);
				}
				SashForm sashForm = (SashForm) parent;
				sashForm.minimize(control);
			}
		}
	}

	protected void windowLayout(Composite parent) {
		if (parent != null) {
			parent.layout(true);
			windowLayout(parent.getParent());
		}
	}

	public MinimizeButtonContainer getButtonContainer() {
		return buttonContainer;
	}

	public void setButtonContainer(MinimizeButtonContainer buttonContainer) {
		this.buttonContainer = buttonContainer;
	}
}
