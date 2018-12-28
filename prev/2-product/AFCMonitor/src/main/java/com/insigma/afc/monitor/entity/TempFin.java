/* 
 * 日期：2015-8-11
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.insigma.commons.ui.anotation.ColumnView;

/**
 * Ticket:收益临时表，用于存储钱箱票箱的实时数据
 * 
 * @author yaoyue
 * @description
 */

@Entity
@Table(name = "TEMP_FIN")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_TEMP_FIN", allocationSize = 1, initialValue = 1)
public class TempFin {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "recordID")
	@ColumnView(name = " 记录号 ")
	private long recordID;

	@ColumnView(name = "更新时间")
	@Column(name = "updataTime")
	private Timestamp updataTime;

	@ColumnView(name = "线路号")
	@Column(name = "lineID")
	private Short lineID;

	@ColumnView(name = "车站号")
	@Column(name = "stationID")
	private Short stationID;

	@ColumnView(name = "节点号")
	@Column(name = "deviceID")
	private Long deviceID;

	@ColumnView(name = "票箱1票卡数量")
	@Column(name = "ticketBox1Amount")
	private Integer ticketBox1Amount;

	@ColumnView(name = "票箱2票卡数量")
	@Column(name = "ticketBox2Amount")
	private Integer ticketBox2Amount;

	@ColumnView(name = "CoinHopper1硬币类型")
	@Column(name = "coinHopper1Type")
	private Integer coinHopper1Type;

	@ColumnView(name = "CoinHopper1硬币数量")
	@Column(name = "coinHopper1Amount")
	private Integer coinHopper1Amount;

	@ColumnView(name = "CoinHopper2硬币类型")
	@Column(name = "coinHopper2Type")
	private Integer coinHopper2Type;

	@ColumnView(name = "CoinHopper2硬币数量")
	@Column(name = "coinHopper2Amount")
	private Integer coinHopper2Amount;

	@ColumnView(name = "纸币回收箱ID")
	@Column(name = "banknoteRecycleBoxID")
	private Long banknoteRecycleBoxID;

	@ColumnView(name = "纸币回收箱金额")
	@Column(name = "banknoteRecycleBoxValue")
	private Long banknoteRecycleBoxValue;

	@ColumnView(name = "纸币循环找零箱ID1")
	@Column(name = "banknoteChangeBoxID1")
	private Long banknoteChangeBoxID1;

	@ColumnView(name = "纸币循环找零箱纸币数量1")
	@Column(name = "banknoteChangeBoxAmount1")
	private Long banknoteChangeBoxAmount1;

	@ColumnView(name = "纸币循环找零箱ID2")
	@Column(name = "banknoteChangeBoxID2")
	private Long banknoteChangeBoxID2;

	@ColumnView(name = "纸币循环找零箱纸币数量2")
	@Column(name = "banknoteChangeBoxAmount2")
	private Long banknoteChangeBoxAmount2;

	@ColumnView(name = "纸币循环找零箱ID3")
	@Column(name = "banknoteChangeBoxID3")
	private Long banknoteChangeBoxID3;

	@ColumnView(name = "纸币循环找零箱纸币数量3")
	@Column(name = "banknoteChangeBoxAmount3")
	private Long banknoteChangeBoxAmount3;

	@ColumnView(name = "纸币循环找零箱ID4")
	@Column(name = "banknoteChangeBoxID4")
	private Long banknoteChangeBoxID4;

	@ColumnView(name = "纸币循环找零箱纸币数量4")
	@Column(name = "banknoteChangeBoxAmount4")
	private Long banknoteChangeBoxAmount4;

	@ColumnView(name = "硬币回收箱ID1")
	@Column(name = "coinRecycleBoxID1")
	private Long coinRecycleBoxID1;

	@ColumnView(name = "硬币回收箱硬币数量1")
	@Column(name = "coinRecycleBoxAmount1")
	private Long coinRecycleBoxAmount1;

	@ColumnView(name = "硬币补币箱ID1")
	@Column(name = "coinRechargeBoxID1")
	private Long coinRechargeBoxID1;

	@ColumnView(name = "硬币补币箱硬币数量1")
	@Column(name = "coinRechargeBoxAmount1")
	private Long coinRechargeBoxAmount1;

	@ColumnView(name = "硬币补币箱ID2")
	@Column(name = "coinRechargeBoxID2")
	private Long coinRechargeBoxID2;

	@ColumnView(name = "硬币补币箱硬币数量2")
	@Column(name = "coinRechargeBoxAmount2")
	private Long coinRechargeBoxAmount2;

	@ColumnView(name = "硬币补币箱ID1")
	@Column(name = "bankNoteRechargeBoxID1")
	private Long bankNoteRechargeBoxID1;

	@ColumnView(name = "纸币补币箱硬币数量1")
	@Column(name = "bankNoteRechargeBoxAmount1")
	private Long bankNoteRechargeBoxAmount1;

	@ColumnView(name = "纸币补币箱ID2")
	@Column(name = "bankNoteRechargeBoxID2")
	private Long bankNoteRechargeBoxID2;

	@ColumnView(name = "纸币补币箱硬币数量2")
	@Column(name = "bankNoteRechargeBoxAmount2")
	private Long bankNoteRechargeBoxAmount2;

	@ColumnView(name = "废票箱票卡数量")
	@Column(name = "invalidTicketBoxAmount")
	private Integer invalidTicketBoxAmount;

	@ColumnView(name = "纸币找零箱回收箱金额")
	@Column(name = "banknoteChangeBoxValue")
	private Long banknoteChangeBoxValue;

	public long getRecordID() {
		return recordID;
	}

	public void setRecordID(long recordID) {
		this.recordID = recordID;
	}

	public Timestamp getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(Timestamp updataTime) {
		this.updataTime = updataTime;
	}

	public Short getLineID() {
		return lineID;
	}

	public void setLineID(Short lineID) {
		this.lineID = lineID;
	}

	public Short getStationID() {
		return stationID;
	}

	public void setStationID(Short stationID) {
		this.stationID = stationID;
	}

	public Long getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Long deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getTicketBox1Amount() {
		return ticketBox1Amount;
	}

	public void setTicketBox1Amount(Integer ticketBox1Amount) {
		this.ticketBox1Amount = ticketBox1Amount;
	}

	public Integer getTicketBox2Amount() {
		return ticketBox2Amount;
	}

	public void setTicketBox2Amount(Integer ticketBox2Amount) {
		this.ticketBox2Amount = ticketBox2Amount;
	}

	public Integer getCoinHopper1Type() {
		return coinHopper1Type;
	}

	public void setCoinHopper1Type(Integer coinHopper1Type) {
		this.coinHopper1Type = coinHopper1Type;
	}

	public Integer getCoinHopper1Amount() {
		return coinHopper1Amount;
	}

	public void setCoinHopper1Amount(Integer coinHopper1Amount) {
		this.coinHopper1Amount = coinHopper1Amount;
	}

	public Integer getCoinHopper2Type() {
		return coinHopper2Type;
	}

	public void setCoinHopper2Type(Integer coinHopper2Type) {
		this.coinHopper2Type = coinHopper2Type;
	}

	public Integer getCoinHopper2Amount() {
		return coinHopper2Amount;
	}

	public void setCoinHopper2Amount(Integer coinHopper2Amount) {
		this.coinHopper2Amount = coinHopper2Amount;
	}

	public Long getBanknoteRecycleBoxID() {
		return banknoteRecycleBoxID;
	}

	public void setBanknoteRecycleBoxID(Long banknoteRecycleBoxID) {
		this.banknoteRecycleBoxID = banknoteRecycleBoxID;
	}

	public Long getBanknoteRecycleBoxValue() {
		return banknoteRecycleBoxValue;
	}

	public void setBanknoteRecycleBoxValue(Long banknoteRecycleBoxValue) {
		this.banknoteRecycleBoxValue = banknoteRecycleBoxValue;
	}

	public Long getBanknoteChangeBoxID1() {
		return banknoteChangeBoxID1;
	}

	public void setBanknoteChangeBoxID1(Long banknoteChangeBoxID1) {
		this.banknoteChangeBoxID1 = banknoteChangeBoxID1;
	}

	public Long getBanknoteChangeBoxAmount1() {
		return banknoteChangeBoxAmount1;
	}

	public void setBanknoteChangeBoxAmount1(Long banknoteChangeBoxAmount1) {
		this.banknoteChangeBoxAmount1 = banknoteChangeBoxAmount1;
	}

	public Long getBanknoteChangeBoxID2() {
		return banknoteChangeBoxID2;
	}

	public void setBanknoteChangeBoxID2(Long banknoteChangeBoxID2) {
		this.banknoteChangeBoxID2 = banknoteChangeBoxID2;
	}

	public Long getBanknoteChangeBoxAmount2() {
		return banknoteChangeBoxAmount2;
	}

	public void setBanknoteChangeBoxAmount2(Long banknoteChangeBoxAmount2) {
		this.banknoteChangeBoxAmount2 = banknoteChangeBoxAmount2;
	}

	public Long getBanknoteChangeBoxID3() {
		return banknoteChangeBoxID3;
	}

	public void setBanknoteChangeBoxID3(Long banknoteChangeBoxID3) {
		this.banknoteChangeBoxID3 = banknoteChangeBoxID3;
	}

	public Long getBanknoteChangeBoxAmount3() {
		return banknoteChangeBoxAmount3;
	}

	public void setBanknoteChangeBoxAmount3(Long banknoteChangeBoxAmount3) {
		this.banknoteChangeBoxAmount3 = banknoteChangeBoxAmount3;
	}

	public Long getBanknoteChangeBoxID4() {
		return banknoteChangeBoxID4;
	}

	public void setBanknoteChangeBoxID4(Long banknoteChangeBoxID4) {
		this.banknoteChangeBoxID4 = banknoteChangeBoxID4;
	}

	public Long getBanknoteChangeBoxAmount4() {
		return banknoteChangeBoxAmount4;
	}

	public void setBanknoteChangeBoxAmount4(Long banknoteChangeBoxAmount4) {
		this.banknoteChangeBoxAmount4 = banknoteChangeBoxAmount4;
	}

	public Long getCoinRecycleBoxID1() {
		return coinRecycleBoxID1;
	}

	public void setCoinRecycleBoxID1(Long coinRecycleBoxID1) {
		this.coinRecycleBoxID1 = coinRecycleBoxID1;
	}

	public Long getCoinRecycleBoxAmount1() {
		return coinRecycleBoxAmount1;
	}

	public void setCoinRecycleBoxAmount1(Long coinRecycleBoxAmount1) {
		this.coinRecycleBoxAmount1 = coinRecycleBoxAmount1;
	}

	public Long getCoinRechargeBoxID1() {
		return coinRechargeBoxID1;
	}

	public void setCoinRechargeBoxID1(Long coinRechargeBoxID1) {
		this.coinRechargeBoxID1 = coinRechargeBoxID1;
	}

	public Long getCoinRechargeBoxAmount1() {
		return coinRechargeBoxAmount1;
	}

	public void setCoinRechargeBoxAmount1(Long coinRechargeBoxAmount1) {
		this.coinRechargeBoxAmount1 = coinRechargeBoxAmount1;
	}

	public Long getCoinRechargeBoxID2() {
		return coinRechargeBoxID2;
	}

	public void setCoinRechargeBoxID2(Long coinRechargeBoxID2) {
		this.coinRechargeBoxID2 = coinRechargeBoxID2;
	}

	public Long getCoinRechargeBoxAmount2() {
		return coinRechargeBoxAmount2;
	}

	public void setCoinRechargeBoxAmount2(Long coinRechargeBoxAmount2) {
		this.coinRechargeBoxAmount2 = coinRechargeBoxAmount2;
	}

	public Long getBankNoteRechargeBoxID1() {
		return bankNoteRechargeBoxID1;
	}

	public void setBankNoteRechargeBoxID1(Long bankNoteRechargeBoxID1) {
		this.bankNoteRechargeBoxID1 = bankNoteRechargeBoxID1;
	}

	public Long getBankNoteRechargeBoxAmount1() {
		return bankNoteRechargeBoxAmount1;
	}

	public void setBankNoteRechargeBoxAmount1(Long bankNoteRechargeBoxAmount1) {
		this.bankNoteRechargeBoxAmount1 = bankNoteRechargeBoxAmount1;
	}

	public Long getBankNoteRechargeBoxID2() {
		return bankNoteRechargeBoxID2;
	}

	public void setBankNoteRechargeBoxID2(Long bankNoteRechargeBoxID2) {
		this.bankNoteRechargeBoxID2 = bankNoteRechargeBoxID2;
	}

	public Long getBankNoteRechargeBoxAmount2() {
		return bankNoteRechargeBoxAmount2;
	}

	public void setBankNoteRechargeBoxAmount2(Long bankNoteRechargeBoxAmount2) {
		this.bankNoteRechargeBoxAmount2 = bankNoteRechargeBoxAmount2;
	}

	public Integer getInvalidTicketBoxAmount() {
		return invalidTicketBoxAmount;
	}

	public void setInvalidTicketBoxAmount(Integer invalidTicketBoxAmount) {
		this.invalidTicketBoxAmount = invalidTicketBoxAmount;
	}

	public Long getBanknoteChangeBoxValue() {
		return banknoteChangeBoxValue;
	}

	public void setBanknoteChangeBoxValue(Long banknoteChangeBoxValue) {
		this.banknoteChangeBoxValue = banknoteChangeBoxValue;
	}

}
