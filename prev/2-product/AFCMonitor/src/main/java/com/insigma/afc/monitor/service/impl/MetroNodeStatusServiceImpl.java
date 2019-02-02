package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.dao.TmoItemStatusDao;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.model.properties.NetworkConfig;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.model.entity.MetroDevice;
import com.insigma.afc.topology.model.entity.MetroLine;
import com.insigma.afc.topology.model.entity.MetroNode;
import com.insigma.afc.topology.model.entity.MetroStation;
import com.insigma.afc.topology.service.TopologyService;
import com.insigma.afc.topology.util.NodeIdUtils;
import com.insigma.commons.exception.ServiceException;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 设备状态相关Service
 * @author xuzhemin
 */
@Service
@Configuration
public class MetroNodeStatusServiceImpl implements IMetroNodeStatusService {

    private static final Logger logger = LoggerFactory.getLogger(MetroNodeStatusServiceImpl.class);

    private TopologyService topologyService;
    private TmoItemStatusDao tmoItemStatusDao;
    private NetworkConfig networkConfig;
    private MonitorConfigService monitorConfigService;

    @Autowired
    public MetroNodeStatusServiceImpl(TopologyService topologyService, TmoItemStatusDao tmoItemStatusDao,
                                      MonitorConfigService monitorConfigService,NetworkConfig networkConfig) {
        this.topologyService = topologyService;
        this.tmoItemStatusDao = tmoItemStatusDao;
        this.networkConfig = networkConfig;
        this.monitorConfigService = monitorConfigService;
    }

    @Override
    public TmoItemStatus getTmoItemStatus(Boolean isDay, long nodeId) {
        TmoItemStatus tmoItemStatus;
        if (isDay) {
            tmoItemStatus = tmoItemStatusDao.
                    findByNodeIdAndModeChangeTimeAfter(nodeId, DateTimeUtil.beginDate(new Date()));
        } else {
            tmoItemStatus = tmoItemStatusDao.findById(nodeId).orElse(null);
        }
        return tmoItemStatus;
    }

    @Override
    public List<EquStatusViewItem> getEquStatusView(DeviceStatusCondition condition) {
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        List<Short> statusLevel = condition.getStatusList();
        String orderField = "updateTime";
        OrderDirection orderType = condition.getOrderType();
        List<Long> deviceList = condition.getNodeIds();

        //成都无法用设备类型区分SC、LC
        List<TmoItemStatus> deviceStatus = tmoItemStatusDao.findAll((root, query, builder) -> {
            switch (orderType){
                case ASC:{
                    query.orderBy(builder.asc(root.get(orderField)));
                    break;
                }
                case DESC:{
                    query.orderBy(builder.desc(root.get(orderField)));
                    break;
                }
                default:
            }
            return builder.notEqual(root.get("stationId"), 0);
        });

        Map<Long, TmoItemStatus> deviceMaps = new HashMap<>();
        Map<Long, TmoItemStatus> stationMaps = new HashMap<>();
        for (TmoItemStatus tmoItemStatus : deviceStatus) {
            if (NodeIdUtils.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() > AFCNodeLevel.SC
                    .getStatusCode()) {
                deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
            } else if (NodeIdUtils.getNodeLevel(tmoItemStatus.getNodeId()) == AFCNodeLevel.SC) {
                stationMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
            }
        }

        List<EquStatusViewItem> viewItemList = new ArrayList<>();
        //获取设备事件信息
        AFCNodeLevel nodeType = NodeIdUtils.getNodeLevelByNo(networkConfig.getNodeNo());
        switch (nodeType) {
            case ACC: {
                List<MetroLine> metroLines = topologyService.getAllMetroLine();
                Map<Short, List<MetroStation>> metroStations = topologyService.getMetroStationsGroupByLineId();
                Map<Integer, List<MetroDevice>> metroDevices = topologyService.getMetroDevicesGroupByStationId();
                for (MetroLine metroLine : metroLines) {
                    for (MetroStation metroStation : metroStations.get(metroLine.getLineID())) {
                        addDevicesToViewItemList(viewItemList, deviceList, startTime, endTime, statusLevel,
                                metroDevices.get(metroStation.getStationId()), deviceMaps,
                                stationMaps.get(metroStation.getStationId().longValue() << 16));
                    }
                }
                break;
            }
            case LC: {
                List<MetroStation> stations = topologyService
                        .getMetroStationsByLineId(networkConfig.getLineNo().shortValue());
                Map<Integer, List<MetroDevice>> metroDevices = topologyService.getMetroDevicesGroupByStationId();
                for (MetroStation metroStation : stations) {
                    addDevicesToViewItemList(viewItemList, deviceList, startTime, endTime, statusLevel,
                            metroDevices.get(metroStation.getStationId()), deviceMaps,
                            stationMaps.get(metroStation.getStationId().longValue() << 16));
                }
                break;
            }
            case SC: {
                Integer stationId = networkConfig.getStationNo();
                List<MetroDevice> metroDevices = topologyService.getMetroDeviceByStationId(stationId);
                addDevicesToViewItemList(viewItemList, deviceList, startTime, endTime, statusLevel,
                        metroDevices, deviceMaps, stationMaps.get(stationId.longValue() << 16));
                break;
            }
            default:
        }
        return viewItemList;
    }


