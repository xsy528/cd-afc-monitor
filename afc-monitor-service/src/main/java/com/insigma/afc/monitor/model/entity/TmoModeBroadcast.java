package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * TmoModeBroadcast entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_MODE_BROADCAST")
@SequenceGenerator(name = "S_TMO_MODE_BROADCAST", sequenceName = "S_TMO_MODE_BROADCAST", allocationSize = 1)
public class TmoModeBroadcast implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TMO_MODE_BROADCAST")
	@Column(name = "RECORD_ID", unique = true, nullable = false, scale = 0)
	private Long recordId;

	@Column(name = "NODE_ID", nullable = false, scale = 0)
	private Long nodeId;

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	private Short lineId;

	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	protected Integer stationId;

	@Column(name = "MODE_CODE", nullable = false, precision = 5, scale = 0)
	private Short modeCode;

	@Column(name = "MODE_BROADCAST_TIME")
	private Date modeBroadcastTime;

	@Column(name = "TARGET_ID", nullable = true, precision = 19, scale = 0)
	private long targetId;

	@Column(name = "OPERATOR_ID", nullable = true, length = 32)
	private String operatorId;

	@Column(name = "MODE_EFFECT_TIME", nullable = false)
	private Date modeEffectTime;

	@Column(name = "BROADCAST_STATUS", nullable = false, precision = 5, scale = 0)
	private Short broadcastStatus;// 未确认；成功；失败

	@Column(name = "BROADCAST_TYPE", nullable = false, precision = 5, scale = 0)
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

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
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

	public Short getModeCode() {
		return modeCode;
	}

	public void setModeCode(Short modeCode) {
		this.modeCode = modeCode;
	}

	public Date getModeBroadcastTime() {
		return modeBroadcastTime;
	}

	public void setModeBroadcastTime(Date modeBroadcastTime) {
		this.modeBroadcastTime = modeBroadcastTime;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getModeEffectTime() {
		return modeEffectTime;
	}

	public void setModeEffectTime(Date modeEffectTime) {
		this.modeEffectTime = modeEffectTime;
	}

	public Short getBroadcastStatus() {
		return broadcastStatus;
	}

	public void setBroadcastStatus(Short broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	public Short getBroadcastType() {
		return broadcastType;
	}

	public void setBroadcastType(Short broadcastType) {
		this.broadcastType = broadcastType;
	}
}