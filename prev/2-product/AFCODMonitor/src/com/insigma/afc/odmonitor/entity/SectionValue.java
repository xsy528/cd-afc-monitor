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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.insigma.afc.entity.convertor.TagValueConvertor;
import com.insigma.afc.topology.convertor.TenToSixteenConvertor;
import com.insigma.commons.ui.anotation.ColumnView;

/**
 * Ticket: 路段信息表实体
 * 
 * @author zhengshuquan
 * @date 2010-9-29
 * @description
 */
@Entity
@Table(name = "TCC_SECTION_VALUES")
public class SectionValue implements Serializable {

	// 20151209 hexingyue
	// 去掉时间权值和下行时间权值字段，
	// 增加乘车时间(行驶时间+停站时间)、换乘时间(换乘等候时间+换乘步行时间)四个时间的上行和下午的字段。
	// 增加舒适度系数的上行和下行，默认为1。
	// 修改字段上行下行统一
	private static final long serialVersionUID = -9040926090506357457L;

	/**
	 * 所属线路号
	 */
	@Column(name = "line_id")
	private Short lineId;

	@Column(name = "line_name")
	//	@View(label = "所属线路")
	@ColumnView(name = "所属线路", sortAble = false)
	private String lineName;

	/**
	 * 路段ID
	 */
	@Id
	@Column(name = "section_Id")
	//	@View(label = "路段编号", format = "%5X")
	@ColumnView(name = "路段编号", sortAble = false, convertor = TenToSixteenConvertor.class)
	private Long sectionId;

	/**
	 * 上行车站
	 */
	@Column(name = "pre_Station_Id")
	private Integer preStationId;

	@Column(name = "pre_Station_name")
	//	@View(label = "上行车站名称")
	@ColumnView(name = "上行车站名称", sortAble = false)
	private String preStationName;

	/**
	 * 下行车站
	 */
	@Column(name = "down_Station_Id")
	private Integer downStationId;

	@Column(name = "down_Station_name")
	//	@View(label = "下行车站名称")
	@ColumnView(name = "下行车站名称", sortAble = false)
	private String downStationName;

	/**
	 * 乘距权值（单位：公里）
	 */
	@Column(name = "pre_distance_Weight")
	//	@View(label = "上行乘距权值(米)")
	@ColumnView(name = "上行乘距权值(米)", sortAble = false)
	private Integer preDistanceWeight;

	@Column(name = "pre_driving_time")
	//	@View(label = "上行行驶时间（秒）")
	@ColumnView(name = "上行行驶时间(秒)", sortAble = false)
	private Integer preDrivingTime;

	@Column(name = "pre_stop_time")
	//	@View(label = "上行停站时间（秒）")
	@ColumnView(name = "上行停站时间(秒)", sortAble = false)
	private Integer preStopTime;

	@Column(name = "pre_wait_time")
	//	@View(label = "上行换乘等候时间（秒）")
	@ColumnView(name = "上行换乘等候时间(秒)", sortAble = false)
	private Integer preWaitTime;

	@Column(name = "pre_walk_time")
	//	@View(label = "上行换乘步行时间（秒）")
	@ColumnView(name = "上行换乘步行时间(秒)", sortAble = false)
	private Integer preWalkTime;

	@Column(name = "pre_comfort_coefficient")
	//	@View(label = "上行舒适度系数")
	@ColumnView(name = "上行舒适度系数", sortAble = false)
	private Double preComfortCoefficient;

	/**
	 * 乘距权值（单位：公里）
	 */
	@Column(name = "down_distance_weight")
	//	@View(label = "下行乘距权值(米)")
	@ColumnView(name = "下行乘距权值(米)", sortAble = false)
	private Integer downDistanceWeight;

	@Column(name = "down_driving_time")
	//	@View(label = "下行行驶时间（秒）")
	@ColumnView(name = "下行行驶时间(秒)", sortAble = false)
	private Integer downDrivingTime;

	@Column(name = "down_stop_time")
	//	@View(label = "下行停站时间（秒）")
	@ColumnView(name = "下行停站时间(秒)", sortAble = false)
	private Integer downStopTime;

	@Column(name = "down_wait_time")
	//	@View(label = "下行换乘等候时间（秒）")
	@ColumnView(name = "下行换乘等候时间(秒)", sortAble = false)
	private Integer downWaitTime;

