package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Tiket:各类查询-设备事件
 *
 * @author xingshaoya
 */
@ApiModel("设备事件")
public class DeviceEvent {

    @ApiModelProperty("节点名称")
    @JsonProperty("node_name")
    private String nodeName;

    @ApiModelProperty("节点ID")
    @JsonProperty("node_id")
    private String nodeId;

    @ApiModelProperty("事件名称")
    @JsonProperty("event_name")
    private String eventName;

    @ApiModelProperty("事件描述")
    @JsonProperty("event_desc")
    private String eventDesc;

    @ApiModelProperty("发生时间")
    @JsonProperty("occur_time")
    private String occurTime;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String node) {
        this.nodeId = node;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    @Override
    public String toString() {
        return "DeviceEvent{" +
                "nodeName='" + nodeName + '\'' +
                ", node='" + nodeId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                ", occurTime='" + occurTime + '\'' +
                '}';
    }
}
