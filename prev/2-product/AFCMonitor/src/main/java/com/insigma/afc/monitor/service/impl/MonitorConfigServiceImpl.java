package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.model.vo.WZMonitorConfigInfo;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.service.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-09:16:44
 */
public class MonitorConfigServiceImpl implements MonitorConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorConfigServiceImpl.class);

    private ILogService logSysService;

    @Override
    public Result<WZMonitorConfigInfo> getMonitorConfig(){
        return Result.success(new WZMonitorConfigInfo());
    }

    @Override
    public Result<WZMonitorConfigInfo> save(WZMonitorConfigInfo monitorConfigInfo) {
        int warning = monitorConfigInfo.getWarning();
        int alarm = monitorConfigInfo.getAlarm();
        int interval = monitorConfigInfo.getInterval();

        if (alarm <= warning) {
            Result.error(ErrorCode.THRESHOLD_INVALID);
        } else if (interval < 5) {
            Result.error(ErrorCode.REFRESH_INTERVAL_INVALID);
        }

        try {
            SystemConfigManager.setConfigItem(MonitorConfigInfo.ALARM_THRESHHOLD, alarm);
            SystemConfigManager.setConfigItem(MonitorConfigInfo.WARNING_THRESHHOLD, warning);
            SystemConfigManager.setConfigItem(MonitorConfigInfo.VIEW_REFRESH_INTERVAL, interval);

        } catch (ApplicationException e) {
            LOGGER.error("保存参数设置失败");
            logSysService.doBizLog("监控参数设置失败。");
            return Result.error(ErrorCode.UNKNOW_ERROR);
        }
        return Result.success(monitorConfigInfo);
    }

    public void setLogSysService(ILogService logSysService) {
        this.logSysService = logSysService;
    }
}
