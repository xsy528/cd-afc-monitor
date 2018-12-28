/* 
 * 日期：2018年6月25日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.dialog;

import java.util.List;
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
import com.insigma.afc.view.AFCWorkBench;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.SystemState;
import com.insigma.commons.framework.config.IPlatFormBuilder;
import com.insigma.commons.framework.view.SystemWindow;
import com.insigma.commons.ui.InitialThread;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket: 温州工作台登录重绘
 * @author chenfuchun
 *
 */
public class WZAFCWorkBench extends AFCWorkBench {

	private static final String image_background = "/com/insigma/afc/images/background.png";

	private static final String image_login = "/com/insigma/afc/images/login.png";

	private static final String image_welcome = "/com/insigma/afc/images/welcome.png";

	protected static final Log logger = LogFactory.getLog(WZAFCWorkBench.class);

	public WZAFCWorkBench(String workbenchName, List<IPlatFormBuilder> platformBuilders) {
		super(workbenchName, platformBuilders);
	}

	public WZAFCWorkBench(String workbenchName) {
		super(workbenchName);
	}

	public void start() {
		initLaunch();
		String workbenchName = AFCApplication.getApplicationName() + "-" + AFCApplication.getVersion(mainClazz);
		while (Application.systemState != SystemState.EXIT) {
			final WZSystemWindow systemWin = new WZSystemWindow(Display.getDefault(), SWT.NONE);
			systemWin.setBackgroundImage(SWTResourceManager.getImage(SystemWindow.class, image_welcome));
			systemWin.setWinStyle(winStyle);
			systemWin.setTitle(workbenchName);
			for (IPlatFormBuilder platformBuilder : platformBuilders) {
				systemWin.getSystem().add(platformBuilder.getPlatForm());
			}

			DatabaseCheckThread thread = new DatabaseCheckThread();
			thread.start();

			systemWin.open();
		}
		System.exit(0);
	}

	/**
	 * 系统初始化，主要是初始化spring，并为登陆做好准备。
	 * 
	 * @return
	 */
	public void systemInit() {
		WZSplashDialog splash = new WZSplashDialog(Display.getDefault());
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

}
