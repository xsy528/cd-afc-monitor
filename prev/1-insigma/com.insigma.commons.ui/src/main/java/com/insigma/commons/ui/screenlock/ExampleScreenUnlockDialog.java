/* 
 * 日期：2012-12-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.screenlock;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Shell;

/**
 * Ticket: 锁屏的例子
 * 
 * @author 郑淦
 */
public class ExampleScreenUnlockDialog extends ScreenUnlockDialog {

	public ExampleScreenUnlockDialog(Shell parent) {
		super(parent);
	}

	@Override
	protected boolean hasPermissionToUnlock(String username, String password) {
		return true;
	}

	@Override
	protected boolean isUserNameAndPwdRight(String username, String password) {
		return true;
	}

	@Override
	protected int getRestartHookCode() {
		return OS.VK_F10;
	}

}
