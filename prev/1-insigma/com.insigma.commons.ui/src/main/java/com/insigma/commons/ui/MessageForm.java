/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageForm {

	private static Log logger = LogFactory.getLog(MessageForm.class);

	static private int _message(final String title, final String message, final int style) {
		if (message == null) {
			return 0;
		}
		if (Display.getDefault().getShells().length == 0) {
			return 0;
		}
		Shell shell = Display.getDefault().getActiveShell();
		if (shell == null || shell.isDisposed()) {
			shell = new Shell(Display.getDefault(), SWT.APPLICATION_MODAL | SWT.ON_TOP);
		}
		final MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setMessage(message);
		messageBox.setText(title);
		return messageBox.open();
	}

	/**
	 * 获取Exception中的中文信息
	 * 
	 * @param ex
	 * @return
	 */
	public static String getMessage(Throwable ex) {
		logger.error("异常堆栈信息", ex);
		String result = null;
		try {
			result = trunString(ex.getMessage());
		} catch (Exception e) {
			result = "获取异常信息错误。";
		}
		return result;
	}

	private static String trunString(String str) {
		if (str == null) {
			return "系统异常";
		}
		String[] s = str.split("。");
		String target = "";
		for (String string : s) {
			if (string.length() > 0) {
				int i = string.charAt(0);
				int maxcha = '\u9fa5';
				int mincha = '\u4e00';
				if (i < maxcha && i > mincha) {
					target += string + "。";
				}
			} else {
				return "系统异常";
			}
		}
		if (target.equals("") && s != null && s.length > 0) {
			target = s[0];
		}
		return target;
	}

	static public void Message(String title, Throwable e) {
		Message(title, getMessage(e), SWT.ICON_ERROR);
	}

	static public void Message(Throwable e) {
		Message("错误信息", getMessage(e), SWT.ICON_ERROR);
	}

	static public void Message(final String message) {
		Message("提示信息", message, SWT.ICON_INFORMATION);
	}

	static public void Message(final String title, final String message) {
		Message(title, message, SWT.ICON_INFORMATION);
	}

	private static class MessageRunnable implements Runnable {

		String title;

		String message;

		int style;

		int result;

		public MessageRunnable(String title, String message, int style) {
			this.title = title;
			this.message = message;
			this.style = style;
		};

		public void run() {
			result = _message(title, message, style);
		}

		public int getResult() {
			return result;
		}
	}

	static public int Message(final String title, final String message, final int style) {

		// 判断当前线程是否为UI线程
		if (Display.getDefault().getThread().getId() == Thread.currentThread().getId()) {
			return _message(title, message, style);
		} else {
			MessageRunnable runnable = new MessageRunnable(title, message, style);
			Display.getDefault().syncExec(runnable);
			return runnable.getResult();
		}
	}

	static public int Query(final String title, final String message) {
		return Message(title, message, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
	}

	static public int Query(final String title, final String message, int style) {
		if (Display.getDefault().getThread().getId() == Thread.currentThread().getId()) {
			return _message(title, message, style);
		}
		return -1;
	}

}
