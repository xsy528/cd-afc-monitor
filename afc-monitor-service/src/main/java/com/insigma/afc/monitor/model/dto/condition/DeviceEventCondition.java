package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Ticket:设备事件查询条件实体类
 * @author xingshaoya
 * @date 2019-03-18 18:20:43
 */
@ApiModel(description = "设备事件查询条件")
public class DeviceEventCondition extends PageBean {

    //根据总线路、分支线路、站点并在开始时间和结束时间内，并且事件等级（非用户限制）in(1,2,3)内

    @ApiModelProperty("线路id数组")
    @JsonProperty("line_ids")
    protected Short[] lineIds;

    @ApiModelProperty("车站id数组")
    @JsonProperty("station_ids")
    private Integer[] stationIds;

    @ApiModelProperty("节点id数组")
    @JsonProperty("node_ids")
    private Long[] nodeIds;

    @ApiModelProperty("开始时间")
    @JsonProperty("event_level")
    private Short eventLevel;

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

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

    public Long[] getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(Long[] nodeIds) {
        this.nodeIds = nodeIds;
    }

    public Short[] getLineIds() {
        return lineIds;
    }

    public void setLineIds(Short[] lineIds) {
        this.lineIds = lineIds;
    }

    public Integer[] getStationIds() {
        return stationIds;
    }

    public void setStationIds(Integer[] stationIds) {
        this.stationIds = stationIds;
    }

    public Short getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Short eventLevel) {
        this.eventLevel = eventLevel;
    }

    @Override
    public String toString() {
        return "DeviceEventCondition{" +
                "lineIds=" + Arrays.toString(lineIds) +
                ", stationIds=" + Arrays.toString(stationIds) +
                ", nodeIds=" + Arrays.toString(nodeIds) +
                ", eventLevel=" + eventLevel +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
