/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.IDialog;

/**
 * Ticket: 
 * @author  jiangxf
 *
 */
public class DirectoryDialog extends org.eclipse.swt.widgets.DirectoryDialog implements IDialog {

	private static String historyPath = null;

	public DirectoryDialog(Shell arg0) {
		super(arg0, SWT.NONE);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DirectoryDialog(Shell arg0, int arg1) {
		super(arg0, arg1);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public String open() {

		if (historyPath != null) {
			this.setFilterPath(historyPath);
		}
		String path = super.open();

		historyPath = path;

		return path;
	}

}