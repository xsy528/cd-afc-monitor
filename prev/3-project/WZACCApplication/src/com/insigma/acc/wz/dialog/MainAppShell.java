package com.insigma.acc.wz.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.widgets.Button;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

public class MainAppShell extends Shell {

	private static String title = "";
	private IBeforeExit beforeExit;

	public MainAppShell(Display display, int style, String title, int height, IBeforeExit beforeExit) {
		super(display, style);
		this.beforeExit = beforeExit;
		initMe(display, style, title, height);
	}

	public MainAppShell(Display display, int style, String title, int height) {
		super(display, style);
		initMe(display, style, title, height);
	}

	/**
	 * 构造函数
	 *
	 * @param display
	 * @param style
	 */
	void initMe(Display display, int style, String title, int height) {
		this.title = title;

		createToolBar();
		// 窗体大小
		setSize(350, 220);

		Monitor primary = Display.getDefault().getMonitors()[0];
		Rectangle bounds = primary.getBounds();
		Rectangle rect = getBounds();
		// int x = bounds.x + (bounds.width - rect.width) / 2;
		// int y = bounds.y + (bounds.height - rect.height) / 2;
		int x = bounds.width - rect.width;
		int y = bounds.height - rect.height - height;
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		setLocation(x, y);
		setBackgroundImage(SWTResourceManager.getImage(MainAppShell.class, "/images/background.png"));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		setLayout(gridLayout);

	}

	/**
	 * 描述：绘制工具栏
	 */
	private void createToolBar() {
		final GridData btnGridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		btnGridData.heightHint = 45;
		btnGridData.widthHint = 120;

		final GridData hideGridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		hideGridData.heightHint = 45;
		hideGridData.widthHint = 110;

		final GridData welcomeLabGridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		welcomeLabGridData.horizontalSpan = 3;
		final Font welcomeMessageFont = SWTResourceManager.getFont("", 30, SWT.NONE);

		Label labWelcomeMessage = new Label(this, SWT.CENTER);
		labWelcomeMessage.setLayoutData(welcomeLabGridData);
		labWelcomeMessage.setFont(welcomeMessageFont);
		labWelcomeMessage.setText(title + "\n\n\n");
		labWelcomeMessage.setVisible(false);

		Label hide = new Label(this, SWT.NONE);
		hide.setLayoutData(hideGridData);
		hide.setVisible(false);

		Button btnCommPort = new Button(this, SWT.NONE);
		btnCommPort.setText("关闭");
		btnCommPort.setLayoutData(btnGridData);
		btnCommPort.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				super.widgetSelected(arg0);
				MessageBox dialog = new MessageBox(Display.getCurrent().getActiveShell(),
						SWT.OK | SWT.CANCEL | SWT.ICON_INFORMATION);
				dialog.setMessage("请确认是否关闭" + title);
				int result = dialog.open();

				if (result == SWT.OK) {
					dialog.setMessage("请再次确认是否关闭程序" + title);
					result = dialog.open();
					if (result == SWT.OK) {
						if (beforeExit != null) {
							try {
								beforeExit.beforeExit();
							} catch (Throwable e) {
								// ignore
							}
						}
						System.exit(0);
					}
				}

			}
		});

		Label hide2 = new Label(this, SWT.NONE);
		hide2.setLayoutData(hideGridData);
		hide2.setVisible(false);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public static interface IBeforeExit {
		void beforeExit();
	}
}
