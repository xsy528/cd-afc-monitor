package com.insigma.afc.monitor.model.dto.condition;

import com.insigma.afc.monitor.constant.OrderDirection;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-30:17:53
 */
public class DeviceStatusCondition {

    private Date startTime;
    private Date endTime;
    private List<Long> nodeIds;
    @NotNull
    private List<Short> statusList;
    private OrderDirection orderType = OrderDirection.ASC;

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

    public List<Short> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Short> statusList) {
        this.statusList = statusList;
    }
}
