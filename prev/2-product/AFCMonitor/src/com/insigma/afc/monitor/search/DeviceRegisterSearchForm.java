/* 
 * 日期：2010-9-27
 * 描述（预留） 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.search;

import java.util.Date;

import com.insigma.afc.provider.DeviceTypeProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 设备寄存器查询表单
 * 
 * @author zhengshuquan
 * @date 2010-9-27
 * @description
 */
public class DeviceRegisterSearchForm {

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true)
	protected Date endTime;

	@View(label = "设备类型", type = "Combo", checkable = true)
	@DataSource(provider = DeviceTypeProvider.class)
	private Integer deviceType;

	@View(label = "设备编号(HEX)", checkable = true, regexp = "[0-9]{0,9}", length = 8)
	private String deviceId;

	@View(label = "查询周期", type = "Combo", checkable = true, checked = true)
	@DataSource(list = { "最近三天内", "最近一周内", "最近半月内", "最近一月内" }, values = { "3", "7", "15", "30" })
	private Integer queryPeriod;

	/**
	 * @return the queryPeriod
	 */
	public Integer getQueryPeriod() {
		return queryPeriod;
	}

	/**
	 * @param queryPeriod
	 *            the queryPeriod to set
	 */
	public void setQueryPeriod(Integer queryPeriod) {
		this.queryPeriod = queryPeriod;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the deviceType
	 */
	public Integer getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
