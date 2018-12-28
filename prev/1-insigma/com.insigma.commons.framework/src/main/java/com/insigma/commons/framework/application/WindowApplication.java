/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-6
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.application;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.view.FrameworkWindow;
import com.insigma.commons.framework.view.dialog.confirm.ExitConfirmDialog;
import com.insigma.commons.ui.MessageForm;

public class WindowApplication extends Application {

	protected static UIPlatForm uiplatForm;
	protected static FrameworkWindow frameworkWindow;

	static List<WindowCloseListener> listeners = new LinkedList<WindowCloseListener>();

	public static UIPlatForm getUiplatForm() {
		return uiplatForm;
	}

	public static void setUiplatForm(UIPlatForm uiplatForm) {
		WindowApplication.uiplatForm = uiplatForm;
	}

	public static FrameworkWindow getFrameworkWindow() {
		return frameworkWindow;
	}

	public static void setFrameworkWindow(FrameworkWindow frameworkWindow) {
		WindowApplication.frameworkWindow = frameworkWindow;
	}

	public static void addWindowCloseListener(WindowCloseListener listener) {
		listeners.add(listener);
	}

	public static void removeWindowCloseListener(WindowCloseListener listener) {
		listeners.remove(listener);
	}

	public static List<WindowCloseListener> getWindowCloseListeners() {
		return listeners;
	}

	public static boolean canColse() {
		List<WindowCloseListener> listeners = WindowApplication.getWindowCloseListeners();
		if (listeners.size() == 1) {
			WindowCloseListener windowCloseListener = listeners.get(0);
			if (windowCloseListener.prepare()) {
				int ok = MessageForm.Query("确定关闭", windowCloseListener.getName(),
						SWT.YES | SWT.NO | SWT.CANCEL | SWT.ICON_QUESTION);
				if (ok == SWT.YES) {
					windowCloseListener.beforeClose();
				} else if (ok == SWT.CANCEL) {
					return false;
				}
			}
			windowCloseListener.afterClose();
			listeners.clear();
		} else {
			boolean changed = false;
			for (WindowCloseListener windowCloseListener : listeners) {
				if (windowCloseListener.prepare()) {
					changed = true;
					break;
				}
			}
			if (changed) {
				ExitConfirmDialog confirmDialog = new ExitConfirmDialog(Display.getDefault().getActiveShell(),
						SWT.None);
				confirmDialog.setObjectList(listeners);
				Object open = confirmDialog.open();
				if (open == null) {
					return false;
				}
			}

			for (WindowCloseListener windowCloseListener : listeners) {
				windowCloseListener.afterClose();
			}
			listeners.clear();
		}

		return true;
	}
}
