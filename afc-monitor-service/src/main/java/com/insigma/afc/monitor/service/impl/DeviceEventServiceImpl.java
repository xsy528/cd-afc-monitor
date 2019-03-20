package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TmoEquEventDao;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.EquEventCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatus;
import com.insigma.afc.monitor.service.DeviceEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ticket:设备事件查询接口
 * @author  xingshaoya
 */
@Service
public class DeviceEventServiceImpl implements DeviceEventService {

    private TmoEquEventDao tmoEquEventDao;

    @Override
    public Page<TmoEquStatus> getDeviceEventSearch(EquEventCondition condition) {
        //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内
        //节点id
        List<Long> nodeIds = condition.getNodeIds();
        //开始时间
        Date startTime = condition.getStartTime();
        //结束时间
        Date endTime = condition.getEndTime();
        //事件等级
        Short eventLevel = condition.getEventLevel();

        Integer pageSize = condition.getPageSize();
        Integer page = condition.getPageNumber();

        return tmoEquEventDao.findAll((root,query,builder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (nodeIds!=null){
                predicates.add(root.get("nodeId").in(nodeIds));
                //将站点ID存入root中
            }
            if (eventLevel!=null){
                predicates.add(builder.equal(root.get("nodeId"),eventLevel));
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
    }

    @Autowired
    public void setTmoEquEventDao(TmoEquEventDao tmoEquEventDao) {
        this.tmoEquEventDao = tmoEquEventDao;
    }
}
