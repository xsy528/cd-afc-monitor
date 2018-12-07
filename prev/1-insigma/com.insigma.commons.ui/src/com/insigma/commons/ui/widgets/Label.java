/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class Label extends org.eclipse.swt.widgets.Label implements IControl {

	private SelectionListener listeners;

	public Label(Composite arg0, int arg1) {
		super(arg0, arg1);
		this.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent arg0) {
				if (listeners != null) {
					SelectionEvent evt = new SelectionEvent(new Event());
					evt.widget = arg0.widget;
					evt.data = arg0.data;
					listeners.widgetSelected(evt);
				}
			}

		});
	}

	protected void checkSubclass() {
	}

	public void addSelectionListener(SelectionListener listener) {
		listeners = listener;
	}

	public void removeSelectionListener(SelectionListener listener) {
		listeners = null;
	}

	public void reset() {
	}
}
