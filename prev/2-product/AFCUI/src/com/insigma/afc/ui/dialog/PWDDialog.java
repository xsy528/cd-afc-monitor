/* 
 * 日期：2008-1-11
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.insigma.commons.application.Application;
import com.insigma.commons.application.IUser;
import com.insigma.commons.codec.md5.MD5Util;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.control.DialogHeaderComposite;
import com.insigma.commons.ui.widgets.Shell;
import com.insigma.commons.util.lang.StringUtil;
import com.swtdesigner.SWTResourceManager;

/**
 * 输入口令对话框
 * 
 * @author 廖红自
 */
public class PWDDialog extends Dialog {

	protected Object result;

	protected Shell shell;

	private Text txtPWD;

	private Text txtNo;

	private final String userID;

	private String imagePath = "/com/insigma/afc/images/logo.png";

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public PWDDialog(org.eclipse.swt.widgets.Shell parent, int style, String userID) {
		super(parent, style);
		this.userID = userID;
		createContents();
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public PWDDialog(org.eclipse.swt.widgets.Shell parent, String userID) {
		super(parent, SWT.NONE);
		this.userID = userID;
		createContents();
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
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

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		shell.setLayout(gridLayout);
		shell.setImage(SWTResourceManager.getImage(PWDDialog.class, imagePath));
		shell.setFont(SWTResourceManager.getFont("", 9, SWT.NONE));
		shell.setSize(378, 254);
		shell.setText("输入口令");
		shell.posCenter();

		final DialogHeaderComposite logoComposite = new DialogHeaderComposite(shell, SWT.NONE);
		logoComposite.setDescription("说明：输入口令");
		logoComposite.setTitle("口令");
		final GridData gdLogoComposite = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gdLogoComposite.heightHint = 70;

		// gd_logoComposite.widthHint = 693;
		// gd_logoComposite.heightHint = 64;
		logoComposite.setLayoutData(gdLogoComposite);

		final Composite composite = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.marginHeight = 15;
		gridLayout_2.numColumns = 2;
		composite.setLayout(gridLayout_2);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		final Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("", 11, SWT.NONE));
		label.setText("操作员编号：");

		txtNo = new Text(composite, SWT.BORDER);
		txtNo.setLayoutData(new GridData(117, 18));
		txtNo.setText(userID);
		txtNo.setTextLimit(8);

		final Label label11 = new Label(composite, SWT.NONE);
		label11.setLayoutData(new GridData(86, SWT.DEFAULT));
		label11.setFont(SWTResourceManager.getFont("", 11, SWT.NONE));
		label11.setText("口     令：");

		txtPWD = new Text(composite, SWT.BORDER);
		txtPWD.setLayoutData(new GridData(117, 18));
		txtPWD.setEchoChar('*');
		txtPWD.setTextLimit(16);

		Label label1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Composite composite1 = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginHeight = 0;
		gridLayout_1.marginTop = 15;
		gridLayout_1.marginWidth = 20;
		gridLayout_1.horizontalSpacing = 20;
		gridLayout_1.numColumns = 2;
		composite1.setLayout(gridLayout_1);
		final GridData gdComposite1 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		gdComposite1.heightHint = 60;
		composite1.setLayoutData(gdComposite1);

		final Button button = new Button(composite1, SWT.NONE);
		button.setLayoutData(new GridData(70, 30));
		shell.setDefaultButton(button);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				Boolean bl = checkLogin();
				result = bl;
				if (bl) {
					shell.close();
				}
			}
		});
		button.setText("确认(&O)");

		final Button button1 = new Button(composite1, SWT.NONE);
		button1.setLayoutData(new GridData(70, 30));
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				result = null;
				shell.close();
			}
		});
		button1.setText("取消(&C)");

		//
	}

	/**
	 * 设置密码框获得光标
	 */
	public void setPwdFocus() {
		txtPWD.setFocus();
	}

	/**
	 * 验证用户密码输入
	 * 
	 * @return 验证结果
	 */
	private Boolean checkLogin() {
		// TODO 密码验证
		try {
			if ((txtNo.getText() == null || "".endsWith(txtNo.getText()))
					&& (txtPWD.getText() == null || "".endsWith(txtPWD.getText()))) {
				MessageForm.Message("提示信息", "请输入操作员编号和口令。", SWT.ICON_INFORMATION);
				return false;
			}

			if (txtNo.getText() == null || "".endsWith(txtNo.getText())) {
				MessageForm.Message("提示信息", "请输入操作员编号。", SWT.ICON_INFORMATION);
				return false;
			}

			if (!StringUtil.isNumeric(txtNo.getText())) {
				MessageForm.Message("警告信息", "操作员编号输入错误。", SWT.ICON_WARNING);
				return false;
			}

			IUser user = Application.getUser();
			if (user == null || !txtNo.getText().equals("" + user.getUserID())) {
				MessageForm.Message("警告信息", "操作员编号输入错误，与登陆用户不匹配。", SWT.ICON_WARNING);
				return false;
			}

			if (txtPWD.getText() == null || "".endsWith(txtPWD.getText())) {
				MessageForm.Message("提示信息", "请输入口令。", SWT.ICON_INFORMATION);
				return false;
			}

			String password = MD5Util.MD5(txtPWD.getText());
			if (!user.getPassWord().equals(password)) {
				MessageForm.Message("警告信息", "输入口令错误。", SWT.ICON_WARNING);
				return false;
			}

		} catch (Exception ex) {
			MessageForm.Message("错误信息", "操作员编号、口令验证异常。", SWT.ICON_ERROR);
			return false;
		}
		return true;
	}

}
