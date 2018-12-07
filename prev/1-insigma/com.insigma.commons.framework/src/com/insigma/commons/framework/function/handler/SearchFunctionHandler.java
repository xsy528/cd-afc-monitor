package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.search.SearchFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.search.FormTableComposite;

public class SearchFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		FormTableComposite composite = new FormTableComposite(parent, SWT.NONE);
		composite.setFunction((SearchFunction) function);
		return composite;
	}
}
