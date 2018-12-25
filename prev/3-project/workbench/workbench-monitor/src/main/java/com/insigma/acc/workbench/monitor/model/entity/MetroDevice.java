package com.insigma.acc.workbench.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="TMETRO_DEVICE")
public class MetroDevice extends MetroNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="DEVICE_ID")
    private Short deviceId;

    @Column(name="DEVICE_NAME")
    private String deviceName;

    @Column(name="STATION_ID")
    private Short stationId;

    @Column(name="STATION_NAME")
    private String stationName;

    @Column(name="LINE_ID")
    private Short lineId;

    @Column(name="LINE_NAME")
    private String lineName;

    @Column(name="PIC_NAME")
    private String picName;

    @Column(name="DEVICE_TYPE")
    private Short deviceType;

    @Column(name="device_SUB_TYPE")
    private Short deviceSubType;

    @Column(name="LOGIC_ADDRESS")
    private Long logicAddress;

    @Column(name="COMM_PORT")
    private Integer commPort;

    @Column(name="ADDR_MAC")
    private String addrMac;

    public Short getLineId() {
        return lineId;
    }

    public void setLineId(Short lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Short getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Short deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Short getStationId() {
        return stationId;
    }

    public void setStationId(Short stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Short getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Short deviceType) {
        this.deviceType = deviceType;
    }

    public Short getDeviceSubType() {
        return deviceSubType;
    }

    public void setDeviceSubType(Short deviceSubType) {
        this.deviceSubType = deviceSubType;
    }

    public Long getLogicAddress() {
        return logicAddress;
    }

    public void setLogicAddress(Long logicAddress) {
        this.logicAddress = logicAddress;
    }

    public Integer getCommPort() {
        return commPort;
    }

    public void setCommPort(Integer commPort) {
        this.commPort = commPort;
    }

    public String getAddrMac() {
        return addrMac;
    }

    public void setAddrMac(String addrMac) {
        this.addrMac = addrMac;
    }

    @Override
    protected void setNodeId() {
        this.nodeId=deviceId.toString();
    }

    @Override
    protected void setNodeType() {
        this.nodeType=NodeType.Device.toString();
    }

    @Override
    protected void setName() {
        this.name = deviceName;
    }
}
