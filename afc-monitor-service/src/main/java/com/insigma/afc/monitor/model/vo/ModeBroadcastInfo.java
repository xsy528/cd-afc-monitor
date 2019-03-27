package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket: 模式广播信息
 *
 * @author xuzhemin
 * 2019-01-03:14:56
 */
@ApiModel(description = "模式广播信息")
public class ModeBroadcastInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("记录id")
    @JsonProperty("record_id")
    private Long recordId;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("上传源车站名称")
    @JsonProperty("source_name")
    private String sourceName;

    @ApiModelProperty("模式")
    @JsonProperty("mode")
    private String mode;

    @ApiModelProperty("广播时间")
    @JsonProperty("mode_broadcast_time")
    private String modeBroadcastTime;

    @ApiModelProperty("模式上传时间")
    @JsonProperty("mode_effect_time")
    private String modeEffectTime;

    @ApiModelProperty("目的车站名称")
    @JsonProperty("target_name")
    private String targetName;

    @ApiModelProperty("广播方式")
    @JsonProperty("mode_broadcast_type")
    private String modeBroadcastType;

    @ApiModelProperty("广播状态")
    @JsonProperty("mode_broadcast_status")
    private String modeBroadcastStatus;

    @ApiModelProperty("操作员编号")
    @JsonProperty("operator_id")
    private String operatorId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getModeEffectTime() {
        return modeEffectTime;
    }

    public void setModeEffectTime(String modeEffectTime) {
        this.modeEffectTime = modeEffectTime;
    }

    public String getModeBroadcastType() {
        return modeBroadcastType;
    }

    public void setModeBroadcastType(String modeBroadcastType) {
        this.modeBroadcastType = modeBroadcastType;
    }

    public String getModeBroadcastStatus() {
        return modeBroadcastStatus;
    }

    public void setModeBroadcastStatus(String modeBroadcastStatus) {
        this.modeBroadcastStatus = modeBroadcastStatus;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeBroadcastTime() {
        return modeBroadcastTime;
    }

    public void setModeBroadcastTime(String modeBroadcastTime) {
        this.modeBroadcastTime = modeBroadcastTime;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
