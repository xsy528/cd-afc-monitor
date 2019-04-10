/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SectionMonitorDTO;
import com.insigma.afc.monitor.model.dto.SectionValuesDTO;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowMonitorCondition;
import com.insigma.afc.monitor.model.vo.SectionOdFlowStatsView;
import com.insigma.afc.monitor.service.SectionODFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 10:50
 */
@Api(tags="断面客流接口")
@RestController
@RequestMapping("/monitor/sectionflow")
public class SectionFlowController {

    private SectionODFlowService sectionODFlowService;

    @ApiOperation("断面客流查询")
    @PostMapping("/search")
    public Result<Page<SectionOdFlowStatsView>> search(@Valid @RequestBody SectionFlowCondition condition){
        return Result.success(sectionODFlowService.getSectionODFlowStatsViewList(condition));
    }

    @ApiOperation("断面客流监控图")
    @PostMapping("/monitor")
    public Result<SectionMonitorDTO> monitor(@Valid @RequestBody SectionFlowMonitorCondition condition){
        return Result.success(sectionODFlowService.getSectionODFlowDensity(condition));
    }

    @ApiOperation("断面信息")
    @PostMapping("/sectionInfo")
    public Result<List<SectionValuesDTO>> sections(){
        return Result.success(sectionODFlowService.getSectionValues());
    }

    @Autowired
    public void setSectionODFlowService(SectionODFlowService sectionODFlowService) {
        this.sectionODFlowService = sectionODFlowService;
    }
}
