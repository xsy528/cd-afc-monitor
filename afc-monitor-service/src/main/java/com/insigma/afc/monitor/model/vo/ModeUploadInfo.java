package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket: 模式上传信息
 *
 * @author xuzhemin
 * 2019-01-03:11:57
 */
@ApiModel("模式上传信息")
public class ModeUploadInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("线路名称")
    @JsonProperty("line_name")
    private String lineName;

    @ApiModelProperty("车站名称")
    @JsonProperty("station_name")
    private String stationName;

    @ApiModelProperty("模式")
    @JsonProperty("mode")
    private String mode;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private String uploadTime;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "ModeUploadInfo{" +
                "lineName='" + lineName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", mode='" + mode + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }
}
