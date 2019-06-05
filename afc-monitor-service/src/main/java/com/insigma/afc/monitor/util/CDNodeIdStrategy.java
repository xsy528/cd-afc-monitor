/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.util;

import com.insigma.afc.monitor.constant.dic.AFCDeviceType;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.topology.INodeIdStrategy;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/12 10:11
 */
public class CDNodeIdStrategy implements INodeIdStrategy {
    /**
     * 将id补全为八位十六进制
     * 例如:车站编号0x0131，转换后会变成0x01310000
     * @param id 节点编号
     * @return 八位表示的id
     */
    @Override
    public Long getNodeNo(Long id) {
        //仅有车站号0421
        if (id > 99 && id < 9999 && id != getAccNodeId()) {
            return id * 100000 + AFCDeviceType.SC * 1000;
        }
        //仅有线路号04
        else if (id < 99) {
            return id * 10000000 + AFCDeviceType.LC * 1000;
        }
        //id全
        else if (id > 9999 && id < 999999999) {
            return id;
        }
        //accid
        else if (id > 99 && id < 9999 && id == getAccNodeId()) {
            return id;
        } else {
            throw new RuntimeException("无效的nodeId" + id);
        }
    }

    /**
     * 获取节点等级
     * @param nodeId 节点id
     * @return 节点等级
     */
    @Override
    public AFCNodeLevel getNodeLevel(Long nodeId) {
        return getNodeLevelByNo(getNodeNo(nodeId));
    }

    /**
     * 获取节点等级
     * @param nodeNo 完整的8位节点编号
     * @return
     */
    @Override
    public AFCNodeLevel getNodeLevelByNo(Long nodeNo) {
        short deviceType = getDeviceType(nodeNo);
        short lineId = getLineId(nodeNo);
        int stationId = getStationOnlyId(nodeNo);
        if (lineId == 0 && stationId == 0 && deviceType == AFCDeviceType.CCHS) {
            return AFCNodeLevel.ACC;
        } else if (lineId != 0 && stationId == 0 && deviceType == AFCDeviceType.LC) {
            return AFCNodeLevel.LC;
        } else if (lineId != 0 && stationId != 0 && deviceType == AFCDeviceType.SC) {
            return AFCNodeLevel.SC;
        } else {
            return AFCNodeLevel.SLE;
        }
    }

    public short getStationOnlyId(long nodeId) {
        if (nodeId > 99 && nodeId < 9999 && nodeId != getAccNodeId()) {
            return (short) (nodeId % 100);
        } else if ((nodeId / 100000 % 100) > 0) {
            return (short) (nodeId / 100000 % 100);
        } else if (((nodeId / 100000) % 100) == 0) {
            return 0;
        } else {
            throw new RuntimeException("无效的nodeId" + nodeId);
        }
    }

    /**
     * 获取设备类型
     * @param deviceId 设备id
     * @return 设备类型
     */
    @Override
    public short getDeviceType(Long deviceId) {
        long newNodeID = getNodeNo(deviceId);
        short nodeType = (short) ((newNodeID / 1000) % 100);
        return nodeType;
    }

    /**
     * 从八位十六进制id中截取前两位线路id
     * @param nodeId 八位十六进制id
     * @return 两位的线路编号
     */
    @Override
    public short getLineId(Long nodeId) {
        if (nodeId < 99) {
            return nodeId.shortValue();
        } else if (nodeId > 99 && nodeId < 9999 && nodeId != getAccNodeId()) {
            return (short) (nodeId / 100);
        } else {
            return (short) (nodeId / 10000000);
        }
    }

    public int getAccNodeId() {
        return AFCDeviceType.CCHS*1000;
    }

    /**
     * 从八位十六进制id中截取前四位车站id
     * @param nodeId 八位十六进制id
     * @return 四位的车站编号
     */
    @Override
    public int getStationId(Long nodeId) {
        if (nodeId > 99 && nodeId < 9999 && nodeId != getAccNodeId()) {
            return nodeId.intValue();
        } else if ((nodeId / 100000) > 0) {
            return (int) (nodeId / 100000);
        } else if (((nodeId / 100000) % 100) == 0) {
            return 0;
        } else {
            throw new RuntimeException("无效的nodeId" + nodeId);
        }
    }
}
