package com.insigma.afc.monitor.listview.event;

import com.insigma.afc.monitor.listview.FilterForm;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class EventFilterForm extends FilterForm {

	private static final long serialVersionUID = 940049848311517032L;

	public static final String FORM_KEY = "FORM_EVENT_CONDITION";

	private Date startTime;

	private Date endTime;

	private Integer pageSize = 20;

	private List eventLevelList;

	private List equTypeList;

	public EventFilterForm() {
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List getEquTypeList() {
		return equTypeList;
	}

	public void setEquTypeList(List equTypeList) {
		this.equTypeList = equTypeList;
	}

	public List getEventLevelList() {
		return eventLevelList;
	}

	public void setEventLevelList(List eventLevelList) {
		this.eventLevelList = eventLevelList;
	}
}
