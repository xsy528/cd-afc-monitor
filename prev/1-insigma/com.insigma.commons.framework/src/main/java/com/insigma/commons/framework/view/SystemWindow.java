package com.insigma.commons.framework.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.application.AnonymousUser;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.IUser;
import com.insigma.commons.application.SystemState;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.framework.application.StatusPage;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.thread.ThreadManager;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.ModuleLoadingDialog;
import com.insigma.commons.ui.widgets.Shell;
import com.swtdesigner.SWTResourceManager;

public class SystemWindow extends Shell {

	private static final String SWITCH_OK = "switchOK";

	private static Log logger = LogFactory.getLog(SystemWindow.class);

	protected ILogService logSrvice;

	public static int WIN_TAB = 0;

	public static int WIN_MENU = 1;

	public static int WIN_EXPANDBAR = 2;

	protected String icon = "";

	protected List<UIPlatForm> system;

	protected UIPlatForm currentUIPlatForm;

	protected int winStyle = SystemWindow.WIN_TAB;

	protected String title = "";

	public static Integer LOG_LOGIN_LOGOUT = 1000;

	public SystemWindow(Display parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = gridLayout.marginLeft = 10;
		gridLayout.marginBottom = gridLayout.marginRight = 50;

		gridLayout.horizontalSpacing = 20;
		gridLayout.verticalSpacing = 20;
		gridLayout.numColumns = 2;
		setLayout(gridLayout);
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		setSize(500, 330);

		try {
			logSrvice = Application.getService(ILogService.class);
			logSrvice.setModule(LOG_LOGIN_LOGOUT);
		} catch (ServiceException e) {
			logger.error("获取服务错误", e);
		}
	}

	public SystemWindow() {
		try {
			logSrvice = Application.getService(ILogService.class);
			logSrvice.setModule(1);
		} catch (ServiceException e) {
			logger.error("获取服务错误", e);
		}
	}

	public void open() {
		final Display display = Display.getDefault();
		Label titleLable = new Label(this, SWT.NONE);
		final GridData gd_titleLable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		titleLable.setLayoutData(gd_titleLable);
		titleLable.setText(title);
		int i = 1;
		if (system != null) {
			for (final UIPlatForm platForm : system) {
				String sid = platForm.getId();
				if (sid == null) {
					logger.info("UIPlatForm:[" + platForm.getName() + "]没有定义systemId，默认初始化该UIPlatForm");
				} else {
					boolean hasAuth = Application.getUser().hasSystem(sid);
					if (!hasAuth) {
						logger.info("用户：[" + Application.getUser().getUserName() + "]没有子系统:" + platForm.getName()
								+ "的权限,忽略该子系统的初始化");
						continue;
					}
				}

				Button button = new Button(this, SWT.NONE);
				button.setText(platForm.getName());
				GridData btnGridData = new GridData(GridData.FILL_BOTH);
				if (i == 1 || i % 2 != 0) {
					btnGridData.horizontalIndent = 40;
				}
				i++;
				button.setLayoutData(btnGridData);
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						FrameworkWindow mainFrame = null;
						if (winStyle == SystemWindow.WIN_TAB) {
							mainFrame = new TabFrameworkWindow(Display.getDefault(), SWT.SHELL_TRIM);
						} else if (winStyle == SystemWindow.WIN_MENU) {
							mainFrame = new MenuFrameworkWindow(Display.getDefault(), SWT.SHELL_TRIM);
						} else {
							mainFrame = new ExpandBarFrameworkWindow(Display.getDefault(), SWT.SHELL_TRIM);
						}

						final FrameworkWindow mainFrame1 = mainFrame;
						final ModuleLoadingDialog progress = new ModuleLoadingDialog(display.getActiveShell(),
								SWT.NONE);
						progress.setText("正在切换到：" + platForm.getName() + "...");
						currentUIPlatForm = platForm;

						display.timerExec(1, new Runnable() {
							public void run() {
								try {
									mainFrame1.setPlatForm(platForm);
								} catch (Exception e) {
									String msg = "[ " + platForm.getName() + " ]模块切换异常，可能是网络问题，请检查后再试。";
									logger.error(msg, e);
									MessageForm.Message("提示信息", msg, SWT.ICON_ERROR);
									mainFrame1.setData(SWITCH_OK, false);// 标记切换失败
								}
								progress.close();
							}
						});
						progress.open();
						if (Boolean.FALSE.equals(mainFrame1.getData(SWITCH_OK))) {
							if (!mainFrame1.isDisposed()) {
								mainFrame1.close();
							}
							return;
						}

						mainFrame.setMaximized(true);
						SystemWindow.this.setVisible(false);
						mainFrame.setText(platForm.getTitle() == null ? "" : platForm.getTitle());
						if (platForm.getIcon() != null && !platForm.getIcon().equals("")) {
							mainFrame.setImage(SWTResourceManager.getImage(SystemWindow.class, platForm.getIcon()));
						}
						if (platForm.getAfterUICreateInitor() != null) {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									while (display.getActiveShell() == null) {
										try {
											Thread.sleep(100);
										} catch (InterruptedException e) {
										}
									}

									Application.setShell(display.getActiveShell());
									platForm.getAfterUICreateInitor().init(mainFrame1);
								}
							});
						}
						for (StatusPage page : platForm.getStatusPages()) {
							page.setStatus(-1);
						}
						Thread logThread = new Thread() {

							@Override
							public void run() {
								logSrvice.doBizLog("用户登录子系统：" + platForm.getName());
							}

						};
						logThread.start();
						mainFrame.open();

