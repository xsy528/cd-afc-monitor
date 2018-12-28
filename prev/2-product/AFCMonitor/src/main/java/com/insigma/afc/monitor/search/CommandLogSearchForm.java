package com.insigma.afc.monitor.search;

import java.util.Date;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCCmdResultType;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.commons.annotation.Condition;
import com.insigma.commons.annotation.QueryClass;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

@QueryClass(queryClass = TmoCmdResult.class, orderby = { "occurTime desc", "stationId asc" })
public class CommandLogSearchForm {

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	@Condition(column = "occurTime", compareType = ">=")
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true)
	@Condition(column = "occurTime", compareType = "<=")
	protected Date endTime;

	@View(label = "操作员编号", checkable = true, regexp = "\\d{0,10}")
	@Condition(column = "operatorId")
	private String operatorId;

	@View(label = "日志类型", type = "Combo", checkable = true)
	@DataSource(provider = AFCCmdLogType.class)
	@Condition(column = "cmdType")
	private Short cmdType;

	@View(label = "发送结果", type = "Combo", checkable = true)
	@DataSource(provider = AFCCmdResultType.class)
	@Condition(column = "cmdResult")
	private Integer cmdResult;

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

	public Integer getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(Integer cmdResult) {
		this.cmdResult = cmdResult;
	}

	/**
	 * @param cmdType
	 *            the cmdType to set
	 */
	public void setCmdType(Short cmdType) {
		this.cmdType = cmdType;
	}

	/**
	 * @return the cmdType
	 */
	public Short getCmdType() {
		return cmdType;
	}

}