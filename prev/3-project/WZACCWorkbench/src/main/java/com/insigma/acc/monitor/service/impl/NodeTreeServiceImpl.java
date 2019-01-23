package com.insigma.acc.monitor.service.impl;

import com.insigma.acc.monitor.model.dto.ImageLocation;
import com.insigma.acc.monitor.model.dto.NodeItem;
import com.insigma.acc.monitor.model.dto.Result;
import com.insigma.acc.monitor.model.dto.TextLocation;
import com.insigma.acc.monitor.service.FileService;
import com.insigma.acc.monitor.service.NodeTreeService;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.dic.AFCDeviceSubType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.*;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Ticket: 节点服务实现类
 *
 * @author xuzhemin
 * 2019-01-22:11:19
 */
public class NodeTreeServiceImpl implements NodeTreeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeTreeServiceImpl.class);

    private IMetroNodeService iMetroNodeService;
    private IMetroNodeStatusService iMetroNodeStatusService;
    private FileService fileService;

    private static final int ONLINE = 0;
    private static final int OFFLINE = 1;
    private static int STATION_OFF = 3;
    private static int STATION_USELESS = 9;

    @Override
    public Result<NodeItem> getEditorNodeTree() {
        NodeItem acc;
        //获取ACC根节点
        MetroACC metroACC = iMetroNodeService.getMetroACC();
        acc = metroNodeToNodeItem(metroACC);
        if (acc==null){
            return Result.success(acc);
        }
        //获取线路节点
        List<MetroLine> metroLines = iMetroNodeService.getAllMetroLine();
        if (metroLines==null||metroLines.isEmpty()){
            return Result.success(acc);
        }
        List<NodeItem> lines = new ArrayList<>();
        for(MetroLine metroLine:metroLines) {
            NodeItem line = metroNodeToNodeItem(metroLine);
            line.setPid(acc.getNodeId());
            lines.add(line);
            //获取车站节点
            List<MetroStation> metroStations = iMetroNodeService.getMetroStation(metroLine.getLineID());
            if (metroStations==null||metroStations.isEmpty()){
                continue;
            }
            List<NodeItem> stations = new ArrayList<>();
            for(MetroStation metroStation:metroStations){
                NodeItem station = metroNodeToNodeItem(metroStation);
                station.setPid(line.getNodeId());
                stations.add(station);
                List<NodeItem> devices = new ArrayList<>();
                //获取设备节点
                List<MetroDevice> metroDevices = iMetroNodeService.getMetroDevice(metroStation.getId().getStationId());
                if (metroDevices==null||metroDevices.isEmpty()){
                    continue;
                }
                for (MetroDevice metroDevice:metroDevices){
                    NodeItem device = metroNodeToNodeItem(metroDevice);
                    device.setPid(station.getNodeId());
                    devices.add(device);
                }
                station.setSubItems(devices);
            }
            line.setSubItems(stations);
        };
        acc.setSubItems(lines);
        return Result.success(acc);
    }

    @Override
    public Result<NodeItem> getMonitorNodeTree() {
        //获取ACC根节点
        MetroACC metroACC = iMetroNodeService.getMetroACC();
        NodeItem acc = metroNodeToNodeItem(metroACC);
        if (acc==null){
            return Result.success(acc);
        }
        // 通信前置机是否在线
        Number onLine = (Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
        int topStatus = OFFLINE;
        if (onLine != null && onLine.equals(ONLINE)) {
            topStatus = ONLINE;
        }
        acc.setStatus(topStatus);
        //获取线路节点
        List<MetroLine> metroLines = iMetroNodeService.getAllMetroLine();
        if (metroLines==null||metroLines.isEmpty()){
            return Result.success(acc);
        }
        List<NodeItem> lines = new ArrayList<>();
        for(MetroLine metroLine:metroLines) {
            if (metroLine.getStatus().shortValue()==1){
                //线路未启用，无需显示
                continue;
            }
            NodeItem line = metroNodeToNodeItem(metroLine);
            line.setStatus(topStatus);
            line.setPid(acc.getNodeId());
            lines.add(line);
            //获取车站节点
            List<MetroStation> metroStations = iMetroNodeService.getMetroStation(metroLine.getLineID());
            if (metroStations==null||metroStations.isEmpty()){
                continue;
            }
            //从数据库获取车站状态
            List<StationStatustViewItem> stationStatustViewItems = iMetroNodeStatusService
                    .getAllStationStatusView(metroStations);
            Map<Long,StationStatustViewItem> stationStatustViewItemMap = new HashMap<>();
            stationStatustViewItems.forEach(stationStatustViewItem ->
                    stationStatustViewItemMap.put(stationStatustViewItem.getNodeId(),stationStatustViewItem));

            List<NodeItem> stations = new ArrayList<>();
            for(MetroStation metroStation:metroStations){
                if (metroStation.getStatus().shortValue()==1){
                    //车站未启用，无需显示
                    continue;
                }
                NodeItem station = metroNodeToNodeItem(metroStation);
                int status = STATION_OFF;
                if (onLine != null && onLine.equals(ONLINE)) {
                    StationStatustViewItem statusItem = stationStatustViewItemMap.get(station.getNodeId());
                    if (statusItem != null) {
                        status = getStationStatus(statusItem);
                        if (statusItem.getStatus() == DeviceStatus.NO_USE) {
                            status = STATION_USELESS;
                        }
                    }
                }
                station.setStatus(status);
                station.setPid(line.getNodeId());
                stations.add(station);
                List<NodeItem> devices = new ArrayList<>();
                //获取设备节点
                List<MetroDevice> metroDevices = iMetroNodeService.getMetroDevice(metroStation.getId().getStationId());
                if (metroDevices==null||metroDevices.isEmpty()){
                    continue;
                }
                // 从数据库加载设备节点状态
                EquStatusFilterForm filterForm = new EquStatusFilterForm();
                List<Short> statusLevelList = new ArrayList<>();
                DeviceStatus.getInstance().getByGroup("1").forEach(pv->statusLevelList.add((short)pv.getKey()));
                filterForm.setStatusLevelList(statusLevelList);
                List<Object> deviceIds = new ArrayList<>();
                metroDevices.forEach(metroDevice -> deviceIds.add(metroDevice.getId().getDeviceId()));
                filterForm.setSelections(deviceIds);

                List<EquStatusViewItem> equStatusViewItems = iMetroNodeStatusService.getEquStatusView(filterForm);
                Map<Long,EquStatusViewItem> equStatusViewItemMap = new HashMap<>();
                equStatusViewItems.forEach(equStatusViewItem ->
                        equStatusViewItemMap.put(equStatusViewItem.id(),equStatusViewItem));
                for (MetroDevice metroDevice:metroDevices){
                    if (metroDevice.getStatus().shortValue() == 1) {
                        //设备未启用不加载
                        continue;
                    }
                    NodeItem device = metroNodeToNodeItem(metroDevice);
                    status = DeviceStatus.OFF_LINE;
                    // 1.如果通信前置机不在线，则节点不在线
                    if (onLine != null && !onLine.equals(ONLINE)) {
                        status = getStatus(false, status);
                    } else {
                        EquStatusViewItem equStatus = equStatusViewItemMap.get(device.getNodeId());
                        // 2.若数据库不存在数据，则节点不在线
                        if (equStatus == null) {
                            status = getStatus(false, status);
                        } else {
                            status = getStatus(equStatus.isOnline(), equStatus.getStatus());
                        }
                    }
                    device.setStatus(status);
                    device.setPid(station.getNodeId());
                    devices.add(device);
                }
                station.setSubItems(devices);
            }
            line.setSubItems(stations);
        };
        acc.setSubItems(lines);
        return Result.success(acc);
    }

    @Override
    public Result<NodeItem> getNodeGroupDeviceTree() {
        NodeItem acc;
        //获取ACC根节点
        MetroACC metroACC = iMetroNodeService.getMetroACC();
        acc = metroNodeToNodeItem(metroACC);
        if (acc==null){
            return Result.success(acc);
        }
        //获取线路节点
        List<MetroLine> metroLines = iMetroNodeService.getAllMetroLine();
        if (metroLines==null||metroLines.isEmpty()){
            return Result.success(acc);
        }
        List<NodeItem> lines = new ArrayList<>();
        for(MetroLine metroLine:metroLines) {
            if (metroLine.getStatus().shortValue()==1){
                //线路未启用，无需显示
                continue;
            }
            NodeItem line = metroNodeToNodeItem(metroLine);
            line.setPid(acc.getNodeId());
            lines.add(line);
            //获取车站节点
            List<MetroStation> metroStations = iMetroNodeService.getMetroStation(metroLine.getLineID());
            if (metroStations==null||metroStations.isEmpty()){
                continue;
            }
            List<NodeItem> stations = new ArrayList<>();
            for(MetroStation metroStation:metroStations){
                if (metroStation.getStatus().shortValue()==1){
                    //车站未启用，无需显示
                    continue;
                }
                NodeItem station = metroNodeToNodeItem(metroStation);
                station.setPid(line.getNodeId());
                stations.add(station);
                //根据设备类型分组
                List<NodeItem> deviceGroups = new ArrayList<>();
                NodeItem post = new NodeItem();
                post.setName("BOM");
                post.setPid(station.getNodeId());
                post.setSubItems(new ArrayList<>());

                NodeItem gate = new NodeItem();
                gate.setName("AGM");
                gate.setPid(station.getNodeId());
                gate.setSubItems(new ArrayList<>());

                NodeItem tvm = new NodeItem();
                tvm.setName("TVM");
                tvm.setPid(station.getNodeId());
                tvm.setSubItems(new ArrayList<>());

                NodeItem tcm = new NodeItem();
                tcm.setName("TCM");
                tcm.setPid(station.getNodeId());
                tcm.setSubItems(new ArrayList<>());

                NodeItem avm = new NodeItem();
                avm.setName("AVM");
                avm.setPid(station.getNodeId());
                avm.setSubItems(new ArrayList<>());

                NodeItem tsm = new NodeItem();
                tsm.setName("ISM");
                tsm.setPid(station.getNodeId());
                tsm.setSubItems(new ArrayList<>());

                NodeItem pca = new NodeItem();
                pca.setName("PCA");
                pca.setPid(station.getNodeId());
                pca.setSubItems(new ArrayList<>());

                //获取设备节点
                List<MetroDevice> metroDevices = iMetroNodeService.getMetroDevice(metroStation.getId().getStationId());
                if (metroDevices==null||metroDevices.isEmpty()){
                    continue;
                }
                for (MetroDevice metroDevice:metroDevices){
                    if (metroDevice.getStatus().shortValue() == 1) {
                    //设备未启用不加载
                        continue;
                    }
                    NodeItem device = metroNodeToNodeItem(metroDevice);
                    device.setPid(station.getNodeId());
                    if (metroDevice.getDeviceType() == AFCDeviceType.POST) {
                        post.getSubItems().add(device);
                    } else if ((AFCDeviceType.GATE == null
                            && AFCDeviceSubType.getInstance().getDicItemMap().containsKey(metroDevice.getDeviceType()))
                            || (AFCDeviceType.GATE != null && metroDevice.getDeviceType() == AFCDeviceType.GATE)) {
                        gate.getSubItems().add(device);
                    } else if (metroDevice.getDeviceType() == AFCDeviceType.TVM) {
                        tvm.getSubItems().add(device);
                    } else if (metroDevice.getDeviceType() == AFCDeviceType.TCM) {
                        tvm.getSubItems().add(device);
                    } else if (metroDevice.getDeviceType() == AFCDeviceType.TSM) {
                        tsm.getSubItems().add(device);
                    } else if (metroDevice.getDeviceType() == AFCDeviceType.AVM) {
                        tsm.getSubItems().add(device);
                    } else if (metroDevice.getDeviceType() == AFCDeviceType.PCA) {
                        pca.getSubItems().add(device);
                    }
                }
                if (!post.getSubItems().isEmpty()){
                    deviceGroups.add(post);
                }
                if (!gate.getSubItems().isEmpty()){
                    deviceGroups.add(gate);
                }
                if (!tvm.getSubItems().isEmpty()){
                    deviceGroups.add(tvm);
                }
                if (!tcm.getSubItems().isEmpty()){
                    deviceGroups.add(tcm);
                }
                if (!avm.getSubItems().isEmpty()){
                    deviceGroups.add(avm);
                }
                if (!tsm.getSubItems().isEmpty()){
                    deviceGroups.add(tsm);
                }
                if (!pca.getSubItems().isEmpty()){
                    deviceGroups.add(pca);
                }
                station.setSubItems(deviceGroups);
            }
            line.setSubItems(stations);
        };
        acc.setSubItems(lines);
        return Result.success(acc);
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

    private NodeItem metroNodeToNodeItem(MetroNode metroNode){
        if (metroNode==null){
            return null;
        }
        AFCNodeLevel level = metroNode.level();
        NodeItem nodeItem = new NodeItem();
        nodeItem.setNodeType(level.toString());
        nodeItem.setIcon(afcNodeLocationToImageLocation(metroNode.getImageLocation()));
        nodeItem.setImageUrl(fileService.getResourceIndex(metroNode.getPicName()));
        switch (level){
            case ACC:{
                MetroACC metroACC = (MetroACC)metroNode;
                nodeItem.setNodeId(metroACC.id());
                nodeItem.setName(metroACC.getAccName());
                nodeItem.setStatus(metroACC.getStatus());
                nodeItem.setText(afcNodeLocationToTextLocation(metroACC.getTextLocation())
                        .setText(metroACC.getAccName()));
                nodeItem.getIcon().setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png","grey.png")));
                break;
            }
            case LC:{
                MetroLine metroLine = (MetroLine) metroNode;
                nodeItem.setNodeId(metroLine.id());
                nodeItem.setName(metroLine.getLineName());
                nodeItem.setStatus(metroLine.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroLine.getTextLocation())
                        .setText(metroLine.getLineName()));
                nodeItem.getIcon().setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png","grey.png")));
                break;
            }
            case SC:{
                MetroStation metroStation = (MetroStation) metroNode;
                nodeItem.setNodeId(metroStation.getId().getStationId().longValue());
                nodeItem.setName(metroStation.getStationName());
                nodeItem.setStatus(metroStation.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroStation.getTextLocation())
                        .setText(metroStation.getStationName()));
                nodeItem.getIcon().setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png","yellow.png",
                        "red.png","grey.png","mode_normal.png","mode_demotion.png","mode_urgency.png","urgency.png",
                        "green_new.png","stop.png")));
                break;
            }
            case SLE:{
                MetroDevice metroDevice = (MetroDevice) metroNode;
                nodeItem.setNodeId(metroDevice.getId().getDeviceId());
                nodeItem.setName(metroDevice.getDeviceName());
                nodeItem.setStatus(metroDevice.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroDevice.getTextLocation())
                        .setText(metroDevice.getDeviceName()));
                List<String> images = new ArrayList<>();
                String prefix = "";
                if (metroDevice.getDeviceType() == AFCDeviceType.POST.shortValue()) {
                    prefix = "BOM";
                } else if ((AFCDeviceType.GATE == null
                        && AFCDeviceSubType.getInstance().getDicItemMap().containsKey(metroDevice.getDeviceType()))
                        || (AFCDeviceType.GATE != null && metroDevice.getDeviceType() == AFCDeviceType.GATE)) {

                    if (metroDevice.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_ENTRY.shortValue()) {
                        prefix = "entry";
                    } else if (metroDevice.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_EXIT.shortValue()) {
                        prefix = "exit";
                    } else if (metroDevice.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_BOTH.shortValue()) {
                        prefix = "both";
                    } else {
                        LOGGER.warn("GATE不存在设备类型=" + metroDevice.getDeviceType());
                        return null;
                    }

                } else if (metroDevice.getDeviceType() == AFCDeviceType.TVM.shortValue()) {
                    prefix = "TVM";
                } else if (metroDevice.getDeviceType() == AFCDeviceType.PCA.shortValue()) {
                    prefix = "PCA";
                } else if (metroDevice.getDeviceType() == AFCDeviceType.TSM.shortValue()) {
                    prefix = "TSM";
                } else {
                    LOGGER.warn("不存在设备类型=" + metroDevice.getDeviceType());
                    return null;
                }
                images.add(prefix+"_green.png");
                images.add(prefix+"_yellow.png");
                images.add(prefix+"_red.png");
                images.add(prefix+"_close.png");
                images.add(prefix+"_grey.png");
                images.add(prefix+"_close.png");
                nodeItem.getIcon().setImgs(fileService.getResourcesIndexs(images));
                break;
            }
        }
        return nodeItem;
    }

    private TextLocation afcNodeLocationToTextLocation(AFCNodeLocation afcNodeLocation){
        return new TextLocation(afcNodeLocation.getX(),afcNodeLocation.getY(),afcNodeLocation.getAngle());
    }

    private ImageLocation afcNodeLocationToImageLocation(AFCNodeLocation afcNodeLocation){
        return new ImageLocation(afcNodeLocation.getX(),afcNodeLocation.getY(),afcNodeLocation.getAngle());
    }

    public void setFileService(FileService fileService){
        this.fileService = fileService;
    }

    public void setiMetroNodeService(IMetroNodeService iMetroNodeService){
        this.iMetroNodeService = iMetroNodeService;
    }

    public void setiMetroNodeStatusService(IMetroNodeStatusService iMetroNodeStatusService){
        this.iMetroNodeStatusService = iMetroNodeStatusService;
    }
}
