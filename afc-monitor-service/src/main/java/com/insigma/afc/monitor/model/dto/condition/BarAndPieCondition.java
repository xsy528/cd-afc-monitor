package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

/**
 * Ticket:客流查询基础条件
 * 包括日期时间和票种，站点
 * @author xingshaoya
 * create time: 2019-03-22 10:40
 */
@ApiModel("柱状图、饼图查询条件")
public class BarAndPieCondition {

    public static String[] LEGEND = new String[] { "进站", "出站", "购票", "充值", "合计" };

    @ApiModelProperty("选择日期")
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date date;

    @ApiModelProperty("选择时间")
    @JsonProperty("time")
    protected String time;

    @ApiModelProperty("票种")
    @JsonProperty("ticket_family")
    private Short ticketFamily;

    @ApiModelProperty("交易类型")
    @JsonProperty("trans_type")
    private List<Integer> transType;

    @ApiModelProperty("线路编号")
    @JsonProperty("lines")
    protected List<Short> lines;

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

    public List<Short> getLines() {
        return lines;
    }

    public void setLines(List<Short> lines) {
        this.lines = lines;
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

}
