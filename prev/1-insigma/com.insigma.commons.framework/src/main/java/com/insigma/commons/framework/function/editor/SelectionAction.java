package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IActionHandler;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.action.FunctionAction;
import com.insigma.commons.framework.function.dialog.MessageDialogFunction;
import com.insigma.commons.framework.function.search.SearchRequest;

public class SelectionAction extends FunctionAction {

	private MessageDialogFunction messageDialogFunction;

	public SelectionAction(String text, String description) {
		super(text);
		super.setActionHandler(new ActionHandlerAdapter() {
			public boolean prepare(Request request) {
				SearchRequest req = (SearchRequest) request;
				if (req.getSelection() != null && req.getSelection().size() > 0) {
					return true;
				}
				return false;
			}
		});
		messageDialogFunction = new MessageDialogFunction("操作警告", description);
		setDialogFunction(messageDialogFunction);
	}

	public void setActionHandler(IActionHandler handler) {
		messageDialogFunction.setCloseAction(new Action("", handler));
	}
}
