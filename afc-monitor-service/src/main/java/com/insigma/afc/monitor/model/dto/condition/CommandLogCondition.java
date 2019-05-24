package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.commons.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 命令日志查询条件实体类
 * @author  xingshaoya
 */
@ApiModel(description = "命令日志查询条件")
public class CommandLogCondition extends PageBean {

    @ApiModelProperty("节点ID")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("操作员ID")
    @JsonProperty("operator_id")
    private String operatorId;

    @ApiModelProperty("日志类型")
    @JsonProperty("log_type")
    private String logType;

    @ApiModelProperty("命令结果")
    @JsonProperty("command_result")
    private String commandResult;

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getCommandResult() {
        return commandResult;
    }

    public void setCommandResult(String commandResult) {
        this.commandResult = commandResult;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CommandLogCondition{" +
                "nodeIds=" + nodeIds +
                ", operatorId='" + operatorId + '\'' +
                ", logType='" + logType + '\'' +
                ", commandResult='" + commandResult + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
