package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.vo.WZMonitorConfigInfo;
import com.insigma.afc.monitor.model.dto.Result;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-09:14:44
 */
public interface MonitorConfigService {
    Result<WZMonitorConfigInfo> getMonitorConfig();
    Result<WZMonitorConfigInfo> save(WZMonitorConfigInfo monitorConfigInfo);
}
