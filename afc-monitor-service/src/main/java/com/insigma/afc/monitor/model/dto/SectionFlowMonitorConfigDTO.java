/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 13:34
 */
@ApiModel("断面客流监控配置")
public class SectionFlowMonitorConfigDTO {

    public SectionFlowMonitorConfigDTO() {
    }

    public SectionFlowMonitorConfigDTO(Integer warning, Integer alarm) {
        this.warning = warning;
        this.alarm = alarm;
    }

    @NotNull
    @Min(1)
    @ApiModelProperty("客流密度警告阈值[人次/分钟]")
    private Integer warning;

    @NotNull
    @Min(1)
    @ApiModelProperty("客流密度报警阈值[人次/分钟]")
    private Integer alarm;

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }
}
