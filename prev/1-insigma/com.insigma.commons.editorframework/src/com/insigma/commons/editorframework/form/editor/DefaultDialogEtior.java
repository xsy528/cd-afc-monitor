/* 
 * 日期：2016-7-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.dialog.DefaultEditorDialog;

public class DefaultDialogEtior implements IDialogEditor {

	@Override
	public Object editor(Object value, Field field) {
		// 确认按钮 
		return editor(value, field, null);
	}

	@Override
	public Object editor(Object value, Field field, Object compareValue) {
		// 确认按钮 
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ConfirmationAction());

		// 编辑框
		DefaultEditorDialog dialog = new DefaultEditorDialog(Display.getCurrent().getActiveShell(), SWT.NONE);
		dialog.setLocalPoint(getLocalPoint());
		dialog.setActions(actions);
		dialog.setField(field);
		dialog.setCompareValue(compareValue);
		dialog.setValue(value);

		return dialog.open();
	}

	/**
	 * 获取新弹出框位置，根据活动窗口自动偏差20
	 * @return
	 */
	private Point getLocalPoint() {
		Point result = null;

		if (Display.getCurrent().getActiveShell().getLocation().x > 100) {
			result = new Point(Display.getCurrent().getActiveShell().getLocation().x + 20,
					Display.getCurrent().getActiveShell().getLocation().y + 20);
		}

		return result;
	}

	public class ConfirmationAction extends Action {
		public ConfirmationAction() {
			super("确认 (&C)");
			this.setImage("/com/insigma/commons/editorframework/images/export.png");
			IActionHandler handler = new ActionHandlerAdapter() {
				public void perform(ActionContext context) {
					context.setData(ActionContext.RESULT_DATA, context.getData());
					Display.getCurrent().getActiveShell().close();
				}
			};
			setHandler(handler);
		}
	}
}
