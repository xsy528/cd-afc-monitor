package com.insigma.afc.monitor.model.dto.condition;

import com.insigma.afc.monitor.constant.OrderDirection;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-30:20:43
 */
public class DeviceEventCondition {

    private List<Long> nodeIds;
    private List<Short> devTypeList;
    private Integer maxCount;
    private String orderField;
    private OrderDirection orderType;
    private Date startTime;
    private Date endTime;

    public List<Short> getDevTypeList() {
        return devTypeList;
    }

    public void setDevTypeList(List<Short> devTypeList) {
        this.devTypeList = devTypeList;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderDirection getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderDirection orderType) {
        this.orderType = orderType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }
}
