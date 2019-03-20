package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatus;
import org.springframework.data.domain.Page;

public interface DeviceEventService {

    /**
     *
     * @return
     */
    Page<TmoEquStatus> getDeviceEventSearch(DeviceEventCondition condition);
}
