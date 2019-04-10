/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:24
 */
@ApiModel("断面客流查询结果")
public class SectionOdFlowStatsView {

    @ApiModelProperty("断面id")
    private Long sectionId;

    @ApiModelProperty("上行客流")
    private String upcount;

    @ApiModelProperty("下行客流")
    private String downcount;

    @ApiModelProperty("总客流")
    private String totalcount;

    public String getUpcount() {
        return upcount;
    }

    public void setUpcount(String upcount) {
        this.upcount = upcount;
    }

    public String getDowncount() {
        return downcount;
    }

    public void setDowncount(String downcount) {
        this.downcount = downcount;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

}
