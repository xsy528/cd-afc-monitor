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

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;

public class SCViewAction extends Action {

	private int direction;

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public SCViewAction() {
		super("视图");
		setImage("/com/insigma/commons/ui/function/view_top_bottom.png");
		//		getItems().add(new SCViewAction(SWT.LEFT));
		getItems().add(new SCViewAction(SWT.RIGHT));
		getItems().add(new SCViewAction(SWT.BOTTOM));
	}

	public SCViewAction(int direction) {
		super("视图");
		this.direction = direction;
		switch (direction) {
		//		case SWT.LEFT:
		//			setName("左侧视图");
		//			setChecked(false);
		//			break;
		case SWT.BOTTOM:
			setName("下侧视图");
			setChecked(true);
			break;
		case SWT.RIGHT:
			setName("右侧视图");
			setChecked(false);
			break;
		}
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				SCViewAction.this.setChecked(!SCViewAction.this.isChecked());
				getFrameWork().toggleView(getDirection(), false);
			}
		};
		setHandler(handler);
	}
}
