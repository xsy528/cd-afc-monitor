package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.BarAndSeriesCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.entity.PassengerData;
import com.insigma.afc.monitor.model.vo.BarChartData;
import com.insigma.afc.monitor.model.vo.PieChartData;
import com.insigma.afc.monitor.model.vo.SeriesChartData;
import org.springframework.data.domain.Page;


/**
 * Ticket:
 *
 * @author: xingshaoya
 * create time: 2019-03-22 10:19
 */
public interface PassengerFlowService {

    /**
     * 获得柱状图
     * @param condition
     * @return
     */
    BarChartData getBarChart(BarAndSeriesCondition condition);

    /**
     * 获得饼图
     * @param condition 查询实体类
     * @return 饼图数据
     */
    PieChartData getPieChat(BarAndSeriesCondition condition);
    /**
     * 获取曲线数据
     * @return 曲线数据
     */
    SeriesChartData getSeriesChart(SeriesCondition condition);

    Page<PassengerData> getODSerchResult(PassengerCondition condition);

}
