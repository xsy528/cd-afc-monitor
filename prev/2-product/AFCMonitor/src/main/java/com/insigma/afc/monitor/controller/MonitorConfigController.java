package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.MonitorConfigInfo;
import com.insigma.afc.monitor.service.MonitorConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Ticket: 监控配置接口
 *
 * @author xuzhemin
 * 2019-01-09:14:37
 */
@Api(tags="监控配置接口")
@RestController
@RequestMapping("/monitor/config")
public class MonitorConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorConfigController.class);

    private MonitorConfigService monitorConfigService;

    @Autowired
    public MonitorConfigController(MonitorConfigService monitorConfigService){
        this.monitorConfigService = monitorConfigService;
    }

    @ApiOperation("获取监控配置信息")
    @GetMapping("/get")
    public Result<MonitorConfigInfo> getMonitorConfig(){
        return monitorConfigService.getMonitorConfig();
    }

    @ApiOperation("保存配置监控信息")
    @PostMapping("/save")
    public Result<MonitorConfigInfo> saveMonitorConfig(@RequestBody MonitorConfigInfo monitorConfigInfo){
        return monitorConfigService.save(monitorConfigInfo);
    }
}
