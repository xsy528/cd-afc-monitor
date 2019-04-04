package com.insigma.afc.monitor.controller;


import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.condition.BarAndSeriesCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.entity.PassengerData;
import com.insigma.afc.monitor.model.vo.BarChartData;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import com.insigma.afc.monitor.model.vo.PieChartData;
import com.insigma.afc.monitor.model.vo.SeriesChartData;
import com.insigma.afc.monitor.service.PassengerFlowService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  xingshaoya
 * @date  2019-03-22 09:44:30
 */
@Api(tags = "客流监控查询接口")
@RestController
@RequestMapping("/monitor/query/")
public class MonitorPassengerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorPassengerController.class);

    private PassengerFlowService passengerFlowService;

    private TopologyService topologyService;

    @Autowired
    public MonitorPassengerController(PassengerFlowService passengerFlowService,TopologyService topologyService) {
        this.passengerFlowService = passengerFlowService;
        this.topologyService = topologyService;
    }

    @ApiOperation("获取客流柱状图")
    @PostMapping("barChart")
    public Result<BarChartData> getBarChart(@Valid @RequestBody BarAndSeriesCondition barAndSeriesCondition) {
      //  try {
            BarChartData data = passengerFlowService.getBarChart(barAndSeriesCondition);
             List<Integer> transType = barAndSeriesCondition.getTransType();
        if (data != null) {
                List<Boolean> showRows = new ArrayList<Boolean>();
                for (int i = 0; i < BarAndSeriesCondition.LEGEND.length; i++) {
                    if (transType.get(i) != 0) {
                        showRows.add(true);
                    } else {
                        showRows.add(false);
                    }
                }
                data.setShowRows(showRows);

            }
            return Result.success(data);
      //  } catch (Exception e) {
       //     return Result.error(ErrorCode.DATABASE_ERROR);
      //  }
    }

    @ApiOperation("获取客流饼状图")
    @PostMapping("pieChart")
    public Result<PieChartData> getPieChart(@Valid @RequestBody BarAndSeriesCondition barAndSeriesCondition) {
    //    try {
            PieChartData data = passengerFlowService.getPieChat(barAndSeriesCondition);
             List<Integer> transType = barAndSeriesCondition.getTransType();
            if (data != null) {
                List<Boolean> showRows = new ArrayList<Boolean>();
                for (int i = 0; i < BarAndSeriesCondition.LEGEND.length - 1; i++) {
                    if (transType.get(i) != 0) {
                        showRows.add(true);
                    } else {
                        showRows.add(false);
                    }
                }
                data.setShowRows(showRows);
            }
            return Result.success(data);
    //    } catch (Exception e) {
     //       return Result.error(ErrorCode.DATABASE_ERROR);
     //   }
    }

    @ApiOperation("获取客流曲线图")
    @PostMapping("seriesChart")
    public Result<SeriesChartData> getSeriesChart(@Valid @RequestBody SeriesCondition seriesCondition) {
       // try {
            SeriesChartData data = passengerFlowService.getSeriesChart(seriesCondition);
        List<Integer> transType = seriesCondition.getTransType();
            if (data != null) {
                List<Boolean> showRows = new ArrayList<Boolean>();
                for (int i = 0; i < SeriesCondition.LEGEND.length - 1; i++) {
                    if (transType.get(i) != 0)  {
                        showRows.add(true);
                    } else {
                        showRows.add(false);
                    }
                }
                data.setShowRows(showRows);
            }
            return Result.success(data);
      //  } catch (Exception e) {
      //      return Result.error(ErrorCode.DATABASE_ERROR);
      //  }
    }

    @ApiOperation("客流查询接口")
    @PostMapping("passengerFlow")
    public Result<List<ODSearchResultItem>> getPassengerFlow(@Valid @RequestBody PassengerCondition passengerCondition) {

        List<ODSearchResultItem> results = passengerFlowService.getODSerchResult(passengerCondition);


        return Result.success(results);
    }
}