package com.insigma.afc.monitor.model.entity;

import com.insigma.commons.util.DateTimeUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 命令日志实体
 * 
 * @author CaiChunye
 */
@Entity
@Table(name = "TMO_CMD_RESULT")
@SequenceGenerator(name = "S_TMO_CMD_RESULT", sequenceName = "S_TMO_CMD_RESULT", allocationSize = 1, initialValue = 1)
public class TmoCmdResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_TMO_CMD_RESULT")
	@Column(name = "LOG_ID", nullable = false, precision = 20, scale = 0)
	private Long logId;

	@Column(name="LINE_ID")
	private Short lineId;

	@Column(name="STATION_ID")
	private Integer stationId;

	@Column(name="NODE_ID")
	private Long nodeId;

	@Column(name = "ITEM_TYPE")
	private Short nodeType;

	@Column(name = "CMD_TYPE", nullable = false, precision = 5, scale = 0)
	private Short cmdType;

	@Column(name = "CMD_NAME", nullable = false, length = 500)
	private String cmdName;

	@Column(name = "OPERATOR_ID", nullable = false, length = 32)
	private String operatorId;

	@Column(name = "OCCUR_TIME", nullable = false, length = 11)
	private Date occurTime;

	@Column(name = "TAG_NAME", length = 32)
	private String tagName;

	@Column(name = "TAG_VALUE", length = 100)
	private String tagValue;

	@Column(name = "CMD_RESULT", nullable = false, precision = 5, scale = 0)
	private Short cmdResult;

	@Column(name = "REMARK", length = 500)
	private String remark;

	@Column(name = "UPLOAD_STATUS", nullable = false, precision = 5, scale = 0)
	private Short uploadStatus;

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeType(Short nodeType) {
		this.nodeType = nodeType;
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public short getLineId() {
		return this.lineId;
	}

	public Integer getStationId() {
		return this.stationId;
	}

	public long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public short getNodeType() {
		return nodeType;
	}

	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCmdName() {
		return this.cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public Short getCmdResult() {
		return this.cmdResult;
	}

	public void setCmdResult(Short cmdResult) {
		this.cmdResult = cmdResult;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getUploadStatus() {
		return this.uploadStatus;
	}

	public void setUploadStatus(Short uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public Short getCmdType() {
		return this.cmdType;
	}

	public void setCmdType(Short cmdType) {
		this.cmdType = cmdType;
	}

	@Override
	public String toString() {
		return "LineId:" + lineId + "  StationId:" + stationId + "  NodeId:" + nodeId + "  "
				+ DateTimeUtil.formatDate(occurTime, "yyyy-MM-dd HH:mm:ss") + " " + tagName + "_" + tagValue
				+ "  CmdType:" + cmdType;
	}

}
