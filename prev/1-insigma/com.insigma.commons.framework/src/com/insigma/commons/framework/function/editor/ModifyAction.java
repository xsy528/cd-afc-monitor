package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IActionHandler;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.action.CloseAction;
import com.insigma.commons.framework.action.FunctionAction;
import com.insigma.commons.framework.action.ResetAction;
import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.function.search.SearchRequest;

public class ModifyAction extends FunctionAction {

	private FormFunction formFunction;

	private StandardDialogFunction dlgFunction;

	public void setForm(Form form) {
		formFunction.setForm(form);
	}

	public void setDescription(String description) {
		dlgFunction.setDescription(description);
	}

	public FormFunction getFormFunction() {
		return formFunction;
	}

	public StandardDialogFunction getDlgFunction() {
		return dlgFunction;
	}

	public ModifyAction(String text, IActionHandler OkHandler) {
		super(text);
		formFunction = new FormFunction();
		dlgFunction = new StandardDialogFunction(formFunction);
		setDialogFunction(dlgFunction);
		setTitle(text);
		setImage("/icon.png");
		setText(text);
		setDescription(text);

		setActionHandler(new ActionHandlerAdapter() {
			public boolean prepare(Request request) {
				SearchRequest req = (SearchRequest) request;
				if (req.getSelection() != null && req.getSelection().size() == 1) {
					return true;
				}
				return false;
			}
		});

		dlgFunction.setOpenAction(new Action("", new ActionHandlerAdapter() {
			public Response perform(Request request) {
				SearchRequest req = (SearchRequest) request;
				Response response = new Response();
				response.setValue(req.getSelection().get(0));
				return response;
			}
		}));

		dlgFunction.setCloseAction(new Action("", new ActionHandlerAdapter() {
			public Response perform(Request request) {
				Response response = new Response();
				response.setCode(IResponseCode.REFRESH);
				return response;
			}
		}));

		dlgFunction.addAction("确定", OkHandler);

		dlgFunction.addAction(new ResetAction());
		dlgFunction.addAction(new CloseAction());
	}

	public ModifyAction(String text) {
		this(text, new ModifyActionHandler());
	}
}
