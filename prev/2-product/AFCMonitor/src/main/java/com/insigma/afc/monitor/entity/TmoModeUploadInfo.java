package com.insigma.afc.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.insigma.afc.entity.convertor.AFCModeCodeConvertor;
import com.insigma.afc.topology.convertor.MetroNodeConvertor;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.convert.DateTimeConvertor;

/**
 * TmoModeUploadInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_MODE_UPLOAD_INFO")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TMO_MODE_UPLOAD_INFO")
@SuppressWarnings("serial")
public class TmoModeUploadInfo implements java.io.Serializable {

	// Fields
	private Long recordId;

	@ColumnView(name = "线路名称/线路编号", sortAble = false, convertor = MetroNodeConvertor.class)
	private Short lineId;
	@ColumnView(name = "车站名称/车站编号", sortAble = false, convertor = MetroNodeConvertor.class)
	private Integer stationId;
	@ColumnView(name = "进入的模式/编号", sortAble = false, convertor = AFCModeCodeConvertor.class)
	private Short modeCode;
	@ColumnView(name = "模式发生时间", sortAble = false, convertor = DateTimeConvertor.class)
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
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