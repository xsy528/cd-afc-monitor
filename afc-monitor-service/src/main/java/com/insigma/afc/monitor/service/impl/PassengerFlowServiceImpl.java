package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.SystemConfigKey;
import com.insigma.afc.monitor.constant.dic.AFCTicketFamily;
import com.insigma.afc.monitor.dao.PassengerDao;
import com.insigma.afc.monitor.dao.TmetroLineDao;
import com.insigma.afc.monitor.dao.TsyConfigDao;
import com.insigma.afc.monitor.dao.util.JdbcTemplateDao;
import com.insigma.afc.monitor.dao.util.PageList;
import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesChartDTO;
import com.insigma.afc.monitor.model.dto.SeriesData;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.entity.TmetroLine;
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
import java.sql.Time;
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
    private TmetroLineDao lineDao;
    private JdbcTemplateDao jdbcDao;

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
                timeIntervals.get(1),condition.getLines(), condition.getTicketFamily());

        //柱状数据
        List<BarPieChartDTO> barPieChartDTOS = new ArrayList<>();
        for (Tuple t : bars) {
            //车站id
            Short lineId = t.get("lineId",Short.class);
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
            try {
                String columnName = getLineNameById(lineId);
                barPieChartDTOS.add(new BarPieChartDTO(columnName, valueOfRows));
            }catch (Exception e){
                LOGGER.error("获取车站节点失败:{}",e.getMessage());
            }
        }
        return barPieChartDTOS;
    }

    @Override
    public List<SeriesChartDTO> getSeriesChart(SeriesCondition condition) {
        List<Integer> timeIntervals = getTimeInterval(condition.getTime());
        List<Short> lines = condition.getLines();
        Date date = condition.getDate();
        List<Tuple> tmoOdFlowStats = passengerDao.findAllSeries(condition.getDate(), timeIntervals.get(0),
                timeIntervals.get(1),lines, condition.getTicketFamily());

        Map<Short, List<Tuple>> sortData = sortData(tmoOdFlowStats, lines);
        // 每个曲线上的点包含timeIntervalId的个数inclueTimeIdCount(默认值为1)
        int inclueTimeIdCount = 1;
        if (condition.getIntervalCount() > 1) {
            inclueTimeIdCount = condition.getIntervalCount();
        }
        Calendar cal = Calendar.getInstance();
        List<SeriesChartDTO> seriersItems = new ArrayList<>();
        // 生成各条曲线数据
        for(Map.Entry<Short,List<Tuple>> entry:sortData.entrySet()){
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
            try {
                String seriesName = getLineNameById(entry.getKey());
                seriersItems.add(new SeriesChartDTO(seriesName, points));
            }catch (Exception e){
                LOGGER.error("获取车站节点失败:{}",e.getMessage());
            }
        }
        return seriersItems;
    }

    @Override
    public Page<ODSearchResultItem> getODSerchResult(PassengerCondition condition) {

        Date date = condition.getDate();
        List<Integer> timeIntervals = getTimeInterval(condition.getTime());
        List<Integer> stations = condition.getStations();
        Short statType = condition.getStatType();

        Integer page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();
        int index = page*pageSize+1;
        Map<Object, String> ticketFamilyMap = AFCTicketFamily.getInstance().getCodeMap();
        return passengerDao.findAll(date, timeIntervals.get(0),timeIntervals.get(1),
                stations,statType,PageRequest.of(page,pageSize)).map(tuple -> {
            ODSearchResultItem t = new ODSearchResultItem();
            Long totalIn = tuple.get("totalIn", Long.class);
            t.setOdIn(totalIn);
            Long totalOut = tuple.get("totalOut", Long.class);
            t.setOdOut(totalOut);
            Long saleCount = tuple.get("saleCount", Long.class);
            t.setOdBuy(saleCount);
            Long addCount = tuple.get("addCount", Long.class);
            t.setOdAdd(addCount);
            Long totalCount = totalIn+totalOut+addCount+saleCount;
            t.setTotalCount(totalCount);
            t.setStationId(tuple.get("stationId",Integer.class));
            t.setStationName(topologyService.getNodeText(t.getStationId().longValue()).getData());
            if (statType==1){
                t.setTicketFamily("全部票种/无");
            }else if(statType==0){
                Integer ticketFamilyType = tuple.get("ticketFamily",Short.class).intValue();
                String ticketFamilyName = ticketFamilyMap.get(ticketFamilyType);
                if (ticketFamilyName == null){
                    t.setTicketFamily("票种未知/" + formatTicketFamilyType(ticketFamilyType));
                }else {
                    t.setTicketFamily(ticketFamilyName + "/" + formatTicketFamilyType(ticketFamilyType));
                }
            }
            return t;
        });
    }

    @Override
    public PageList getShareODSerchResult(TimeShareCondition condition) {
        Integer intervalCount = condition.getIntervalCount();
        Integer nodeId = condition.getNodeId();
        Short ticketType = condition.getTicketType();
        Date date = condition.getDate();
        String time = condition.getTime();
        List<Integer> timeInterval = getTimeInterval(time);
        Integer startTimeIndex = timeInterval.get(0);
        Integer endTimeIndex = timeInterval.get(1);

        StringBuilder sql = new StringBuilder("select ");
        StringBuilder timeSql = new StringBuilder("TO_CHAR(to_date(TO_CHAR(t.REF_DATE_TIME, 'yyyy-mm-dd hh24') || ':' || (to_number(TO_CHAR(t.REF_DATE_TIME, 'mi')) - mod(to_number(TO_CHAR(t.REF_DATE_TIME, 'mi')), " +
                + intervalCount + ")) || ':' || '00','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')");

        sql.append(timeSql);
        sql.append("  AS TIME, SUM(t.TOTAL_IN) AS TOTAL_IN, SUM(t.TOTAL_OUT) AS TOTAL_OUT, SUM(t.SALE_COUNT) AS SALE_COUNT, SUM(t.ADD_COUNT) AS ADD_COUNT " +
                " FROM TMO_OD_FLOW_STATS t WHERE 1=1");

        if (nodeId != null && nodeId != 4) {
            sql.append(" and t.station_id = " + nodeId);
        }
        if (ticketType != null) {
            sql.append(" and t.ticket_family = " + ticketType);
        }
        if (date != null) {
            sql.append(" and t.gathering_date = TO_DATE('"+new java.sql.Date(date.getTime())+"','YY-MM-DD')");
        }
        if (startTimeIndex != null) {
            sql.append(" and t.time_interval_id >= " + startTimeIndex);
        }
        if (endTimeIndex != null) {
            sql.append(" and t.time_interval_id <= " + endTimeIndex);
        }

        sql.append(" group by ");
        sql.append(timeSql);

        return jdbcDao.queryByPageForOracle(sql.toString(), null, condition.getPageNumber(), condition.getPageSize(), null);
    }

    @Override
    public PageList getTicketCompareResult(TicketCompareCondition condition) {

        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        List<Integer> stations = condition.getStations();
        List<Integer> transType = condition.getTransType();
        Integer timeInterval = 5;
        int beginInterval = DateTimeUtil.convertTimeToIndex(startTime, timeInterval);
        int endInterval = DateTimeUtil.convertTimeToIndex(endTime, timeInterval);
        boolean sameDate = sameDate(startTime, endTime);

        StringBuilder sql = new StringBuilder("select t.ticket_family AS TICKETFAMILY ,sum(t.total_in) AS TOTAL_IN ,sum(t.total_out) " +
                "AS TOTAL_OUT ,sum(t.sale_count) AS SALE_COUNT ,sum(t.add_count) AS ADD_COUNT  from tmo_od_flow_stats t where 1= 1 ");

        String startDate = DateTimeUtil.formatDateToString(startTime, "yyyy-MM-dd");
        String endDate = DateTimeUtil.formatDateToString(endTime, "yyyy-MM-dd");
        if (sameDate) {
            sql.append(" and (t.gathering_date =TO_DATE('"+ startDate +"','YY-MM-DD')" );
            sql.append( " and t.time_interval_id >=" +beginInterval );
            sql.append(" and t.time_interval_id< "+endInterval+") ");
        } else {
            sql.append(" and ((t.gathering_date = TO_DATE('"+ startDate +"','YY-MM-DD')"+
                    " and t.time_interval_id >="+beginInterval);
            sql.append(") or (t.gathering_date>TO_DATE('"+ startDate +"','YY-MM-DD')"+
                    " and t.gathering_date<TO_DATE('"+ endDate +"','YY-MM-DD')");
            sql.append(") or (t.gathering_date =TO_DATE('"+ endDate +"','YY-MM-DD')"+
                    " and t.time_interval_id<"+endInterval+" ))");
        }
        sql.append(" and t.station_id in("+listToString(stations.toArray())+") ");
        sql.append(" group by t.ticket_family ");

        return jdbcDao.queryByPageForOracle(sql.toString(), null, condition.getPageNumber(), condition.getPageSize(), null);
    }

    private String listToString(Object[] objects){
        StringBuilder builder = new StringBuilder();
        for (Object object:objects){
            builder.append(object+",");
        }
        builder.append("-1");
        return builder.toString();
    }
    private boolean sameDate(Date beforeDate, Date endDate) {
        String beforeDateString = DateTimeUtil.formatDateToString(beforeDate, "yyyy-MM-dd");
        String endDateString = DateTimeUtil.formatDateToString(endDate, "yyyy-MM-dd");
        if (beforeDateString.equals(endDateString)) {
            return true;
        }
        return false;
    }

    /**
     * 格式化票种编号
     * @param ticketFamilyType 票种编号
     * @return 名称
     */
    private String formatTicketFamilyType(Integer ticketFamilyType){
        return String.format("%02x",ticketFamilyType).toUpperCase();
    }

    /**
     * 梳理信息
     *
     * @param data       查找的数据
     * @param lines 线路集合
     * @return 有序数据
     */
    private Map<Short, List<Tuple>> sortData(List<Tuple> data, List<Short> lines) {
        Map<Short, List<Tuple>> dataMap = new HashMap<>(lines.size());
        lines.forEach(id->dataMap.put(id,new ArrayList<>()));
        // 将数据按照各自的车站分配到数组中
        for (Tuple odData : data) {
            Short stationId = odData.get("lineId",Short.class);
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

    private String getLineNameById(Short lineId){
        if(lineId==null){
            return "未知线路";
        }
        TmetroLine line = lineDao.findByLineId(lineId);
        if(line == null){
            return "未知线路";
        }
        return line.getLineName();
    }

    @Autowired
    public void setPassengerDao(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }
    @Autowired
    public void setLineDao(TmetroLineDao lineDao) {
        this.lineDao = lineDao;
    }
    @Autowired
    public void setJdbcDao(JdbcTemplateDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }
}
