package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket:客流查询接口
 *
 * @author xingshaoya
 * create time: 2019-03-22 14:33
 */
public class PassengerCondition extends PageBean {
    @ApiModelProperty("选择日期")
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("选择时间")
    @JsonProperty("time")
    private String time;

    @ApiModelProperty("统计类型")
    @JsonProperty("stat_type")
    private Short statType;

    @JsonProperty("station_ids")
    private List<Integer> stationIds;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Short getStatType() {
        return statType;
    }

    public void setStatType(Short statType) {
        this.statType = statType;
    }

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }
}
