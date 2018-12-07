/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Shell;

public class FontDialog extends org.eclipse.swt.widgets.FontDialog {

	public static FontData fontData = null;

	public FontDialog(Shell arg0) {
		super(arg0);
	}

	public FontDialog(Shell arg0, int arg1) {
		super(arg0, arg1);
	}

	@SuppressWarnings("deprecation")
	public FontData open() {
		if (fontData != null) {
			this.setFontData(fontData);
		}
		FontData font = super.open();
		fontData = font;
		return font;
	}

	protected void checkSubclass() {
	}

}
