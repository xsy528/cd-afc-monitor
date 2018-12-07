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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.view.ActionExecutor;
import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.IDialog;
import com.insigma.commons.ui.control.DialogHeaderComposite;
import com.swtdesigner.SWTResourceManager;

public abstract class FrameworkDialog extends Dialog implements IDialog, IResponseDisplayer {

	protected Log logger = LogFactory.getLog(getClass());

	protected Object result;

	protected Shell shell;

	protected StandardDialogFunction function;

	protected IViewComponent viewComponent;

	protected Request openRequest;

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	protected void performAction(Action action, Request request) {
		try {
			ActionExecutor actionExecutor = new ActionExecutor(this);
			actionExecutor.execute(action, request);
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	public FrameworkDialog(Shell parent, int style) {
		super(parent, style);
	}

	public FrameworkDialog(Shell parent) {
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
		while (!shell.isDisposed() && !display.isDisposed()) {
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

		shell.setText(function.getText() == null ? "" : function.getText());

		if (function.getImage() != null) {
			shell.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
		}

		final DialogHeaderComposite insigmaLogoComposite = new DialogHeaderComposite(shell, SWT.NONE);
		final GridData gd_insigmaLogoComposite = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_insigmaLogoComposite.heightHint = 70;
		insigmaLogoComposite.setLayoutData(gd_insigmaLogoComposite);
		insigmaLogoComposite.setTitle(function.getTitle() == null ? "" : function.getTitle());
		insigmaLogoComposite.setDescription(function.getDescription() == null ? "" : function.getDescription());

		viewComponent = createComposite();
		Control control = (Control) viewComponent;
		control.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (function.getActions() != null) {

			final Label label = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
			label.setText("Label");

			final Composite composite = new Composite(shell, SWT.NONE);
			final GridLayout gridLayout_1 = new GridLayout();
			gridLayout_1.verticalSpacing = 0;
			gridLayout_1.marginHeight = 0;
			gridLayout_1.horizontalSpacing = 20;
			gridLayout_1.marginWidth = 20;
			gridLayout_1.marginTop = 15;
			gridLayout_1.numColumns = function.getActions().size();
			composite.setLayout(gridLayout_1);

			final GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false);
			gd_composite.heightHint = 60;
			composite.setLayoutData(gd_composite);

			for (Action action : function.getActions()) {
				final Button button = new Button(composite, SWT.NONE);
				final GridData gd_btnClose = new GridData(SWT.FILL, SWT.FILL, false, false);
				gd_btnClose.heightHint = 30;
				gd_btnClose.widthHint = action.getLen();
				button.setLayoutData(gd_btnClose);
				button.setData(action);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if ((e.widget.getData() != null) && (e.widget.getData() instanceof Action)) {
							Action ac = (Action) e.widget.getData();
							Request request = null;
							if (viewComponent instanceof IRequestGenerator) {
								request = ((IRequestGenerator) viewComponent).getRequest();
								if (null != request) {
									request.setParent(openRequest);
								}
							}

							performAction(ac, request);
						}
					}
				});
				button.setText(action.getText());
			}
		}

	}

	protected abstract IViewComponent createComposite();

	public StandardDialogFunction getFunction() {
		return function;
	}

	public void setFunction(StandardDialogFunction function) {
		this.function = function;
	}

	public Request getOpenRequest() {
		return openRequest;
	}

	public void setOpenRequest(Request openRequest) {
		this.openRequest = openRequest;
	}

	public void setResponse(Response response) {
		if (viewComponent instanceof IResponseDisplayer) {
			IResponseDisplayer responseDisplayer = ((IResponseDisplayer) viewComponent);
			responseDisplayer.setResponse(response);
		}
		if ((response.getCode() & IResponseCode.DIALOG_CLOSE) != 0) {
			Request request = null;
			if (viewComponent instanceof IRequestGenerator) {
				request = ((IRequestGenerator) viewComponent).getRequest();
			}
			//            if (function.getCloseAction() != null) {
			//                performAction(function.getCloseAction(), request);
			//            }
			result = request;
			if (!shell.isDisposed()) {
				shell.close();
			}
		}
	}

	public void refresh() {

	}

	public void reset() {

	}
}
