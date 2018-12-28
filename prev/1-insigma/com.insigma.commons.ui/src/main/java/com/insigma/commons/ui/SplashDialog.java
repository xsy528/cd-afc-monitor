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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.SWTResourceManager;

public class SplashDialog {

	private String imageFile = "/com/insigma/afc/monitor/gif/backgroud.png";

	private InitialThread initialThread;

	protected ProgressBar progressBar;

	protected CLabel label;

	private Object result;

	private Shell shell;

	private Display display;

	private Composite canvas;

	/** 进度条是否需要自动增长(默认自动增长) */
	private boolean autoIncrease = true;

	protected int width = 500;

	protected int height = 330;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public SplashDialog(Display display, int style) {
		this.display = display;
		createContents();
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public SplashDialog(Display display) {
		this.display = display;
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		shell.open();
		shell.layout();

		// 启动初始化线程
		if (initialThread != null) {
			Thread thread = new Thread(initialThread, "初始化线程");
			thread.start();
		}
		if (autoIncrease) {
			display.asyncExec(new Runnable() {

				public void run() {
					if (!shell.isDisposed()) {
						progressBar.setSelection(progressBar.getSelection() + 1);
						display.timerExec(1000, this);
					}
				}

			});
		}

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
		//        int width = 500;
		//        int height = 330;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		shell.setLayout(new FillLayout());
		canvas = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		canvas.setLayout(gridLayout);

		final Composite composite = new Composite(canvas, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		label = new CLabel(canvas, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(255, 255, 255));
		label.setText("系统正在初始化...");
		label.setVisible(true);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		progressBar = new ProgressBar(canvas, SWT.NONE);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		CLabel label = new CLabel(canvas, SWT.NONE);
		label.setVisible(true);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImage(Image image) {
		canvas.setBackgroundImage(image);
	}

	public InitialThread getInitialThread() {
		return initialThread;
	}

	public void setInitialThread(InitialThread initThread) {

		this.initialThread = initThread;

		initialThread.setStatusChangeListener(new IStatusChangeListener() {

			public void StatusChanged() {

				int progress = initialThread.getProgress();

				if (progress < 100) {
					label.setText(initialThread.getMessage());
					progressBar.setSelection(progress);
				} else {
					if (!shell.isDisposed()) {
						shell.close();
					}
				}
			}
		});

	}

	public Shell getShell() {
		return this.shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

}
