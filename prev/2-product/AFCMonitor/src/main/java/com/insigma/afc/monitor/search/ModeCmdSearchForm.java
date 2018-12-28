/* 
 * 日期：2010-12-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.search;

import java.util.Date;

import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

public class ModeCmdSearchForm {

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true)
	protected Date endTime;

	@View(label = "操作员编号", type = "Text", checkable = true, regexp = "\\d{0,10}")
	private String operatorId;

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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
