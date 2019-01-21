package com.insigma.afc.monitor.listview.equstatus;

import com.insigma.afc.topology.AFCNode;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.convert.DateTimeConvertor;

import java.util.Date;

public class StationStatustViewItem extends AFCNode {
	private static final long serialVersionUID = 1885333299581941692L;

	private boolean isOnline;

	private int status;

	private long mode;

	@ColumnView(name = "正常设备(个)", sortAble = false)
	private int normalEvent;

	@ColumnView(name = "警告设备(个)", sortAble = false)
	private int warnEvent;

	@ColumnView(name = "报警设备(个)", sortAble = false)
	private int alarmEvent;

	@ColumnView(name = "模式生效时间", convertor = DateTimeConvertor.class, sortAble = false)
	private Date updateTime;

	private Date StationStatusUpdateTime;

	public StationStatustViewItem() {
	}

	public short getLineId() {
		return lineId;
	}

	public void setLineId(short lineId) {
		this.lineId = lineId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public long getMode() {
		return mode;
	}

	public void setMode(long mode) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
