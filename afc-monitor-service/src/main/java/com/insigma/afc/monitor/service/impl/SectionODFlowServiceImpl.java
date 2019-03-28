/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TccSectionValuesDao;
import com.insigma.afc.monitor.model.dto.SectionFlowMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.model.entity.MetroStation;
import com.insigma.afc.monitor.model.entity.TccSectionValues;
import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStats;
import com.insigma.afc.monitor.model.vo.SectionOdFlowStatsView;
import com.insigma.afc.monitor.service.MonitorConfigService;
import com.insigma.afc.monitor.service.SectionODFlowService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:37
 */
@Service
public class SectionODFlowServiceImpl implements SectionODFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionODFlowServiceImpl.class);

    private ThreadLocal<Map<Long, TccSectionValues>> sectionmap = ThreadLocal.withInitial(()->new HashMap<>());
    /**
     * 五分钟一个时间段
     */
    private int timePeriod = 5;
    /**
     * 最大时间段跨度
     */
    private int maxPeriods = 3;

    private TccSectionValuesDao sectionValuesDao;
    private MonitorConfigService configService;
    private TopologyService topologyService;

    private EntityManager entityManager;

    @Override
    public List<SectionOdFlowStatsView> getSectionODFlowStatsViewList(SectionFlowCondition condition) {
        Short direction = condition.getDirection();
        String timeSection = condition.getTimeSection();
        // 时间段 5分钟为一个时间段，第一个时段为1
        List<Long> timeIntervalIds = new ArrayList<>();
        if (timeSection != null) {
            String[] temp = timeSection.split("-");

            Date date1 = DateTimeUtil.parseStringToDate(temp[0], "HH:mm");
            Date date2 = DateTimeUtil.parseStringToDate(temp[1], "HH:mm");

            int period1 = DateTimeUtil.convertTimeToIndex(date1, timePeriod);
            int period2 = DateTimeUtil.convertTimeToIndex(date2, timePeriod);
            if (date2.getDate() - date1.getDate() == 1) {
                period2 = 289;
            }
            int len = period2-period1;
            if (len>=maxPeriods){
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < len; i++) {
                timeIntervalIds.add((long)(period1 + i));
            }
        }

        List<TmoSectionOdFlowStats> secOdFlowStatslist = getSectionOdFlowStatsList(condition.getLineIds(),
                timeIntervalIds,condition.getDate(),PageRequest.of(condition.getPageNumber(),condition.getPageSize()));
        List<SectionOdFlowStatsView> secOdFlowViewlist = new ArrayList<>();
        if (secOdFlowStatslist==null||secOdFlowStatslist.isEmpty()) {
            return secOdFlowViewlist;
        }

        SectionFlowMonitorConfigDTO sectionFlowMonitorConfigDTO = configService.getSectionFlowMonitorConfig().getData();
        int lowFlow = sectionFlowMonitorConfigDTO.getWarning();
        int highFlow = sectionFlowMonitorConfigDTO.getAlarm();

        int minutes = timeIntervalIds.size() * timePeriod;
        for (TmoSectionOdFlowStats flowStats : secOdFlowStatslist) {
            SectionOdFlowStatsView flowStatsView = new SectionOdFlowStatsView();
            TccSectionValues sectionValues = sectionmap.get().get(flowStats.getSectionId());
            flowStatsView.setSectionId(flowStats.getSectionId());
            flowStatsView.setLine(topologyService.getNodeText(sectionValues.getLineId().longValue()).getData());
            flowStatsView.setUpstation(topologyService.getNodeText(sectionValues.getPreStationId().longValue())
                    .getData());
            flowStatsView.setDownstation(topologyService.getNodeText(sectionValues.getDownStationId().longValue())
                    .getData());
            flowStatsView.setBusinessday(DateTimeUtil.formatDate(flowStats.getGatheringDate(), "yyyy-MM-dd"));
            // 上行客流密度
            double flowcount = 0;
            // 下行客流密度
            double downFlowcount = 0;

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
                // 上行客流密度
                flowcount = flowStats.getUpCount() / 100.0;
                // 下行客流密度
                downFlowcount = flowStats.getDownCount() / 100.0;
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

            // 获取车站位置
            MetroStation mapItem = topologyService.getStationNode(sectionValues.getPreStationId()).getData();
            if (mapItem == null) {
                LOGGER.debug("车站[" + sectionValues.getPreStationId() + "] 未找到。");
                continue;
            }
            flowStatsView.setUpStationPosX(mapItem.getImageLocation().getX());
            flowStatsView.setUpStationPosY(mapItem.getImageLocation().getY());

            mapItem = topologyService.getStationNode((sectionValues.getDownStationId())).getData();
            if (mapItem == null) {
                LOGGER.debug("车站[" + sectionValues.getDownStationId() + "] 未找到。");
                continue;
            }
            flowStatsView.setDownStationPosX(mapItem.getImageLocation().getX());
            flowStatsView.setDownStationPosY(mapItem.getImageLocation().getY());
            //
            short odLevelFlag = 0;
            double count = flowcount / minutes;

            odLevelFlag = getOdLevelFlag(count,lowFlow,highFlow);
            // 上行客流密度
            flowStatsView.setOdLevelFlag(odLevelFlag);
            // 下行客流密度
            count = downFlowcount / minutes;
            odLevelFlag = getOdLevelFlag(count,lowFlow,highFlow);
            flowStatsView.setDownOdLevelFlag(odLevelFlag);
            secOdFlowViewlist.add(flowStatsView);
        }
        return secOdFlowViewlist;
    }

    private List<TmoSectionOdFlowStats> getSectionOdFlowStatsList(List<Short> lineIds,List<Long> timeIntervalIds,
                                                                  Date date,PageRequest pageRequest) {
        List<TccSectionValues> sectionList = getSectionValuesList(lineIds);
        if (sectionList.isEmpty()) {
            return null;
        }
        sectionList.forEach((s) -> sectionmap.get().put(s.getSectionId(), s));

        // 当天的时段
        List<Long> timeIntervalIds1 = null;
        // 昨天的时段
        List<Long> timeIntervalIds2 = null;

        if (timeIntervalIds == null) {
            timeIntervalIds = new ArrayList<>();
        }
        if (!timeIntervalIds.isEmpty() && date != null) {
            // 跨天
            if (timeIntervalIds.get(0) > timeIntervalIds.get(timeIntervalIds.size() - 1)) {
                int splitIndex = 0;
                for (int i = 0; i < timeIntervalIds.size(); i++) {
                    if (i + 1 < timeIntervalIds.size()) {
                        splitIndex++;
                        if (timeIntervalIds.get(i) > timeIntervalIds.get(i + 1)) {
                            break;
                        }
                    }
                }
                timeIntervalIds2 = timeIntervalIds.subList(0,splitIndex);
                timeIntervalIds1 = timeIntervalIds.subList(splitIndex,timeIntervalIds.size()-splitIndex);
            } else {
                timeIntervalIds1 = timeIntervalIds;
            }
        }

        StringBuilder qlBuilder = new StringBuilder("select t.sectionId,sum(t.upCount),sum(t.downCount)," +
                "sum(t.totalCount) from TmoSectionOdFlowStats t where 1=1 ");

        Map<String,Object> parameters = new HashMap<>();
        if (timeIntervalIds1!=null){
            qlBuilder.append(" and t.timeIntervalId in :timeIntervalId1 and t.gatheringDate=:gatheringDate ");
            parameters.put("timeIntervalId1",timeIntervalIds1);
            parameters.put("gatheringDate",date);
        }
        if (timeIntervalIds2!=null){
            qlBuilder.append(" and (t.timeIntervalId in :timeIntervalId2 or t.gatheringDate=:gatheringDate) ");
            parameters.put("timeIntervalId2",timeIntervalIds2);
            parameters.put("gatheringDate",DateTimeUtil.getDateDiff(date, -1));
        }
        if (!sectionmap.get().isEmpty()){
            qlBuilder.append(" and t.sectionId in :sectionIds ");
            parameters.put("sectionIds",sectionmap.get().keySet());
        }
        qlBuilder.append(" group by t.sectionId order by t.sectionId asc ");

        String ql = qlBuilder.toString();
        Query query = entityManager.createQuery(ql);
        parameters.forEach((k,v)-> {
            LOGGER.debug("k="+k+",v="+v);
            query.setParameter(k,v);
        });
        query.setFirstResult(pageRequest.getPageNumber()*pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List list = query.getResultList();
        List<TmoSectionOdFlowStats> secOdFlowStatslist = new ArrayList<>();
        for (Object o : list) {
            TmoSectionOdFlowStats value = new TmoSectionOdFlowStats();
            Object[] objs = (Object[]) o;
            value.setSectionId((Long) objs[0]);
            value.setGatheringDate(date);
            value.setTimeIntervalId(0L);
            value.setUpCount((Long) objs[1]);
            value.setDownCount((Long) objs[2]);
            value.setTotalCount((Long) objs[3]);
            secOdFlowStatslist.add(value);
        }
        return secOdFlowStatslist;
    }

    private short getOdLevelFlag(double count,int lowFlow,int highFlow) {
        short odLevelFlag;
        if (count <= lowFlow) {
            odLevelFlag = 0;
        } else if (count >= highFlow) {
            odLevelFlag = 2;
        } else {
            odLevelFlag = 1;
        }
        return odLevelFlag;
    }

    private List<TccSectionValues> getSectionValuesList(List<Short> lines) {
        return sectionValuesDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (lines != null && !lines.isEmpty()) {
                predicates.add(root.get("lineId").in(lines));
            }
            // 不包括换乘段
            predicates.add(builder.equal(root.get("transferFlag"), 0));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
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
