package com.insigma.afc.monitor.model.dto;

import com.insigma.afc.topology.AFCNode;

import java.io.Serializable;
import java.util.Date;

public class EquStatusViewItem extends AFCNode implements Serializable {

	private boolean isOnline;

	//0正常 1警告 2报警 4离线 0xff停止服务
	private int status;

	//	@ColumnView(name = "正常事件")
	private String normalEvent;

	//	@ColumnView(name = "警告事件")
	private String warnEvent;

	//	@ColumnView(name = "报警事件")
	private String alarmEvent;

	private Date updateTime;

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean status) {
		this.isOnline = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
