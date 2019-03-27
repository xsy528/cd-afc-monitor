package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.SystemConfigKey;
import com.insigma.afc.monitor.dao.TsyConfigDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.NodeStatusMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SectionFlowMonitorConfigDTO;
import com.insigma.afc.monitor.model.entity.TsyConfig;
import com.insigma.afc.monitor.service.MonitorConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Result<NodeStatusMonitorConfigDTO> getMonitorConfig(){
        List<TsyConfig> tsyConfigs = tsyConfigDao.findAllById(Arrays.asList(SystemConfigKey.WARNING_THRESHHOLD,
                SystemConfigKey.ALARM_THRESHHOLD, SystemConfigKey.VIEW_REFRESH_INTERVAL));

        Integer warning = 0;
        Integer alarm = 0;
        Integer interval = 0;
        for (TsyConfig tsyConfig:tsyConfigs){
            switch (tsyConfig.getConfigKey()){
                case SystemConfigKey.WARNING_THRESHHOLD:{
                    warning = Integer.valueOf(tsyConfig.getConfigValue());
                    break;
                }
                case SystemConfigKey.ALARM_THRESHHOLD:{
                    alarm = Integer.valueOf(tsyConfig.getConfigValue());
                    break;
                }
                case SystemConfigKey.VIEW_REFRESH_INTERVAL:{
                    interval = Integer.valueOf(tsyConfig.getConfigValue());
                    break;
                }
                default:
            }
        }
        return Result.success(new NodeStatusMonitorConfigDTO(warning,alarm,interval));
    }

    @Override
    public Result<NodeStatusMonitorConfigDTO> save(NodeStatusMonitorConfigDTO monitorConfigInfo) {
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

    @Override
    public Result<SectionFlowMonitorConfigDTO> getSectionFlowMonitorConfig() {
        List<TsyConfig> tsyConfigs = tsyConfigDao.findAllById(Arrays.asList(
                SystemConfigKey.SECTION_PASSENGERFLOW_LOW,
                SystemConfigKey.SECTION_PASSENGERFLOW_HIGH));

        Integer warning = 20000;
        Integer alarm = 40000;
        for (TsyConfig tsyConfig:tsyConfigs){
            switch (tsyConfig.getConfigKey()){
                case SystemConfigKey.SECTION_PASSENGERFLOW_LOW:{
                    warning = Integer.valueOf(tsyConfig.getConfigValue());
                    break;
                }
                case SystemConfigKey.SECTION_PASSENGERFLOW_HIGH:{
                    alarm = Integer.valueOf(tsyConfig.getConfigValue());
                    break;
                }
                default:
            }
        }
        return Result.success(new SectionFlowMonitorConfigDTO(warning,alarm));
    }

    @Override
    public Result<SectionFlowMonitorConfigDTO> save(SectionFlowMonitorConfigDTO monitorConfigDTO) {
        int warning = monitorConfigDTO.getWarning();
        int alarm = monitorConfigDTO.getAlarm();
        if (warning>=alarm){
            return Result.error(ErrorCode.THRESHOLD_INVALID);
        }
        //保存配置
        List<TsyConfig> tsyConfigList = new ArrayList<>();
        tsyConfigList.add(new TsyConfig(SystemConfigKey.SECTION_PASSENGERFLOW_HIGH,String.valueOf(alarm)));
        tsyConfigList.add(new TsyConfig(SystemConfigKey.SECTION_PASSENGERFLOW_LOW,String.valueOf(warning)));
        tsyConfigDao.saveAll(tsyConfigList);
        return Result.success(monitorConfigDTO);
    }

}
