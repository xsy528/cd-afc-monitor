/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.acc.monitor.wz.entity.TstNodeStocks;
import com.insigma.acc.monitor.wz.entity.TstTvmBoxStocks;
import com.insigma.acc.wz.define.WZCommandType;
import com.insigma.acc.wz.define.WZDeviceType;
import com.insigma.acc.wz.monitor.handler.WZCommandActionHandler;
import com.insigma.acc.wz.monitor.service.IWZMonitorService;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.FrameWorkViewFolder;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.dialog.StandardDialog;

/**
 * Ticket: 钱箱票箱查询
 * @author  mengyifan
 *
 */
public class WZDeviceBoxDialog extends FrameWorkDialog {
	protected MetroDevice device;
	protected FrameWorkViewFolder views;
	EditorFrameWork editorFrameWork;

	private ListView view1;
	private ListView view2;
	@Autowire
	private IWZMonitorService monitorService;

	private Log logger = LogFactory.getLog(WZDeviceBoxDialog.class);

	public WZDeviceBoxDialog(MetroDevice device, EditorFrameWork parent, int style) {
		super(parent, style | StandardDialog.BUTTONBAR);
		editorFrameWork = parent;
		this.device = device;
		views = new FrameWorkViewFolder(parent, SWT.BORDER);
	}

	@Override
	protected void createAction(List<Action> actions) {
		//		if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
		//				|| device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
		actions.add(new Action("实时查询", queryHandler));
		//		}
		actions.add(new Action("刷新", refreshHandler));
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(Composite parent) {

		setText("查看设备(0x" + Long.toHexString(device.id()) + ")票箱/钱箱信息");
		setTitle("查看设备(0x" + Long.toHexString(device.id()) + ")票箱/钱箱信息");
		setDescription("描述：查看设备(0x" + Long.toHexString(device.id()) + ")票箱/钱箱信息");
		setSize(600, 480);

		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		views.setParent(parent);
		views.setMaximizeVisible(false);
		views.setMinimizeVisible(false);
		views.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		views.setEditorFrameWork(editorFrameWork);

		//根据规范只有TVM和TSM有钱箱
		if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
				|| device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
			//不用的对象别乱new 关闭时shell销毁 而这个对象并未存在在shell内，会无法回收
			this.view1 = new ListView(editorFrameWork, SWT.NONE, DeviceCashBoxViewItem.class);
			view1.setText("设备钱箱信息 ");
			addView(view1);
		}

		//		if (!WZDeviceType.AVM.equals(AFCApplication.getNodeType(device.id()))) {
		//			this.view2 = new ListView(editorFrameWork, SWT.NONE, DeviceTicketBoxViewItem.class);
		//			view2.setText("设备票箱信息 ");
		//			addView(view2);
		//		}

		if (device.getDeviceType() != WZDeviceType.TSM.shortValue()) {
			this.view2 = new ListView(editorFrameWork, SWT.NONE, DeviceTicketBoxViewItem.class);
			view2.setText("设备票箱信息 ");
			addView(view2);
		}

		refreshData(device);
		parent.layout();
	}

	public void addView(FrameWorkView view) {
		this.views.addView(view);
	}

	/**
	 * @return the device
	 */
	public MetroDevice getDevice() {
		return device;
	}

