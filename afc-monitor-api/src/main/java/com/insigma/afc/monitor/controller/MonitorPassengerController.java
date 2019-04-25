package com.insigma.afc.monitor.controller;


import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SeriesChartDTO;
import com.insigma.afc.monitor.model.dto.condition.BarAndPieCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import com.insigma.afc.monitor.service.PassengerFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xingshaoya
 * @date 2019-03-22 09:44:30
 */
@Api(tags = "客流监控接口")
@RestController
@RequestMapping("/monitor/passenger/")
public class MonitorPassengerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorPassengerController.class);

    private PassengerFlowService passengerFlowService;

    @Autowired
    public MonitorPassengerController(PassengerFlowService passengerFlowService) {
        this.passengerFlowService = passengerFlowService;
    }

    @ApiOperation("获取客流柱状图、饼图")
    @PostMapping("barPieChart")
    public Result<List<BarPieChartDTO>> getBarPieChart(@Valid @RequestBody BarAndPieCondition barAndPieCondition) {
        return Result.success(passengerFlowService.getBarPieChart(barAndPieCondition));
    }

    @ApiOperation("获取客流曲线图")
    @PostMapping("seriesChart")
    public Result<List<SeriesChartDTO>> getSeriesChart(@Valid @RequestBody SeriesCondition seriesCondition) {
        return Result.success(passengerFlowService.getSeriesChart(seriesCondition));
    }

    @ApiOperation("客流查询接口")
    @PostMapping("passengerFlow")
    public Result<Page<ODSearchResultItem>> getPassengerFlow(@Valid @RequestBody PassengerCondition passengerCondition) {
        return Result.success(passengerFlowService.getODSerchResult(passengerCondition));
    }
}