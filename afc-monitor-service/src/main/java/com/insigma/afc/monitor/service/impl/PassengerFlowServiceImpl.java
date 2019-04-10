package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCTicketFamily;
import com.insigma.afc.monitor.dao.PassengerDao;
import com.insigma.afc.monitor.model.dto.condition.BarAndSeriesCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.entity.TmoOdFlowStats;
import com.insigma.afc.monitor.model.vo.BarChartData;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import com.insigma.afc.monitor.model.vo.PieChartData;
import com.insigma.afc.monitor.model.vo.SeriesChartData;
import com.insigma.afc.monitor.service.PassengerFlowService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;


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

    /*时间间隔*/
    private int timePeriod = 5;
    /**
     * 最大时间段跨度
     */
    private int maxPeriods = 3;

    private TopologyService topologyService;

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    @Override
    public BarChartData getBarChart(BarAndSeriesCondition condition) {

        Page<Object[]> bars = getModel(condition);

        LOGGER.debug(bars.toString());
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

        List<BarChartData.ColumnData> columnItems = new ArrayList<BarChartData.ColumnData>();
        int columnIndex = 0;
        for (Object[] objects : bars) {
            // 车站名、进站、出站、购票、充值
            BigDecimal stationId = (BigDecimal) objects[0];
            BigDecimal odin = (BigDecimal) objects[1];
            BigDecimal odout = (BigDecimal) objects[2];
            BigDecimal odbuy = (BigDecimal) objects[3];
            BigDecimal odadd = (BigDecimal) objects[4];
            BigDecimal total = odin.add(odout).add(odbuy).add(odadd);
            List<Number> valueOfRows = new ArrayList<Number>();
            for (int j = 0; j < names.size(); j++) {
                if (j >= 4) {
                    valueOfRows.add(total);
                } else {
                    valueOfRows.add((BigDecimal) objects[j + 1]);
                }
            }
           // LOGGER.debug(stationId.toString());
            //LOGGER.debug(stationNameMap.toString());
            String culumnName = topologyService.getNodeText(stationId.longValue()).getData();

            if (culumnName.equals("车站名未知")) {
                culumnName = culumnName + columnIndex;
                columnIndex++;
            }
            BarChartData.ColumnData d = new BarChartData.ColumnData(culumnName, valueOfRows);

            LOGGER.debug(d.toString());
            columnItems.add(d);
        }
        chartData.setColumnItems(columnItems);

        return chartData;

    }

    @Override
    public PieChartData getPieChat(BarAndSeriesCondition condition) {
        Page<Object[]> model = getModel(condition);
        PieChartData chartData = new PieChartData();

        String[] partNames = condition.getPartNames();
        if (partNames == null || partNames.length <= 0) {
            return null;
        }
        List<String> names = new ArrayList<String>();
        for (String name : partNames) {
            names.add(name);
        }
        chartData.setPartNames(names);

        List<PieChartData.PieData> pieItems = new ArrayList<>();
        // double[] v = new double[names.size()];
        int pieIndex = 0;
        for (Object[] objects : model) {
            BigDecimal stationId = (BigDecimal) objects[0];
            BigDecimal odin = (BigDecimal) objects[1];
            BigDecimal odout = (BigDecimal) objects[2];
            BigDecimal odbuy = (BigDecimal) objects[3];
            BigDecimal odadd = (BigDecimal) objects[4];
            BigDecimal total = odin.add(odout).add(odbuy).add(odadd);
            List<Double> valueOfPies = new ArrayList<Double>();
            for (int j = 0; j < names.size(); j++) {
                double value = 0.0;
                if (j >= 4) {
                    value = total.doubleValue();
                } else {
                    value = ((BigDecimal) objects[j + 1]).doubleValue();
                }
                valueOfPies.add(value);
            }
            String pieName = topologyService.getNodeText(stationId.longValue()).getData();
            if (pieName.equals("车站名未知")) {
                pieName = pieName + pieIndex;
                pieIndex++;
            }
            PieChartData.PieData d = new PieChartData.PieData(pieName, valueOfPies);
            pieItems.add(d);
        }
        chartData.setPieItems(pieItems);
        return chartData;
    }

    @Override
    public SeriesChartData getSeriesChart(SeriesCondition condition) {
        Date date1 = condition.getDate();
        List<Long> timeInterval = getTimeInterval(condition.getTime());
        List<Long> stationId1 = condition.getStationId();
        Short ticketFamily = condition.getTicketFamily();
        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();
        Page<Object[]> bars;
        if(ticketFamily!=null){
        bars = passengerDao.findAllSeriesBySeriesCondition(date1, timeInterval,
                stationId1, ticketFamily, PageRequest.of(page, pageSize));
        }else{
        bars = passengerDao.findAllSeriesBySeriesCondition2(date1, timeInterval,
                    stationId1, PageRequest.of(page, pageSize));
        }
        LOGGER.debug("-----------------------------");
        String[] partNames = condition.getPartNames();
        if (partNames == null || partNames.length <= 0) {
            return null;
        }
        SeriesChartData chartData = new SeriesChartData();

        List<String> names = new ArrayList<String>();
        for (String name : partNames) {
            names.add(name);
        }
        chartData.setPartNames(names);
        Calendar cal = Calendar.getInstance();

        Map<String, List<Object[]>> sortData = sortData(bars, stationId1);
        // 数据库中一个timeIntervalId数据点包含的分钟数，比如timePeriod=5，则表明timeIntervalId包含5分钟的客流数据。默认值为5
        // 通过该值可以获取对于的时间点
        int timePeriod = 5;
        if (timeInterval.get(0) > 1) {
            timePeriod = timeInterval.get(0).intValue();
        }
        // 每个曲线上的点包含timeIntervalId的个数inclueTimeIdCount(默认值为1)
        int inclueTimeIdCount = 1;
        if (condition.getIntervalCount() > 1) {
            inclueTimeIdCount = condition.getIntervalCount();
        }
        chartData.setIntervalCount(condition.getIntervalCount());

        chartData.setTicketType(ticketFamily +0);

        Map<String, List<SeriesChartData.SeriesData>> seriersItems = new HashMap<>();
        int stationIndex = 0;
        // 生成各条曲线数据
        for (String stationId : sortData.keySet()) {
            List<Object[]> dataList = sortData.get(stationId);
            if (dataList.isEmpty()) {
                continue;
            }
            long pointodin = 0;
            long pointodout = 0;
            long pointodbuy = 0;
            long pointodadd = 0;
            long pointtotal = 0;
            // 每个点包含的各项值
            int count = 0;
            Long timeIntervalId = null;
            // 曲线上数据点
            List<SeriesChartData.SeriesData> valueOfSeries = new ArrayList<>();
            for (Object[] objects : dataList) {
                // int stationId = (Integer) objects[0];
                timeIntervalId = (Long) objects[1];
                Long odin = (Long) objects[2];
                Long odout = (Long) objects[3];
                Long odbuy = (Long) objects[4];
                Long odadd = (Long) objects[5];
                Long total = odin+odout+odbuy+odadd;

                pointodin = odin.longValue();
                pointodout += odout.longValue();
                pointodbuy += odbuy.longValue();
                pointodadd += odadd.longValue();
                pointtotal += total.longValue();

                count++;
                // 客流值点加入到曲线
                if (count >= inclueTimeIdCount) {
                    Date date = date1;
                    cal.setTime(date);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.add(Calendar.MINUTE, timeIntervalId.intValue() * timePeriod);

                    SeriesChartData.SeriesData seriesData = new SeriesChartData.SeriesData();
                    seriesData.setDate(cal.getTime());
                    seriesData.setValueOfPart(new long[] { pointodin, pointodout, pointodbuy, pointodadd });
                    valueOfSeries.add(seriesData);

                    pointodin = 0;
                    pointodout = 0;
                    pointodbuy = 0;
                    pointodadd = 0;
                    pointtotal = 0;

                    count = 0;
                }
            }
            // 判断是否还有剩余的最后数据点没加到曲线上，有的话就加上
            if (count > 0) {
                Date date = date1;
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.add(Calendar.MINUTE, timeIntervalId.intValue() * timePeriod);

                SeriesChartData.SeriesData seriesData = new SeriesChartData.SeriesData();
                seriesData.setDate(cal.getTime());
                seriesData.setValueOfPart(new long[] { pointodin, pointodout, pointodbuy, pointodadd });
                valueOfSeries.add(seriesData);
            }

            String seriesName = topologyService.getNodeText(Long.parseLong(stationId)).getData();
            if (seriesName.equals("车站名未知")) {
                seriesName = seriesName + stationIndex;
            }
            seriersItems.put(seriesName, valueOfSeries);
            stationIndex++;
        }

        chartData.setSeriersItems(seriersItems);
        return chartData;
    }

    @Override
    public List<ODSearchResultItem> getODSerchResult(PassengerCondition condition) {

        List<TmoOdFlowStats> counts = passengerDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("gatheringDate"),condition.getDate()));
            predicates.add(root.get("timeIntervalId").in(getTimeInterval(condition.getTime())));
            predicates.add(root.get("stationId").in(condition.getStationId()));
            query.orderBy(builder.desc(root.get("stationId")));
            return builder.and(predicates.toArray(new Predicate[0]));
        });

        Long totalSize = counts==null?0L:counts.size();


        Date date = condition.getDate();
        List<Long> stationId = condition.getStationId();
        List<Long> timeInterval = getTimeInterval(condition.getTime());

        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();
        Page<Object[]> all;
        Page<Object[]> totalDataList;
        if(condition.getStatType() != 0) {
            all = passengerDao.findAllByCondition(date, timeInterval,stationId,
                    PageRequest.of(page, pageSize));

            totalDataList = passengerDao.findAllTotalOD(
                    date,timeInterval, PageRequest.of(page, pageSize)
            );
        }else {
            all = passengerDao.findAllByCondition2(date, timeInterval,stationId,
                    PageRequest.of(page, pageSize));

            totalDataList = passengerDao.findAllTotalOD2(
                    date,timeInterval, PageRequest.of(page, pageSize)
            );
        }
        List<ODSearchResultItem> formData = new ArrayList<ODSearchResultItem>();

            // 总客流

            if (totalDataList != null && totalDataList.getTotalPages() > 0) {
                for (Object[] totalData : totalDataList) {
                    ODSearchResultItem t = new ODSearchResultItem();

                    t.setStationName(totalData[0] + "号线总客流");

                    Map<Object, String> ticketFamily = AFCTicketFamily.getInstance().getCodeMap();

                    if (condition.getStatType() == 1) {
                        t.setTicketFamily(totalData[1].toString() + "/无");
                    } else if (ticketFamily == null
                            || ticketFamily.get(((BigDecimal) totalData[1]).intValue()) == null) {
                        t.setTicketFamily("票种未知/" + totalData[1]);
                    } else {
                        t.setTicketFamily(
                                ticketFamily.get(((BigDecimal) totalData[1]).intValue()) + "/" + totalData[1]);
                    }

                    t.setOdIn((String) totalData[2]);
                    t.setOdOut((String) totalData[3]);
                    t.setOdBuy((String) totalData[4]);
                    t.setOdAdd((String) totalData[5]);
                    formData.add(t);
                }
            }
        int no = page * pageSize + 1;
        for (Object[] d : all) {
            ODSearchResultItem t = new ODSearchResultItem();

           // t.setId(no++);
            t.setStationName(topologyService.getNodeText(((BigDecimal) d[0]).longValue()).getData());
            Map<Object, String> ticketFamily = AFCTicketFamily.getInstance().getCodeMap();

            if (condition.getStatType() == 1) {
                t.setTicketFamily(d[1].toString() + "/无");
            } else if (ticketFamily == null || ticketFamily.get(((BigDecimal) d[1]).intValue()) == null) {
                t.setTicketFamily("票种未知/" + d[1]);
            } else {
                t.setTicketFamily(ticketFamily.get(((BigDecimal) d[1]).intValue()) + "/" + d[1]);
            }

            t.setOdIn(d[2].toString());
            t.setOdOut(d[3].toString());
            t.setOdBuy(d[4].toString());
            t.setOdAdd(d[5].toString());

            t.setStationId(d[0].toString());
            formData.add(t);
        }
            return  formData;
    }

    /**
     * 获得数据库数据
     * @param condition 查询条件
     * @return
     */
    public Page<Object[]> getModel(BarAndSeriesCondition condition){
        List<Long> stationIds = condition.getStationId();
        Date date = condition.getDate();
        Short ticketFamily = condition.getTicketFamily();
        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();
        List<Long> timeInterval = getTimeInterval(condition.getTime());
        Page<Object[]> bars;
        if(ticketFamily!=null) {
             bars = passengerDao.findAllBarAndPieByConditon(date, timeInterval, stationIds, ticketFamily, PageRequest.of(page, pageSize));
        }else{
            bars = passengerDao.findAllBarAndPieByConditon2(date, timeInterval, stationIds, PageRequest.of(page, pageSize));
        }
        return bars;
    }

    /**
     * 梳理信息
     * @param data  查找的数据
     * @param stationIds 站点集合
     * @return
     */
    private Map<String, List<Object[]>> sortData(Page<Object[]> data, List<Long> stationIds) {
        Map<String, List<Object[]>> dataMap = new HashMap<>(stationIds.size());
        for (int j = 0; j < stationIds.size(); j++) {
            dataMap.put(stationIds.get(j) + "", new ArrayList<Object[]>());
        }
        // 将数据按照各自的车站分配到数组中
        for (Object[] odData : data) {
            long id = (Long) odData[0];
            if (dataMap.containsKey("" + id)) {
                dataMap.get("" + id).add(odData);
            }
        }
        return dataMap;
    }

    /**
     * 获取时间ID
     * @param time
     * @return
     */
    public List<Long> getTimeInterval(String time){

            List<Long> timeIntervalIds = new ArrayList<>();
            String[] temp = time.split("-");

            Date date1 = DateTimeUtil.parseStringToDate(temp[0], "HH:mm");
            Date date2 = DateTimeUtil.parseStringToDate(temp[1], "HH:mm");
            LOGGER.debug(date1.toString()+"XXX"+date2.toString());
            int period1 = DateTimeUtil.convertTimeToIndex(date1, timePeriod);
            int period2 = DateTimeUtil.convertTimeToIndex(date2, timePeriod);
            if (date2.getDate() - date1.getDate() == 1) {
                period2 = 289;
            }
            int len = period2-period1;
//            if (len>=maxPeriods){
//                throw new IllegalArgumentException();
//            }
            LOGGER.debug(period2+"="+period1);
            for (int i = 0; i < len; i++) {
                timeIntervalIds.add((long)(period1 + i));
            }
            //生成时间戳数组 ，in
        LOGGER.debug(timeIntervalIds.get(0).toString());
        return  timeIntervalIds;
    }
//    public String[] getPartNames(List<Integer> list){
//        List<String> partNames = new ArrayList<>();
//        String[] legend = BarAndSeriesCondition.getLEGEND();
//        for (int i = 0;i<list.size();i++) {
//            if (list.get(i)!=0){
//                partNames.add(legend[i]);
//            }
//        }
//        return partNames.toArray(new String[partNames.size()]);
//    }
}
