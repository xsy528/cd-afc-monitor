package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.ConsoleFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.ConsoleFunctionComposite;
import com.insigma.commons.framework.view.FunctionComposite;

public class ConsoleFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		ConsoleFunctionComposite composite = new ConsoleFunctionComposite(parent, SWT.NONE);
		composite.setFunction((ConsoleFunction) function);
		return composite;
	}
}
