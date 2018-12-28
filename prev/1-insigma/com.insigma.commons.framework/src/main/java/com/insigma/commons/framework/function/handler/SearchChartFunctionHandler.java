package com.insigma.commons.framework.function.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.chart.ChartFunction;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.chart.ChartComposite;

public class SearchChartFunctionHandler extends FunctionHandler {

	public FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function) {
		ChartComposite composite = new ChartComposite(parent, SWT.NONE);
		composite.setFunction((ChartFunction) function);
		return composite;
	}
}
