package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesChartDTO;
import com.insigma.afc.monitor.model.dto.condition.BarAndPieCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * Ticket:客流监控服务
 *
 * @author xingshaoya
 * create time: 2019-03-22 10:19
 */
public interface PassengerFlowService {

    /**
     * 获得柱状图、饼图数据
     * @param condition 查询条件
     * @return 数据
     */
    List<BarPieChartDTO> getBarPieChart(BarAndPieCondition condition);

    /**
     * 获取曲线图数据
     * @param condition 查询条件
     * @return 数据
     */
    List<SeriesChartDTO> getSeriesChart(SeriesCondition condition);

    /**
     * 客流查询
     * @param condition 查询条件
     * @return 分页数据
     */
    Page<ODSearchResultItem> getODSerchResult(PassengerCondition condition);

}
