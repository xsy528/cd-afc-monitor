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
    protected Date time;

    @ApiModelProperty("统计类型")
    @JsonProperty("stat_type")
    protected Short statType;

    private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

    private int startTimeIndex;

    private int endTimeIndex;

    private List<Long> stationId;

    private String[] partNames;

    private int timeInterval = 5;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Short getStatType() {
        return statType;
    }

    public void setStatType(Short statType) {
        this.statType = statType;
    }

    public Map<Integer, String> getStationNameMap() {
        return stationNameMap;
    }

    public void setStationNameMap(Map<Integer, String> stationNameMap) {
        this.stationNameMap = stationNameMap;
    }

    public int getStartTimeIndex() {
        return startTimeIndex;
    }

    public void setStartTimeIndex(int startTimeIndex) {
        this.startTimeIndex = startTimeIndex;
    }

    public int getEndTimeIndex() {
        return endTimeIndex;
    }

    public void setEndTimeIndex(int endTimeIndex) {
        this.endTimeIndex = endTimeIndex;
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
