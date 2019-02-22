package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel("监控参数")
public class MonitorConfigInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("警告阈值")
	private Integer warning = 12;

	@ApiModelProperty("报警阈值")
	private Integer alarm = 200;

	@ApiModelProperty("刷新时间")
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
