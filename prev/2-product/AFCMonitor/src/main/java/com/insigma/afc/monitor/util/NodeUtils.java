package com.insigma.afc.monitor.util;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: 节点工具
 *
 * @author xuzhemin
 * 2018-12-28:10:50
 */
public class NodeUtils {

    private static Map<Integer, MetroStation> stationMap = new HashMap<>();

    private NodeUtils(){

    }

    /**
     * 将十进制节点id转化为十六进制字符串
     * @param id 十进制id
     * @param afcNodeLevel 节点等级
     * @return 十六进制字符串
     */
    public static String getNodeId(long id, AFCNodeLevel afcNodeLevel){
        switch (afcNodeLevel){
            case ACC:{
                return "0x00000000";
            }
            case LC: {
                return String.format("0x%02x000000",id);
            }
            case SC:{
                return String.format("0x%04x0000",id);
            }
            case SLE:{
                return String.format("0x%08x",id);
            }
            default: return "";
        }
    }

    public static String getNodeText(long id, AFCNodeLevel afcNodeLevel){
        switch (afcNodeLevel){
            case ACC:{
                return "清分中心/0x00000000";
            }
            case LC: {
                return String.format("温州S%s线/0x%02x000000",id,id);
            }
            case SC:{
                Map<Integer, MetroStation> stationMap = (Map<Integer, MetroStation>) Application
                        .getData(ApplicationKey.STATION_LIST_KEY);
                for (Map.Entry<Integer, MetroStation> entry : stationMap.entrySet()) {
                    if (entry.getKey().longValue()==id) {
                        return String.format("%s/0x%04x0000", entry.getValue().getStationName(), id);
                    }
                }
                return String.format("--/0x%04x0000", id);
            }
            case SLE:{
                MetroNode node = AFCApplication.getNode(id);
                if (node == null) {
                    return String.format("--/0x%08x", id);
                }
                return String.format("%s/0x%08x",node.name(), id);
            }
            default: return "--/--";
        }
    }

    public static String getNodeText(Object itemObject) {
        if (stationMap.isEmpty()){
            synchronized (stationMap) {
                if (stationMap.isEmpty()) {
                    stationMap = (Map<Integer, MetroStation>) Application.getData(ApplicationKey.STATION_LIST_KEY);
                }
            }
        }

        if (itemObject instanceof Integer) {
            int stationId = (Integer) itemObject;
            MetroNode node = AFCApplication.getNode(stationId);
            if (node == null) {
                return String.format(Application.getStationlineIdFormat(), stationId);
            }
            return String.format("%s/0x" + Application.getStationlineIdFormat(), node.name(), stationId);
        } else if (itemObject instanceof Short) {
            short lineId = (Short) itemObject;
            MetroNode node = AFCApplication.getNode(lineId);
            if (node == null) {
                String nodeName = String.format("温州S%s线",lineId);
                return String.format("%s/0x" + Application.getLineIdFormat(),nodeName, lineId);
            }
            return String.format("%s/0x" + Application.getLineIdFormat(), node.name(), lineId);
        } else if ("0".equals(itemObject.toString())) {
            return "清分中心/0x00000000";
        }

        if (itemObject instanceof Number) {
            Number nodeId = (Number) itemObject;

            if (AFCApplication.getNodeLevel(nodeId.longValue()) != null) {
                //车站节点
                if (AFCApplication.getNodeLevel(nodeId.longValue()).equals(AFCNodeLevel.SC)) {
                    int stationId = AFCApplication.getStationId(nodeId.longValue());

                    for (Map.Entry<Integer, MetroStation> entry : stationMap.entrySet()) {
                        if (entry.getKey().equals(stationId)) {
                            return String.format("%s/0x" + Application.getDeviceIdFormat(),
                                    entry.getValue().getStationName(), nodeId);
                        }
                    }
                    return "--/" + String.format(Application.getDeviceIdFormat(),
                            AFCApplication.getNodeId(nodeId.longValue()));

                }
                //设备节点
                else if (AFCApplication.getNodeLevel(nodeId.longValue()).equals(AFCNodeLevel.SLE)) {
                    MetroNode node = AFCApplication.getNode(nodeId.longValue());
                    if (node == null) {
                        return "--/" + String.format(Application.getDeviceIdFormat(),
                                AFCApplication.getNodeId(nodeId.longValue()));
                    }
                    return String.format("%s/0x" + Application.getDeviceIdFormat(), node.name(), nodeId.longValue());
                }
            }

        }
        return "--";
    }

    /**
     * 将十六进制字符串转化为十进制id
     * @param nodeId 例 0x01300102
     * @param afcNodeLevel 节点等级
     * @return 十进制id
     */
    public static int getNodeId(String nodeId,AFCNodeLevel afcNodeLevel){
        int id = Integer.parseInt(nodeId.substring(2),16);
        switch (afcNodeLevel){
            case ACC:{
                return 0;
            }
            case LC: {
                return id>>24;
            }
            case SC:{
                return id>>16;
            }
            case SLE:{
                return id;
            }
            default: return -1;
        }
    }

    public static AFCNodeLevel getNodeLevel(long nodeId){
        if (nodeId==0){
            return AFCNodeLevel.ACC;
        }else if (nodeId<=0xff){
            return AFCNodeLevel.LC;
        }else if (nodeId<=0xffff){
            return AFCNodeLevel.SC;
        }else if(nodeId<=0xffffffff){
            return AFCNodeLevel.SLE;
        }
        return null;
    }
}
