package com.insigma.afc.monitor.model.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * 	ticket: 设备视图模型
 *  @author: xuzhemin
 */
public class EquStatusViewItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long nodeId;

	private Short lineId;

	private Integer stationId;

	private Boolean isOnline;

	/**
	 * 0正常 1警告 2报警 4离线 0xff停止服务
	 */
	private Short status;

	private String normalEvent;

	private String warnEvent;

	private String alarmEvent;

	private Date updateTime;

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

	public String getNormalEvent() {
		return normalEvent;
	}

	public void setNormalEvent(String normalEvent) {
		this.normalEvent = normalEvent;
	}

	public String getWarnEvent() {
		return warnEvent;
	}

	public void setWarnEvent(String warnEvent) {
		this.warnEvent = warnEvent;
	}

	public String getAlarmEvent() {
		return alarmEvent;
	}

	public void setAlarmEvent(String alarmEvent) {
		this.alarmEvent = alarmEvent;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
