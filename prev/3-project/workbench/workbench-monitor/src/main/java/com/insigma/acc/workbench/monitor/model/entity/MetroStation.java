package com.insigma.acc.workbench.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="TMETRO_STATION")
public class MetroStation extends MetroNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
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

    @Column(name="BACK_PIC_NAME")
    private String backPicName;

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

    public String getBackPicName() {
        return backPicName;
    }

    public void setBackPicName(String backPicName) {
        this.backPicName = backPicName;
    }

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

    @Override
    protected void setNodeId() {
        this.nodeId=stationId.toString();
    }

    @Override
    protected void setNodeType() {
        this.nodeType=NodeType.Station.toString();
    }

    @Override
    protected void setName() {
        this.name = stationName;
    }
}
