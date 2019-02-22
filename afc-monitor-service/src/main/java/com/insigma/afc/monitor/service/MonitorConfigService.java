package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.afc.monitor.model.dto.Result;

/**
 * Ticket: 监控配置信息服务
 *
 * @author xuzhemin
 * 2019-01-09:14:44
 */
public interface MonitorConfigService {
    /**
     * 获取监控配置
     * @return
     */
    Result<MonitorConfigInfo> getMonitorConfig();

    /**
     * 保存监控配置
     * @param monitorConfigInfo
     * @return
     */
    Result<MonitorConfigInfo> save(MonitorConfigInfo monitorConfigInfo);
}
