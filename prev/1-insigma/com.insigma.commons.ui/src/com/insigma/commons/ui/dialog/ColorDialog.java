/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;

/**
 * Ticket: 
 * @author  jiangxf
 *
 */
public class ColorDialog extends org.eclipse.swt.widgets.ColorDialog {

	private static RGB historyRGB = null;

	public ColorDialog(Shell arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ColorDialog(Shell arg0, int arg1) {
		super(arg0, arg1);
	}

	@Override
	public RGB open() {

		if (historyRGB != null) {

			this.setRGB(historyRGB);
		}
		RGB rgb = super.open();

		historyRGB = rgb;

		return rgb;
	}

	public void setRGB(int red, int green, int blue) {
		RGB rgb = new RGB(red, green, blue);
		super.setRGB(rgb);
	}

	@Override
	protected void checkSubclass() {
	}

}
