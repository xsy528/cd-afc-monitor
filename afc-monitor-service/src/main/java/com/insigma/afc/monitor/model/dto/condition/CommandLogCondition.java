package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;

/**
 * 命令日志查询条件实体类
 * @author  xingshaoya
 */
@ApiModel(description = "命令日志查询条件")
public class CommandLogCondition extends PageBean {

    private static final long serialVersionUID = 1L;
    //路线、站点，节点并在开始时间和结束时间内，如果有操作员编号，要包含操作员编号，如果有日志类型要包含日志类型,如果有命令结果要包含命令结果。

    @ApiModelProperty("线路id数组")
    @JsonProperty("line_ids")
    protected Short[] lineIds;

    @ApiModelProperty("车站id数组")
    @JsonProperty("station_ids")
    private Integer[] stationIds;

    @ApiModelProperty("节点id数组")
    @JsonProperty("node_ids")
    private Long[] nodeIds;
    //默认为null

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

    public Short[] getLineIds() {
        return lineIds;
    }

    public void setLineIds(Short[] lineIds) {
        this.lineIds = lineIds;
    }

    public Integer[] getStationIds() {
        return stationIds;
    }

    public void setStationIds(Integer[] stationIds) {
        this.stationIds = stationIds;
    }

    public Long[] getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(Long[] nodeIds) {
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
                "lineIds=" + Arrays.toString(lineIds) +
                ", stationIds=" + Arrays.toString(stationIds) +
                ", nodeIds=" + Arrays.toString(nodeIds) +
                ", operatorId='" + operatorId + '\'' +
                ", logType='" + logType + '\'' +
                ", commandResult='" + commandResult + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
