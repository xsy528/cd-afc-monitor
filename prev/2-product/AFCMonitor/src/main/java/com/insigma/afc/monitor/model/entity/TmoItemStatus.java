package com.insigma.afc.monitor.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.insigma.afc.topology.AFCNode;

/**
 * TmoItemStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_ITEM_STATUS")
public class TmoItemStatus extends AFCNode implements java.io.Serializable {

	private static final long serialVersionUID = -3602719248501005866L;

	private Short itemStatus;// 状态：正常，警告，报警

	private Boolean itemActivity;// 在线状态

	private Short currentModeCode;// 当前模式

	private Short lastModeCode;// 上一次模式

	private Date modeChangeTime;// 模式切换时间

	private Date updateTime;// 状态更新时间

	// Constructors

	/** default constructor */
	public TmoItemStatus() {
	}

	public TmoItemStatus(short lineId, int stationId, long nodeId, short nodeType) {
		super(lineId, stationId, nodeId, nodeType);
	}

	public TmoItemStatus(short lineId, int stationId, long nodeId, short nodeType, short itemStatus,
			Boolean itemActivity, Short currentModeCode, Short lastModeCode, Date modeChangeTime, Date updateTime) {
		super(lineId, stationId, nodeId, nodeType);
		this.itemStatus = itemStatus;
		this.itemActivity = itemActivity;
		this.currentModeCode = currentModeCode;
		this.lastModeCode = lastModeCode;
		this.modeChangeTime = modeChangeTime;
		this.updateTime = updateTime;
	}

	@Id
	@Column(name = "DEVICE_ID", nullable = false, scale = 0)
	public long getNodeId() {
		return this.nodeId;
	}

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	@Override
	public short getLineId() {
		return this.lineId;
	}

	@Override
	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	public int getStationId() {
		return this.stationId;
	}

	@Override
	@Column(name = "ITEM_TYPE", nullable = false, precision = 11, scale = 0)
	public short getNodeType() {
		return super.getNodeType();
	}

	@Column(name = "ITEM_STATUS", precision = 5, scale = 0)
	public Short getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(Short itemStatus) {
		this.itemStatus = itemStatus;
	}

	@Column(name = "ITEM_ACTIVITY", precision = 5, scale = 0)
	public Boolean getItemActivity() {
		return this.itemActivity;
	}

	public void setItemActivity(Boolean itemActivity) {
		this.itemActivity = itemActivity;
	}

	@Column(name = "CURRENT_MODE_CODE")
	public Short getCurrentModeCode() {
		return this.currentModeCode;
	}

	public void setCurrentModeCode(Short currentModeCode) {
		this.currentModeCode = currentModeCode;
	}

	@Column(name = "LAST_MODE_CODE")
	public Short getLastModeCode() {
		return this.lastModeCode;
	}

	public void setLastModeCode(Short lastModeCode) {
		this.lastModeCode = lastModeCode;
	}

	@Column(name = "MODE_CHANGE_TIME")
	public Date getModeChangeTime() {
		return this.modeChangeTime;
	}

	public void setModeChangeTime(Date modeChangeTime) {
		this.modeChangeTime = modeChangeTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}