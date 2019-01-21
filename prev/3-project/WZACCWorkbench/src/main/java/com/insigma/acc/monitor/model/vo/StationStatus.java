package com.insigma.acc.monitor.model.vo;

import java.io.Serializable;

/**
 * 车站状态数据类
 */
public class StationStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String status;

	private String mode;

	private int normalEvent;

	private int warnEvent;

	private int alarmEvent;

	private String updateTime;

	private boolean isOnline;

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean online) {
		isOnline = online;
	}

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

	public int getNormalEvent() {
		return normalEvent;
	}

	public void setNormalEvent(int normalEvent) {
		this.normalEvent = normalEvent;
	}

	public int getWarnEvent() {
		return warnEvent;
	}

	public void setWarnEvent(int warnEvent) {
		this.warnEvent = warnEvent;
	}

	public int getAlarmEvent() {
		return alarmEvent;
	}

	public void setAlarmEvent(int alarmEvent) {
		this.alarmEvent = alarmEvent;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
