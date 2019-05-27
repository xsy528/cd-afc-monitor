package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * TmoModeUploadInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_MODE_UPLOAD_INFO")
@SequenceGenerator(name = "S_TMO_MODE_UPLOAD_INFO", sequenceName = "S_TMO_MODE_UPLOAD_INFO")
@SuppressWarnings("serial")
public class TmoModeUploadInfo implements java.io.Serializable {

	// Fields
	private Long recordId;

	private Short lineId;
	private Integer stationId;
	private Short modeCode;
	private Date modeUploadTime;
	private Short broadcastStatus;

	// Constructors

	/** default constructor */
	public TmoModeUploadInfo() {
	}

	/** full constructor */
	public TmoModeUploadInfo(Long recordId, Short lineId, Integer stationId, Short modeCode, Date modeUploadTime,
			Short broadcastStatus) {
		this.recordId = recordId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.modeCode = modeCode;
		this.modeUploadTime = modeUploadTime;
		this.broadcastStatus = broadcastStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TMO_MODE_UPLOAD_INFO")
	@Column(name = "RECORD_ID", unique = true, nullable = false, scale = 0)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	@Column(name = "MODE_CODE", nullable = false, precision = 5, scale = 0)
	public Short getModeCode() {
		return this.modeCode;
	}

	public void setModeCode(Short modeCode) {
		this.modeCode = modeCode;
	}

	@Column(name = "MODE_UPLOAD_TIME")
	public Date getModeUploadTime() {
		return this.modeUploadTime;
	}

	public void setModeUploadTime(Date modeUploadTime) {
		this.modeUploadTime = modeUploadTime;
	}

	@Column(name = "BROADCAST_STATUS", nullable = false, precision = 5, scale = 0)
	public Short getBroadcastStatus() {
		return this.broadcastStatus;
	}

	public void setBroadcastStatus(Short broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

}