    @Override
    public TmoItemStatus getTmoItemStatus(Short lineId, Integer stationId, Long deviceId) {
        AFCNodeLevel nodeType = NodeIdUtils.getNodeLevel(deviceId);
        List<TmoItemStatus> tmoItemStatusList = tmoItemStatusDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (lineId != null) {
                predicates.add(builder.equal(root.get("lineId"), lineId));
            }
            if (stationId != null) {
                predicates.add(builder.equal(root.get("stationId"), stationId));
            }
            if (deviceId != null) {
                predicates.add(builder.equal(root.get("nodeId"), deviceId));
            }
            if (nodeType != null) {
                predicates.add(builder.equal(root.get("nodeType"), nodeType.getStatusCode()));
            }
            // 按节点在线状态降序排列，如果同时有在线和离线状态记录，则优先显示在线状态。
            query.orderBy(builder.desc(root.get("itemActivity")));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
        if (tmoItemStatusList != null && !tmoItemStatusList.isEmpty()) {
            logger.error("******成功获取模式上传信息列表数据");
            return tmoItemStatusList.get(0);
        } else {
            return null;
        }
    }

    private void addDevicesToViewItemList(List<EquStatusViewItem> viewItemList, List<Long> deviceList, Date startTime,
                                          Date endTime, List<Short> statusLevel, List<MetroDevice> metroDeviceList,
                                          Map<Long, TmoItemStatus> deviceMaps, TmoItemStatus stationStatus) {
        //判断车站是否在线
        boolean isOnLine = false;
        if (stationStatus != null) {
            isOnLine = stationStatus.getItemActivity();
        }
        if (metroDeviceList==null){
            return;
        }
        for (MetroDevice metroDevice : metroDeviceList) {
            EquStatusViewItem temp = new EquStatusViewItem();
            if (deviceMaps.containsKey(metroDevice.getDeviceId())) {
                TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getDeviceId());
                temp = getEquStatusViewItem(tmoItemStatus, statusLevel, isOnLine);
                Date updateTime = tmoItemStatus.getUpdateTime();

                //更新时间是否在时间范围内
                boolean timeInValid = (startTime != null && updateTime.before(startTime))
                        || (endTime != null && updateTime.after(endTime));
                if (timeInValid) {
                    continue;
                }
            } else if (startTime == null && endTime == null) {
                boolean deviceBo = false;
                boolean statusBo = false;
                for (Long deviceId : deviceList) {
                    if (deviceId.equals(metroDevice.getDeviceId())) {
                        deviceBo = true;
                        break;
                    }
                }

                for (Short status : statusLevel) {
                    if (status.equals(DeviceStatus.OFF_LINE)) {
                        statusBo = true;
                        break;
                    }
                }
                if ((deviceList.size() == 0 || deviceBo) && (statusLevel.size() == 0 || statusBo)) {
                    temp.setNodeId(metroDevice.getDeviceId());
                    temp.setStatus(DeviceStatus.OFF_LINE);
                    temp.setNormalEvent("0 个(0%)");
                    temp.setWarnEvent("0 个(0%)");
                    temp.setAlarmEvent("0 个(100%)");
                }

            } else {
                continue;
            }
            if (temp.getNodeId()!=null) {
                for (Long nodeid : deviceList) {
                    if (nodeid.equals(temp.getNodeId())) {
                        viewItemList.add(temp);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 获取设备状态信息
     * @param tmoItemStatus 节点状态
     * @param statusLevel 状态等级
     * @param isOnline 前置机是否在线
     * @return
     */
    private EquStatusViewItem getEquStatusViewItem(TmoItemStatus tmoItemStatus, List<Short> statusLevel,
                                                   boolean isOnline) {
        EquStatusViewItem temp = new EquStatusViewItem();
        long normal = 0;
        long warning = 0;
        long alarm = 0;
        int normalRate;
        int warningRate;
        int alarmRate;

        //TODO 服务在线状态需要有地方维护
        Number onLine = 1;//(Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
        boolean hasStatus = true;
        boolean hasOff = true;

        if (statusLevel.size() > 0) {
            hasOff = false;
            for (Short staus : statusLevel) {
                if (staus.shortValue() == DeviceStatus.OFF_LINE) {
                    hasOff = true;
                    break;
                }
            }
        }

        if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity() && onLine != null
                && onLine.intValue() == 0 && isOnline) {
            if (null != tmoItemStatus.getItemStatus()) {

                if (statusLevel.size() > 0) {
                    hasStatus = false;
                    for (Short staus : statusLevel) {
                        if (tmoItemStatus.getItemStatus().equals(staus)) {
                            hasStatus = true;
                            break;
                        }
                    }
                }

                temp.setStatus(tmoItemStatus.getItemStatus());
            } else {
                hasStatus = false;
                temp.setStatus(DeviceStatus.OFF_LINE);

            }
        } else {
            hasStatus = false;
            temp.setStatus(DeviceStatus.OFF_LINE);

        }

        temp.setNodeId(tmoItemStatus.getNodeId());
        temp.setUpdateTime(tmoItemStatus.getUpdateTime());
        if (temp.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
            temp.setOnline(tmoItemStatus.getItemActivity());

        } else {
            temp.setOnline(false);
        }
        long total = 1;
        normalRate = (int) (normal * 100.0 / total + 0.5);
        warningRate = (int) (warning * 100.0 / total + 0.5);
        alarmRate = 100 - normalRate - warningRate;

        temp.setNormalEvent(normal + " 个(" + normalRate + "%)");
        temp.setWarnEvent(warning + " 个(" + warningRate + "%)");
        temp.setAlarmEvent(alarm + " 个(" + alarmRate + "%)");

        if ((hasOff && DeviceStatus.OFF_LINE.equals(temp.getStatus())) || hasStatus) {
            return temp;
        }
        return new EquStatusViewItem();
    }

    @Override
    public List<StationStatustViewItem> getStationStatusView(StationStatusCondition condition) {
        List<Integer> stationIds = condition.getStationIds();
        //获取通信前置机状态
        Number onLine = 1;//(Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);

        Result<MonitorConfigInfo> monitorConfigInfoResult = monitorConfigService.getMonitorConfig();
        if (!monitorConfigInfoResult.isSuccess()){
            throw new ServiceException("获取监控配置信息异常");
        }
        MonitorConfigInfo monitorConfigInfo = monitorConfigInfoResult.getData();
        //获取告警阈值
        Integer alarmNum = monitorConfigInfo.getAlarm();
        //获取警告阈值
        Integer warningNum = monitorConfigInfo.getWarning();

        List<StationStatustViewItem> result = new ArrayList<>();

        //查询车站状态
        List<TmoItemStatus> stationStatus = tmoItemStatusDao
                .findByNodeTypeAndStationIdNotOrderByNodeIdAsc(AFCNodeLevel.SC.getStatusCode().shortValue(), 0);
        //查询设备状态
        List<TmoItemStatus> deviceStatus = tmoItemStatusDao
                .findByNodeTypeGreaterThanOrderByNodeIdAsc(AFCNodeLevel.SC.getStatusCode().shortValue());
        Map<Integer, TmoItemStatus> stationMaps = new HashMap<>();
        for (TmoItemStatus tmoItemStatus : stationStatus) {
            stationMaps.put(tmoItemStatus.getStationId(), tmoItemStatus);
        }
        Map<Long, TmoItemStatus> deviceMaps = new HashMap<>();
        for (TmoItemStatus tmoItemStatus : deviceStatus) {
            deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
        }

        MetroNode afcNode = topologyService.getNodeByNo(networkConfig.getNodeNo());
        if (stationStatus.size() == 0) {
            return result;
        }
        if (afcNode.level().equals(AFCNodeLevel.ACC)) {
            List<MetroLine> subNodes = topologyService.getAllMetroLine();
            for (MetroLine metroLine : subNodes) {
                List<MetroStation> subStationNodes = topologyService
                        .getMetroStationsByLineId(metroLine.getLineID());
                for (MetroStation metroStation : subStationNodes) {
                    getStationStatusView(metroStation, stationMaps, deviceMaps, onLine, alarmNum, warningNum, result,
                            stationIds);
                }
            }
        } else if (afcNode.level().equals(AFCNodeLevel.LC)) {
            MetroLine line = (MetroLine) afcNode;
            List<MetroStation> subNodes = topologyService.getMetroStationsByLineId(line.getLineID());
            for (MetroStation metroStation : subNodes) {
                getStationStatusView(metroStation, stationMaps, deviceMaps, onLine, alarmNum, warningNum, result,
                        stationIds);
            }
        } else if (afcNode.level().equals(AFCNodeLevel.SC)) {
            MetroStation station = (MetroStation) afcNode;
            getStationStatusView(station, stationMaps, deviceMaps, onLine, alarmNum, warningNum, result, stationIds);
        }
        return result;
    }

    public void getStationStatusView(MetroStation station, Map<Integer, TmoItemStatus> stationMaps,
                                     Map<Long, TmoItemStatus> deviceMaps, Number onLine, Integer alarmNum,
                                     Integer warningNum, List<StationStatustViewItem> result, List<Integer> stationIDs) {

        Integer stationId = station.getStationId();
        boolean isNeed = true;
        if (stationIDs != null) {
            isNeed = false;
            for (Integer sid : stationIDs) {
                if (sid.equals(stationId)) {
                    isNeed = true;
                }
            }
        }
        if (!isNeed) {
            return;
        }
        int normalEvent = 0;
        int warnEvent = 0;
        int alarmEvent = 0;

        Short lineId = station.getLineId();
        StationStatustViewItem viewItem = new StationStatustViewItem();
        viewItem.setLineId(lineId);
        viewItem.setStationId(stationId);
        viewItem.setNodeId(station.id());
        viewItem.setNodeType(NodeIdUtils.getNodeType(stationId.longValue()));
        TmoItemStatus tmoItemStatus = stationMaps.get(station.getStationId());
        if (tmoItemStatus!=null) {
            viewItem.setMode(getCurrentmode(tmoItemStatus));
            viewItem.setUpdateTime(tmoItemStatus.getModeChangeTime());
            if (onLine != null && onLine.intValue() == 0) {
                viewItem.setOnline(tmoItemStatus.getItemActivity());
            } else {
                viewItem.setOnline(false);
            }
            if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity() && onLine != null
                    && onLine.intValue() == 0) {
                viewItem.setStatus(DeviceStatus.NORMAL);
            } else {
                viewItem.setStatus(DeviceStatus.OFF_LINE);
            }
        } else {
            viewItem.setMode(-1L);
            viewItem.setUpdateTime(null);
            viewItem.setOnline(false);
            viewItem.setStatus(DeviceStatus.OFF_LINE);
        }
        for (MetroDevice metroDevice : topologyService.getMetroDeviceByStationId(stationId)) {
            EquStatusViewItem equViewItem = new EquStatusViewItem();
            equViewItem.setLineId(lineId);
            equViewItem.setStationId(stationId);
            equViewItem.setNodeId(metroDevice.getDeviceId());
            if (deviceMaps.containsKey(metroDevice.id()) && onLine != null
                    && onLine.intValue() == 0) {
                TmoItemStatus deviceStatus = deviceMaps.get(metroDevice.id());
                equViewItem.setOnline(deviceStatus.getItemActivity());
                //启用设备才纳入报警，警告的设备数的计算
                if (null != deviceStatus.getItemActivity() && deviceStatus.getItemActivity()
                        && null != deviceStatus.getItemStatus()) {
                    equViewItem.setStatus(deviceStatus.getItemStatus());
                    if (deviceStatus.getItemStatus().equals(DeviceStatus.NORMAL)
                            && metroDevice.getStatus() == 0) {
                        ++normalEvent;
                    } else if (deviceStatus.getItemStatus().equals(DeviceStatus.WARNING)
                            && metroDevice.getStatus() == 0) {
                        ++warnEvent;
                    } else if (deviceStatus.getItemStatus().equals(DeviceStatus.ALARM)
                            && metroDevice.getStatus() == 0) {
                        ++alarmEvent;
                    } else if (deviceStatus.getItemStatus().equals(DeviceStatus.STOP_SERVICE)
                            && metroDevice.getStatus() == 0) {
                        ++alarmEvent;
                    }
                } else if (metroDevice.getStatus() == 0) {
                    ++alarmEvent;
                }
            } else if (metroDevice.getStatus() == 0) {
                ++alarmEvent;
            }
        }
        viewItem.setNormalEvent(normalEvent);
        viewItem.setWarnEvent(warnEvent);
        viewItem.setAlarmEvent(alarmEvent);
        if (alarmEvent < alarmNum && alarmEvent >= warningNum
                && viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
            viewItem.setStatus(DeviceStatus.WARNING);
        } else if (alarmEvent >= alarmNum && viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
            viewItem.setStatus(DeviceStatus.ALARM);
        }
        //如果车站未启用,将车站的状态设置为未启用-yang
        if (station.getStatus() != 0) {
            viewItem.setStatus(DeviceStatus.NO_USE);
        }
        result.add(viewItem);
    }

    /**
     * 取车站当前处于的模式编号
     *
     * @param tmoItemStatus 车站节点状态
     * @return 模式编号
     */
    private long getCurrentmode(TmoItemStatus tmoItemStatus) {

        Short currentModeCode = tmoItemStatus.getCurrentModeCode();

        if (currentModeCode == null) {
            currentModeCode = 0;
        }

        return Long.valueOf(currentModeCode);
    }
}
