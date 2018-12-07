package com.insigma.afc.monitor.dialog.monitor;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.IColorItem;

public class DeviceEventViewItem implements IColorItem {

	@View(label = "序号")
	private String deviceBoxInfo = "";

	@View(label = "发生时间")
	private String occurTime = "";

	@View(label = "事件名称")
	private String tagName = "";

	@View(label = "事件值")
	private String tagValue = "";

	@View(label = "事件等级")
	private String strStatusLevel = "";

	@View(label = "事件描述")
	private String evtDesc = "";

	private int statusLevel = 0;

	public String getDeviceBoxInfo() {
		return deviceBoxInfo;
	}

	public void setDeviceBoxInfo(String deviceBoxInfo) {
		this.deviceBoxInfo = deviceBoxInfo;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public int getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(int statusLevel) {
		this.statusLevel = statusLevel;
	}

	public String getEvtDesc() {
		return evtDesc;
	}

	public void setEvtDesc(String evtDesc) {
		this.evtDesc = evtDesc;
	}

	public String getStrStatusLevel() {
		return strStatusLevel;
	}

	public void setStrStatusLevel(String strStatusLevel) {
		this.strStatusLevel = strStatusLevel;
	}

	public Color getBackgound() {
		return null;
	}

	public Color getForeground() {
		if (statusLevel >= 2) {
			return ColorConstants.COLOR_ERROR;
		} else if (statusLevel >= 1) {
			return ColorConstants.COLOR_WARN;
		} else {
			return ColorConstants.COLOR_NORMAL;
		}
	}

}
