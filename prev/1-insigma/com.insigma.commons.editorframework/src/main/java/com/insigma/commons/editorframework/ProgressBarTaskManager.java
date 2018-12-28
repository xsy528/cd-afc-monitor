/* 
 * 日期：2010-11-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.ui.dialog.ProgressBarDialog;

public class ProgressBarTaskManager {

	private ProgressBarDialog progress;

	protected class UIThread implements Runnable {
		Action action;

		public UIThread(Action action) {
			this.action = action;
		}

		public void run() {
			ActionContext context = new ActionContext(action);
			context.setFrameWork(action.getFrameWork());
			action.getHandler().perform(context);
			progress.close();
		}
	}

	public void submit(Action action) {
		Display.getDefault().asyncExec(new UIThread(action));

		progress = new ProgressBarDialog(Display.getDefault().getActiveShell(), SWT.NONE);
		if ((action.getShowProgressText() != null) && (!"".equals(action.getShowProgressText()))) {
			progress.setText(action.getShowProgressText());
		}
		progress.open();

	}
}
