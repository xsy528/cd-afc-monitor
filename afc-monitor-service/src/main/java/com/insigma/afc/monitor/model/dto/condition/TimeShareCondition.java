package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 11:38.
 * @Ticket :
 */
@ApiModel("分时查询查询条件")
public class TimeShareCondition extends PageBean {

    @ApiModelProperty("日期")
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("时间段")
    @JsonProperty("time")
    private String time;

    @ApiModelProperty("时间间隔（分钟）")
    @JsonProperty("interval_count")
    private Integer intervalCount;

    @ApiModelProperty("票种")
    @JsonProperty("ticket_family")
    private Short ticketType;

    @ApiModelProperty("节点名称")
    @JsonProperty("station_id")
    private Integer nodeId;

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

    public Integer getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    public Short getTicketType() {
        return ticketType;
    }

    public void setTicketType(Short ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
