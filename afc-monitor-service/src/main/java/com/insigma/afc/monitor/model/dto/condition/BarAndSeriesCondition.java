package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

/**
 * Ticket:客流查询基础条件
 * 包括日期时间和票种，站点
 * @author: xingshaoya
 * create time: 2019-03-22 10:40
 */
public class BarAndSeriesCondition extends PageBean {

    public static String[] LEGEND = new String[] { "进站", "出站", "购票", "充值", "合计" };

    @ApiModelProperty("选择日期")
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date date;

    @ApiModelProperty("选择时间")
    @JsonProperty("time")
    protected String time;
    //

    @ApiModelProperty("票种")
    @JsonProperty("ticket_family")
    protected Short ticketFamily;

    @ApiModelProperty("交易类型")
    @JsonProperty("trans_type")
    protected List<Integer> transType = new ArrayList<>(LEGEND.length);

    private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

    private List<Long> stationId;

    private String[] partNames;

    private int timeInterval = 5;

    public BarAndSeriesCondition() {

    }

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

    public Map<Integer, String> getStationNameMap() {
        return stationNameMap;
    }

    public void setStationNameMap(Map<Integer, String> stationNameMap) {
        this.stationNameMap = stationNameMap;
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

    public Short getTicketFamily() {
        return ticketFamily;
    }

    public void setTicketFamily(Short ticketFamily) {
        this.ticketFamily = ticketFamily;
    }

    public List<Integer> getTransType() {
        return transType;
    }

    public void setTransType(List<Integer> transType) {
        this.transType = transType;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public static String[] getLEGEND() {
        return LEGEND;
    }
}
