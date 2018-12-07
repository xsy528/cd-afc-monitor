package com.insigma.afc.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.insigma.afc.monitor.action.convertor.EquStatusDescConvertor;
import com.insigma.afc.monitor.action.convertor.EquStatusNameConvertor;
import com.insigma.afc.monitor.entity.convertor.EventCtrRowColorConvertor;
import com.insigma.afc.topology.convertor.MetroNodeConvertor;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.TableView;
import com.insigma.commons.ui.convert.DateTimeConvertor;

/**
 * TmoEquStatus entity. 
 * author yangy
 */
@Entity
@Table(name = "TMO_EQU_STATUS")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TMO_EQU_STATUS", allocationSize = 1, initialValue = 1)
@TableView(colorConvertor = EventCtrRowColorConvertor.class)
public class TmoEquStatus implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 953222996637934947L;

	private Long recordId;

	private Short lineId;

	private Short stationId;

	private Short deviceId;

	private Short deviceType;

	@ColumnView(name = "节点名称/节点编码", convertor = MetroNodeConvertor.class, sortAble = false)
	private Long nodeId;

	//	@ColumnView(name = "状态ID")
	private Short statusId;

	@ColumnView(name = "状态名称/状态ID", convertor = EquStatusNameConvertor.class, sortAble = false)
	private String statusName;

	//	@ColumnView(name = "状态值")
	private Short statusValue;

	@ColumnView(name = "状态描述/状态值", convertor = EquStatusDescConvertor.class, sortAble = false)
	private String statusDesc;

	//	@ColumnView(name = "是否上送 ACC/LCC", convertor = UploadLcOrACCConvertor.class)
	//	private Short uploadLccoracc;

	//	@ColumnView(name = "适用设备")
	private String applyDevice;

	private Timestamp sysTime;

	/** 发生时间 */
	@ColumnView(name = "发生时间", convertor = DateTimeConvertor.class, sortAble = false)
	private Timestamp occurTime;

	//item1 作为状态等级使用
	private Short item1;
	private Short item2;

	// Constructors

	/** default constructor */
	public TmoEquStatus() {
	}

	/** minimal constructor */
	public TmoEquStatus(Long recordId, Short lineId, Short stationId, Short deviceId, Short deviceType, Long nodeId) {
		this.recordId = recordId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.nodeId = nodeId;
	}

	/** full constructor */
	public TmoEquStatus(Long recordId, Short lineId, Short stationId, Short deviceId, Short deviceType, Long nodeId,
			Short statusId, String statusName, Short statusValue, String statusDesc, String applyDevice,
			Timestamp sysTime, Timestamp occurTime, Short item1, Short item2) {
		this.recordId = recordId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.nodeId = nodeId;
		this.statusId = statusId;
		this.statusName = statusName;
		this.statusValue = statusValue;
		this.statusDesc = statusDesc;
		this.applyDevice = applyDevice;
		this.sysTime = sysTime;
		this.occurTime = occurTime;
		this.item1 = item1;
		this.item2 = item2;
	}

	public TmoEquStatus(Short lineId, Short stationId, Short deviceId, Short deviceType, Long nodeId, Short statusId,
			String statusName, Short statusValue, String statusDesc, String applyDevice, Timestamp sysTime,
			Timestamp occurTime, Short item1, Short item2) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.nodeId = nodeId;
		this.statusId = statusId;
		this.statusName = statusName;
		this.statusValue = statusValue;
		this.statusDesc = statusDesc;
		this.applyDevice = applyDevice;
		this.sysTime = sysTime;
		this.occurTime = occurTime;
		this.item1 = item1;
		this.item2 = item2;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "RECORD_ID", unique = true, nullable = false)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "LINE_ID", nullable = false)
	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "STATION_ID", nullable = false)
	public Short getStationId() {
		return this.stationId;
	}

	public void setStationId(Short stationId) {
		this.stationId = stationId;
	}

	@Column(name = "DEVICE_ID", nullable = false)
	public Short getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Short deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "DEVICE_TYPE", nullable = false)
	public Short getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(Short deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "NODE_ID", nullable = false)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "STATUS_ID")
	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	@Column(name = "STATUS_NAME", length = 64)
	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Column(name = "STATUS_VALUE")
	public Short getStatusValue() {
		return this.statusValue;
	}

	public void setStatusValue(Short statusValue) {
		this.statusValue = statusValue;
	}

	@Column(name = "STATUS_DESC", length = 300)
	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Column(name = "APPLY_DEVICE", length = 64)
	public String getApplyDevice() {
		return this.applyDevice;
	}

	public void setApplyDevice(String applyDevice) {
		this.applyDevice = applyDevice;
	}

	@Column(name = "SYS_TIME", length = 26)
	public Timestamp getSysTime() {
		return this.sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

	@Column(name = "OCCUR_TIME", length = 26)
	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "ITEM1")
	public Short getItem1() {
		return this.item1;
	}

	public void setItem1(Short item1) {
		this.item1 = item1;
	}

	@Column(name = "ITEM2")
	public Short getItem2() {
		return this.item2;
	}

	public void setItem2(Short item2) {
		this.item2 = item2;
	}

}