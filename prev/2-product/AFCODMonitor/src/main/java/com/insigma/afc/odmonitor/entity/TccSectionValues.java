package com.insigma.afc.odmonitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 创建时间 2010-10-8 下午05:12:26 <br>
 * 描述: 路段信息表<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */

@Entity
@Table(name = "TCC_SECTION_VALUES")
public class TccSectionValues {

	private Long sectionId;

	private Short lineId;

	private Integer preStationId;

	private Integer downStationId;

	// private Integer timeWeight;

	private Integer preDistanceWeight;

	private Short transferFlag;

	private String lineName;

	private String preStationName;

	private String downStationName;

	// private Integer downTimeWeight;

	private Integer downDistanceWeight;

	// 20151209 hexingyue
	// 去掉时间权值和下行时间权值字段，
	// 增加乘车时间(行驶时间+停站时间)、换乘时间(换乘等候时间+换乘步行时间)四个时间的上行和下午的字段。
	// 增加舒适度系数的上行和下行，默认为1。

	private Integer preDrivingTime;

	private Integer preStopTime;

	private Integer preWaitTime;

	private Integer preWalkTime;

	private Double preComfortCoefficient;

	private Integer downDrivingTime;

	private Integer downStopTime;

	private Integer downWaitTime;

	private Integer downWalkTime;

	private Double downComfortCoefficient;

	// Constructors

	/** default constructor */
	public TccSectionValues() {
	}

	/** minimal constructor */
	public TccSectionValues(Long sectionId, Short lineId, Integer preStationId, Integer downStationId,
			Integer preDrivingTime, Integer preDistanceWeight, Short transferFlag) {
		this.sectionId = sectionId;
		this.lineId = lineId;
		this.preStationId = preStationId;
		this.downStationId = downStationId;
		this.preDrivingTime = preDrivingTime;
		this.preDistanceWeight = preDistanceWeight;
		this.transferFlag = transferFlag;
	}

	/**
	 * @param sectionId
	 * @param lineId
	 * @param preStationId
	 * @param downStationId
	 * @param preDistanceWeight
	 * @param transferFlag
	 * @param lineName
	 * @param preStationName
	 * @param downStationName
	 * @param downDistanceWeight
	 * @param preDrivingTime
	 * @param preStopTime
	 * @param preWaitTime
	 * @param preWalkTime
	 * @param preComfortCoefficient
	 * @param downDrivingTime
	 * @param downStopTime
	 * @param downWaitTime
	 * @param downWalkTime
	 * @param downComfortCoefficient
	 */
	public TccSectionValues(Long sectionId, Short lineId, Integer preStationId, Integer downStationId,
			Integer preDistanceWeight, Short transferFlag, String lineName, String preStationName,
			String downStationName, Integer downDistanceWeight, Integer preDrivingTime, Integer preStopTime,
			Integer preWaitTime, Integer preWalkTime, Double preComfortCoefficient, Integer downDrivingTime,
			Integer downStopTime, Integer downWaitTime, Integer downWalkTime, Double downComfortCoefficient) {
		super();
		this.sectionId = sectionId;
		this.lineId = lineId;
		this.preStationId = preStationId;
		this.downStationId = downStationId;
		this.preDistanceWeight = preDistanceWeight;
		this.transferFlag = transferFlag;
		this.lineName = lineName;
		this.preStationName = preStationName;
		this.downStationName = downStationName;
		this.downDistanceWeight = downDistanceWeight;
		this.preDrivingTime = preDrivingTime;
		this.preStopTime = preStopTime;
		this.preWaitTime = preWaitTime;
		this.preWalkTime = preWalkTime;
		this.preComfortCoefficient = preComfortCoefficient;
		this.downDrivingTime = downDrivingTime;
		this.downStopTime = downStopTime;
		this.downWaitTime = downWaitTime;
		this.downWalkTime = downWalkTime;
		this.downComfortCoefficient = downComfortCoefficient;
	}

