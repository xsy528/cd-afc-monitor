package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.table.KTableFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.table.ObjectKTableViewer;

public class KTableViewFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		ObjectKTableViewer composite = new ObjectKTableViewer(parent, SWT.NONE);
		final KTableFunction kfun = (KTableFunction) function;
		composite.setFunction(kfun, kfun.fixedRows, kfun.fixedColumns);
		return composite;

	}

	public void load(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer, Function function) {
		KTableFunction func = (KTableFunction) function;
		if (func.getLoadAction() != null) {
			performAction(requestGenerator, responseDisplayer, func.getLoadAction());
		}

		if (func.getActiveAction() != null) {
			performAction(requestGenerator, responseDisplayer, func.getActiveAction());
		}
	}
}
