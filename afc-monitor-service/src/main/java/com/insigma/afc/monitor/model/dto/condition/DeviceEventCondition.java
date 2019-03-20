package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-30:20:43
 */
@ApiModel(description = "设备事件查询条件")
public class DeviceEventCondition extends PageBean {

    //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内

    @ApiModelProperty("设备节点数组")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("设备类型数组")
    @JsonProperty("dev_type_list")
    private List<Short> devTypeList;

    @ApiModelProperty("最大条数")
    @JsonProperty("max_count")
    private Integer maxCount;

    @ApiModelProperty("排序字段")
    @JsonProperty("order_field")
    private String orderField;

    @ApiModelProperty("排序方向")
    @JsonProperty("order_type")
    private OrderDirection orderType;

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
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