	@Id
	@Column(name = "SECTION_ID", length = 19)
	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	@Column(name = "LINE_ID", length = 5)
	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "PRE_STATION_ID", length = 11)
	public Integer getPreStationId() {
		return preStationId;
	}

	public void setPreStationId(Integer preStationId) {
		this.preStationId = preStationId;
	}

	@Column(name = "DOWN_STATION_ID", length = 11)
	public Integer getDownStationId() {
		return downStationId;
	}

	public void setDownStationId(Integer downStationId) {
		this.downStationId = downStationId;
	}

	// @Column(name = "TIME_WEIGHT", length = 11)
	// public Integer getTimeWeight() {
	// return timeWeight;
	// }
	//
	// public void setTimeWeight(Integer timeWeight) {
	// this.timeWeight = timeWeight;
	// }

	@Column(name = "PRE_DISTANCE_WEIGHT", length = 11)
	public Integer getPreDistanceWeight() {
		return preDistanceWeight;
	}

	public void setPreDistanceWeight(Integer preDistanceWeight) {
		this.preDistanceWeight = preDistanceWeight;
	}

	@Column(name = "TRANSFER_FLAG", length = 5)
	public Short getTransferFlag() {
		return this.transferFlag;
	}

	public void setTransferFlag(Short transferFlag) {
		this.transferFlag = transferFlag;
	}

	@Column(name = "LINE_NAME", length = 32)
	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Column(name = "PRE_STATION_NAME", length = 32)
	public String getPreStationName() {
		return this.preStationName;
	}

	public void setPreStationName(String preStationName) {
		this.preStationName = preStationName;
	}

	@Column(name = "DOWN_STATION_NAME", length = 32)
	public String getDownStationName() {
		return this.downStationName;
	}

	public void setDownStationName(String downStationName) {
		this.downStationName = downStationName;
	}

	// @Column(name = "DOWN_TIME_WEIGHT", length = 11)
	// public Integer getDownTimeWeight() {
	// return this.downTimeWeight;
	// }
	//
	// public void setDownTimeWeight(Integer downTimeWeight) {
	// this.downTimeWeight = downTimeWeight;
	// }

	@Column(name = "DOWN_DISTANCE_WEIGHT", length = 5)
	public Integer getDownDistanceWeight() {
		return this.downDistanceWeight;
	}

	public void setDownDistanceWeight(Integer downDistanceWeight) {
		this.downDistanceWeight = downDistanceWeight;
	}

	@Column(name = "PRE_DRIVING_TIME", length = 11)
	public Integer getPreDrivingTime() {
		return preDrivingTime;
	}

	public void setPreDrivingTime(Integer preDrivingTime) {
		this.preDrivingTime = preDrivingTime;
	}

	@Column(name = "PRE_STOP_TIME", length = 11)
	public Integer getPreStopTime() {
		return preStopTime;
	}

	public void setPreStopTime(Integer preStopTime) {
		this.preStopTime = preStopTime;
	}

	@Column(name = "PRE_WAIT_TIME", length = 11)
	public Integer getPreWaitTime() {
		return preWaitTime;
	}

	public void setPreWaitTime(Integer preWaitTime) {
		this.preWaitTime = preWaitTime;
	}

	@Column(name = "PRE_WALK_TIME", length = 11)
	public Integer getPreWalkTime() {
		return preWalkTime;
	}

	public void setPreWalkTime(Integer preWalkTime) {
		this.preWalkTime = preWalkTime;
	}

	@Column(name = "PRE_COMFORT_COEFFICIENT", length = 11)
	public Double getPreComfortCoefficient() {
		return preComfortCoefficient;
	}

	public void setPreComfortCoefficient(Double preComfortCoefficient) {
		this.preComfortCoefficient = preComfortCoefficient;
	}

	@Column(name = "DOWN_DRIVING_TIME", length = 11)
	public Integer getDownDrivingTime() {
		return downDrivingTime;
	}

	public void setDownDrivingTime(Integer downDrivingTime) {
		this.downDrivingTime = downDrivingTime;
	}

	@Column(name = "DOWN_STOP_TIME", length = 11)
	public Integer getDownStopTime() {
		return downStopTime;
	}

	public void setDownStopTime(Integer downStopTime) {
		this.downStopTime = downStopTime;
	}

	@Column(name = "DOWN_WAIT_TIME", length = 11)
	public Integer getDownWaitTime() {
		return downWaitTime;
	}

	public void setDownWaitTime(Integer downWaitTime) {
		this.downWaitTime = downWaitTime;
	}

	@Column(name = "DOWN_WALK_TIME", length = 11)
	public Integer getDownWalkTime() {
		return downWalkTime;
	}

	public void setDownWalkTime(Integer downWalkTime) {
		this.downWalkTime = downWalkTime;
	}

	@Column(name = "DOWN_COMFORT_COEFFICIENT", length = 11)
	public Double getDownComfortCoefficient() {
		return downComfortCoefficient;
	}

	public void setDownComfortCoefficient(Double downComfortCoefficient) {
		this.downComfortCoefficient = downComfortCoefficient;
	}

}
