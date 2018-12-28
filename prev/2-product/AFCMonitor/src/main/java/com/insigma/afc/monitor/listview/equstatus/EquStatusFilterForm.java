package com.insigma.afc.monitor.listview.equstatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.listview.FilterForm;
import com.insigma.afc.provider.DeviceStatusComboProvider;
import com.insigma.afc.provider.DeviceTypeProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;

public class EquStatusFilterForm extends FilterForm {

	public static final String FORM_KEY = "FORM_EQU_STATUS_CONDITION";

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = false, nullvalue = "currentBeginDate")
	private Date startTime;

	@View(label = "结束时间", type = "DateTime", checkable = true)
	private Date endTime;

	//	@View(label = "状态条数", type = "Spinner")
	@Validation(maxValue = 10000)
	private Integer pageSize = 1000;

	@View(label = "状态类型", type = "CheckBox")
	@DataSource(provider = DeviceStatusComboProvider.class)
	private List<Short> statusLevelList = new ArrayList<Short>();

	@DataSource(provider = DeviceTypeProvider.class)
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

}
