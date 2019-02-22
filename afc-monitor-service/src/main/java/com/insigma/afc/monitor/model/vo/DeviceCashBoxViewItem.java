package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket:钱箱查询结果行
 *
 * @author xuzhemin
 * 2019-01-29:10:11
 */
@ApiModel("钱箱查询结果行")
public class DeviceCashBoxViewItem {

    @ApiModelProperty("钱箱信息")
    @JsonProperty("device_cash_box_info")
    private String deviceCashBoxInfo;

    @ApiModelProperty("钱箱值")
    @JsonProperty("device_cash_box_value")
    private String deviceCashBoxValue;

    public String getDeviceCashBoxInfo() {
        return deviceCashBoxInfo;
    }

    public void setDeviceCashBoxInfo(String deviceCashBoxInfo) {
        this.deviceCashBoxInfo = deviceCashBoxInfo;
    }

    public String getDeviceCashBoxValue() {
        return deviceCashBoxValue;
    }

    public void setDeviceCashBoxValue(String deviceCashBoxValue) {
        this.deviceCashBoxValue = deviceCashBoxValue;
    }

}
