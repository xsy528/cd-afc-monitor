package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/28 10:17.
 * @Ticket :
 */
@ApiModel("客流监控配置")
public class PassengerFlowConfigDTO {
    public PassengerFlowConfigDTO() {
    }

    public PassengerFlowConfigDTO(@NotNull @Min(10) Integer refresh) {
        this.refresh = refresh;
    }

    @NotNull
    @Min(10)
    @Max(600)
    @ApiModelProperty("客流刷新频率[秒/次]")
    private Integer refresh;

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }
}
