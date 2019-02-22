package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Ticket:票箱查询结果行
 *
 * @author xuzhemin
 * 2019-01-29:10:11
 */
@ApiModel("票箱查询结果行")
public class DeviceTicketBoxViewItem {

    @JsonProperty("device_ticket_box_info")
    private String deviceTicketBoxInfo = "";

    @JsonProperty("device_ticket_box_value")
    private String deviceTicketBoxValue = "";

    public String getDeviceTicketBoxInfo() {
        return deviceTicketBoxInfo;
    }

    public void setDeviceTicketBoxInfo(String deviceTicketBoxInfo) {
        this.deviceTicketBoxInfo = deviceTicketBoxInfo;
    }

    public String getDeviceTicketBoxValue() {
        return deviceTicketBoxValue;
    }

    public void setDeviceTicketBoxValue(String deviceTicketBoxValue) {
        this.deviceTicketBoxValue = deviceTicketBoxValue;
    }
}
