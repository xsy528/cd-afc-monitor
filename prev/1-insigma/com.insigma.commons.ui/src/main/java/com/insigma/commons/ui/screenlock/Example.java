/* 
 * 日期：2012-12-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.screenlock;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Ticket: 锁屏的例子
 * 
 * @author 郑淦
 */
public class Example {

	protected Shell shell;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Example window = new Example();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启用hook
	 */
	private void startHOOK() {
		// 启动前先禁用快捷键
		WinEventInterceptor.setCommonUseKeyDisable();
		// 禁用任务管理器
		WinEventInterceptor.setTaskmgrDisable();
		// 隐藏任务栏
		WinEventInterceptor.setTaskBarHide((int) shell.handle);
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		startHOOK();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		shell = new Shell(SWT.TITLE);
		shell.addControlListener(new ControlAdapter() {
			public void controlMoved(ControlEvent e) {
				shell.setLocation(0, 0);
				shell.layout();
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				WinEventInterceptor.resetTaskBar((int) shell.handle);
			}
		});
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		shell.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
		shell.setText("SWT Application");
		final Button button = new Button(shell, SWT.PUSH);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				ExampleScreenUnlockDialog a = new ExampleScreenUnlockDialog(shell);
				a.open();
			}
		});
		button.setText("解除屏幕锁定");

		final Button closeBtn = new Button(shell, SWT.NONE);
		closeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				shell.dispose();
			}
		});
		closeBtn.setText("关闭窗口");
		//
	}
}
