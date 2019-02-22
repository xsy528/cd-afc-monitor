package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.SystemConfigKey;
import com.insigma.afc.monitor.dao.TsyConfigDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.TsyConfig;
import com.insigma.afc.monitor.service.MonitorConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Ticket:监控配置服务实现类
 *
 * @author xuzhemin
 * 2019-01-09:16:44
 */
@Service
public class MonitorConfigServiceImpl implements MonitorConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorConfigServiceImpl.class);

    private TsyConfigDao tsyConfigDao;

    @Autowired
    public MonitorConfigServiceImpl(TsyConfigDao tsyConfigDao){
        this.tsyConfigDao = tsyConfigDao;
    }

    @Override
    public Result<MonitorConfigInfo> getMonitorConfig(){
        TsyConfig warningConfig = tsyConfigDao.findById(SystemConfigKey.WARNING_THRESHHOLD).orElse(null);
        TsyConfig alarmConfig = tsyConfigDao.findById(SystemConfigKey.ALARM_THRESHHOLD).orElse(null);
        TsyConfig refreshIntervalConfig = tsyConfigDao.findById(SystemConfigKey.VIEW_REFRESH_INTERVAL)
                .orElse(null);
        Integer warning;
        Integer alarm;
        Integer interval;
        if (warningConfig==null){
            warning = 0;
        }else{
            warning = Integer.valueOf(warningConfig.getConfigValue());
        }
        if (alarmConfig==null){
            alarm = 0;
        }else{
            alarm = Integer.valueOf(alarmConfig.getConfigValue());
        }
        if (refreshIntervalConfig==null){
            interval = 0;
        }else{
            interval = Integer.valueOf(refreshIntervalConfig.getConfigValue());
        }
        return Result.success(new MonitorConfigInfo(warning,alarm,interval));
    }

    @Override
    public Result<MonitorConfigInfo> save(MonitorConfigInfo monitorConfigInfo) {
        int warning = monitorConfigInfo.getWarning();
        int alarm = monitorConfigInfo.getAlarm();
        int interval = monitorConfigInfo.getInterval();

        //警告阈值要小于报警阈值
        if (alarm <= warning) {
            return Result.error(ErrorCode.THRESHOLD_INVALID);
        } else if (interval < 5) {
            //时间间隔不能小于5秒
            return Result.error(ErrorCode.REFRESH_INTERVAL_INVALID);
        }

        //保存配置
        List<TsyConfig> tsyConfigList = new ArrayList<>();
        tsyConfigList.add(new TsyConfig(SystemConfigKey.ALARM_THRESHHOLD,String.valueOf(alarm)));
        tsyConfigList.add(new TsyConfig(SystemConfigKey.WARNING_THRESHHOLD,String.valueOf(warning)));
        tsyConfigList.add(new TsyConfig(SystemConfigKey.VIEW_REFRESH_INTERVAL,String.valueOf(interval)));
        tsyConfigDao.saveAll(tsyConfigList);

        return Result.success(monitorConfigInfo);
    }

}
