package com.insigma.afc.monitor.model.dto;

import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;

public class MonitorConfigInfo {

	public static final String IS_AUTO_BROADCAST = SystemConfigKey.IS_AUTO_BROADCAST;

	public static final String WARNING_THRESHHOLD = SystemConfigKey.WARNING_THRESHHOLD;

	public static final String ALARM_THRESHHOLD = SystemConfigKey.ALARM_THRESHHOLD;

	public static final String VIEW_REFRESH_INTERVAL = SystemConfigKey.VIEW_REFRESH_INTERVAL;

	private Integer warning = 12;

	private Integer alarm = 200;

	private Integer broadcastmode = 0;

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
