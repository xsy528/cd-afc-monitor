/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TccSectionValuesDao;
import com.insigma.afc.monitor.dao.TmoSectionOdFlowStatsDao;
import com.insigma.afc.monitor.model.dto.*;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowMonitorCondition;
import com.insigma.afc.monitor.model.entity.TccSectionValues;
import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStats;
import com.insigma.afc.monitor.model.vo.SectionOdFlowStatsView;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.monitor.service.SectionODFlowService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:37
 */
@Service
public class SectionODFlowServiceImpl implements SectionODFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionODFlowServiceImpl.class);
    /**
     * 五分钟一个时间段
     */
    private int timePeriod = 5;
    /**
     * 最大时间段跨度
     */
    private int maxPeriods = 3;

    private int maxPeriod = 288;

    private TccSectionValuesDao sectionValuesDao;
    private MonitorConfigService configService;
    private TopologyService topologyService;
    private EntityManager entityManager;
    private TmoSectionOdFlowStatsDao sectionOdFlowStatsDao;

    @Override
    public SectionMonitorDTO getSectionODFlowDensity(SectionFlowMonitorCondition condition){
        SectionMonitorDTO data = new SectionMonitorDTO();
        SectionFlowMonitorConfigDTO monitorConfigDTO = configService.getSectionFlowMonitorConfig().getData();
        data.setDensityAlarm(monitorConfigDTO.getAlarm().doubleValue());
        data.setDensityWarning(monitorConfigDTO.getWarning().doubleValue());
        List<SectionMonitorDataDTO> sectionMonitorDataDTOS = new ArrayList<>();
        data.setSections(sectionMonitorDataDTOS);

        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        String lastTime = condition.getLastTime();

        //最近5，10，15分钟
        if (lastTime!=null) {
            endTime = new Date();
            startTime = getLastStartTime(endTime,lastTime);
        }

        Map<Date,List<Long>> intervals = getTimeIntervals(startTime,endTime);
        List<TmoSectionOdFlowStats> secOdFlowStatsList = getSectionOdFlowStatsList(condition.getLineIds(),intervals);
        if (secOdFlowStatsList==null||secOdFlowStatsList.isEmpty()) {
            return data;
        }

        int intervalSize = getIntervalSize(intervals);
        if (intervalSize>maxPeriods){
            throw new IllegalArgumentException();
        }
        int minutes = intervalSize * timePeriod;
        for (TmoSectionOdFlowStats flowStats : secOdFlowStatsList) {
            SectionMonitorDataDTO dataDTO = new SectionMonitorDataDTO();
            dataDTO.setSectionId(flowStats.getSectionId());
            // 上行客流密度
            dataDTO.setUpDensity(flowStats.getUpCount() / 100.0/minutes);
            // 下行客流密度
            dataDTO.setDownDensity(flowStats.getDownCount() / 100.0/minutes);
            sectionMonitorDataDTOS.add(dataDTO);
        }

        return data;
    }

    @Override
    public Page<SectionOdFlowStatsView> getSectionODFlowStatsViewList(SectionFlowCondition condition) {
        Short direction = condition.getDirection();
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();

        Map<Date,List<Long>> intervals = getTimeIntervals(startTime,endTime);

        return getSectionOdFlowStatsPage(condition.getLineIds(),
                condition.getPageNumber(),condition.getPageSize(),intervals).map(flowStats->{
            SectionOdFlowStatsView flowStatsView = new SectionOdFlowStatsView();
            flowStatsView.setSectionId(flowStats.getSectionId());
            // 客流
            double flowcount;
            // 统计客流
            BigDecimal decimal;
            if (direction == null || direction == 0) {
                flowcount = (double) flowStats.getUpCount() / 100.0;
                decimal = new BigDecimal(flowcount);
                flowStatsView.setUpcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
            } else {
                flowStatsView.setUpcount(Integer.toString(0));
            }

            if (direction == null || direction == 1) {
                flowcount = (double) flowStats.getDownCount() / 100.0;
                decimal = new BigDecimal(flowcount);
                flowStatsView.setDowncount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
            } else {
                flowStatsView.setDowncount(Integer.toString(0));
            }

            if (direction == null) {
                // 总客流=上行客流加下行客流
                flowStatsView.setTotalcount(Integer.parseInt(flowStatsView.getUpcount())
                        + Integer.parseInt(flowStatsView.getDowncount()) + "");
            } else if (direction == 0) {
                //下行
                flowcount = (flowStats.getTotalCount() - flowStats.getDownCount()) / 100.0;
                decimal = new BigDecimal(flowcount);
                flowStatsView.setTotalcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
            } else if (direction == 1) {
                //上行
                flowcount = (flowStats.getTotalCount() - flowStats.getUpCount()) / 100.0;
                decimal = new BigDecimal(flowcount);
                flowStatsView.setTotalcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
            }
            return flowStatsView;
        });
    }

    @Override
    public List<TmoSectionOdFlowStatsDTO> getSectionODFlowStatistics(SectionFlowMonitorCondition condition) {
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        String lastTime = condition.getLastTime();

        //最近5，10，15分钟
        if (lastTime!=null) {
            endTime = new Date();
            startTime = getLastStartTime(endTime,lastTime);
        }
        Map<Date,List<Long>> intervals = getTimeIntervals(startTime,endTime);
        List<Short> lineIds = condition.getLineIds();
        List<TmoSectionOdFlowStats> tmoSectionOdFlowStats = sectionOdFlowStatsDao.findAll((root,query,build)->{
            List<Predicate> predicates = new ArrayList<>();
            if (lineIds!=null&&!lineIds.isEmpty()){
                predicates.add(root.get("lineId").in(lineIds));
            }
            if (intervals!=null){
                List<Predicate> orPredicates = new ArrayList<>();
                intervals.forEach((d,intervalList)-> {
                    if (intervalList.size()==maxPeriod){
                        orPredicates.add(build.equal(root.get("gatheringDate"), d));
                    }else {
                        Predicate predicate = build.equal(root.get("gatheringDate"), d);
                        Predicate predicate1 = root.get("timeIntervalId").in(intervalList);
                        orPredicates.add(build.and(predicate,predicate1));
                    }
                });
                predicates.add(build.or(orPredicates.toArray(new Predicate[0])));
            }
            return build.and(predicates.toArray(new Predicate[0]));
        });
        List<TmoSectionOdFlowStatsDTO> tmoSectionOdFlowStatsDTOS = new ArrayList<>();
        for (TmoSectionOdFlowStats t:tmoSectionOdFlowStats){
            TmoSectionOdFlowStatsDTO dto = new TmoSectionOdFlowStatsDTO();
            BeanUtils.copyProperties(t, dto);
            dto.setUpCount(dto.getUpCount() / 100);
            dto.setDownCount(dto.getDownCount() / 100);
            tmoSectionOdFlowStatsDTOS.add(dto);
        }
        return tmoSectionOdFlowStatsDTOS;
    }

    @Override
    public List<SectionValuesDTO> getSectionValues() {
        List<TccSectionValues> tccSectionValues = sectionValuesDao.findAll();
        List<SectionValuesDTO> sectionValuesDTOS = new ArrayList<>();
        tccSectionValues.forEach(t->{
            SectionValuesDTO sectionValuesDTO = new SectionValuesDTO();
            sectionValuesDTO.setSectionId(t.getSectionId());
            sectionValuesDTO.setPreStation(topologyService.getNodeText(t.getPreStationId().longValue()).getData());
            sectionValuesDTO.setDownStation(topologyService.getNodeText(t.getDownStationId().longValue()).getData());
            sectionValuesDTOS.add(sectionValuesDTO);
        });
        return sectionValuesDTOS;
    }

    /**
     * 根据结束时间和时间跨度计算开始时间
     * @param endTime 结束时间
     * @param lastTime 时间跨度
     * @return 开始时间
     */
    private Date getLastStartTime(Date endTime,String lastTime){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(endTime);
        startCalendar.add(Calendar.MINUTE,-(Integer.parseInt(lastTime)-5));
        return startCalendar.getTime();
    }

    /**
     * 计算时间段长度
     * @param intervals 时间段
     * @return 长度
     */
    private int getIntervalSize(Map<Date,List<Long>> intervals){
        int size = 0;
        if (intervals==null){
            return size;
        }
        for(List<Long> list:intervals.values()){
            size+=list.size();
        }
        return size;
    }

    /**
     * 根据开始和结束时间计算时间段
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日期和时间段
     */
    private Map<Date,List<Long>> getTimeIntervals(Date startTime,Date endTime){
        Map<Date,List<Long>> intervals = new HashMap<>(16);

        if (startTime==null||endTime==null||startTime.after(endTime)){
            throw new IllegalArgumentException("参数不符合要求");
        }

        // 时间段 5分钟为一个时间段，第一个时段为1
        int period1 = DateTimeUtil.convertTimeToIndex(startTime, timePeriod);
        int period2 = DateTimeUtil.convertTimeToIndex(endTime, timePeriod);

        Date date1 = getDate(startTime);
        Date date2 = getDate(endTime);

        long days = (date2.getTime()-date1.getTime())/(24*60*60*1000);

        //同一天
        if (days==0){
            List<Long> timeIntervalIds = new ArrayList<>();
            for (int i = 0; i <= period2-period1; i++) {
                timeIntervalIds.add((long)(period1 + i));
            }
            intervals.put(date1,timeIntervalIds);
        }
        //跨天
        int maxInterval = 288;
        Calendar calendar1 = Calendar.getInstance();
        if (days>0){
            for (int d = 0; d <=days;d++) {
                if (d==0){
                    //第一天
                    List<Long> timeIntervalIds = new ArrayList<>();
                    for (int i = 0; i <= maxInterval-period1; i++) {
                        timeIntervalIds.add((long)(period1 + i));
                    }
                    intervals.put(date1,timeIntervalIds);
                }else if (d==days){
                    //最后一天
                    List<Long> timeIntervalIds = new ArrayList<>();
                    for (int i = 1; i <= period2; i++) {
                        timeIntervalIds.add((long)(i));
                    }
                    intervals.put(date2,timeIntervalIds);
                }else {
                    List<Long> timeIntervalIds = new ArrayList<>();
                    for (int i = 1; i <= maxInterval; i++) {
                        timeIntervalIds.add((long)i);
                    }
                    calendar1.setTime(date1);
                    calendar1.add(Calendar.DATE,d);
                    intervals.put(calendar1.getTime(),timeIntervalIds);
                }
            }
        }
        return intervals;
    }

    /**
     * 获取断面客流信息
     * @param lineIds 所在线路
     * @param pageSize 页大小
     * @param pageNumber 页码
     * @param intervals 时间段
     * @return 客流信息
     */
    private Page<TmoSectionOdFlowStats> getSectionOdFlowStatsPage(List<Short> lineIds,int pageNumber,int pageSize,
                                                                  Map<Date,List<Long>> intervals) {
        //获取路段信息
        List<TccSectionValues> sectionList = getSectionValuesList(lineIds);
        if (sectionList.isEmpty()) {
            return Page.empty();
        }
        Map<Long,TccSectionValues> sectionMap = new HashMap<>(16);
        sectionList.forEach((s) -> sectionMap.put(s.getSectionId(), s));

        String select = "select t.sectionId,sum(t.upCount),sum(t.downCount),sum(t.totalCount) ";
        //查询需要的时间段和路段
        StringBuilder qlBuilder = new StringBuilder(" from TmoSectionOdFlowStats t where 1=1 and ( ");
        Map<String,Object> parameters = new HashMap<>(16);
        intervals.forEach((date,list)->{
            qlBuilder.append(" (t.timeIntervalId in :timeIntervalId and t.gatheringDate=:gatheringDate) or ");
            parameters.put("timeIntervalId",list);
            parameters.put("gatheringDate",date);
        });
        qlBuilder.delete(qlBuilder.length()-4,qlBuilder.length());
        qlBuilder.append(" )");

        if (!sectionMap.isEmpty()){
            qlBuilder.append(" and t.sectionId in :sectionIds ");
            parameters.put("sectionIds",sectionMap.keySet());
        }
        qlBuilder.append(" group by t.sectionId order by t.sectionId asc ");

        String ql = select+qlBuilder.toString();
        Query query = entityManager.createQuery(ql);
        parameters.forEach((k,v)-> {
            LOGGER.debug("k="+k+",v="+v);
            setParameter(query,k,v);
        });
        Query countQuery = entityManager.createQuery("select count(distinct t.sectionId) "+qlBuilder.toString());
        parameters.forEach((k,v)-> setParameter(countQuery,k,v));
        long total = 0;
        try{
            total = (long)countQuery.getSingleResult();
        }catch (NoResultException e){

        }
        query.setFirstResult(pageNumber*pageSize);
        query.setMaxResults(pageSize);
        List list = query.getResultList();
        List<TmoSectionOdFlowStats> secOdFlowStatslist = getTmoSectionOdFlowStats(list);
        return new PageImpl<>(secOdFlowStatslist,PageRequest.of(pageNumber,pageSize),total);
    }

    private List<TmoSectionOdFlowStats> getTmoSectionOdFlowStats(List list){
        List<TmoSectionOdFlowStats> secOdFlowStatslist = new ArrayList<>();
        for (Object o : list) {
            TmoSectionOdFlowStats value = new TmoSectionOdFlowStats();
            Object[] objs = (Object[]) o;
            value.setSectionId((Long) objs[0]);
            value.setUpCount((Long) objs[1]);
            value.setDownCount((Long) objs[2]);
            value.setTotalCount((Long) objs[3]);
            secOdFlowStatslist.add(value);
        }
        return secOdFlowStatslist;
    }

    private void setParameter(Query query,String key,Object value){
        query.setParameter(key,value);
    }

    private List<TmoSectionOdFlowStats> getSectionOdFlowStatsList(List<Short> lineIds, Map<Date,List<Long>> intervals){
        //获取路段信息
        List<TccSectionValues> sectionList = getSectionValuesList(lineIds);
        if (sectionList.isEmpty()) {
            return null;
        }
        Map<Long,TccSectionValues> sectionMap = new HashMap<>(16);
        sectionList.forEach((s) -> sectionMap.put(s.getSectionId(), s));

        //查询需要的时间段和路段
        StringBuilder qlBuilder = new StringBuilder("select t.sectionId,sum(t.upCount),sum(t.downCount)," +
                "sum(t.totalCount) from TmoSectionOdFlowStats t where 1=1 and ( ");
        Map<String,Object> parameters = new HashMap<>(16);
        intervals.forEach((date,list)->{
            qlBuilder.append(" (t.timeIntervalId in :timeIntervalId and t.gatheringDate=:gatheringDate) or ");
            parameters.put("timeIntervalId",list);
            parameters.put("gatheringDate",date);
        });
        qlBuilder.delete(qlBuilder.length()-4,qlBuilder.length());
        qlBuilder.append(" )");

        if (!sectionMap.isEmpty()){
            qlBuilder.append(" and t.sectionId in :sectionIds ");
            parameters.put("sectionIds",sectionMap.keySet());
        }
        qlBuilder.append(" group by t.sectionId order by t.sectionId asc ");

        String ql = qlBuilder.toString();
        Query query = entityManager.createQuery(ql);
        parameters.forEach((k,v)-> {
            LOGGER.debug("k="+k+",v="+v);
            query.setParameter(k,v);
        });
        List list = query.getResultList();
        return getTmoSectionOdFlowStats(list);
    }

    private List<TccSectionValues> getSectionValuesList(List<Short> lines) {
        return sectionValuesDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (lines != null && !lines.isEmpty()) {
                predicates.add(root.get("lineId").in(lines));
            }
            // 不包括换乘段
            predicates.add(builder.equal(root.get("transferFlag"), (short)0));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    private Date getDate(Date datetime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    @Autowired
    public void setSectionOdFlowStatsDao(TmoSectionOdFlowStatsDao sectionOdFlowStatsDao) {
        this.sectionOdFlowStatsDao = sectionOdFlowStatsDao;
    }

    @Autowired
    public void setSectionValuesDao(TccSectionValuesDao sectionValuesDao) {
        this.sectionValuesDao = sectionValuesDao;
    }

    @Autowired
    public void setConfigService(MonitorConfigService configService) {
        this.configService = configService;
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
