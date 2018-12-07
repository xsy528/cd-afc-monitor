package com.insigma.afc.odmonitor.odwarning;

import com.insigma.commons.ui.anotation.View;

public class StationConfigForm {

	@View(label = "客流刷新周期", type = "Spinner")
	private Integer interval;

	@View(label = "进站客流警告阀值", type = "Spinner")
	private Integer inwarning;

	@View(label = "出站客流警告阀值", type = "Spinner")
	private Integer outwarning;

	@View(label = "客流合计警告阀值", type = "Spinner")
	private Integer totalwarning;

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getInwarning() {
		return inwarning;
	}

	public void setInwarning(Integer inwarning) {
		this.inwarning = inwarning;
	}

	public Integer getOutwarning() {
		return outwarning;
	}

	public void setOutwarning(Integer outwarning) {
		this.outwarning = outwarning;
	}

	public Integer getTotalwarning() {
		return totalwarning;
	}

	public void setTotalwarning(Integer totalwarning) {
		this.totalwarning = totalwarning;
	}

}
