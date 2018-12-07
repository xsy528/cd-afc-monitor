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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class BitGroup extends EnhanceComposite implements IInputControl {

	protected Button[] buttons;

	protected GridLayout layout;

	public BitGroup(Composite parent, int style) {
		super(parent, style);
		layout = new GridLayout();
		setLayout(layout);
	}

	public void setOptions(String[] list) {
		buttons = new Button[list.length];
		for (int i = 0; i < list.length; i++) {
			if (!list[i].equals("")) {
				buttons[i] = new Button(this, SWT.CHECK);
				buttons[i].setText(list[i]);
				buttons[i].setBackground(getBackground());
			}
		}
		layout.numColumns = list.length;
		layout();
	}

	public int getValue() {
		int value = 0;
		for (int i = 0; i < buttons.length; i++) {
			if ((buttons[i] != null) && buttons[i].getSelection()) {
				value = value | (1 << i);
			}
		}
		return value;
	}

	public void setValue(int value) {
		for (int i = 0; i < buttons.length; i++) {
			if ((value & 0x01) != 0) {
				buttons[buttons.length - 1 - i].setSelection(true);
			} else {
				buttons[buttons.length - 1 - i].setSelection(false);
			}
			value = (value >> 1);
		}
	}

	public Object getObjectValue() {
		return getValue();
	}

	public void setObjectValue(Object value) {
		int val = Integer.valueOf(value.toString());
		setValue(val);
	}

	public void reset() {
		if (buttons != null) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setSelection(false);
			}
		}
	}
}
