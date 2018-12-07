package com.insigma.commons.editorframework.iframework;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.handler.FunctionHandler;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;

public class MapFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		MapViewComposite composite = new MapViewComposite(parent, SWT.NONE);
		composite.setFunction((MapFunction) function);
		return composite;
	}
}
