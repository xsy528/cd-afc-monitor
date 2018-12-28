package com.insigma.commons.framework.view;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IFunctionAware;
import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.extend.UserFunction;
import com.insigma.commons.framework.function.ActionFunction;
import com.insigma.commons.framework.function.dialog.DialogFunction;
import com.insigma.commons.framework.function.handler.FunctionHandler;
import com.insigma.commons.framework.function.handler.FunctionHanlderManager;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class CompositeFrameWork extends EnhanceComposite implements IViewComponent {

	protected Log logger = LogFactory.getLog(getClass());

	protected StackLayout stackLayout;

	protected EnhanceComposite mainComposite;

	protected Map<Integer, Composite> compositeMap = new HashMap<Integer, Composite>();

	protected DialogFunctionHandler dialogFunctionHandler = new DialogFunctionHandler();

	public void validate(Request request) {

	}

	public void createMainComposite() {
		mainComposite = new EnhanceComposite(this, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		stackLayout = new StackLayout();
		mainComposite.setLayout(stackLayout);
	}

	public CompositeFrameWork(Composite parent, int style) {
		super(parent, style);

	}

	public void handleFunction(Function function) {

		if (function.getRelateFunction() != null) {
			handleFunction(function.getRelateFunction());
		}

		if (function instanceof DialogFunction) {
			Request request = null;
			if (stackLayout.topControl instanceof IRequestGenerator) {
				request = ((IRequestGenerator) stackLayout.topControl).getRequest();
			}
			IResponseDisplayer responseDisplayer = null;
			if (stackLayout.topControl instanceof IResponseDisplayer) {
				responseDisplayer = ((IResponseDisplayer) stackLayout.topControl);
			}
			dialogFunctionHandler.setShell(getShell());
			dialogFunctionHandler.process((DialogFunction) function, request, responseDisplayer);

			return;
		}

		if (function instanceof ActionFunction) {
			handleActionFunction((ActionFunction) function);
			return;
		}
		if (function instanceof UserFunction) {
			handleUserFunction((UserFunction) function);
			return;
		}

		FunctionHandler functionHandler = FunctionHanlderManager.getFunctionHandler(function);
		if (functionHandler != null) {

			if (compositeMap.containsKey(function.hashCode())) {
				stackLayout.topControl = compositeMap.get(function.hashCode());
			} else {
				FunctionComposite composite = functionHandler.create(mainComposite, this, function);
				composite.setFrameworkComposite(this);
				function.setViewComponent(composite);
				stackLayout.topControl = composite;
				compositeMap.put(function.hashCode(), composite);
			}

			IRequestGenerator requestGenerator = null;
			if (stackLayout.topControl instanceof IRequestGenerator) {
				requestGenerator = ((IRequestGenerator) stackLayout.topControl);
			}

			IResponseDisplayer responseDisplayer = null;
			if (stackLayout.topControl instanceof IResponseDisplayer) {
				responseDisplayer = ((IResponseDisplayer) stackLayout.topControl);
			}

			functionHandler.load(requestGenerator, responseDisplayer, function);
		}

		mainComposite.layout();

	}

	protected void performAction(Action action) {
		try {
			if (action == null) {
				return;
			}
			logger.info("执行Action " + action.getText());
			Request request = null;
			IResponseDisplayer responseDisplayer = null;
			if (stackLayout.topControl instanceof IRequestGenerator) {
				request = ((IRequestGenerator) stackLayout.topControl).getRequest();
			}
			if (stackLayout.topControl instanceof IResponseDisplayer) {
				responseDisplayer = ((IResponseDisplayer) stackLayout.topControl);
			}
			ActionExecutor actionExecutor = new ActionExecutor(responseDisplayer);
			actionExecutor.execute(action, request);
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	/**
	 * 处理后台功能
	 * 
	 * @param function
	 */
	protected void handleActionFunction(ActionFunction function) {
		if (function.getAction() != null) {
			performAction(function.getAction());
		}
	}

	/**
	 * 处理自定义的功能
	 * 
	 * @param function
	 */
	protected void handleUserFunction(UserFunction function) {
		if (compositeMap.containsKey(function.hashCode())) {
			Composite composite = compositeMap.get(function.hashCode());
			if (composite instanceof EnhanceComposite) {
				((EnhanceComposite) composite).active(function);
			}
			stackLayout.topControl = composite;
		} else {
			if (function.getViewComponentBuilder() != null) {
				Composite composite = function.getViewComponentBuilder().create(mainComposite);
				stackLayout.topControl = composite;
				if (composite instanceof IViewComponent) {
					function.setViewComponent((IViewComponent) composite);
				}
				if (composite instanceof IFunctionAware) {
					((IFunctionAware) composite).setFunction(function);
				}
				compositeMap.put(function.hashCode(), composite);
			} else {
				Class<?> compositeClass = function.getComposite();
				try {
					Constructor<?> constructor = compositeClass.getConstructor(Composite.class, int.class);
					Object composite = constructor.newInstance(mainComposite, SWT.NONE);
					stackLayout.topControl = (Composite) composite;
					if (composite instanceof IFunctionAware) {
						((IFunctionAware) composite).setFunction(function);
					}
					compositeMap.put(function.hashCode(), (Composite) composite);
				} catch (Exception e) {
					logger.error("Action执行失败", e);
				}
			}
		}
		mainComposite.layout();
	}
}