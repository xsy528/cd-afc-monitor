/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

public class BooleanGroup extends BitGroup {

	public BooleanGroup(Composite parent, int style) {
		super(parent, style);
	}

	public Object getObjectValue() {
		ArrayList<Boolean> value = new ArrayList<Boolean>();
		for (int i = 0; i < buttons.length; i++) {
			value.add(buttons[i].getSelection());
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public void setObjectValue(Object value) {
		List<Boolean> v = (List<Boolean>) value;
		for (int i = 0; i < v.size(); i++) {
			buttons[i].setSelection(v.get(i));
		}
	}
}
