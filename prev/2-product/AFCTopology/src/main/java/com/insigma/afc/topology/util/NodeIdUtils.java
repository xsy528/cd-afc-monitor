package com.insigma.afc.topology.util;

import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.constant.dic.AFCDeviceType;

/**
 * Ticket: 节点id工具类
 *
 * @author xuzhemin
 * 2019-02-02:11:18
 */
public class NodeIdUtils {

    private NodeIdUtils(){}

    /**
     * 将id补全为八位十六进制
     * 例如:车站编号0x0131，转换后会变成0x01310000
     * @param id 节点编号
     * @return 八位表示的id
     */
    public static Long getNodeNo(Long id) {
        long nodeId;
        if (id < 0xff) {
            nodeId = (id << 24);
        } else if (id > 0x100 && id < 0xffff) {
            nodeId = (id << 16);
        } else {
            return id;
        }
        return nodeId;
    }

    /**
     * 获取节点等级
     * @param nodeId 节点id
     * @return 节点等级
     */
    public static AFCNodeLevel getNodeLevel(Long nodeId) {
        return getNodeLevelByNo(getNodeNo(nodeId));
    }

    /**
     * 获取节点等级
     * @param nodeNo 完整的8位节点编号
     * @return
     */
    public static AFCNodeLevel getNodeLevelByNo(Long nodeNo) {
        if ((nodeNo >> 24) == 0L) {
            return AFCNodeLevel.ACC;
        } else if ((nodeNo >> 24) != 0L && (nodeNo & 0x00FFFFFFL) == 0L) {
            return AFCNodeLevel.LC;
        } else if ((nodeNo >> 24) != 0L && ((nodeNo & 0x00FF0000L) >> 16) != 0L
                && ((nodeNo & 0x0000FF00L) >> 8) == 0L) {
            return AFCNodeLevel.SC;
        } else if ((nodeNo >> 24) != 0L && ((nodeNo & 0x00FF0000L) >> 16) != 0L
                && ((nodeNo & 0x0000FF00L) >> 8) != 0L) {
            return AFCNodeLevel.SLE;
        } else {
            throw new RuntimeException("无效的nodeId");
        }
    }

    /**
     * 获取节点类型
     * @param nodeId 节点id
     * @return 节点类型
     */
    public static short getNodeType(Long nodeId) {
        nodeId = getNodeNo(nodeId);
        if ((nodeId >> 24) == 0L) {
            //ACC
            return AFCDeviceType.CCHS;
        } else if ((nodeId >> 24) != 0L && (nodeId & 0x00FFFFFFL) == 0L) {
            //LC
            return AFCDeviceType.LC;
        } else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
                && ((nodeId & 0x0000FF00L) >> 8) == 0L) {
            //SC
            return AFCDeviceType.SC;
        } else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
                && ((nodeId & 0x0000FF00L) >> 8) != 0L) {
            //SLE设备类型
            return (short) ((nodeId & 0x0000ff00) >> 8);
        } else {
            throw new RuntimeException("无效的nodeId");
        }
    }

    /**
     * 获取设备类型
     * @param deviceId 设备id
     * @return 设备类型
     */
    public static short getDeviceType(Long deviceId) {
        return (short) ((deviceId & 0x0000ff00) >> 8);
    }

    /**
     * 从八位十六进制id中截取前两位线路id
     * @param nodeId 八位十六进制id
     * @return 两位的线路编号
     */
    public static short getLineId(Long nodeId) {
        if (nodeId < 0xff) {
            return nodeId.shortValue();
        } else if (nodeId > 0xff && nodeId < 0xffff) {
            return (short) (nodeId >> 8);
        } else {
            return (short) (nodeId >> 24);
        }

    }

    /**
     * 从八位十六进制id中截取前四位车站id
     * @param nodeId 八位十六进制id
     * @return 四位的车站编号
     */
    public static int getStationId(Long nodeId) {
        if ((nodeId >> 24) == 0L) {
            return 0;
        } else if ((nodeId >> 24) != 0L && (nodeId & 0x00FFFFFFL) == 0L) {
            return 0;
        } else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
                && ((nodeId & 0x0000FF00L) >> 8) == 0L) {
            return (int) ((nodeId & 0xFFFF0000L) >> 16);
        } else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
                && ((nodeId & 0x0000FF00L) >> 8) != 0L) {
            return (int) ((nodeId & 0xFFFF0000L) >> 16);
        } else {
            throw new RuntimeException("无效的nodeId");
        }
    }
}
