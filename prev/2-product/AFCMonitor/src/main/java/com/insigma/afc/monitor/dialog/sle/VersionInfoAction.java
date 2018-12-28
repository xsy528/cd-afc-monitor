package com.insigma.afc.monitor.dialog.sle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.monitor.service.IVersionInfoService;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.dialog.TableViewDialog;
import com.insigma.commons.spring.Autowire;

public class VersionInfoAction extends NodeAction {

	public VersionInfoAction() {
		setName("设备参数版本信息");
		setImage("/com/insigma/afc/monitor/images/toolbar/device-monitor.png");
		setHandler(new DeviceStatusInfoHandler());
	}

	public class DeviceStatusInfoHandler extends ActionHandlerAdapter {

		@Autowire
		private IVersionInfoService monitorService;

		public void perform(Action action) {

			MetroDevice device = (MetroDevice) getNode();

			List<VersionItem> viewItem = getDeviceEODVersionViewItemList(device.getId().getLineId(),
					device.getId().getStationId(), device.getId().getDeviceId());

			TableViewDialog dlg = new TableViewDialog(action.getFrameWork(), SWT.BAR);
			dlg.setText("设备参数版本信息");
			dlg.setTitle("设备参数版本信息");
			dlg.setDescription("描述：查看设备编号("
					+ String.format(Application.getDeviceIdFormat(), device.getId().getDeviceId()).toUpperCase()
					+ ")运行参数版本信息");
			dlg.setSize(600, 480);
			dlg.setDataList(viewItem);
			dlg.open();

		}

		private List<VersionItem> getDeviceEODVersionViewItemList(short lineId, int stationId, long deviceId) {

			List<VersionItem> result = null;

			Object[] eodVersionInfo = null;

			try {
				eodVersionInfo = monitorService.getTapEodStationVersionInquirys(lineId, stationId, deviceId);
			} catch (Exception e) {
				logger.error("票箱信息获取失败。", e);
			}

			if (null != eodVersionInfo) {
				result = new ArrayList<VersionItem>();
				result.add(new VersionItem("清分中心级当前参数版本", String.valueOf(eodVersionInfo[0])));
				result.add(new VersionItem("清分中心级将来参数版本", String.valueOf(eodVersionInfo[1])));
				result.add(new VersionItem("线路中心级当前参数版本", String.valueOf(eodVersionInfo[2])));
				result.add(new VersionItem("线路中心级将来参数版本", String.valueOf(eodVersionInfo[3])));
				result.add(new VersionItem("黑名单版本", String.valueOf(eodVersionInfo[4])));
				result.add(new VersionItem("模式履历版本", String.valueOf(eodVersionInfo[5])));
			}
			return result;
		}
	}
}
