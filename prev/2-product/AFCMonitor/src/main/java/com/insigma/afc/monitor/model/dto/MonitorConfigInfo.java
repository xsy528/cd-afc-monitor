package com.insigma.afc.monitor.model.dto;

import java.io.Serializable;

public class MonitorConfigInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer warning = 12;

	private Integer alarm = 200;

	private Integer interval = 30;

	public MonitorConfigInfo() {
	}

	public MonitorConfigInfo(Integer warning, Integer alarm, Integer interval) {
		this.warning = warning;
		this.alarm = alarm;
		this.interval = interval;
	}

	public Integer getWarning() {
		return warning;
	}

	public void setWarning(Integer warning) {
		this.warning = warning;
	}

	public Integer getAlarm() {
		return alarm;
	}

	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
}
