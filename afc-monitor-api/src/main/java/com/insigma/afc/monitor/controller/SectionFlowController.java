/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.service.SectionODFlowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 10:50
 */
@Api(tags="断面客流查询接口")
@RestController
@RequestMapping("/monitor/sectionflow")
public class SectionFlowController {

    private SectionODFlowService sectionODFlowService;

    @PostMapping("/search")
    public Result search(@Valid @RequestBody SectionFlowCondition condition){
        return Result.success(sectionODFlowService.getSectionODFlowStatsViewList(condition));
    }

    @Autowired
    public void setSectionODFlowService(SectionODFlowService sectionODFlowService) {
        this.sectionODFlowService = sectionODFlowService;
    }
}
