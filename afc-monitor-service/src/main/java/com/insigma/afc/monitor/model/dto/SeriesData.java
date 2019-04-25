/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket: 曲线图点数据
 *
 * @author xuzhemin
 * 2019/4/25 13:08
 */
@ApiModel("曲线图点数据")
public class SeriesData {

    public SeriesData(Date date, List<Long> values) {
        this.date = date;
        this.values = values;
    }

    @ApiModelProperty("时间")
    private Date date;

    @ApiModelProperty("进站，出站，购票，充值")
    private List<Long> values;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
}
