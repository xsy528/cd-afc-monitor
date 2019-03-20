package com.insigma.afc.monitor.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *Ticket: 模式日志信息
 *
 * @author xingshaoya
 * 2019-03-13:11:57
 */
@ApiModel("模式日志信息")
public class ModeCmdInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //显示结果： "发送时间", "操作员姓名/编号", "车站/编号", "模式名称", "发送结果/编号"

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private String uploadTime;

    @ApiModelProperty("操作员姓名/编号")
    @JsonProperty("operator_id")
    private String operatorId;

    @ApiModelProperty("车站名称")
    @JsonProperty("station_name")
    private String stationName;

    @ApiModelProperty("车站编号")
    @JsonProperty("station_id")
    private int stationId;

    @ApiModelProperty("模式名称")
    @JsonProperty("cmd_name")
    private String cmdName;

    @ApiModelProperty("发送结果")
    @JsonProperty("cmd_result")
    private String cmdResult;

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(String cmdResult) {
        this.cmdResult = cmdResult;
    }

    @Override
    public String toString() {
        return "ModeCmdInfo{" +
                "uploadTime='" + uploadTime + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationId=" + stationId +
                ", cmdName='" + cmdName + '\'' +
                ", cmdResult='" + cmdResult + '\'' +
                '}';
    }
}
