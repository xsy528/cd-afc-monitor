package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-03:17:52
 */
@ApiModel("设备事件")
public class EquEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("节点名称")
    @JsonProperty("node_name")
    private String nodeName;

    @ApiModelProperty("状态名称")
    @JsonProperty("status_name")
    private String statusName;

    @ApiModelProperty("状态描述")
    @JsonProperty("status_desc")
    private String statusDesc;

    @ApiModelProperty("应用设备")
    @JsonProperty("apply_device")
    private String applyDevice;

    @ApiModelProperty("发生时间")
    @JsonProperty("occur_time")
    private String occurTime;

    @ApiModelProperty("状态值")
    private Short item;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getApplyDevice() {
        return applyDevice;
    }

    public void setApplyDevice(String applyDevice) {
        this.applyDevice = applyDevice;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public Short getItem() {
        return item;
    }

    public void setItem(Short item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "EquEvent{" +
                "nodeName='" + nodeName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", applyDevice='" + applyDevice + '\'' +
                ", occurTime='" + occurTime + '\'' +
                ", item=" + item +
                '}';
    }
}
