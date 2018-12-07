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

/**
 * Ticket: TVM票箱钱箱信息表
 * @author  mengyifan
 *
 */
@Entity
@Table(name = "TST_TVM_BOX_STOCKS")
@SuppressWarnings("serial")
public class TstTvmBoxStocks implements Serializable {

	@Id
	/* identifier field */
	@Column(name = "node_Id")
	private Long nodeId;

	@Column(name = "station_Id")
	/* persistent field */
	private Integer stationId;

	@Column(name = "line_Id")
	/* persistent field */
	private Short lineId;

	@Column(name = "upload_Time")
	private Date uploadTime;

	@Column(name = "first_chief_5jiao_quantity")
	private Long firstChief5jiaoQuantity;

	@Column(name = "first_chief_5jiao_money")
	private Long firstChief5jiaoMoney;

	@Column(name = "first_chief_10jiao_quantity")
	private Long firstChief10jiaoQuantity;

	@Column(name = "first_chief_10jiao_money")
	private Long firstChief10jiaoMoney;

	@Column(name = "second_chief_5jiao_quantity")
	private Long secondChief5jiaoQuantity;

	@Column(name = "second_chief_5jiao_money")
	private Long secondChief5jiaoMoney;

	@Column(name = "second_chief_10jiao_quantity")
	private Long secondChief10jiaoQuantity;

	@Column(name = "second_chief_10jiao_money")
	private Long secondChief10jiaoMoney;

	@Column(name = "first_cyc_5jiao_quantity")
	private Long firstCyc5jiaoQuantity;

	@Column(name = "first_cyc_5jiao_money")
	private Long firstCyc5jiaoMoney;

	@Column(name = "first_cyc_10jiao_quantity")
	private Long firstCyc10jiaoQuantity;

	@Column(name = "first_cyc_10jiao_money")
	private Long firstCyc10jiaoMoney;

	@Column(name = "second_cyc_5jiao_quantity")
	private Long secondCyc5jiaoQuantity;

	@Column(name = "second_cyc_5jiao_money")
	private Long secondCyc5jiaoMoney;

	@Column(name = "second_cyc_10jiao_quantity")
	private Long secondCyc10jiaoQuantity;

	@Column(name = "second_cyc_10jiao_money")
	private Long secondCyc10jiaoMoney;

	@Column(name = "recycle_5jiao_quantity")
	private Long recycle5jiaoQuantity;

	@Column(name = "recycle_5jiao_money")
	private Long recycle5jiaoMoney;

	@Column(name = "recycle_10jiao_quantity")
	private Long recycle10jiaoQuantity;

	@Column(name = "recycle_10jiao_money")
	private Long recycle10jiaoMoney;

	@Column(name = "first_resve_5jiao_quantity")
	private Long firstResve5jiaoQuantity;

	@Column(name = "first_resve_5jiao_money")
	private Long firstResve5jiaoMoney;

	@Column(name = "first_resve_10jiao_quantity")
	private Long firstResve10jiaoQuantity;

	@Column(name = "first_resve_10jiao_money")
	private Long firstResve10jiaoMoney;

	@Column(name = "second_resve_5jiao_quantity")
	private Long secondResve5jiaoQuantity;

	@Column(name = "second_resve_5jiao_money")
	private Long secondResve5jiaoMoney;

	@Column(name = "second_resve_10jiao_quantity")
	private Long secondResve10jiaoQuantity;

	@Column(name = "second_resve_10jiao_money")
	private Long secondResve10jiaoMoney;

	@Column(name = "first_chief_1yuan_quantity")
	private Long firstChief1yuanQuantity;

	@Column(name = "first_chief_1yuan_money")
	private Long firstChief1yuanMoney;

	@Column(name = "first_chief_2yuan_quantity")
	private Long firstChief2yuanQuantity;

	@Column(name = "first_chief_2yuan_money")
	private Long firstChief2yuanMoney;

	@Column(name = "first_chief_5yuan_quantity")
	private Long firstChief5yuanQuantity;

	@Column(name = "first_chief_5yuan_money")
	private Long firstChief5yuanMoney;

	@Column(name = "first_chief_10yuan_quantity")
	private Long firstChief10yuanQuantity;

	@Column(name = "first_chief_10yuan_money")
	private Long firstChief10yuanMoney;

	@Column(name = "first_chief_20yuan_quantity")
	private Long firstChief20yuanQuantity;

	@Column(name = "first_chief_20yuan_money")
	private Long firstChief20yuanMoney;

	@Column(name = "first_chief_50yuan_quantity")
	private Long firstChief50yuanQuantity;

	@Column(name = "first_chief_50yuan_money")
	private Long firstChief50yuanMoney;

	@Column(name = "first_chief_100yuan_quantity")
	private Long firstChief100yuanQuantity;

	@Column(name = "first_chief_100yuan_money")
	private Long firstChief100yuanMoney;

	@Column(name = "second_chief_1yuan_quantity")
	private Long secondChief1yuanQuantity;

	@Column(name = "second_chief_1yuan_money")
	private Long secondChief1yuanMoney;

	@Column(name = "second_chief_2yuan_quantity")
	private Long secondChief2yuanQuantity;

	@Column(name = "second_chief_2yuan_money")
	private Long secondChief2yuanMoney;

	@Column(name = "second_chief_5yuan_quantity")
	private Long secondChief5yuanQuantity;

	@Column(name = "second_chief_5yuan_money")
	private Long secondChief5yuanMoney;

	@Column(name = "second_chief_10yuan_quantity")
	private Long secondChief10yuanQuantity;

	@Column(name = "second_chief_10yuan_money")
	private Long secondChief10yuanMoney;

	@Column(name = "second_chief_20yuan_quantity")
	private Long secondChief20yuanQuantity;

	@Column(name = "second_chief_20yuan_money")
	private Long secondChief20yuanMoney;

	@Column(name = "second_chief_50yuan_quantity")
	private Long secondChief50yuanQuantity;

	@Column(name = "second_chief_50yuan_money")
	private Long secondChief50yuanMoney;

	@Column(name = "second_chief_100yuan_quantity")
	private Long secondChief100yuanQuantity;

	@Column(name = "second_chief_100yuan_money")
	private Long secondChief100yuanMoney;

	@Column(name = "first_cyc_1yuan_quantity")
	private Long firstCyc1yuanQuantity;

	@Column(name = "first_cyc_1yuan_money")
	private Long firstCyc1yuanMoney;

	@Column(name = "first_cyc_2yuan_quantity")
	private Long firstCyc2yuanQuantity;

	@Column(name = "first_cyc_2yuan_money")
	private Long firstCyc2yuanMoney;

	@Column(name = "first_cyc_5yuan_quantity")
	private Long firstCyc5yuanQuantity;

	@Column(name = "first_cyc_5yuan_money")
	private Long firstCyc5yuanMoney;

	@Column(name = "first_cyc_10yuan_quantity")
	private Long firstCyc10yuanQuantity;

	@Column(name = "first_cyc_10yuan_money")
	private Long firstCyc10yuanMoney;

	@Column(name = "first_cyc_20yuan_quantity")
	private Long firstCyc20yuanQuantity;

	@Column(name = "first_cyc_20yuan_money")
	private Long firstCyc20yuanMoney;

	@Column(name = "first_cyc_50yuan_quantity")
	private Long firstCyc50yuanQuantity;

	@Column(name = "first_cyc_50yuan_money")
	private Long firstCyc50yuanMoney;

	@Column(name = "first_cyc_100yuan_quantity")
	private Long firstCyc100yuanQuantity;

	@Column(name = "first_cyc_100yuan_money")
	private Long firstCyc100yuanMoney;

	@Column(name = "second_cyc_1yuan_quantity")
	private Long secondCyc1yuanQuantity;

	@Column(name = "second_cyc_1yuan_money")
	private Long secondCyc1yuanMoney;

	@Column(name = "second_cyc_2yuan_quantity")
	private Long secondCyc2yuanQuantity;

	@Column(name = "second_cyc_2yuan_money")
	private Long secondCyc2yuanMoney;

	@Column(name = "second_cyc_5yuan_quantity")
	private Long secondCyc5yuanQuantity;

	@Column(name = "second_cyc_5yuan_money")
	private Long secondCyc5yuanMoney;

	@Column(name = "second_cyc_10yuan_quantity")
	private Long secondCyc10yuanQuantity;

	@Column(name = "second_cyc_10yuan_money")
	private Long secondCyc10yuanMoney;

	@Column(name = "second_cyc_20yuan_quantity")
	private Long secondCyc20yuanQuantity;

	@Column(name = "second_cyc_20yuan_money")
	private Long secondCyc20yuanMoney;

	@Column(name = "second_cyc_50yuan_quantity")
	private Long secondCyc50yuanQuantity;

	@Column(name = "second_cyc_50yuan_money")
	private Long secondCyc50yuanMoney;

	@Column(name = "second_cyc_100yuan_quantity")
	private Long secondCyc100yuanQuantity;

	@Column(name = "second_cyc_100yuan_money")
	private Long secondCyc100yuanMoney;

	@Column(name = "recycle_1yuan_quantity")
	private Long recycle1yuanQuantity;

	@Column(name = "recycle_1yuan_money")
	private Long recycle1yuanMoney;

	@Column(name = "recycle_2yuan_quantity")
	private Long recycle2yuanQuantity;

	@Column(name = "recycle_2yuan_money")
	private Long recycle2yuanMoney;

	@Column(name = "recycle_5yuan_quantity")
	private Long recycle5yuanQuantity;

