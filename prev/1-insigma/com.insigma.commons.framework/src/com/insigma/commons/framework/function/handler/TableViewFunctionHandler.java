package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.table.TableViewComposite;

public class TableViewFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		TableViewComposite composite = new TableViewComposite(parent, SWT.NONE);
		composite.setFunction((TableViewFunction) function);
		return composite;

	}

	public void load(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer, Function function) {
		TableViewFunction func = (TableViewFunction) function;
		if (func.getLoadAction() != null) {
			performAction(requestGenerator, responseDisplayer, func.getLoadAction());
		}

		if (func.getActiveAction() != null) {
			performAction(requestGenerator, responseDisplayer, func.getActiveAction());
		}
	}
}
