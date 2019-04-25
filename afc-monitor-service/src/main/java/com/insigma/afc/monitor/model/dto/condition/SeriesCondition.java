package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


/**
 * Ticket:曲线图查询条件实体类
 *
 * @author xingshaoya
 * create time: 2019-03-22 11:25
 */
public class SeriesCondition extends BarAndPieCondition {

    @ApiModelProperty("时间间隔（x5分钟）")
    @JsonProperty("interval_count")
    private Integer intervalCount = 1;

    public Integer getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }
}
