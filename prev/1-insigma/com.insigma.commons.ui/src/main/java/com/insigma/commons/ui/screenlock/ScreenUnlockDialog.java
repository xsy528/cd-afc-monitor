/* 
 * 日期：2012-12-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.screenlock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.widgets.Button;

/**
 * Ticket: 用于解除屏幕锁定的窗口
 * 
 * @author 郑淦
 */
public abstract class ScreenUnlockDialog extends StandardDialog {
	private Text pwdText;

	private Text nameText;

	public ScreenUnlockDialog(Shell parent) {
		super(parent, SWT.TITLE);
		setSize(500, 375);
		setText("屏幕解锁");
		setDescription("输入你的用户名和密码，解除软件对屏幕的锁定，关闭此窗口将重新锁定。");

		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				WinEventInterceptor.restartHook(getRestartHookCode());
			}
		});
	}

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());
		final Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout());
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite contentComposite = new Composite(mainComposite, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 15;
		gridLayout.marginWidth = 15;
		gridLayout.horizontalSpacing = 15;
		gridLayout.marginHeight = 15;
		gridLayout.numColumns = 2;
		contentComposite.setLayout(gridLayout);
		contentComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		final Label nameLabel = new Label(contentComposite, SWT.NONE);
		nameLabel.setText("用户名：");

		nameText = new Text(contentComposite, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.widthHint = 120;
		nameText.setLayoutData(gd_nameText);

		final Label pwdLabel = new Label(contentComposite, SWT.NONE);
		pwdLabel.setText("密  码：");

		pwdText = new Text(contentComposite, SWT.BORDER);
		pwdText.setEchoChar('*');
		final GridData gd_pwdText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_pwdText.widthHint = 120;
		pwdText.setLayoutData(gd_pwdText);

		final Composite btnComposite = new Composite(contentComposite, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.horizontalSpacing = 15;
		gridLayout_1.numColumns = 2;
		gridLayout_1.marginWidth = 0;
		btnComposite.setLayout(gridLayout_1);
		btnComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));

		final Button okBtn = new Button(btnComposite, SWT.NONE);
		okBtn.setText("确定(&O)");
		final GridData gd_okBtn = new GridData(70, 30);
		okBtn.setLayoutData(gd_okBtn);
		okBtn.addSelectionListener(new ScreenUnlockListener());

		final Button closeBtn = new Button(btnComposite, SWT.NONE);
		closeBtn.setText("关闭(&C)");
		final GridData gd_closeBtn = new GridData(70, 30);
		closeBtn.setLayoutData(gd_closeBtn);
		closeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	/**
	 * 先验证用户名密码是否正确
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	protected abstract boolean isUserNameAndPwdRight(String username, String password);

	/**
	 * 验证是否有权限来解除锁屏
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	protected abstract boolean hasPermissionToUnlock(String username, String password);

	/**
	 * 重新启用锁屏的快捷键的CODE，例如F10是OS.VK_F10，即0x79
	 */
	protected abstract int getRestartHookCode();

	class ScreenUnlockListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (!isUserNameAndPwdRight(nameText.getText(), pwdText.getText())) {
				MessageForm.Message("您输入的用户名或密码有误，请重试");
				return;
			}
			if (!hasPermissionToUnlock(nameText.getText(), pwdText.getText())) {
				MessageForm.Message("您没有权限解除程序对屏幕的锁定。");
				return;
			}
			WinEventInterceptor.resetKeyboard();
			WinEventInterceptor.resetTaskmgr();
			MessageForm.Message("已解除程序对屏幕的锁定。");
			shell.dispose();
		}
	}
}
