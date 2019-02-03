package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.model.dto.*;
import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.model.vo.NodeItem;
import com.insigma.afc.monitor.service.FileService;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.monitor.service.NodeTreeService;
import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.constant.dic.AFCDeviceSubType;
import com.insigma.afc.topology.constant.dic.AFCDeviceType;
import com.insigma.afc.topology.model.entity.*;
import com.insigma.afc.topology.service.TopologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ticket: 节点树服务实现类
 *
 * @author xuzhemin
 * 2019-01-22:11:19
 */
@Service
public class NodeTreeServiceImpl implements NodeTreeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeTreeServiceImpl.class);

    private TopologyService topologyService;
    private FileService fileService;
    private IMetroNodeStatusService nodeStatusService;
    private MonitorConfigService monitorConfigService;

    private static final int ONLINE = 0;
    private static final int OFFLINE = 1;
    private static int STATION_OFF = 3;
    private static int STATION_USELESS = 9;

    public NodeTreeServiceImpl(TopologyService topologyService, FileService fileService,
                               IMetroNodeStatusService nodeStatusService) {
        this.fileService = fileService;
        this.topologyService = topologyService;
        this.nodeStatusService = nodeStatusService;
    }

    @Override
    public Result<NodeItem> getEditorNodeTree() {
        return Result.success(getTree(true, false, false));
    }

    @Override
    public Result<NodeItem> getMonitorNodeTree() {
        return Result.success(getTree(false, false, true));
    }

    @Override
    public Result<NodeItem> getNodeGroupDeviceTree() {
        return Result.success(getTree(false, true, false));
    }

    private NodeItem getTree(boolean showUnusedNode, boolean groupDevice, boolean showStatus) {
        //获取ACC根节点
        MetroACC metroACC = topologyService.getMetroACC();
        NodeItem acc = metroNodeToNodeItem(metroACC);
        Number onLine = null;
        Map<Long, StationStatustViewItem> stationStatusViewItemMap = null;
        Map<Long, EquStatusViewItem> equStatusViewItemMap = null;
        if (showStatus) {
            // 通信前置机是否在线 TODO
            onLine = 1;//(Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
            int topStatus = OFFLINE;
            if (onLine != null && onLine.equals(ONLINE)) {
                topStatus = ONLINE;
            }
            acc.setStatus(topStatus);
        }
        //获取线路节点
        List<MetroLine> metroLines = topologyService.getAllMetroLine();
        if (metroLines.isEmpty()) {
            return acc;
        }
        //获取车站节点
        List<MetroStation> allMetroStations = topologyService.getAllMetroStation();
        Map<Short, List<MetroStation>> stationMap = new HashMap<>();
        allMetroStations.forEach(metroStation ->
                stationMap.computeIfAbsent(metroStation.getLineId(), (key) -> new ArrayList<>()).add(metroStation));

        //获取设备节点
        List<MetroDevice> allMetroDevices = topologyService.getAllMetroDevice();
        Map<Integer, List<MetroDevice>> deviceMap = new HashMap<>();
        allMetroDevices.forEach(metroDevice ->
                deviceMap.computeIfAbsent(metroDevice.getStationId(), (key) -> new ArrayList<>()).add(metroDevice));

        if (showStatus) {
            //从数据库获取车站状态
            List<Integer> stationIds = new ArrayList<>();
            allMetroStations.forEach(metroStation -> stationIds.add(metroStation.getStationId()));
            StationStatusCondition condition = new StationStatusCondition();
            condition.setStationIds(stationIds);
            List<StationStatustViewItem> stationStatusViewItems = nodeStatusService.getStationStatusView(condition);
            stationStatusViewItemMap = new HashMap<>();
            for (StationStatustViewItem stationStatustViewItem : stationStatusViewItems) {
                stationStatusViewItemMap.put(stationStatustViewItem.getNodeId(), stationStatustViewItem);
            }

            // 从数据库加载设备节点状态
            DeviceStatusCondition filterForm = new DeviceStatusCondition();
            List<Short> statusLevelList = new ArrayList<>();
            DeviceStatus.getInstance().getByGroup("1").forEach(pv -> statusLevelList.add((short) pv.getKey()));
            filterForm.setStatusList(statusLevelList);
            List<Long> deviceIds = new ArrayList<>();
            allMetroDevices.forEach(metroDevice -> deviceIds.add(metroDevice.getDeviceId()));
            filterForm.setNodeIds(deviceIds);
            List<EquStatusViewItem> equStatusViewItems = nodeStatusService.getEquStatusView(filterForm);
            equStatusViewItemMap = new HashMap<>();
            for (EquStatusViewItem equStatusViewItem : equStatusViewItems) {
                equStatusViewItemMap.put(equStatusViewItem.getNodeId(), equStatusViewItem);
            }
        }

        //构造节点树
        List<NodeItem> lines = new ArrayList<>();
        for (MetroLine metroLine : metroLines) {
            if (!showUnusedNode && metroLine.getStatus() == 1) {
                //线路未启用，无需显示
                continue;
            }
            NodeItem line = metroNodeToNodeItem(metroLine);
            line.setPid(acc.getNodeId());
            lines.add(line);
            //获取车站节点
            List<MetroStation> metroStations = stationMap.get(metroLine.getLineID());
            if (metroStations == null) {
                continue;
            }
            List<NodeItem> stations = new ArrayList<>();
            for (MetroStation metroStation : metroStations) {
                if (!showUnusedNode && metroStation.getStatus() == 1) {
                    //车站未启用，无需显示
                    continue;
                }
                NodeItem station = metroNodeToNodeItem(metroStation);
                if (showStatus) {
                    int status = STATION_OFF;
                    if (onLine != null && onLine.equals(ONLINE)) {
                        StationStatustViewItem statusItem = stationStatusViewItemMap.get(station.getNodeId());
                        if (statusItem != null) {
                            status = getStationStatus(statusItem);
                            if (statusItem.getStatus().equals(DeviceStatus.NO_USE)) {
                                status = STATION_USELESS;
                            }
                        }
                    }
                    station.setStatus(status);
                }
                station.setPid(line.getNodeId());
                stations.add(station);

                List<NodeItem> subNodes;
                //获取设备节点
                if (groupDevice) {
                    subNodes = getDeviceGroupByType(deviceMap.get(metroStation.getStationId()), showUnusedNode,
                            metroStation.getStationId());
                } else {
                    subNodes = getDeviceList(deviceMap.get(metroStation.getStationId()), metroStation.getStationId(),
                            showUnusedNode, showStatus, onLine, equStatusViewItemMap);
                }
                station.setSubItems(subNodes);
            }
            line.setSubItems(stations);
        }
        ;
        acc.setSubItems(lines);
        return acc;
    }

    private List<NodeItem> getDeviceList(List<MetroDevice> metroDevices, Integer stationId, boolean showUnusedNode,
                                         boolean showStatus, Number onLine,
                                         Map<Long, EquStatusViewItem> equStatusViewItemMap) {
        if (metroDevices == null) {
            return null;
        }
        List<NodeItem> devices = new ArrayList<>();
        for (MetroDevice metroDevice : metroDevices) {
            if (!showUnusedNode && metroDevice.getStatus() == 1) {
                continue;
            }
            NodeItem device = metroNodeToNodeItem(metroDevice);

            if (showStatus) {
                Short status = DeviceStatus.OFF_LINE;
                // 1.如果通信前置机不在线，则节点不在线
                if (onLine != null && !onLine.equals(ONLINE)) {
                    status = getStatus(false, status);
                } else {
                    EquStatusViewItem equStatus = equStatusViewItemMap.get(device.getNodeId());
                    // 2.若数据库不存在数据，则节点不在线
                    if (equStatus == null) {
                        status = getStatus(false, status);
                    } else {
                        status = getStatus(equStatus.getOnline(), equStatus.getStatus());
                    }
                }
                device.setStatus(status.intValue());
            }
            device.setPid(stationId.longValue());
            devices.add(device);
        }
        return devices;
    }

    private List<NodeItem> getDeviceGroupByType(List<MetroDevice> metroDevices, boolean showUnusedNode
            , Integer stationId) {
        if (metroDevices == null) {
            return null;
        }
        //根据设备类型分组
        List<NodeItem> deviceGroups = new ArrayList<>();
        Map<String, NodeItem> deviceGroupMap = new HashMap<>();
        String post = "BOM";
        String gate = "AGM";
        String tvm = "TVM";
        String tsm = "TCM";
        String pca = "PCA";
        for (String name : Arrays.asList(post, gate, tvm, tsm, pca)) {
            NodeItem subNode = new NodeItem();
            subNode.setName(name);
            subNode.setPid(stationId.longValue());
            subNode.setSubItems(new ArrayList<>());
            deviceGroups.add(subNode);
            deviceGroupMap.put(name, subNode);
        }
        for (MetroDevice metroDevice : metroDevices) {
            if (!showUnusedNode && metroDevice.getStatus() == 1) {
                //设备未启用不加载
                continue;
            }
            NodeItem device = metroNodeToNodeItem(metroDevice);
            device.setPid(stationId.longValue());
            if (metroDevice.getDeviceType() == AFCDeviceType.POST) {
                deviceGroupMap.get(post).getSubItems().add(device);
            } else if ((AFCDeviceType.GATE == null
                    && AFCDeviceSubType.getInstance().getDicItemMap().containsKey(metroDevice.getDeviceType()))
                    || (AFCDeviceType.GATE != null && metroDevice.getDeviceType() == AFCDeviceType.GATE)) {
                deviceGroupMap.get(gate).getSubItems().add(device);
            } else if (metroDevice.getDeviceType() == AFCDeviceType.TVM) {
                deviceGroupMap.get(tvm).getSubItems().add(device);
            } else if (metroDevice.getDeviceType() == AFCDeviceType.TCM) {
                deviceGroupMap.get(tvm).getSubItems().add(device);
            } else if (metroDevice.getDeviceType() == AFCDeviceType.TSM) {
                deviceGroupMap.get(tsm).getSubItems().add(device);
            } else if (metroDevice.getDeviceType() == AFCDeviceType.AVM) {
                deviceGroupMap.get(tsm).getSubItems().add(device);
            } else if (metroDevice.getDeviceType() == AFCDeviceType.PCA) {
                deviceGroupMap.get(pca).getSubItems().add(device);
            }
        }
        deviceGroups.removeIf(nodeItem -> nodeItem.getSubItems().isEmpty());
        return deviceGroups;
    }

    private NodeItem metroNodeToNodeItem(MetroNode metroNode) {
        if (metroNode == null) {
            return null;
        }
        AFCNodeLevel level = metroNode.level();
        NodeItem nodeItem = new NodeItem();
        nodeItem.setNodeType(level.toString());
        nodeItem.setImageUrl(fileService.getResourceIndex(metroNode.getPicName()));
        switch (level) {
            case ACC: {
                MetroACC metroACC = (MetroACC) metroNode;
                nodeItem.setNodeId(metroACC.id());
                nodeItem.setName(metroACC.getAccName());
                nodeItem.setStatus(metroACC.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroACC.getTextLocation())
                        .setText(metroACC.getAccName()));
                nodeItem.setIcon(afcNodeLocationToImageLocation(metroACC.getImageLocation())
                        .setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png", "grey.png"))));
                break;
            }
            case LC: {
                MetroLine metroLine = (MetroLine) metroNode;
                nodeItem.setNodeId(metroLine.id());
                nodeItem.setName(metroLine.getLineName());
                nodeItem.setStatus(metroLine.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroLine.getTextLocation())
                        .setText(metroLine.getLineName()));
                nodeItem.setIcon(afcNodeLocationToImageLocation(metroLine.getImageLocation())
                        .setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png", "grey.png"))));
                break;
            }
            case SC: {
                MetroStation metroStation = (MetroStation) metroNode;
                nodeItem.setNodeId(metroStation.getStationId().longValue());
                nodeItem.setName(metroStation.getStationName());
                nodeItem.setStatus(metroStation.getStatus().intValue());
                nodeItem.setText(afcNodeLocationToTextLocation(metroStation.getTextLocation())
                        .setText(metroStation.getStationName()));
                nodeItem.setIcon(afcNodeLocationToImageLocation(metroStation.getImageLocation())
                        .setImgs(fileService.getResourcesIndexs(Arrays.asList("green.png", "yellow.png",
                                "red.png", "grey.png", "mode_normal.png", "mode_demotion.png", "mode_urgency.png", "urgency.png",
                                "green_new.png", "stop.png"))));
                break;
            }
            case SLE: {
                MetroDevice metroDevice = (MetroDevice) metroNode;
                nodeItem.setNodeId(metroDevice.getDeviceId());
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
                images.add(prefix + "_green.png");
                images.add(prefix + "_yellow.png");
                images.add(prefix + "_red.png");
                images.add(prefix + "_close.png");
                images.add(prefix + "_grey.png");
                images.add(prefix + "_close.png");
                nodeItem.setIcon(afcNodeLocationToImageLocation(metroDevice.getImageLocation())
                        .setImgs(fileService.getResourcesIndexs(images)));
                break;
            }
            default:
        }
        return nodeItem;
    }

    private short getStatus(boolean isOnline, int status) {
        if (isOnline) {
            if (status == DeviceStatus.NORMAL) {
                // 正常
                return 0;
            } else if (status == DeviceStatus.WARNING) {
                // 警告
                return 1;
            } else if (status == DeviceStatus.ALARM) {
                // 报警
                return 2;
            } else if (status == DeviceStatus.OFF_LINE) {
                // 离线
                return 4;
            } else if (status == DeviceStatus.STOP_SERVICE) {
                // 停止服务
                return 5;
            } else {
                return 4;
            }
        } else {
            return 4;
        }
    }

    private int getStationStatus(StationStatustViewItem statusItem) {
        MonitorConfigInfo monitorConfigInfo = monitorConfigService.getMonitorConfig().getData();
        long currentMode = statusItem.getMode();
        // 报警阀值
        Integer alarmNum = monitorConfigInfo.getAlarm();
        if (alarmNum == null) {
            alarmNum = 0;
        }
        // 警告阀值
        Integer warningNum = monitorConfigInfo.getWarning();
        if (warningNum == null) {
            warningNum = 0;
        }
        if (statusItem.getOnline()) {
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

    private TextLocation afcNodeLocationToTextLocation(AFCNodeLocation afcNodeLocation) {
        return new TextLocation(afcNodeLocation.getX(), afcNodeLocation.getY(), afcNodeLocation.getAngle());
    }

    private ImageLocation afcNodeLocationToImageLocation(AFCNodeLocation afcNodeLocation) {
        return new ImageLocation(afcNodeLocation.getX(), afcNodeLocation.getY(), afcNodeLocation.getAngle());
    }
}
