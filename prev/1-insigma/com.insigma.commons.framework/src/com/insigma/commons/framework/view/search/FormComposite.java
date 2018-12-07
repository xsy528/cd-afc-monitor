/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @history       
 */
package com.insigma.commons.framework.view.search;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.function.form.FormRequest;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.form.FormEditor;

public class FormComposite<T> extends FunctionComposite {

	protected FormFunction<T> function;

	protected FormEditor<T> formEditor;

	protected Composite compositeAction;

	public FormComposite(Composite parent, int style) {
		super(parent, style);
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(FormFunction<T> function) {
		if (this.function != null) {
			return;
		}
		context.setFunction(function);
		this.function = function;

		formEditor = new FormEditor<T>(this, SWT.BORDER);
		GridData pdata = new GridData(GridData.FILL_BOTH);
		formEditor.setLayoutData(pdata);
		formEditor.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		if (function.getForm() != null) {
			formEditor.setForm(function.getForm());
			formEditor.layout();
		}

		if (function.getActions() != null && function.getActions().size() > 0) {
			buildButtonBar(this, function.getActions());
		}
	}

	public void setForm(Form<T> form) {
		function.setForm(form);
		formEditor.setForm(form);
		formEditor.layout();
	}

	public Request getRequest() {
		FormRequest<T> request = new FormRequest<T>();
		request.setContext(context);
		Form<T> form = formEditor.getForm();
		request.setForm(form);
		return request;
	}

	public void setResponse(Response response) {
		if (response != null && response.getValue() != null && response.getValue() instanceof Form) {
			formEditor.setForm((Form) response.getValue());
		}
	}
}
