package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.commons.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-02-27 14:44
 */
@ApiModel(description = "模式广播查询条件")
public class ModeBroadcastCondition extends PageBean {

    @ApiModelProperty("车站id数组")
    @JsonProperty("station_ids")
    private List<Integer> stationIds;

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

    @ApiModelProperty("进入的模式")
    @JsonProperty("entry_mode")
    private Short entryMode;

    @ApiModelProperty("操作员id")
    @JsonProperty("operator_id")
    private String operatorId;

    @ApiModelProperty("目标车站id")
    @JsonProperty("dest_station_id")
    private Integer destStationId;

    @ApiModelProperty("模式广播状态")
    @JsonProperty("broadcast_status")
    private Short broadcastStatus;

    @ApiModelProperty("模式广播方式")
    @JsonProperty("broadcast_type")
    private Short broadcastType;

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
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

    public Short getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(Short entryMode) {
        this.entryMode = entryMode;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getDestStationId() {
        return destStationId;
    }

    public void setDestStationId(Integer destStationId) {
        this.destStationId = destStationId;
    }

    public Short getBroadcastStatus() {
        return broadcastStatus;
    }

    public void setBroadcastStatus(Short broadcastStatus) {
        this.broadcastStatus = broadcastStatus;
    }

    public Short getBroadcastType() {
        return broadcastType;
    }

    public void setBroadcastType(Short broadcastType) {
        this.broadcastType = broadcastType;
    }
}
