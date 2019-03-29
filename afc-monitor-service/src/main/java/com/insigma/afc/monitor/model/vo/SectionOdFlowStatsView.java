/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
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

    @ApiModelProperty("运营日期")
    private String businessday;

    @ApiModelProperty("线路/编号")
    private String line;

    @ApiModelProperty("车站1/编号")
    private String upstation;

    @ApiModelProperty("车站2/编号")
    private String downstation;

    @ApiModelProperty("上行客流")
    private String upcount;

    @ApiModelProperty("下行客流")
    private String downcount;

    @ApiModelProperty("总客流")
    private String totalcount;

    @ApiModelProperty("断面id")
    private Long sectionId;

    @ApiModelProperty("客流报警密度阈值")
    @JsonProperty("density_alarm")
    private Double densityAlarm;

    @ApiModelProperty("客流警告密度阈值")
    @JsonProperty("density_warning")
    private Double densityWarning;

    @ApiModelProperty("上行客流密度")
    @JsonProperty("up_density")
    private Double upDensity;

    @ApiModelProperty("下行客流密度")
    @JsonProperty("down_density")
    private Double downDensity;

    public String getBusinessday() {
        return businessday;
    }

    public void setBusinessday(String businessday) {
        this.businessday = businessday;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getUpstation() {
        return upstation;
    }

    public void setUpstation(String upstation) {
        this.upstation = upstation;
    }

    public String getDownstation() {
        return downstation;
    }

    public void setDownstation(String downstation) {
        this.downstation = downstation;
    }

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

    public Double getDensityAlarm() {
        return densityAlarm;
    }

    public void setDensityAlarm(Double densityAlarm) {
        this.densityAlarm = densityAlarm;
    }

    public Double getDensityWarning() {
        return densityWarning;
    }

    public void setDensityWarning(Double densityWarning) {
        this.densityWarning = densityWarning;
    }

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
}
