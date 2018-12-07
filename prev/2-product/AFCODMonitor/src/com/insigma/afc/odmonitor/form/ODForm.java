package com.insigma.afc.odmonitor.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流柱状图form
 * 
 * @author 廖红自
 */
public class ODForm {

	@View(label = "选择日期 ", type = "Date")
	protected Date date;

	@View(label = "选择时段 ", type = "TimeSectionV2")
	protected String time;

	private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

	private int startTimeIndex;

	private int endTimeIndex;

	private Integer[] stationId;

	private String[] partNames;

	private int timeInterval = 5;

	public ODForm() {

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer[] getStationId() {
		return stationId;
	}

	public void setStationId(Integer[] stationId) {
		this.stationId = stationId;
	}

	public int getStartTimeIndex() {
		return startTimeIndex;
	}

	public void setStartTimeIndex(int startTimeIndex) {
		this.startTimeIndex = startTimeIndex;
	}

	public int getEndTimeIndex() {
		return endTimeIndex;
	}

	public void setEndTimeIndex(int endTimeIndex) {
		this.endTimeIndex = endTimeIndex;
	}

	public String[] getPartNames() {
		return partNames;
	}

	public void setPartNames(String[] partNames) {
		this.partNames = partNames;
	}

	public Map<Integer, String> getStationNameMap() {
		return stationNameMap;
	}

	public void setStationNameMap(Map<Integer, String> stationNameMap) {
		this.stationNameMap = stationNameMap;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
}