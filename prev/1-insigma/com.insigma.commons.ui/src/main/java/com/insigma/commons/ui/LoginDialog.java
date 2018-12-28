/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.widgets.Text;
import com.swtdesigner.SWTResourceManager;

public abstract class LoginDialog {

	protected Button btnOk;

	protected Button btnCancel;

	protected Object result;

	protected Shell shell;

	protected Text txtUserID;

	protected Text txtPassword;

	protected String userName;

	protected String passWord;

	protected Font font;

	protected Display display;

	protected Composite composite;

	protected String backgroudImage = "/backgroud.png";

	protected Image background;

	// 关闭按纽是否可用 默认可用
	protected boolean btnCancelisEnable = true;

	protected SelectionAdapter loginAction = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			userName = txtUserID.getText();
			passWord = txtPassword.getText();
			if (checkLogin()) {
				shell.close();
			}
		}
	};

	protected SelectionAdapter exitAction = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			System.exit(0);
		}
	};

	public Object open() {
		display = Display.getCurrent();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {

		shell = new Shell(display, SWT.NONE);
		int width = 500;
		int height = 330;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 500, 330);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		if (background != null) {
			composite.setBackgroundImage(background);
		}

		shell.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));

		font = SWTResourceManager.getFont("宋体", 9, SWT.BOLD);

		Composite group_1 = new Composite(composite, SWT.NONE);
		group_1.setBounds(90, 140, 345, 94);

		Label lblUserID = new Label(group_1, SWT.NONE);
		lblUserID.setFont(font);
		lblUserID.setBounds(60, 25, 80, 26);
		lblUserID.setText("用户编号");

		txtUserID = new Text(group_1, SWT.BORDER);

		//        txtUserID.setText("1");
		txtUserID.setFont(font);
		txtUserID.setBounds(150, 20, 118, 25);

		txtPassword = new Text(group_1, SWT.BORDER);

		//        txtPassword.setText("111111");
		txtPassword.setFont(font);
		txtPassword.setEchoChar('*');
		txtPassword.setBounds(150, 60, 118, 25);

		Label lblUserID_1 = new Label(group_1, SWT.NONE);
		lblUserID_1.setFont(font);
		lblUserID_1.setBounds(60, 65, 80, 26);
		lblUserID_1.setText("口    令");

		Composite group_2 = new Composite(composite, SWT.NONE);
		group_2.setBounds(90, 225, 345, 53);

		btnOk = new Button(group_2, SWT.NONE);
		btnOk.addSelectionListener(loginAction);
		btnOk.setFont(font);
		btnOk.setBounds(75, 10, 76, 25);
		btnOk.setText("登 录&L");

		btnCancel = new Button(group_2, SWT.NONE);
		btnCancel.addSelectionListener(exitAction);
		btnCancel.setFont(font);
		btnCancel.setBounds(175, 10, 76, 25);
		btnCancel.setText("关 闭&Q");
		btnCancel.setEnabled(btnCancelisEnable);

		txtUserID.setAccessControl(btnOk);
		txtPassword.setAccessControl(btnOk);

	}

	public abstract boolean checkLogin();

	public abstract boolean checkValid();

	public void setImage(Image image) {
		background = image;
		if (composite != null) {
			composite.setBackgroundImage(background);
		}
	}

	public void setImageFile(String imagefile) {
		backgroudImage = imagefile;
	}

	public void setLocation(Point location) {
		shell.setLocation(location);
	}

	/**
	 * 设置关闭按纽是否可用
	 * 
	 * @param btnOkisEnable
	 */
	public void setBtnCancelisEnable(boolean btnOkisEnable) {
		this.btnCancelisEnable = btnOkisEnable;
	}
}
