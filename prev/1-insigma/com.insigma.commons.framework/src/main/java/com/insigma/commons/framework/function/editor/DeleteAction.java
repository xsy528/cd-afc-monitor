package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IActionHandler;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.action.FunctionAction;
import com.insigma.commons.framework.function.dialog.MessageDialogFunction;
import com.insigma.commons.framework.function.search.SearchRequest;

public class DeleteAction extends FunctionAction {

	public DeleteAction(String text, IActionHandler OkHandler) {
		super(text);
		setActionHandler(new ActionHandlerAdapter() {
			public boolean prepare(Request request) {
				SearchRequest req = (SearchRequest) request;
				if (req.getSelection() != null && req.getSelection().size() > 0) {
					return true;
				}
				return false;
			}
		});
		MessageDialogFunction messageDialogFunction = new MessageDialogFunction("操作警告", "确定要删除选中信息吗?");
		setDialogFunction(messageDialogFunction);

		messageDialogFunction.setCloseAction(new Action(text, OkHandler));
	}

	public DeleteAction(String text) {
		this(text, new DeleteActionHandler());
	}
}
