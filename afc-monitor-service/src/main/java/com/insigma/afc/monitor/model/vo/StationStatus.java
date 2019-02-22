package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 车站状态数据类
 */
@ApiModel("车站状态")
public class StationStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("车站名称")
	private String name;

	@ApiModelProperty("车站状态")
	private String status;

	@ApiModelProperty("车站模式")
	private String mode;

	@ApiModelProperty("正常事件数量")
	@JsonProperty("normal_event")
	private Integer normalEvent;

	@ApiModelProperty("警告事件数量")
	@JsonProperty("warn_event")
	private Integer warnEvent;

	@ApiModelProperty("报警事件数量")
	@JsonProperty("alarm_event")
	private Integer alarmEvent;

	@ApiModelProperty("更新时间")
	@JsonProperty("update_time")
	private String updateTime;

	@ApiModelProperty("收否在线")
	@JsonProperty("is_online")
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
