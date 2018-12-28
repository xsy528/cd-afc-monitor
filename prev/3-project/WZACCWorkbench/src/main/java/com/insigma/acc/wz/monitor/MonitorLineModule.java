package com.insigma.acc.wz.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.monitor.action.CommandFactoryAction;
import com.insigma.acc.wz.monitor.action.WZDeviceMonitorAction;
import com.insigma.acc.wz.monitor.action.WZDeviceStatusAction;
import com.insigma.acc.wz.monitor.action.WZEquEventRefreshAction;
import com.insigma.acc.wz.monitor.action.WZFilterAction;
import com.insigma.acc.wz.monitor.action.WZModeCommandAction;
import com.insigma.acc.wz.monitor.action.WZMonitorConfigAction;
import com.insigma.acc.wz.monitor.action.WZRefreshAction;
import com.insigma.acc.wz.monitor.action.WZSortAction;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.monitor.action.ModeQueryAction;
import com.insigma.afc.monitor.action.TimeSyncAction;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.listview.FilterAction;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.equstatus.EquStatusRefreshAction;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.NodeStatusRefreshAction;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.afc.monitor.listview.mode.ModeBroadcastRefreshAction;
import com.insigma.afc.monitor.listview.mode.ModeCmdLogRefreshAction;
import com.insigma.afc.monitor.map.GraphicMapGenerator;
import com.insigma.afc.monitor.map.builder.GraphicMapBuilder;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.ModuleEventAdapter;
import com.insigma.commons.framework.config.IModuleBuilder;
import com.insigma.commons.framework.extend.UserModule;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.service.ILogService;

/**
 * 
 * Ticket: 监控管理->路网监控
 * 
 * @author hexingyue
 *
 */
public class MonitorLineModule implements IModuleBuilder {
	private Log logger = LogFactory.getLog(MonitorLineModule.class);

	private ILogService logSysService;

	private String moduleName = "   路网监控   ";

	private final CommonTreeProvider treeProvider;

	private int refreshInterval = 15 * 1000;

	private int mapRefreshInterval = 10 * 1000;

	private static int online = 0;

	private static int OFF_LINE = 1;

	private static int STATION_OFF = 3;
	private static int STATION_USELESS = 9;

	private List<Object> selections = new ArrayList<Object>();

	public MonitorLineModule() {
		treeProvider = new CommonTreeProvider();
		treeProvider.setDepth(AFCNodeLevel.SLE);

		int configItemForInt = (Integer) Application.getData(SystemConfigKey.VIEW_REFRESH_INTERVAL);

		if (configItemForInt != 0) {
			refreshInterval = configItemForInt * 1000;
		}

		getSelections();
	}

	private void getSelections() {
		try {
			IMetroNodeService metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
			List<MetroDevice> devices = metroNodeService.getAllMetroDevice();
			if (devices != null) {
				for (MetroDevice device : devices) {
					selections.add(device);
				}
			}
			List<MetroLine> lines = metroNodeService.getAllMetroLine();
			if (lines != null) {
				for (MetroLine line : lines) {
					selections.add(line);
				}
			}
			List<MetroStation> stations = metroNodeService.getAllMetroStation();
			if (stations != null) {
				for (MetroStation station : stations) {
					selections.add(station);
				}
			}
		} catch (ServiceException e) {
			logger.error("获取metroNodeService异常", e);
		}
	}

