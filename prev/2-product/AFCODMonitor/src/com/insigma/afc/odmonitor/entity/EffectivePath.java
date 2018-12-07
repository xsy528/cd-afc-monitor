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

import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: 有效路径表实体
 * 
 * @author zhengshuquan
 * @date 2010-9-29
 * @description
 */
@Entity
@Table(name = "TCC_EFFECTIVE_PATHS")
// @SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TCC_EFFECTIVE_PATHS")
public class EffectivePath implements Serializable {

	private static final long serialVersionUID = -7747048277122544144L;

	/**
	 * 有效路径编号
	 */
	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "path_Id")
	@View(label = "有效路径编号")
	private Integer pathId;

	/**
	 * 起始线路号
	 */
	@Column(name = "pre_Line_Id")
	private Short preLineId;

	@Column(name = "pre_Line_Name")
	// @View(label = "起始线路名称")
	private String preLineName;

	/**
	 * 起始车站号
	 */
	@Column(name = "pre_Station_Id")
	private Integer preStationId;

	@Column(name = "pre_Station_Name")
	@View(label = "起始车站名称")
	private String preStationName;

	/**
	 * 目标线路号
	 */
	@Column(name = "line_Id")
	private Short lineId;

	@Column(name = "line_Name")
	// @View(label = "目标线路名称")
	private String lineName;

	/**
	 * 目标车站号
	 */
	@Column(name = "station_Id")
	private Integer stationId;

	@Column(name = "station_Name")
	@View(label = "目标车站名称")
	private String stationName;

	/**
	 * 路径被选择概率 (*100存入数据库）
	 */
	@Column(name = "probability")
	@View(label = "路径被选择概率", postfix = "%", regexp = "[0-9]{0,2}|[1][0]{0,2}")
	private Integer probability;

	/**
	 *
	 */
	public EffectivePath() {
		super();
	}

	/**
	 * @param pathId
	 * @param preLineId
	 * @param preStationId
	 * @param lineId
	 * @param stationId
	 * @param probability
	 */
	public EffectivePath(Integer pathId, Short preLineId, Integer preStationId, Short lineId, Integer stationId,
			Integer probability) {
		super();
		this.pathId = pathId;
		this.preLineId = preLineId;
		this.preStationId = preStationId;
		this.lineId = lineId;
		this.stationId = stationId;
		this.probability = probability;
	}

	/**
	 * @return the pathId
	 */
	public Integer getPathId() {
		return pathId;
	}

	/**
	 * @param pathId
	 *            the pathId to set
	 */
	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}

	/**
	 * @return the preLineId
	 */
	public Short getPreLineId() {
		return preLineId;
	}

	/**
	 * @param preLineId
	 *            the preLineId to set
	 */
	public void setPreLineId(Short preLineId) {
		this.preLineId = preLineId;
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
	 * @return the stationId
	 */
	public Integer getStationId() {
		return stationId;
	}

	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the probability
	 */
	public Integer getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	/**
	 * @return the preLineName
	 */
	public String getPreLineName() {
		return preLineName;
	}

	/**
	 * @param preLineName
	 *            the preLineName to set
	 */
	public void setPreLineName(String preLineName) {
		this.preLineName = preLineName;
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
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param stationName
	 *            the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}
