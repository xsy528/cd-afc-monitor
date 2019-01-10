package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.monitor.form.WZMonitorConfigInfo;
import com.insigma.acc.wz.web.model.vo.Result;

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
