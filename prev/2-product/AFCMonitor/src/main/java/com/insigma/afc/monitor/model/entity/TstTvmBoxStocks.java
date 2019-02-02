/* 
 * 日期：2017年6月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Ticket: TVM票箱钱箱信息表
 * @author  mengyifan
 *
 */
@Entity
@Table(name = "TST_TVM_BOX_STOCKS")
public class TstTvmBoxStocks implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "node_Id")
	private Long nodeId;

	@Column(name = "station_Id")
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

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getFirstChief5jiaoQuantity() {
		return firstChief5jiaoQuantity;
	}

	public void setFirstChief5jiaoQuantity(Long firstChief5jiaoQuantity) {
		this.firstChief5jiaoQuantity = firstChief5jiaoQuantity;
	}

	public Long getFirstChief5jiaoMoney() {
		return firstChief5jiaoMoney;
	}

	public void setFirstChief5jiaoMoney(Long firstChief5jiaoMoney) {
		this.firstChief5jiaoMoney = firstChief5jiaoMoney;
	}

	public Long getFirstChief10jiaoQuantity() {
		return firstChief10jiaoQuantity;
	}

	public void setFirstChief10jiaoQuantity(Long firstChief10jiaoQuantity) {
		this.firstChief10jiaoQuantity = firstChief10jiaoQuantity;
	}

	public Long getFirstChief10jiaoMoney() {
		return firstChief10jiaoMoney;
	}

	public void setFirstChief10jiaoMoney(Long firstChief10jiaoMoney) {
		this.firstChief10jiaoMoney = firstChief10jiaoMoney;
	}

	public Long getSecondChief5jiaoQuantity() {
		return secondChief5jiaoQuantity;
	}

	public void setSecondChief5jiaoQuantity(Long secondChief5jiaoQuantity) {
		this.secondChief5jiaoQuantity = secondChief5jiaoQuantity;
	}

	public Long getSecondChief5jiaoMoney() {
		return secondChief5jiaoMoney;
	}

	public void setSecondChief5jiaoMoney(Long secondChief5jiaoMoney) {
		this.secondChief5jiaoMoney = secondChief5jiaoMoney;
	}

	public Long getSecondChief10jiaoQuantity() {
		return secondChief10jiaoQuantity;
	}

	public void setSecondChief10jiaoQuantity(Long secondChief10jiaoQuantity) {
		this.secondChief10jiaoQuantity = secondChief10jiaoQuantity;
	}

	public Long getSecondChief10jiaoMoney() {
		return secondChief10jiaoMoney;
	}

	public void setSecondChief10jiaoMoney(Long secondChief10jiaoMoney) {
		this.secondChief10jiaoMoney = secondChief10jiaoMoney;
	}

	public Long getFirstCyc5jiaoQuantity() {
		return firstCyc5jiaoQuantity;
	}

	public void setFirstCyc5jiaoQuantity(Long firstCyc5jiaoQuantity) {
		this.firstCyc5jiaoQuantity = firstCyc5jiaoQuantity;
	}

	public Long getFirstCyc5jiaoMoney() {
		return firstCyc5jiaoMoney;
	}

	public void setFirstCyc5jiaoMoney(Long firstCyc5jiaoMoney) {
		this.firstCyc5jiaoMoney = firstCyc5jiaoMoney;
	}

	public Long getFirstCyc10jiaoQuantity() {
		return firstCyc10jiaoQuantity;
	}

	public void setFirstCyc10jiaoQuantity(Long firstCyc10jiaoQuantity) {
		this.firstCyc10jiaoQuantity = firstCyc10jiaoQuantity;
	}

	public Long getFirstCyc10jiaoMoney() {
		return firstCyc10jiaoMoney;
	}

	public void setFirstCyc10jiaoMoney(Long firstCyc10jiaoMoney) {
		this.firstCyc10jiaoMoney = firstCyc10jiaoMoney;
	}

	public Long getSecondCyc5jiaoQuantity() {
		return secondCyc5jiaoQuantity;
	}

	public void setSecondCyc5jiaoQuantity(Long secondCyc5jiaoQuantity) {
		this.secondCyc5jiaoQuantity = secondCyc5jiaoQuantity;
	}

	public Long getSecondCyc5jiaoMoney() {
		return secondCyc5jiaoMoney;
	}

	public void setSecondCyc5jiaoMoney(Long secondCyc5jiaoMoney) {
		this.secondCyc5jiaoMoney = secondCyc5jiaoMoney;
	}

	public Long getSecondCyc10jiaoQuantity() {
		return secondCyc10jiaoQuantity;
	}

	public void setSecondCyc10jiaoQuantity(Long secondCyc10jiaoQuantity) {
		this.secondCyc10jiaoQuantity = secondCyc10jiaoQuantity;
	}

	public Long getSecondCyc10jiaoMoney() {
		return secondCyc10jiaoMoney;
	}

	public void setSecondCyc10jiaoMoney(Long secondCyc10jiaoMoney) {
		this.secondCyc10jiaoMoney = secondCyc10jiaoMoney;
	}

	public Long getRecycle5jiaoQuantity() {
		return recycle5jiaoQuantity;
	}

	public void setRecycle5jiaoQuantity(Long recycle5jiaoQuantity) {
		this.recycle5jiaoQuantity = recycle5jiaoQuantity;
	}

	public Long getRecycle5jiaoMoney() {
		return recycle5jiaoMoney;
	}

	public void setRecycle5jiaoMoney(Long recycle5jiaoMoney) {
		this.recycle5jiaoMoney = recycle5jiaoMoney;
	}

	public Long getRecycle10jiaoQuantity() {
		return recycle10jiaoQuantity;
	}

	public void setRecycle10jiaoQuantity(Long recycle10jiaoQuantity) {
		this.recycle10jiaoQuantity = recycle10jiaoQuantity;
	}

	public Long getRecycle10jiaoMoney() {
		return recycle10jiaoMoney;
	}

	public void setRecycle10jiaoMoney(Long recycle10jiaoMoney) {
		this.recycle10jiaoMoney = recycle10jiaoMoney;
	}

	public Long getFirstResve5jiaoQuantity() {
		return firstResve5jiaoQuantity;
	}

	public void setFirstResve5jiaoQuantity(Long firstResve5jiaoQuantity) {
		this.firstResve5jiaoQuantity = firstResve5jiaoQuantity;
	}

	public Long getFirstResve5jiaoMoney() {
		return firstResve5jiaoMoney;
	}

	public void setFirstResve5jiaoMoney(Long firstResve5jiaoMoney) {
		this.firstResve5jiaoMoney = firstResve5jiaoMoney;
	}

	public Long getFirstResve10jiaoQuantity() {
		return firstResve10jiaoQuantity;
	}

	public void setFirstResve10jiaoQuantity(Long firstResve10jiaoQuantity) {
		this.firstResve10jiaoQuantity = firstResve10jiaoQuantity;
	}

	public Long getFirstResve10jiaoMoney() {
		return firstResve10jiaoMoney;
	}

	public void setFirstResve10jiaoMoney(Long firstResve10jiaoMoney) {
		this.firstResve10jiaoMoney = firstResve10jiaoMoney;
	}

	public Long getSecondResve5jiaoQuantity() {
		return secondResve5jiaoQuantity;
	}

	public void setSecondResve5jiaoQuantity(Long secondResve5jiaoQuantity) {
		this.secondResve5jiaoQuantity = secondResve5jiaoQuantity;
	}

	public Long getSecondResve5jiaoMoney() {
		return secondResve5jiaoMoney;
	}

	public void setSecondResve5jiaoMoney(Long secondResve5jiaoMoney) {
		this.secondResve5jiaoMoney = secondResve5jiaoMoney;
	}

	public Long getSecondResve10jiaoQuantity() {
		return secondResve10jiaoQuantity;
	}

	public void setSecondResve10jiaoQuantity(Long secondResve10jiaoQuantity) {
		this.secondResve10jiaoQuantity = secondResve10jiaoQuantity;
	}

	public Long getSecondResve10jiaoMoney() {
		return secondResve10jiaoMoney;
	}

	public void setSecondResve10jiaoMoney(Long secondResve10jiaoMoney) {
		this.secondResve10jiaoMoney = secondResve10jiaoMoney;
	}

	public Long getFirstChief1yuanQuantity() {
		return firstChief1yuanQuantity;
	}

	public void setFirstChief1yuanQuantity(Long firstChief1yuanQuantity) {
		this.firstChief1yuanQuantity = firstChief1yuanQuantity;
	}

	public Long getFirstChief1yuanMoney() {
		return firstChief1yuanMoney;
	}

	public void setFirstChief1yuanMoney(Long firstChief1yuanMoney) {
		this.firstChief1yuanMoney = firstChief1yuanMoney;
	}

	public Long getFirstChief2yuanQuantity() {
		return firstChief2yuanQuantity;
	}

	public void setFirstChief2yuanQuantity(Long firstChief2yuanQuantity) {
		this.firstChief2yuanQuantity = firstChief2yuanQuantity;
	}

	public Long getFirstChief2yuanMoney() {
		return firstChief2yuanMoney;
	}

	public void setFirstChief2yuanMoney(Long firstChief2yuanMoney) {
		this.firstChief2yuanMoney = firstChief2yuanMoney;
	}

	public Long getFirstChief5yuanQuantity() {
		return firstChief5yuanQuantity;
	}

	public void setFirstChief5yuanQuantity(Long firstChief5yuanQuantity) {
		this.firstChief5yuanQuantity = firstChief5yuanQuantity;
	}

	public Long getFirstChief5yuanMoney() {
		return firstChief5yuanMoney;
	}

	public void setFirstChief5yuanMoney(Long firstChief5yuanMoney) {
		this.firstChief5yuanMoney = firstChief5yuanMoney;
	}

	public Long getFirstChief10yuanQuantity() {
		return firstChief10yuanQuantity;
	}

	public void setFirstChief10yuanQuantity(Long firstChief10yuanQuantity) {
		this.firstChief10yuanQuantity = firstChief10yuanQuantity;
	}

	public Long getFirstChief10yuanMoney() {
		return firstChief10yuanMoney;
	}

	public void setFirstChief10yuanMoney(Long firstChief10yuanMoney) {
		this.firstChief10yuanMoney = firstChief10yuanMoney;
	}

	public Long getFirstChief20yuanQuantity() {
		return firstChief20yuanQuantity;
	}

	public void setFirstChief20yuanQuantity(Long firstChief20yuanQuantity) {
		this.firstChief20yuanQuantity = firstChief20yuanQuantity;
	}

	public Long getFirstChief20yuanMoney() {
		return firstChief20yuanMoney;
	}

	public void setFirstChief20yuanMoney(Long firstChief20yuanMoney) {
		this.firstChief20yuanMoney = firstChief20yuanMoney;
	}

	public Long getFirstChief50yuanQuantity() {
		return firstChief50yuanQuantity;
	}

	public void setFirstChief50yuanQuantity(Long firstChief50yuanQuantity) {
		this.firstChief50yuanQuantity = firstChief50yuanQuantity;
	}

	public Long getFirstChief50yuanMoney() {
		return firstChief50yuanMoney;
	}

	public void setFirstChief50yuanMoney(Long firstChief50yuanMoney) {
		this.firstChief50yuanMoney = firstChief50yuanMoney;
	}

	public Long getFirstChief100yuanQuantity() {
		return firstChief100yuanQuantity;
	}

	public void setFirstChief100yuanQuantity(Long firstChief100yuanQuantity) {
		this.firstChief100yuanQuantity = firstChief100yuanQuantity;
	}

	public Long getFirstChief100yuanMoney() {
		return firstChief100yuanMoney;
	}

	public void setFirstChief100yuanMoney(Long firstChief100yuanMoney) {
		this.firstChief100yuanMoney = firstChief100yuanMoney;
	}

	public Long getSecondChief1yuanQuantity() {
		return secondChief1yuanQuantity;
	}

	public void setSecondChief1yuanQuantity(Long secondChief1yuanQuantity) {
		this.secondChief1yuanQuantity = secondChief1yuanQuantity;
	}

	public Long getSecondChief1yuanMoney() {
		return secondChief1yuanMoney;
	}

	public void setSecondChief1yuanMoney(Long secondChief1yuanMoney) {
		this.secondChief1yuanMoney = secondChief1yuanMoney;
	}

	public Long getSecondChief2yuanQuantity() {
		return secondChief2yuanQuantity;
	}

	public void setSecondChief2yuanQuantity(Long secondChief2yuanQuantity) {
		this.secondChief2yuanQuantity = secondChief2yuanQuantity;
	}

	public Long getSecondChief2yuanMoney() {
		return secondChief2yuanMoney;
	}

	public void setSecondChief2yuanMoney(Long secondChief2yuanMoney) {
		this.secondChief2yuanMoney = secondChief2yuanMoney;
	}

	public Long getSecondChief5yuanQuantity() {
		return secondChief5yuanQuantity;
	}

	public void setSecondChief5yuanQuantity(Long secondChief5yuanQuantity) {
		this.secondChief5yuanQuantity = secondChief5yuanQuantity;
	}

	public Long getSecondChief5yuanMoney() {
		return secondChief5yuanMoney;
	}

	public void setSecondChief5yuanMoney(Long secondChief5yuanMoney) {
		this.secondChief5yuanMoney = secondChief5yuanMoney;
	}

	public Long getSecondChief10yuanQuantity() {
		return secondChief10yuanQuantity;
	}

	public void setSecondChief10yuanQuantity(Long secondChief10yuanQuantity) {
		this.secondChief10yuanQuantity = secondChief10yuanQuantity;
	}

	public Long getSecondChief10yuanMoney() {
		return secondChief10yuanMoney;
	}

	public void setSecondChief10yuanMoney(Long secondChief10yuanMoney) {
		this.secondChief10yuanMoney = secondChief10yuanMoney;
	}

	public Long getSecondChief20yuanQuantity() {
		return secondChief20yuanQuantity;
	}

	public void setSecondChief20yuanQuantity(Long secondChief20yuanQuantity) {
		this.secondChief20yuanQuantity = secondChief20yuanQuantity;
	}

	public Long getSecondChief20yuanMoney() {
		return secondChief20yuanMoney;
	}

	public void setSecondChief20yuanMoney(Long secondChief20yuanMoney) {
		this.secondChief20yuanMoney = secondChief20yuanMoney;
	}

	public Long getSecondChief50yuanQuantity() {
		return secondChief50yuanQuantity;
	}

	public void setSecondChief50yuanQuantity(Long secondChief50yuanQuantity) {
		this.secondChief50yuanQuantity = secondChief50yuanQuantity;
	}

	public Long getSecondChief50yuanMoney() {
		return secondChief50yuanMoney;
	}

	public void setSecondChief50yuanMoney(Long secondChief50yuanMoney) {
		this.secondChief50yuanMoney = secondChief50yuanMoney;
	}

	public Long getSecondChief100yuanQuantity() {
		return secondChief100yuanQuantity;
	}

	public void setSecondChief100yuanQuantity(Long secondChief100yuanQuantity) {
		this.secondChief100yuanQuantity = secondChief100yuanQuantity;
	}

	public Long getSecondChief100yuanMoney() {
		return secondChief100yuanMoney;
	}

	public void setSecondChief100yuanMoney(Long secondChief100yuanMoney) {
		this.secondChief100yuanMoney = secondChief100yuanMoney;
	}

	public Long getFirstCyc1yuanQuantity() {
		return firstCyc1yuanQuantity;
	}

	public void setFirstCyc1yuanQuantity(Long firstCyc1yuanQuantity) {
		this.firstCyc1yuanQuantity = firstCyc1yuanQuantity;
	}

	public Long getFirstCyc1yuanMoney() {
		return firstCyc1yuanMoney;
	}

	public void setFirstCyc1yuanMoney(Long firstCyc1yuanMoney) {
		this.firstCyc1yuanMoney = firstCyc1yuanMoney;
	}

	public Long getFirstCyc2yuanQuantity() {
		return firstCyc2yuanQuantity;
	}

	public void setFirstCyc2yuanQuantity(Long firstCyc2yuanQuantity) {
		this.firstCyc2yuanQuantity = firstCyc2yuanQuantity;
	}

	public Long getFirstCyc2yuanMoney() {
		return firstCyc2yuanMoney;
	}

	public void setFirstCyc2yuanMoney(Long firstCyc2yuanMoney) {
		this.firstCyc2yuanMoney = firstCyc2yuanMoney;
	}

	public Long getFirstCyc5yuanQuantity() {
		return firstCyc5yuanQuantity;
	}

	public void setFirstCyc5yuanQuantity(Long firstCyc5yuanQuantity) {
		this.firstCyc5yuanQuantity = firstCyc5yuanQuantity;
	}

	public Long getFirstCyc5yuanMoney() {
		return firstCyc5yuanMoney;
	}

	public void setFirstCyc5yuanMoney(Long firstCyc5yuanMoney) {
		this.firstCyc5yuanMoney = firstCyc5yuanMoney;
	}

	public Long getFirstCyc10yuanQuantity() {
		return firstCyc10yuanQuantity;
	}

	public void setFirstCyc10yuanQuantity(Long firstCyc10yuanQuantity) {
		this.firstCyc10yuanQuantity = firstCyc10yuanQuantity;
	}

	public Long getFirstCyc10yuanMoney() {
		return firstCyc10yuanMoney;
	}

	public void setFirstCyc10yuanMoney(Long firstCyc10yuanMoney) {
		this.firstCyc10yuanMoney = firstCyc10yuanMoney;
	}

	public Long getFirstCyc20yuanQuantity() {
		return firstCyc20yuanQuantity;
	}

	public void setFirstCyc20yuanQuantity(Long firstCyc20yuanQuantity) {
		this.firstCyc20yuanQuantity = firstCyc20yuanQuantity;
	}

	public Long getFirstCyc20yuanMoney() {
		return firstCyc20yuanMoney;
	}

	public void setFirstCyc20yuanMoney(Long firstCyc20yuanMoney) {
		this.firstCyc20yuanMoney = firstCyc20yuanMoney;
	}

	public Long getFirstCyc50yuanQuantity() {
		return firstCyc50yuanQuantity;
	}

	public void setFirstCyc50yuanQuantity(Long firstCyc50yuanQuantity) {
		this.firstCyc50yuanQuantity = firstCyc50yuanQuantity;
	}

	public Long getFirstCyc50yuanMoney() {
		return firstCyc50yuanMoney;
	}

	public void setFirstCyc50yuanMoney(Long firstCyc50yuanMoney) {
		this.firstCyc50yuanMoney = firstCyc50yuanMoney;
	}

	public Long getFirstCyc100yuanQuantity() {
		return firstCyc100yuanQuantity;
	}

	public void setFirstCyc100yuanQuantity(Long firstCyc100yuanQuantity) {
		this.firstCyc100yuanQuantity = firstCyc100yuanQuantity;
	}

	public Long getFirstCyc100yuanMoney() {
		return firstCyc100yuanMoney;
	}

	public void setFirstCyc100yuanMoney(Long firstCyc100yuanMoney) {
		this.firstCyc100yuanMoney = firstCyc100yuanMoney;
	}

	public Long getSecondCyc1yuanQuantity() {
		return secondCyc1yuanQuantity;
	}

	public void setSecondCyc1yuanQuantity(Long secondCyc1yuanQuantity) {
		this.secondCyc1yuanQuantity = secondCyc1yuanQuantity;
	}

	public Long getSecondCyc1yuanMoney() {
		return secondCyc1yuanMoney;
	}

	public void setSecondCyc1yuanMoney(Long secondCyc1yuanMoney) {
		this.secondCyc1yuanMoney = secondCyc1yuanMoney;
	}

	public Long getSecondCyc2yuanQuantity() {
		return secondCyc2yuanQuantity;
	}

	public void setSecondCyc2yuanQuantity(Long secondCyc2yuanQuantity) {
		this.secondCyc2yuanQuantity = secondCyc2yuanQuantity;
	}

	public Long getSecondCyc2yuanMoney() {
		return secondCyc2yuanMoney;
	}

	public void setSecondCyc2yuanMoney(Long secondCyc2yuanMoney) {
		this.secondCyc2yuanMoney = secondCyc2yuanMoney;
	}

	public Long getSecondCyc5yuanQuantity() {
		return secondCyc5yuanQuantity;
	}

	public void setSecondCyc5yuanQuantity(Long secondCyc5yuanQuantity) {
		this.secondCyc5yuanQuantity = secondCyc5yuanQuantity;
	}

	public Long getSecondCyc5yuanMoney() {
		return secondCyc5yuanMoney;
	}

	public void setSecondCyc5yuanMoney(Long secondCyc5yuanMoney) {
		this.secondCyc5yuanMoney = secondCyc5yuanMoney;
	}

	public Long getSecondCyc10yuanQuantity() {
		return secondCyc10yuanQuantity;
	}

	public void setSecondCyc10yuanQuantity(Long secondCyc10yuanQuantity) {
		this.secondCyc10yuanQuantity = secondCyc10yuanQuantity;
	}

	public Long getSecondCyc10yuanMoney() {
		return secondCyc10yuanMoney;
	}

	public void setSecondCyc10yuanMoney(Long secondCyc10yuanMoney) {
		this.secondCyc10yuanMoney = secondCyc10yuanMoney;
	}

	public Long getSecondCyc20yuanQuantity() {
		return secondCyc20yuanQuantity;
	}

	public void setSecondCyc20yuanQuantity(Long secondCyc20yuanQuantity) {
		this.secondCyc20yuanQuantity = secondCyc20yuanQuantity;
	}

	public Long getSecondCyc20yuanMoney() {
		return secondCyc20yuanMoney;
	}

	public void setSecondCyc20yuanMoney(Long secondCyc20yuanMoney) {
		this.secondCyc20yuanMoney = secondCyc20yuanMoney;
	}

	public Long getSecondCyc50yuanQuantity() {
		return secondCyc50yuanQuantity;
	}

	public void setSecondCyc50yuanQuantity(Long secondCyc50yuanQuantity) {
		this.secondCyc50yuanQuantity = secondCyc50yuanQuantity;
	}

	public Long getSecondCyc50yuanMoney() {
		return secondCyc50yuanMoney;
	}

	public void setSecondCyc50yuanMoney(Long secondCyc50yuanMoney) {
		this.secondCyc50yuanMoney = secondCyc50yuanMoney;
	}

	public Long getSecondCyc100yuanQuantity() {
		return secondCyc100yuanQuantity;
	}

	public void setSecondCyc100yuanQuantity(Long secondCyc100yuanQuantity) {
		this.secondCyc100yuanQuantity = secondCyc100yuanQuantity;
	}

	public Long getSecondCyc100yuanMoney() {
		return secondCyc100yuanMoney;
	}

	public void setSecondCyc100yuanMoney(Long secondCyc100yuanMoney) {
		this.secondCyc100yuanMoney = secondCyc100yuanMoney;
	}

	public Long getRecycle1yuanQuantity() {
		return recycle1yuanQuantity;
	}

	public void setRecycle1yuanQuantity(Long recycle1yuanQuantity) {
		this.recycle1yuanQuantity = recycle1yuanQuantity;
	}

	public Long getRecycle1yuanMoney() {
		return recycle1yuanMoney;
	}

	public void setRecycle1yuanMoney(Long recycle1yuanMoney) {
		this.recycle1yuanMoney = recycle1yuanMoney;
	}

	public Long getRecycle2yuanQuantity() {
		return recycle2yuanQuantity;
	}

	public void setRecycle2yuanQuantity(Long recycle2yuanQuantity) {
		this.recycle2yuanQuantity = recycle2yuanQuantity;
	}

	public Long getRecycle2yuanMoney() {
		return recycle2yuanMoney;
	}

	public void setRecycle2yuanMoney(Long recycle2yuanMoney) {
		this.recycle2yuanMoney = recycle2yuanMoney;
	}

	public Long getRecycle5yuanQuantity() {
		return recycle5yuanQuantity;
	}

	public void setRecycle5yuanQuantity(Long recycle5yuanQuantity) {
		this.recycle5yuanQuantity = recycle5yuanQuantity;
	}

	public Long getRecycle5yuanMoney() {
		return recycle5yuanMoney;
	}

	public void setRecycle5yuanMoney(Long recycle5yuanMoney) {
		this.recycle5yuanMoney = recycle5yuanMoney;
	}

	public Long getRecycle10yuanQuantity() {
		return recycle10yuanQuantity;
	}

	public void setRecycle10yuanQuantity(Long recycle10yuanQuantity) {
		this.recycle10yuanQuantity = recycle10yuanQuantity;
	}

	public Long getRecycle10yuanMoney() {
		return recycle10yuanMoney;
	}

	public void setRecycle10yuanMoney(Long recycle10yuanMoney) {
		this.recycle10yuanMoney = recycle10yuanMoney;
	}

	public Long getRecycle20yuanQuantity() {
		return recycle20yuanQuantity;
	}

	public void setRecycle20yuanQuantity(Long recycle20yuanQuantity) {
		this.recycle20yuanQuantity = recycle20yuanQuantity;
	}

	public Long getRecycle20yuanMoney() {
		return recycle20yuanMoney;
	}

	public void setRecycle20yuanMoney(Long recycle20yuanMoney) {
		this.recycle20yuanMoney = recycle20yuanMoney;
	}

	public Long getRecycle50yuanQuantity() {
		return recycle50yuanQuantity;
	}

	public void setRecycle50yuanQuantity(Long recycle50yuanQuantity) {
		this.recycle50yuanQuantity = recycle50yuanQuantity;
	}

	public Long getRecycle50yuanMoney() {
		return recycle50yuanMoney;
	}

	public void setRecycle50yuanMoney(Long recycle50yuanMoney) {
		this.recycle50yuanMoney = recycle50yuanMoney;
	}

	public Long getRecycle100yuanQuantity() {
		return recycle100yuanQuantity;
	}

	public void setRecycle100yuanQuantity(Long recycle100yuanQuantity) {
		this.recycle100yuanQuantity = recycle100yuanQuantity;
	}

	public Long getRecycle100yuanMoney() {
		return recycle100yuanMoney;
	}

	public void setRecycle100yuanMoney(Long recycle100yuanMoney) {
		this.recycle100yuanMoney = recycle100yuanMoney;
	}

	public Long getFirstResve1yuanQuantity() {
		return firstResve1yuanQuantity;
	}

	public void setFirstResve1yuanQuantity(Long firstResve1yuanQuantity) {
		this.firstResve1yuanQuantity = firstResve1yuanQuantity;
	}

	public Long getFirstResve1yuanMoney() {
		return firstResve1yuanMoney;
	}

	public void setFirstResve1yuanMoney(Long firstResve1yuanMoney) {
		this.firstResve1yuanMoney = firstResve1yuanMoney;
	}

	public Long getFirstResve2yuanQuantity() {
		return firstResve2yuanQuantity;
	}

	public void setFirstResve2yuanQuantity(Long firstResve2yuanQuantity) {
		this.firstResve2yuanQuantity = firstResve2yuanQuantity;
	}

	public Long getFirstResve2yuanMoney() {
		return firstResve2yuanMoney;
	}

	public void setFirstResve2yuanMoney(Long firstResve2yuanMoney) {
		this.firstResve2yuanMoney = firstResve2yuanMoney;
	}

	public Long getFirstResve5yuanQuantity() {
		return firstResve5yuanQuantity;
	}

	public void setFirstResve5yuanQuantity(Long firstResve5yuanQuantity) {
		this.firstResve5yuanQuantity = firstResve5yuanQuantity;
	}

	public Long getFirstResve5yuanMoney() {
		return firstResve5yuanMoney;
	}

	public void setFirstResve5yuanMoney(Long firstResve5yuanMoney) {
		this.firstResve5yuanMoney = firstResve5yuanMoney;
	}

	public Long getFirstResve10yuanQuantity() {
		return firstResve10yuanQuantity;
	}

	public void setFirstResve10yuanQuantity(Long firstResve10yuanQuantity) {
		this.firstResve10yuanQuantity = firstResve10yuanQuantity;
	}

	public Long getFirstResve10yuanMoney() {
		return firstResve10yuanMoney;
	}

	public void setFirstResve10yuanMoney(Long firstResve10yuanMoney) {
		this.firstResve10yuanMoney = firstResve10yuanMoney;
	}

	public Long getFirstResve20yuanQuantity() {
		return firstResve20yuanQuantity;
	}

	public void setFirstResve20yuanQuantity(Long firstResve20yuanQuantity) {
		this.firstResve20yuanQuantity = firstResve20yuanQuantity;
	}

	public Long getFirstResve20yuanMoney() {
		return firstResve20yuanMoney;
	}

	public void setFirstResve20yuanMoney(Long firstResve20yuanMoney) {
		this.firstResve20yuanMoney = firstResve20yuanMoney;
	}

	public Long getFirstResve50yuanQuantity() {
		return firstResve50yuanQuantity;
	}

	public void setFirstResve50yuanQuantity(Long firstResve50yuanQuantity) {
		this.firstResve50yuanQuantity = firstResve50yuanQuantity;
	}

	public Long getFirstResve50yuanMoney() {
		return firstResve50yuanMoney;
	}

	public void setFirstResve50yuanMoney(Long firstResve50yuanMoney) {
		this.firstResve50yuanMoney = firstResve50yuanMoney;
	}

	public Long getFirstResve100yuanQuantity() {
		return firstResve100yuanQuantity;
	}

	public void setFirstResve100yuanQuantity(Long firstResve100yuanQuantity) {
		this.firstResve100yuanQuantity = firstResve100yuanQuantity;
	}

	public Long getFirstResve100yuanMoney() {
		return firstResve100yuanMoney;
	}

	public void setFirstResve100yuanMoney(Long firstResve100yuanMoney) {
		this.firstResve100yuanMoney = firstResve100yuanMoney;
	}

	public Long getSecondResve1yuanQuantity() {
		return secondResve1yuanQuantity;
	}

	public void setSecondResve1yuanQuantity(Long secondResve1yuanQuantity) {
		this.secondResve1yuanQuantity = secondResve1yuanQuantity;
	}

	public Long getSecondResve1yuanMoney() {
		return secondResve1yuanMoney;
	}

	public void setSecondResve1yuanMoney(Long secondResve1yuanMoney) {
		this.secondResve1yuanMoney = secondResve1yuanMoney;
	}

	public Long getSecondResve2yuanQuantity() {
		return secondResve2yuanQuantity;
	}

	public void setSecondResve2yuanQuantity(Long secondResve2yuanQuantity) {
		this.secondResve2yuanQuantity = secondResve2yuanQuantity;
	}

	public Long getSecondResve2yuanMoney() {
		return secondResve2yuanMoney;
	}

	public void setSecondResve2yuanMoney(Long secondResve2yuanMoney) {
		this.secondResve2yuanMoney = secondResve2yuanMoney;
	}

	public Long getSecondResve5yuanQuantity() {
		return secondResve5yuanQuantity;
	}

	public void setSecondResve5yuanQuantity(Long secondResve5yuanQuantity) {
		this.secondResve5yuanQuantity = secondResve5yuanQuantity;
	}

	public Long getSecondResve5yuanMoney() {
		return secondResve5yuanMoney;
	}

	public void setSecondResve5yuanMoney(Long secondResve5yuanMoney) {
		this.secondResve5yuanMoney = secondResve5yuanMoney;
	}

	public Long getSecondResve10yuanQuantity() {
		return secondResve10yuanQuantity;
	}

	public void setSecondResve10yuanQuantity(Long secondResve10yuanQuantity) {
		this.secondResve10yuanQuantity = secondResve10yuanQuantity;
	}

	public Long getSecondResve10yuanMoney() {
		return secondResve10yuanMoney;
	}

	public void setSecondResve10yuanMoney(Long secondResve10yuanMoney) {
		this.secondResve10yuanMoney = secondResve10yuanMoney;
	}

	public Long getSecondResve20yuanQuantity() {
		return secondResve20yuanQuantity;
	}

	public void setSecondResve20yuanQuantity(Long secondResve20yuanQuantity) {
		this.secondResve20yuanQuantity = secondResve20yuanQuantity;
	}

	public Long getSecondResve20yuanMoney() {
		return secondResve20yuanMoney;
	}

	public void setSecondResve20yuanMoney(Long secondResve20yuanMoney) {
		this.secondResve20yuanMoney = secondResve20yuanMoney;
	}

	public Long getSecondResve50yuanQuantity() {
		return secondResve50yuanQuantity;
	}

	public void setSecondResve50yuanQuantity(Long secondResve50yuanQuantity) {
		this.secondResve50yuanQuantity = secondResve50yuanQuantity;
	}

	public Long getSecondResve50yuanMoney() {
		return secondResve50yuanMoney;
	}

	public void setSecondResve50yuanMoney(Long secondResve50yuanMoney) {
		this.secondResve50yuanMoney = secondResve50yuanMoney;
	}

	public Long getSecondResve100yuanQuantity() {
		return secondResve100yuanQuantity;
	}

	public void setSecondResve100yuanQuantity(Long secondResve100yuanQuantity) {
		this.secondResve100yuanQuantity = secondResve100yuanQuantity;
	}

	public Long getSecondResve100yuanMoney() {
		return secondResve100yuanMoney;
	}

	public void setSecondResve100yuanMoney(Long secondResve100yuanMoney) {
		this.secondResve100yuanMoney = secondResve100yuanMoney;
	}

	public Long getIgnore1yuanQuantity() {
		return ignore1yuanQuantity;
	}

	public void setIgnore1yuanQuantity(Long ignore1yuanQuantity) {
		this.ignore1yuanQuantity = ignore1yuanQuantity;
	}

	public Long getIgnore1yuanMoney() {
		return ignore1yuanMoney;
	}

	public void setIgnore1yuanMoney(Long ignore1yuanMoney) {
		this.ignore1yuanMoney = ignore1yuanMoney;
	}

	public Long getIgnore2yuanQuantity() {
		return ignore2yuanQuantity;
	}

	public void setIgnore2yuanQuantity(Long ignore2yuanQuantity) {
		this.ignore2yuanQuantity = ignore2yuanQuantity;
	}

	public Long getIgnore2yuanMoney() {
		return ignore2yuanMoney;
	}

	public void setIgnore2yuanMoney(Long ignore2yuanMoney) {
		this.ignore2yuanMoney = ignore2yuanMoney;
	}

	public Long getIgnore5yuanQuantity() {
		return ignore5yuanQuantity;
	}

	public void setIgnore5yuanQuantity(Long ignore5yuanQuantity) {
		this.ignore5yuanQuantity = ignore5yuanQuantity;
	}

	public Long getIgnore5yuanMoney() {
		return ignore5yuanMoney;
	}

	public void setIgnore5yuanMoney(Long ignore5yuanMoney) {
		this.ignore5yuanMoney = ignore5yuanMoney;
	}

	public Long getIgnore10yuanQuantity() {
		return ignore10yuanQuantity;
	}

	public void setIgnore10yuanQuantity(Long ignore10yuanQuantity) {
		this.ignore10yuanQuantity = ignore10yuanQuantity;
	}

	public Long getIgnore10yuanMoney() {
		return ignore10yuanMoney;
	}

	public void setIgnore10yuanMoney(Long ignore10yuanMoney) {
		this.ignore10yuanMoney = ignore10yuanMoney;
	}

	public Long getIgnore20yuanQuantity() {
		return ignore20yuanQuantity;
	}

	public void setIgnore20yuanQuantity(Long ignore20yuanQuantity) {
		this.ignore20yuanQuantity = ignore20yuanQuantity;
	}

	public Long getIgnore20yuanMoney() {
		return ignore20yuanMoney;
	}

	public void setIgnore20yuanMoney(Long ignore20yuanMoney) {
		this.ignore20yuanMoney = ignore20yuanMoney;
	}

	public Long getIgnore50yuanQuantity() {
		return ignore50yuanQuantity;
	}

	public void setIgnore50yuanQuantity(Long ignore50yuanQuantity) {
		this.ignore50yuanQuantity = ignore50yuanQuantity;
	}

	public Long getIgnore50yuanMoney() {
		return ignore50yuanMoney;
	}

	public void setIgnore50yuanMoney(Long ignore50yuanMoney) {
		this.ignore50yuanMoney = ignore50yuanMoney;
	}

	public Long getIgnore100yuanQuantity() {
		return ignore100yuanQuantity;
	}

	public void setIgnore100yuanQuantity(Long ignore100yuanQuantity) {
		this.ignore100yuanQuantity = ignore100yuanQuantity;
	}

	public Long getIgnore100yuanMoney() {
		return ignore100yuanMoney;
	}

	public void setIgnore100yuanMoney(Long ignore100yuanMoney) {
		this.ignore100yuanMoney = ignore100yuanMoney;
	}

	public Long getFirstTicketQuantity() {
		return firstTicketQuantity;
	}

	public void setFirstTicketQuantity(Long firstTicketQuantity) {
		this.firstTicketQuantity = firstTicketQuantity;
	}

	public Long getSecondTicketQuantity() {
		return secondTicketQuantity;
	}

	public void setSecondTicketQuantity(Long secondTicketQuantity) {
		this.secondTicketQuantity = secondTicketQuantity;
	}

	public Long getThirdTicketQuantity() {
		return thirdTicketQuantity;
	}

	public void setThirdTicketQuantity(Long thirdTicketQuantity) {
		this.thirdTicketQuantity = thirdTicketQuantity;
	}
}