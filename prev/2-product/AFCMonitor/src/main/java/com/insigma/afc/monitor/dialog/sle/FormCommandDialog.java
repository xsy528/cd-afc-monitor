package com.insigma.afc.monitor.dialog.sle;

import java.util.List;

import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.dialog.TreeFormDialog;
import com.insigma.commons.framework.function.form.Form;

public class FormCommandDialog extends TreeFormDialog {

	private Form form;

	private CommandActionHandler actionHandler;

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
		this.setValue(form);
	}

	public CommandActionHandler getActionHandler() {
		return actionHandler;
	}

	public void setActionHandler(CommandActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	public FormCommandDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	public void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", actionHandler));
		actions.add(new CloseAction());
	}
}