	@Override
	public Module getModule() {
		UserModule module = new UserModule();
		module.setModuleListener(new ModuleEventAdapter() {

			@Override
			public void create(Object object) {

				// 获取功能按钮
				List<Action> actions = getActions();

				EditorFrameWork framework = (EditorFrameWork) object;
				framework.setActions(actions);

				{
					final MapTreeView mapTreeView = new MapTreeView(framework, SWT.NONE);
					mapTreeView.setText("监控模块");
					framework.addView("MONITOR_VIEW", mapTreeView, SWT.LEFT);

					final MapEditorView map = new MapEditorView(framework, SWT.NONE);
					map.setText("监控图 ");
					framework.addView("MONITOR_MAPEDITORVIEW", map, SWT.CENTER);

					// 生成地图相关信息
					final GraphicMapGenerator tmpMapGenerator = new GraphicMapGenerator();
					GraphicMapBuilder graphicMapBuilder = new GraphicMapBuilder();

					// 双击设备监视
					graphicMapBuilder.setDevice_doubleClickAction(new WZDeviceMonitorAction());

					graphicMapBuilder.contextActions = actions;
					tmpMapGenerator.setGraphicMapBuilder(graphicMapBuilder);
					mapTreeView.setGraphicMapGenerator(tmpMapGenerator);
					mapTreeView.refresh();

					map.setRefreshAction(
							new Action("监控树刷新", new com.insigma.commons.editorframework.ActionHandlerAdapter() {

								@Override
								public void perform(ActionContext context) {
									try {
										ActionTreeNode selection = mapTreeView.getSelection();
										ActionTreeNode root = selection;
										logger.debug("开始监控设备信息 ");
										if (root == null) {
											logger.debug("根节点信息为空");
											return;
										}
										while (root.getParentNode() != null) {
											root = root.getParentNode();
										}
										doimage(root);
										List<ActionTreeNode> subNodes = root.getSubNodes();
										if (subNodes == null) {
											return;
										}

										for (ActionTreeNode node : subNodes) {
											doimage(node);
											if (subNodes == null) {
												return;
											}
											List<ActionTreeNode> ssubNodes = node.getSubNodes();
											for (ActionTreeNode snode : ssubNodes) {
												doimage(snode);
											}
										}
										map.reDraw();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}

								public void doimage(ActionTreeNode root) {
									MapItem mapitem = (MapItem) root.getValue();
									// 通信前置机是否在线
									Number onLine = (Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
									// 设备
									for (MapItem item : mapitem.getMapItems()) {
										MetroNode node = (MetroNode) item.getValue();
										if (node == null || !(node instanceof MetroDevice)) {
											continue;
										}
										int status = DeviceStatus.OFF_LINE;
										MetroDevice metroDevice = (MetroDevice) node;
										long nodeId = metroDevice.getNodeId();

										// 1.如果通信前置机不在线，则节点不在线
										if (onLine != null && !onLine.equals(online)) {
											status = getStatus(false, status);
										} else {
											// 从数据库加载设备节点状态
											EquStatusViewItem equStatus = node.getAFCNodeInfo(EquStatusViewItem.class);

											// 2.若数据库不存在数据，则节点不在线
											if (equStatus == null) {
												status = getStatus(false, status);
											} else {
												status = getStatus(equStatus.isOnline(), equStatus.getStatus());

											}
											Application.addImagePath(nodeId, status + "");
											item.setStatus(status);

										}
									}

									{
										MetroNode value = (MetroNode) mapitem.getValue();

										// ACC（本级节点）只要通信前置机在线即在线
										if (value instanceof MetroACC) {
											ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
											TreeItem treeitem = mapTreeView.find(root);

											int topStatus = OFF_LINE;
											if (onLine != null && onLine.equals(online)) {
												topStatus = online;
											}
											String imagePath = images.getImagesPath().get(topStatus);
											treeitem.setImage(images.getImages().get(topStatus));
											mapitem.setStatus(topStatus);

											int nodeId = ((MetroACC) value).getAccID();
											if (imagePath != null) {
												Application.addImagePath(AFCApplication.getNodeId(nodeId),
														topStatus + "");
												Application.addImagePath(nodeId, imagePath);

												// 修改节点图标时，同时修改展开和收缩的图标-yang
												root.setExpendIcon(imagePath);
												root.setCloseIcon(imagePath);
											}
										}

										// 线路节点在温州为虚拟节点因此与ACC判断逻辑一致
										if (value instanceof MetroLine) {
											ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
											TreeItem treeitem = mapTreeView.find(root);

											int topStatus = OFF_LINE;
											if (onLine != null && onLine.equals(online)) {
												topStatus = online;
											}
											String imagePath = images.getImagesPath().get(topStatus);
											treeitem.setImage(images.getImages().get(topStatus));
											mapitem.setStatus(topStatus);

											int nodeId = ((MetroLine) value).getLineID();
											if (imagePath != null) {
												Application.addImagePath(AFCApplication.getNodeId(nodeId),
														topStatus + "");
												Application.addImagePath(nodeId, imagePath);

												// 修改节点图标时，同时修改展开和收缩的图标-yang
												root.setExpendIcon(imagePath);
												root.setCloseIcon(imagePath);
											}
										}

										// 车站
										if (value instanceof MetroStation) {
											int status = STATION_OFF;
											int nodeId = ((MetroStation) value).getStationId();

											if (onLine != null && onLine.equals(online)) {
												StationStatustViewItem statusItem = value
														.getAFCNodeInfo(StationStatustViewItem.class);

												if (statusItem != null) {
													status = getStationStatus(statusItem);
													if (statusItem.getStatus() == DeviceStatus.NO_USE) {
														status = STATION_USELESS;
													}

												}
											}
											ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
											TreeItem treeitem = mapTreeView.find(root);

											String imagePath = images.getImagesPath().get(status);
											treeitem.setImage(images.getImages().get(status));
											mapitem.setStatus(status);

											if (imagePath != null) {
												Application.addImagePath(nodeId, imagePath);
												Application.addImagePath(AFCApplication.getNodeId(nodeId), status + "");

												// 修改节点图标时，同时修改展开和收缩的图标-yang
												root.setExpendIcon(imagePath);
												root.setCloseIcon(imagePath);
											}
										}
									}

								}

								// 状态转化为对应的图片信息
								private int getStatus(boolean isOnline, int status) {
									if (isOnline) {
										if (status == DeviceStatus.NORMAL) {// 正常
											return 0;
										} else if (status == DeviceStatus.WARNING) {// 警告
											return 1;
										} else if (status == DeviceStatus.ALARM) {// 报警
											return 2;
										} else if (status == DeviceStatus.OFF_LINE) {// 离线
											return 4;
										} else if (status == DeviceStatus.STOP_SERVICE) {// 停止服务
											return 5;
										} else {
											return 4;
										}
									} else {
										return 4;
									}
								}

								private int getStationStatus(StationStatustViewItem statusItem) {
									long currentMode = statusItem.getMode();
									// 报警阀值
									Integer alarmNum = (Integer) Application.getData(SystemConfigKey.ALARM_THRESHHOLD);
									if (alarmNum == null) {
										alarmNum = 0;
									}
									// 警告阀值
									Integer warningNum = (Integer) Application
											.getData(SystemConfigKey.WARNING_THRESHHOLD);
									if (warningNum == null) {
										warningNum = 0;
									}
									// statusItem.setOnline(true);
									if (statusItem.isOnline()) {
										// 如果车站不属于任何一个降级模式，则车站属于正常模式，即currentmode==0
										if (currentMode == 0) {
											if (statusItem.getAlarmEvent() < alarmNum
													&& statusItem.getAlarmEvent() < warningNum) {
												return 0;
											} else if (statusItem.getAlarmEvent() < alarmNum
													&& statusItem.getAlarmEvent() >= warningNum) {
												return 1;
											} else if (statusItem.getAlarmEvent() >= alarmNum) {
												return 2;
											} else {
												return 3;
											}
										} else {
											if (statusItem.getAlarmEvent() < alarmNum
													&& statusItem.getAlarmEvent() < warningNum) {
												return 4;
											} else if (statusItem.getAlarmEvent() < alarmNum
													&& statusItem.getAlarmEvent() >= warningNum) {
												return 5;
											} else if (statusItem.getAlarmEvent() >= alarmNum) {
												return 6;
											} else {
												return 3;
											}
										}
									} else {
										return 3;
									}
								}
							}));
					map.setRefreshInterval(mapRefreshInterval);
				}

				// 车站状态:具有刷新功能
				{
					ListView view = new ListView(framework, SWT.NONE, StationStatustViewItem.class) {
						@Override
						public int getRefreshInterval() {
							return getInterval();
						}

						private int getInterval() {
							int intervalTime = refreshInterval;

							return intervalTime;
						}
					};
					view.setText("车站状态列表 ");
					view.setWidths(new int[] { 180, 100, 130, 120, 120, 120, 150 });
					framework.addView(view, SWT.BOTTOM);

					NodeStatusRefreshAction refresh = new NodeStatusRefreshAction(view);
					view.setRefreshAction(refresh);
				}

				// 设备状态:具有过滤、刷新功能。
				{
					ListView view = new ListView(framework, SWT.NONE, EquStatusViewItem.class) {
						@Override
						public int getRefreshInterval() {
							return getInterval();
						}

						private int getInterval() {
							int intervalTime = refreshInterval;

							return intervalTime;
						}
					};
					view.setText("设备状态列表 ");
					view.setWidths(new int[] { 170, 150, 150 });
					framework.addView(view, SWT.BOTTOM);

					EquStatusFilterForm filterForm = new EquStatusFilterForm();
					view.setData(EquStatusFilterForm.FORM_KEY, filterForm);

					// 过滤功能
					FilterAction filterAction = new FilterAction(view);
					filterAction.setFilterForm(filterForm);
					filterAction.setFormKey(EquStatusFilterForm.FORM_KEY);
					filterAction.setTreeProvider(treeProvider);
					view.addAction(filterAction);
					// 刷新功能
					EquStatusRefreshAction refreshAction = new EquStatusRefreshAction(view);
					view.setRefreshAction(refreshAction);

				}

				// 模式日志列表：具有刷新功能
				{

					ListView view = new ListView(framework, SWT.NONE, TmoModeUploadInfo.class) {
						@Override
						public int getRefreshInterval() {
							return getInterval();
						}

						private int getInterval() {
							int intervalTime = refreshInterval;

							return intervalTime;
						}
					};
					view.setText("模式上传信息");
					view.setWidths(new int[] { 170, 150, 150, 150 });
					framework.addView(view, SWT.BOTTOM);

					// 刷新
					ModeCmdLogRefreshAction refresh = new ModeCmdLogRefreshAction(view);
					view.setRefreshAction(refresh);

				}

				// 模式广播列表：具有刷新功能
				{
					ListView view = new ListView(framework, SWT.NONE, TmoModeBroadcast.class);
					view.setText("模式广播信息");
					view.setWidths(new int[] { 170, 170, 170, 250, 150 });
					framework.addView(view, SWT.BOTTOM);
					ModeBroadcastRefreshAction refresh = new ModeBroadcastRefreshAction(view);
					view.setRefreshAction(refresh);
					view.setRefreshInterval(refreshInterval);
				}

				// {
				//
				// ListView view = new ListView(framework, SWT.NONE,
				// TmoCmdResult.class) {
				// @Override
				// public int getRefreshInterval() {
				// return getInterval();
				// }
				//
				// private int getInterval() {
				// int intervalTime = refreshInterval;
				// return intervalTime;
				// }
				// };
				// view.setText("命令日志列表");
				// view.setWidths(new int[] { 170, 170, 150, 150, 180 });
				// framework.addView(view, SWT.BOTTOM);
				// List<TmoCmdResult> itemList = new ArrayList<TmoCmdResult>();
				// view.setObjectList(itemList);
				//
				// // 刷新
				// CmdLogRefreshAction refresh = new CmdLogRefreshAction(view);
				// view.setRefreshAction(refresh);
				// }

				// 设备事件列表， 具有筛选，排序，刷新功能
				// by chenyao
				{
					ListView view = new ListView(framework, SWT.NONE, TmoEquStatusCur.class) {
						@Override
						public int getRefreshInterval() {
							return getInterval();
						}

						private int getInterval() {
							int intervalTime = refreshInterval;

							return intervalTime;
						}
					};
					view.setText("设备事件列表");
					framework.addView(view, SWT.BOTTOM);

					EventFilterForm filterForm = new EventFilterForm();
					view.setData(EventFilterForm.FORM_KEY, filterForm);
					// 排序功能
					WZSortAction sortAction = new WZSortAction(view);
					//					sortAction.setViewName(EventRefreshAction.LIST_VIEW_NAME);
					sortAction.setFormKey(EventFilterForm.FORM_KEY);
					sortAction.getFields().add(new PairValue<String, String>("nodeId", "  节点名称/节点编码"));
					sortAction.getFields().add(new PairValue<String, String>("statusName", "  状态名称/状态ID"));
					sortAction.getFields().add(new PairValue<String, String>("statusDesc", "  状态描述/状态值"));
					sortAction.getFields().add(new PairValue<String, String>("applyDevice", "  适用设备"));
					sortAction.getFields().add(new PairValue<String, String>("occurTime", "  发生时间"));
					view.addAction(sortAction);

					// 过滤功能
					WZFilterAction filterAction = new WZFilterAction(view);
					filterAction.setFilterForm(filterForm);
					filterAction.setFormKey(EventFilterForm.FORM_KEY);
					filterAction.setTreeProvider(treeProvider);
					view.addAction(filterAction);
					// 刷新功能
					WZEquEventRefreshAction refreshAction = new WZEquEventRefreshAction(view);
					view.setRefreshAction(refreshAction);
				}
			}
		});
		module.setText(moduleName);
		module.setComposite(EditorFrameWork.class);

		return module;
	}

	private List<Action> getActions() {
		List<Action> actions = new ArrayList<Action>();
		CommonTreeProvider tree = new CommonTreeProvider();
		tree.setDepth(AFCNodeLevel.SC);

		{
			// 运营模式
			WZModeCommandAction action = new WZModeCommandAction();
			action.setTreeProvider(tree);
			action.setLogSysService(logSysService);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_OPERATING_MODE.toString());
			actions.add(action);
		}

		{
			// 模式查询
			ModeQueryAction action = new ModeQueryAction();
			action.setTreeProvider(new CommonTreeProvider());
			action.setLogSysService(logSysService);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_MODE_SEARCH.toString());
			action.setTreeProvider(tree);
			actions.add(action);
		}

		{
			// 时钟同步
			TimeSyncAction action = new TimeSyncAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_TIME_SYN.toString());
			action.setLogSysService(logSysService);
			action.setTreeProvider(tree);
			actions.add(action);
		}
		//
		// {
		// // 选择性命令发送
		// SelectivityCommandSendAction action = new
		// SelectivityCommandSendAction();
		// CommonTreeProvider commonTreeProvider = new CommonTreeProvider();
		// commonTreeProvider.setDepth(AFCNodeLevel.SLE);
		// action.setTreeProvider(commonTreeProvider);
		// action.setID(CDLCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_CONTROL_COMMAND.toString());
		// action.setLogSysService(logSysService);
		// actions.add(action);
		//
		// }
		{
			// 发送控制命令
			CommandFactoryAction action = new CommandFactoryAction();
			action.setLogSysService(logSysService);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_CONTROL_COMMAND.toString());
			// tree.setDepth(AFCNodeLevel.SC);
			CommonTreeProvider deviceTree = new CommonTreeProvider();
			deviceTree.setDepth(AFCNodeLevel.SLE);
			action.setTreeProvider(deviceTree);
			actions.add(action);
		}
		// {
		// // 发送控制命令
		// boolean valid = Application.getUser()
		// .hasFunction(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_CONTROL_COMMAND.toString());
		// CommandFactoryAction action = new CommandFactoryAction();
		// action.setBizLogger(logSysService);
		// action.setTreeProvider(tree);
		// action.setTargetType(AFCNodeLevel.SLE);
		// if (valid) {
		// actions.add(action);
		// }
		// }
		{
			// 设备监控
			WZDeviceMonitorAction action = new WZDeviceMonitorAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_DEVICE_MONITOR.toString());
			actions.add(action);
		}
		//
		// {
		// // 查看寄存器
		// CDRegisterDataAction action = new CDRegisterDataAction();
		// action.setID(CDLCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_REGISTER_DATA.toString());
		// actions.add(action);
		// }
		//
		{
			// 查看钱箱票箱
			WZDeviceStatusAction action = new WZDeviceStatusAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_TICKET_BOX.toString());
			actions.add(action);
		}

		{
			WZRefreshAction action = new WZRefreshAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_REFRESH.toString());
			actions.add(action);
		}
		{
			WZMonitorConfigAction action = new WZMonitorConfigAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_MONITOR_CONFIGURATION.toString());
			action.setLogSysService(logSysService);
			actions.add(action);
		}

		return actions;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public ILogService getLogSysService() {
		return logSysService;
	}

	public void setLogSysService(final ILogService logSysService) {
		this.logSysService = logSysService;
	}
}
