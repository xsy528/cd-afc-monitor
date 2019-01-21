package com.insigma.afc.monitor.search;

import com.insigma.commons.ui.anotation.ColumnView;

/**
 * 模式广播查询
 * 
 * @author CaiChunye
 */

public class TmoModeBroadcastViewItem {

	private Long id;

	@ColumnView(name = "序号", sortAble = false)
	private String recordId;

	@ColumnView(name = "模式上传源线路/编号", sortAble = false)
	private String lineName;

	@ColumnView(name = "模式上传源车站/编号", sortAble = false)
	private String stationName;

	@ColumnView(name = "进入的模式名称/编号", sortAble = false)
	private String modeCode;

	@ColumnView(name = "模式上传时间", sortAble = false)
	private String modeUploadTime;

	@ColumnView(name = "模式广播时间 ", sortAble = false)
	private String modeBroadcastTime;

	@ColumnView(name = "模式广播方式", sortAble = false)
	private String broadcastType;

	@ColumnView(name = "模式广播状态", sortAble = false)
	private String broadcastStatus;

	@ColumnView(name = "模式广播目的车站/编号", sortAble = false)
	private String targetId;

	@ColumnView(name = "操作员编号", sortAble = false)
	private String operatorId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getModeBroadcastTime() {
		return modeBroadcastTime;
	}

	public void setModeBroadcastTime(String modeBroadcastTime) {
		this.modeBroadcastTime = modeBroadcastTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the modeUploadtTime
	 */
	public String getModeUploadTime() {
		return modeUploadTime;
	}

	/**
	 * @param modeUploadtTime
	 *            the modeUploadtTime to set
	 */
	public void setModeUploadTime(String modeUploadtTime) {
		this.modeUploadTime = modeUploadtTime;
	}

	/**
	 * @return the broadcastType
	 */
	public String getBroadcastType() {
		return broadcastType;
	}

	/**
	 * @param broadcastType
	 *            the broadcastType to set
	 */
	public void setBroadcastType(String broadcastType) {
		this.broadcastType = broadcastType;
	}

	/**
	 * @return the broadcastStatus
	 */
	public String getBroadcastStatus() {
		return broadcastStatus;
	}

	/**
	 * @param broadcastStatus
	 *            the broadcastStatus to set
	 */
	public void setBroadcastStatus(String broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	/**
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId
	 *            the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

}
