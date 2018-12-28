package com.insigma.afc.monitor.search;

import java.util.Date;

import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.ui.widgets.provider.AFCMetroStationsProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

public class ModeBroadCastInfoForm {

	private static final long serialVersionUID = 2923200887046240022L;

	@View(label = "模式上传开始时间：    ", type = "DateTime", checkable = true, checked = true)
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "模式上传结束时间：    ", type = "DateTime", checkable = true)
	protected Date endTime;

	@View(label = "进入的模式名称", checkable = true, type = "Combo")
	@DataSource(provider = AFCModeCode.class)
	private Short modeCode;

	@View(label = "操作员编号", checkable = true, regexp = "\\d{0,10}")
	private String operatorId;

	// @View(label = "广播源线路", checkable = true, type = "Combo")
	// @DataSource(provider = AFCMetroLinesProvider.class)
	// private Short broadcastSourcLines;

	//	@View(label = "模式广播目的线路", checkable = true, type = "Combo")
	//	@DataSource(provider = AFCMetroLinesProvider.class)
	//	private Short broadcastDestLines;

	@View(label = "模式广播目的车站：    ", checkable = true, type = "Combo")
	@DataSource(provider = AFCMetroStationsProvider.class)
	private Short broadcastDestStations;

	@View(label = "模式广播状态", checkable = true, type = "Combo")
	@DataSource(list = { "未确认", "成功", "失败" })
	private Short broadcastStatus;

	@View(label = "模式广播方式", checkable = true, type = "Combo")
	@DataSource(list = { "自动", "手动" })
	private Short broadcastType;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Short getModeCode() {
		return modeCode;
	}

	public void setModeCode(Short modeCode) {
		this.modeCode = modeCode;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the broadcastStatus
	 */
	public Short getBroadcastStatus() {
		return broadcastStatus;
	}

	/**
	 * @param broadcastStatus
	 *            the broadcastStatus to set
	 */
	public void setBroadcastStatus(Short broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	/**
	 * @return the broadcastType
	 */
	public Short getBroadcastType() {
		return broadcastType;
	}

	/**
	 * @param broadcastType
	 *            the broadcastType to set
	 */
	public void setBroadcastType(Short broadcastType) {
		this.broadcastType = broadcastType;
	}

	/**
	 * @return the broadcastDestStations
	 */
	public Short getBroadcastDestStations() {
		return broadcastDestStations;
	}

	/**
	 * @param broadcastDestStations the broadcastDestStations to set
	 */
	public void setBroadcastDestStations(Short broadcastDestStations) {
		this.broadcastDestStations = broadcastDestStations;
	}

}