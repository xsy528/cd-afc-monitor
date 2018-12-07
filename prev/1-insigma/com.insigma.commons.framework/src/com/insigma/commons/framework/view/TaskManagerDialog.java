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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.control.DialogHeaderComposite;

public class TaskManagerDialog extends Dialog {

	protected Object result;

	protected Shell shell;

	public TaskManagerDialog(Shell parent, int style) {
		super(parent, style);
	}

	public TaskManagerDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
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
		shell.setSize(551, 424);

		shell.setLocation(FormUtil.getCenterLocation(shell.getSize().x, shell.getSize().y));

		shell.setText("任务管理器");

		final DialogHeaderComposite insigmaLogoComposite = new DialogHeaderComposite(shell, SWT.NONE);
		final GridData gd_insigmaLogoComposite = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_insigmaLogoComposite.heightHint = 70;
		insigmaLogoComposite.setLayoutData(gd_insigmaLogoComposite);
		insigmaLogoComposite.setTitle("任务管理器");
		insigmaLogoComposite.setDescription("任务管理器");

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
		gridLayout_1.numColumns = 1;
		composite.setLayout(gridLayout_1);

		final GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		gd_composite.heightHint = 60;
		composite.setLayoutData(gd_composite);

	}
}
