/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.swtdesigner.SWTResourceManager;

public class BackGoundAction extends MapAction {

	public BackGoundAction() {
		setName("背景");
		setImage("/com/insigma/commons/editorframework/images/backgroundimg.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
				String fileName = dialog.open();
				if (getMap() != null && fileName != null) {
					getMap().setBackgroundImage(SWTResourceManager.getImage(fileName));
				}
			}
		};
		setHandler(handler);
	}
}
