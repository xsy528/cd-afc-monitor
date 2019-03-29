/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/29 16:20
 */
@ApiModel("断面客流密度")
public class SectionMonitorDataDTO {

    @ApiModelProperty("上行客流密度")
    @JsonProperty("up_density")
    private Double upDensity;

    @ApiModelProperty("下行客流密度")
    @JsonProperty("down_density")
    private Double downDensity;

    @ApiModelProperty("断面id")
    private Long sectionId;

    public Double getUpDensity() {
        return upDensity;
    }

    public void setUpDensity(Double upDensity) {
        this.upDensity = upDensity;
    }

    public Double getDownDensity() {
        return downDensity;
    }

    public void setDownDensity(Double downDensity) {
        this.downDensity = downDensity;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
