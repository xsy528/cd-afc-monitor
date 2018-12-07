package com.insigma.commons.framework.function.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.view.ActionExecutor;
import com.insigma.commons.framework.view.CompositeFrameWork;
import com.insigma.commons.framework.view.FunctionComposite;

public abstract class FunctionHandler {

	protected Log logger = LogFactory.getLog(getClass());

	public abstract FunctionComposite create(Composite parent, CompositeFrameWork framework, Function function);

	public void load(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer, Function function) {

	}

	protected void performAction(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer,
			Action action) {
		try {
			if (action == null) {
				return;
			}
			logger.info("执行Action " + action.getText());
			Request request = null;
			if (requestGenerator != null) {
				request = requestGenerator.getRequest();
			}
			ActionExecutor actionExecutor = new ActionExecutor(responseDisplayer);
			actionExecutor.execute(action, request);
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	protected void performAction(IRequestGenerator requestGenerator, IResponseDisplayer responseDisplayer,
			Action action, Object value) {
		try {
			logger.info("执行Action " + action.getText());
			Request request = null;
			if (requestGenerator != null) {
				request = requestGenerator.getRequest();
			} else {
				request = new Request();
			}
			request.setValue(value);
			ActionExecutor actionExecutor = new ActionExecutor(responseDisplayer);
			actionExecutor.execute(action, request);
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}
}
