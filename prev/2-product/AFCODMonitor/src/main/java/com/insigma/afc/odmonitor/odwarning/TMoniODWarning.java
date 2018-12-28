package com.insigma.afc.odmonitor.odwarning;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 客流告警实体 Ticket:
 * 
 * @author 廖红自
 */
public class TMoniODWarning implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Short lineNo;

	/** identifier field */
	private Integer stationNo;

	/** persistent field */
	private String stationName;

	/** persistent field */
	private Integer inWarning;

	/** persistent field */
	private Integer outWarning;

	/** persistent field */
	private Integer totalWarning;

	/** nullable persistent field */
	private String remark;

	/** full constructor */
	public TMoniODWarning(Short lineNo, Integer stationNo, String stationName, Integer inWarning, Integer outWarning,
			Integer totalWarning, String remark) {
		this.lineNo = lineNo;
		this.stationNo = stationNo;
		this.stationName = stationName;
		this.inWarning = inWarning;
		this.outWarning = outWarning;
		this.totalWarning = totalWarning;
		this.remark = remark;
	}

	/** default constructor */
	public TMoniODWarning() {
	}

	/** minimal constructor */
	public TMoniODWarning(Short lineNo, Integer stationNo, String stationName, Integer inWarning, Integer outWarning,
			Integer totalWarning) {
		this.lineNo = lineNo;
		this.stationNo = stationNo;
		this.stationName = stationName;
		this.inWarning = inWarning;
		this.outWarning = outWarning;
		this.totalWarning = totalWarning;
	}

	/**
	 * @return lineNo
	 */
	public Short getLineNo() {
		return this.lineNo;
	}

	/**
	 * @param lineNo
	 *            lineNo
	 */
	public void setLineNo(Short lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * @return stationNo
	 */
	public Integer getStationNo() {
		return this.stationNo;
	}

	/**
	 * @param stationNo
	 *            stationNo
	 */
	public void setStationNo(Integer stationNo) {
		this.stationNo = stationNo;
	}

	/**
	 * @return stationName
	 */
	public String getStationName() {
		return this.stationName;
	}

	/**
	 * @param stationName
	 *            stationName
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * @return inWarning
	 */
	public Integer getInWarning() {
		return this.inWarning;
	}

	/**
	 * @param inWarning
	 *            inWarning
	 */
	public void setInWarning(Integer inWarning) {
		this.inWarning = inWarning;
	}

	/**
	 * @return outWarning
	 */
	public Integer getOutWarning() {
		return this.outWarning;
	}

	/**
	 * @param outWarning
	 *            outWarning
	 */
	public void setOutWarning(Integer outWarning) {
		this.outWarning = outWarning;
	}

	/**
	 * @return totalWarning
	 */
	public Integer getTotalWarning() {
		return this.totalWarning;
	}

	/**
	 * @param totalWarning
	 *            totalWarning
	 */
	public void setTotalWarning(Integer totalWarning) {
		this.totalWarning = totalWarning;
	}

	/**
	 * @return remark
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * @param remark
	 *            remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("lineNo", getLineNo()).append("stationNo", getStationNo()).toString();
	}

	/**
	 * @param other
	 *            other
	 * @return boolean
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TMoniODWarning)) {
			return false;
		}
		TMoniODWarning castOther = (TMoniODWarning) other;
		return new EqualsBuilder().append(this.getLineNo(), castOther.getLineNo())
				.append(this.getStationNo(), castOther.getStationNo()).isEquals();
	}

	/**
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLineNo()).append(getStationNo()).toHashCode();
	}

}
