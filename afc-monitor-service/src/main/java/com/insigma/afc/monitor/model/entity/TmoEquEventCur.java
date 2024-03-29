package com.insigma.afc.monitor.model.entity;

import com.insigma.commons.util.DateTimeUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 当前事件实体
 * 
 * @author CaiChunye
 *
 */
@Entity
@Table(name = "TMO_EQU_EVENT_CUR")
@SequenceGenerator(name = "S_TMO_EQU_EVENT_CUR", sequenceName = "S_TMO_EQU_EVENT_CUR")
public class TmoEquEventCur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7722106191503732450L;

	/** 事件ID */
	private Long eventId;

	/** 节点序号*/
	private Long nodeId;

	/** 线路号 */
	private Short lineId;

	/**车站号 */
	private Integer stationId;

	/**设备类型 */
	private Short equType;

	/** 部件类型*/
	private String componentType;

	/** 标记名 */
	private String tagName;

	/** 事件值 */
	private Integer tagId;

	/** 标记值 */
	private Integer tagValue;

	/** 状态级别 */
	private Short statusLevel;

	/** 事件类型 */
	private Short eventType;

	/** 事件描述*/
	private String evtDesc;

	/** 发生时间 */
	private Date occurTime;

	/** 事件状态： */
	private Short eventStatus;

	/** 备注 */
	private String remark;

	public TmoEquEventCur(Long eventId, Long nodeId, Short lineId, Integer stationId, Short equType,
			String componentType, String tagName, Integer tagValue, Integer tagId, Short statusLevel, Short eventType,
			String evtDesc, Date occurTime, Short eventStatus, String remark) {
		this.eventId = eventId;
		this.nodeId = nodeId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.equType = equType;
		this.componentType = componentType;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.tagId = tagId;
		this.statusLevel = statusLevel;
		this.eventType = eventType;
		this.evtDesc = evtDesc;
		this.occurTime = occurTime;
		this.eventStatus = eventStatus;
		this.remark = remark;
	}

	public TmoEquEventCur() {
	}

	public TmoEquEventCur(Long eventId) {
		this.eventId = eventId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TMO_EQU_EVENT_CUR")
	@Column(name = "EVENT_ID", length = 20)
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "NODE_ID", length = 20)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "LINE_ID", length = 5)
	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "STATION_ID", length = 11)
	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	@Column(name = "EQU_TYPE", length = 11)
	public Short getEquType() {
		return this.equType;
	}

	public void setEquType(Short equType) {
		this.equType = equType;
	}

	@Column(name = "COMPONENT_TYPE", length = 11)
	public String getComponentType() {
		return this.componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	@Column(name = "TAG_NAME", length = 32)
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Column(name = "TAG_VALUE", length = 11)
	public Integer getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(Integer tagValue) {
		this.tagValue = tagValue;
	}

	@Column(name = "TAG_ID", length = 11)
	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	@Column(name = "STATUS_LEVEL", length = 5)
	public Short getStatusLevel() {
		return this.statusLevel;
	}

	public void setStatusLevel(Short statusLevel) {
		this.statusLevel = statusLevel;
	}

	@Column(name = "EVENT_TYPE", length = 5)
	public Short getEventType() {
		return this.eventType;
	}

	public void setEventType(Short eventType) {
		this.eventType = eventType;
	}

	@Column(name = "EVT_DESC", length = 5)
	public String getEvtDesc() {
		return this.evtDesc;
	}

	public void setEvtDesc(String evtDesc) {
		this.evtDesc = evtDesc;
	}

	@Column(name = "OCCUR_TIME", nullable = false)
	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "EVENT_STATUS", length = 5)
	public Short getEventStatus() {
		return this.eventStatus;
	}

	public void setEventStatus(Short eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "LineId:" + lineId + "  StationId:" + stationId + "  NodeId:" + nodeId + "  "
				+ DateTimeUtil.formatDate(occurTime, "yyyy-MM-dd HH:mm:ss") + " " + tagName + "_" + tagValue;
	}

}
