package com.insigma.acc.wz.monitor.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.acc.wz.monitor.service.IWZModeService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.monitor.dialog.MetroNodeMonitorDialog;
import com.insigma.afc.monitor.dialog.monitor.ComponentView;
import com.insigma.afc.monitor.entity.TmoEquStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.entity.TmoItemStatus;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.service.IMetroEventService;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.ui.MessageForm;

public class WZDeviceMonitorAction extends NodeAction {

	private IMetroNodeStatusService statusService;

	private IWZModeService modeService;

	protected ICommonDAO commonDAO;

	public class DeviceMonitorActionHandler extends ActionHandlerAdapter {

		MetroDevice device = null;

		@Override
		public void perform(final ActionContext context) {
			MapItem mapItem = null;
			GraphicItem gitem = (GraphicItem) context.getData(ActionContext.SELECTED_ITEM);
			if (gitem != null) {
				mapItem = gitem.getParent();
			}
			if (mapItem == null) {
				if (getNode() instanceof MetroDevice) {
					device = (MetroDevice) getNode();
				}
			} else if (mapItem.getValue() instanceof MetroDevice) {
				device = (MetroDevice) mapItem.getValue();
			}

			if (device == null) {
				return;
			}

			final MetroDevice deviceNode = device;
			EditorFrameWork frameWork = context.getFrameWork();
			MetroNodeMonitorDialog dlg = new MetroNodeMonitorDialog(device, frameWork, SWT.NONE);

			try {
				statusService = Application.getService(IMetroNodeStatusService.class);
			} catch (ServiceException e) {
				logger.error("IMetroNodeStatusService加载失败", e);
			}

			try {
				modeService = Application.getService(IWZModeService.class);
			} catch (ServiceException e) {
				logger.error("IWZModeService加载失败", e);
			}

			Short itemStatus = DeviceStatus.OFF_LINE;
			try {
				//从itemStatus表中获取设备状态
				TmoItemStatus tmoItemStatus = statusService.getTmoItemStatus(false, device.getNodeId());

				if (tmoItemStatus != null && tmoItemStatus.getItemActivity()) {
					itemStatus = tmoItemStatus.getItemStatus();

				}
			} catch (Exception e) {
				logger.error("查询设备状态失败", e);
				MessageForm.Message("错误信息", "数据库连接异常。", SWT.ICON_ERROR);
				return;
			}

			String statusStr = DeviceStatus.getInstance().getNameByValue(itemStatus);

			String modeStr = null;
			Short mode = 1;

			TmoItemStatus currentTmoItemMode = modeService.getCurrentTmoItemMode(device.getNodeId());
			if (currentTmoItemMode != null) {
				if (currentTmoItemMode.getCurrentModeCode() != null) {
					mode = currentTmoItemMode.getCurrentModeCode();
				}
				modeStr = AFCModeCode.getInstance().getNameByValue(mode.intValue());
			} else {
				modeStr = "未知模式";
			}

			dlg.setDescription("描述：设备[" + AFCApplication.getNodeName(device.getNodeId()) + "]为" + statusStr + "状态");

			{
				final ComponentView view = new ComponentView(frameWork, SWT.NONE);
				view.setText("设备信息");
				dlg.addView(view);
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						view.setEquipment(deviceNode);
					}
				});
			}
			{
				final ListView view = new ListView(frameWork, SWT.NONE, TmoEquStatus.class);
				view.setText("设备状态信息 ");
				view.setWidths(new int[] { 180, 120, 120, 120, 150 });
				dlg.addView(view);
				Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						EquStatusFilterForm filerForm = new EquStatusFilterForm();
						List<Object> selections = new ArrayList<Object>();
						selections.add(device);
						filerForm.setSelections(selections);
						filerForm.setPageSize(200);
						List<TmoEquStatusCur> nextLines = new ArrayList<TmoEquStatusCur>();
						try {
							IMetroEventService service = Application.getService(IMetroEventService.class);
							PageHolder page = service.getEquStatusList(filerForm, 0);
							nextLines = page.getDatas();
							view.setObjectList(nextLines);
						} catch (Exception e) {
							logger.error("设备状态信息列表获取失败。", e);
						}
					}
				});
			}
			//			{
			//				final ListView view = new ListView(frameWork, SWT.NONE, TmoEquEvent.class);
			//				view.setText("事件列表 ");
			//				view.setWidths(new int[] { 180, 180, 180, 150 });
			//				dlg.addView(view);
			//				Display.getCurrent().asyncExec(new Runnable() {
			//
			//					@Override
			//					public void run() {
			//						EventFilterForm filerForm = new EventFilterForm();
			//						List<Object> selections = new ArrayList<Object>();
			//						selections.add(device);
			//						filerForm.setSelections(selections);
			//						filerForm.setPageSize(200);
			//						List<TmoEquEventCur> nextLines = new ArrayList<TmoEquEventCur>();
			//						try {
			//							IMetroEventService service = Application.getService(IMetroEventService.class);
			//							PageHolder page = service.getEventCurList(filerForm, 0);
			//							nextLines = page.getDatas();
			//							view.setObjectList(nextLines);
			//						} catch (Exception e) {
			//							logger.error("设备事件列表获取失败。", e);
			//						}
			//					}
			//				});
			//			}
			//			{
			//				final ListView view = new ListView(frameWork, SWT.NONE, TmoModeUploadInfo.class);
			//				view.setText("设备模式列表 ");
			//				view.setWidths(new int[] { 185, 140, 140, 270, 150 });
			//				dlg.addView(view);
			//				Display.getCurrent().asyncExec(new Runnable() {
			//
			//					@Override
			//					public void run() {
			//
			//						List<TmoModeUploadInfo> nextLines = new ArrayList<TmoModeUploadInfo>();
			//						try {
			//							IModeService modeService = Application.getService(IModeService.class);
			//
			//							long nodeId = device.getId().getDeviceId();
			//							nextLines = modeService.getModeUploadInfoList(null, null, new Long[] { nodeId }, null, null,
			//									null, null, 1, 20);
			//							TmoModeUploadInfo tmoModeUploadInfo = new TmoModeUploadInfo();
			//							//将节点所拥有的本车站的模式信息置顶
			//							for (int i = 0; i < nextLines.size(); i++) {
			//								if (nextLines.get(i).getLineId() == AFCApplication.getLineId(nodeId)
			//										&& nextLines.get(i).getStationId() == AFCApplication.getStationId(nodeId)) {
			//									tmoModeUploadInfo = nextLines.get(i);
			//									nextLines.remove(i);
			//									nextLines.add(0, tmoModeUploadInfo);
			//									break;
			//								}
			//
			//							}
			//
			//							view.setObjectList(nextLines);
			//							//隐藏节点编号那一列
			//							{
			//								TableColumn tc = view.getTable().getColumn(0);
			//								view.getTable().getColumn(0).setWidth(0);
			//								view.getTable().getColumn(0).setResizable(false);
			//							}
			//
			//						} catch (Exception e) {
			//							logger.error("设备模式广播列表获取失败。", e);
			//						}
			//					}
			//				});
			//			}

			dlg.open();
		}
	}

	public WZDeviceMonitorAction() {
		setName("监视设备");
		setImage("/com/insigma/afc/monitor/images/toolbar/device-monitor.png");
		setHandler(new DeviceMonitorActionHandler());
	}
}
