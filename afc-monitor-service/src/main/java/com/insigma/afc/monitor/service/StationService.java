package com.insigma.afc.monitor.service;

import java.util.Map;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/28 14:25.
 * @Ticket :
 */
public interface StationService {
    /**
     * 获取车站id和名称
     * @return
     */
    Map<Integer,String> getStationNameAndId();

    /**
     * 根据id获取name
     * @param id
     * @return
     */
    String getStationNameById(Integer id);
}
