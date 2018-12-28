/* 
 * 日期：2017年8月10日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.search;

import java.util.Date;
import java.util.List;

import com.insigma.afc.provider.EventLevelButtonGroupProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 设备事件查询Form
 * @author  mengyifan
 *
 */
public class DeviceEventSearchForm {

	private static final long serialVersionUID = 7210146358996669769L;

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true, checked = true)
	protected Date endTime = DateTimeUtil.getCurrentEndDate();

	//	@View(label = "事件等级", type = "ButtonGroup")
	@DataSource(provider = EventLevelButtonGroupProvider.class)
	private List<Integer> eventType;

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

	public List<Integer> getEventType() {
		return eventType;
	}

	public void setEventType(List<Integer> eventType) {
		this.eventType = eventType;
	}

}