	/**
	 * 实例化
	 */
	private WZCommandActionHandler queryHandler = new WZCommandActionHandler() {
		private final Log logger = LogFactory.getLog(WZCommandActionHandler.class);

		private Object arg = null;

		@Override
		@SuppressWarnings("unchecked")
		public void perform(final ActionContext context) {
			EnhancedThread th = new EnhancedThread("发送设备钱箱查询命令") {
				@Override
				public void error(final Exception e) {
					logger.error("发送设备钱箱查询命令。", e);
				}

				@Override
				public void execute() {
					final List<MetroNode> ids = new ArrayList<MetroNode>();
					ids.add(device);
					if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
							|| device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
						send(context, CommandType.CMD_QUERY_MONEY_BOX, "设备钱箱查询命令", arg, ids,
								AFCCmdLogType.LOG_DEVICE_CMD.shortValue());
					}
					if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
							|| device.getDeviceType() == WZDeviceType.POST.shortValue()
							|| device.getDeviceType() == WZDeviceType.ENG.shortValue()
							|| device.getDeviceType() == WZDeviceType.EXG.shortValue()
							|| device.getDeviceType() == WZDeviceType.REG.shortValue()) {
						send(context, WZCommandType.CMD_QUERY_TICKET_BOX, "设备票箱查询命令", arg, ids,
								AFCCmdLogType.LOG_DEVICE_CMD.shortValue());
					}
					//					else if (device.getDeviceType() == WZDeviceType.POST.shortValue()
					//							|| device.getDeviceType() == WZDeviceType.ENG.shortValue()
					//							|| device.getDeviceType() == WZDeviceType.EXG.shortValue()
					//							|| device.getDeviceType() == WZDeviceType.REG.shortValue()) {
					//						send(context, CommandType.CMD_QUERY_BOM_AGM_TICKET_BOX, "BOM/AGM票箱查询命令", arg, ids,
					//								AFCCmdLogType.LOG_DEVICE_CMD.shortValue());
					//					} 
					//					else if (device.getDeviceType() == WZDeviceType.AVM.shortValue()) {
					//						send(context, CommandType.CMD_QUERY_AVM_MONEY_BOX, "AVM钱箱查询命令", arg, ids,
					//								AFCCmdLogType.LOG_DEVICE_CMD.shortValue());
					//					}

				}

			};
			th.start();
		}
	};
	/**
	 * 实例化
	 */
	private CommandActionHandler refreshHandler = new CommandActionHandler() {
		//		private final Log logger = LogFactory.getLog(CommandActionHandler.class);
		//		private Object arg = null;

		public void perform(final ActionContext context) {
			refreshData(device);

		}
	};

	public void refreshData(MetroDevice device) {
		try {
			monitorService = Application.getService(IWZMonitorService.class);
		} catch (ServiceException e1) {

			e1.printStackTrace();
		}

		List<TstNodeStocks> ticketBoxList = monitorService.getTicketBoxStocks(device.id());

		//设备钱箱信息
		if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
				|| device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
			List<TstTvmBoxStocks> tvmTicketBoxList = monitorService.getTVMTicketBoxStocks(device.id());
			//			view1.setText("设备钱箱信息 ");
			view1.setWidths(new int[] { 220, 120, 140 });
			try {
				List<DeviceCashBoxViewItem> cashBoxViewlist = new ArrayList<DeviceCashBoxViewItem>();
				if (!(tvmTicketBoxList.size() < 1)) {
					TstTvmBoxStocks tvmBoxStocks = tvmTicketBoxList.get(0);
					long coinFirstChief = getAmount(tvmBoxStocks.getFirstChief5jiaoMoney())
							+ getAmount(tvmBoxStocks.getFirstChief10jiaoMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币主找零箱1(元)",
							String.format("%.2f", Double.valueOf(coinFirstChief) / 100) + "");
					long coinSecondChief = getAmount(tvmBoxStocks.getSecondChief5jiaoMoney())
							+ getAmount(tvmBoxStocks.getSecondChief10jiaoMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币主找零箱2(元)",
							String.format("%.2f", Double.valueOf(coinSecondChief) / 100) + "");
					long coinFirstCyc = getAmount(tvmBoxStocks.getFirstCyc5jiaoMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc10jiaoMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币循环找零箱1(元)",
							String.format("%.2f", Double.valueOf(coinFirstCyc) / 100) + "");
					long coinSecondCyc = getAmount(tvmBoxStocks.getSecondCyc5jiaoMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc10jiaoMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币循环找零箱2(元)",
							String.format("%.2f", Double.valueOf(coinSecondCyc) / 100) + "");
					long coinRecycle = getAmount(tvmBoxStocks.getRecycle5jiaoMoney())
							+ getAmount(tvmBoxStocks.getRecycle10jiaoMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币回收箱(元)",
							String.format("%.2f", Double.valueOf(coinRecycle) / 100) + "");
					//					long coinFirstResve = getAmount(tvmBoxStocks.getFirstResve5jiaoMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve10jiaoMoney());
					//					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币钱箱预留箱1(元)",
					//							String.format("%.2f", Double.valueOf(coinFirstResve) / 100) + "");
					//					long coinSecondResve = getAmount(tvmBoxStocks.getSecondResve5jiaoMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve10jiaoMoney());
					//					addDeviceCashBoxViewItem(cashBoxViewlist, "硬币钱箱预留箱2(元)",
					//							String.format("%.2f", Double.valueOf(coinSecondResve) / 100) + "");

					long noteFirstChief = getAmount(tvmBoxStocks.getFirstChief1yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief2yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief5yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief10yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief20yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief50yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstChief100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币主找零箱1(元)",
							String.format("%.2f", Double.valueOf(noteFirstChief) / 100) + "");
					long noteSecondChief = getAmount(tvmBoxStocks.getSecondChief1yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief2yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief5yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief10yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief20yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief50yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondChief100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币主找零箱2(元)",
							String.format("%.2f", Double.valueOf(noteSecondChief) / 100) + "");
					long noteFirstCyc = getAmount(tvmBoxStocks.getFirstCyc1yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc2yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc5yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc10yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc20yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc50yuanMoney())
							+ getAmount(tvmBoxStocks.getFirstCyc100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币循环找零箱1(元)",
							String.format("%.2f", Double.valueOf(noteFirstCyc) / 100) + "");
					long noteSecondCyc = getAmount(tvmBoxStocks.getSecondCyc1yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc2yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc5yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc10yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc20yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc50yuanMoney())
							+ getAmount(tvmBoxStocks.getSecondCyc100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币循环找零箱2(元)",
							String.format("%.2f", Double.valueOf(noteSecondCyc) / 100) + "");
					long noteRecycle = getAmount(tvmBoxStocks.getRecycle1yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle2yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle5yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle10yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle20yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle50yuanMoney())
							+ getAmount(tvmBoxStocks.getRecycle100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币回收箱(元)",
							String.format("%.2f", Double.valueOf(noteRecycle) / 100) + "");
					long noteIgnore = getAmount(tvmBoxStocks.getIgnore1yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore2yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore5yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore10yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore20yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore50yuanMoney())
							+ getAmount(tvmBoxStocks.getIgnore100yuanMoney());
					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币废币箱(元)",
							String.format("%.2f", Double.valueOf(noteIgnore) / 100) + "");
					//					long noteFirstResve = getAmount(tvmBoxStocks.getFirstResve1yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve2yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve5yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve10yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve20yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve50yuanMoney())
					//							+ getAmount(tvmBoxStocks.getFirstResve100yuanMoney());
					//					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币钱箱预留箱1(元)",
					//							String.format("%.2f", Double.valueOf(noteFirstResve) / 100) + "");
					//					long noteSecondResve = getAmount(tvmBoxStocks.getSecondResve1yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve2yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve5yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve10yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve20yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve50yuanMoney())
					//							+ getAmount(tvmBoxStocks.getSecondResve100yuanMoney());
					//					addDeviceCashBoxViewItem(cashBoxViewlist, "纸币钱箱预留箱2(元)",
					//							String.format("%.2f", Double.valueOf(noteSecondResve) / 100) + "");
				}
				view1.setObjectList(cashBoxViewlist);
			} catch (Exception e) {
				logger.error("设备钱箱信息列表获取失败。", e);
			}

			//ISM无票箱
			if (device.getDeviceType() != WZDeviceType.TSM.shortValue()) {
				//				view2.setText("设备票箱信息 ");
				view2.setWidths(new int[] { 220, 120, 140 });
				try {
					List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<DeviceTicketBoxViewItem>();
					if (!(tvmTicketBoxList.size() < 1)) {
						TstTvmBoxStocks tvmBoxStocks = tvmTicketBoxList.get(0);
						addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱1中票卡数量(张)",
								getAmount(tvmBoxStocks.getFirstTicketQuantity()) + "");
						addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱2中票卡数量(张)",
								getAmount(tvmBoxStocks.getSecondTicketQuantity()) + "");
						addDeviceTicketBoxViewItem(ticketBoxViewlist, "废票箱中票卡数量(张)",
								getAmount(tvmBoxStocks.getThirdTicketQuantity()) + "");
					}
					view2.setObjectList(ticketBoxViewlist);
				} catch (Exception e) {
					logger.error("设备票箱信息列表获取失败。", e);
				}
			}

		} else {
			//				view2.setText("设备票箱信息 ");
			view2.setWidths(new int[] { 220, 120, 140 });
			try {
				List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<DeviceTicketBoxViewItem>();
				if (!(ticketBoxList.size() < 1)) {
					TstNodeStocks ticketBoxStock = ticketBoxList.get(0);
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱1中票卡数量(张)",
							getAmount(ticketBoxStock.getInfoItem0()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱2中票卡数量(张)",
							getAmount(ticketBoxStock.getInfoItem1()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "回收票箱1中票卡数量(张)",
							getAmount(ticketBoxStock.getInfoItem2()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "回收票箱2中票卡数量(张)",
							getAmount(ticketBoxStock.getInfoItem3()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "废票箱中票卡数量(张)",
							getAmount(ticketBoxStock.getInfoItem4()) + "");
				}
				view2.setObjectList(ticketBoxViewlist);
			} catch (Exception e) {
				logger.error("设备票箱信息列表获取失败。", e);
			}
		}
	}

	private Long getAmount(Long amount) {
		return amount == null ? 0 : amount;
	}

	//钱箱查询结果行
	public class DeviceCashBoxViewItem {

		@ColumnView(name = "设备钱箱信息", sortAble = false)
		private String deviceCashBoxInfo = "";

		@ColumnView(name = "设备钱箱值", sortAble = false)
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

		@ColumnView(name = "设备票箱信息", sortAble = false)
		private String deviceTicketBoxInfo = "";

		@ColumnView(name = "设备票箱值(张)", sortAble = false)
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

	public static void main(String[] args) {
		System.out.println(100 / 100);

	}

}
