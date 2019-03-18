package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class EquEventCondition extends PageBean {


    //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内
    @ApiModelProperty("节点ID")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("站点ID")
    @JsonProperty("station_ids")
    private List<Short> stationIds;

    @ApiModelProperty("路线ID")
    @JsonProperty("line_ids")
    private Integer lineIds;


    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("endTime")
    private Date endTime;

    @ApiModelProperty("事件等级")
    @JsonProperty("envent_level")
    private Date eventLevel;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<Short> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Short> stationIds) {
        this.stationIds = stationIds;
    }

    public Integer getLineIds() {
        return lineIds;
    }

    public void setLineIds(Integer lineIds) {
        this.lineIds = lineIds;
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

    public Date getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Date eventLevel) {
        this.eventLevel = eventLevel;
    }

    @Override
    public String toString() {
        return "EquEventCondition{" +
                "nodeIds=" + nodeIds +
                ", stationIds=" + stationIds +
                ", lineIds=" + lineIds +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", eventLevel=" + eventLevel +
                '}';
    }
}
