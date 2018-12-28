package com.insigma.afc.monitor.listview.equstatus;

import java.io.Serializable;
import java.util.Date;

import com.insigma.afc.entity.convertor.AFCDeviceStatusConvertor;
import com.insigma.afc.monitor.entity.convertor.EquStatusRowColorConvertor;
import com.insigma.afc.topology.AFCNode;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.TableView;
import com.insigma.commons.ui.convert.DateTimeConvertor;

@TableView(colorConvertor = EquStatusRowColorConvertor.class)
public class EquStatusViewItem extends AFCNode implements Serializable {

	private boolean isOnline;

	@ColumnView(name = "设备状态", convertor = AFCDeviceStatusConvertor.class, sortAble = false)
	//0正常 1警告 2报警 4离线 0xff停止服务
	private int status;

	//	@ColumnView(name = "正常事件")
	private String normalEvent;

	//	@ColumnView(name = "警告事件")
	private String warnEvent;

	//	@ColumnView(name = "报警事件")
	private String alarmEvent;

	@ColumnView(name = "状态更新时间", convertor = DateTimeConvertor.class, sortAble = false)
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
