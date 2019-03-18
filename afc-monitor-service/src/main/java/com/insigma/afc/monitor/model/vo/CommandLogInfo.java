package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "命令日志信息")
public class CommandLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    //节点名称/编码，命令名称,操作员名称/编号，发送时间，命令结果/应答码
    @ApiModelProperty("节点名称")
    @JsonProperty("node_name")
    private String nodeName;

    @ApiModelProperty("节点ID")
    @JsonProperty("node_id")
    private long nodeId;

    @ApiModelProperty("命令名称")
    @JsonProperty("cmd_name")
    private String cmdName;

    @ApiModelProperty("操作员编号")
    @JsonProperty("operator_id")
    private String operatotId;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private String uploadTime;

    @ApiModelProperty("命令结果")
    @JsonProperty("cmd_result")
    private String cmdResult;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getOperatotId() {
        return operatotId;
    }

    public void setOperatotId(String operatotId) {
        this.operatotId = operatotId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(String cmdResult) {
        this.cmdResult = cmdResult;
    }

    @Override
    public String toString() {
        return "CommandLogInfo{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", cmdName='" + cmdName + '\'' +
                ", operatotId='" + operatotId + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", cmdResult='" + cmdResult + '\'' +
                '}';
    }
}
