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

public class NewAction extends FunctionAction {

	private FormFunction formFunction;

	private StandardDialogFunction dlgFunction;

	public void setForm(Form form) {
		formFunction.setForm(form);
	}

	public void setDescription(String description) {
		dlgFunction.setDescription(description);
	}

	public NewAction(String text, IActionHandler OkHandler) {
		this(text, null, OkHandler);
	}

	public NewAction(String text, Form form, IActionHandler OkHandler) {

		super(text);
		formFunction = new FormFunction();
		dlgFunction = new StandardDialogFunction(formFunction);
		setDialogFunction(dlgFunction);
		formFunction.setForm(form);
		setTitle(text);
		setImage("/icon.png");
		setText(text);
		setDescription(text);

		dlgFunction.setOpenAction(new Action("", new ActionHandlerAdapter() {
			public Response perform(Request request) {
				Response response = new Response();
				response.setCode(IResponseCode.RESET);
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

		setDialogFunction(dlgFunction);
	}

	public NewAction(String text, Form form) {
		this(text, form, new NewActionHandler());
	}
}
