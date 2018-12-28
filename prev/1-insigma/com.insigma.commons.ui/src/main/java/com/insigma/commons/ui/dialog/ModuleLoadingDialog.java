/* 
 * 日期：2010-11-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

/**
 * 模块切换对话框
 * @author changjin_qiu
 *
 */
public class ModuleLoadingDialog extends Dialog {

	private Shell shell;

	private Composite composite;

	private String text = "正在加载，请稍候...";

	public ModuleLoadingDialog(Shell shell, int style) {
		super(shell, SWT.APPLICATION_MODAL | SWT.ON_TOP);
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public void close() {
		if (null != shell && !shell.isDisposed()) {
			shell.close();
		}
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL);
		int width = 291;
		int height = 60;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		shell.setLayout(new FillLayout());
		composite = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginTop = 10;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		Label middleLabel = new Label(composite, SWT.NONE);
		middleLabel.setFont(SWTResourceManager.getFont("隶书", 18, SWT.SIMPLE));
		GridData gd2 = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		middleLabel.setLayoutData(gd2);
		middleLabel.setText(text);

		new Label(composite, SWT.NONE);

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
