package com.insigma.afc.topology.service;

import com.insigma.afc.topology.model.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Ticket: 线网接口
 *
 * @author xuzhemin
 * 2019-01-28:15:34
 */
public interface TopologyService {

    /**
     * 获取ACC节点
     * @return
     */
    MetroACC getMetroACC();

    /**
     * 获取所有线路
     * @return
     */
    List<MetroLine> getAllMetroLine();

    /**
     * 获取所有车站
     * @return
     */
    List<MetroStation> getAllMetroStation();

    /**
     * 获取按照线路分组的所有车站
     * @return
     */
    Map<Short,List<MetroStation>> getMetroStationsGroupByLineId();

    /**
     * 获取所有设备
     * @return
     */
    List<MetroDevice> getAllMetroDevice();

    /**
     * 获取车站所有设备
     * @return
     */
    List<MetroDevice> getMetroDeviceByStationId(Integer stationId);

    /**
     * 获取按照车站分组的所有设备
     * @return
     */
    Map<Integer,List<MetroDevice>> getMetroDevicesGroupByStationId();

    /**
     * 根据节点id获取节点
     * @param nodeId
     * @return
     */
    MetroNode getNode(Long nodeId);

    /**
     * 根据完整节点id获取指定等级的节点
     * @param nodeId
     * @return
     */
    MetroNode getNodeByNo(Long nodeId);

    /**
     * 根据线路id获取全部车站
     * @param lineId
     * @return
     */
    List<MetroStation> getMetroStationsByLineId(Short lineId);

    /**
     * 获取设备节点
     * @param deviceId
     * @return
     */
    MetroDevice getDeviceNode(Long deviceId);

    /**
     * 获取节点的名称和编号
     * @param nodeId
     * @return
     */
    String getNodeText(Object nodeId);

}
