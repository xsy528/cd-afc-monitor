package com.insigma.afc.log.form;

import java.util.Date;

import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

public class LogInfo {

	@View(label = "起始时间", type = "DateTime")
	private Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime")
	private Date endTime = DateTimeUtil.getCurrentEndDate();

	@View(label = "操作员编号", checkable = true, regexp = "AnonymousUser|\\d{1,10}")
	@Validation(length = 18, describ = "请输入最多18位的数字")
	private String operatorId;

	// @View(label = "日志类型", type = "Combo", list = { "登录日志", "操作日志", "退出日志",
	// "启动日志", "日始日终日志", "文件流水号审计日志", "收益管理日志", "数据库日志" }, checkable = true)
	// private Integer logType;

	@View(label = "日志等级", type = "Combo", checkable = true)
	@DataSource(provider = LogComboProvider.class)
	private Short logClass;

	@View(label = "日志描述", checkable = true, regexp = ".{0,100}")
	private String logDesc;

	public LogInfo() {

	}

	public LogInfo(Date startTime, Date endTime, String operatorId, Short logClass, String logDesc) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.operatorId = operatorId;
		// this.logType = logType;
		this.logClass = logClass;
		this.logDesc = logDesc;
	}

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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	// public Integer getLogType() {
	// return logType;
	// }
	//
	// public void setLogType(Integer logType) {
	// this.logType = logType;
	// }

	public Short getLogClass() {
		return logClass;
	}

	public void setLogClass(Short logClass) {
		this.logClass = logClass;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

}
