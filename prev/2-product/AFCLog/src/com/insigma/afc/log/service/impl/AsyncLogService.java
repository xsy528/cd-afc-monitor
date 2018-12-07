package com.insigma.afc.log.service.impl;

import com.insigma.commons.service.ILogService;

/**
 * 实现异步记录日志的功能。该功能可以在实时要求高的业务模块中启用。
 * 
 *
 */
public class AsyncLogService implements ILogService {

	private int lineId;

	private int stationId;

	private long nodeId;

	/**
	 * 模块：比如收益管理、参数管理、通信管理等
	 */
	private int module;

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public void doBizErrorLog(String message) {

	}

	public void doBizErrorLog(String message, Throwable exception) {

	}

	public void doBizLog(String message) {

	}

	public void doBizWarningLog(String message) {

	}

	public void doBizWarningLog(String message, Throwable exception) {

	}

}
