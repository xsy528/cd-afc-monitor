package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.healthIndicator.RegisterHealthIndicator;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.NodeItem;
import com.insigma.afc.monitor.model.dto.NodeStatusMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.condition.MonitorTreeCondition;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.monitor.service.NodeTreeService;
import com.insigma.afc.monitor.service.rest.NodeTreeRestService;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.dic.DicitemEntry;
import com.insigma.commons.model.dto.Result;
import com.insigma.commons.properties.AppProperties;
import com.insigma.commons.util.NodeIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ticket: 节点树服务实现类
 *
 * @author xuzhemin
 * 2019-01-22:11:19
 */
@Service
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class NodeTreeServiceImpl implements NodeTreeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeTreeServiceImpl.class);

    private IMetroNodeStatusService nodeStatusService;
    private MonitorConfigService monitorConfigService;
    private RegisterHealthIndicator registerHealthIndicator;
    private NodeTreeRestService nodeTreeService;
    private AppProperties appProperties;

    private static final int ONLINE = 0;
    private static final int OFFLINE = 1;
    private static final int STATION_OFF = 3;
    private static final int STATION_USELESS = 9;
    private static final int DEVICE_OFFLINE = 3;

    @Autowired
    public NodeTreeServiceImpl(IMetroNodeStatusService nodeStatusService, MonitorConfigService monitorConfigService,
                               RegisterHealthIndicator registerHealthIndicator, NodeTreeRestService nodeTreeService,
                               AppProperties appProperties) {
        this.nodeStatusService = nodeStatusService;
        this.monitorConfigService = monitorConfigService;
        this.registerHealthIndicator = registerHealthIndicator;
        this.nodeTreeService = nodeTreeService;
        this.appProperties = appProperties;
    }

    @Override
    public Result<NodeItem> getMonitorTree(MonitorTreeCondition condition) {
        AFCNodeLevel level = condition.getLevel();
        if (level == null) {
            level = AFCNodeLevel.SLE;
        }
        Integer stationId = condition.getStationId();

        // 通信前置机是否在线
        boolean msOnline = Status.UP.equals(registerHealthIndicator.health().getStatus());
        int topStatus = OFFLINE;
        if (msOnline) {
            topStatus = ONLINE;
        }

        AFCNodeLevel localLevel = NodeIdUtils.nodeIdStrategy.getNodeLevel(appProperties.getNodeNo());
        switch (localLevel) {
            case ACC: {
                //获取节点树
                NodeItem acc = nodeTreeService.monitorTree(level).getData();
                if (acc == null) {
                    return Result.success(null);
                }

                acc.setStatus(topStatus);
                //获取线路节点
                List<NodeItem> lines = acc.getSubItems();
                if (lines == null || AFCNodeLevel.ACC.equals(level)) {
                    return Result.success(acc);
                }
                //从数据库获取线路状态
                List<TmoItemStatus> lineTmoItemStatuses = nodeStatusService.getLineTmoItemStatus();
                Map<Short, TmoItemStatus> lineTmoItemStatusMap = new HashMap<>();
                for (TmoItemStatus tmoItemStatus : lineTmoItemStatuses) {
                    lineTmoItemStatusMap.put(tmoItemStatus.getLineId(), tmoItemStatus);
                }

                //获取车站id
                List<Integer> stationIds = new ArrayList<>();
                //获取设备id
                List<Long> deviceIds = new ArrayList<>();
                for (NodeItem line : lines) {
                    setStationIdAndDeviceId(line.getSubItems(), stationIds, deviceIds, stationId);
                }

                //从数据库获取车站状态
                Map<Integer, StationStatustViewItem> stationStatusViewItemMap = getStationStatusViewItemMap(level,
                        stationIds);

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level, deviceIds);

                //填入各节点状态
                for (NodeItem line : lines) {
                    //判定线路状态
                    TmoItemStatus lineTmoItemStatus = lineTmoItemStatusMap.get(line.getNodeId().shortValue());
                    if (lineTmoItemStatus != null && lineTmoItemStatus.getItemActivity() != null
                            && lineTmoItemStatus.getItemActivity() && msOnline) {
                        line.setStatus(ONLINE);
                    } else {
                        line.setStatus(OFFLINE);
                    }

                    List<NodeItem> stations = line.getSubItems();
                    if (stations == null) {
                        continue;
                    }
                    setStationStatus(stations, stationId, msOnline, line.getStatus()==ONLINE,
                            stationStatusViewItemMap, equStatusViewItemMap);
                }
                return Result.success(acc);
            }
            case LC: {
                //获取节点树
                NodeItem line = nodeTreeService.monitorTree(level).getData();

                //从数据库获取线路状态
                List<TmoItemStatus> lineTmoItemStatuses = nodeStatusService.getLineTmoItemStatus();
                Map<Short, TmoItemStatus> lineTmoItemStatusMap = new HashMap<>(lineTmoItemStatuses.size());
                for (TmoItemStatus tmoItemStatus : lineTmoItemStatuses) {
                    lineTmoItemStatusMap.put(tmoItemStatus.getLineId(), tmoItemStatus);
                }

                //获取车站id
                List<Integer> stationIds = new ArrayList<>();
                //获取设备id
                List<Long> deviceIds = new ArrayList<>();
                List<NodeItem> stations = line.getSubItems();
                setStationIdAndDeviceId(stations, stationIds, deviceIds, stationId);

                //从数据库获取车站状态
                Map<Integer, StationStatustViewItem> stationStatusViewItemMap = getStationStatusViewItemMap(level,
                        stationIds);

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level, deviceIds);

                //填入各节点状态
                //判定线路状态
                TmoItemStatus lineTmoItemStatus = lineTmoItemStatusMap.get(line.getNodeId().shortValue());
                if (lineTmoItemStatus != null && lineTmoItemStatus.getItemActivity() != null
                        && lineTmoItemStatus.getItemActivity() && msOnline) {
                    line.setStatus(ONLINE);
                } else {
                    line.setStatus(OFFLINE);
                }

                if (stations == null) {
                    return Result.success(line);
                }
                setStationStatus(stations, stationId, msOnline, line.getStatus()==ONLINE,
                        stationStatusViewItemMap, equStatusViewItemMap);
                return Result.success(line);
            }
            case SC: {
                //获取节点树
                NodeItem station = nodeTreeService.monitorTree(level).getData();
                //获取设备id
                List<Long> deviceIds = new ArrayList<>();

                List<NodeItem> devices = station.getSubItems();
                if (devices != null) {
                    for (NodeItem device : devices) {
                        deviceIds.add(device.getNodeId());
                    }
                }

                //从数据库获取车站状态
                List<Integer> stationIds = Collections.singletonList(station.getNodeId().intValue());
                List<StationStatustViewItem> stationStatusViewItems = nodeStatusService
                        .getStationStatusView(new StationStatusCondition(stationIds));
                StationStatustViewItem statusItem = null;
                if (!stationStatusViewItems.isEmpty()) {
                    statusItem = stationStatusViewItems.get(0);
                }

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level, deviceIds);

                //填入各节点状态
                //可以只查询特定的车站
                if (stationId != null && !stationId.equals(station.getNodeId().intValue())) {
                    station.setSubItems(null);
                    return Result.success(null);
                }
                int status = STATION_OFF;
                if (msOnline) {
                    if (statusItem != null) {
                        status = getStationStatus(statusItem);
                        if (statusItem.getStatus().equals(DeviceStatus.NO_USE)) {
                            status = STATION_USELESS;
                        }
                    }
                }
                station.setStatus(status);
                if (devices == null) {
                    return Result.success(station);
                }
                setDeviceStatus(devices, msOnline,true,status!=STATION_OFF,equStatusViewItemMap);

                return Result.success(station);
            }
            default:
                return null;
        }
    }

    /**
     * 从数据库获取车站状态
     *
     * @param level      树等级
     * @param stationIds 车站数组
     * @return map
     */
    private Map<Integer, StationStatustViewItem> getStationStatusViewItemMap(AFCNodeLevel level, List<Integer>
            stationIds) {
        Map<Integer, StationStatustViewItem> stationStatusViewItemMap = new HashMap<>();
        if (level.getStatusCode() > AFCNodeLevel.LC.getStatusCode()) {
            List<StationStatustViewItem> stationStatusViewItems = nodeStatusService
                    .getStationStatusView(new StationStatusCondition(stationIds));
            for (StationStatustViewItem stationStatustViewItem : stationStatusViewItems) {
                stationStatusViewItemMap.put(stationStatustViewItem.getStationId(), stationStatustViewItem);
            }
        }
        return stationStatusViewItemMap;
    }

    private Map<Long, EquStatusViewItem> getEquStatusViewItemMap(AFCNodeLevel level, List<Long> deviceIds) {
        //从数据库获取设备状态
        Map<Long, EquStatusViewItem> equStatusViewItemMap = new HashMap<>();
        if (level.getStatusCode() > AFCNodeLevel.SC.getStatusCode()) {
            DeviceStatusCondition filterForm = new DeviceStatusCondition();
            filterForm.setNodeIds(deviceIds);
            List<EquStatusViewItem> equStatusViewItems = nodeStatusService.getEquStatusView(filterForm);
            for (EquStatusViewItem equStatusViewItem : equStatusViewItems) {
                equStatusViewItemMap.put(equStatusViewItem.getNodeId(), equStatusViewItem);
            }
        }
        return equStatusViewItemMap;
    }

    private void setStationIdAndDeviceId(List<NodeItem> stations, List<Integer> stationIds, List<Long> deviceIds,
                                        Integer stationId) {
        if (stations == null) {
            return;
        }
        for (NodeItem station : stations) {
            stationIds.add(station.getNodeId().intValue());
            if (stationId != null && stationId != station.getNodeId().intValue()) {
                continue;
            }
            List<NodeItem> devices = station.getSubItems();
            if (devices == null) {
                continue;
            }
            for (NodeItem device : devices) {
                deviceIds.add(device.getNodeId());
            }
        }
    }

    private void setDeviceStatus(List<NodeItem> devices, boolean msOnline,boolean lineOnline,boolean stationOnline,
                                 Map<Long, EquStatusViewItem> equStatusViewItemMap) {
        for (NodeItem device : devices) {
            int deviceStatus = DEVICE_OFFLINE;
            if (msOnline&&lineOnline&&stationOnline){
                EquStatusViewItem equStatus = equStatusViewItemMap.get(device.getNodeId());
                // 2.若数据库不存在数据，则节点不在线
                if (equStatus != null) {
                    deviceStatus = getDeviceStatus(equStatus.getStatus(),equStatus.getOnline());
                }
            }
            device.setStatus(deviceStatus);
        }
    }

    private void setStationStatus(List<NodeItem> stations, Integer stationId, boolean msOnline, boolean lineOnline,
                                  Map<Integer, StationStatustViewItem> stationStatusViewItemMap,
                                  Map<Long, EquStatusViewItem> equStatusViewItemMap) {
        for (NodeItem station : stations) {
            //可以只查询特定的车站
            if (stationId != null && !stationId.equals(station.getNodeId().intValue())) {
                station.setSubItems(null);
                continue;
            }
            int status = STATION_OFF;
            if (msOnline&&lineOnline) {
                StationStatustViewItem statusItem = stationStatusViewItemMap.get(station.getNodeId().intValue());
                if (statusItem != null) {
                    if (statusItem.getStatus().equals(DeviceStatus.NO_USE)) {
                        status = STATION_USELESS;
                    }else{
                        status = getStationStatus(statusItem);
                    }
                }
            }
            station.setStatus(status);
            List<NodeItem> devices = station.getSubItems();
            if (devices == null) {
                continue;
            }
            setDeviceStatus(devices, msOnline,lineOnline,status!=STATION_OFF,equStatusViewItemMap);
        }
    }

    /**
     * 获取设备状态
     *
     * @param status   状态
     * @return 状态
     */
    private int getDeviceStatus(int status,boolean active) {
        if (!active){
            //暂停服务
            return 4;
        }else if (status == DeviceStatus.NORMAL) {
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
            return 3;
        }
        return DEVICE_OFFLINE;
    }

    /**
     * 获取车站状态
     *
     * @param statusItem 车站状态
     * @return 状态码
     */
    private int getStationStatus(StationStatustViewItem statusItem) {
        NodeStatusMonitorConfigDTO monitorConfigInfo = monitorConfigService.getMonitorConfig().getData();
        int currentMode = statusItem.getMode().intValue();
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

        String group = getModeGroupNameByValue(currentMode);

        if (statusItem.getOnline()) {
            // 如果车站不属于任何一个降级模式，则车站属于正常模式，即currentmode==0
            if (currentMode == 0) {
                if (statusItem.getAlarmEvent() < alarmNum
                        && statusItem.getAlarmEvent() < warningNum) {
                    // 绿色
                    return 0;
                } else if (statusItem.getAlarmEvent() < alarmNum
                        && statusItem.getAlarmEvent() >= warningNum) {
                    // 黄色
                    return 1;
                } else if (statusItem.getAlarmEvent() >= alarmNum) {
                    // 红色
                    return 2;
                } else {
                    // 灰色
                    return 3;
                }
            } else {
                if(AFCModeCode.MODE_SIGN_DESCEND.equals(group)){
                    return 5;
                }
                if (AFCModeCode.MODE_SIGN_URGENCY.equals(group)){
                    return 6;
                } else {
                    return 3;
                }
//                if (statusItem.getAlarmEvent() < alarmNum
//                        && statusItem.getAlarmEvent() < warningNum) {
//                    // 正常模式
//                    return 4;
//                } else if (statusItem.getAlarmEvent() < alarmNum
//                        && statusItem.getAlarmEvent() >= warningNum) {
//                    // 降级模式
//                    return 5;
//                } else if (statusItem.getAlarmEvent() >= alarmNum) {
//                    // 紧急模式
//                    return 6;
//                } else {
//                    // 灰色
//                    return 3;
//                }
            }
        } else {
            //灰色
            return 3;
        }
    }

    public String getModeGroupNameByValue(Integer key) {
        for (DicitemEntry v : AFCModeCode.getInstance().dicItecEntryMap.values()) {
            if (v.value.equals(key)) {
                return v.dicitem.group();
            }
        }
        return "未知";
    }
}
