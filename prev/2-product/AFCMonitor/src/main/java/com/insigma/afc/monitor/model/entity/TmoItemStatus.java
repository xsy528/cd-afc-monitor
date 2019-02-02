package com.insigma.afc.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * TmoItemStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_ITEM_STATUS")
public class TmoItemStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DEVICE_ID", nullable = false, scale = 0)
	private Long nodeId;

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	private Short lineId;

	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	private Integer stationId;

	@Column(name = "ITEM_STATUS", precision = 5, scale = 0)
	private Short itemStatus;// 状态：正常，警告，报警

	@Column(name = "ITEM_ACTIVITY", precision = 5, scale = 0)
	private Boolean itemActivity;// 在线状态

	@Column(name = "CURRENT_MODE_CODE")
	private Short currentModeCode;// 当前模式

	@Column(name = "LAST_MODE_CODE")
	private Short lastModeCode;// 上一次模式

	@Column(name = "MODE_CHANGE_TIME")
	private Date modeChangeTime;// 模式切换时间

	@Column(name = "ITEM_TYPE", nullable = false, precision = 11, scale = 0)
	private Short nodeType;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;// 状态更新时间

	// Constructors

	/** default constructor */
	public TmoItemStatus() {
	}

	public TmoItemStatus(short lineId, int stationId, long nodeId, short nodeType) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.nodeId = nodeId;
		this.nodeType = nodeType;
	}

	public TmoItemStatus(short lineId, int stationId, long nodeId, short nodeType, short itemStatus,
			Boolean itemActivity, Short currentModeCode, Short lastModeCode, Date modeChangeTime, Date updateTime) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.nodeId = nodeId;
		this.nodeType = nodeType;
		this.itemStatus = itemStatus;
		this.itemActivity = itemActivity;
		this.currentModeCode = currentModeCode;
		this.lastModeCode = lastModeCode;
		this.modeChangeTime = modeChangeTime;
		this.updateTime = updateTime;
	}

	public Short getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(Short itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Boolean getItemActivity() {
		return this.itemActivity;
	}

	public void setItemActivity(Boolean itemActivity) {
		this.itemActivity = itemActivity;
	}

	public Short getCurrentModeCode() {
		return this.currentModeCode;
	}

	public void setCurrentModeCode(Short currentModeCode) {
		this.currentModeCode = currentModeCode;
	}

	public Short getLastModeCode() {
		return this.lastModeCode;
	}

	public void setLastModeCode(Short lastModeCode) {
		this.lastModeCode = lastModeCode;
	}

	public Date getModeChangeTime() {
		return this.modeChangeTime;
	}

	public void setModeChangeTime(Date modeChangeTime) {
		this.modeChangeTime = modeChangeTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Short getNodeType() {
		return nodeType;
	}

	public void setNodeType(Short nodeType) {
		this.nodeType = nodeType;
	}
}