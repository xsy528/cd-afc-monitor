package com.insigma.afc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.initor.AFCDaemonInitor;
import com.insigma.afc.initor.AFCInitor;
import com.insigma.afc.initor.SystemInitor;
import com.insigma.afc.ui.status.DatabaseCheckThread;
import com.insigma.afc.workbench.CommLaunch;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.SystemState;
import com.insigma.commons.framework.config.IPlatFormBuilder;
import com.insigma.commons.framework.view.SystemWindow;
import com.insigma.commons.ui.InitialThread;
import com.insigma.commons.ui.SplashDialog;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AFCWorkBench {

	private static final String image_background = "/com/insigma/afc/images/background.png";

	private static final String image_login = "/com/insigma/afc/images/login.png";

	private static final Log logger = LogFactory.getLog(AFCWorkBench.class);

	protected final List<SystemInitor> systemInitors = new ArrayList<SystemInitor>();

	protected final List<AFCInitor> afcInitors = new ArrayList<AFCInitor>();

	protected final List<AFCDaemonInitor> afcDaemonInitors = new ArrayList<AFCDaemonInitor>();

	protected ExecutorService threadPool;

	protected List<IPlatFormBuilder> platformBuilders = new ArrayList<IPlatFormBuilder>();

	protected int winStyle = SystemWindow.WIN_TAB;

	protected Class mainClazz;

	public void setMainClazz(Class mainClazz) {
		this.mainClazz = mainClazz;
	}

	public AFCWorkBench(String workbenchName) {
	}

	public AFCWorkBench(String workbenchName, List<IPlatFormBuilder> platformBuilders) {
		this.platformBuilders = platformBuilders;
	}

	public void start() {
		initLaunch();
		String workbenchName = AFCApplication.getApplicationName() + "-" + AFCApplication.getVersion(mainClazz);
		while (Application.systemState != SystemState.EXIT) {
			userLogin();
			final SystemWindow systemWin = new SystemWindow(Display.getDefault(), SWT.NONE);
			systemWin.setBackgroundImage(SWTResourceManager.getImage(SystemWindow.class, image_login));
			systemWin.setWinStyle(winStyle);
			systemWin.setTitle(workbenchName);
			for (IPlatFormBuilder platformBuilder : platformBuilders) {
				systemWin.getSystem().add(platformBuilder.getPlatForm());
			}

			DatabaseCheckThread thread = new DatabaseCheckThread();
			thread.start();
			systemWin.open();
			//			Application.systemState = SystemState.EXIT;
		}
		System.exit(0);
	}

	protected void initLaunch() {
		CommLaunch.loadSystemInfo(null);
		CommLaunch.addShutdownHook(null);
	}

	/**
	 * 用户登陆
	 */
	private void userLogin() {
	}

	/**
	 * 系统初始化，主要是初始化spring，并为登陆做好准备。
	 * 
	 * @return
	 */
	public void systemInit() {
		SplashDialog splash = new SplashDialog(Display.getDefault(), SWT.NONE);
		splash.setImage(SWTResourceManager.getImage(AFCWorkBench.class, image_background));
		threadPool = Executors.newCachedThreadPool();
		splash.setInitialThread(new InitialThread() {

			public void run() {
				try {
					int procc = 0;
					int off = 100 / (systemInitors.size() + afcInitors.size());
					for (SystemInitor initor : systemInitors) {
						if (initor.isDaemon()) {
							continue;
						}
						setMessage("开始启动" + initor.getName());
						boolean ok = initor.init();
						if (!ok && !initor.nextIfFail()) {
							setMessage("启动：" + initor.getName() + "异常，程序退出");
							Thread.sleep(1000 * 5);
							System.exit(0);
						}
						setProcess(++procc * off);
					}
					logger.debug("异步执行daemon的系统初始化.");
					for (final SystemInitor initor : systemInitors) {
						if (initor.isDaemon()) {
							threadPool.execute(new Runnable() {

								public void run() {
									initor.init();
								}
							});
						}
					}

					for (AFCInitor initor : afcInitors) {
						logger.debug("开始启动" + initor.getName());
						setMessage("开始启动" + initor.getName());
						initor.init();
						setProcess(++procc * off);
						logger.debug("启动" + initor.getName() + "结束。");
					}
					logger.debug("初始化AFC应用环境成功。");

					for (final AFCDaemonInitor initor : afcDaemonInitors) {
						threadPool.execute(new Runnable() {
							public void run() {
								logger.debug("开始启动" + initor.getName());
								initor.init();
							}
						});
					}
					setProcess(100);
				} catch (Exception e) {
					logger.error("系统启动异常", e);
				}

			}
		});

		splash.open();
	}

	public void addSystemInitor(SystemInitor initor) {
		this.systemInitors.add(initor);
	}

	public void addAfcInitor(AFCInitor initor) {
		this.afcInitors.add(initor);
	}

	public void addAfcDaemonInitor(AFCDaemonInitor initor) {
		this.afcDaemonInitors.add(initor);
	}

	public static void main(String[] args) {
		new AFCWorkBench("AFC工作台", null).start();
	}

	public int getWinStyle() {
		return winStyle;
	}

	public void setWinStyle(int winStyle) {
		this.winStyle = winStyle;
	}

	public void setPlatformBuilders(List<IPlatFormBuilder> platformBuilders) {
		this.platformBuilders = platformBuilders;
	}

}
