package com.insigma.afc.monitor.listview.equstatus;

import com.insigma.afc.monitor.listview.FilterForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquStatusFilterForm extends FilterForm {

	public static final String FORM_KEY = "FORM_EQU_STATUS_CONDITION";

	private Date startTime;

	private Date endTime;

	private Integer pageSize = 1000;

	private List<Short> statusLevelList = new ArrayList<Short>();

	private List<Short> equTypeList = new ArrayList<Short>();

	public EquStatusFilterForm() {
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

	public List<Short> getEquTypeList() {
		return equTypeList;
	}

	public List<Short> getStatusLevelList() {
		return statusLevelList;
	}

	public void setStatusLevelList(List<Short> statusLevelList) {
		this.statusLevelList = statusLevelList;
	}
}
