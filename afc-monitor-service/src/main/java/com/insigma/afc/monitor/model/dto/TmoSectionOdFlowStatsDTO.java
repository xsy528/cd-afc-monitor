/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/15 11:01
 */
@ApiModel("客流统计")
public class TmoSectionOdFlowStatsDTO {

    @ApiModelProperty("断面id")
    @JsonProperty("section_id")
    private Long sectionId;

    @ApiModelProperty("运营日")
    @JsonProperty("gathering_date")
    private Date gatheringDate;

    @ApiModelProperty("时间段id")
    @JsonProperty("time_interval_id")
    private Long timeIntervalId;

    @ApiModelProperty("上行客流")
    @JsonProperty("up_count")
    private Long upCount;

    @ApiModelProperty("下行客流")
    @JsonProperty("down_count")
    private Long downCount;

    @ApiModelProperty("客流总数")
    @JsonProperty("total_count")
    private Long totalCount;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Date getGatheringDate() {
        return gatheringDate;
    }

    public void setGatheringDate(Date gatheringDate) {
        this.gatheringDate = gatheringDate;
    }

    public Long getTimeIntervalId() {
        return timeIntervalId;
    }

    public void setTimeIntervalId(Long timeIntervalId) {
        this.timeIntervalId = timeIntervalId;
    }

    public Long getUpCount() {
        return upCount;
    }

    public void setUpCount(Long upCount) {
        this.upCount = upCount;
    }

    public Long getDownCount() {
        return downCount;
    }

    public void setDownCount(Long downCount) {
        this.downCount = downCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
