package com.insigma.afc.monitor.model.vo;

import java.io.Serializable;

/**
 * 车站状态数据类
 */
public class StationStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String status;

	private String mode;

	private Integer normalEvent;

	private Integer warnEvent;

	private Integer alarmEvent;

	private String updateTime;

	private Boolean isOnline;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getOnline() {
		return isOnline;
	}

	public void setOnline(Boolean online) {
		isOnline = online;
	}
}
