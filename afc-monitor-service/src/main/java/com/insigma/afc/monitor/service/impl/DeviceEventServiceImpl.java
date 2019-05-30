package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TmoEquEventDao;
import com.insigma.afc.monitor.model.dto.condition.EquEventCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatus;
import com.insigma.afc.monitor.model.vo.DeviceEvent;
import com.insigma.afc.monitor.service.DeviceEventService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.function.Function;

/**
 * Ticket:设备事件查询接口
 * @author  xingshaoya
 */
@Service
public class DeviceEventServiceImpl implements DeviceEventService {

    private TmoEquEventDao tmoEquEventDao;
    private TopologyService topologyService;

    @Override
    public Page<DeviceEvent> getDeviceEventSearch(EquEventCondition condition) {
        //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内
        //节点id
        List<Long> nodeIds = condition.getNodeIds();
        //开始时间
        Date startTime = condition.getStartTime();
        //结束时间
        Date endTime = condition.getEndTime();

        Integer pageSize = condition.getPageSize();
        Integer page = condition.getPageNumber();

        Page<TmoEquStatus> tmoEquStatusPage = tmoEquEventDao.findAll((root,query,builder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (nodeIds!=null&&!nodeIds.isEmpty()){
                predicates.add(root.get("nodeId").in(nodeIds));
                //将站点ID存入root中
            }
            if (startTime!=null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"),startTime));
                //获取开始时间之后
            }
            if (endTime!=null){
                predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"),endTime));
                //获取结束时间之前
            }
            query.orderBy(builder.desc(root.get("occurTime")));
            //以occurTime降序
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page,pageSize));

        Set<Long> ids = new HashSet<>();
        Map<Long,String> textMap = null;
        if (!tmoEquStatusPage.isEmpty()){
            for (TmoEquStatus tmoEquStatus:tmoEquStatusPage.getContent()){
                ids.add(tmoEquStatus.getNodeId());
            }
            textMap = topologyService.getNodeTexts(ids).getData();
        }

        return tmoEquStatusPage.map(new TmoEquStatusToDeviceEvent(textMap));
    }

    private class TmoEquStatusToDeviceEvent implements Function<TmoEquStatus,DeviceEvent> {
        private Map<Long,String> textMap;

        TmoEquStatusToDeviceEvent(Map<Long,String> textMap){
            this.textMap = textMap;
        }

        @Override
        public DeviceEvent apply(TmoEquStatus tmoEquStatus) {
            //返回结果集合显示，显示实体类
            //节点名称/节点编码,事件名称/编号，事件描述,发生时间
            DeviceEvent deviceEventInfo = new DeviceEvent();

            deviceEventInfo.setNodeName(textMap.get(tmoEquStatus.getNodeId()));
            deviceEventInfo.setNodeId(tmoEquStatus.getNodeId().toString());
            deviceEventInfo.setEventName(tmoEquStatus.getStatusName()+"/"+tmoEquStatus.getStatusId());
            deviceEventInfo.setEventDesc(tmoEquStatus.getStatusDesc()+"/"+tmoEquStatus.getStatusValue());
            deviceEventInfo.setOccurTime(DateTimeUtil.formatDate(tmoEquStatus.getOccurTime()));

            return deviceEventInfo;
        }
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    @Autowired
    public void setTmoEquEventDao(TmoEquEventDao tmoEquEventDao) {
        this.tmoEquEventDao = tmoEquEventDao;
    }
}
