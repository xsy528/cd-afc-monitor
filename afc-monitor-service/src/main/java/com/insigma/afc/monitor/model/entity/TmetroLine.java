package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 14:38.
 * @Ticket :
 */
@Entity
@Table(name = "TMETRO_LINE")
public class TmetroLine {
    private short lineId;
    private String lineName;
    private String ipAddress;
    private short status;
    private String remark;
    private String picName;
    private Long imageX;
    private Long imageY;
    private Long imageAngle;
    private Long textX;
    private Long textY;
    private Long textAngle;
    private String extJsonValue;
    private Boolean isVirtual;

    @Id
    @Column(name = "LINE_ID", nullable = false, precision = 0)
    public short getLineId() {
        return lineId;
    }

    public void setLineId(short lineId) {
        this.lineId = lineId;
    }

    @Basic
    @Column(name = "LINE_NAME", nullable = false, length = 32)
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Basic
    @Column(name = "IP_ADDRESS", nullable = true, length = 32)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Basic
    @Column(name = "STATUS", nullable = false, precision = 0)
    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "PIC_NAME", nullable = false, length = 32)
    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    @Basic
    @Column(name = "IMAGE_X", nullable = true, precision = 0)
    public Long getImageX() {
        return imageX;
    }

    public void setImageX(Long imageX) {
        this.imageX = imageX;
    }

    @Basic
    @Column(name = "IMAGE_Y", nullable = true, precision = 0)
    public Long getImageY() {
        return imageY;
    }

    public void setImageY(Long imageY) {
        this.imageY = imageY;
    }

    @Basic
    @Column(name = "IMAGE_ANGLE", nullable = true, precision = 0)
    public Long getImageAngle() {
        return imageAngle;
    }

    public void setImageAngle(Long imageAngle) {
        this.imageAngle = imageAngle;
    }

    @Basic
    @Column(name = "TEXT_X", nullable = true, precision = 0)
    public Long getTextX() {
        return textX;
    }

    public void setTextX(Long textX) {
        this.textX = textX;
    }

    @Basic
    @Column(name = "TEXT_Y", nullable = true, precision = 0)
    public Long getTextY() {
        return textY;
    }

    public void setTextY(Long textY) {
        this.textY = textY;
    }

    @Basic
    @Column(name = "TEXT_ANGLE", nullable = true, precision = 0)
    public Long getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(Long textAngle) {
        this.textAngle = textAngle;
    }

    @Basic
    @Column(name = "EXT_JSON_VALUE", nullable = true, length = 3000)
    public String getExtJsonValue() {
        return extJsonValue;
    }

    public void setExtJsonValue(String extJsonValue) {
        this.extJsonValue = extJsonValue;
    }

    @Basic
    @Column(name = "IS_VIRTUAL", nullable = true, precision = 0)
    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TmetroLine that = (TmetroLine) o;
        return lineId == that.lineId &&
                status == that.status &&
                Objects.equals(lineName, that.lineName) &&
                Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(picName, that.picName) &&
                Objects.equals(imageX, that.imageX) &&
                Objects.equals(imageY, that.imageY) &&
                Objects.equals(imageAngle, that.imageAngle) &&
                Objects.equals(textX, that.textX) &&
                Objects.equals(textY, that.textY) &&
                Objects.equals(textAngle, that.textAngle) &&
                Objects.equals(extJsonValue, that.extJsonValue) &&
                Objects.equals(isVirtual, that.isVirtual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineId, lineName, ipAddress, status, remark, picName, imageX, imageY, imageAngle, textX, textY, textAngle, extJsonValue, isVirtual);
    }
}
