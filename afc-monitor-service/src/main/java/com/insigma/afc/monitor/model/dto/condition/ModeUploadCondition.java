package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-02-27 11:40
 */
@ApiModel(description = "模式上传查询条件")
public class ModeUploadCondition extends PageBean {

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
}
