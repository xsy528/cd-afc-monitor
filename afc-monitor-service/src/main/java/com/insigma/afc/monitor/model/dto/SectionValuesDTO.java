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

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/9 13:52
 */
@ApiModel("断面信息")
public class SectionValuesDTO {

    @JsonProperty("section_id")
    @ApiModelProperty("断面id")
    private Long sectionId;

    @ApiModelProperty("起始车站")
    @JsonProperty("pre_station")
    private String preStation;

    @ApiModelProperty("结束车站")
    @JsonProperty("down_station")
    private String downStation;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getPreStation() {
        return preStation;
    }

    public void setPreStation(String preStation) {
        this.preStation = preStation;
    }

    public String getDownStation() {
        return downStation;
    }

    public void setDownStation(String downStation) {
        this.downStation = downStation;
    }
}
