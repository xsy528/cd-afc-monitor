package com.insigma.afc.monitor.dialog.config;

import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

public class MonitorConfigInfo {

	public static final String IS_AUTO_BROADCAST = SystemConfigKey.IS_AUTO_BROADCAST;

	public static final String WARNING_THRESHHOLD = SystemConfigKey.WARNING_THRESHHOLD;

	public static final String ALARM_THRESHHOLD = SystemConfigKey.ALARM_THRESHHOLD;

	public static final String VIEW_REFRESH_INTERVAL = SystemConfigKey.VIEW_REFRESH_INTERVAL;

	@View(label = "警告阀值[个]", type = "Spinner", regexp = "\\d{1,6}")
	private Integer warning = 12;

	@View(label = "报警阀值[个]", type = "Spinner", regexp = "\\d{1,6}")
	private Integer alarm = 200;

	@View(label = "广播模式", type = "RadioBox")
	@DataSource(list = { "确认广播", "自动广播" })
	private Integer broadcastmode = 0;

	@View(label = "刷新周期[秒]", type = "Spinner", regexp = "\\d{1,6}")
	private Integer interval = 30;

	public MonitorConfigInfo() {
		warning = SystemConfigManager.getConfigItemForInt(WARNING_THRESHHOLD);
		alarm = SystemConfigManager.getConfigItemForInt(ALARM_THRESHHOLD);
		broadcastmode = SystemConfigManager.getConfigItemForInt(IS_AUTO_BROADCAST);
		interval = SystemConfigManager.getConfigItemForInt(VIEW_REFRESH_INTERVAL);
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
