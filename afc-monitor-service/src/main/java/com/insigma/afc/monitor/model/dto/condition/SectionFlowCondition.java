/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:15
 */
@ApiModel("断面客流查询条件")
public class SectionFlowCondition extends PageBean {

    @ApiModelProperty("线路id")
    @JsonProperty("line_ids")
    private List<Short> lineIds;

    @ApiModelProperty("方向")
    private Short direction;

    @ApiModelProperty("日期")
    private Date date;

    @ApiModelProperty("时间段")
    @JsonProperty("time_section")
    private String timeSection;

    public List<Short> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Short> lineIds) {
        this.lineIds = lineIds;
    }

    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(String timeSection) {
        this.timeSection = timeSection;
    }

}
