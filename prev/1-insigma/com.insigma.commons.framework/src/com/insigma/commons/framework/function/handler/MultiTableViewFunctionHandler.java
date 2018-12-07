package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.table.MultiTableViewFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.table.MultiTableViewComposite;

public class MultiTableViewFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		MultiTableViewComposite composite = new MultiTableViewComposite(parent, SWT.NONE);
		composite.setFunction((MultiTableViewFunction) function);
		return composite;

	}

	public void load(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer, Function function) {
		MultiTableViewFunction func = (MultiTableViewFunction) function;
		if (func.getLoadAction() != null) {
			performAction(requestGenerator, responseDisplayer, func.getLoadAction());
		}
	}
}
