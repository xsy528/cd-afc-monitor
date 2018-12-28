/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.IDialog;

/**
 * Ticket: 
 * @author  jiangxf
 *
 */
public class FileDialog extends org.eclipse.swt.widgets.FileDialog implements IDialog {

	private static String filterPath = null;

	public FileDialog(Shell arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FileDialog(Shell arg0, int arg1) {
		super(arg0, arg1);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public String open() {
		if (filterPath != null) {
			this.setFilterPath(filterPath);
		}
		String fileName = super.open();
		filterPath = fileName;
		return fileName;
	}

}
