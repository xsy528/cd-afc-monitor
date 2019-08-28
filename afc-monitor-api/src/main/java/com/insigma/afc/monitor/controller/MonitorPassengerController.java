package com.insigma.afc.monitor.controller;


import com.insigma.afc.monitor.constant.dic.cd.CDTicketFamily;
import com.insigma.afc.monitor.dao.util.PageList;
import com.insigma.afc.monitor.model.ResultContent;
import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesChartDTO;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import com.insigma.afc.monitor.model.vo.TicketCompareVO;
import com.insigma.afc.monitor.model.vo.TimeShareVO;
import com.insigma.afc.monitor.service.PassengerFlowService;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 14:19.
 * @Ticket :
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

    @ApiOperation("客流监控-分时查询")
    @PostMapping("timeShareQuery")
    public Result<ResultContent<List<TimeShareVO>>> getTimeShareInfo(@RequestBody TimeShareCondition condition){

        PageList pageList = passengerFlowService.getShareODSerchResult(condition);



        return  Result.success(ResultContent.content(PageRequest.of(condition.getPageNumber(),condition.getPageSize()),pageList.getTotalRows()+0L,
                getTimeShareInfo(pageList)));
    }

    @ApiOperation("客流监控-票卡对比")
    @PostMapping("passengerTicketCompare")
    public Result<ResultContent<List<TicketCompareVO>>> getTicketCompare(@RequestBody TicketCompareCondition condition){

        PageList pageList = passengerFlowService.getTicketCompareResult(condition);

        List list = pageList.getList();
        List<TicketCompareVO> data = new ArrayList<>();
        String[] partNames = condition.getPartNames();

        for(int i = 0 ;i<partNames.length;i++) {
            data.add(new TicketCompareVO(getPie(list,i),partNames[i]));
        }


        return Result.success(ResultContent.content(PageRequest.of(condition.getPageNumber(),condition.getPageSize()),pageList.getTotalRows()+0L,
               data));
    }
    private BarPieChartDTO getPie(List list,int index){
        BarPieChartDTO dto = new BarPieChartDTO();
        for (Object object:list) {
            Map<String, Object> map = (Map) object;
            dto.setName(getTicketType(map.get("TICKETFAMILY")));
            List<Long> values = new ArrayList<>();
            if(index == 1){
                //进站
                values.add(Long.valueOf(map.get("TOTAL_IN")+""));
            }else if(index == 2){
                //出站
                values.add(Long.valueOf(map.get("TOTAL_OUT")+""));
            }else if(index == 3){
                //购票
                values.add(Long.valueOf(map.get("SALE_COUNT")+""));
            }else if(index == 4){
                //充值
                values.add(Long.valueOf(map.get("ADD_COUNT")+""));
            }else {
                dto.setName("未知");
                dto.setValues(null);
            }


            dto.setValues(values);
        }
        return dto;
    }

    private String getTicketType(Object key){
        if(key==null){
            return  "--";
        }
        String name = CDTicketFamily.getInstance().getNameByValue(key);
        if(name==null){
            return "未知票种";
        }
        return name;

    }

    private void printMap(Map<String,Object> line) {
        for (Map.Entry<String, Object> entry : line.entrySet()) {
            String key = entry.getKey();
            LOGGER.debug(key + "=========" + entry.getValue());
        }
    }

    private List<TimeShareVO> getTimeShareInfo(PageList pageList){
        List list = pageList.getList();
        List<TimeShareVO> data = new ArrayList<>();

        for (Object object:list){
            TimeShareVO vo = new TimeShareVO();
            Map<String,Object> map = (Map)object;
            printMap(map);

            vo.setTime(map.get("TIME")+"");
            vo.setTotalIn(map.get("TOTAL_IN")+"");
            vo.setTotalOut(map.get("TOTAL_OUT")+"");
            vo.setTotalBuy(map.get("SALE_COUNT")+"");
            vo.setTotalAdd(map.get("ADD_COUNT")+"");


            data.add(vo);
        }
        return data;
    }
}