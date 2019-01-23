package com.insigma.afc.monitor.action;

import org.eclipse.swt.graphics.Color;

public class CommandResult{

	private String logId;

	private String id;

	private String operatorId;

	private String cmdName;

	private String arg;

	private short result;

	private String cmdResult;

	private String occurTime;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(String cmdResult) {
		this.cmdResult = cmdResult;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public Color getBackgound() {
		return null;
	}

	public Color getForeground() {
		return null;
//		if (result != 0) {
//			return ColorConstants.COLOR_ERROR;
//		}
//		return ColorConstants.COLOR_NORMAL;
	}

	public short getResult() {
		return result;
	}

	public void setResult(short result) {
		this.result = result;
	}

}
