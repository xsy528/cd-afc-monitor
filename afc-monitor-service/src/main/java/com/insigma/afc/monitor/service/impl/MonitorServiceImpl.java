/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCDeviceType;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.dao.TstNodeStocksDao;
import com.insigma.afc.monitor.dao.TstTvmBoxStocksDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.model.vo.DeviceCashBoxViewItem;
import com.insigma.afc.monitor.model.vo.DeviceModuleItem;
import com.insigma.afc.monitor.model.vo.DeviceTicketBoxViewItem;
import com.insigma.afc.monitor.model.vo.Location;
import com.insigma.afc.monitor.service.IDeviceModuleFactory;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.MonitorService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ticket: 监视服务
 * @author  mengyifan
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceImpl.class);

	private TstTvmBoxStocksDao tstTvmBoxStocksDao;
	private TstNodeStocksDao tstNodeStocksDao;

	private IDeviceModuleFactory deviceModuleFactory;
	private IMetroNodeStatusService statusService;
	private TopologyService topologyService;

	@Autowired
	public MonitorServiceImpl(TstTvmBoxStocksDao tstTvmBoxStocksDao, TstNodeStocksDao tstNodeStocksDao,
							  TopologyService topologyService, IDeviceModuleFactory deviceModuleFactory,
							  IMetroNodeStatusService statusService){
		this.tstTvmBoxStocksDao = tstTvmBoxStocksDao;
		this.tstNodeStocksDao = tstNodeStocksDao;
		this.topologyService = topologyService;
		this.deviceModuleFactory = deviceModuleFactory;
		this.statusService = statusService;
	}

	@Override
	public TstTvmBoxStocks getTVMTicketBoxStocks(long deviceId) {
		Optional<TstTvmBoxStocks> tstTvmBoxStocksOptional = tstTvmBoxStocksDao.findById(deviceId);
		return tstTvmBoxStocksOptional.orElse(null);
	}

	@Override
	public TstNodeStocks getTicketBoxStocks(long deviceId) {
		Optional<TstNodeStocks> tstNodeStocksOptional = tstNodeStocksDao.findById(deviceId);
		return tstNodeStocksOptional.orElse(null);
	}

	@Override
	public Result getDeviceDetail(long id){
		Result<MetroDevice> result = topologyService.getDeviceNode(id);
		if (!result.isSuccess()){
			LOGGER.error(result.getMessage());
			throw new ServiceException("获取设备节点异常");
		}
		MetroDevice equipment = result.getData();
		//模块信息
		List<MetroDeviceModule> subNodes = deviceModuleFactory.getMetroDeviceModuleList(equipment);

		//表格数据
		List<DeviceModuleItem> viewItems = new ArrayList<>();
		for (MetroDeviceModule node : subNodes) {
			AFCNodeLocation location = node.getTextLocation();
			DeviceModuleItem deviceModuleItem = new DeviceModuleItem(node.getModuleName(), node.getStatus(),
					node.getRemark(),new Location(location.getX(),location.getY(),0));
			viewItems.add(deviceModuleItem);
		}
		Map<String,Object> data = new HashMap<>();
		data.put("station",equipment.getStationName());
		data.put("device", topologyService.getNodeText(equipment.getDeviceId()));

		//设备状态
		Short itemStatus = DeviceStatus.OFF_LINE;
		try {
			//从itemStatus表中获取设备状态
			TmoItemStatus tmoItemStatus = statusService.getTmoItemStatus(false, id);

			if (tmoItemStatus != null && tmoItemStatus.getItemActivity()) {
				itemStatus = tmoItemStatus.getItemStatus();
			}
		} catch (Exception e) {
			LOGGER.error("查询设备状态失败", e);
		}
		String statusStr = DeviceStatus.getInstance().getNameByValue(itemStatus);
		data.put("status", statusStr);
		//设备底图
		//int imgUrl = fileService.getResourceIndex(equipment.getPicName().substring(equipment.getPicName().lastIndexOf("/")+1));
		//data.put("imgUrl",imgUrl);
		data.put("modules",viewItems);
		return Result.success(data);
	}

	@Override
	public Result getBoxDetail(long id) {

		MetroDevice device = topologyService.getDeviceNode(id).getData();
		if (device==null){
			return Result.error(ErrorCode.NODE_NOT_EXISTS);
		}
		Optional<TstNodeStocks> tstNodeStocksOptional = tstNodeStocksDao.findById(id);
		Map<String,Object> data = new HashMap<>();
		//设备钱箱信息,根据规范只有TVM和TSM有钱箱
		if (AFCDeviceType.TVM.equals(device.getDeviceType())) {
			Optional<TstTvmBoxStocks> tstTvmBoxStocksOptional = tstTvmBoxStocksDao.findById(id);
			try {
				List<DeviceCashBoxViewItem> cashBoxViewlist = new ArrayList<>();
				if (tstTvmBoxStocksOptional.isPresent()) {
					TstTvmBoxStocks tvmBoxStocks = tstTvmBoxStocksOptional.get();
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
				}
				data.put("cashBoxList",cashBoxViewlist);
			} catch (Exception e) {
				LOGGER.error("设备钱箱信息列表获取失败。", e);
			}

			try {
				List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<>();
				if (tstTvmBoxStocksOptional.isPresent()) {
					TstTvmBoxStocks tvmBoxStocks = tstTvmBoxStocksOptional.get();
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱1中票卡数量(张)",
							getAmount(tvmBoxStocks.getFirstTicketQuantity()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "发售票箱2中票卡数量(张)",
							getAmount(tvmBoxStocks.getSecondTicketQuantity()) + "");
					addDeviceTicketBoxViewItem(ticketBoxViewlist, "废票箱中票卡数量(张)",
							getAmount(tvmBoxStocks.getThirdTicketQuantity()) + "");
				}
				data.put("ticketBoxList",ticketBoxViewlist);
			} catch (Exception e) {
				LOGGER.error("设备票箱信息列表获取失败。", e);
			}
		} else {
			try {
				List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<>();
				if (tstNodeStocksOptional.isPresent()) {
					TstNodeStocks ticketBoxStock = tstNodeStocksOptional.get();
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
				data.put("ticketBoxList",ticketBoxViewlist);
			} catch (Exception e) {
				LOGGER.error("设备票箱信息列表获取失败。", e);
			}
		}
		if (data.isEmpty()){
			return Result.error(ErrorCode.UNKNOW_ERROR);
		}
		return Result.success(data);
	}


	private Long getAmount(Long amount) {
		return amount == null ? 0 : amount;
	}

	private void addDeviceCashBoxViewItem(List<DeviceCashBoxViewItem> itemList, String deviceBoxInfo,
										  Object deviceBoxValue) {
		DeviceCashBoxViewItem item = new DeviceCashBoxViewItem();
		item.setDeviceCashBoxInfo(deviceBoxInfo);
		item.setDeviceCashBoxValue((null != deviceBoxValue ? deviceBoxValue.toString() : "0"));
		itemList.add(item);
	}

	private void addDeviceTicketBoxViewItem(List<DeviceTicketBoxViewItem> itemList, String deviceBoxInfo,
											Object deviceBoxValue) {
		DeviceTicketBoxViewItem item = new DeviceTicketBoxViewItem();
		item.setDeviceTicketBoxInfo(deviceBoxInfo);
		item.setDeviceTicketBoxValue((null != deviceBoxValue ? deviceBoxValue.toString() : "0"));
		itemList.add(item);
	}
}
