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

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/29 16:16
 */
@ApiModel("断面客流数据")
public class SectionMonitorDTO {

    @ApiModelProperty("客流报警密度阈值")
    @JsonProperty("density_alarm")
    private Double densityAlarm;

    @ApiModelProperty("客流警告密度阈值")
    @JsonProperty("density_warning")
    private Double densityWarning;

    @ApiModelProperty("断面密度数组")
    private List<SectionMonitorDataDTO> sections;

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

    public List<SectionMonitorDataDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionMonitorDataDTO> sections) {
        this.sections = sections;
    }
}
