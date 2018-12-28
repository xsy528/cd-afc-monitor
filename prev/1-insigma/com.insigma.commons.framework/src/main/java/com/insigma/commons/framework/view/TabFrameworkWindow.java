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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.application.WindowApplication;
import com.insigma.commons.ui.control.StatusBar;
import com.insigma.commons.ui.widgets.CTabFolder;
import com.insigma.commons.ui.widgets.CTabItem;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.swtdesigner.SWTResourceManager;

public class TabFrameworkWindow extends FrameworkWindow {

	private CTabFolder tabFolder;

	public TabFrameworkWindow(Display parent, int style) {
		super(parent, style);
	}

	protected void createContents() {

		setMaximized(true);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		createTabFolder();

		final Label label = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label.setText("Label");

		statusBar = new StatusBar(this, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 32;
		statusBar.setLayoutData(gridData);
		statusBar.setLayout(new GridLayout());

	}

	private void createTabFolder() {
		tabFolder = new CTabFolder(this, SWT.MULTI | SWT.BORDER);
		final GridData gdTabFolder = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolder.setLayoutData(gdTabFolder);
		tabFolder.setTabHeight(32);
		tabFolder.setSimple(false);
		addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent arg0) {
				if (platForm.getModules() != null) {
					for (Module functiongroup : platForm.getModules()) {
						if (functiongroup.getModuleListener() != null) {
							functiongroup.getModuleListener().end();
						}
					}
				}
			}
		});
	}

	public void setPlatForm(UIPlatForm platForm) {

		this.platForm = platForm;
		WindowApplication.setUiplatForm(platForm);
		WindowApplication.setFrameworkWindow(this);

		createContents();

		if (platForm.getActions() != null) {
			ToolBar topComposite = new ToolBar(tabFolder, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
			tabFolder.setTopRight(topComposite);
			for (final Action action : platForm.getActions()) {
				ToolItem item = new ToolItem(topComposite, SWT.PUSH);
				if (action.getImage() != null) {
					Image image = SWTResourceManager.getImage(action.getClass(), action.getImage());
					if (image != null && image.getImageData().alphaData != null) {
						item.setImage(image);
					}
				}
				item.setText(action.getText());
				item.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent event) {
						ActionExecutor executor = new ActionExecutor(TabFrameworkWindow.this);
						Request request = new Request();
						request.setAction(action);
						executor.execute(action, request);
					}

					public void widgetDefaultSelected(SelectionEvent event) {
					}
				});
			}
		}
		if (platForm.getStatusPages() != null) {
			createStatusBar();
		}
		List<Module> notDefinedModules = new ArrayList<Module>();
		if (platForm.getModules() != null) {
			for (int i = 0; i < platForm.getModules().size(); i++) {
				final Module module = platForm.getModules().get(i);

				String fid = module.getFunctionID();
				if (fid == null) {
					logger.warn("Module:[" + module.getText() + "]没有定义functionId，默认初始化该module");
					notDefinedModules.add(module);
					// continue;
				} else {
					boolean hasAuth = Application.getUser().hasModule(fid);
					if (!hasAuth) {
						logger.debug("用户：[" + Application.getUser().getUserName() + "]没有module:" + module
								+ "的权限,忽略该module的初始化");
						continue;
					}
				}

				if (i == 0 || module.isNeedInit()) {
					CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
					tabItem.setText(module.getText());
					tabItem.setData("insigma_module", module);
					Composite composite = createComposite(module, tabFolder);
					tabItem.setControl(composite);
					tabItem.setInited(true);
				} else {
					CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
					tabItem.setText(module.getText());
					tabItem.setData(module);
					tabItem.setData("insigma_module", module);
				}

			}
			tabFolder.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					CTabItem sitem = (CTabItem) e.item;
					Module module = (Module) sitem.getData("insigma_module");
					if (!sitem.isInited()) {
						Composite composite = createComposite(module, tabFolder);
						sitem.setControl(composite);
						sitem.setInited(true);
						tabFolder.layout(true);
					}
					if (module.getModuleListener() != null) {
						module.getModuleListener().active();
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
		if (notDefinedModules.size() > 0) {
			// MessageForm.Message("以下模块的functionId没有定义，默认不显示", "以下模块的functionId没有定义，默认不显示，请尽快定义\n"
			// + StringUtils.collectionToDelimitedString(notDefinedModules, "\n"),
			// SWT.ICON_ERROR);
		}
	}

	public void setResponse(Response response) {
		if ((response.getCode() & IResponseCode.DIALOG_CLOSE) != 0) {
			this.close();
		}
	}
}