	@Column(name = "down_walk_time")
	//	@View(label = "下行换乘步行时间（秒）")
	@ColumnView(name = "下行换乘步行时间(秒)", sortAble = false)
	private Integer downWalkTime;

	@Column(name = "down_comfort_coefficient")
	//	@View(label = "下行舒适度系数")
	@ColumnView(name = "下行舒适度系数", sortAble = false)
	private Double downComfortCoefficient;

	/**
	 * 0:否，1：是
	 */
	@Column(name = "transfer_Flag")
	@ColumnView(name = "是否换乘段", sortAble = false, convertor = TagValueConvertor.class)
	private Short transferFlag;

	/**
	 *
	 */
	public SectionValue() {
		super();
	}

	/**
	 * @return the sectionId
	 */
	public Long getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId
	 *            the sectionId to set
	 */
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
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
	 * @return the stationId
	 */
	public Integer getDownStationId() {
		return downStationId;
	}

	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setDownStationId(Integer downStationId) {
		this.downStationId = downStationId;
	}

	/**
	 * @return the distanceWeight
	 */
	public Integer getPreDistanceWeight() {
		return preDistanceWeight;
	}

	/**
	 * @param distanceWeight
	 *            the distanceWeight to set
	 */
	public void setPreDistanceWeight(Integer preDistanceWeight) {
		this.preDistanceWeight = preDistanceWeight;
	}

	/**
	 * @return the transferFlag
	 */
	public Short getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param transferFlag
	 *            the transferFlag to set
	 */
	public void setTransferFlag(Short transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName
	 *            the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
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
	 * @return the stationName
	 */
	public String getDownStationName() {
		return downStationName;
	}

	/**
	 * @param stationName
	 *            the stationName to set
	 */
	public void setDownStationName(String downStationName) {
		this.downStationName = downStationName;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[路段编号:");
		sb.append(Long.toHexString(sectionId).toUpperCase());
		sb.append(" 所属线路:");
		sb.append(lineName);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * @param downDistanceWeight
	 *            the downDistanceWeight to set
	 */
	public void setDownDistanceWeight(Integer downDistanceWeight) {
		this.downDistanceWeight = downDistanceWeight;
	}

	/**
	 * @return the downDistanceWeight
	 */
	public Integer getDownDistanceWeight() {
		return downDistanceWeight;
	}

	public Integer getPreDrivingTime() {
		return preDrivingTime;
	}

	public void setPreDrivingTime(Integer preDrivingTime) {
		this.preDrivingTime = preDrivingTime;
	}

	public Integer getPreStopTime() {
		return preStopTime;
	}

	public void setPreStopTime(Integer preStopTime) {
		this.preStopTime = preStopTime;
	}

	public Integer getPreWaitTime() {
		return preWaitTime;
	}

	public void setPreWaitTime(Integer preWaitTime) {
		this.preWaitTime = preWaitTime;
	}

	public Integer getPreWalkTime() {
		return preWalkTime;
	}

	public void setPreWalkTime(Integer preWalkTime) {
		this.preWalkTime = preWalkTime;
	}

	public Double getPreComfortCoefficient() {
		return this.preComfortCoefficient;
	}

	public void setPreComfortCoefficient(Double preComfortCoefficient) {
		this.preComfortCoefficient = preComfortCoefficient;
	}

	public Integer getDownDrivingTime() {
		return downDrivingTime;
	}

	public void setDownDrivingTime(Integer downDrivingTime) {
		this.downDrivingTime = downDrivingTime;
	}

	public Integer getDownStopTime() {
		return downStopTime;
	}

	public void setDownStopTime(Integer downStopTime) {
		this.downStopTime = downStopTime;
	}

	public Integer getDownWaitTime() {
		return downWaitTime;
	}

	public void setDownWaitTime(Integer downWaitTime) {
		this.downWaitTime = downWaitTime;
	}

	public Integer getDownWalkTime() {
		return downWalkTime;
	}

	public void setDownWalkTime(Integer downWalkTime) {
		this.downWalkTime = downWalkTime;
	}

	public Double getDownComfortCoefficient() {
		return this.downComfortCoefficient;
	}

	public void setDownComfortCoefficient(Double downComfortCoefficient) {
		this.downComfortCoefficient = downComfortCoefficient;
	}

}
