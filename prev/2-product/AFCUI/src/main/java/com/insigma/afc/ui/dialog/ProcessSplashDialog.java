/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Request;
import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.IStatusChangeListener;
import com.swtdesigner.SWTResourceManager;

public class ProcessSplashDialog {

	private static Log logger = LogFactory.getLog(ProcessSplashDialog.class);

	private String imageFile = "/com/insigma/afc/images/logo.png";

	private ProcessInitialThread initialThread;

	private ProgressBar progressBar;

	private StyledText label;

	private Object result;

	private List<Action> finishActions;

	private boolean finishActionsInited = false;

	private Shell shell;

	private Display display;

	private Composite canvas;

	private Composite toolbar;

	/**
	 * Create the dialog
	 *
	 * @param parent
	 * @param style
	 */
	public ProcessSplashDialog(Display display, int style) {
		this.display = display;
		createContents();
	}

	/**
	 * Create the dialog
	 *
	 * @param parent
	 */
	public ProcessSplashDialog(Display display) {
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
		shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
		shell.setImage(SWTResourceManager.getImage(ProcessSplashDialog.class, getImageFile()));
		int width = 500;
		int height = 230;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));

		shell.setLayout(new FillLayout());
		canvas = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		canvas.setLayout(gridLayout);

		label = new StyledText(canvas,
				SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.MIRRORED | SWT.H_SCROLL | SWT.BORDER);
		GridData labelLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		labelLayoutData.heightHint = 200;
		label.setLayoutData(labelLayoutData);
		label.setText("正在初始化...\n");
		label.setVisible(true);
		label.setLayoutData(new GridData(GridData.FILL_BOTH));

		progressBar = new ProgressBar(canvas, SWT.NONE);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		toolbar = new Composite(canvas, SWT.NONE);
		toolbar.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, true, false));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.marginWidth = 0;
		gridLayout_2.marginHeight = 0;
		gridLayout_2.numColumns = 4;
		toolbar.setLayout(gridLayout_2);

	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImage(Image image) {
		canvas.setBackgroundImage(image);
	}

	public ProcessInitialThread getInitialThread() {
		return initialThread;
	}

	public void setInitialThread(ProcessInitialThread initThread) {
		this.initialThread = initThread;
		initialThread.setStatusChangeListener(new IStatusChangeListener() {
			public void StatusChanged() {
				try {
					if (finishActionsInited) {
						return;
					}
					if (!initialThread.isSuccessful) {
						Label failImage = new Label(toolbar, SWT.NONE);
						failImage.setText("操作失败。");
						//                        failImage.setImage(SWTResourceManager.getImage(ProcessSplashDialog.class,
						//                                "/com/insigma/afc/images/fail.png"));
						Label failmsg = new Label(toolbar, SWT.NONE);
						failmsg.setText("操作失败。");
						toolbar.layout(true);
						initialThread.setProcess(100);
						progressBar.setSelection(100);
						label.setText(initialThread.getAllMessage().toString());
						label.setSelection(label.getCharCount());
						finishActionsInited = true;
						return;
					}

					int progress = initialThread.getProgress();

					if (progress < 100) {
						label.setText(initialThread.getAllMessage().toString());
						label.setSelection(label.getCharCount());
						progressBar.setSelection(progress);
					} else {
						progressBar.setSelection(progress);
						label.setText(initialThread.getAllMessage().toString());
						label.setSelection(label.getCharCount());

						Label failImage = new Label(toolbar, SWT.NONE);
						failImage.setText("操作成功。");
						//                        failImage.setImage(SWTResourceManager.getImage(ProcessSplashDialog.class,
						//                                "/com/insigma/afc/images/ok.png"));
						toolbar.layout(true);
						finishActionsInited = true;

						if (finishActions != null && finishActions.size() > 0) {
							for (final Action action : finishActions) {
								Button item = new Button(toolbar, SWT.PUSH);
								item.setText(action.getText());
								item.addSelectionListener(new SelectionListener() {

									public void widgetSelected(SelectionEvent event) {
										Display.getDefault().asyncExec(new Runnable() {

											public void run() {
												action.getActionHandler().perform(new Request());
											}
										});
									}

									public void widgetDefaultSelected(SelectionEvent event) {
									}
								});
							}
							canvas.layout(true);
						}

					}
				} catch (Exception e) {
					logger.error("ProcessInitialThread异常", e);
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

	public void addFinishedAction(Action action) {
		if (finishActions == null) {
			finishActions = new ArrayList<Action>();
		}
		finishActions.add(action);
	}
}
