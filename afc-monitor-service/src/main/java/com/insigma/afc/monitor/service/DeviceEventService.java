package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.EquEventCondition;
import com.insigma.afc.monitor.model.vo.DeviceEvent;
import org.springframework.data.domain.Page;

/**
 * Tiket: 各类查询-设备事件查询服务
 * @author  xingshaoya
 */
public interface DeviceEventService {

    /**
     * 获取筛选的设备事件列表
     * @param condition 设备事件查询条件实体类
     * @return 分页数据
     */
    Page<DeviceEvent> getDeviceEventSearch(EquEventCondition condition);
}
