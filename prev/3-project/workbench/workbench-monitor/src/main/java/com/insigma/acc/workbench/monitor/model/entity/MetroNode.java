package com.insigma.acc.workbench.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MetroNode {

    protected String nodeId;
    protected String nodeType;
    protected String name;

    abstract protected void setNodeId();
    abstract protected void setNodeType();
    abstract protected void setName();

    enum NodeType{
        ACC,Line,Station,Device
    }

    @Column(name="IMAGE_X")
    protected Integer imageX;

    @Column(name="IMAGE_Y")
    protected Integer imageY;

    @Column(name="IMAGE_ANGLE")
    protected Integer imageAngle;

    @Column(name="TEXT_X")
    protected Integer textX;

    @Column(name="TEXT_Y")
    protected Integer textY;

    @Column(name="TEXT_ANGLE")
    protected Integer textAngle;

    @Column(name="STATUS")
    protected Short status;

    @Column(name="REMARK")
    protected String remark;

    @Column(name="EXT_JSON_VALUE")
    protected String extJsonValue;

    @Column(name="IP_ADDRESS")
    private String ipAddress;

    public String getNodeId(){
        return nodeId;
    }

    public String getNodeType(){
        return nodeType;
    }

    public String getName(){
        return name;
    }

    public Integer getImageX() {
        return imageX;
    }

    public void setImageX(Integer imageX) {
        this.imageX = imageX;
    }

    public Integer getImageY() {
        return imageY;
    }

    public void setImageY(Integer imageY) {
        this.imageY = imageY;
    }

    public Integer getImageAngle() {
        return imageAngle;
    }

    public void setImageAngle(Integer imageAngle) {
        this.imageAngle = imageAngle;
    }

    public Integer getTextX() {
        return textX;
    }

    public void setTextX(Integer textX) {
        this.textX = textX;
    }

    public Integer getTextY() {
        return textY;
    }

    public void setTextY(Integer textY) {
        this.textY = textY;
    }

    public Integer getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(Integer textAngle) {
        this.textAngle = textAngle;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtJsonValue() {
        return extJsonValue;
    }

    public void setExtJsonValue(String extJsonValue) {
        this.extJsonValue = extJsonValue;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
