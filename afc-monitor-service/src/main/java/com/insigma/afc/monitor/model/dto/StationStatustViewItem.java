package com.insigma.afc.monitor.model.dto;


import java.io.Serializable;
import java.util.Date;

public class StationStatustViewItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Short lineId;

	private Integer stationId;

	private Long nodeId;

	private Short nodeType;

	private Boolean isOnline;

	private Short status;

	private Long mode;

	private Integer normalEvent;

	private Integer warnEvent;

	private Integer alarmEvent;

	private Date updateTime;

	private Date StationStatusUpdateTime;

	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Short getNodeType() {
		return nodeType;
	}

	public void setNodeType(Short nodeType) {
		this.nodeType = nodeType;
	}

	public Boolean getOnline() {
		return isOnline;
	}

	public void setOnline(Boolean online) {
		isOnline = online;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getMode() {
		return mode;
	}

	public void setMode(Long mode) {
		this.mode = mode;
	}

	public Integer getNormalEvent() {
		return normalEvent;
	}

	public void setNormalEvent(Integer normalEvent) {
		this.normalEvent = normalEvent;
	}

	public Integer getWarnEvent() {
		return warnEvent;
	}

	public void setWarnEvent(Integer warnEvent) {
		this.warnEvent = warnEvent;
	}

	public Integer getAlarmEvent() {
		return alarmEvent;
	}

	public void setAlarmEvent(Integer alarmEvent) {
		this.alarmEvent = alarmEvent;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getStationStatusUpdateTime() {
		return StationStatusUpdateTime;
	}

	public void setStationStatusUpdateTime(Date stationStatusUpdateTime) {
		StationStatusUpdateTime = stationStatusUpdateTime;
	}
}
