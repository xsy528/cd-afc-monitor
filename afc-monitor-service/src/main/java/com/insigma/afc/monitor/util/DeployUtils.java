/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.util;

import com.insigma.afc.security.util.SecurityUtils;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.util.NodeIdUtils;

import java.util.*;

/**
 * Ticket: 部署点工具类
 *
 * @author xuzhemin
 * 2019/5/14 19:27
 */
public class DeployUtils {

    private static Short accId = 0;

    /**
     * 有acc部署点
     * @return 是否有acc部署点
     */
    public static boolean haveAcc() {
        List<Long> deployIds = SecurityUtils.getDeployIds();
        for (Long deploy : deployIds) {
            if (deploy == accId.longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有线路部署点
     *
     * @param lineId 线路Id
     * @return 是否有部署点
     */
    public static boolean haveLine(Short lineId) {
        List<Long> deployIds = SecurityUtils.getDeployIds();
        for (Long deploy : deployIds) {
            if (deploy == accId.longValue()){
                return true;
            }
            if (deploy == lineId.longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有车站部署点
     * @param stationId 车站id
     * @return 是否有车站部署点
     */
    public static boolean haveStation(Integer stationId) {
        Short lineId = NodeIdUtils.nodeIdStrategy.getLineId(stationId.longValue());
        List<Long> deployIds = SecurityUtils.getDeployIds();
        for (Long deploy : deployIds) {
            if (deploy == accId.longValue()){
                return true;
            }
            if (deploy == lineId.longValue()) {
                return true;
            }
            if (deploy == stationId.longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有设备部署点
     * @param deviceId 设备id
     * @return 是否有设备部署点
     */
    public static boolean haveDevice(Long deviceId) {
        Short lineId = NodeIdUtils.nodeIdStrategy.getLineId(deviceId);
        Integer stationId = NodeIdUtils.nodeIdStrategy.getStationId(deviceId);
        List<Long> deployIds = SecurityUtils.getDeployIds();
        for (Long deploy : deployIds) {
            if (deploy == accId.longValue()){
                return true;
            }
            if (deploy == lineId.longValue()) {
                return true;
            }
            if (deploy == stationId.longValue()) {
                return true;
            }
            if (deploy.equals(deviceId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取线路部署点
     *
     * @return 线路部署点集合
     */
    public static List<Short> getDeployLineIds() {
        List<Long> deployIds = SecurityUtils.getDeployIds();
        Set<Short> lineIds = new HashSet<>();
        for (Long deploy : deployIds) {
            if (NodeIdUtils.nodeIdStrategy.getNodeLevel(deploy)==AFCNodeLevel.LC) {
                lineIds.add(NodeIdUtils.nodeIdStrategy.getLineId(deploy));
            }
        }
        return Arrays.asList(lineIds.toArray(new Short[0]));
    }

    /**
     * 获取车站部署点
     * @return 车站部署点集合
     */
    public static List<Integer> getDeployStationIds() {
        List<Long> deployIds = SecurityUtils.getDeployIds();
        Set<Integer> stationIds = new HashSet<>();
        for (Long deploy : deployIds) {
            if (NodeIdUtils.nodeIdStrategy.getNodeLevel(deploy)==AFCNodeLevel.SC) {
                stationIds.add(NodeIdUtils.nodeIdStrategy.getStationId(deploy));
            }
        }
        return Arrays.asList(stationIds.toArray(new Integer[0]));
    }

    /**
     * 获取设备部署点
     * @return 设备部署点集合
     */
    public static List<Long> getDeployDeviceIds() {
        List<Long> deployIds = SecurityUtils.getDeployIds();
        List<Long> deviceIds = new ArrayList<>();
        for (Long deploy : deployIds) {
            if (NodeIdUtils.nodeIdStrategy.getNodeLevel(deploy)==AFCNodeLevel.SLE) {
                deviceIds.add(deploy);
            }
        }
        return deviceIds;
    }

}
