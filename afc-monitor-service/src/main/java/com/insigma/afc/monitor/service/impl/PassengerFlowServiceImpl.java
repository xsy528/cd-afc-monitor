package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.PassengerDao;
import com.insigma.afc.monitor.model.dto.condition.BarAndSeriesCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.entity.PassengerData;
import com.insigma.afc.monitor.model.vo.BarChartData;
import com.insigma.afc.monitor.model.vo.PieChartData;
import com.insigma.afc.monitor.model.vo.SeriesChartData;
import com.insigma.afc.monitor.service.PassengerFlowService;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Ticket:客流监控查询实现方法
 *
 * @author: xingshaoya
 * create time: 2019-03-22 13:33
 */
@Service
public class PassengerFlowServiceImpl implements PassengerFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerFlowServiceImpl.class);

    @Autowired
    private PassengerDao passengerDao;

    @Override
    public BarChartData getBarChart(BarAndSeriesCondition condition) {
        List<Integer> stationIds = condition.getStationId();
        Date date = condition.getDate();
        int startTimeIndex = condition.getStartTimeIndex();
        int endTimeIndex = condition.getEndTimeIndex();
        Short ticketFamily = condition.getTicketFamily();
        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();

        Page<Object[]> bars = passengerDao.findAllBarByConditon(date, startTimeIndex,
                endTimeIndex, stationIds,ticketFamily, PageRequest.of(page,pageSize));
        String[] partNames = condition.getPartNames();
        if (partNames == null || partNames.length <= 0) {
            return null;
        }
        BarChartData chartData = new BarChartData();
        List<String> names = new ArrayList<String>();
        for (String name : partNames) {
            names.add(name);
        }
        chartData.setRowNames(names);

        Map<Integer, String> stationNameMap = condition.getStationNameMap();
        List<BarChartData.ColumnData> columnItems = new ArrayList<BarChartData.ColumnData>();
        int columnIndex = 0;
        for (Object[] objects : bars) {
            // 车站名、进站、出站、购票、充值
            int stationId = (Integer) objects[0];
            long odin = (Long) objects[1];
            long odout = (Long) objects[2];
            long odbuy = (Long) objects[3];
            long odadd = (Long) objects[4];
            long total = odin + odout + odbuy + odadd;
            List<Number> valueOfRows = new ArrayList<Number>();
            for (int j = 0; j < names.size(); j++) {
                if (j >= 4) {
                    valueOfRows.add(total);
                } else {
                    valueOfRows.add((Long) objects[j + 1]);
                }
            }
            String culumnName = stationNameMap.get(stationId);
            if (culumnName.equals("车站名未知")) {
                culumnName = culumnName + columnIndex;
                columnIndex++;
            }
            BarChartData.ColumnData d = new BarChartData.ColumnData(culumnName, valueOfRows);
            columnItems.add(d);
        }
        chartData.setColumnItems(columnItems);

        return chartData;

    }

    @Override
    public PieChartData getPieChat(BarAndSeriesCondition condition) {
        return null;
    }

    @Override
    public SeriesChartData getSeriesChart(SeriesCondition condition) {
        return null;
    }

    @Override
    public Page<PassengerData> getODSerchResult(PassengerCondition condition) {
        return null;
    }
}
