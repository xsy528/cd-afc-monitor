package com.insigma.afc.monitor.entity;

import com.insigma.afc.topology.AFCNode;

import javax.persistence.*;
import java.util.Date;

/**
 * TmoModeBroadcast entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_MODE_BROADCAST")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TMO_MODE_BROADCAST", allocationSize = 1, initialValue = 1)
public class TmoModeBroadcast extends AFCNode implements java.io.Serializable {

	private static final long serialVersionUID = -2016157168440972766L;

	private Long recordId;

	private long nodeId;

	protected int stationId;

	private Short modeCode;

	private Date modeBroadcastTime;

	private long targetId;

	private String operatorId;

	private Date modeEffectTime;

	private Short broadcastStatus;// 未确认；成功；失败

	private Short broadcastType;// 自动；手动

	// Constructors

	/** default constructor */
	public TmoModeBroadcast() {
	}

	/** full constructor */
	public TmoModeBroadcast(Long recordId, Long nodeId, Short lineId, Integer stationId, Short modeCode,
			Date modeBroadcastTime, String operatorId) {
		this.recordId = recordId;
		this.nodeId = nodeId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.modeCode = modeCode;
		this.modeBroadcastTime = modeBroadcastTime;
		this.operatorId = operatorId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "RECORD_ID", unique = true, nullable = false, scale = 0)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Override
	@Column(name = "NODE_ID", nullable = false, scale = 0)
	public long getNodeId() {
		return nodeId;
	}

	@Override
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	public short getLineId() {
		return lineId;
	}

	@Override
	public void setLineId(short lineId) {
		this.lineId = lineId;
	}

	@Override
	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	public int getStationId() {
		return this.stationId;
	}

	@Override
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	@Column(name = "MODE_CODE", nullable = false, precision = 5, scale = 0)
	public Short getModeCode() {
		return modeCode;
	}

	public void setModeCode(Short modeCode) {
		this.modeCode = modeCode;
	}

	@Column(name = "MODE_BROADCAST_TIME")
	public Date getModeBroadcastTime() {
		return this.modeBroadcastTime;
	}

	public void setModeBroadcastTime(Date modeBroadcastTime) {
		this.modeBroadcastTime = modeBroadcastTime;
	}

	@Column(name = "OPERATOR_ID", nullable = true, length = 32)
	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the modeUploadTime
	 */
	@Column(name = "MODE_EFFECT_TIME", nullable = false)
	public Date getModeEffectTime() {
		return modeEffectTime;
	}

	/**
	 * @param modeUploadTime
	 *            the modeUploadTime to set
	 */
	public void setModeEffectTime(Date modeEffectTime) {
		this.modeEffectTime = modeEffectTime;
	}

	/**
	 * @return the broadcastStatus
	 */
	@Column(name = "BROADCAST_STATUS", nullable = false, precision = 5, scale = 0)
	public Short getBroadcastStatus() {
		return broadcastStatus;
	}

	/**
	 * @param broadcastStatus
	 *            the broadcastStatus to set
	 */
	public void setBroadcastStatus(Short broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	/**
	 * @return the targetId
	 */

	@Column(name = "TARGET_ID", nullable = true, precision = 19, scale = 0)
	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the broadcastType
	 */
	@Column(name = "BROADCAST_TYPE", nullable = false, precision = 5, scale = 0)
	public Short getBroadcastType() {
		return broadcastType;
	}

	/**
	 * @param broadcastType
	 *            the broadcastType to set
	 */
	public void setBroadcastType(Short broadcastType) {
		this.broadcastType = broadcastType;
	}

	// @Override
	// @Column(name = "ITEM_TYPE")
	// public short getNodeType() {
	// return nodeType;
	// }
	//
	// @Override
	// public void setNodeType(short nodeType) {
	// this.nodeType = nodeType;
	// }

}