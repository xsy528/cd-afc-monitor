package com.insigma.afc.monitor.service.impl;

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

    @Autowired
    public NodeTreeServiceImpl(IMetroNodeStatusService nodeStatusService, MonitorConfigService monitorConfigService,
                               RegisterHealthIndicator registerHealthIndicator,NodeTreeRestService nodeTreeService,
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
        if (level==null){
            level = AFCNodeLevel.SLE;
        }
        Integer stationId = condition.getStationId();

        // 通信前置机是否在线
        boolean onLine = Status.UP.equals(registerHealthIndicator.health().getStatus());
        int topStatus = OFFLINE;
        if (onLine) {
            topStatus = ONLINE;
        }

        AFCNodeLevel localLevel = NodeIdUtils.nodeIdStrategy.getNodeLevel(appProperties.getNodeNo());
        switch (localLevel){
            case ACC:{
                //获取节点树
                NodeItem acc = nodeTreeService.monitorTree(level).getData();
                if (acc==null){
                    return Result.success(null);
                }

                acc.setStatus(topStatus);
                //获取线路节点
                List<NodeItem> lines = acc.getSubItems();
                if (lines == null||AFCNodeLevel.ACC.equals(level)) {
                    return Result.success(acc);
                }
                //从数据库获取线路状态
                List<TmoItemStatus> lineTmoItemStatuses = nodeStatusService.getLineTmoItemStatus();
                Map<Short,TmoItemStatus> lineTmoItemStatusMap = new HashMap<>();
                for (TmoItemStatus tmoItemStatus:lineTmoItemStatuses){
                    lineTmoItemStatusMap.put(tmoItemStatus.getLineId(),tmoItemStatus);
                }

                //获取车站id
                List<Integer> stationIds = new ArrayList<>();
                //获取设备id
                List<Long> deviceIds = new ArrayList<>();
                for (NodeItem line : lines) {
                    setStatonIdAndDeviceId(line.getSubItems(),stationIds,deviceIds,stationId);
                }

                //从数据库获取车站状态
                Map<Long, StationStatustViewItem> stationStatusViewItemMap = new HashMap<>();
                if (level.getStatusCode()>AFCNodeLevel.LC.getStatusCode()){
                    List<StationStatustViewItem> stationStatusViewItems = nodeStatusService
                            .getStationStatusView(new StationStatusCondition(stationIds));
                    for (StationStatustViewItem stationStatustViewItem : stationStatusViewItems) {
                        stationStatusViewItemMap.put(stationStatustViewItem.getNodeId(), stationStatustViewItem);
                    }
                }

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level,deviceIds);

                //填入各节点状态
                for (NodeItem line : lines) {
                    //判定线路状态
                    TmoItemStatus lineTmoItemStatus = lineTmoItemStatusMap.get(line.getNodeId().shortValue());
                    if (lineTmoItemStatus!=null&&lineTmoItemStatus.getItemActivity()!=null
                            &&lineTmoItemStatus.getItemActivity()&&onLine) {
                        line.setStatus(ONLINE);
                    } else {
                        line.setStatus(OFFLINE);
                    }

                    List<NodeItem> stations = line.getSubItems();
                    if (stations == null) {
                        continue;
                    }
                    setStationStatus(stations,stationId,onLine,line.getNodeId(),stationStatusViewItemMap,
                            equStatusViewItemMap);
                }
                return Result.success(acc);
            }
            case LC:{
                //获取节点树
                NodeItem line = nodeTreeService.monitorTree(level).getData();

                //从数据库获取线路状态
                List<TmoItemStatus> lineTmoItemStatuses = nodeStatusService.getLineTmoItemStatus();
                Map<Short,TmoItemStatus> lineTmoItemStatusMap = new HashMap<>();
                for (TmoItemStatus tmoItemStatus:lineTmoItemStatuses){
                    lineTmoItemStatusMap.put(tmoItemStatus.getLineId(),tmoItemStatus);
                }

                //获取车站id
                List<Integer> stationIds = new ArrayList<>();
                //获取设备id
                List<Long> deviceIds = new ArrayList<>();
                List<NodeItem> stations = line.getSubItems();
                setStatonIdAndDeviceId(stations,stationIds,deviceIds,stationId);

                //从数据库获取车站状态
                Map<Long, StationStatustViewItem> stationStatusViewItemMap = new HashMap<>();
                if (level.getStatusCode()>AFCNodeLevel.LC.getStatusCode()){
                    List<StationStatustViewItem> stationStatusViewItems = nodeStatusService
                            .getStationStatusView(new StationStatusCondition(stationIds));
                    for (StationStatustViewItem stationStatustViewItem : stationStatusViewItems) {
                        stationStatusViewItemMap.put(stationStatustViewItem.getNodeId(), stationStatustViewItem);
                    }
                }

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level,deviceIds);

                //填入各节点状态
                    //判定线路状态
                    TmoItemStatus lineTmoItemStatus = lineTmoItemStatusMap.get(line.getNodeId().shortValue());
                    if (lineTmoItemStatus!=null&&lineTmoItemStatus.getItemActivity()!=null
                            &&lineTmoItemStatus.getItemActivity()&&onLine) {
                        line.setStatus(ONLINE);
                    } else {
                        line.setStatus(OFFLINE);
                    }

                    if (stations == null) {
                        return Result.success(line);
                    }
                setStationStatus(stations,stationId,onLine,line.getNodeId(),stationStatusViewItemMap,
                        equStatusViewItemMap);
                return Result.success(line);
            }
            case SC:{
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
                if (!stationStatusViewItems.isEmpty()){
                    statusItem = stationStatusViewItems.get(0);
                }

                //从数据库获取设备状态
                Map<Long, EquStatusViewItem> equStatusViewItemMap = getEquStatusViewItemMap(level,deviceIds);

                //填入各节点状态
                //可以只查询特定的车站
                if (stationId!=null&&!stationId.equals(station.getNodeId().intValue())){
                    station.setSubItems(null);
                    return Result.success(null);
                }
                int status = STATION_OFF;
                if (onLine) {
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
                setDeviceStatus(devices,onLine,equStatusViewItemMap,status);

                return Result.success(station);
            }
            default: return null;
        }
    }

    private Map<Long, EquStatusViewItem> getEquStatusViewItemMap(AFCNodeLevel level,List<Long> deviceIds){
        //从数据库获取设备状态
        Map<Long, EquStatusViewItem> equStatusViewItemMap = new HashMap<>();
        if (level.getStatusCode()>AFCNodeLevel.SC.getStatusCode()){
            DeviceStatusCondition filterForm = new DeviceStatusCondition();
            filterForm.setNodeIds(deviceIds);
            List<EquStatusViewItem> equStatusViewItems = nodeStatusService.getEquStatusView(filterForm);
            for (EquStatusViewItem equStatusViewItem : equStatusViewItems) {
                equStatusViewItemMap.put(equStatusViewItem.getNodeId(), equStatusViewItem);
            }
        }
        return equStatusViewItemMap;
    }

    private void setStatonIdAndDeviceId(List<NodeItem> stations,List<Integer> stationIds,List<Long> deviceIds,
                                        Integer stationId){
        if (stations==null){
            return;
        }
        for (NodeItem station : stations) {
            stationIds.add(station.getNodeId().intValue());
            if (stationId!=null&&stationId!=station.getNodeId().intValue()){
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

    private void setDeviceStatus(List<NodeItem> devices,boolean onLine,
                                 Map<Long, EquStatusViewItem> equStatusViewItemMap,int status){
        for (NodeItem device : devices) {
            Short deviceStatus = DeviceStatus.OFF_LINE;
            // 1.如果通信前置机不在线，则节点不在线
            if (!onLine) {
                deviceStatus = getStatus(false, deviceStatus);
            } else {
                EquStatusViewItem equStatus = equStatusViewItemMap.get(device.getNodeId());
                // 2.若数据库不存在数据，则节点不在线
                if (equStatus == null) {
                    deviceStatus = getStatus(false, status);
                } else {
                    deviceStatus = getStatus(equStatus.getOnline(), equStatus.getStatus());
                }
            }
            device.setStatus(deviceStatus.intValue());
        }
    }

    private void setStationStatus(List<NodeItem> stations, Integer stationId, boolean onLine,Long pid,
                                  Map<Long, StationStatustViewItem> stationStatusViewItemMap,
                                  Map<Long, EquStatusViewItem> equStatusViewItemMap){
        for (NodeItem station : stations) {
            //可以只查询特定的车站
            if (stationId!=null&&!stationId.equals(station.getNodeId().intValue())){
                station.setSubItems(null);
                continue;
            }
            int status = STATION_OFF;
            if (onLine) {
                StationStatustViewItem statusItem = stationStatusViewItemMap.get(station.getNodeId());
                if (statusItem != null) {
                    status = getStationStatus(statusItem);
                    if (statusItem.getStatus().equals(DeviceStatus.NO_USE)) {
                        status = STATION_USELESS;
                    }
                }
            }
            station.setStatus(status);
            station.setPid(pid);
            List<NodeItem> devices = station.getSubItems();
            if (devices == null) {
                continue;
            }
            setDeviceStatus(devices,onLine,equStatusViewItemMap,status);
        }
    }

    /**
     * 获取节点状态
     * @param isOnline 前置机在线状态
     * @param status 状态
     * @return 状态
     */
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
                return 3;
            } else if (status == DeviceStatus.STOP_SERVICE) {
                // 停止服务
                return 4;
            }
        }
        return 3;
    }

    /**
     * 获取车站状态
     * @param statusItem 车站状态
     * @return 状态码
     */
    private int getStationStatus(StationStatustViewItem statusItem) {
        NodeStatusMonitorConfigDTO monitorConfigInfo = monitorConfigService.getMonitorConfig().getData();
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
}
