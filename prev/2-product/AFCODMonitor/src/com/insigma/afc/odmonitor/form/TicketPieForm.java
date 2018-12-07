package com.insigma.afc.odmonitor.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.insigma.commons.lang.DateSpan;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流饼图form
 * 
 * @author 廖红自
 */
public class TicketPieForm {

	@View(label = "选择时间 ", type = "DateTimeSpan")
	protected DateSpan dateTimeSpan;

	@View(label = "交易类型 ", type = "BitGroup")
	@DataSource(list = { "进站", "出站", "购票", "充值" })
	protected Integer transType = 0xff;

	private Date startDate;

	private Date endDate;

	private Integer[] stationId;

	private int startTimeIndex;

	private int endTimeIndex;

	private String[] partNames;

	private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

	private int timeInterval;

	private int intervalCount = 5;

	public DateSpan getDateTimeSpan() {
		return dateTimeSpan;
	}

	public void setDateTimeSpan(DateSpan dateTimeSpan) {
		this.dateTimeSpan = dateTimeSpan;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public int getIntervalCount() {
		return intervalCount;
	}

	public void setIntervalCount(int intervalCount) {
		this.intervalCount = intervalCount;
	}

}