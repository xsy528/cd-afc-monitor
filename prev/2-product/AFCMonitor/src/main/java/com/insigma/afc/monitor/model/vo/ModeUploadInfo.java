package com.insigma.afc.monitor.model.vo;

import java.io.Serializable;

/**
 * Ticket: 模式上传信息
 *
 * @author xuzhemin
 * 2019-01-03:11:57
 */
public class ModeUploadInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lineName;
    private String stationName;
    private String mode;
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
