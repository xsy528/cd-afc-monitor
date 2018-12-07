/**
 * iFrameWork 框架
 *
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司
 * @history
 */
package com.insigma.commons.framework.view.dialog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.dialog.ModuleDialogFunction;
import com.insigma.commons.framework.view.ActionExecutor;
import com.insigma.commons.framework.view.FrameworkComposite;
import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.IDialog;
import com.swtdesigner.SWTResourceManager;

public class ModuleDialog extends Dialog implements IDialog, IResponseDisplayer {

	protected Log logger = LogFactory.getLog(getClass());

	protected Object result;

	protected Shell shell;

	protected ModuleDialogFunction function;

	protected Request openRequest;

	private FrameworkComposite frameworkComposite;

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	protected void performAction(Action action, Request request) {
		try {
			logger.info("执行:" + action.getText());
			ActionExecutor actionExecutor = new ActionExecutor(this);
			actionExecutor.execute(action, request);
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	public ModuleDialog(Shell parent, int style) {
		super(parent, style);
	}

	public ModuleDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	public Object open() {

		createContents();

		if (function.getOpenAction() != null) {
			performAction(function.getOpenAction(), openRequest);
		}

		if ((function.getWidth() != 0) && (function.getHeight() != 0)) {
			shell.setSize(function.getWidth(), function.getHeight());
		}
		shell.setLocation(FormUtil.getCenterLocation(shell.getSize().x, shell.getSize().y));

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		shell.setLayout(gridLayout);

		shell.setText(function.getTitle() == null ? "" : function.getTitle());

		if (function.getImage() != null) {
			shell.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
		}

		frameworkComposite = new FrameworkComposite(shell, SWT.NONE);
		frameworkComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		frameworkComposite.setModule(function.getModule());
	}

	public ModuleDialogFunction getFunction() {
		return function;
	}

	public void setFunction(ModuleDialogFunction function) {
		this.function = function;
	}

	public Request getOpenRequest() {
		return openRequest;
	}

	public void setOpenRequest(Request openRequest) {
		this.openRequest = openRequest;
	}

	public void setResponse(Response response) {
	}

	public void refresh() {
	}

	public void reset() {
	}
}
