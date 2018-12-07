/* 
 * 日期：2017年6月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.monitor.wz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Ticket: TST_NODE_STOCKS实体
 * @author  mengyifan
 *
 */
@Entity
@Table(name = "TST_NODE_STOCKS")
@SuppressWarnings("serial")
public class TstNodeStocks implements Serializable {

	/** identifier field */
	// 设备节点标识
	@Id
	@Column(name = "node_id")
	private Long nodeId;

	@Column(name = "line_Id")
	/** persistent field */
	private Short lineId;

	@Column(name = "station_Id")
	/** persistent field */
	private Integer stationId;

	// 操作时间
	@Column(name = "upload_Time")
	/** nullable persistent field */
	private Date uploadTime;

	/** nullable persistent field */

	@Column(name = "info_Item0")
	private Long infoItem0;

	@Column(name = "info_Item1")
	private Long infoItem1;

	@Column(name = "info_Item2")
	/** nullable persistent field */
	private Long infoItem2;

	@Column(name = "info_Item3")
	private Long infoItem3;

	@Column(name = "info_Item4")
	/** nullable persistent field */
	private Long infoItem4;

	// 预留
	@Column(name = "info_Item5")
	/** nullable persistent field */
	private Long infoItem5;

	// 预留
	@Column(name = "info_Item6")
	/** nullable persistent field */
	private Long infoItem6;

	// 预留
	@Column(name = "info_Item7")
	/** nullable persistent field */
	private Long infoItem7;

	// 预留
	@Column(name = "info_Item8")
	/** nullable persistent field */
	private Long infoItem8;

	// 预留
	@Column(name = "info_Item9")
	/** nullable persistent field */
	private Long infoItem9;

	/** full constructor */
	public TstNodeStocks(Short lineId, Integer stationId, Date uploadTime, Long infoItem0, Long infoItem1,
			Long infoItem2, Long infoItem3, Long infoItem4, Long infoItem5, Long infoItem6, Long infoItem7,
			Long infoItem8, Long infoItem9) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.uploadTime = uploadTime;
		this.infoItem0 = infoItem0;
		this.infoItem1 = infoItem1;
		this.infoItem2 = infoItem2;
		this.infoItem3 = infoItem3;
		this.infoItem4 = infoItem4;
		this.infoItem5 = infoItem5;
		this.infoItem6 = infoItem6;
		this.infoItem7 = infoItem7;
		this.infoItem8 = infoItem8;
		this.infoItem9 = infoItem9;
	}

	public TstNodeStocks(Long id, Short lineId, Integer stationId, Date uploadTime, Long infoItem0, Long infoItem1,
			Long infoItem2, Long infoItem3) {
		super();
		this.nodeId = id;
		this.lineId = lineId;
		this.stationId = stationId;
		this.uploadTime = uploadTime;
		this.infoItem0 = infoItem0;
		this.infoItem1 = infoItem1;
		this.infoItem2 = infoItem2;
		this.infoItem3 = infoItem3;
	}

	/** default constructor */
	public TstNodeStocks() {
	}

	/** minimal constructor */
	public TstNodeStocks(Short lineId, Integer stationId) {
		this.lineId = lineId;
		this.stationId = stationId;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long id) {
		this.nodeId = id;
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

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getInfoItem0() {
		return this.infoItem0;
	}

	public void setInfoItem0(Long infoItem0) {
		this.infoItem0 = infoItem0;
	}

	public Long getInfoItem1() {
		return this.infoItem1;
	}

	public void setInfoItem1(Long infoItem1) {
		this.infoItem1 = infoItem1;
	}

	public Long getInfoItem2() {
		return this.infoItem2;
	}

	public void setInfoItem2(Long infoItem2) {
		this.infoItem2 = infoItem2;
	}

	public Long getInfoItem3() {
		return this.infoItem3;
	}

	public void setInfoItem3(Long infoItem3) {
		this.infoItem3 = infoItem3;
	}

	public Long getInfoItem4() {
		return this.infoItem4;
	}

	public void setInfoItem4(Long infoItem4) {
		this.infoItem4 = infoItem4;
	}

	public Long getInfoItem5() {
		return this.infoItem5;
	}

	public void setInfoItem5(Long infoItem5) {
		this.infoItem5 = infoItem5;
	}

	public Long getInfoItem6() {
		return this.infoItem6;
	}

	public void setInfoItem6(Long infoItem6) {
		this.infoItem6 = infoItem6;
	}

	public Long getInfoItem7() {
		return this.infoItem7;
	}

	public void setInfoItem7(Long infoItem7) {
		this.infoItem7 = infoItem7;
	}

	public Long getInfoItem8() {
		return this.infoItem8;
	}

	public void setInfoItem8(Long infoItem8) {
		this.infoItem8 = infoItem8;
	}

	public Long getInfoItem9() {
		return this.infoItem9;
	}

	public void setInfoItem9(Long infoItem9) {
		this.infoItem9 = infoItem9;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getNodeId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TstNodeStocks))
			return false;
		TstNodeStocks castOther = (TstNodeStocks) other;
		return new EqualsBuilder().append(this.getNodeId(), castOther.getNodeId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getNodeId()).toHashCode();
	}

}
