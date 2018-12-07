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
package com.insigma.commons.framework.view;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.action.FunctionAction;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.StatusPage;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.application.WindowApplication;
import com.insigma.commons.framework.extend.UserModule;
import com.insigma.commons.ui.control.StatusBar;
import com.insigma.commons.ui.widgets.Shell;

public abstract class FrameworkWindow extends Shell implements IResponseDisplayer {

	protected Log logger = LogFactory.getLog(getClass());

	protected UIPlatForm platForm;

	protected StatusBar statusBar;

	protected DialogFunctionHandler dialogFunctionHandler = new DialogFunctionHandler();

	public FrameworkWindow(Display parent, int style) {
		super(parent, style);

	}

	@Override
	public void dispose() {
		if (WindowApplication.canColse()) {
			super.dispose();
		}
	}

	public void checkSubclass() {
	}

	public void open() {
		final Display display = Display.getDefault();
		super.open();
		layout();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
				if (platForm != null && platForm.getStatusPages() != null) {
					for (StatusPage statusPage : platForm.getStatusPages()) {
						statusPage.update();
					}
				}
			}
		}
	}

	protected void createContents() {

		setMaximized(true);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		final Label label = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label.setText("Label");

		statusBar = new StatusBar(this, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 32;
		statusBar.setLayoutData(gridData);
		statusBar.setLayout(new GridLayout());

	}

	public UIPlatForm getPlatForm() {
		return platForm;
	}

	public Composite createComposite(Module module, Composite parent) {
		Composite composite = null;
		if (module instanceof UserModule) {
			UserModule userModule = (UserModule) module;
			if (userModule.getViewComponentBuilder() != null) {
				composite = userModule.getViewComponentBuilder().create(parent);
			} else {
				Class<?> compositeClass = userModule.getComposite();
				try {
					Constructor<?> constructor = compositeClass.getConstructor(Composite.class, int.class);
					Object compositeObj = constructor.newInstance(parent, SWT.BORDER);
					composite = (Composite) (compositeObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			composite = new FrameworkComposite(parent, SWT.BORDER);
			((FrameworkComposite) composite).setModule(module);
		}

		if (module.getModuleListener() != null) {
			module.getModuleListener().create(composite);
		}

		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return composite;
	}

	public void setPlatForm(UIPlatForm platForm) {
		this.platForm = platForm;
		WindowApplication.setUiplatForm(platForm);
		WindowApplication.setFrameworkWindow(this);
	}

	protected void createStatusBar() {
		((GridLayout) statusBar.getLayout()).numColumns = platForm.getStatusPages().size() * 2;
		for (final StatusPage statusPage : platForm.getStatusPages()) {
			Class<?> compositeClass = statusPage.getComposite();
			try {
				Constructor<?> constructor = compositeClass.getConstructor(Composite.class, int.class);
				Object composite = constructor.newInstance(statusBar, SWT.CENTER);

				Control control = (Control) composite;

				if (statusPage.getSelectionAction() != null) {
					control.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent arg0) {
							Action selectionAction = statusPage.getSelectionAction();
							if (selectionAction instanceof FunctionAction) {
								FunctionAction faction = (FunctionAction) selectionAction;
								dialogFunctionHandler.setShell(getShell());
								dialogFunctionHandler.process(faction.getDialogFunction(), null, FrameworkWindow.this);
							} else {
								ActionExecutor executor = new ActionExecutor(FrameworkWindow.this);
								executor.execute(selectionAction, null);
							}
						}
					});
				}

				statusPage.setControl(control);

				GridData gridData = new GridData(GridData.FILL_VERTICAL);

				if (statusPage.getWidth() < 0) {
					gridData.grabExcessHorizontalSpace = true;
				} else if (statusPage.getWidth() == 0) {
					gridData.widthHint = 100;
				} else {
					gridData.widthHint = statusPage.getWidth();
				}

				gridData.verticalAlignment = SWT.CENTER;
				((Control) composite).setLayoutData(gridData);

				final Label label = new Label(statusBar, SWT.VERTICAL | SWT.SEPARATOR);
				label.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		statusBar.layout();

	}

	public void refresh() {

	}

	public void reset() {

	}

	public void setResponse(Response response) {

	}
}
