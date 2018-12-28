package com.insigma.acc.wz.monitor.form;

import java.util.Date;

import com.insigma.acc.wz.provider.WZModeCodeProvider;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.commons.annotation.Condition;
import com.insigma.commons.annotation.QueryClass;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

@QueryClass(queryClass = TmoModeUploadInfo.class)
public class WZModeUploadForm {

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	@Condition(column = "modeUploadTime", compareType = ">=")
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true)
	@Condition(column = "modeUploadTime", compareType = "<=")
	protected Date endTime;

	@View(label = "进入的模式", checkable = true, type = "Combo")
	@DataSource(provider = WZModeCodeProvider.class)
	@Condition(column = "broadcastStatus", compareType = "=")
	private Short modeCode;

	// @View(label = "广播状态", checkable = true, type = "Combo", list = { "未广播",
	// "已广播" }, values = { "0", "1" })
	private Short broadCastStatus;

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

	public Short getBroadCastStatus() {
		return broadCastStatus;
	}

	public void setBroadCastStatus(Short broadCastStatus) {
		this.broadCastStatus = broadCastStatus;
	}

	public Short getModeCode() {
		return modeCode;
	}

	public void setModeCode(Short modeCode) {
		this.modeCode = modeCode;
	}
}