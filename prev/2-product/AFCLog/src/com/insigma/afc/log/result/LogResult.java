package com.insigma.afc.log.result;

import com.insigma.afc.log.convertor.LogRowColorConvertor;
import com.insigma.afc.topology.convertor.UserNameConvertor;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.TableView;

@TableView(colorConvertor = LogRowColorConvertor.class)
public class LogResult {

	//	@ColumnView(name = "序号", sortAble = false)
	//	private String logId;

	@ColumnView(name = "发生时间", sortAble = false)
	private String occurTime;

	@ColumnView(name = "日志描述", sortAble = false)
	private String logDesc;

	@ColumnView(name = "操作员名称/编号", convertor = UserNameConvertor.class, sortAble = false)
	private String operatorId;

	@ColumnView(name = "模块类型", sortAble = false)
	private String moduleType;

	@ColumnView(name = "日志等级", sortAble = false)
	private String logClass;

	@ColumnView(name = "IP地址", sortAble = false)
	private String ipAddress;

	public LogResult() {
	}

	public LogResult(String logId, String occurTime, String logDesc, String operatorId, String moduleType,
			String logClass, String ipAddress) {
		//		this.logId = logId;
		this.occurTime = occurTime;
		this.logDesc = logDesc;
		this.operatorId = operatorId;
		this.moduleType = moduleType;
		this.logClass = logClass;
		this.ipAddress = ipAddress;
	}

	//	public String getLogId() {
	//		return logId;
	//	}
	//
	//	public void setLogId(String logId) {
	//		this.logId = logId;
	//	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getLogClass() {
		return logClass;
	}

	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
