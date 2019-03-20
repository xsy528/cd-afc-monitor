package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 设备事件查询条件实体类
 * @author xingshaoya
 */
public class EquEventCondition extends PageBean {

    private static final long serialVersionUID = 1L;
    //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内

    @ApiModelProperty("节点ID")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("站点ID")
    @JsonProperty("station_ids")
    private List<Integer> stationIds;

    @ApiModelProperty("路线ID")
    @JsonProperty("line_ids")
    private List<Short> lineIds;


    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

    @ApiModelProperty("事件等级")
    @JsonProperty("event_level")
    private Short eventLevel;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }

    public List<Short> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Short> lineIds) {
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

    public Short getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Short eventLevel) {
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
