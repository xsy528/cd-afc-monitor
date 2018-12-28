package com.insigma.afc.monitor.listview.event;

import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.listview.FilterForm;
import com.insigma.afc.provider.DeviceTypeProvider;
import com.insigma.commons.ui.anotation.ButtonGroupView;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;

@SuppressWarnings("unchecked")
public class EventFilterForm extends FilterForm {

	private static final long serialVersionUID = 940049848311517032L;

	public static final String FORM_KEY = "FORM_EVENT_CONDITION";

	@View(label = "开始时间", type = "DateTime", checkable = true, nullvalue = "currentBeginDate")
	private Date startTime;

	@View(label = "结束时间", type = "DateTime", checkable = true)
	private Date endTime;

	@View(label = "事件条数", type = "Spinner")
	@Validation(maxValue = 10000)
	private Integer pageSize = 20;

	//	@View(label = "事件等级", type = "ButtonGroup")
	//	@DataSource(provider = EventLevelButtonGroupProvider.class)
	private List eventLevelList;

	@View(label = "设备类型", type = "CheckBox")
	@DataSource(provider = DeviceTypeProvider.class)
	@ButtonGroupView(numColumns = 2)
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
