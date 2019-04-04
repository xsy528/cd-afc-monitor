package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket:客流查询接口
 *
 * @author: xingshaoya
 * create time: 2019-03-22 14:33
 */
public class PassengerCondition extends PageBean {
    @ApiModelProperty("选择日期")
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date date;

    @ApiModelProperty("选择时间")
    @JsonProperty("time")
    protected String time;

    @ApiModelProperty("统计类型")
    @JsonProperty("stat_type")
    protected Short statType;

    //@JsonProperty("station_name_map")
    //private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

    @JsonProperty("station_id")
    private List<Long> stationId;

    @JsonProperty("part_names")
    private String[] partNames;

    @JsonProperty("time_interval")
    private int timeInterval = 5;

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

    public List<Long> getStationId() {
        return stationId;
    }

    public void setStationId(List<Long> stationId) {
        this.stationId = stationId;
    }

    public String[] getPartNames() {
        return partNames;
    }

    public void setPartNames(String[] partNames) {
        this.partNames = partNames;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }
}
