package com.insigma.afc.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.insigma.afc.topology.convertor.UserNameConvertor;
import com.insigma.commons.ui.anotation.ColumnView;

@Entity
@Table(name = "TSY_OP_LOG")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TSY_OP_LOG")
public class TsyOpLog implements Serializable {

	private static final long serialVersionUID = -8880673089789816953L;

	public static final short UNCREATEFILE = 0;

	public static final short CREATEFILE = 1;

	public static final int PAGE_SIZE = 10000;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@ColumnView(name = "序号")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "LINE_ID")
	private Short lineId;

	@Column(name = "STATION_ID")
	private Integer stationId;

	@Column(name = "NODE_ID")
	private Long nodeId;

	@Column(name = "OCCUR_TIME")
	@ColumnView(name = "发生时间")
	private Date occurTime;

	@Column(name = "OPERATOR_ID")
	@ColumnView(name = "操作员名称/编号", convertor = UserNameConvertor.class)
	private String operatorId;

	@Column(name = "LOG_TYPE")
	private Short logType;

	@Column(name = "LOG_DESC")
	@ColumnView(name = "日志描述")
	private String logDesc;

	@Column(name = "LOG_CLASS")
	@ColumnView(name = "日志等级")
	private Short logClass;

	@Column(name = "MODULE_CODE")
	@ColumnView(name = "模块类型")
	private Integer moduleCode;

	@Column(name = "IP_ADDRESS")
	@ColumnView(name = "IP地址")
	private String ipAddress;

	/** uploadStatus field ，默认值0表示未生成文件，1表示已生成文件 */
	@Column(name = "UPLOAD_STATUS")
	private Short uploadStatus = UNCREATEFILE;

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public TsyOpLog(Long logId, Short lineId, Integer stationId, Long nodeId, Date occurTime, String operatorId,
			Short logType, String logDesc, Short logClass, Integer moduleCode, String ipAddress) {
		super();
		this.logId = logId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.nodeId = nodeId;
		this.occurTime = occurTime;
		this.operatorId = operatorId;
		this.logType = logType;
		this.logDesc = logDesc;
		this.logClass = logClass;
		this.moduleCode = moduleCode;
		this.ipAddress = ipAddress;
	}

	/** default constructor */
	public TsyOpLog() {
	}

	/** minimal constructor */
	public TsyOpLog(Short lineId, Integer stationId, Date occurTime, String operatorId, Short logType, Short logClass,
			Integer moduleCode) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.occurTime = occurTime;
		this.operatorId = operatorId;
		this.logType = logType;
		this.logClass = logClass;
		this.moduleCode = moduleCode;
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Short getLogType() {
		return this.logType;
	}

	public void setLogType(Short logType) {
		this.logType = logType;
	}

	public String getLogDesc() {
		return this.logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public Short getLogClass() {
		return this.logClass;
	}

	public void setLogClass(Short logClass) {
		this.logClass = logClass;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(Integer moduleCode) {
		this.moduleCode = moduleCode;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("logId", getLogId()).toString();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TsyOpLog))
			return false;
		TsyOpLog castOther = (TsyOpLog) other;
		return new EqualsBuilder().append(this.getLogId(), castOther.getLogId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLogId()).toHashCode();
	}

	public Short getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Short uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

}
