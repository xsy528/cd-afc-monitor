package com.insigma.afc.monitor.model.vo;

/**
 * 模式广播查询
 * 
 * @author CaiChunye
 */

public class TmoModeBroadcastViewItem {

	private Long id;

	private String recordId;

	private String lineName;

	private String stationName;

	private String modeCode;

	private String modeUploadTime;

	private String modeBroadcastTime;

	private String broadcastType;

	private String broadcastStatus;

	private String targetId;

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