						currentUIPlatForm = null;
						logThread = new Thread() {

							@Override
							public void run() {
								logSrvice.doBizLog("用户登出子系统：" + platForm.getName());
							}

						};
						logThread.start();

						if (Application.systemState == SystemState.LOGOUT) {
							logoutProcess();
						} else {
							SystemWindow.this.setVisible(true);
						}
					}
				});

				if (platForm.getIcon() != null && !platForm.getIcon().equals("")) {
					icon = platForm.getIcon();
				}
			}
		}
		this.setImage(SWTResourceManager.getImage(SystemWindow.class, icon));
		Button button = new Button(this, SWT.NONE);
		GridData buttonGridData = new GridData(GridData.FILL_BOTH);
		if (i == 1 || i % 2 != 0) {
			buttonGridData.horizontalIndent = 40;
		}
		button.setLayoutData(buttonGridData);
		button.setText("注销系统");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (MessageForm.Query("确认信息", "确定要注销工作台吗？") == SWT.YES) {
					logoutProcess();
				}
			}
		});
		super.open();
		layout();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
				if (!checkUser()) {
					dispose();
				}
			}
		}
	}

	private void logoutProcess() {
		try {
			ThreadManager.getInstance().stopAll();
			Application.systemState = SystemState.LOGOUT;
			ThreadManager.getInstance().stopAll();
			logSrvice.doBizLog("用户注销");
		} catch (Exception e) {
			logger.error("注销异常", e);
		} finally {
			SystemWindow.this.close();
		}
	}

	private boolean checkUser() {
		IUser user = Application.getUser();
		if (user == null || user instanceof AnonymousUser) {
			return false;
		} else {
			return true;
		}
	}

	public void setWinStyle(int winStyle) {
		this.winStyle = winStyle;
	}

	public List<UIPlatForm> getSystem() {
		if (system == null) {
			system = new ArrayList<UIPlatForm>();
		}
		return system;
	}

	public void setSystem(List<UIPlatForm> system) {
		this.system = system;
	}

	public UIPlatForm getCurrentUIPlatForm() {
		return currentUIPlatForm;
	}

	public void setTitle(String workbenchName) {
		this.title = workbenchName;
		if (workbenchName != null) {
			setText(workbenchName);
		}

	}

	protected void openWin() {
		super.open();
	}

}
