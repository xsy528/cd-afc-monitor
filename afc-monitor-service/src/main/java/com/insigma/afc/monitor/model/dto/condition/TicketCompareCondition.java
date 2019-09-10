package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 11:38.
 * @Ticket :
 */
@ApiModel("票卡对比查询条件")
public class TicketCompareCondition  {

    private final String[] partNames = {"进站","出站","购票","充值"};

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;


    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("交易类型")
    @JsonProperty("trans_type")
    private List<Integer> transType;

    @ApiModelProperty("站点id")
    @JsonProperty("stations")
    private List<Integer> stations;

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

    public List<Integer> getTransType() {
        return transType;
    }

    public void setTransType(List<Integer> transType) {
        this.transType = transType;
    }

    public List<Integer> getStations() {
        return stations;
    }

    public void setStations(List<Integer> stations) {
        this.stations = stations;
    }

    public String[] getPartNames() {
        return partNames;
    }

}
