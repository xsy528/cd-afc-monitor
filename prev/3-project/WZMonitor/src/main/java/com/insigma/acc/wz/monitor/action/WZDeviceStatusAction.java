/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.action;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.acc.wz.monitor.dialog.WZDeviceBoxDialog;
import com.insigma.acc.wz.monitor.service.IWZMonitorService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.anotation.ColumnView;

/**
 * Ticket: 钱箱票箱查询
 * @author  mengyifan
 *
 */
public class WZDeviceStatusAction extends NodeAction {

	protected ICommonDAO commonDAO;

	public class DeviceMonitorActionHandler extends ActionHandlerAdapter {

		MetroDevice device = null;
		Short lineId = null;
		Integer stationId = null;
		@Autowire
		private IWZMonitorService monitorService;

		@Override
		public void perform(final ActionContext context) {
			device = (MetroDevice) getNode();
			lineId = AFCApplication.getLineId(device.id());
			stationId = AFCApplication.getStationId(device.id());

			EditorFrameWork frameWork = context.getFrameWork();
			WZDeviceBoxDialog dlg = new WZDeviceBoxDialog(device, frameWork, SWT.NONE);
			dlg.open();
		}
	}

	public WZDeviceStatusAction() {
		setName("查看票箱/钱箱状态");
		setImage("/com/insigma/afc/monitor/images/toolbar/view-status.png");
		setHandler(new DeviceMonitorActionHandler());
	}

	//钱箱查询结果行
	public class DeviceCashBoxViewItem {

		@ColumnView(name = "设备钱箱信息")
		private String deviceCashBoxInfo = "";

		@ColumnView(name = "设备钱箱值")
		private String deviceCashBoxValue = "";

		public String getDeviceCashBoxInfo() {
			return deviceCashBoxInfo;
		}

		public void setDeviceCashBoxInfo(String deviceCashBoxInfo) {
			this.deviceCashBoxInfo = deviceCashBoxInfo;
		}

		public String getDeviceCashBoxValue() {
			return deviceCashBoxValue;
		}

		public void setDeviceCashBoxValue(String deviceCashBoxValue) {
			this.deviceCashBoxValue = deviceCashBoxValue;
		}

	}

	private void addDeviceCashBoxViewItem(List<DeviceCashBoxViewItem> itemList, String deviceBoxInfo,
			Object deviceBoxValue) {
		DeviceCashBoxViewItem item = new DeviceCashBoxViewItem();
		item.setDeviceCashBoxInfo(deviceBoxInfo);
		item.setDeviceCashBoxValue((null != deviceBoxValue ? deviceBoxValue.toString() : "0"));
		itemList.add(item);
	}

	//票箱查询结果行
	public class DeviceTicketBoxViewItem {

		@ColumnView(name = "设备票箱信息")
		private String deviceTicketBoxInfo = "";

		@ColumnView(name = "设备票箱值(张)")
		private String deviceTicketBoxValue = "";

		public String getDeviceTicketBoxInfo() {
			return deviceTicketBoxInfo;
		}

		public void setDeviceTicketBoxInfo(String deviceTicketBoxInfo) {
			this.deviceTicketBoxInfo = deviceTicketBoxInfo;
		}

		public String getDeviceTicketBoxValue() {
			return deviceTicketBoxValue;
		}

		public void setDeviceTicketBoxValue(String deviceTicketBoxValue) {
			this.deviceTicketBoxValue = deviceTicketBoxValue;
		}
	}

	private void addDeviceTicketBoxViewItem(List<DeviceTicketBoxViewItem> itemList, String deviceBoxInfo,
			Object deviceBoxValue) {
		DeviceTicketBoxViewItem item = new DeviceTicketBoxViewItem();
		item.setDeviceTicketBoxInfo(deviceBoxInfo);
		item.setDeviceTicketBoxValue((null != deviceBoxValue ? deviceBoxValue.toString() : "0"));
		itemList.add(item);
	}

}
