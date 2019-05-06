package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.NodeStatusMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.SectionFlowMonitorConfigDTO;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Ticket: 监控配置接口
 *
 * @author xuzhemin
 * 2019-01-09 14:37
 */
@Api(tags = "监控配置接口")
@RestController
@RequestMapping("/monitor/config")
public class MonitorConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorConfigController.class);

    private MonitorConfigService monitorConfigService;

    @Autowired
    public MonitorConfigController(MonitorConfigService monitorConfigService) {
        this.monitorConfigService = monitorConfigService;
    }

    @ApiOperation("获取节点监控配置信息")
    @PostMapping("/get")
    public Result<NodeStatusMonitorConfigDTO> getMonitorConfig() {
        return monitorConfigService.getMonitorConfig();
    }

    @ApiOperation("保存节点监控配置信息")
    @PostMapping("/save")
    public Result<NodeStatusMonitorConfigDTO> saveMonitorConfig(@Valid @RequestBody NodeStatusMonitorConfigDTO
                                                                            monitorConfigInfo) {
        return monitorConfigService.save(monitorConfigInfo);
    }

    @ApiOperation("获取断面客流监控配置信息")
    @PostMapping("/getSectionFlowConfig")
    public Result<SectionFlowMonitorConfigDTO> getSectionFlowConfig() {
        return monitorConfigService.getSectionFlowMonitorConfig();
    }

    @ApiOperation("保存断面客流监控配置信息")
    @PostMapping("/saveSectionFlowConfig")
    public Result<SectionFlowMonitorConfigDTO> saveSectionFlowConfig(@Valid @RequestBody SectionFlowMonitorConfigDTO
                                                                                 configDTO) {
        return monitorConfigService.save(configDTO);
    }
}