	@Column(name = "recycle_5yuan_money")
	private Long recycle5yuanMoney;

	@Column(name = "recycle_10yuan_quantity")
	private Long recycle10yuanQuantity;

	@Column(name = "recycle_10yuan_money")
	private Long recycle10yuanMoney;

	@Column(name = "recycle_20yuan_quantity")
	private Long recycle20yuanQuantity;

	@Column(name = "recycle_20yuan_money")
	private Long recycle20yuanMoney;

	@Column(name = "recycle_50yuan_quantity")
	private Long recycle50yuanQuantity;

	@Column(name = "recycle_50yuan_money")
	private Long recycle50yuanMoney;

	@Column(name = "recycle_100yuan_quantity")
	private Long recycle100yuanQuantity;

	@Column(name = "recycle_100yuan_money")
	private Long recycle100yuanMoney;

	@Column(name = "first_resve_1yuan_quantity")
	private Long firstResve1yuanQuantity;

	@Column(name = "first_resve_1yuan_money")
	private Long firstResve1yuanMoney;

	@Column(name = "first_resve_2yuan_quantity")
	private Long firstResve2yuanQuantity;

	@Column(name = "first_resve_2yuan_money")
	private Long firstResve2yuanMoney;

	@Column(name = "first_resve_5yuan_quantity")
	private Long firstResve5yuanQuantity;

	@Column(name = "first_resve_5yuan_money")
	private Long firstResve5yuanMoney;

	@Column(name = "first_resve_10yuan_quantity")
	private Long firstResve10yuanQuantity;

	@Column(name = "first_resve_10yuan_money")
	private Long firstResve10yuanMoney;

	@Column(name = "first_resve_20yuan_quantity")
	private Long firstResve20yuanQuantity;

	@Column(name = "first_resve_20yuan_money")
	private Long firstResve20yuanMoney;

	@Column(name = "first_resve_50yuan_quantity")
	private Long firstResve50yuanQuantity;

	@Column(name = "first_resve_50yuan_money")
	private Long firstResve50yuanMoney;

	@Column(name = "first_resve_100yuan_quantity")
	private Long firstResve100yuanQuantity;

	@Column(name = "first_resve_100yuan_money")
	private Long firstResve100yuanMoney;

	@Column(name = "second_resve_1yuan_quantity")
	private Long secondResve1yuanQuantity;

	@Column(name = "second_resve_1yuan_money")
	private Long secondResve1yuanMoney;

	@Column(name = "second_resve_2yuan_quantity")
	private Long secondResve2yuanQuantity;

	@Column(name = "second_resve_2yuan_money")
	private Long secondResve2yuanMoney;

	@Column(name = "second_resve_5yuan_quantity")
	private Long secondResve5yuanQuantity;

	@Column(name = "second_resve_5yuan_money")
	private Long secondResve5yuanMoney;

	@Column(name = "second_resve_10yuan_quantity")
	private Long secondResve10yuanQuantity;

	@Column(name = "second_resve_10yuan_money")
	private Long secondResve10yuanMoney;

	@Column(name = "second_resve_20yuan_quantity")
	private Long secondResve20yuanQuantity;

	@Column(name = "second_resve_20yuan_money")
	private Long secondResve20yuanMoney;

	@Column(name = "second_resve_50yuan_quantity")
	private Long secondResve50yuanQuantity;

	@Column(name = "second_resve_50yuan_money")
	private Long secondResve50yuanMoney;

	@Column(name = "second_resve_100yuan_quantity")
	private Long secondResve100yuanQuantity;

	@Column(name = "second_resve_100yuan_money")
	private Long secondResve100yuanMoney;

	@Column(name = "ignore_1yuan_quantity")
	private Long ignore1yuanQuantity;

	@Column(name = "ignore_1yuan_money")
	private Long ignore1yuanMoney;

	@Column(name = "ignore_2yuan_quantity")
	private Long ignore2yuanQuantity;

	@Column(name = "ignore_2yuan_money")
	private Long ignore2yuanMoney;

	@Column(name = "ignore_5yuan_quantity")
	private Long ignore5yuanQuantity;

	@Column(name = "ignore_5yuan_money")
	private Long ignore5yuanMoney;

	@Column(name = "ignore_10yuan_quantity")
	private Long ignore10yuanQuantity;

	@Column(name = "ignore_10yuan_money")
	private Long ignore10yuanMoney;

	@Column(name = "ignore_20yuan_quantity")
	private Long ignore20yuanQuantity;

	@Column(name = "ignore_20yuan_money")
	private Long ignore20yuanMoney;

	@Column(name = "ignore_50yuan_quantity")
	private Long ignore50yuanQuantity;

	@Column(name = "ignore_50yuan_money")
	private Long ignore50yuanMoney;

	@Column(name = "ignore_100yuan_quantity")
	private Long ignore100yuanQuantity;

	@Column(name = "ignore_100yuan_money")
	private Long ignore100yuanMoney;

	@Column(name = "first_ticket_quantity")
	private Long firstTicketQuantity;

	@Column(name = "second_ticket_quantity")
	private Long secondTicketQuantity;

	@Column(name = "third_ticket_quantity")
	private Long thirdTicketQuantity;

