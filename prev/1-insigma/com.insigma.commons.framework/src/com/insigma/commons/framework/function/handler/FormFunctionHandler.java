package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.search.FormComposite;

public class FormFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		FormComposite composite = new FormComposite(parent, SWT.NONE);
		composite.setFunction((FormFunction) function);
		return composite;
	}
}
