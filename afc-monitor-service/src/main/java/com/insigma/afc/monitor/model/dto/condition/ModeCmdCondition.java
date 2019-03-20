package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.afc.monitor.model.dto.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 模式日志查询条件
 * @author  xingshaoya
 */
public class ModeCmdCondition extends PageBean {

    private static final long serialVersionUID = 1L;
    //站点，操作员ID，指令结果，开始时间，结束时间，null，指令类型，页数，页数大小

    @ApiModelProperty("节点ID")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("站点ID")
    @JsonProperty("station_ids")
    private List<Integer> stationIds;

    @ApiModelProperty("路线ID")
    @JsonProperty("line_ids")
    private List<Short> lineIds;
    //默认为null

    @ApiModelProperty("操作员ID")
    @JsonProperty("operator_id")
    private String operatorId;

    @ApiModelProperty("指令结果")
    @JsonProperty("cmd_result")
    private Integer cmdResult;
    //默认为null

    @ApiModelProperty("开始时间")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("end_time")
    private Date endTime;

    @ApiModelProperty("结果是否成功")
    @JsonProperty("success_result")
    private Boolean successResult;
    //默认为null

    @ApiModelProperty("指令类型")
    @JsonProperty("cmd_type")
    private Short cmdType;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }

    public List<Short> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Short> lineIds) {
        this.lineIds = lineIds;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(Integer cmdResult) {
        this.cmdResult = cmdResult;
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

    public Boolean getSuccessResult() {
        return successResult;
    }

    public void setSuccessResult(Boolean successResult) {
        this.successResult = successResult;
    }

    public Short getCmdType() {
        return cmdType;
    }

    public void setCmdType(Short cmdType) {
        this.cmdType = cmdType;
    }

    @Override
    public String toString() {
        return "ModeCmdCondition{" +
                "nodeIds=" + nodeIds +
                ", stationIds=" + stationIds +
                ", lineIds=" + lineIds +
                ", operatorId='" + operatorId + '\'' +
                ", cmdResult=" + cmdResult +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", successResult=" + successResult +
                ", cmdType=" + cmdType +
                '}';
    }
}