	/** default constructor */
	public TstTvmBoxStocks() {
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the stationId
	 */
	public Integer getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the lineId
	 */
	public Short getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the firstChief5jiaoQuantity
	 */
	public Long getFirstChief5jiaoQuantity() {
		return firstChief5jiaoQuantity;
	}

	/**
	 * @param firstChief5jiaoQuantity the firstChief5jiaoQuantity to set
	 */
	public void setFirstChief5jiaoQuantity(Long firstChief5jiaoQuantity) {
		this.firstChief5jiaoQuantity = firstChief5jiaoQuantity;
	}

	/**
	 * @return the firstChief5jiaoMoney
	 */
	public Long getFirstChief5jiaoMoney() {
		return firstChief5jiaoMoney;
	}

	/**
	 * @param firstChief5jiaoMoney the firstChief5jiaoMoney to set
	 */
	public void setFirstChief5jiaoMoney(Long firstChief5jiaoMoney) {
		this.firstChief5jiaoMoney = firstChief5jiaoMoney;
	}

	/**
	 * @return the firstChief10jiaoQuantity
	 */
	public Long getFirstChief10jiaoQuantity() {
		return firstChief10jiaoQuantity;
	}

	/**
	 * @param firstChief10jiaoQuantity the firstChief10jiaoQuantity to set
	 */
	public void setFirstChief10jiaoQuantity(Long firstChief10jiaoQuantity) {
		this.firstChief10jiaoQuantity = firstChief10jiaoQuantity;
	}

	/**
	 * @return the firstChief10jiaoMoney
	 */
	public Long getFirstChief10jiaoMoney() {
		return firstChief10jiaoMoney;
	}

	/**
	 * @param firstChief10jiaoMoney the firstChief10jiaoMoney to set
	 */
	public void setFirstChief10jiaoMoney(Long firstChief10jiaoMoney) {
		this.firstChief10jiaoMoney = firstChief10jiaoMoney;
	}

	/**
	 * @return the secondChief5jiaoQuantity
	 */
	public Long getSecondChief5jiaoQuantity() {
		return secondChief5jiaoQuantity;
	}

	/**
	 * @param secondChief5jiaoQuantity the secondChief5jiaoQuantity to set
	 */
	public void setSecondChief5jiaoQuantity(Long secondChief5jiaoQuantity) {
		this.secondChief5jiaoQuantity = secondChief5jiaoQuantity;
	}

	/**
	 * @return the secondChief5jiaoMoney
	 */
	public Long getSecondChief5jiaoMoney() {
		return secondChief5jiaoMoney;
	}

	/**
	 * @param secondChief5jiaoMoney the secondChief5jiaoMoney to set
	 */
	public void setSecondChief5jiaoMoney(Long secondChief5jiaoMoney) {
		this.secondChief5jiaoMoney = secondChief5jiaoMoney;
	}

	/**
	 * @return the secondChief10jiaoQuantity
	 */
	public Long getSecondChief10jiaoQuantity() {
		return secondChief10jiaoQuantity;
	}

	/**
	 * @param secondChief10jiaoQuantity the secondChief10jiaoQuantity to set
	 */
	public void setSecondChief10jiaoQuantity(Long secondChief10jiaoQuantity) {
		this.secondChief10jiaoQuantity = secondChief10jiaoQuantity;
	}

	/**
	 * @return the secondChief10jiaoMoney
	 */
	public Long getSecondChief10jiaoMoney() {
		return secondChief10jiaoMoney;
	}

	/**
	 * @param secondChief10jiaoMoney the secondChief10jiaoMoney to set
	 */
	public void setSecondChief10jiaoMoney(Long secondChief10jiaoMoney) {
		this.secondChief10jiaoMoney = secondChief10jiaoMoney;
	}

	/**
	 * @return the firstCyc5jiaoQuantity
	 */
	public Long getFirstCyc5jiaoQuantity() {
		return firstCyc5jiaoQuantity;
	}

	/**
	 * @param firstCyc5jiaoQuantity the firstCyc5jiaoQuantity to set
	 */
	public void setFirstCyc5jiaoQuantity(Long firstCyc5jiaoQuantity) {
		this.firstCyc5jiaoQuantity = firstCyc5jiaoQuantity;
	}

	/**
	 * @return the firstCyc5jiaoMoney
	 */
	public Long getFirstCyc5jiaoMoney() {
		return firstCyc5jiaoMoney;
	}

	/**
	 * @param firstCyc5jiaoMoney the firstCyc5jiaoMoney to set
	 */
	public void setFirstCyc5jiaoMoney(Long firstCyc5jiaoMoney) {
		this.firstCyc5jiaoMoney = firstCyc5jiaoMoney;
	}

	/**
	 * @return the firstCyc10jiaoQuantity
	 */
	public Long getFirstCyc10jiaoQuantity() {
		return firstCyc10jiaoQuantity;
	}

	/**
	 * @param firstCyc10jiaoQuantity the firstCyc10jiaoQuantity to set
	 */
	public void setFirstCyc10jiaoQuantity(Long firstCyc10jiaoQuantity) {
		this.firstCyc10jiaoQuantity = firstCyc10jiaoQuantity;
	}

	/**
	 * @return the firstCyc10jiaoMoney
	 */
	public Long getFirstCyc10jiaoMoney() {
		return firstCyc10jiaoMoney;
	}

	/**
	 * @param firstCyc10jiaoMoney the firstCyc10jiaoMoney to set
	 */
	public void setFirstCyc10jiaoMoney(Long firstCyc10jiaoMoney) {
		this.firstCyc10jiaoMoney = firstCyc10jiaoMoney;
	}

	/**
	 * @return the secondCyc5jiaoQuantity
	 */
	public Long getSecondCyc5jiaoQuantity() {
		return secondCyc5jiaoQuantity;
	}

	/**
	 * @param secondCyc5jiaoQuantity the secondCyc5jiaoQuantity to set
	 */
	public void setSecondCyc5jiaoQuantity(Long secondCyc5jiaoQuantity) {
		this.secondCyc5jiaoQuantity = secondCyc5jiaoQuantity;
	}

	/**
	 * @return the secondCyc5jiaoMoney
	 */
	public Long getSecondCyc5jiaoMoney() {
		return secondCyc5jiaoMoney;
	}

	/**
	 * @param secondCyc5jiaoMoney the secondCyc5jiaoMoney to set
	 */
	public void setSecondCyc5jiaoMoney(Long secondCyc5jiaoMoney) {
		this.secondCyc5jiaoMoney = secondCyc5jiaoMoney;
	}

	/**
	 * @return the secondCyc10jiaoQuantity
	 */
	public Long getSecondCyc10jiaoQuantity() {
		return secondCyc10jiaoQuantity;
	}

	/**
	 * @param secondCyc10jiaoQuantity the secondCyc10jiaoQuantity to set
	 */
	public void setSecondCyc10jiaoQuantity(Long secondCyc10jiaoQuantity) {
		this.secondCyc10jiaoQuantity = secondCyc10jiaoQuantity;
	}

	/**
	 * @return the secondCyc10jiaoMoney
	 */
	public Long getSecondCyc10jiaoMoney() {
		return secondCyc10jiaoMoney;
	}

	/**
	 * @param secondCyc10jiaoMoney the secondCyc10jiaoMoney to set
	 */
	public void setSecondCyc10jiaoMoney(Long secondCyc10jiaoMoney) {
		this.secondCyc10jiaoMoney = secondCyc10jiaoMoney;
	}

	/**
	 * @return the recycle5jiaoQuantity
	 */
	public Long getRecycle5jiaoQuantity() {
		return recycle5jiaoQuantity;
	}

	/**
	 * @param recycle5jiaoQuantity the recycle5jiaoQuantity to set
	 */
	public void setRecycle5jiaoQuantity(Long recycle5jiaoQuantity) {
		this.recycle5jiaoQuantity = recycle5jiaoQuantity;
	}

	/**
	 * @return the recycle5jiaoMoney
	 */
	public Long getRecycle5jiaoMoney() {
		return recycle5jiaoMoney;
	}

	/**
	 * @param recycle5jiaoMoney the recycle5jiaoMoney to set
	 */
	public void setRecycle5jiaoMoney(Long recycle5jiaoMoney) {
		this.recycle5jiaoMoney = recycle5jiaoMoney;
	}

	/**
	 * @return the recycle10jiaoQuantity
	 */
	public Long getRecycle10jiaoQuantity() {
		return recycle10jiaoQuantity;
	}

	/**
	 * @param recycle10jiaoQuantity the recycle10jiaoQuantity to set
	 */
	public void setRecycle10jiaoQuantity(Long recycle10jiaoQuantity) {
		this.recycle10jiaoQuantity = recycle10jiaoQuantity;
	}

	/**
	 * @return the recycle10jiaoMoney
	 */
	public Long getRecycle10jiaoMoney() {
		return recycle10jiaoMoney;
	}

	/**
	 * @param recycle10jiaoMoney the recycle10jiaoMoney to set
	 */
	public void setRecycle10jiaoMoney(Long recycle10jiaoMoney) {
		this.recycle10jiaoMoney = recycle10jiaoMoney;
	}

	/**
	 * @return the firstResve5jiaoQuantity
	 */
	public Long getFirstResve5jiaoQuantity() {
		return firstResve5jiaoQuantity;
	}

	/**
	 * @param firstResve5jiaoQuantity the firstResve5jiaoQuantity to set
	 */
	public void setFirstResve5jiaoQuantity(Long firstResve5jiaoQuantity) {
		this.firstResve5jiaoQuantity = firstResve5jiaoQuantity;
	}

	/**
	 * @return the firstResve5jiaoMoney
	 */
	public Long getFirstResve5jiaoMoney() {
		return firstResve5jiaoMoney;
	}

	/**
	 * @param firstResve5jiaoMoney the firstResve5jiaoMoney to set
	 */
	public void setFirstResve5jiaoMoney(Long firstResve5jiaoMoney) {
		this.firstResve5jiaoMoney = firstResve5jiaoMoney;
	}

	/**
	 * @return the firstResve10jiaoQuantity
	 */
	public Long getFirstResve10jiaoQuantity() {
		return firstResve10jiaoQuantity;
	}

	/**
	 * @param firstResve10jiaoQuantity the firstResve10jiaoQuantity to set
	 */
	public void setFirstResve10jiaoQuantity(Long firstResve10jiaoQuantity) {
		this.firstResve10jiaoQuantity = firstResve10jiaoQuantity;
	}

	/**
	 * @return the firstResve10jiaoMoney
	 */
	public Long getFirstResve10jiaoMoney() {
		return firstResve10jiaoMoney;
	}

	/**
	 * @param firstResve10jiaoMoney the firstResve10jiaoMoney to set
	 */
	public void setFirstResve10jiaoMoney(Long firstResve10jiaoMoney) {
		this.firstResve10jiaoMoney = firstResve10jiaoMoney;
	}

	/**
	 * @return the secondResve5jiaoQuantity
	 */
	public Long getSecondResve5jiaoQuantity() {
		return secondResve5jiaoQuantity;
	}

	/**
	 * @param secondResve5jiaoQuantity the secondResve5jiaoQuantity to set
	 */
	public void setSecondResve5jiaoQuantity(Long secondResve5jiaoQuantity) {
		this.secondResve5jiaoQuantity = secondResve5jiaoQuantity;
	}

	/**
	 * @return the secondResve5jiaoMoney
	 */
	public Long getSecondResve5jiaoMoney() {
		return secondResve5jiaoMoney;
	}

	/**
	 * @param secondResve5jiaoMoney the secondResve5jiaoMoney to set
	 */
	public void setSecondResve5jiaoMoney(Long secondResve5jiaoMoney) {
		this.secondResve5jiaoMoney = secondResve5jiaoMoney;
	}

	/**
	 * @return the secondResve10jiaoQuantity
	 */
	public Long getSecondResve10jiaoQuantity() {
		return secondResve10jiaoQuantity;
	}

	/**
	 * @param secondResve10jiaoQuantity the secondResve10jiaoQuantity to set
	 */
	public void setSecondResve10jiaoQuantity(Long secondResve10jiaoQuantity) {
		this.secondResve10jiaoQuantity = secondResve10jiaoQuantity;
	}

	/**
	 * @return the secondResve10jiaoMoney
	 */
	public Long getSecondResve10jiaoMoney() {
		return secondResve10jiaoMoney;
	}

	/**
	 * @param secondResve10jiaoMoney the secondResve10jiaoMoney to set
	 */
	public void setSecondResve10jiaoMoney(Long secondResve10jiaoMoney) {
		this.secondResve10jiaoMoney = secondResve10jiaoMoney;
	}

	/**
	 * @return the firstChief1yuanQuantity
	 */
	public Long getFirstChief1yuanQuantity() {
		return firstChief1yuanQuantity;
	}

	/**
	 * @param firstChief1yuanQuantity the firstChief1yuanQuantity to set
	 */
	public void setFirstChief1yuanQuantity(Long firstChief1yuanQuantity) {
		this.firstChief1yuanQuantity = firstChief1yuanQuantity;
	}

	/**
	 * @return the firstChief1yuanMoney
	 */
	public Long getFirstChief1yuanMoney() {
		return firstChief1yuanMoney;
	}

	/**
	 * @param firstChief1yuanMoney the firstChief1yuanMoney to set
	 */
	public void setFirstChief1yuanMoney(Long firstChief1yuanMoney) {
		this.firstChief1yuanMoney = firstChief1yuanMoney;
	}

	/**
	 * @return the firstChief2yuanQuantity
	 */
	public Long getFirstChief2yuanQuantity() {
		return firstChief2yuanQuantity;
	}

	/**
	 * @param firstChief2yuanQuantity the firstChief2yuanQuantity to set
	 */
	public void setFirstChief2yuanQuantity(Long firstChief2yuanQuantity) {
		this.firstChief2yuanQuantity = firstChief2yuanQuantity;
	}

	/**
	 * @return the firstChief2yuanMoney
	 */
	public Long getFirstChief2yuanMoney() {
		return firstChief2yuanMoney;
	}

	/**
	 * @param firstChief2yuanMoney the firstChief2yuanMoney to set
	 */
	public void setFirstChief2yuanMoney(Long firstChief2yuanMoney) {
		this.firstChief2yuanMoney = firstChief2yuanMoney;
	}

	/**
	 * @return the firstChief5yuanQuantity
	 */
	public Long getFirstChief5yuanQuantity() {
		return firstChief5yuanQuantity;
	}

	/**
	 * @param firstChief5yuanQuantity the firstChief5yuanQuantity to set
	 */
	public void setFirstChief5yuanQuantity(Long firstChief5yuanQuantity) {
		this.firstChief5yuanQuantity = firstChief5yuanQuantity;
	}

	/**
	 * @return the firstChief5yuanMoney
	 */
	public Long getFirstChief5yuanMoney() {
		return firstChief5yuanMoney;
	}

	/**
	 * @param firstChief5yuanMoney the firstChief5yuanMoney to set
	 */
	public void setFirstChief5yuanMoney(Long firstChief5yuanMoney) {
		this.firstChief5yuanMoney = firstChief5yuanMoney;
	}

	/**
	 * @return the firstChief10yuanQuantity
	 */
	public Long getFirstChief10yuanQuantity() {
		return firstChief10yuanQuantity;
	}

	/**
	 * @param firstChief10yuanQuantity the firstChief10yuanQuantity to set
	 */
	public void setFirstChief10yuanQuantity(Long firstChief10yuanQuantity) {
		this.firstChief10yuanQuantity = firstChief10yuanQuantity;
	}

	/**
	 * @return the firstChief10yuanMoney
	 */
	public Long getFirstChief10yuanMoney() {
		return firstChief10yuanMoney;
	}

	/**
	 * @param firstChief10yuanMoney the firstChief10yuanMoney to set
	 */
	public void setFirstChief10yuanMoney(Long firstChief10yuanMoney) {
		this.firstChief10yuanMoney = firstChief10yuanMoney;
	}

	/**
	 * @return the firstChief20yuanQuantity
	 */
	public Long getFirstChief20yuanQuantity() {
		return firstChief20yuanQuantity;
	}

	/**
	 * @param firstChief20yuanQuantity the firstChief20yuanQuantity to set
	 */
	public void setFirstChief20yuanQuantity(Long firstChief20yuanQuantity) {
		this.firstChief20yuanQuantity = firstChief20yuanQuantity;
	}

	/**
	 * @return the firstChief20yuanMoney
	 */
	public Long getFirstChief20yuanMoney() {
		return firstChief20yuanMoney;
	}

	/**
	 * @param firstChief20yuanMoney the firstChief20yuanMoney to set
	 */
	public void setFirstChief20yuanMoney(Long firstChief20yuanMoney) {
		this.firstChief20yuanMoney = firstChief20yuanMoney;
	}

	/**
	 * @return the firstChief50yuanQuantity
	 */
	public Long getFirstChief50yuanQuantity() {
		return firstChief50yuanQuantity;
	}

	/**
	 * @param firstChief50yuanQuantity the firstChief50yuanQuantity to set
	 */
	public void setFirstChief50yuanQuantity(Long firstChief50yuanQuantity) {
		this.firstChief50yuanQuantity = firstChief50yuanQuantity;
	}

	/**
	 * @return the firstChief50yuanMoney
	 */
	public Long getFirstChief50yuanMoney() {
		return firstChief50yuanMoney;
	}

	/**
	 * @param firstChief50yuanMoney the firstChief50yuanMoney to set
	 */
	public void setFirstChief50yuanMoney(Long firstChief50yuanMoney) {
		this.firstChief50yuanMoney = firstChief50yuanMoney;
	}

	/**
	 * @return the firstChief100yuanQuantity
	 */
	public Long getFirstChief100yuanQuantity() {
		return firstChief100yuanQuantity;
	}

	/**
	 * @param firstChief100yuanQuantity the firstChief100yuanQuantity to set
	 */
	public void setFirstChief100yuanQuantity(Long firstChief100yuanQuantity) {
		this.firstChief100yuanQuantity = firstChief100yuanQuantity;
	}

	/**
	 * @return the firstChief100yuanMoney
	 */
	public Long getFirstChief100yuanMoney() {
		return firstChief100yuanMoney;
	}

	/**
	 * @param firstChief100yuanMoney the firstChief100yuanMoney to set
	 */
	public void setFirstChief100yuanMoney(Long firstChief100yuanMoney) {
		this.firstChief100yuanMoney = firstChief100yuanMoney;
	}

	/**
	 * @return the secondChief1yuanQuantity
	 */
	public Long getSecondChief1yuanQuantity() {
		return secondChief1yuanQuantity;
	}

	/**
	 * @param secondChief1yuanQuantity the secondChief1yuanQuantity to set
	 */
	public void setSecondChief1yuanQuantity(Long secondChief1yuanQuantity) {
		this.secondChief1yuanQuantity = secondChief1yuanQuantity;
	}

	/**
	 * @return the secondChief1yuanMoney
	 */
	public Long getSecondChief1yuanMoney() {
		return secondChief1yuanMoney;
	}

	/**
	 * @param secondChief1yuanMoney the secondChief1yuanMoney to set
	 */
	public void setSecondChief1yuanMoney(Long secondChief1yuanMoney) {
		this.secondChief1yuanMoney = secondChief1yuanMoney;
	}

	/**
	 * @return the secondChief2yuanQuantity
	 */
	public Long getSecondChief2yuanQuantity() {
		return secondChief2yuanQuantity;
	}

	/**
	 * @param secondChief2yuanQuantity the secondChief2yuanQuantity to set
	 */
	public void setSecondChief2yuanQuantity(Long secondChief2yuanQuantity) {
		this.secondChief2yuanQuantity = secondChief2yuanQuantity;
	}

	/**
	 * @return the secondChief2yuanMoney
	 */
	public Long getSecondChief2yuanMoney() {
		return secondChief2yuanMoney;
	}

	/**
	 * @param secondChief2yuanMoney the secondChief2yuanMoney to set
	 */
	public void setSecondChief2yuanMoney(Long secondChief2yuanMoney) {
		this.secondChief2yuanMoney = secondChief2yuanMoney;
	}

	/**
	 * @return the secondChief5yuanQuantity
	 */
	public Long getSecondChief5yuanQuantity() {
		return secondChief5yuanQuantity;
	}

	/**
	 * @param secondChief5yuanQuantity the secondChief5yuanQuantity to set
	 */
	public void setSecondChief5yuanQuantity(Long secondChief5yuanQuantity) {
		this.secondChief5yuanQuantity = secondChief5yuanQuantity;
	}

	/**
	 * @return the secondChief5yuanMoney
	 */
	public Long getSecondChief5yuanMoney() {
		return secondChief5yuanMoney;
	}

	/**
	 * @param secondChief5yuanMoney the secondChief5yuanMoney to set
	 */
	public void setSecondChief5yuanMoney(Long secondChief5yuanMoney) {
		this.secondChief5yuanMoney = secondChief5yuanMoney;
	}

	/**
	 * @return the secondChief10yuanQuantity
	 */
	public Long getSecondChief10yuanQuantity() {
		return secondChief10yuanQuantity;
	}

	/**
	 * @param secondChief10yuanQuantity the secondChief10yuanQuantity to set
	 */
	public void setSecondChief10yuanQuantity(Long secondChief10yuanQuantity) {
		this.secondChief10yuanQuantity = secondChief10yuanQuantity;
	}

	/**
	 * @return the secondChief10yuanMoney
	 */
	public Long getSecondChief10yuanMoney() {
		return secondChief10yuanMoney;
	}

	/**
	 * @param secondChief10yuanMoney the secondChief10yuanMoney to set
	 */
	public void setSecondChief10yuanMoney(Long secondChief10yuanMoney) {
		this.secondChief10yuanMoney = secondChief10yuanMoney;
	}

	/**
	 * @return the secondChief20yuanQuantity
	 */
	public Long getSecondChief20yuanQuantity() {
		return secondChief20yuanQuantity;
	}

	/**
	 * @param secondChief20yuanQuantity the secondChief20yuanQuantity to set
	 */
	public void setSecondChief20yuanQuantity(Long secondChief20yuanQuantity) {
		this.secondChief20yuanQuantity = secondChief20yuanQuantity;
	}

	/**
	 * @return the secondChief20yuanMoney
	 */
	public Long getSecondChief20yuanMoney() {
		return secondChief20yuanMoney;
	}

	/**
	 * @param secondChief20yuanMoney the secondChief20yuanMoney to set
	 */
	public void setSecondChief20yuanMoney(Long secondChief20yuanMoney) {
		this.secondChief20yuanMoney = secondChief20yuanMoney;
	}

	/**
	 * @return the secondChief50yuanQuantity
	 */
	public Long getSecondChief50yuanQuantity() {
		return secondChief50yuanQuantity;
	}

	/**
	 * @param secondChief50yuanQuantity the secondChief50yuanQuantity to set
	 */
	public void setSecondChief50yuanQuantity(Long secondChief50yuanQuantity) {
		this.secondChief50yuanQuantity = secondChief50yuanQuantity;
	}

	/**
	 * @return the secondChief50yuanMoney
	 */
	public Long getSecondChief50yuanMoney() {
		return secondChief50yuanMoney;
	}

	/**
	 * @param secondChief50yuanMoney the secondChief50yuanMoney to set
	 */
	public void setSecondChief50yuanMoney(Long secondChief50yuanMoney) {
		this.secondChief50yuanMoney = secondChief50yuanMoney;
	}

	/**
	 * @return the secondChief100yuanQuantity
	 */
	public Long getSecondChief100yuanQuantity() {
		return secondChief100yuanQuantity;
	}

	/**
	 * @param secondChief100yuanQuantity the secondChief100yuanQuantity to set
	 */
	public void setSecondChief100yuanQuantity(Long secondChief100yuanQuantity) {
		this.secondChief100yuanQuantity = secondChief100yuanQuantity;
	}

	/**
	 * @return the secondChief100yuanMoney
	 */
	public Long getSecondChief100yuanMoney() {
		return secondChief100yuanMoney;
	}

	/**
	 * @param secondChief100yuanMoney the secondChief100yuanMoney to set
	 */
	public void setSecondChief100yuanMoney(Long secondChief100yuanMoney) {
		this.secondChief100yuanMoney = secondChief100yuanMoney;
	}

	/**
	 * @return the firstCyc1yuanQuantity
	 */
	public Long getFirstCyc1yuanQuantity() {
		return firstCyc1yuanQuantity;
	}

	/**
	 * @param firstCyc1yuanQuantity the firstCyc1yuanQuantity to set
	 */
	public void setFirstCyc1yuanQuantity(Long firstCyc1yuanQuantity) {
		this.firstCyc1yuanQuantity = firstCyc1yuanQuantity;
	}

	/**
	 * @return the firstCyc1yuanMoney
	 */
	public Long getFirstCyc1yuanMoney() {
		return firstCyc1yuanMoney;
	}

	/**
	 * @param firstCyc1yuanMoney the firstCyc1yuanMoney to set
	 */
	public void setFirstCyc1yuanMoney(Long firstCyc1yuanMoney) {
		this.firstCyc1yuanMoney = firstCyc1yuanMoney;
	}

	/**
	 * @return the firstCyc2yuanQuantity
	 */
	public Long getFirstCyc2yuanQuantity() {
		return firstCyc2yuanQuantity;
	}

	/**
	 * @param firstCyc2yuanQuantity the firstCyc2yuanQuantity to set
	 */
	public void setFirstCyc2yuanQuantity(Long firstCyc2yuanQuantity) {
		this.firstCyc2yuanQuantity = firstCyc2yuanQuantity;
	}

	/**
	 * @return the firstCyc2yuanMoney
	 */
	public Long getFirstCyc2yuanMoney() {
		return firstCyc2yuanMoney;
	}

	/**
	 * @param firstCyc2yuanMoney the firstCyc2yuanMoney to set
	 */
	public void setFirstCyc2yuanMoney(Long firstCyc2yuanMoney) {
		this.firstCyc2yuanMoney = firstCyc2yuanMoney;
	}

	/**
	 * @return the firstCyc5yuanQuantity
	 */
	public Long getFirstCyc5yuanQuantity() {
		return firstCyc5yuanQuantity;
	}

	/**
	 * @param firstCyc5yuanQuantity the firstCyc5yuanQuantity to set
	 */
	public void setFirstCyc5yuanQuantity(Long firstCyc5yuanQuantity) {
		this.firstCyc5yuanQuantity = firstCyc5yuanQuantity;
	}

	/**
	 * @return the firstCyc5yuanMoney
	 */
	public Long getFirstCyc5yuanMoney() {
		return firstCyc5yuanMoney;
	}

	/**
	 * @param firstCyc5yuanMoney the firstCyc5yuanMoney to set
	 */
	public void setFirstCyc5yuanMoney(Long firstCyc5yuanMoney) {
		this.firstCyc5yuanMoney = firstCyc5yuanMoney;
	}

	/**
	 * @return the firstCyc10yuanQuantity
	 */
	public Long getFirstCyc10yuanQuantity() {
		return firstCyc10yuanQuantity;
	}

	/**
	 * @param firstCyc10yuanQuantity the firstCyc10yuanQuantity to set
	 */
	public void setFirstCyc10yuanQuantity(Long firstCyc10yuanQuantity) {
		this.firstCyc10yuanQuantity = firstCyc10yuanQuantity;
	}

	/**
	 * @return the firstCyc10yuanMoney
	 */
	public Long getFirstCyc10yuanMoney() {
		return firstCyc10yuanMoney;
	}

	/**
	 * @param firstCyc10yuanMoney the firstCyc10yuanMoney to set
	 */
	public void setFirstCyc10yuanMoney(Long firstCyc10yuanMoney) {
		this.firstCyc10yuanMoney = firstCyc10yuanMoney;
	}

	/**
	 * @return the firstCyc20yuanQuantity
	 */
	public Long getFirstCyc20yuanQuantity() {
		return firstCyc20yuanQuantity;
	}

	/**
	 * @param firstCyc20yuanQuantity the firstCyc20yuanQuantity to set
	 */
	public void setFirstCyc20yuanQuantity(Long firstCyc20yuanQuantity) {
		this.firstCyc20yuanQuantity = firstCyc20yuanQuantity;
	}

	/**
	 * @return the firstCyc20yuanMoney
	 */
	public Long getFirstCyc20yuanMoney() {
		return firstCyc20yuanMoney;
	}

	/**
	 * @param firstCyc20yuanMoney the firstCyc20yuanMoney to set
	 */
	public void setFirstCyc20yuanMoney(Long firstCyc20yuanMoney) {
		this.firstCyc20yuanMoney = firstCyc20yuanMoney;
	}

	/**
	 * @return the firstCyc50yuanQuantity
	 */
	public Long getFirstCyc50yuanQuantity() {
		return firstCyc50yuanQuantity;
	}

	/**
	 * @param firstCyc50yuanQuantity the firstCyc50yuanQuantity to set
	 */
	public void setFirstCyc50yuanQuantity(Long firstCyc50yuanQuantity) {
		this.firstCyc50yuanQuantity = firstCyc50yuanQuantity;
	}

	/**
	 * @return the firstCyc50yuanMoney
	 */
	public Long getFirstCyc50yuanMoney() {
		return firstCyc50yuanMoney;
	}

	/**
	 * @param firstCyc50yuanMoney the firstCyc50yuanMoney to set
	 */
	public void setFirstCyc50yuanMoney(Long firstCyc50yuanMoney) {
		this.firstCyc50yuanMoney = firstCyc50yuanMoney;
	}

	/**
	 * @return the firstCyc100yuanQuantity
	 */
	public Long getFirstCyc100yuanQuantity() {
		return firstCyc100yuanQuantity;
	}

	/**
	 * @param firstCyc100yuanQuantity the firstCyc100yuanQuantity to set
	 */
	public void setFirstCyc100yuanQuantity(Long firstCyc100yuanQuantity) {
		this.firstCyc100yuanQuantity = firstCyc100yuanQuantity;
	}

	/**
	 * @return the firstCyc100yuanMoney
	 */
	public Long getFirstCyc100yuanMoney() {
		return firstCyc100yuanMoney;
	}

	/**
	 * @param firstCyc100yuanMoney the firstCyc100yuanMoney to set
	 */
	public void setFirstCyc100yuanMoney(Long firstCyc100yuanMoney) {
		this.firstCyc100yuanMoney = firstCyc100yuanMoney;
	}

	/**
	 * @return the secondCyc1yuanQuantity
	 */
	public Long getSecondCyc1yuanQuantity() {
		return secondCyc1yuanQuantity;
	}

	/**
	 * @param secondCyc1yuanQuantity the secondCyc1yuanQuantity to set
	 */
	public void setSecondCyc1yuanQuantity(Long secondCyc1yuanQuantity) {
		this.secondCyc1yuanQuantity = secondCyc1yuanQuantity;
	}

	/**
	 * @return the secondCyc1yuanMoney
	 */
	public Long getSecondCyc1yuanMoney() {
		return secondCyc1yuanMoney;
	}

	/**
	 * @param secondCyc1yuanMoney the secondCyc1yuanMoney to set
	 */
	public void setSecondCyc1yuanMoney(Long secondCyc1yuanMoney) {
		this.secondCyc1yuanMoney = secondCyc1yuanMoney;
	}

	/**
	 * @return the secondCyc2yuanQuantity
	 */
	public Long getSecondCyc2yuanQuantity() {
		return secondCyc2yuanQuantity;
	}

	/**
	 * @param secondCyc2yuanQuantity the secondCyc2yuanQuantity to set
	 */
	public void setSecondCyc2yuanQuantity(Long secondCyc2yuanQuantity) {
		this.secondCyc2yuanQuantity = secondCyc2yuanQuantity;
	}

	/**
	 * @return the secondCyc2yuanMoney
	 */
	public Long getSecondCyc2yuanMoney() {
		return secondCyc2yuanMoney;
	}

	/**
	 * @param secondCyc2yuanMoney the secondCyc2yuanMoney to set
	 */
	public void setSecondCyc2yuanMoney(Long secondCyc2yuanMoney) {
		this.secondCyc2yuanMoney = secondCyc2yuanMoney;
	}

	/**
	 * @return the secondCyc5yuanQuantity
	 */
	public Long getSecondCyc5yuanQuantity() {
		return secondCyc5yuanQuantity;
	}

	/**
	 * @param secondCyc5yuanQuantity the secondCyc5yuanQuantity to set
	 */
	public void setSecondCyc5yuanQuantity(Long secondCyc5yuanQuantity) {
		this.secondCyc5yuanQuantity = secondCyc5yuanQuantity;
	}

	/**
	 * @return the secondCyc5yuanMoney
	 */
	public Long getSecondCyc5yuanMoney() {
		return secondCyc5yuanMoney;
	}

	/**
	 * @param secondCyc5yuanMoney the secondCyc5yuanMoney to set
	 */
	public void setSecondCyc5yuanMoney(Long secondCyc5yuanMoney) {
		this.secondCyc5yuanMoney = secondCyc5yuanMoney;
	}

	/**
	 * @return the secondCyc10yuanQuantity
	 */
	public Long getSecondCyc10yuanQuantity() {
		return secondCyc10yuanQuantity;
	}

	/**
	 * @param secondCyc10yuanQuantity the secondCyc10yuanQuantity to set
	 */
	public void setSecondCyc10yuanQuantity(Long secondCyc10yuanQuantity) {
		this.secondCyc10yuanQuantity = secondCyc10yuanQuantity;
	}

	/**
	 * @return the secondCyc10yuanMoney
	 */
	public Long getSecondCyc10yuanMoney() {
		return secondCyc10yuanMoney;
	}

	/**
	 * @param secondCyc10yuanMoney the secondCyc10yuanMoney to set
	 */
	public void setSecondCyc10yuanMoney(Long secondCyc10yuanMoney) {
		this.secondCyc10yuanMoney = secondCyc10yuanMoney;
	}

	/**
	 * @return the secondCyc20yuanQuantity
	 */
	public Long getSecondCyc20yuanQuantity() {
		return secondCyc20yuanQuantity;
	}

	/**
	 * @param secondCyc20yuanQuantity the secondCyc20yuanQuantity to set
	 */
	public void setSecondCyc20yuanQuantity(Long secondCyc20yuanQuantity) {
		this.secondCyc20yuanQuantity = secondCyc20yuanQuantity;
	}

	/**
	 * @return the secondCyc20yuanMoney
	 */
	public Long getSecondCyc20yuanMoney() {
		return secondCyc20yuanMoney;
	}

	/**
	 * @param secondCyc20yuanMoney the secondCyc20yuanMoney to set
	 */
	public void setSecondCyc20yuanMoney(Long secondCyc20yuanMoney) {
		this.secondCyc20yuanMoney = secondCyc20yuanMoney;
	}

	/**
	 * @return the secondCyc50yuanQuantity
	 */
	public Long getSecondCyc50yuanQuantity() {
		return secondCyc50yuanQuantity;
	}

	/**
	 * @param secondCyc50yuanQuantity the secondCyc50yuanQuantity to set
	 */
	public void setSecondCyc50yuanQuantity(Long secondCyc50yuanQuantity) {
		this.secondCyc50yuanQuantity = secondCyc50yuanQuantity;
	}

	/**
	 * @return the secondCyc50yuanMoney
	 */
	public Long getSecondCyc50yuanMoney() {
		return secondCyc50yuanMoney;
	}

	/**
	 * @param secondCyc50yuanMoney the secondCyc50yuanMoney to set
	 */
	public void setSecondCyc50yuanMoney(Long secondCyc50yuanMoney) {
		this.secondCyc50yuanMoney = secondCyc50yuanMoney;
	}

	/**
	 * @return the secondCyc100yuanQuantity
	 */
	public Long getSecondCyc100yuanQuantity() {
		return secondCyc100yuanQuantity;
	}

	/**
	 * @param secondCyc100yuanQuantity the secondCyc100yuanQuantity to set
	 */
	public void setSecondCyc100yuanQuantity(Long secondCyc100yuanQuantity) {
		this.secondCyc100yuanQuantity = secondCyc100yuanQuantity;
	}

	/**
	 * @return the secondCyc100yuanMoney
	 */
	public Long getSecondCyc100yuanMoney() {
		return secondCyc100yuanMoney;
	}

	/**
	 * @param secondCyc100yuanMoney the secondCyc100yuanMoney to set
	 */
	public void setSecondCyc100yuanMoney(Long secondCyc100yuanMoney) {
		this.secondCyc100yuanMoney = secondCyc100yuanMoney;
	}

	/**
	 * @return the recycle1yuanQuantity
	 */
	public Long getRecycle1yuanQuantity() {
		return recycle1yuanQuantity;
	}

	/**
	 * @param recycle1yuanQuantity the recycle1yuanQuantity to set
	 */
	public void setRecycle1yuanQuantity(Long recycle1yuanQuantity) {
		this.recycle1yuanQuantity = recycle1yuanQuantity;
	}

	/**
	 * @return the recycle1yuanMoney
	 */
	public Long getRecycle1yuanMoney() {
		return recycle1yuanMoney;
	}

	/**
	 * @param recycle1yuanMoney the recycle1yuanMoney to set
	 */
	public void setRecycle1yuanMoney(Long recycle1yuanMoney) {
		this.recycle1yuanMoney = recycle1yuanMoney;
	}

	/**
	 * @return the recycle2yuanQuantity
	 */
	public Long getRecycle2yuanQuantity() {
		return recycle2yuanQuantity;
	}

	/**
	 * @param recycle2yuanQuantity the recycle2yuanQuantity to set
	 */
	public void setRecycle2yuanQuantity(Long recycle2yuanQuantity) {
		this.recycle2yuanQuantity = recycle2yuanQuantity;
	}

	/**
	 * @return the recycle2yuanMoney
	 */
	public Long getRecycle2yuanMoney() {
		return recycle2yuanMoney;
	}

	/**
	 * @param recycle2yuanMoney the recycle2yuanMoney to set
	 */
	public void setRecycle2yuanMoney(Long recycle2yuanMoney) {
		this.recycle2yuanMoney = recycle2yuanMoney;
	}

	/**
	 * @return the recycle5yuanQuantity
	 */
	public Long getRecycle5yuanQuantity() {
		return recycle5yuanQuantity;
	}

	/**
	 * @param recycle5yuanQuantity the recycle5yuanQuantity to set
	 */
	public void setRecycle5yuanQuantity(Long recycle5yuanQuantity) {
		this.recycle5yuanQuantity = recycle5yuanQuantity;
	}

	/**
	 * @return the recycle5yuanMoney
	 */
	public Long getRecycle5yuanMoney() {
		return recycle5yuanMoney;
	}

	/**
	 * @param recycle5yuanMoney the recycle5yuanMoney to set
	 */
	public void setRecycle5yuanMoney(Long recycle5yuanMoney) {
		this.recycle5yuanMoney = recycle5yuanMoney;
	}

	/**
	 * @return the recycle10yuanQuantity
	 */
	public Long getRecycle10yuanQuantity() {
		return recycle10yuanQuantity;
	}

	/**
	 * @param recycle10yuanQuantity the recycle10yuanQuantity to set
	 */
	public void setRecycle10yuanQuantity(Long recycle10yuanQuantity) {
		this.recycle10yuanQuantity = recycle10yuanQuantity;
	}

	/**
	 * @return the recycle10yuanMoney
	 */
	public Long getRecycle10yuanMoney() {
		return recycle10yuanMoney;
	}

	/**
	 * @param recycle10yuanMoney the recycle10yuanMoney to set
	 */
	public void setRecycle10yuanMoney(Long recycle10yuanMoney) {
		this.recycle10yuanMoney = recycle10yuanMoney;
	}

	/**
	 * @return the recycle20yuanQuantity
	 */
	public Long getRecycle20yuanQuantity() {
		return recycle20yuanQuantity;
	}

	/**
	 * @param recycle20yuanQuantity the recycle20yuanQuantity to set
	 */
	public void setRecycle20yuanQuantity(Long recycle20yuanQuantity) {
		this.recycle20yuanQuantity = recycle20yuanQuantity;
	}

	/**
	 * @return the recycle20yuanMoney
	 */
	public Long getRecycle20yuanMoney() {
		return recycle20yuanMoney;
	}

	/**
	 * @param recycle20yuanMoney the recycle20yuanMoney to set
	 */
	public void setRecycle20yuanMoney(Long recycle20yuanMoney) {
		this.recycle20yuanMoney = recycle20yuanMoney;
	}

	/**
	 * @return the recycle50yuanQuantity
	 */
	public Long getRecycle50yuanQuantity() {
		return recycle50yuanQuantity;
	}

	/**
	 * @param recycle50yuanQuantity the recycle50yuanQuantity to set
	 */
	public void setRecycle50yuanQuantity(Long recycle50yuanQuantity) {
		this.recycle50yuanQuantity = recycle50yuanQuantity;
	}

	/**
	 * @return the recycle50yuanMoney
	 */
	public Long getRecycle50yuanMoney() {
		return recycle50yuanMoney;
	}

	/**
	 * @param recycle50yuanMoney the recycle50yuanMoney to set
	 */
	public void setRecycle50yuanMoney(Long recycle50yuanMoney) {
		this.recycle50yuanMoney = recycle50yuanMoney;
	}

	/**
	 * @return the recycle100yuanQuantity
	 */
	public Long getRecycle100yuanQuantity() {
		return recycle100yuanQuantity;
	}

	/**
	 * @param recycle100yuanQuantity the recycle100yuanQuantity to set
	 */
	public void setRecycle100yuanQuantity(Long recycle100yuanQuantity) {
		this.recycle100yuanQuantity = recycle100yuanQuantity;
	}

	/**
	 * @return the recycle100yuanMoney
	 */
	public Long getRecycle100yuanMoney() {
		return recycle100yuanMoney;
	}

	/**
	 * @param recycle100yuanMoney the recycle100yuanMoney to set
	 */
	public void setRecycle100yuanMoney(Long recycle100yuanMoney) {
		this.recycle100yuanMoney = recycle100yuanMoney;
	}

	/**
	 * @return the firstResve1yuanQuantity
	 */
	public Long getFirstResve1yuanQuantity() {
		return firstResve1yuanQuantity;
	}

	/**
	 * @param firstResve1yuanQuantity the firstResve1yuanQuantity to set
	 */
	public void setFirstResve1yuanQuantity(Long firstResve1yuanQuantity) {
		this.firstResve1yuanQuantity = firstResve1yuanQuantity;
	}

	/**
	 * @return the firstResve1yuanMoney
	 */
	public Long getFirstResve1yuanMoney() {
		return firstResve1yuanMoney;
	}

	/**
	 * @param firstResve1yuanMoney the firstResve1yuanMoney to set
	 */
	public void setFirstResve1yuanMoney(Long firstResve1yuanMoney) {
		this.firstResve1yuanMoney = firstResve1yuanMoney;
	}

	/**
	 * @return the firstResve2yuanQuantity
	 */
	public Long getFirstResve2yuanQuantity() {
		return firstResve2yuanQuantity;
	}

	/**
	 * @param firstResve2yuanQuantity the firstResve2yuanQuantity to set
	 */
	public void setFirstResve2yuanQuantity(Long firstResve2yuanQuantity) {
		this.firstResve2yuanQuantity = firstResve2yuanQuantity;
	}

	/**
	 * @return the firstResve2yuanMoney
	 */
	public Long getFirstResve2yuanMoney() {
		return firstResve2yuanMoney;
	}

	/**
	 * @param firstResve2yuanMoney the firstResve2yuanMoney to set
	 */
	public void setFirstResve2yuanMoney(Long firstResve2yuanMoney) {
		this.firstResve2yuanMoney = firstResve2yuanMoney;
	}

	/**
	 * @return the firstResve5yuanQuantity
	 */
	public Long getFirstResve5yuanQuantity() {
		return firstResve5yuanQuantity;
	}

	/**
	 * @param firstResve5yuanQuantity the firstResve5yuanQuantity to set
	 */
	public void setFirstResve5yuanQuantity(Long firstResve5yuanQuantity) {
		this.firstResve5yuanQuantity = firstResve5yuanQuantity;
	}

	/**
	 * @return the firstResve5yuanMoney
	 */
	public Long getFirstResve5yuanMoney() {
		return firstResve5yuanMoney;
	}

	/**
	 * @param firstResve5yuanMoney the firstResve5yuanMoney to set
	 */
	public void setFirstResve5yuanMoney(Long firstResve5yuanMoney) {
		this.firstResve5yuanMoney = firstResve5yuanMoney;
	}

	/**
	 * @return the firstResve10yuanQuantity
	 */
	public Long getFirstResve10yuanQuantity() {
		return firstResve10yuanQuantity;
	}

	/**
	 * @param firstResve10yuanQuantity the firstResve10yuanQuantity to set
	 */
	public void setFirstResve10yuanQuantity(Long firstResve10yuanQuantity) {
		this.firstResve10yuanQuantity = firstResve10yuanQuantity;
	}

	/**
	 * @return the firstResve10yuanMoney
	 */
	public Long getFirstResve10yuanMoney() {
		return firstResve10yuanMoney;
	}

	/**
	 * @param firstResve10yuanMoney the firstResve10yuanMoney to set
	 */
	public void setFirstResve10yuanMoney(Long firstResve10yuanMoney) {
		this.firstResve10yuanMoney = firstResve10yuanMoney;
	}

	/**
	 * @return the firstResve20yuanQuantity
	 */
	public Long getFirstResve20yuanQuantity() {
		return firstResve20yuanQuantity;
	}

	/**
	 * @param firstResve20yuanQuantity the firstResve20yuanQuantity to set
	 */
	public void setFirstResve20yuanQuantity(Long firstResve20yuanQuantity) {
		this.firstResve20yuanQuantity = firstResve20yuanQuantity;
	}

	/**
	 * @return the firstResve20yuanMoney
	 */
	public Long getFirstResve20yuanMoney() {
		return firstResve20yuanMoney;
	}

	/**
	 * @param firstResve20yuanMoney the firstResve20yuanMoney to set
	 */
	public void setFirstResve20yuanMoney(Long firstResve20yuanMoney) {
		this.firstResve20yuanMoney = firstResve20yuanMoney;
	}

	/**
	 * @return the firstResve50yuanQuantity
	 */
	public Long getFirstResve50yuanQuantity() {
		return firstResve50yuanQuantity;
	}

	/**
	 * @param firstResve50yuanQuantity the firstResve50yuanQuantity to set
	 */
	public void setFirstResve50yuanQuantity(Long firstResve50yuanQuantity) {
		this.firstResve50yuanQuantity = firstResve50yuanQuantity;
	}

	/**
	 * @return the firstResve50yuanMoney
	 */
	public Long getFirstResve50yuanMoney() {
		return firstResve50yuanMoney;
	}

	/**
	 * @param firstResve50yuanMoney the firstResve50yuanMoney to set
	 */
	public void setFirstResve50yuanMoney(Long firstResve50yuanMoney) {
		this.firstResve50yuanMoney = firstResve50yuanMoney;
	}

	/**
	 * @return the firstResve100yuanQuantity
	 */
	public Long getFirstResve100yuanQuantity() {
		return firstResve100yuanQuantity;
	}

	/**
	 * @param firstResve100yuanQuantity the firstResve100yuanQuantity to set
	 */
	public void setFirstResve100yuanQuantity(Long firstResve100yuanQuantity) {
		this.firstResve100yuanQuantity = firstResve100yuanQuantity;
	}

	/**
	 * @return the firstResve100yuanMoney
	 */
	public Long getFirstResve100yuanMoney() {
		return firstResve100yuanMoney;
	}

	/**
	 * @param firstResve100yuanMoney the firstResve100yuanMoney to set
	 */
	public void setFirstResve100yuanMoney(Long firstResve100yuanMoney) {
		this.firstResve100yuanMoney = firstResve100yuanMoney;
	}

	/**
	 * @return the secondResve1yuanQuantity
	 */
	public Long getSecondResve1yuanQuantity() {
		return secondResve1yuanQuantity;
	}

	/**
	 * @param secondResve1yuanQuantity the secondResve1yuanQuantity to set
	 */
	public void setSecondResve1yuanQuantity(Long secondResve1yuanQuantity) {
		this.secondResve1yuanQuantity = secondResve1yuanQuantity;
	}

	/**
	 * @return the secondResve1yuanMoney
	 */
	public Long getSecondResve1yuanMoney() {
		return secondResve1yuanMoney;
	}

	/**
	 * @param secondResve1yuanMoney the secondResve1yuanMoney to set
	 */
	public void setSecondResve1yuanMoney(Long secondResve1yuanMoney) {
		this.secondResve1yuanMoney = secondResve1yuanMoney;
	}

	/**
	 * @return the secondResve2yuanQuantity
	 */
	public Long getSecondResve2yuanQuantity() {
		return secondResve2yuanQuantity;
	}

	/**
	 * @param secondResve2yuanQuantity the secondResve2yuanQuantity to set
	 */
	public void setSecondResve2yuanQuantity(Long secondResve2yuanQuantity) {
		this.secondResve2yuanQuantity = secondResve2yuanQuantity;
	}

	/**
	 * @return the secondResve2yuanMoney
	 */
	public Long getSecondResve2yuanMoney() {
		return secondResve2yuanMoney;
	}

	/**
	 * @param secondResve2yuanMoney the secondResve2yuanMoney to set
	 */
	public void setSecondResve2yuanMoney(Long secondResve2yuanMoney) {
		this.secondResve2yuanMoney = secondResve2yuanMoney;
	}

	/**
	 * @return the secondResve5yuanQuantity
	 */
	public Long getSecondResve5yuanQuantity() {
		return secondResve5yuanQuantity;
	}

	/**
	 * @param secondResve5yuanQuantity the secondResve5yuanQuantity to set
	 */
	public void setSecondResve5yuanQuantity(Long secondResve5yuanQuantity) {
		this.secondResve5yuanQuantity = secondResve5yuanQuantity;
	}

	/**
	 * @return the secondResve5yuanMoney
	 */
	public Long getSecondResve5yuanMoney() {
		return secondResve5yuanMoney;
	}

	/**
	 * @param secondResve5yuanMoney the secondResve5yuanMoney to set
	 */
	public void setSecondResve5yuanMoney(Long secondResve5yuanMoney) {
		this.secondResve5yuanMoney = secondResve5yuanMoney;
	}

	/**
	 * @return the secondResve10yuanQuantity
	 */
	public Long getSecondResve10yuanQuantity() {
		return secondResve10yuanQuantity;
	}

	/**
	 * @param secondResve10yuanQuantity the secondResve10yuanQuantity to set
	 */
	public void setSecondResve10yuanQuantity(Long secondResve10yuanQuantity) {
		this.secondResve10yuanQuantity = secondResve10yuanQuantity;
	}

	/**
	 * @return the secondResve10yuanMoney
	 */
	public Long getSecondResve10yuanMoney() {
		return secondResve10yuanMoney;
	}

	/**
	 * @param secondResve10yuanMoney the secondResve10yuanMoney to set
	 */
	public void setSecondResve10yuanMoney(Long secondResve10yuanMoney) {
		this.secondResve10yuanMoney = secondResve10yuanMoney;
	}

	/**
	 * @return the secondResve20yuanQuantity
	 */
	public Long getSecondResve20yuanQuantity() {
		return secondResve20yuanQuantity;
	}

	/**
	 * @param secondResve20yuanQuantity the secondResve20yuanQuantity to set
	 */
	public void setSecondResve20yuanQuantity(Long secondResve20yuanQuantity) {
		this.secondResve20yuanQuantity = secondResve20yuanQuantity;
	}

	/**
	 * @return the secondResve20yuanMoney
	 */
	public Long getSecondResve20yuanMoney() {
		return secondResve20yuanMoney;
	}

	/**
	 * @param secondResve20yuanMoney the secondResve20yuanMoney to set
	 */
	public void setSecondResve20yuanMoney(Long secondResve20yuanMoney) {
		this.secondResve20yuanMoney = secondResve20yuanMoney;
	}

	/**
	 * @return the secondResve50yuanQuantity
	 */
	public Long getSecondResve50yuanQuantity() {
		return secondResve50yuanQuantity;
	}

	/**
	 * @param secondResve50yuanQuantity the secondResve50yuanQuantity to set
	 */
	public void setSecondResve50yuanQuantity(Long secondResve50yuanQuantity) {
		this.secondResve50yuanQuantity = secondResve50yuanQuantity;
	}

	/**
	 * @return the secondResve50yuanMoney
	 */
	public Long getSecondResve50yuanMoney() {
		return secondResve50yuanMoney;
	}

	/**
	 * @param secondResve50yuanMoney the secondResve50yuanMoney to set
	 */
	public void setSecondResve50yuanMoney(Long secondResve50yuanMoney) {
		this.secondResve50yuanMoney = secondResve50yuanMoney;
	}

	/**
	 * @return the secondResve100yuanQuantity
	 */
	public Long getSecondResve100yuanQuantity() {
		return secondResve100yuanQuantity;
	}

	/**
	 * @param secondResve100yuanQuantity the secondResve100yuanQuantity to set
	 */
	public void setSecondResve100yuanQuantity(Long secondResve100yuanQuantity) {
		this.secondResve100yuanQuantity = secondResve100yuanQuantity;
	}

	/**
	 * @return the secondResve100yuanMoney
	 */
	public Long getSecondResve100yuanMoney() {
		return secondResve100yuanMoney;
	}

	/**
	 * @param secondResve100yuanMoney the secondResve100yuanMoney to set
	 */
	public void setSecondResve100yuanMoney(Long secondResve100yuanMoney) {
		this.secondResve100yuanMoney = secondResve100yuanMoney;
	}

	/**
	 * @return the ignore1yuanQuantity
	 */
	public Long getIgnore1yuanQuantity() {
		return ignore1yuanQuantity;
	}

	/**
	 * @param ignore1yuanQuantity the ignore1yuanQuantity to set
	 */
	public void setIgnore1yuanQuantity(Long ignore1yuanQuantity) {
		this.ignore1yuanQuantity = ignore1yuanQuantity;
	}

	/**
	 * @return the ignore1yuanMoney
	 */
	public Long getIgnore1yuanMoney() {
		return ignore1yuanMoney;
	}

	/**
	 * @param ignore1yuanMoney the ignore1yuanMoney to set
	 */
	public void setIgnore1yuanMoney(Long ignore1yuanMoney) {
		this.ignore1yuanMoney = ignore1yuanMoney;
	}

	/**
	 * @return the ignore2yuanQuantity
	 */
	public Long getIgnore2yuanQuantity() {
		return ignore2yuanQuantity;
	}

	/**
	 * @param ignore2yuanQuantity the ignore2yuanQuantity to set
	 */
	public void setIgnore2yuanQuantity(Long ignore2yuanQuantity) {
		this.ignore2yuanQuantity = ignore2yuanQuantity;
	}

	/**
	 * @return the ignore2yuanMoney
	 */
	public Long getIgnore2yuanMoney() {
		return ignore2yuanMoney;
	}

	/**
	 * @param ignore2yuanMoney the ignore2yuanMoney to set
	 */
	public void setIgnore2yuanMoney(Long ignore2yuanMoney) {
		this.ignore2yuanMoney = ignore2yuanMoney;
	}

	/**
	 * @return the ignore5yuanQuantity
	 */
	public Long getIgnore5yuanQuantity() {
		return ignore5yuanQuantity;
	}

	/**
	 * @param ignore5yuanQuantity the ignore5yuanQuantity to set
	 */
	public void setIgnore5yuanQuantity(Long ignore5yuanQuantity) {
		this.ignore5yuanQuantity = ignore5yuanQuantity;
	}

	/**
	 * @return the ignore5yuanMoney
	 */
	public Long getIgnore5yuanMoney() {
		return ignore5yuanMoney;
	}

	/**
	 * @param ignore5yuanMoney the ignore5yuanMoney to set
	 */
	public void setIgnore5yuanMoney(Long ignore5yuanMoney) {
		this.ignore5yuanMoney = ignore5yuanMoney;
	}

	/**
	 * @return the ignore10yuanQuantity
	 */
	public Long getIgnore10yuanQuantity() {
		return ignore10yuanQuantity;
	}

	/**
	 * @param ignore10yuanQuantity the ignore10yuanQuantity to set
	 */
	public void setIgnore10yuanQuantity(Long ignore10yuanQuantity) {
		this.ignore10yuanQuantity = ignore10yuanQuantity;
	}

	/**
	 * @return the ignore10yuanMoney
	 */
	public Long getIgnore10yuanMoney() {
		return ignore10yuanMoney;
	}

	/**
	 * @param ignore10yuanMoney the ignore10yuanMoney to set
	 */
	public void setIgnore10yuanMoney(Long ignore10yuanMoney) {
		this.ignore10yuanMoney = ignore10yuanMoney;
	}

	/**
	 * @return the ignore20yuanQuantity
	 */
	public Long getIgnore20yuanQuantity() {
		return ignore20yuanQuantity;
	}

	/**
	 * @param ignore20yuanQuantity the ignore20yuanQuantity to set
	 */
	public void setIgnore20yuanQuantity(Long ignore20yuanQuantity) {
		this.ignore20yuanQuantity = ignore20yuanQuantity;
	}

	/**
	 * @return the ignore20yuanMoney
	 */
	public Long getIgnore20yuanMoney() {
		return ignore20yuanMoney;
	}

	/**
	 * @param ignore20yuanMoney the ignore20yuanMoney to set
	 */
	public void setIgnore20yuanMoney(Long ignore20yuanMoney) {
		this.ignore20yuanMoney = ignore20yuanMoney;
	}

	/**
	 * @return the ignore50yuanQuantity
	 */
	public Long getIgnore50yuanQuantity() {
		return ignore50yuanQuantity;
	}

	/**
	 * @param ignore50yuanQuantity the ignore50yuanQuantity to set
	 */
	public void setIgnore50yuanQuantity(Long ignore50yuanQuantity) {
		this.ignore50yuanQuantity = ignore50yuanQuantity;
	}

	/**
	 * @return the ignore50yuanMoney
	 */
	public Long getIgnore50yuanMoney() {
		return ignore50yuanMoney;
	}

	/**
	 * @param ignore50yuanMoney the ignore50yuanMoney to set
	 */
	public void setIgnore50yuanMoney(Long ignore50yuanMoney) {
		this.ignore50yuanMoney = ignore50yuanMoney;
	}

	/**
	 * @return the ignore100yuanQuantity
	 */
	public Long getIgnore100yuanQuantity() {
		return ignore100yuanQuantity;
	}

	/**
	 * @param ignore100yuanQuantity the ignore100yuanQuantity to set
	 */
	public void setIgnore100yuanQuantity(Long ignore100yuanQuantity) {
		this.ignore100yuanQuantity = ignore100yuanQuantity;
	}

	/**
	 * @return the ignore100yuanMoney
	 */
	public Long getIgnore100yuanMoney() {
		return ignore100yuanMoney;
	}

	/**
	 * @param ignore100yuanMoney the ignore100yuanMoney to set
	 */
	public void setIgnore100yuanMoney(Long ignore100yuanMoney) {
		this.ignore100yuanMoney = ignore100yuanMoney;
	}

	/**
	 * @return the firstTicketQuantity
	 */
	public Long getFirstTicketQuantity() {
		return firstTicketQuantity;
	}

	/**
	 * @param firstTicketQuantity the firstTicketQuantity to set
	 */
	public void setFirstTicketQuantity(Long firstTicketQuantity) {
		this.firstTicketQuantity = firstTicketQuantity;
	}

	/**
	 * @return the secondTicketQuantity
	 */
	public Long getSecondTicketQuantity() {
		return secondTicketQuantity;
	}

	/**
	 * @param secondTicketQuantity the secondTicketQuantity to set
	 */
	public void setSecondTicketQuantity(Long secondTicketQuantity) {
		this.secondTicketQuantity = secondTicketQuantity;
	}

	/**
	 * @return the thirdTicketQuantity
	 */
	public Long getThirdTicketQuantity() {
		return thirdTicketQuantity;
	}

	/**
	 * @param thirdTicketQuantity the thirdTicketQuantity to set
	 */
	public void setThirdTicketQuantity(Long thirdTicketQuantity) {
		this.thirdTicketQuantity = thirdTicketQuantity;
	}

}