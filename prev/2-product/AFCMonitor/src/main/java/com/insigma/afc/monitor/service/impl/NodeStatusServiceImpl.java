package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.model.define.WZDeviceType;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.Location;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.TstNodeStocks;
import com.insigma.afc.monitor.model.entity.TstTvmBoxStocks;
import com.insigma.afc.monitor.model.vo.DeviceModuleItem;
import com.insigma.afc.monitor.service.FileService;
import com.insigma.afc.monitor.service.IWZMonitorService;
import com.insigma.afc.monitor.service.NodeStatusService;
import com.insigma.afc.monitor.util.NodeUtils;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.service.IDeviceModuleFactory;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.CommonDAO;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-03:10:39
 */
@Service
public class NodeStatusServiceImpl implements NodeStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusServiceImpl.class);

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private IDeviceModuleFactory iDeviceModuleFactory;

    @Autowired
    private IMetroNodeStatusService statusService;

    @Autowired
    private FileService fileService;

    @Autowired
    private IWZMonitorService monitorService;

    @Override
    public List<TmoModeUploadInfo> getModeUpload(long nodeId){
        List<TmoModeUploadInfo> tmoModeUploadInfos = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        QueryFilter filters = new QueryFilter();
        filters.propertyFilter("modeUploadTime", calendar.getTime(), ">=");
        filters.propertyFilter("modeUploadTime", new Date(), "<=");
        filters.addOrderBy("modeUploadTime", false);
        AFCNodeLevel nodeLevel = NodeUtils.getNodeLevel(nodeId);
        if (nodeLevel != null) {
            switch (nodeLevel) {
                case LC:
                    filters.propertyFilter("lineId", (short) nodeId);
                    break;
                case SC:
                    filters.propertyFilter("stationId", (int) nodeId);
                    break;
                default:
                    break;
            }
        }
        try{
            tmoModeUploadInfos = commonDAO.fetchListByFilter(filters, TmoModeUploadInfo.class);
        }catch (OPException e){
            LOGGER.error("查询模式上传信息异常",e);
        }
        return tmoModeUploadInfos;
    }

    @Override
    public List<TmoModeBroadcast> getModeBroadcast(){
        QueryFilter filters = new QueryFilter();
        Date date = DateTimeUtil.beginDate(new Date());
        filters.propertyFilter("modeBroadcastTime",date , ">=");
        filters.addOrderBy("modeBroadcastTime", true);
        filters.addOrderBy("targetId", true);
        List<TmoModeBroadcast> tmoModeBroadcasts = new ArrayList<>();
        try{
            tmoModeBroadcasts = commonDAO.fetchListByFilter(filters, TmoModeBroadcast.class);
        }catch (OPException e){
            LOGGER.error("查询模式上传信息异常",e);
        }
        return tmoModeBroadcasts;
    }

    public Result getDeviceDetail(long id){
        //模块信息
        List<MetroDeviceModule> subNodes = null;
        MetroNode metroNode = AFCApplication.getNode(id);
        if (metroNode==null||metroNode.level()!=AFCNodeLevel.SLE){
            return Result.error(ErrorCode.NODE_NOT_EXISTS);
        }
        MetroDevice equipment = (MetroDevice) metroNode;
        try {
            subNodes = iDeviceModuleFactory.getMetroDeviceModuleList(equipment);
        } catch (Exception e) {
            //初始化部件信息异常
        }

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
        data.put("device",NodeUtils.getNodeText(equipment.getId().getDeviceId(),AFCNodeLevel.SLE));

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
        int imgUrl = fileService.getResourceIndex(equipment.getPicName()
                .substring(equipment.getPicName().lastIndexOf("/")+1));
        data.put("imgUrl",imgUrl);
        data.put("modules",viewItems);
        return Result.success(data);
    }

    @Override
    public Result getBoxDetail(long id) {
        List<TstNodeStocks> ticketBoxList = monitorService.getTicketBoxStocks(id);
        MetroNode metroNode = AFCApplication.getNode(id);
        if (metroNode==null||metroNode.level()!=AFCNodeLevel.SLE){
            return Result.error(ErrorCode.NODE_NOT_EXISTS);
        }
        Map<String,Object> data = new HashMap<>();
        MetroDevice device = (MetroDevice)metroNode;
        //设备钱箱信息,根据规范只有TVM和TSM有钱箱
        if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
                || device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
            List<TstTvmBoxStocks> tvmTicketBoxList = monitorService.getTVMTicketBoxStocks(device.id());
            try {
                List<DeviceCashBoxViewItem> cashBoxViewlist = new ArrayList<>();
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

            //ISM无票箱
            if (device.getDeviceType() != WZDeviceType.TSM.shortValue()) {
                try {
                    List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<>();
                    if (!(tvmTicketBoxList.size() < 1)) {
                        TstTvmBoxStocks tvmBoxStocks = tvmTicketBoxList.get(0);
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
            }
        } else {
            try {
                List<DeviceTicketBoxViewItem> ticketBoxViewlist = new ArrayList<>();
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

    //钱箱查询结果行
    public class DeviceCashBoxViewItem {

        private String deviceCashBoxInfo = "";

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

        private String deviceTicketBoxInfo = "";

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

    public void setCommonDAO(CommonDAO commonDAO){
        this.commonDAO = commonDAO;
    }
    public void setiDeviceModuleFactory(IDeviceModuleFactory iDeviceModuleFactory){
        this.iDeviceModuleFactory = iDeviceModuleFactory;
    }
    public void setStatusService(IMetroNodeStatusService statusService){
        this.statusService = statusService;
    }
    public void setFileService(FileService fileService){
        this.fileService = fileService;
    }
    public void setMonitorService(IWZMonitorService monitorService){
        this.monitorService = monitorService;
    }
}
