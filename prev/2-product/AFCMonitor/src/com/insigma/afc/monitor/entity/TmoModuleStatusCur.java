package com.insigma.afc.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TmoEquModuleCur entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_EQU_MODULE_CUR")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TMO_MODE_UPLOAD_INFO", allocationSize = 1, initialValue = 1)
public class TmoModuleStatusCur implements java.io.Serializable {

	private static final long serialVersionUID = -3584291420204758529L;

	private long eventId;

	private long nodeId;

	private short lineId;

	private int stationId;

	private short equType;

	private int tagId;

	private String tagName;

	private int tagValue;

	private short statusLevel;

	private short eventType;

	private String evtDesc;

	private Date occurTime;

	private short eventStatus;

	private String remark;

	// Constructors

	/** default constructor */
	public TmoModuleStatusCur() {
	}

	/** minimal constructor */
	public TmoModuleStatusCur(long eventId) {
		this.eventId = eventId;
	}

	/** full constructor */
	public TmoModuleStatusCur(long eventId, long nodeId, short lineId, int stationId, short equType, int tagId,
			String tagName, int tagValue, short statusLevel, short eventType, String evtDesc, Date occurTime,
			short eventStatus, String remark) {
		this.eventId = eventId;
		this.nodeId = nodeId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.equType = equType;
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.statusLevel = statusLevel;
		this.eventType = eventType;
		this.evtDesc = evtDesc;
		this.occurTime = occurTime;
		this.eventStatus = eventStatus;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "EVENT_ID", unique = true, nullable = false, scale = 0)
	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "NODE_ID", scale = 0)
	public long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "LINE_ID", precision = 5, scale = 0)
	public short getLineId() {
		return this.lineId;
	}

	public void setLineId(short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "STATION_ID", precision = 11, scale = 0)
	public int getStationId() {
		return this.stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	@Column(name = "EQU_TYPE", precision = 5, scale = 0)
	public short getEquType() {
		return this.equType;
	}

	public void setEquType(short equType) {
		this.equType = equType;
	}

	@Column(name = "TAG_ID", precision = 11, scale = 0)
	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	@Column(name = "TAG_NAME", length = 32)
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Column(name = "TAG_VALUE", precision = 11, scale = 0)
	public int getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(int tagValue) {
		this.tagValue = tagValue;
	}

	@Column(name = "STATUS_LEVEL", precision = 5, scale = 0)
	public short getStatusLevel() {
		return this.statusLevel;
	}

	public void setStatusLevel(short statusLevel) {
		this.statusLevel = statusLevel;
	}

	@Column(name = "EVENT_TYPE", precision = 5, scale = 0)
	public short getEventType() {
		return this.eventType;
	}

	public void setEventType(short eventType) {
		this.eventType = eventType;
	}

	@Column(name = "EVT_DESC", length = 100)
	public String getEvtDesc() {
		return this.evtDesc;
	}

	public void setEvtDesc(String evtDesc) {
		this.evtDesc = evtDesc;
	}

	@Column(name = "OCCUR_TIME")
	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "EVENT_STATUS", precision = 5, scale = 0)
	public short getEventStatus() {
		return this.eventStatus;
	}

	public void setEventStatus(short eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Column(name = "REMARK", length = 300)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}