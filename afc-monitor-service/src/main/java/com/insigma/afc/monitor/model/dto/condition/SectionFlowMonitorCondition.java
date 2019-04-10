/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/10 9:55
 */
@ApiModel("断面客流监控图查询条件")
public class SectionFlowMonitorCondition {

    @ApiModelProperty("线路数组")
    @JsonProperty("line_ids")
    private List<Short> lineIds;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("end_time")
    private Date endTime;

    @ApiModelProperty("最近时间")
    @JsonProperty("last_time")
    @Pattern(regexp = "5|10|15")
    private String lastTime;

    public List<Short> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Short> lineIds) {
        this.lineIds = lineIds;
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

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
