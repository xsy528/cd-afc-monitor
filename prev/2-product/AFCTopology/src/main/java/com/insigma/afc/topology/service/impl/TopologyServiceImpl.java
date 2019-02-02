package com.insigma.afc.topology.service.impl;

import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.dao.MetroACCDao;
import com.insigma.afc.topology.dao.MetroDeviceDao;
import com.insigma.afc.topology.dao.MetroLineDao;
import com.insigma.afc.topology.dao.MetroStationDao;
import com.insigma.afc.topology.exception.NodeNotFoundException;
import com.insigma.afc.topology.model.entity.*;
import com.insigma.afc.topology.service.TopologyService;
import com.insigma.afc.topology.util.NodeIdUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket: 线网服务实现类
 *
 * @author xuzhemin
 * 2019-01-28:15:54
 */
@Service
public class TopologyServiceImpl implements TopologyService {

    private static String deviceIdFormat = "%08x";
    private static String stationlineIdFormat = "%04x";
    private static String lineIdFormat = "%02x";
    private static String stationIdFormat = "%02x";
    private static String deviceTypeFormat = "%02x";

    private MetroACCDao metroACCDao;
    private MetroLineDao metroLineDao;
    private MetroStationDao metroStationDao;
    private MetroDeviceDao metroDeviceDao;

    @Autowired
    public TopologyServiceImpl(MetroACCDao metroACCDao,MetroLineDao metroLineDao,MetroStationDao metroStationDao,
                           MetroDeviceDao metroDeviceDao){
        this.metroACCDao = metroACCDao;
        this.metroLineDao = metroLineDao;
        this.metroStationDao = metroStationDao;
        this.metroDeviceDao = metroDeviceDao;
    }

    @Override
    public MetroACC getMetroACC() {
        return metroACCDao.findById((short)0).orElseThrow(()->new NodeNotFoundException(0L,AFCNodeLevel.ACC));
    }

    @Override
    public List<MetroLine> getAllMetroLine() {
        return metroLineDao.findAll();
    }

    @Override
    public List<MetroStation> getAllMetroStation() {
        return metroStationDao.findAll();
    }

    @Override
    public Map<Short, List<MetroStation>> getMetroStationsGroupByLineId() {
        List<MetroStation> allMetroStations = getAllMetroStation();
        Map<Short,List<MetroStation>> map = new HashMap<>();
        for (MetroStation metroStation:allMetroStations){
            List<MetroStation> metroStations = map.computeIfAbsent(metroStation.getLineId(),(m)->new ArrayList<>());
            metroStations.add(metroStation);
        }
        return map;
    }

    @Override
    public List<MetroDevice> getAllMetroDevice() {
        return metroDeviceDao.findAll();
    }

    @Override
    public List<MetroDevice> getMetroDeviceByStationId(Integer stationId) {
        return metroDeviceDao.findByStationId(stationId);
    }

    @Override
    public Map<Integer, List<MetroDevice>> getMetroDevicesGroupByStationId() {
        List<MetroDevice> allMetroDevices = getAllMetroDevice();
        Map<Integer,List<MetroDevice>> map = new HashMap<>();
        for (MetroDevice metroDevice:allMetroDevices){
            map.computeIfAbsent(metroDevice.getStationId(),(key)->new ArrayList<>()).add(metroDevice);
        }
        return map;
    }

    @Override
    public MetroNode getNode(Long nodeId) {
        return getNodeByNo(NodeIdUtils.getNodeNo(nodeId));
    }

    @Override
    public MetroNode getNodeByNo(Long nodeNo) {
        if (nodeNo==0){
            return metroACCDao.findById((short)0).orElseThrow(()->new NodeNotFoundException(0L,AFCNodeLevel.ACC));
        }else if(nodeNo>0&&nodeNo<=0xff000000L){
            return metroLineDao.findById((short)(nodeNo>>24)).orElseThrow(()->
                    new NodeNotFoundException(nodeNo>>24,AFCNodeLevel.LC));
        }else if(nodeNo>0xff000000L&&nodeNo<=0xffff0000L){
            return metroStationDao.findByStationId((int)(nodeNo>>16))
                    .orElseThrow(()->new NodeNotFoundException(nodeNo>>16,AFCNodeLevel.SC));
        }else if(nodeNo>0xffff0000L&&nodeNo<=0xffffffffL){
            return metroDeviceDao.findByDeviceId(nodeNo).orElseThrow(()->
                    new NodeNotFoundException(nodeNo,AFCNodeLevel.SLE));
        }
        throw new NodeNotFoundException(nodeNo);
    }

    @Override
    public List<MetroStation> getMetroStationsByLineId(Short lineId) {
        return metroStationDao.findByLineId(lineId);
    }

    @Override
    public MetroDevice getDeviceNode(Long deviceId) {
        MetroNode metroNode = getNodeByNo(deviceId);
        if (metroNode.level()==AFCNodeLevel.SLE){
            return (MetroDevice)metroNode;
        }
        throw new NodeNotFoundException(deviceId,AFCNodeLevel.SLE);
    }

    @Override
    public String getNodeText(Object nodeId) {
        if (nodeId instanceof Integer) {
            int stationId = (Integer) nodeId;
            MetroStation metroStation = metroStationDao.findByStationId(stationId)
                    .orElseThrow(()->new ServiceException("节点不存在"));
            return String.format("%s/0x" + stationlineIdFormat, metroStation.name(), stationId);
        } else if (nodeId instanceof Short) {
            short lineId = (Short) nodeId;
            MetroLine metroLine = metroLineDao.getOne(lineId);
            return String.format("%s/0x" + lineIdFormat, metroLine.name(), lineId);
        } else if ("0".equals(nodeId.toString())) {
            return "清分中心/0x00000000";
        }

        if (nodeId instanceof Number) {
            Number nodeNo = (Number) nodeId;
            AFCNodeLevel level = NodeIdUtils.getNodeLevel(nodeNo.longValue());
            switch (level){
                case SC:{
                    int stationId = nodeNo.intValue()>>16;
                    MetroStation metroStation = metroStationDao.findByStationId(stationId)
                            .orElseThrow(()->new ServiceException("节点不存在"));
                    return String.format("%s/0x" + deviceIdFormat, metroStation.name(), nodeId);
                }
                case SLE:{
                    MetroDevice metroDevice = metroDeviceDao.findByDeviceId(nodeNo.longValue())
                            .orElseThrow(()->new ServiceException("节点不存在"));
                    return String.format("%s/0x" + deviceIdFormat, metroDevice.name(), nodeNo.longValue());
                }
                default:
            }
        }
        return "--";
    }

}
