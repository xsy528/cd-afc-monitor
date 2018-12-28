package com.insigma.commons.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.IDialog;
import com.insigma.commons.ui.control.DialogHeaderComposite;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

public class StandardDialog extends Dialog implements IDialog {

	public static final int LOGO = 0x01;

	public static final int BUTTONBAR = 0x02;

	public static final int CLOSE = 0x04;

	protected Point localPoint;

	private Object result;

	protected Shell shell;

	private DialogHeaderComposite logo;

	private int style;

	private Composite composite;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public DialogHeaderComposite getLogo() {
		return logo;
	}

	public void setLogo(DialogHeaderComposite logo) {
		this.logo = logo;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public void setDescription(String description, RGB strColor, FontData fd) {
		logo.setDescription(description, strColor, fd);
	}

	public void setDescription(String description) {
		logo.setDescription(description);
	}

	public void setTitle(String title) {
		logo.setTitle(title);
	}

	public void setImage(Image arg0) {
		shell.setImage(arg0);
	}

	public void setSize(int arg0, int arg1) {
		shell.setSize(arg0, arg1);
	}

	public void setText(String arg0) {
		shell.setText(arg0);
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public StandardDialog(Shell parent, int style) {
		super(parent, style);
		this.style = style;
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		logo = new DialogHeaderComposite(shell, SWT.NONE);

		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		shell.setLayout(gridLayout);
		shell.setImage(SWTResourceManager.getImage(StandardDialog.class, "/com/insigma/afc/images/logo.png"));

		final GridData gd_logo = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_logo.heightHint = 70;
		logo.setLayoutData(gd_logo);

		composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	public Object open() {

		//		if ((style & BUTTONBAR) != 0) {
		Label label = new Label(getShell(), SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createBar();
		//		}
		createContents(composite);
		composite.layout();

		shell.setLocation(
				null != localPoint ? localPoint : FormUtil.getCenterLocation(shell.getSize().x, shell.getSize().y));

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	public void close() {
		shell.close();
	}

	protected void createContents(Composite parent) {

	}

	public void setLocalPoint(Point localPoint) {
		this.localPoint = localPoint;
	}

	protected void createBar() {
		final Composite btnComposite = new Composite(shell, SWT.NONE);
		final GridData gdBtnComposite = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		gdBtnComposite.heightHint = 60;
		btnComposite.setLayoutData(gdBtnComposite);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginTop = 15;
		gridLayout_1.marginWidth = 15;
		gridLayout_1.marginHeight = 0;
		gridLayout_1.horizontalSpacing = 15;
		gridLayout_1.numColumns = 2;
		btnComposite.setLayout(gridLayout_1);

		final Button exitBtn = new Button(btnComposite, SWT.NONE);
		exitBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				shell.dispose();
			}
		});
		exitBtn.setLayoutData(new GridData(70, 30));
		exitBtn.setText("关闭(&X)");
	}

}
