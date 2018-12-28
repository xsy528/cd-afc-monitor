package com.insigma.afc.monitor.map;

import javax.xml.bind.annotation.XmlAttribute;

import com.insigma.afc.entity.convertor.DeviceTypeConvertor;
import com.insigma.commons.ui.anotation.ColumnView;

public class DeviceNodeQueryItem {

	@XmlAttribute
	Short lineId;

	@XmlAttribute
	String lineName;

	@XmlAttribute
	Integer stationId;

	@XmlAttribute
	@ColumnView(name = "站点名称")
	private String stationName;

	@XmlAttribute
	@ColumnView(name = "设备名称")
	private String deviceName;

	@XmlAttribute
	@ColumnView(name = "设备节点", el = "$f{%09d}")
	private Long deviceNode;

	@XmlAttribute
	@ColumnView(name = "设备类型", convertor = DeviceTypeConvertor.class)
	private Short deviceType;

	// "子类型"
	@XmlAttribute
	private Short deviceSubType;

	// "地图设备子类型"
	@XmlAttribute
	private Integer subMapType;

	// 逻辑地址
	@XmlAttribute
	private Integer logicAddress;

	@XmlAttribute
	// @ColumnView(name= "使用状态", convertor = DevStatusValueConvertor.class)
	private Short status;

	@XmlAttribute
	// @ColumnView(name= "IP地址")
	private String ipAddress;

	@XmlAttribute
	@ColumnView(name = "横坐标")
	private Integer posX;

	@XmlAttribute
	@ColumnView(name = "纵坐标")
	private Integer posY;

	@XmlAttribute
	@ColumnView(name = "角度")
	private Integer angle;

	@XmlAttribute
	String picName;

	@XmlAttribute
	Integer mapType;

	@XmlAttribute
	@ColumnView(name = "备注")
	private String remark;

	@XmlAttribute
	private Integer textPosX;

	@XmlAttribute
	private Integer textPosY;

	@XmlAttribute
	private Integer textAngle;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Long getDeviceNode() {
		return deviceNode;
	}

	public void setDeviceNode(Long deviceNode) {
		this.deviceNode = deviceNode;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public Integer getLogicAddress() {
		return logicAddress;
	}

	public void setLogicAddress(Integer logicAddress) {
		this.logicAddress = logicAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Integer getTextPosX() {
		return textPosX;
	}

	public void setTextPosX(Integer textPosX) {
		this.textPosX = textPosX;
	}

	public Integer getTextPosY() {
		return textPosY;
	}

	public void setTextPosY(Integer textPosY) {
		this.textPosY = textPosY;
	}

	public Integer getTextAngle() {
		return textAngle;
	}

	public void setTextAngle(Integer textAngle) {
		this.textAngle = textAngle;
	}

	public Integer getSubMapType() {
		return subMapType;
	}

	public void setSubMapType(Integer subMapType) {
		this.subMapType = subMapType;
	}
}
