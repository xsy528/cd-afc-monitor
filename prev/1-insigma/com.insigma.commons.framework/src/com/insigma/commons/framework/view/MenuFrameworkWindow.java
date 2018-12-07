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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.application.WindowApplication;
import com.insigma.commons.ui.control.StatusBar;
import com.insigma.commons.ui.widgets.Menu;
import com.insigma.commons.ui.widgets.MenuItem;

public class MenuFrameworkWindow extends FrameworkWindow {

	private Menu mainMenu;

	private Composite composite;

	private StackLayout stackLayout;

	public MenuFrameworkWindow(Display parent, int style) {
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

		mainMenu = new Menu(this, SWT.BAR);
		setMenuBar(mainMenu);
		composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		stackLayout = new StackLayout();
		composite.setLayout(stackLayout);

		final Label label = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label.setText("Label");

		statusBar = new StatusBar(this, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 32;
		statusBar.setLayoutData(gridData);
		statusBar.setLayout(new GridLayout());

	}

	public void setPlatForm(UIPlatForm platForm) {
		this.platForm = platForm;

		WindowApplication.setUiplatForm(platForm);
		WindowApplication.setFrameworkWindow(this);

		createContents();

		if (platForm.getStatusPages() != null) {
			createStatusBar();
		}

		if (platForm.getModules() != null) {
			for (int i = 0; i < platForm.getModules().size(); i++) {
				Module module = platForm.getModules().get(i);
				MenuItem menuItem = new MenuItem(mainMenu, SWT.CASCADE);
				menuItem.setText(module.getText());
				mainMenu.setVisible(true);

				Menu menu = new Menu(this, SWT.DROP_DOWN);
				menuItem.setMenu(menu);

				Composite userComposite = createComposite(module, composite);
				menuItem.setData(userComposite);

				for (final Function func : module.getFunction()) {
					MenuItem item = new MenuItem(menu, SWT.PUSH);
					item.setText(func.getTitle());
					item.setData(userComposite);
					item.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							if (arg0.widget.getData() instanceof Composite) {
								stackLayout.topControl = (Control) arg0.widget.getData();
								composite.layout();
							}
						}
					});
				}

			}
		}
	}
}
