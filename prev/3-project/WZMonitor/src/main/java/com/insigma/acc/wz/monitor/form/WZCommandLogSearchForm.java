/* 
 * 日期：2017年6月13日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.form;

import java.util.Date;
import java.util.List;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCMackCode;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.commons.annotation.Condition;
import com.insigma.commons.annotation.QueryClass;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 命令日志查询form
 * @author  mengyifan
 *
 */
@QueryClass(queryClass = TmoCmdResult.class, orderby = { "occurTime desc", "stationId asc" })
public class WZCommandLogSearchForm {

	@View(label = "开始时间", type = "DateTime", checkable = true, checked = true)
	@Condition(column = "occurTime", compareType = ">=")
	protected Date startTime = DateTimeUtil.getCurrentBeginDate();

	@View(label = "结束时间", type = "DateTime", checkable = true, checked = true)
	@Condition(column = "occurTime", compareType = "<=")
	protected Date endTime = DateTimeUtil.getCurrentEndDate();

	@View(label = "操作员编号", checkable = true, regexp = "\\d{0,10}")
	@Condition(column = "operatorId")
	private String operatorId;

	@View(label = "日志类型", type = "Combo", checkable = true)
	@DataSource(provider = AFCCmdLogType.class)
	@Condition(column = "cmdType")
	private Short cmdType;

	@View(label = "命令结果", type = "Combo", checkable = true)
	@DataSource(provider = AFCMackCode.class)
	@Condition(column = "cmdResult")
	private Short cmdResult;

	@Condition(column = "nodeId", compareType = "in")
	private List<Long> deviceIds;

	@Condition(column = "stationId", compareType = "in")
	private List<Integer> stationIds;

	@Condition(column = "lineId", compareType = "in")
	private List<Short> lineIds;

	@Condition(column = "nodeType", compareType = "in")
	private List<Short> nodeType;

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

	public Short getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(Short cmdResult) {
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

	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}

	public List<Integer> getStationIds() {
		return stationIds;
	}

	public void setStationIds(List<Integer> stationIds) {
		this.stationIds = stationIds;
	}

	public List<Short> getLineIds() {
		return lineIds;
	}

	public void setLineIds(List<Short> lineIds) {
		this.lineIds = lineIds;
	}

	public List<Short> getNodeType() {
		return nodeType;
	}

	public void setNodeType(List<Short> nodeType) {
		this.nodeType = nodeType;
	}

}
