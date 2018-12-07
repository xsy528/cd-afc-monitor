/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.widgets.CoolBar;

public class CoolItem extends org.eclipse.swt.widgets.CoolItem implements IControl {

	public CoolItem(CoolBar parent, int style) {
		super(parent, style);
	}

	public void reset() {

	}

	@Override
	protected void checkSubclass() {
	}

	public void setEnabled(boolean enable) {

	}

	public boolean isEnabled() {
		return true;
	}
}
