package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-02:09:51
 */
@ApiModel("设备状态")
public class EquStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备节点id")
    @JsonProperty("node_id")
    private Long nodeId;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("状态描述")
    private String status;

    @ApiModelProperty("状态更新时间")
    @JsonProperty("update_time")
    private String updateTime;

    @ApiModelProperty("设备是否在线")
    @JsonProperty("is_online")
    private boolean isOnline;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
