package com.insigma.afc.log.form;

import java.util.Date;

import com.insigma.commons.ui.anotation.View;

public class LogDescInfo {

	@View(label = "模块", modify = false)
	private String moduleName;

	@View(label = "IP地址", modify = false)
	private String addressIp;

	@View(label = "时间", modify = false)
	private Date dateTime;

	@View(label = "描述", modify = false)
	private String logDesc;

	public LogDescInfo() {
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getAddressIp() {
		return addressIp;
	}

	public void setAddressIp(String addressIp) {
		this.addressIp = addressIp;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

}
