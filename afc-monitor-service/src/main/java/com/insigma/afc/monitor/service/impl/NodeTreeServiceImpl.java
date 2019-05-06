package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.healthIndicator.RegisterHealthIndicator;
import com.insigma.afc.monitor.model.dto.*;
import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.NodeStatusMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.monitor.service.NodeTreeService;
import com.insigma.afc.monitor.service.rest.NodeTreeRestService;
import com.insigma.commons.model.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
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

    private IMetroNodeStatusService nodeStatusService;
    private MonitorConfigService monitorConfigService;
    private RegisterHealthIndicator registerHealthIndicator;
    private NodeTreeRestService nodeTreeService;

    private static final int ONLINE = 0;
    private static final int OFFLINE = 1;
    private static int STATION_OFF = 3;
    private static int STATION_USELESS = 9;

    @Autowired
    public NodeTreeServiceImpl(IMetroNodeStatusService nodeStatusService, MonitorConfigService monitorConfigService,
                               RegisterHealthIndicator registerHealthIndicator,NodeTreeRestService nodeTreeService) {
        this.nodeStatusService = nodeStatusService;
        this.monitorConfigService = monitorConfigService;
        this.registerHealthIndicator = registerHealthIndicator;
        this.nodeTreeService = nodeTreeService;
    }

    @Override
    public Result<NodeItem> getMonitorTree() {
        //获取节点树
        NodeItem acc = nodeTreeService.monitorTree().getData();

        // 通信前置机是否在线
        boolean onLine = Status.UP.equals(registerHealthIndicator.health().getStatus());
        int topStatus = OFFLINE;
        if (onLine) {
            topStatus = ONLINE;
        }
        acc.setStatus(topStatus);

        //获取线路节点
        List<NodeItem> lines = acc.getSubItems();
        if (lines == null) {
            return Result.success(acc);
        }
        List<Integer> stationIds = new ArrayList<>();
        List<Long> deviceIds = new ArrayList<>();
        for (NodeItem line : lines) {
            List<NodeItem> stations = line.getSubItems();
            if (stations == null) {
                continue;
            }
            for (NodeItem station : stations) {
                stationIds.add(station.getNodeId().intValue());
                List<NodeItem> devices = station.getSubItems();
                if (devices == null) {
                    continue;
                }
                for (NodeItem device : devices) {
                    deviceIds.add(device.getNodeId());
                }
            }
        }
        //从数据库获取车站状态
        List<StationStatustViewItem> stationStatusViewItems = nodeStatusService
                .getStationStatusView(new StationStatusCondition(stationIds));
        Map<Long, StationStatustViewItem> stationStatusViewItemMap = new HashMap<>();
        for (StationStatustViewItem stationStatustViewItem : stationStatusViewItems) {
            stationStatusViewItemMap.put(stationStatustViewItem.getNodeId(), stationStatustViewItem);
        }

        //从数据库获取设备状态
        DeviceStatusCondition filterForm = new DeviceStatusCondition();
        filterForm.setNodeIds(deviceIds);
        List<EquStatusViewItem> equStatusViewItems = nodeStatusService.getEquStatusView(filterForm);
        Map<Long, EquStatusViewItem> equStatusViewItemMap = new HashMap<>();
        for (EquStatusViewItem equStatusViewItem : equStatusViewItems) {
            equStatusViewItemMap.put(equStatusViewItem.getNodeId(), equStatusViewItem);
        }

        //填入各节点状态
        for (NodeItem line : lines) {
            List<NodeItem> stations = line.getSubItems();
            if (stations == null) {
                continue;
            }
            for (NodeItem station : stations) {
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
                station.setPid(line.getNodeId());
                List<NodeItem> devices = station.getSubItems();
                if (devices == null) {
                    continue;
                }
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
        }
        return Result.success(acc);
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
                return 3;
            } else if (status == DeviceStatus.STOP_SERVICE) {
                // 停止服务
                return 4;
            }
        }
        return 3;
    }

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
