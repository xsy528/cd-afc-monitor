package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.commons.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 设备事件查询条件实体类
 * @author xingshaoya
 */
public class EquEventCondition extends PageBean {

    @ApiModelProperty("节点ID")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
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

    @Override
    public String toString() {
        return "EquEventCondition{" +
                "nodeIds=" + nodeIds +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
