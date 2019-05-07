package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.SystemConfigKey;
import com.insigma.afc.monitor.constant.dic.AFCTicketFamily;
import com.insigma.afc.monitor.dao.PassengerDao;
import com.insigma.afc.monitor.dao.TsyConfigDao;
import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesData;
import com.insigma.afc.monitor.model.dto.condition.BarAndPieCondition;
import com.insigma.afc.monitor.model.dto.condition.PassengerCondition;
import com.insigma.afc.monitor.model.dto.condition.SeriesCondition;
import com.insigma.afc.monitor.model.entity.TmoOdFlowStats;
import com.insigma.afc.monitor.model.vo.ODSearchResultItem;
import com.insigma.afc.monitor.service.PassengerFlowService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.persistence.criteria.Predicate;
import java.util.*;


/**
 * Ticket:客流监控查询实现方法
 *
 * @author xingshaoya
 * create time: 2019-03-22 13:33
 */
@Service
public class PassengerFlowServiceImpl implements PassengerFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerFlowServiceImpl.class);

    private PassengerDao passengerDao;
    private TopologyService topologyService;

    @Autowired
    public PassengerFlowServiceImpl(TsyConfigDao tsyConfigDao){
        tsyConfigDao.findById(SystemConfigKey.OD_INTERVAL).ifPresent(t->timePeriod=Integer.valueOf(t.getConfigValue()));
    }

    /**
     * 时间间隔
     */
    private int timePeriod = 5;

    @Override
    public List<BarPieChartDTO> getBarPieChart(BarAndPieCondition condition) {

        List<Integer> timeIntervals = getTimeInterval(condition.getTime());
        List<Tuple> bars = passengerDao.findAllBarAndPie(condition.getDate(), timeIntervals.get(0),
                timeIntervals.get(1),condition.getStationIds(), condition.getTicketFamily());

        //柱状数据
        List<BarPieChartDTO> barPieChartDTOS = new ArrayList<>();
        for (Tuple t : bars) {
            //车站id
            Integer stationId = t.get("stationId",Integer.class);
            //进站
            long odin = t.get("totalIn",Long.class);
            //出站
            long odout = t.get("totalOut",Long.class);
            //购票
            long odbuy = t.get("saleCount",Long.class);
            //充值
            long odadd = t.get("addCount",Long.class);
            //合计
            long total = odin+odout+odbuy+odadd;
            List<Long> valueOfRows = Arrays.asList(odin,odout,odbuy,odadd,total);
            //车站名称
            String culumnName = topologyService.getStationNode(stationId).getData().getStationName();
            barPieChartDTOS.add(new BarPieChartDTO(culumnName, valueOfRows));
        }
        return barPieChartDTOS;
    }

    @Override
    public List<SeriesChartDTO> getSeriesChart(SeriesCondition condition) {
        List<Integer> timeIntervals = getTimeInterval(condition.getTime());
        List<Integer> stationIds = condition.getStationIds();
        Date date = condition.getDate();
        List<Tuple> tmoOdFlowStats = passengerDao.findAllSeries(condition.getDate(), timeIntervals.get(0),
                timeIntervals.get(1),condition.getStationIds(), condition.getTicketFamily());

        Map<Integer, List<Tuple>> sortData = sortData(tmoOdFlowStats, stationIds);
        // 每个曲线上的点包含timeIntervalId的个数inclueTimeIdCount(默认值为1)
        int inclueTimeIdCount = 1;
        if (condition.getIntervalCount() > 1) {
            inclueTimeIdCount = condition.getIntervalCount();
        }
        Calendar cal = Calendar.getInstance();
        List<SeriesChartDTO> seriersItems = new ArrayList<>();
        // 生成各条曲线数据
        for(Map.Entry<Integer,List<Tuple>> entry:sortData.entrySet()){
            List<Tuple> dataList = entry.getValue();
            if (dataList.isEmpty()){
                continue;
            }
            long pointodin = 0;
            long pointodout = 0;
            long pointodbuy = 0;
            long pointodadd = 0;

            // 每个点包含的各项值
            int count = 0;
            Integer timeIntervalId = null;
            // 曲线上数据点
            List<SeriesData> points = new ArrayList<>();
            for (Tuple t : dataList) {
                timeIntervalId = t.get("timeIntervalId",Integer.class);
                pointodin += t.get("totalIn",Long.class);
                pointodout += t.get("totalOut",Long.class);
                pointodbuy += t.get("saleCount",Long.class);
                pointodadd += t.get("addCount",Long.class);
                count++;
                // 客流值点加入到曲线
                if (count >= inclueTimeIdCount) {
                    cal.setTime(date);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.add(Calendar.MINUTE, timeIntervalId * timePeriod);

                    points.add(new SeriesData(cal.getTime(),
                            Arrays.asList(pointodin, pointodout, pointodbuy, pointodadd)));

                    pointodin = 0;
                    pointodout = 0;
                    pointodbuy = 0;
                    pointodadd = 0;
                    count = 0;
                }
            }
            // 判断是否还有剩余的最后数据点没加到曲线上，有的话就加上
            if (count > 0) {
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.add(Calendar.MINUTE, timeIntervalId * timePeriod);

                points.add(new SeriesData(cal.getTime(), Arrays.asList(pointodin, pointodout, pointodbuy, pointodadd)));
            }

            String seriesName = topologyService.getStationNode(entry.getKey()).getData().getStationName();
            seriersItems.add(new SeriesChartDTO(seriesName, points));
        }
        return seriersItems;
    }

    @Override
    public Page<ODSearchResultItem> getODSerchResult(PassengerCondition condition) {

        Date date = condition.getDate();
        List<Integer> timeIntervals = getTimeInterval(condition.getTime());
        List<Integer> stationIds = condition.getStationIds();
        Short statType = condition.getStatType();

        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();
        Map<Object, String> ticketFamily = AFCTicketFamily.getInstance().getCodeMap();
        return passengerDao.findAll(date, timeIntervals.get(0),timeIntervals.get(1),
                stationIds,statType,PageRequest.of(page,pageSize)).map(tuple -> {
            ODSearchResultItem t = new ODSearchResultItem();
            t.setOdIn(tuple.get("totalIn",Long.class));
            t.setOdOut(tuple.get("totalOut",Long.class));
            t.setOdBuy(tuple.get("saleCount",Long.class));
            t.setOdAdd(tuple.get("addCount",Long.class));
            t.setStationId(tuple.get("stationId",Integer.class));
            t.setStationName(topologyService.getNodeText(t.getStationId().longValue()).getData());
            if (statType==1){
                t.setTicketFamily("全部票种/无");
            }else if(statType==0){
                Integer ticketFamilyType = tuple.get("ticketFamily",Short.class).intValue();
                if (ticketFamily == null || ticketFamily.get(ticketFamilyType) == null){
                    t.setTicketFamily("票种未知/" + ticketFamilyType);
                }else {
                    t.setTicketFamily(ticketFamily.get(ticketFamilyType) + "/" + ticketFamilyType);
                }
            }
            return t;
        });
    }

    /**
     * 梳理信息
     *
     * @param data       查找的数据
     * @param stationIds 站点集合
     * @return 有序数据
     */
    private Map<Integer, List<Tuple>> sortData(List<Tuple> data, List<Integer> stationIds) {
        Map<Integer, List<Tuple>> dataMap = new HashMap<>(stationIds.size());
        stationIds.forEach(id->dataMap.put(id,new ArrayList<>()));
        // 将数据按照各自的车站分配到数组中
        for (Tuple odData : data) {
            Integer stationId = odData.get("stationId",Integer.class);
            if (dataMap.containsKey(stationId)){
                dataMap.get(stationId).add(odData);
            }
        }
        return dataMap;
    }

    /**
     * 获取时间段
     *
     * @param time 时间
     * @return 时间段
     */
    private List<Integer> getTimeInterval(String time) {
        List<Integer> timeIntervalIds = new ArrayList<>();
        String[] temp = time.split("-");

        Date date1 = DateTimeUtil.parseStringToDate(temp[0], "HH:mm");
        Date date2 = DateTimeUtil.parseStringToDate(temp[1], "HH:mm");

        if (date1.after(date2)){
            throw new IllegalArgumentException();
        }

        int period1 = DateTimeUtil.convertTimeToIndex(date1, timePeriod);
        int period2 = DateTimeUtil.convertTimeToIndex(date2, timePeriod);

        //[start,end)
        int minute = Integer.parseInt(temp[1].substring(3, 5));
        if (minute%timePeriod==0&&period1<period2){
            period2 = period2-1;
        }

        timeIntervalIds.add(period1);
        timeIntervalIds.add(period2);

        return timeIntervalIds;
    }

    @Autowired
    public void setPassengerDao(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }
}
