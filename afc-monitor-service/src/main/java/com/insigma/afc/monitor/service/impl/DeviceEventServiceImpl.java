package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.dao.TmoEquEventDao;
import com.insigma.afc.monitor.dao.TmoEquStatusCurDao;
import com.insigma.afc.monitor.dao.TmoItemStatusDao;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
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
 * 设备事件查询接口
 * @author  xingshaoya
 */
@Service
public class DeviceEventServiceImpl implements DeviceEventService {

    @Autowired
    private TmoEquEventDao tmoEquEventDao;
    /**
     * 获取设备事件
     * @param condition
     * @return
     */
    @Override
    public Page<TmoEquStatus> getDeviceEventSearch(DeviceEventCondition condition) {
        //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内
        //节点id
        List<Long> nodeIds = condition.getNodeIds();
        //开始时间
        Date startTime = condition.getStartTime();
        //结束时间
        Date endTime = condition.getEndTime();
        //设备类型数组
        List<Short> devTypeList = condition.getDevTypeList();
        //最大条数
        Integer maxCount = condition.getMaxCount();
        //排序字段
        String orderField = condition.getOrderField();
        //排序方向
        OrderDirection orderType = condition.getOrderType();

        Integer pageSize = condition.getPageSize();
        Integer page = condition.getPageNumber();


        return tmoEquEventDao.findAll((root,query,builder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (nodeIds!=null){
                predicates.add(root.get("nodeId").in(nodeIds));  //将站点ID存入root中
            }
            if (startTime!=null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"),startTime));//获取开始时间之后
            }
            if (endTime!=null){
                predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"),endTime));//获取结束时间之前
            }
            //事件等级
//            if (nodeIds!=null){
//                predicates.add(root.get("nodeId").in(nodeIds));  //将站点ID存入root中
//            }
            query.orderBy(builder.desc(root.get("occurTime")));//以modeUploadtime降序
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page,pageSize));
    }

}