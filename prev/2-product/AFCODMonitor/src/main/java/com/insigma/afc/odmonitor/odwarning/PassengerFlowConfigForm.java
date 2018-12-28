package com.insigma.afc.odmonitor.odwarning;

import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

public class PassengerFlowConfigForm {

	@View(label = "警告阀值", type = "Spinner")
	private Integer warning = 12;

	@View(label = "报警阀值", type = "Spinner")
	private Integer alarm = 200;

	@View(label = "广播模式", type = "RadioBox")
	@DataSource(list = { "确认广播", "自动广播" })
	private Integer broadcastmode = 0;

	@View(label = "刷新周期", type = "Spinner")
	private Integer interval = 200;

	public PassengerFlowConfigForm() {
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

	public Integer getBroadcastmode() {
		return broadcastmode;
	}

	public void setBroadcastmode(Integer broadcastmode) {
		this.broadcastmode = broadcastmode;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
}
