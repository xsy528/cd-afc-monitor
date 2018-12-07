/*
 * 日期：2010-9-29
 * 描述（预留）
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.odmonitor.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: 有效路径路段表实体
 * 
 * @author zhengshuquan
 * @date 2010-9-29
 * @description
 */
@Entity
@Table(name = "TCC_PATH_SECTIONS")
// @SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TCC_PATH_SECTIONS")
public class EffectivePathSection implements Serializable {

	private static final long serialVersionUID = -722349845662940299L;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "pathId", column = @Column(name = "path_Id", nullable = false, precision = 11, scale = 0) ),
			@AttributeOverride(name = "sectionId", column = @Column(name = "section_Id", nullable = false, precision = 11, scale = 0) ) })
	EffectivePathSectionId id;

	/**
	 * 路段序号（1，2，3… 表示该路段是这条路径的第几段）
	 */
	@Column(name = "section_Index")
	@View(label = "路段序号")
	@ColumnView(name = "路段序号")
	private Short sectionIndex;

	/**
	 * 所属线路号
	 */
	@Column(name = "line_id")
	private Short lineId;

	/**
	 * 开始车站
	 */
	@Column(name = "pre_Station_Id")
	private Integer preStationId;

	@Column(name = "pre_Station_name")
	@View(label = "起始车站名称")
	@ColumnView(name = "起始车站名称")
	private String preStationName;

	/**
	 * 结束车站
	 */
	@Column(name = "down_Station_Id")
	private Integer downStationId;

	@Column(name = "down_Station_name")
	@View(label = "结束车站名称")
	private String downStationName;

	/**
	 * 乘距权值（单位：米）
	 */
	@Column(name = "distance_Weight")
	@View(label = "乘距权值(米)")
	private Integer distanceWeight;

	@Column(name = "driving_time")
	@View(label = "行驶时间（秒）")
	private Integer drivingTime;

	@Column(name = "stop_time")
	@View(label = "停站时间（秒）")
	private Integer stopTime;

	@Column(name = "wait_time")
	@View(label = "换乘等候时间（秒）")
	private Integer waitTime;

	@Column(name = "walk_time")
	@View(label = "换乘步行时间（秒）")
	private Integer walkTime;

	@Column(name = "comfort_coefficient")
	@View(label = "舒适度系数")
	private Double comfortCoefficient;

	/**
	 * 0 上行，1 下行
	 */
	@Column(name = "up_Down_Tag")
	@View(label = "是否上行")
	private short upDownTag;

	/**
	 *
	 */
	public EffectivePathSection() {
		super();
		this.id = new EffectivePathSectionId();
	}

	/**
	 * @param pathId
	 * @param sectionId
	 * @param sectionIndex
	 */
	public EffectivePathSection(Integer pathId, Long sectionId, Short sectionIndex) {
		super();
		this.id = new EffectivePathSectionId(pathId, sectionId);
		// this.pathId = pathId;
		// this.sectionId = sectionId;
		this.sectionIndex = sectionIndex;
	}

	/**
	 * @return the pathId
	 */
	public Integer getPathId() {
		return id.getPathId();
	}

	/**
	 * @param pathId
	 *            the pathId to set
	 */
	public void setPathId(Integer pathId) {
		this.id.setPathId(pathId);
	}

	/**
	 * @return the sectionId
	 */
	public Long getSectionId() {
		return id.getSectionId();
	}

	/**
	 * @param sectionId
	 *            the sectionId to set
	 */
	public void setSectionId(Long sectionId) {
		this.id.setSectionId(sectionId);
	}

	/**
	 * @return the sectionIndex
	 */
	public Short getSectionIndex() {
		return sectionIndex;
	}

	/**
	 * @param sectionIndex
	 *            the sectionIndex to set
	 */
	public void setSectionIndex(Short sectionIndex) {
		this.sectionIndex = sectionIndex;
	}

	/**
	 * @return the lineId
	 */
	public Short getLineId() {
		return lineId;
	}

	/**
	 * @param lineId
	 *            the lineId to set
	 */
	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the preStationId
	 */
	public Integer getPreStationId() {
		return preStationId;
	}

	/**
	 * @param preStationId
	 *            the preStationId to set
	 */
	public void setPreStationId(Integer preStationId) {
		this.preStationId = preStationId;
	}

	/**
	 * @return the preStationName
	 */
	public String getPreStationName() {
		return preStationName;
	}

	/**
	 * @param preStationName
	 *            the preStationName to set
	 */
	public void setPreStationName(String preStationName) {
		this.preStationName = preStationName;
	}

	/**
	 * @return the downStationId
	 */
	public Integer getDownStationId() {
		return downStationId;
	}

	/**
	 * @param downStationId the downStationId to set
	 */
	public void setDownStationId(Integer downStationId) {
		this.downStationId = downStationId;
	}

	/**
	 * @return the distanceWeight
	 */
	public Integer getDistanceWeight() {
		return distanceWeight;
	}

	/**
	 * @param distanceWeight
	 *            the distanceWeight to set
	 */
	public void setDistanceWeight(Integer distanceWeight) {
		this.distanceWeight = distanceWeight;
	}

	/**
	 * @return the downStationName
	 */
	public String getDownStationName() {
		return downStationName;
	}

	/**
	 * @param downStationName the downStationName to set
	 */
	public void setDownStationName(String downStationName) {
		this.downStationName = downStationName;
	}

	/**
	 * @return the drivingTime
	 */
	public Integer getDrivingTime() {
		return drivingTime;
	}

	/**
	 * @param drivingTime the drivingTime to set
	 */
	public void setDrivingTime(Integer drivingTime) {
		this.drivingTime = drivingTime;
	}

	/**
	 * @return the stopTime
	 */
	public Integer getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime the stopTime to set
	 */
	public void setStopTime(Integer stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return the waitTime
	 */
	public Integer getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * @return the walkTime
	 */
	public Integer getWalkTime() {
		return walkTime;
	}

	/**
	 * @param walkTime the walkTime to set
	 */
	public void setWalkTime(Integer walkTime) {
		this.walkTime = walkTime;
	}

	/**
	 * @return the comfortCoefficient
	 */
	public Double getComfortCoefficient() {
		return comfortCoefficient;
	}

	/**
	 * @param comfortCoefficient
	 *            the comfortCoefficient to set
	 */
	public void setComfortCoefficient(Double comfortCoefficient) {
		this.comfortCoefficient = comfortCoefficient;
	}

	/**
	 * @param upDownTag
	 *            the upDownTag to set
	 */
	public void setUpDownTag(short upDownTag) {
		this.upDownTag = upDownTag;
	}

	/**
	 * @return the upDownTag
	 */
	public short getUpDownTag() {
		return upDownTag;
	}

}
