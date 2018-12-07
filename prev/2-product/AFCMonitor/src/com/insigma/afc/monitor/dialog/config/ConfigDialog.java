package com.insigma.afc.monitor.dialog.config;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.view.form.FormEditor;

public class ConfigDialog<T> extends FrameWorkDialog {

	private FormEditor<T> formEditor;

	private IActionHandler actionHandler;

	private IActionHandler restoreHandler;

	private T form;

	public IActionHandler getActionHandler() {
		return actionHandler;
	}

	public void setActionHandler(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	public IActionHandler getRestoreHandler() {
		return restoreHandler;
	}

	public void setRestoreHandler(IActionHandler restoreHandler) {
		this.restoreHandler = restoreHandler;
	}

	public ConfigDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	protected void createAction(List<Action> actions) {
		actions.add(new Action("保存 (&S)", actionHandler));
		if (restoreHandler != null) {
			//			actions.add(new Action("默认 (&R)", restoreHandler));
		}
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());
		formEditor = new FormEditor<T>(parent, SWT.BORDER);
		formEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		formEditor.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		formEditor.setForm(new Form<T>(form, 1));
		parent.layout();
	}

	public void setValue(T form) {
		this.form = form;
		if (formEditor != null) {
			formEditor.setForm(new Form<T>(form, 1));
		}
	}

	public T getValue() {
		return formEditor.getForm().getEntity();
	}
}
