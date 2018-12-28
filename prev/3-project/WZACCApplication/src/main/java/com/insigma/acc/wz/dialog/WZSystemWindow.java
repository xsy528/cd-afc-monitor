/* 
 * 日期：2018年6月25日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.insigma.commons.application.Application;
import com.insigma.commons.application.SystemState;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.framework.application.StatusPage;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.view.ExpandBarFrameworkWindow;
import com.insigma.commons.framework.view.FrameworkWindow;
import com.insigma.commons.framework.view.MenuFrameworkWindow;
import com.insigma.commons.framework.view.SystemWindow;
import com.insigma.commons.framework.view.TabFrameworkWindow;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.EmptyLogService;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.thread.ThreadManager;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket: 系统登录界面重绘
 * @author chenfuchun
 *
 */
public class WZSystemWindow extends SystemWindow {

	private static Log logger = LogFactory.getLog(WZSystemWindow.class);

	HashMap<EnhanceComposite, String> imgMap = new HashMap<EnhanceComposite, String>();

	public WZSystemWindow(Display parent, int style) {
		super(parent, style);
		setLayout(null);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setSize(630, 473);
		logSrvice.setModule(ModuleCode.MODULE_OTHER);
	}

	public void open() {
		final Display display = Display.getDefault();
		Label titleLable = new Label(this, SWT.NONE);
		final GridData gd_titleLable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		titleLable.setLayoutData(gd_titleLable);
		titleLable.setText(title);

		List<Point> list = new ArrayList<Point>();
		int i = 1;
		if (system != null) {
			// 下面参数的340和205是画圆的圆心坐标，+-5是为了好看而加的偏移量 By ChenYao
			list = getLocations(96, 340 - 5, 205 + 5, system.size() + 1);
			for (final UIPlatForm platForm : system) {
				String sid = platForm.getId();
				if (sid == null) {
					logger.info("默认初始化该UIPlatForm:[" + platForm.getName() + "]");
				} else {
					boolean hasAuth = Application.getUser().hasSystem(sid);
					if (!hasAuth) {
						logger.info("用户：[" + Application.getUser().getUserName() + "]没有子系统:" + platForm.getName()
								+ "的权限,忽略该子系统的初始化");
						continue;
					}
				}

				EnhanceComposite composite = new EnhanceComposite(this, SWT.INHERIT_DEFAULT);
				composite.setImage(SWTResourceManager.getImage(SystemWindow.class, platForm.getImage()));
				imgMap.put(composite, platForm.getImage());
				Point point = list.get(i);
				composite.setBounds(point.x, point.y, 64, 64);

				if (platForm.getIcon() != null && !platForm.getIcon().equals("")) {
					icon = platForm.getIcon();
				}
				i++;

				//鼠标事件
				{
					addMouseListener(composite);

					composite.addListener(SWT.MouseUp, new Listener() {
						public void handleEvent(Event e) {
							EnhanceComposite imgComp = (EnhanceComposite) e.widget;
							String imgPath = imgMap.get(imgComp).replace(".", "1.");
							((EnhanceComposite) e.widget)
									.setImage(SWTResourceManager.getImage(SystemWindow.class, imgPath));

							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									try {
										checkConn();
										FrameworkWindow mainFrame = null;
										if (winStyle == SystemWindow.WIN_TAB) {
											mainFrame = new TabFrameworkWindow(Display.getDefault(), SWT.SHELL_TRIM);
										} else if (winStyle == SystemWindow.WIN_MENU) {
											mainFrame = new MenuFrameworkWindow(Display.getDefault(), SWT.SHELL_TRIM);
										} else {
											mainFrame = new ExpandBarFrameworkWindow(Display.getDefault(),
													SWT.SHELL_TRIM);
										}

										mainFrame.setPlatForm(platForm);
										mainFrame.setMaximized(true);
										WZSystemWindow.this.setVisible(false);
										mainFrame.setText(platForm.getTitle() == null ? "" : platForm.getTitle());
										if (platForm.getIcon() != null && !platForm.getIcon().equals("")) {
											mainFrame.setImage(SWTResourceManager.getImage(SystemWindow.class,
													platForm.getIcon()));
										}
										if (platForm.getAfterUICreateInitor() != null) {
											platForm.getAfterUICreateInitor().init(mainFrame);
										}
										for (StatusPage page : platForm.getStatusPages()) {
											page.setStatus(-1);
										}

										getLogService().doBizLog("用户登录子系统：" + platForm.getName() + "。");
										currentUIPlatForm = platForm;
										try {
											mainFrame.open();
										} catch (Exception e) {
											getLogService().doBizErrorLog("用户登录子系统失败", e);
											return;
										}
										currentUIPlatForm = null;
										getLogService().doBizLog("用户登出子系统：" + platForm.getName() + "。");
										// changeBy ChenYao 解决注销不能直接到登录界面的问题
										// WZSystemWindow.this.setVisible(true);

										if (Application.systemState == SystemState.LOGOUT) {
											logoutProcess();
										} else {
											WZSystemWindow.this.setVisible(true);
										}

										// changeBy ChenYao 解决注销不能直接到登录界面的问题

									} catch (Exception e) {
										logSrvice.doBizErrorLog("用户登录子系统失败", e);
										MessageForm.Message("登陆失败", "登陆失败，请检查数据库连接。", SWT.ICON_ERROR);
										return;
									}
								}
							});
						}
					});
				}
			}
		}
		//注销
		EnhanceComposite composite = new EnhanceComposite(this, SWT.INHERIT_DEFAULT);
		composite.setImage(SWTResourceManager.getImage(SystemWindow.class, "/com/insigma/afc/images/Logout.png"));
		imgMap.put(composite, "/com/insigma/afc/images/Logout.png");
		Point lastPoint = list.get(0);
		composite.setBounds(lastPoint.x, lastPoint.y, 64, 64);
		addMouseListener(composite);
		composite.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				EnhanceComposite imgComp = (EnhanceComposite) e.widget;
				String imgPath = imgMap.get(imgComp).replace(".", "1.");
				((EnhanceComposite) e.widget).setImage(SWTResourceManager.getImage(SystemWindow.class, imgPath));
				// 注销
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (MessageForm.Query("提示信息", "确定要注销工作台吗？") == SWT.YES) {
							logoutProcess();
						}
					}
				});
			}
		});

		super.openWin();
		layout();
		while (!isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {
				logSrvice.doBizErrorLog("用户登录异常", e);
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
			WZSystemWindow.this.close();
		}
	}

	/**
	 *
	 * @param diameter 直径
	 * @param x 圆心x
	 * @param y 圆心y
	 * @param count 等分数目
	 * @return
	 */
	private List<Point> getLocations(int diameter, int x, int y, int count) {
		double TWO_PI = 3.1415926 * 2;
		double py = 1.0;
		int dox, doy;
		List<Point> list = new ArrayList<Point>();
		for (double i = 0.0; i >= -TWO_PI; i -= TWO_PI / count) {
			dox = (int) (diameter * Math.cos(i)) + x;
			doy = (int) (diameter * Math.sin(i)) + y;
			Point point = new Point(dox, doy);
			list.add(point);
		}
		List<Point> result = new ArrayList<>();
		for (int i = 1; i < list.size(); i++) {
			result.add(list.get(i));
		}
		result.add(list.get(0));

		return result;
	}
	//	private List<Point> getLocations(int diameter, int x, int y, int count) {
	//		double TWO_PI = 3.1415926 * 2;
	//		double py = 1.0;
	//		int dox, doy;
	//		List<Point> list = new ArrayList<Point>();
	//		for (double i = 0.0; i >= -TWO_PI; i -= TWO_PI / count) {
	//			dox = (int) ((diameter - 10 * (int) py) * Math.cos(i - py)) + x;
	//			doy = (int) ((diameter - 10 * (int) py) * Math.sin(i - py)) + y;
	//			Point point = new Point(dox, doy);
	//			list.add(point);
	//		}
	//		return list;
	//	}

	/**
	* @param composite
	*/
	private void addMouseListener(EnhanceComposite composite) {
		composite.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				EnhanceComposite imgComp = (EnhanceComposite) e.widget;
				String imgPath = imgMap.get(imgComp).replace(".", "2.");
				((EnhanceComposite) e.widget).setImage(SWTResourceManager.getImage(SystemWindow.class, imgPath));
			}
		});
		composite.addListener(SWT.MouseExit, new Listener() {
			public void handleEvent(Event e) {
				EnhanceComposite imgComp = (EnhanceComposite) e.widget;
				String imgPath = imgMap.get(imgComp);
				((EnhanceComposite) e.widget).setImage(SWTResourceManager.getImage(SystemWindow.class, imgPath));
			}
		});
		composite.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				EnhanceComposite imgComp = (EnhanceComposite) e.widget;
				String imgPath = imgMap.get(imgComp).replace(".", "3.");
				((EnhanceComposite) e.widget).setImage(SWTResourceManager.getImage(SystemWindow.class, imgPath));
			}
		});
	}

	/**
	* @throws ServiceException
	* @throws OPException
	*/
	private boolean checkConn() {
		String hql = "from TapUser u where u.userId=?";
		try {
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			commonDAO.getEntityListHQL(hql, Application.getUser().getUserID());
		} catch (ServiceException e) {
			logger.error("登陆失败，获取ICommonDAO失败", e);
			return false;
		} catch (OPException e) {
			logger.error("登陆失败，数据库连接失败", e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	private ILogService getLogService() {
		ILogService logSrvice = null;
		try {
			logSrvice = (ILogService) Application.getService(ILogService.class);
			logSrvice.setModule(ModuleCode.MODULE_OTHER);
		} catch (ServiceException e) {
			logger.error("获取服务错误", e);
			logSrvice = new EmptyLogService();
		}
		return logSrvice;
	}

}
