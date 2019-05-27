package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * TmoEquStatusCur entity. 
 * author zengdong
 */
@Entity
@Table(name = "TMO_EQU_STATUS_CUR")
@SequenceGenerator(name = "S_TMO_EQU_STATUS_CUR", sequenceName = "S_TMO_EQU_STATUS_CUR", allocationSize = 1)
public class TmoEquStatusCur implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TMO_EQU_STATUS_CUR")
	@Column(name = "RECORD_ID", unique = true, nullable = false)
	private Long recordId;

	@Column(name = "LINE_ID", nullable = false)
	private Short lineId;

	@Column(name = "STATION_ID", nullable = false)
	private Short stationId;

	@Column(name = "DEVICE_ID", nullable = false)
	private Short deviceId;

	@Column(name = "DEVICE_TYPE", nullable = false)
	private Short deviceType;

	@Column(name = "NODE_ID", nullable = false)
	private Long nodeId;

	@Column(name = "STATUS_ID")
	private Short statusId;

	@Column(name = "STATUS_NAME", length = 64)
	private String statusName;

	@Column(name = "STATUS_VALUE")
	private Short statusValue;

	@Column(name = "STATUS_DESC", length = 300)
	private String statusDesc;

	@Column(name = "APPLY_DEVICE", length = 64)
	private String applyDevice;

	@Column(name = "SYS_TIME", length = 26)
	private Timestamp sysTime;

	@Column(name = "OCCUR_TIME", length = 26)
	private Timestamp occurTime;

	@Column(name = "ITEM1")
	private Short item1;

	@Column(name = "ITEM2")
	private Short item2;


	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Short getStationId() {
		return this.stationId;
	}

	public void setStationId(Short stationId) {
		this.stationId = stationId;
	}

	public Short getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Short deviceId) {
		this.deviceId = deviceId;
	}

	public Short getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(Short deviceType) {
		this.deviceType = deviceType;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Short getStatusValue() {
		return this.statusValue;
	}

	public void setStatusValue(Short statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getApplyDevice() {
		return this.applyDevice;
	}

	public void setApplyDevice(String applyDevice) {
		this.applyDevice = applyDevice;
	}

	public Timestamp getSysTime() {
		return this.sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public Short getItem1() {
		return this.item1;
	}

	public void setItem1(Short item1) {
		this.item1 = item1;
	}

	public Short getItem2() {
		return this.item2;
	}

	public void setItem2(Short item2) {
		this.item2 = item2;
	}

}