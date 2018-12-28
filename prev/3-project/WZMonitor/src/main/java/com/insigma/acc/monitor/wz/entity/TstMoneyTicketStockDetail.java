/* 
 * 日期：2017年6月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.monitor.wz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Ticket: TST_MONEY_TICKET_STOCK_DETAIL实体
 * @author  mengyifan
 *
 */

@Entity
@Table(name = "TST_MONEY_TICKET_STOCK_DETAIL")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_MT_STOCK_DETAIL")
public class TstMoneyTicketStockDetail {

	@Id
	@Column(name = "record_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private long recordID;

	/**
	 * 报文类型
	 */
	@Column(name = "message_id")
	private long messageId;

	@Column(name = "line_id")
	private long lineId;

	@Column(name = "station_id")
	private int stationId;

	@Column(name = "device_id")
	private long DeviceID;

	@Column(name = "device_ip")
	private long DeviceIP;

	@Column(name = "bom_shift_code")
	private long BOMShiftCode;

	@Column(name = "event_type")
	private short EventType;

	@Column(name = "container_id")
	private long ContainerID;

	@Column(name = "operate_time")
	private java.sql.Timestamp OperateTime;

	@Column(name = "operator_id")
	private long OperatorID;

	@Column(name = "bom_shift_id")
	private long BOMShiftID;

	@Column(name = "operate_flag")
	private short OperateFlag;

	@Column(name = "ticket_quantity")
	private long TicketQuantity;

	@Column(name = "bank_note_box_id")
	private long BankNoteBoxID;

	@Column(name = "operate_tag")
	private short OperateTag;

	@Column(name = "total_money ")
	private int TotalMoney;

	// 纸币主找零1
	@Column(name = "first_chief_1yuan_quantity ")
	private long FirstChiefOneYuanQuantity;

	@Column(name = "first_chief_1yuan_money")
	private int FirstChiefOneYuanMoney;

	@Column(name = "first_chief_2yuan_quantity")
	private long FirstChiefTwoYuanQuantity;

	@Column(name = "first_chief_2yuan_money")
	private int FirstChiefTwoYuanMoney;

	@Column(name = "first_chief_5yuan_quantity")
	private long FirstChiefFiveYuanQuantity;

	@Column(name = "first_chief_5yuan_money")
	private int FirstChiefFiveYuanMoney;

	@Column(name = "first_chief_10yuan_quantity")
	private long FirstChiefTenYuanQuantity;

	@Column(name = "first_chief_10yuan_money")
	private int FirstChiefTenYuanMoney;

	@Column(name = "first_chief_20yuan_quantity")
	private long FirstChiefTwentyYuanQuantity;

	@Column(name = "first_chief_20yuan_money")
	private int FirstChiefTwentyYuanMoney;

	@Column(name = "first_chief_50yuan_quantity")
	private long FirstChiefFiftyYuanQuantity;

	@Column(name = "first_chief_50yuan_money")
	private int FirstChiefFiftyYuanMoney;

	@Column(name = "first_chief_100yuan_quantity")
	private long FirstChiefHundredYuanQuantity;

	@Column(name = "first_chief_100yuan_money")
	private int FirstChiefHundredYuanMoney;

	// 纸币主找零2
	@Column(name = "second_chief_1yuan_quantity ")
	private long SecondChiefOneYuanQuantity;

	@Column(name = "second_chief_1yuan_money")
	private int SecondChiefOneYuanMoney;

	@Column(name = "second_chief_2yuan_quantity")
	private long SecondChiefTwoYuanQuantity;

	@Column(name = "second_chief_2yuan_money")
	private int SecondChiefTwoYuanMoney;

	@Column(name = "second_chief_5yuan_quantity")
	private long SecondChiefFiveYuanQuantity;

	@Column(name = "second_chief_5yuan_money")
	private int SecondChiefFiveYuanMoney;

	@Column(name = "second_chief_10yuan_quantity")
	private long SecondChiefTenYuanQuantity;

	@Column(name = "second_chief_10yuan_money")
	private int SecondChiefTenYuanMoney;

	@Column(name = "second_chief_20yuan_quantity")
	private long SecondChiefTwentyYuanQuantity;

	@Column(name = "second_chief_20yuan_money")
	private int SecondChiefTwentyYuanMoney;

	@Column(name = "second_chief_50yuan_quantity")
	private long SecondChiefFiftyYuanQuantity;

	@Column(name = "second_chief_50yuan_money")
	private int SecondChiefFiftyYuanMoney;

	@Column(name = "second_chief_100yuan_quantity")
	private long SecondChiefHundredYuanQuantity;

	@Column(name = "second_chief_100yuan_money")
	private int SecondChiefHundredYuanMoney;

	// 纸币循环找零1
	@Column(name = "first_cyc_1yuan_quantity ")
	private long FirstCycOneYuanQuantity;

	@Column(name = "first_cyc_1yuan_money")
	private int FirstCycOneYuanMoney;

	@Column(name = "first_cyc_2yuan_quantity")
	private long FirstCycTwoYuanQuantity;

	@Column(name = "first_cyc_2yuan_money")
	private int FirstCycTwoYuanMoney;

	@Column(name = "first_cyc_5yuan_quantity")
	private long FirstCycFiveYuanQuantity;

	@Column(name = "first_cyc_5yuan_money")
	private int FirstCycFiveYuanMoney;

	@Column(name = "first_cyc_10yuan_quantity")
	private long FirstCycTenYuanQuantity;

	@Column(name = "first_cyc_10yuan_money")
	private int FirstCycTenYuanMoney;

	@Column(name = "first_cyc_20yuan_quantity")
	private long FirstCycTwentyYuanQuantity;

	@Column(name = "first_cyc_20yuan_money")
	private int FirstCycTwentyYuanMoney;

	@Column(name = "first_cyc_50yuan_quantity")
	private long FirstCycFiftyYuanQuantity;

	@Column(name = "first_cyc_50yuan_money")
	private int FirstCycFiftyYuanMoney;

	@Column(name = "first_cyc_100yuan_quantity")
	private long FirstCycHundredYuanQuantity;

	@Column(name = "first_cyc_100yuan_money")
	private int FirstCycHundredYuanMoney;

	// 纸币循环找零2
	@Column(name = "second_cyc_1yuan_quantity ")
	private long SecondCycOneYuanQuantity;

	@Column(name = "second_cyc_1yuan_money")
	private int SecondCycOneYuanMoney;

	@Column(name = "second_cyc_2yuan_quantity")
	private long SecondCycTwoYuanQuantity;

	@Column(name = "second_cyc_2yuan_money")
	private int SecondCycTwoYuanMoney;

	@Column(name = "second_cyc_5yuan_quantity")
	private long SecondCycFiveYuanQuantity;

	@Column(name = "second_cyc_5yuan_money")
	private int SecondCycFiveYuanMoney;

	@Column(name = "second_cyc_10yuan_quantity")
	private long SecondCycTenYuanQuantity;

	@Column(name = "second_cyc_10yuan_money")
	private int SecondCycTenYuanMoney;

	@Column(name = "second_cyc_20yuan_quantity")
	private long SecondCycTwentyYuanQuantity;

	@Column(name = "second_cyc_20yuan_money")
	private int SecondCycTwentyYuanMoney;

	@Column(name = "second_cyc_50yuan_quantity")
	private long SecondCycFiftyYuanQuantity;

	@Column(name = "second_cyc_50yuan_money")
	private int SecondCycFiftyYuanMoney;

	@Column(name = "second_cyc_100yuan_quantity")
	private long SecondCycHundredYuanQuantity;

	@Column(name = "second_cyc_100yuan_money")
	private int SecondCycHundredYuanMoney;

	// 纸币回收箱
	@Column(name = "recycle_1yuan_quantity ")
	private long RecycleOneYuanQuantity;

	@Column(name = "recycle_1yuan_money")
	private int RecycleOneYuanMoney;

	@Column(name = "recycle_2yuan_quantity")
	private long RecycleTwoYuanQuantity;

	@Column(name = "recycle_2yuan_money")
	private int RecycleTwoYuanMoney;

	@Column(name = "recycle_5yuan_quantity")
	private long RecycleFiveYuanQuantity;

	@Column(name = "recycle_5yuan_money")
	private int RecycleFiveYuanMoney;

	@Column(name = "recycle_10yuan_quantity")
	private long RecycleTenYuanQuantity;

	@Column(name = "recycle_10yuan_money")
	private int RecycleTenYuanMoney;

	@Column(name = "recycle_20yuan_quantity")
	private long RecycleTwentyYuanQuantity;

	@Column(name = "recycle_20yuan_money")
	private int RecycleTwentyYuanMoney;

	@Column(name = "recycle_50yuan_quantity")
	private long RecycleFiftyYuanQuantity;

	@Column(name = "recycle_50yuan_money")
	private int RecycleFiftyYuanMoney;

	@Column(name = "recycle_100yuan_quantity")
	private long RecycleHundredYuanQuantity;

	@Column(name = "recycle_100yuan_money")
	private int RecycleHundredYuanMoney;

	// 纸币预留1
	@Column(name = "first_resve_1yuan_quantity")
	private long FirstResveOneYuanQuantity;

	@Column(name = "first_resve_1yuan_money")
	private int FirstResveOneYuanMoney;

	@Column(name = "first_resve_2yuan_quantity")
	private long FirstResveTwoYuanQuantity;

	@Column(name = "first_resve_2yuan_money")
	private int FirstResveTwoYuanMoney;

	@Column(name = "first_resve_5yuan_quantity")
	private long FirstResveFiveYuanQuantity;

	@Column(name = "first_resve_5yuan_money")
	private int FirstResveFiveYuanMoney;

	@Column(name = "first_resve_10yuan_quantity")
	private long FirstResveTenYuanQuantity;

	@Column(name = "first_resve_10yuan_money")
	private int FirstResveTenYuanMoney;

	@Column(name = "first_resve_20yuan_quantity")
	private long FirstResveTwentyYuanQuantity;

	@Column(name = "first_resve_20yuan_money")
	private int FirstResveTwentyYuanMoney;

	@Column(name = "first_resve_50yuan_quantity")
	private long FirstResveFiftyYuanQuantity;

	@Column(name = "first_resve_50yuan_money")
	private int FirstResveFiftyYuanMoney;

	@Column(name = "first_resve_100yuan_quantity")
	private long FirstResveHundredYuanQuantity;

	@Column(name = "first_resve_100yuan_money")
	private int FirstResveHundredYuanMoney;

	// 纸币预留2
	@Column(name = "second_resve_1yuan_quantity ")
	private long SecondResveOneYuanQuantity;

	@Column(name = "second_resve_1yuan_money")
	private int SecondResveOneYuanMoney;

	@Column(name = "second_resve_2yuan_quantity")
	private long SecondResveTwoYuanQuantity;

	@Column(name = "second_resve_2yuan_money")
	private int SecondResveTwoYuanMoney;

	@Column(name = "second_resve_5yuan_quantity")
	private long SecondResveFiveYuanQuantity;

	@Column(name = "second_resve_5yuan_money")
	private int SecondResveFiveYuanMoney;

	@Column(name = "second_resve_10yuan_quantity")
	private long SecondResveTenYuanQuantity;

	@Column(name = "second_resve_10yuan_money")
	private int SecondResveTenYuanMoney;

	@Column(name = "second_resve_20yuan_quantity")
	private long SecondResveTwentyYuanQuantity;

	@Column(name = "second_resve_20yuan_money")
	private int SecondResveTwentyYuanMoney;

	@Column(name = "second_resve_50yuan_quantity")
	private long SecondResveFiftyYuanQuantity;

	@Column(name = "second_resve_50yuan_money")
	private int SecondResveFiftyYuanMoney;

	@Column(name = "second_resve_100yuan_quantity")
	private long SecondResveHundredYuanQuantity;

	@Column(name = "second_resve_100yuan_money")
	private int SecondResveHundredYuanMoney;

	//  纸币废币箱
	@Column(name = "ignore_1yuan_quantity ")
	private long FirstIgnoreOneYuanQuantity;

	@Column(name = "ignore_1yuan_money")
	private int FirstIgnoreOneYuanMoney;

	@Column(name = "ignore_2yuan_quantity")
	private long FirstIgnoreTwoYuanQuantity;

	@Column(name = "ignore_2yuan_money")
	private int FirstIgnoreTwoYuanMoney;

	@Column(name = "ignore_5yuan_quantity")
	private long FirstIgnoreFiveYuanQuantity;

	@Column(name = "ignore_5yuan_money")
	private int FirstIgnoreFiveYuanMoney;

	@Column(name = "ignore_10yuan_quantity")
	private long FirstIgnoreTenYuanQuantity;

	@Column(name = "ignore_10yuan_money")
	private int FirstIgnoreTenYuanMoney;

	@Column(name = "ignore_20yuan_quantity")
	private long FirstIgnoreTwentyYuanQuantity;

	@Column(name = "ignore_20yuan_money")
	private int FirstIgnoreTwentyYuanMoney;

	@Column(name = "ignore_50yuan_quantity")
	private long FirstIgnoreFiftyYuanQuantity;

	@Column(name = "ignore_50yuan_money")
	private int FirstIgnoreFiftyYuanMoney;

	@Column(name = "ignore_100yuan_quantity")
	private long FirstIgnoreHundredYuanQuantity;

	@Column(name = "ignore_100yuan_money")
	private int FirstIgnoreHundredYuanMoney;

	@Column(name = "coin_operatepos")
	private short CoinOperatePos;

	@Column(name = "coin_box_id")
	private long CoinBoxID;

	//	@Column(name = "coin_total_money")
	//	private long CoinTotalMoney;

	// 硬币主找零
	@Column(name = "first_chief_5jiao_quantity")
	private long FirstChiefFiveJiaoQuantity;

	@Column(name = "first_chief_5jiao_money")
	private int FirstChiefFiveJiaoMoney;

	@Column(name = "first_chief_10jiao_quantity")
	private long FirstChiefTenJiaoQuantity;

	@Column(name = "first_chief_10jiao_money")
	private int FirstChiefTenJiaoMoney;

	@Column(name = "second_chief_5jiao_quantity")
	private long SecondChiefFiveJiaoQuantity;

	@Column(name = "second_chief_5jiao_money")
	private int SecondChiefFiveJiaoMoney;

	@Column(name = "second_chief_10jiao_quantity")
	private long SecondChiefTenJiaoQuantity;

	@Column(name = "second_chief_10jiao_money")
	private int SecondChiefTenJiaoMoney;

	// 硬币循环找零
	@Column(name = "first_cyc_5jiao_quantity")
	private long FirstCycFiveJiaoQuantity;

	@Column(name = "first_cyc_5jiao_money")
	private int FirstCycFiveJiaoMoney;

	@Column(name = "first_cyc_10jiao_quantity")
	private long FirstCycTenJiaoQuantity;

	@Column(name = "first_cyc_10jiao_money")
	private int FirstCycTenJiaoMoney;

	@Column(name = "second_cyc_5jiao_quantity")
	private long SecondCycFiveJiaoQuantity;

	@Column(name = "second_cyc_5jiao_money")
	private int SecondCycFiveJiaoMoney;

	@Column(name = "second_cyc_10jiao_quantity")
	private long SecondCycTenJiaoQuantity;

	@Column(name = "second_cyc_10jiao_money")
	private int SecondCycTenJiaoMoney;

	// 硬币回收
	@Column(name = "recycle_5jiao_quantity")
	private long RecycleFiveJiaoQuantity;

	@Column(name = "recycle_5jiao_money")
	private int RecycleFiveJiaoMoney;

	@Column(name = "recycle_10jiao_quantity")
	private long RecycleTenJiaoQuantity;

	@Column(name = "recycle_10jiao_money")
	private int RecycleTenJiaoMoney;

	// 硬币预留1
	@Column(name = "first_resve_5jiao_quantity")
	private long FirstResveFiveJiaoQuantity;

	@Column(name = "first_resve_5jiao_money")
	private int FirstResveFiveJiaoMoney;

	@Column(name = "first_resve_10jiao_quantity")
	private long FirstResveTenJiaoQuantity;

	@Column(name = "first_resve_10jiao_money")
	private int FirstResveTenJiaoMoney;

	// 硬币预留2
	@Column(name = "second_resve_5jiao_quantity")
	private long SecondResveFiveJiaoQuantity;

	@Column(name = "second_resve_5jiao_money")
	private int SecondResveFiveJiaoMoney;

	@Column(name = "second_resve_10jiao_quantity")
	private long SecondResveTenJiaoQuantity;

	@Column(name = "second_resve_10jiao_money")
	private int SecondResveTenJiaoMoney;

	@Column(name = "operator_type")
	private Short operatorType;

	@Column(name = "remarks")
	private String remarks;

	/**
	 * @return the recordID
	 */
	public long getRecordID() {
		return recordID;
	}

	/**
	 * @param recordID the recordID to set
	 */
	public void setRecordID(long recordID) {
		this.recordID = recordID;
	}

	/**
	 * @return the messageId
	 */
	public long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the lineId
	 */
	public long getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(long lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the stationId
	 */
	public int getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the deviceID
	 */
	public long getDeviceID() {
		return DeviceID;
	}

	/**
	 * @param deviceID the deviceID to set
	 */
	public void setDeviceID(long deviceID) {
		DeviceID = deviceID;
	}

	/**
	 * @return the deviceIP
	 */
	public long getDeviceIP() {
		return DeviceIP;
	}

	/**
	 * @param deviceIP the deviceIP to set
	 */
	public void setDeviceIP(long deviceIP) {
		DeviceIP = deviceIP;
	}

	/**
	 * @return the bOMShiftCode
	 */
	public long getBOMShiftCode() {
		return BOMShiftCode;
	}

	/**
	 * @param bOMShiftCode the bOMShiftCode to set
	 */
	public void setBOMShiftCode(long bOMShiftCode) {
		BOMShiftCode = bOMShiftCode;
	}

	/**
	 * @return the eventType
	 */
	public short getEventType() {
		return EventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(short eventType) {
		EventType = eventType;
	}

	/**
	 * @return the containerID
	 */
	public long getContainerID() {
		return ContainerID;
	}

	/**
	 * @param containerID the containerID to set
	 */
	public void setContainerID(long containerID) {
		ContainerID = containerID;
	}

	/**
	 * @return the operateTime
	 */
	public java.sql.Timestamp getOperateTime() {
		return OperateTime;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(java.sql.Timestamp operateTime) {
		OperateTime = operateTime;
	}

	/**
	 * @return the operatorID
	 */
	public long getOperatorID() {
		return OperatorID;
	}

	/**
	 * @param operatorID the operatorID to set
	 */
	public void setOperatorID(long operatorID) {
		OperatorID = operatorID;
	}

	/**
	 * @return the bOMShiftID
	 */
	public long getBOMShiftID() {
		return BOMShiftID;
	}

	/**
	 * @param bOMShiftID the bOMShiftID to set
	 */
	public void setBOMShiftID(long bOMShiftID) {
		BOMShiftID = bOMShiftID;
	}

	/**
	 * @return the operateFlag
	 */
	public short getOperateFlag() {
		return OperateFlag;
	}

	/**
	 * @param operateFlag the operateFlag to set
	 */
	public void setOperateFlag(short operateFlag) {
		OperateFlag = operateFlag;
	}

	/**
	 * @return the ticketQuantity
	 */
	public long getTicketQuantity() {
		return TicketQuantity;
	}

	/**
	 * @param ticketQuantity the ticketQuantity to set
	 */
	public void setTicketQuantity(long ticketQuantity) {
		TicketQuantity = ticketQuantity;
	}

	/**
	 * @return the bankNoteBoxID
	 */
	public long getBankNoteBoxID() {
		return BankNoteBoxID;
	}

	/**
	 * @param bankNoteBoxID the bankNoteBoxID to set
	 */
	public void setBankNoteBoxID(long bankNoteBoxID) {
		BankNoteBoxID = bankNoteBoxID;
	}

	/**
	 * @return the operateTag
	 */
	public short getOperateTag() {
		return OperateTag;
	}

	/**
	 * @param operateTag the operateTag to set
	 */
	public void setOperateTag(short operateTag) {
		OperateTag = operateTag;
	}

	/**
	 * @return the totalMoney
	 */
	public int getTotalMoney() {
		return TotalMoney;
	}

	/**
	 * @param totalMoney the totalMoney to set
	 */
	public void setTotalMoney(int totalMoney) {
		TotalMoney = totalMoney;
	}

	/**
	 * @return the firstChiefOneYuanQuantity
	 */
	public long getFirstChiefOneYuanQuantity() {
		return FirstChiefOneYuanQuantity;
	}

	/**
	 * @param firstChiefOneYuanQuantity the firstChiefOneYuanQuantity to set
	 */
	public void setFirstChiefOneYuanQuantity(long firstChiefOneYuanQuantity) {
		FirstChiefOneYuanQuantity = firstChiefOneYuanQuantity;
	}

	/**
	 * @return the firstChiefOneYuanMoney
	 */
	public int getFirstChiefOneYuanMoney() {
		return FirstChiefOneYuanMoney;
	}

	/**
	 * @param firstChiefOneYuanMoney the firstChiefOneYuanMoney to set
	 */
	public void setFirstChiefOneYuanMoney(int firstChiefOneYuanMoney) {
		FirstChiefOneYuanMoney = firstChiefOneYuanMoney;
	}

	/**
	 * @return the firstChiefTwoYuanQuantity
	 */
	public long getFirstChiefTwoYuanQuantity() {
		return FirstChiefTwoYuanQuantity;
	}

	/**
	 * @param firstChiefTwoYuanQuantity the firstChiefTwoYuanQuantity to set
	 */
	public void setFirstChiefTwoYuanQuantity(long firstChiefTwoYuanQuantity) {
		FirstChiefTwoYuanQuantity = firstChiefTwoYuanQuantity;
	}

	/**
	 * @return the firstChiefTwoYuanMoney
	 */
	public int getFirstChiefTwoYuanMoney() {
		return FirstChiefTwoYuanMoney;
	}

	/**
	 * @param firstChiefTwoYuanMoney the firstChiefTwoYuanMoney to set
	 */
	public void setFirstChiefTwoYuanMoney(int firstChiefTwoYuanMoney) {
		FirstChiefTwoYuanMoney = firstChiefTwoYuanMoney;
	}

	/**
	 * @return the firstChiefFiveYuanQuantity
	 */
	public long getFirstChiefFiveYuanQuantity() {
		return FirstChiefFiveYuanQuantity;
	}

	/**
	 * @param firstChiefFiveYuanQuantity the firstChiefFiveYuanQuantity to set
	 */
	public void setFirstChiefFiveYuanQuantity(long firstChiefFiveYuanQuantity) {
		FirstChiefFiveYuanQuantity = firstChiefFiveYuanQuantity;
	}

	/**
	 * @return the firstChiefFiveYuanMoney
	 */
	public int getFirstChiefFiveYuanMoney() {
		return FirstChiefFiveYuanMoney;
	}

	/**
	 * @param firstChiefFiveYuanMoney the firstChiefFiveYuanMoney to set
	 */
	public void setFirstChiefFiveYuanMoney(int firstChiefFiveYuanMoney) {
		FirstChiefFiveYuanMoney = firstChiefFiveYuanMoney;
	}

	/**
	 * @return the firstChiefTenYuanQuantity
	 */
	public long getFirstChiefTenYuanQuantity() {
		return FirstChiefTenYuanQuantity;
	}

	/**
	 * @param firstChiefTenYuanQuantity the firstChiefTenYuanQuantity to set
	 */
	public void setFirstChiefTenYuanQuantity(long firstChiefTenYuanQuantity) {
		FirstChiefTenYuanQuantity = firstChiefTenYuanQuantity;
	}

	/**
	 * @return the firstChiefTenYuanMoney
	 */
	public int getFirstChiefTenYuanMoney() {
		return FirstChiefTenYuanMoney;
	}

	/**
	 * @param firstChiefTenYuanMoney the firstChiefTenYuanMoney to set
	 */
	public void setFirstChiefTenYuanMoney(int firstChiefTenYuanMoney) {
		FirstChiefTenYuanMoney = firstChiefTenYuanMoney;
	}

	/**
	 * @return the firstChiefTwentyYuanQuantity
	 */
	public long getFirstChiefTwentyYuanQuantity() {
		return FirstChiefTwentyYuanQuantity;
	}

	/**
	 * @param firstChiefTwentyYuanQuantity the firstChiefTwentyYuanQuantity to set
	 */
	public void setFirstChiefTwentyYuanQuantity(long firstChiefTwentyYuanQuantity) {
		FirstChiefTwentyYuanQuantity = firstChiefTwentyYuanQuantity;
	}

	/**
	 * @return the firstChiefTwentyYuanMoney
	 */
	public int getFirstChiefTwentyYuanMoney() {
		return FirstChiefTwentyYuanMoney;
	}

	/**
	 * @param firstChiefTwentyYuanMoney the firstChiefTwentyYuanMoney to set
	 */
	public void setFirstChiefTwentyYuanMoney(int firstChiefTwentyYuanMoney) {
		FirstChiefTwentyYuanMoney = firstChiefTwentyYuanMoney;
	}

	/**
	 * @return the firstChiefFiftyYuanQuantity
	 */
	public long getFirstChiefFiftyYuanQuantity() {
		return FirstChiefFiftyYuanQuantity;
	}

	/**
	 * @param firstChiefFiftyYuanQuantity the firstChiefFiftyYuanQuantity to set
	 */
	public void setFirstChiefFiftyYuanQuantity(long firstChiefFiftyYuanQuantity) {
		FirstChiefFiftyYuanQuantity = firstChiefFiftyYuanQuantity;
	}

	/**
	 * @return the firstChiefFiftyYuanMoney
	 */
	public int getFirstChiefFiftyYuanMoney() {
		return FirstChiefFiftyYuanMoney;
	}

	/**
	 * @param firstChiefFiftyYuanMoney the firstChiefFiftyYuanMoney to set
	 */
	public void setFirstChiefFiftyYuanMoney(int firstChiefFiftyYuanMoney) {
		FirstChiefFiftyYuanMoney = firstChiefFiftyYuanMoney;
	}

	/**
	 * @return the firstChiefHundredYuanQuantity
	 */
	public long getFirstChiefHundredYuanQuantity() {
		return FirstChiefHundredYuanQuantity;
	}

	/**
	 * @param firstChiefHundredYuanQuantity the firstChiefHundredYuanQuantity to set
	 */
	public void setFirstChiefHundredYuanQuantity(long firstChiefHundredYuanQuantity) {
		FirstChiefHundredYuanQuantity = firstChiefHundredYuanQuantity;
	}

	/**
	 * @return the firstChiefHundredYuanMoney
	 */
	public int getFirstChiefHundredYuanMoney() {
		return FirstChiefHundredYuanMoney;
	}

	/**
	 * @param firstChiefHundredYuanMoney the firstChiefHundredYuanMoney to set
	 */
	public void setFirstChiefHundredYuanMoney(int firstChiefHundredYuanMoney) {
		FirstChiefHundredYuanMoney = firstChiefHundredYuanMoney;
	}

	/**
	 * @return the secondChiefOneYuanQuantity
	 */
	public long getSecondChiefOneYuanQuantity() {
		return SecondChiefOneYuanQuantity;
	}

	/**
	 * @param secondChiefOneYuanQuantity the secondChiefOneYuanQuantity to set
	 */
	public void setSecondChiefOneYuanQuantity(long secondChiefOneYuanQuantity) {
		SecondChiefOneYuanQuantity = secondChiefOneYuanQuantity;
	}

	/**
	 * @return the secondChiefOneYuanMoney
	 */
	public int getSecondChiefOneYuanMoney() {
		return SecondChiefOneYuanMoney;
	}

	/**
	 * @param secondChiefOneYuanMoney the secondChiefOneYuanMoney to set
	 */
	public void setSecondChiefOneYuanMoney(int secondChiefOneYuanMoney) {
		SecondChiefOneYuanMoney = secondChiefOneYuanMoney;
	}

	/**
	 * @return the secondChiefTwoYuanQuantity
	 */
	public long getSecondChiefTwoYuanQuantity() {
		return SecondChiefTwoYuanQuantity;
	}

	/**
	 * @param secondChiefTwoYuanQuantity the secondChiefTwoYuanQuantity to set
	 */
	public void setSecondChiefTwoYuanQuantity(long secondChiefTwoYuanQuantity) {
		SecondChiefTwoYuanQuantity = secondChiefTwoYuanQuantity;
	}

	/**
	 * @return the secondChiefTwoYuanMoney
	 */
	public int getSecondChiefTwoYuanMoney() {
		return SecondChiefTwoYuanMoney;
	}

	/**
	 * @param secondChiefTwoYuanMoney the secondChiefTwoYuanMoney to set
	 */
	public void setSecondChiefTwoYuanMoney(int secondChiefTwoYuanMoney) {
		SecondChiefTwoYuanMoney = secondChiefTwoYuanMoney;
	}

	/**
	 * @return the secondChiefFiveYuanQuantity
	 */
	public long getSecondChiefFiveYuanQuantity() {
		return SecondChiefFiveYuanQuantity;
	}

	/**
	 * @param secondChiefFiveYuanQuantity the secondChiefFiveYuanQuantity to set
	 */
	public void setSecondChiefFiveYuanQuantity(long secondChiefFiveYuanQuantity) {
		SecondChiefFiveYuanQuantity = secondChiefFiveYuanQuantity;
	}

	/**
	 * @return the secondChiefFiveYuanMoney
	 */
	public int getSecondChiefFiveYuanMoney() {
		return SecondChiefFiveYuanMoney;
	}

	/**
	 * @param secondChiefFiveYuanMoney the secondChiefFiveYuanMoney to set
	 */
	public void setSecondChiefFiveYuanMoney(int secondChiefFiveYuanMoney) {
		SecondChiefFiveYuanMoney = secondChiefFiveYuanMoney;
	}

	/**
	 * @return the secondChiefTenYuanQuantity
	 */
	public long getSecondChiefTenYuanQuantity() {
		return SecondChiefTenYuanQuantity;
	}

	/**
	 * @param secondChiefTenYuanQuantity the secondChiefTenYuanQuantity to set
	 */
	public void setSecondChiefTenYuanQuantity(long secondChiefTenYuanQuantity) {
		SecondChiefTenYuanQuantity = secondChiefTenYuanQuantity;
	}

	/**
	 * @return the secondChiefTenYuanMoney
	 */
	public int getSecondChiefTenYuanMoney() {
		return SecondChiefTenYuanMoney;
	}

	/**
	 * @param secondChiefTenYuanMoney the secondChiefTenYuanMoney to set
	 */
	public void setSecondChiefTenYuanMoney(int secondChiefTenYuanMoney) {
		SecondChiefTenYuanMoney = secondChiefTenYuanMoney;
	}

	/**
	 * @return the secondChiefTwentyYuanQuantity
	 */
	public long getSecondChiefTwentyYuanQuantity() {
		return SecondChiefTwentyYuanQuantity;
	}

	/**
	 * @param secondChiefTwentyYuanQuantity the secondChiefTwentyYuanQuantity to set
	 */
	public void setSecondChiefTwentyYuanQuantity(long secondChiefTwentyYuanQuantity) {
		SecondChiefTwentyYuanQuantity = secondChiefTwentyYuanQuantity;
	}

	/**
	 * @return the secondChiefTwentyYuanMoney
	 */
	public int getSecondChiefTwentyYuanMoney() {
		return SecondChiefTwentyYuanMoney;
	}

	/**
	 * @param secondChiefTwentyYuanMoney the secondChiefTwentyYuanMoney to set
	 */
	public void setSecondChiefTwentyYuanMoney(int secondChiefTwentyYuanMoney) {
		SecondChiefTwentyYuanMoney = secondChiefTwentyYuanMoney;
	}

	/**
	 * @return the secondChiefFiftyYuanQuantity
	 */
	public long getSecondChiefFiftyYuanQuantity() {
		return SecondChiefFiftyYuanQuantity;
	}

	/**
	 * @param secondChiefFiftyYuanQuantity the secondChiefFiftyYuanQuantity to set
	 */
	public void setSecondChiefFiftyYuanQuantity(long secondChiefFiftyYuanQuantity) {
		SecondChiefFiftyYuanQuantity = secondChiefFiftyYuanQuantity;
	}

	/**
	 * @return the secondChiefFiftyYuanMoney
	 */
	public int getSecondChiefFiftyYuanMoney() {
		return SecondChiefFiftyYuanMoney;
	}

	/**
	 * @param secondChiefFiftyYuanMoney the secondChiefFiftyYuanMoney to set
	 */
	public void setSecondChiefFiftyYuanMoney(int secondChiefFiftyYuanMoney) {
		SecondChiefFiftyYuanMoney = secondChiefFiftyYuanMoney;
	}

	/**
	 * @return the secondChiefHundredYuanQuantity
	 */
	public long getSecondChiefHundredYuanQuantity() {
		return SecondChiefHundredYuanQuantity;
	}

	/**
	 * @param secondChiefHundredYuanQuantity the secondChiefHundredYuanQuantity to set
	 */
	public void setSecondChiefHundredYuanQuantity(long secondChiefHundredYuanQuantity) {
		SecondChiefHundredYuanQuantity = secondChiefHundredYuanQuantity;
	}

	/**
	 * @return the secondChiefHundredYuanMoney
	 */
	public int getSecondChiefHundredYuanMoney() {
		return SecondChiefHundredYuanMoney;
	}

	/**
	 * @param secondChiefHundredYuanMoney the secondChiefHundredYuanMoney to set
	 */
	public void setSecondChiefHundredYuanMoney(int secondChiefHundredYuanMoney) {
		SecondChiefHundredYuanMoney = secondChiefHundredYuanMoney;
	}

	/**
	 * @return the firstCycOneYuanQuantity
	 */
	public long getFirstCycOneYuanQuantity() {
		return FirstCycOneYuanQuantity;
	}

	/**
	 * @param firstCycOneYuanQuantity the firstCycOneYuanQuantity to set
	 */
	public void setFirstCycOneYuanQuantity(long firstCycOneYuanQuantity) {
		FirstCycOneYuanQuantity = firstCycOneYuanQuantity;
	}

	/**
	 * @return the firstCycOneYuanMoney
	 */
	public int getFirstCycOneYuanMoney() {
		return FirstCycOneYuanMoney;
	}

	/**
	 * @param firstCycOneYuanMoney the firstCycOneYuanMoney to set
	 */
	public void setFirstCycOneYuanMoney(int firstCycOneYuanMoney) {
		FirstCycOneYuanMoney = firstCycOneYuanMoney;
	}

	/**
	 * @return the firstCycTwoYuanQuantity
	 */
	public long getFirstCycTwoYuanQuantity() {
		return FirstCycTwoYuanQuantity;
	}

	/**
	 * @param firstCycTwoYuanQuantity the firstCycTwoYuanQuantity to set
	 */
	public void setFirstCycTwoYuanQuantity(long firstCycTwoYuanQuantity) {
		FirstCycTwoYuanQuantity = firstCycTwoYuanQuantity;
	}

	/**
	 * @return the firstCycTwoYuanMoney
	 */
	public int getFirstCycTwoYuanMoney() {
		return FirstCycTwoYuanMoney;
	}

	/**
	 * @param firstCycTwoYuanMoney the firstCycTwoYuanMoney to set
	 */
	public void setFirstCycTwoYuanMoney(int firstCycTwoYuanMoney) {
		FirstCycTwoYuanMoney = firstCycTwoYuanMoney;
	}

	/**
	 * @return the firstCycFiveYuanQuantity
	 */
	public long getFirstCycFiveYuanQuantity() {
		return FirstCycFiveYuanQuantity;
	}

	/**
	 * @param firstCycFiveYuanQuantity the firstCycFiveYuanQuantity to set
	 */
	public void setFirstCycFiveYuanQuantity(long firstCycFiveYuanQuantity) {
		FirstCycFiveYuanQuantity = firstCycFiveYuanQuantity;
	}

	/**
	 * @return the firstCycFiveYuanMoney
	 */
	public int getFirstCycFiveYuanMoney() {
		return FirstCycFiveYuanMoney;
	}

	/**
	 * @param firstCycFiveYuanMoney the firstCycFiveYuanMoney to set
	 */
	public void setFirstCycFiveYuanMoney(int firstCycFiveYuanMoney) {
		FirstCycFiveYuanMoney = firstCycFiveYuanMoney;
	}

	/**
	 * @return the firstCycTenYuanQuantity
	 */
	public long getFirstCycTenYuanQuantity() {
		return FirstCycTenYuanQuantity;
	}

	/**
	 * @param firstCycTenYuanQuantity the firstCycTenYuanQuantity to set
	 */
	public void setFirstCycTenYuanQuantity(long firstCycTenYuanQuantity) {
		FirstCycTenYuanQuantity = firstCycTenYuanQuantity;
	}

	/**
	 * @return the firstCycTenYuanMoney
	 */
	public int getFirstCycTenYuanMoney() {
		return FirstCycTenYuanMoney;
	}

	/**
	 * @param firstCycTenYuanMoney the firstCycTenYuanMoney to set
	 */
	public void setFirstCycTenYuanMoney(int firstCycTenYuanMoney) {
		FirstCycTenYuanMoney = firstCycTenYuanMoney;
	}

	/**
	 * @return the firstCycTwentyYuanQuantity
	 */
	public long getFirstCycTwentyYuanQuantity() {
		return FirstCycTwentyYuanQuantity;
	}

	/**
	 * @param firstCycTwentyYuanQuantity the firstCycTwentyYuanQuantity to set
	 */
	public void setFirstCycTwentyYuanQuantity(long firstCycTwentyYuanQuantity) {
		FirstCycTwentyYuanQuantity = firstCycTwentyYuanQuantity;
	}

	/**
	 * @return the firstCycTwentyYuanMoney
	 */
	public int getFirstCycTwentyYuanMoney() {
		return FirstCycTwentyYuanMoney;
	}

	/**
	 * @param firstCycTwentyYuanMoney the firstCycTwentyYuanMoney to set
	 */
	public void setFirstCycTwentyYuanMoney(int firstCycTwentyYuanMoney) {
		FirstCycTwentyYuanMoney = firstCycTwentyYuanMoney;
	}

	/**
	 * @return the firstCycFiftyYuanQuantity
	 */
	public long getFirstCycFiftyYuanQuantity() {
		return FirstCycFiftyYuanQuantity;
	}

	/**
	 * @param firstCycFiftyYuanQuantity the firstCycFiftyYuanQuantity to set
	 */
	public void setFirstCycFiftyYuanQuantity(long firstCycFiftyYuanQuantity) {
		FirstCycFiftyYuanQuantity = firstCycFiftyYuanQuantity;
	}

	/**
	 * @return the firstCycFiftyYuanMoney
	 */
	public int getFirstCycFiftyYuanMoney() {
		return FirstCycFiftyYuanMoney;
	}

	/**
	 * @param firstCycFiftyYuanMoney the firstCycFiftyYuanMoney to set
	 */
	public void setFirstCycFiftyYuanMoney(int firstCycFiftyYuanMoney) {
		FirstCycFiftyYuanMoney = firstCycFiftyYuanMoney;
	}

	/**
	 * @return the firstCycHundredYuanQuantity
	 */
	public long getFirstCycHundredYuanQuantity() {
		return FirstCycHundredYuanQuantity;
	}

	/**
	 * @param firstCycHundredYuanQuantity the firstCycHundredYuanQuantity to set
	 */
	public void setFirstCycHundredYuanQuantity(long firstCycHundredYuanQuantity) {
		FirstCycHundredYuanQuantity = firstCycHundredYuanQuantity;
	}

	/**
	 * @return the firstCycHundredYuanMoney
	 */
	public int getFirstCycHundredYuanMoney() {
		return FirstCycHundredYuanMoney;
	}

	/**
	 * @param firstCycHundredYuanMoney the firstCycHundredYuanMoney to set
	 */
	public void setFirstCycHundredYuanMoney(int firstCycHundredYuanMoney) {
		FirstCycHundredYuanMoney = firstCycHundredYuanMoney;
	}

	/**
	 * @return the secondCycOneYuanQuantity
	 */
	public long getSecondCycOneYuanQuantity() {
		return SecondCycOneYuanQuantity;
	}

	/**
	 * @param secondCycOneYuanQuantity the secondCycOneYuanQuantity to set
	 */
	public void setSecondCycOneYuanQuantity(long secondCycOneYuanQuantity) {
		SecondCycOneYuanQuantity = secondCycOneYuanQuantity;
	}

	/**
	 * @return the secondCycOneYuanMoney
	 */
	public int getSecondCycOneYuanMoney() {
		return SecondCycOneYuanMoney;
	}

	/**
	 * @param secondCycOneYuanMoney the secondCycOneYuanMoney to set
	 */
	public void setSecondCycOneYuanMoney(int secondCycOneYuanMoney) {
		SecondCycOneYuanMoney = secondCycOneYuanMoney;
	}

	/**
	 * @return the secondCycTwoYuanQuantity
	 */
	public long getSecondCycTwoYuanQuantity() {
		return SecondCycTwoYuanQuantity;
	}

	/**
	 * @param secondCycTwoYuanQuantity the secondCycTwoYuanQuantity to set
	 */
	public void setSecondCycTwoYuanQuantity(long secondCycTwoYuanQuantity) {
		SecondCycTwoYuanQuantity = secondCycTwoYuanQuantity;
	}

	/**
	 * @return the secondCycTwoYuanMoney
	 */
	public int getSecondCycTwoYuanMoney() {
		return SecondCycTwoYuanMoney;
	}

	/**
	 * @param secondCycTwoYuanMoney the secondCycTwoYuanMoney to set
	 */
	public void setSecondCycTwoYuanMoney(int secondCycTwoYuanMoney) {
		SecondCycTwoYuanMoney = secondCycTwoYuanMoney;
	}

	/**
	 * @return the secondCycFiveYuanQuantity
	 */
	public long getSecondCycFiveYuanQuantity() {
		return SecondCycFiveYuanQuantity;
	}

	/**
	 * @param secondCycFiveYuanQuantity the secondCycFiveYuanQuantity to set
	 */
	public void setSecondCycFiveYuanQuantity(long secondCycFiveYuanQuantity) {
		SecondCycFiveYuanQuantity = secondCycFiveYuanQuantity;
	}

	/**
	 * @return the secondCycFiveYuanMoney
	 */
	public int getSecondCycFiveYuanMoney() {
		return SecondCycFiveYuanMoney;
	}

	/**
	 * @param secondCycFiveYuanMoney the secondCycFiveYuanMoney to set
	 */
	public void setSecondCycFiveYuanMoney(int secondCycFiveYuanMoney) {
		SecondCycFiveYuanMoney = secondCycFiveYuanMoney;
	}

	/**
	 * @return the secondCycTenYuanQuantity
	 */
	public long getSecondCycTenYuanQuantity() {
		return SecondCycTenYuanQuantity;
	}

	/**
	 * @param secondCycTenYuanQuantity the secondCycTenYuanQuantity to set
	 */
	public void setSecondCycTenYuanQuantity(long secondCycTenYuanQuantity) {
		SecondCycTenYuanQuantity = secondCycTenYuanQuantity;
	}

	/**
	 * @return the secondCycTenYuanMoney
	 */
	public int getSecondCycTenYuanMoney() {
		return SecondCycTenYuanMoney;
	}

	/**
	 * @param secondCycTenYuanMoney the secondCycTenYuanMoney to set
	 */
	public void setSecondCycTenYuanMoney(int secondCycTenYuanMoney) {
		SecondCycTenYuanMoney = secondCycTenYuanMoney;
	}

	/**
	 * @return the secondCycTwentyYuanQuantity
	 */
	public long getSecondCycTwentyYuanQuantity() {
		return SecondCycTwentyYuanQuantity;
	}

	/**
	 * @param secondCycTwentyYuanQuantity the secondCycTwentyYuanQuantity to set
	 */
	public void setSecondCycTwentyYuanQuantity(long secondCycTwentyYuanQuantity) {
		SecondCycTwentyYuanQuantity = secondCycTwentyYuanQuantity;
	}

	/**
	 * @return the secondCycTwentyYuanMoney
	 */
	public int getSecondCycTwentyYuanMoney() {
		return SecondCycTwentyYuanMoney;
	}

	/**
	 * @param secondCycTwentyYuanMoney the secondCycTwentyYuanMoney to set
	 */
	public void setSecondCycTwentyYuanMoney(int secondCycTwentyYuanMoney) {
		SecondCycTwentyYuanMoney = secondCycTwentyYuanMoney;
	}

	/**
	 * @return the secondCycFiftyYuanQuantity
	 */
	public long getSecondCycFiftyYuanQuantity() {
		return SecondCycFiftyYuanQuantity;
	}

	/**
	 * @param secondCycFiftyYuanQuantity the secondCycFiftyYuanQuantity to set
	 */
	public void setSecondCycFiftyYuanQuantity(long secondCycFiftyYuanQuantity) {
		SecondCycFiftyYuanQuantity = secondCycFiftyYuanQuantity;
	}

	/**
	 * @return the secondCycFiftyYuanMoney
	 */
	public int getSecondCycFiftyYuanMoney() {
		return SecondCycFiftyYuanMoney;
	}

	/**
	 * @param secondCycFiftyYuanMoney the secondCycFiftyYuanMoney to set
	 */
	public void setSecondCycFiftyYuanMoney(int secondCycFiftyYuanMoney) {
		SecondCycFiftyYuanMoney = secondCycFiftyYuanMoney;
	}

	/**
	 * @return the secondCycHundredYuanQuantity
	 */
	public long getSecondCycHundredYuanQuantity() {
		return SecondCycHundredYuanQuantity;
	}

	/**
	 * @param secondCycHundredYuanQuantity the secondCycHundredYuanQuantity to set
	 */
	public void setSecondCycHundredYuanQuantity(long secondCycHundredYuanQuantity) {
		SecondCycHundredYuanQuantity = secondCycHundredYuanQuantity;
	}

	/**
	 * @return the secondCycHundredYuanMoney
	 */
	public int getSecondCycHundredYuanMoney() {
		return SecondCycHundredYuanMoney;
	}

	/**
	 * @param secondCycHundredYuanMoney the secondCycHundredYuanMoney to set
	 */
	public void setSecondCycHundredYuanMoney(int secondCycHundredYuanMoney) {
		SecondCycHundredYuanMoney = secondCycHundredYuanMoney;
	}

	/**
	 * @return the recycleOneYuanQuantity
	 */
	public long getRecycleOneYuanQuantity() {
		return RecycleOneYuanQuantity;
	}

	/**
	 * @param recycleOneYuanQuantity the recycleOneYuanQuantity to set
	 */
	public void setRecycleOneYuanQuantity(long recycleOneYuanQuantity) {
		RecycleOneYuanQuantity = recycleOneYuanQuantity;
	}

	/**
	 * @return the recycleOneYuanMoney
	 */
	public int getRecycleOneYuanMoney() {
		return RecycleOneYuanMoney;
	}

	/**
	 * @param recycleOneYuanMoney the recycleOneYuanMoney to set
	 */
	public void setRecycleOneYuanMoney(int recycleOneYuanMoney) {
		RecycleOneYuanMoney = recycleOneYuanMoney;
	}

	/**
	 * @return the recycleTwoYuanQuantity
	 */
	public long getRecycleTwoYuanQuantity() {
		return RecycleTwoYuanQuantity;
	}

	/**
	 * @param recycleTwoYuanQuantity the recycleTwoYuanQuantity to set
	 */
	public void setRecycleTwoYuanQuantity(long recycleTwoYuanQuantity) {
		RecycleTwoYuanQuantity = recycleTwoYuanQuantity;
	}

	/**
	 * @return the recycleTwoYuanMoney
	 */
	public int getRecycleTwoYuanMoney() {
		return RecycleTwoYuanMoney;
	}

	/**
	 * @param recycleTwoYuanMoney the recycleTwoYuanMoney to set
	 */
	public void setRecycleTwoYuanMoney(int recycleTwoYuanMoney) {
		RecycleTwoYuanMoney = recycleTwoYuanMoney;
	}

	/**
	 * @return the recycleFiveYuanQuantity
	 */
	public long getRecycleFiveYuanQuantity() {
		return RecycleFiveYuanQuantity;
	}

	/**
	 * @param recycleFiveYuanQuantity the recycleFiveYuanQuantity to set
	 */
	public void setRecycleFiveYuanQuantity(long recycleFiveYuanQuantity) {
		RecycleFiveYuanQuantity = recycleFiveYuanQuantity;
	}

	/**
	 * @return the recycleFiveYuanMoney
	 */
	public int getRecycleFiveYuanMoney() {
		return RecycleFiveYuanMoney;
	}

	/**
	 * @param recycleFiveYuanMoney the recycleFiveYuanMoney to set
	 */
	public void setRecycleFiveYuanMoney(int recycleFiveYuanMoney) {
		RecycleFiveYuanMoney = recycleFiveYuanMoney;
	}

	/**
	 * @return the recycleTenYuanQuantity
	 */
	public long getRecycleTenYuanQuantity() {
		return RecycleTenYuanQuantity;
	}

	/**
	 * @param recycleTenYuanQuantity the recycleTenYuanQuantity to set
	 */
	public void setRecycleTenYuanQuantity(long recycleTenYuanQuantity) {
		RecycleTenYuanQuantity = recycleTenYuanQuantity;
	}

	/**
	 * @return the recycleTenYuanMoney
	 */
	public int getRecycleTenYuanMoney() {
		return RecycleTenYuanMoney;
	}

	/**
	 * @param recycleTenYuanMoney the recycleTenYuanMoney to set
	 */
	public void setRecycleTenYuanMoney(int recycleTenYuanMoney) {
		RecycleTenYuanMoney = recycleTenYuanMoney;
	}

	/**
	 * @return the recycleTwentyYuanQuantity
	 */
	public long getRecycleTwentyYuanQuantity() {
		return RecycleTwentyYuanQuantity;
	}

	/**
	 * @param recycleTwentyYuanQuantity the recycleTwentyYuanQuantity to set
	 */
	public void setRecycleTwentyYuanQuantity(long recycleTwentyYuanQuantity) {
		RecycleTwentyYuanQuantity = recycleTwentyYuanQuantity;
	}

	/**
	 * @return the recycleTwentyYuanMoney
	 */
	public int getRecycleTwentyYuanMoney() {
		return RecycleTwentyYuanMoney;
	}

	/**
	 * @param recycleTwentyYuanMoney the recycleTwentyYuanMoney to set
	 */
	public void setRecycleTwentyYuanMoney(int recycleTwentyYuanMoney) {
		RecycleTwentyYuanMoney = recycleTwentyYuanMoney;
	}

	/**
	 * @return the recycleFiftyYuanQuantity
	 */
	public long getRecycleFiftyYuanQuantity() {
		return RecycleFiftyYuanQuantity;
	}

	/**
	 * @param recycleFiftyYuanQuantity the recycleFiftyYuanQuantity to set
	 */
	public void setRecycleFiftyYuanQuantity(long recycleFiftyYuanQuantity) {
		RecycleFiftyYuanQuantity = recycleFiftyYuanQuantity;
	}

	/**
	 * @return the recycleFiftyYuanMoney
	 */
	public int getRecycleFiftyYuanMoney() {
		return RecycleFiftyYuanMoney;
	}

	/**
	 * @param recycleFiftyYuanMoney the recycleFiftyYuanMoney to set
	 */
	public void setRecycleFiftyYuanMoney(int recycleFiftyYuanMoney) {
		RecycleFiftyYuanMoney = recycleFiftyYuanMoney;
	}

	/**
	 * @return the recycleHundredYuanQuantity
	 */
	public long getRecycleHundredYuanQuantity() {
		return RecycleHundredYuanQuantity;
	}

	/**
	 * @param recycleHundredYuanQuantity the recycleHundredYuanQuantity to set
	 */
	public void setRecycleHundredYuanQuantity(long recycleHundredYuanQuantity) {
		RecycleHundredYuanQuantity = recycleHundredYuanQuantity;
	}

	/**
	 * @return the recycleHundredYuanMoney
	 */
	public int getRecycleHundredYuanMoney() {
		return RecycleHundredYuanMoney;
	}

	/**
	 * @param recycleHundredYuanMoney the recycleHundredYuanMoney to set
	 */
	public void setRecycleHundredYuanMoney(int recycleHundredYuanMoney) {
		RecycleHundredYuanMoney = recycleHundredYuanMoney;
	}

	/**
	 * @return the firstResveOneYuanQuantity
	 */
	public long getFirstResveOneYuanQuantity() {
		return FirstResveOneYuanQuantity;
	}

	/**
	 * @param firstResveOneYuanQuantity the firstResveOneYuanQuantity to set
	 */
	public void setFirstResveOneYuanQuantity(long firstResveOneYuanQuantity) {
		FirstResveOneYuanQuantity = firstResveOneYuanQuantity;
	}

	/**
	 * @return the firstResveOneYuanMoney
	 */
	public int getFirstResveOneYuanMoney() {
		return FirstResveOneYuanMoney;
	}

	/**
	 * @param firstResveOneYuanMoney the firstResveOneYuanMoney to set
	 */
	public void setFirstResveOneYuanMoney(int firstResveOneYuanMoney) {
		FirstResveOneYuanMoney = firstResveOneYuanMoney;
	}

	/**
	 * @return the firstResveTwoYuanQuantity
	 */
	public long getFirstResveTwoYuanQuantity() {
		return FirstResveTwoYuanQuantity;
	}

	/**
	 * @param firstResveTwoYuanQuantity the firstResveTwoYuanQuantity to set
	 */
	public void setFirstResveTwoYuanQuantity(long firstResveTwoYuanQuantity) {
		FirstResveTwoYuanQuantity = firstResveTwoYuanQuantity;
	}

	/**
	 * @return the firstResveTwoYuanMoney
	 */
	public int getFirstResveTwoYuanMoney() {
		return FirstResveTwoYuanMoney;
	}

	/**
	 * @param firstResveTwoYuanMoney the firstResveTwoYuanMoney to set
	 */
	public void setFirstResveTwoYuanMoney(int firstResveTwoYuanMoney) {
		FirstResveTwoYuanMoney = firstResveTwoYuanMoney;
	}

	/**
	 * @return the firstResveFiveYuanQuantity
	 */
	public long getFirstResveFiveYuanQuantity() {
		return FirstResveFiveYuanQuantity;
	}

	/**
	 * @param firstResveFiveYuanQuantity the firstResveFiveYuanQuantity to set
	 */
	public void setFirstResveFiveYuanQuantity(long firstResveFiveYuanQuantity) {
		FirstResveFiveYuanQuantity = firstResveFiveYuanQuantity;
	}

	/**
	 * @return the firstResveFiveYuanMoney
	 */
	public int getFirstResveFiveYuanMoney() {
		return FirstResveFiveYuanMoney;
	}

	/**
	 * @param firstResveFiveYuanMoney the firstResveFiveYuanMoney to set
	 */
	public void setFirstResveFiveYuanMoney(int firstResveFiveYuanMoney) {
		FirstResveFiveYuanMoney = firstResveFiveYuanMoney;
	}

	/**
	 * @return the firstResveTenYuanQuantity
	 */
	public long getFirstResveTenYuanQuantity() {
		return FirstResveTenYuanQuantity;
	}

	/**
	 * @param firstResveTenYuanQuantity the firstResveTenYuanQuantity to set
	 */
	public void setFirstResveTenYuanQuantity(long firstResveTenYuanQuantity) {
		FirstResveTenYuanQuantity = firstResveTenYuanQuantity;
	}

	/**
	 * @return the firstResveTenYuanMoney
	 */
	public int getFirstResveTenYuanMoney() {
		return FirstResveTenYuanMoney;
	}

	/**
	 * @param firstResveTenYuanMoney the firstResveTenYuanMoney to set
	 */
	public void setFirstResveTenYuanMoney(int firstResveTenYuanMoney) {
		FirstResveTenYuanMoney = firstResveTenYuanMoney;
	}

	/**
	 * @return the firstResveTwentyYuanQuantity
	 */
	public long getFirstResveTwentyYuanQuantity() {
		return FirstResveTwentyYuanQuantity;
	}

	/**
	 * @param firstResveTwentyYuanQuantity the firstResveTwentyYuanQuantity to set
	 */
	public void setFirstResveTwentyYuanQuantity(long firstResveTwentyYuanQuantity) {
		FirstResveTwentyYuanQuantity = firstResveTwentyYuanQuantity;
	}

	/**
	 * @return the firstResveTwentyYuanMoney
	 */
	public int getFirstResveTwentyYuanMoney() {
		return FirstResveTwentyYuanMoney;
	}

	/**
	 * @param firstResveTwentyYuanMoney the firstResveTwentyYuanMoney to set
	 */
	public void setFirstResveTwentyYuanMoney(int firstResveTwentyYuanMoney) {
		FirstResveTwentyYuanMoney = firstResveTwentyYuanMoney;
	}

	/**
	 * @return the firstResveFiftyYuanQuantity
	 */
	public long getFirstResveFiftyYuanQuantity() {
		return FirstResveFiftyYuanQuantity;
	}

	/**
	 * @param firstResveFiftyYuanQuantity the firstResveFiftyYuanQuantity to set
	 */
	public void setFirstResveFiftyYuanQuantity(long firstResveFiftyYuanQuantity) {
		FirstResveFiftyYuanQuantity = firstResveFiftyYuanQuantity;
	}

	/**
	 * @return the firstResveFiftyYuanMoney
	 */
	public int getFirstResveFiftyYuanMoney() {
		return FirstResveFiftyYuanMoney;
	}

	/**
	 * @param firstResveFiftyYuanMoney the firstResveFiftyYuanMoney to set
	 */
	public void setFirstResveFiftyYuanMoney(int firstResveFiftyYuanMoney) {
		FirstResveFiftyYuanMoney = firstResveFiftyYuanMoney;
	}

	/**
	 * @return the firstResveHundredYuanQuantity
	 */
	public long getFirstResveHundredYuanQuantity() {
		return FirstResveHundredYuanQuantity;
	}

	/**
	 * @param firstResveHundredYuanQuantity the firstResveHundredYuanQuantity to set
	 */
	public void setFirstResveHundredYuanQuantity(long firstResveHundredYuanQuantity) {
		FirstResveHundredYuanQuantity = firstResveHundredYuanQuantity;
	}

	/**
	 * @return the firstResveHundredYuanMoney
	 */
	public int getFirstResveHundredYuanMoney() {
		return FirstResveHundredYuanMoney;
	}

	/**
	 * @param firstResveHundredYuanMoney the firstResveHundredYuanMoney to set
	 */
	public void setFirstResveHundredYuanMoney(int firstResveHundredYuanMoney) {
		FirstResveHundredYuanMoney = firstResveHundredYuanMoney;
	}

	/**
	 * @return the secondResveOneYuanQuantity
	 */
	public long getSecondResveOneYuanQuantity() {
		return SecondResveOneYuanQuantity;
	}

	/**
	 * @param secondResveOneYuanQuantity the secondResveOneYuanQuantity to set
	 */
	public void setSecondResveOneYuanQuantity(long secondResveOneYuanQuantity) {
		SecondResveOneYuanQuantity = secondResveOneYuanQuantity;
	}

	/**
	 * @return the secondResveOneYuanMoney
	 */
	public int getSecondResveOneYuanMoney() {
		return SecondResveOneYuanMoney;
	}

	/**
	 * @param secondResveOneYuanMoney the secondResveOneYuanMoney to set
	 */
	public void setSecondResveOneYuanMoney(int secondResveOneYuanMoney) {
		SecondResveOneYuanMoney = secondResveOneYuanMoney;
	}

	/**
	 * @return the secondResveTwoYuanQuantity
	 */
	public long getSecondResveTwoYuanQuantity() {
		return SecondResveTwoYuanQuantity;
	}

	/**
	 * @param secondResveTwoYuanQuantity the secondResveTwoYuanQuantity to set
	 */
	public void setSecondResveTwoYuanQuantity(long secondResveTwoYuanQuantity) {
		SecondResveTwoYuanQuantity = secondResveTwoYuanQuantity;
	}

	/**
	 * @return the secondResveTwoYuanMoney
	 */
	public int getSecondResveTwoYuanMoney() {
		return SecondResveTwoYuanMoney;
	}

	/**
	 * @param secondResveTwoYuanMoney the secondResveTwoYuanMoney to set
	 */
	public void setSecondResveTwoYuanMoney(int secondResveTwoYuanMoney) {
		SecondResveTwoYuanMoney = secondResveTwoYuanMoney;
	}

	/**
	 * @return the secondResveFiveYuanQuantity
	 */
	public long getSecondResveFiveYuanQuantity() {
		return SecondResveFiveYuanQuantity;
	}

	/**
	 * @param secondResveFiveYuanQuantity the secondResveFiveYuanQuantity to set
	 */
	public void setSecondResveFiveYuanQuantity(long secondResveFiveYuanQuantity) {
		SecondResveFiveYuanQuantity = secondResveFiveYuanQuantity;
	}

	/**
	 * @return the secondResveFiveYuanMoney
	 */
	public int getSecondResveFiveYuanMoney() {
		return SecondResveFiveYuanMoney;
	}

	/**
	 * @param secondResveFiveYuanMoney the secondResveFiveYuanMoney to set
	 */
	public void setSecondResveFiveYuanMoney(int secondResveFiveYuanMoney) {
		SecondResveFiveYuanMoney = secondResveFiveYuanMoney;
	}

	/**
	 * @return the secondResveTenYuanQuantity
	 */
	public long getSecondResveTenYuanQuantity() {
		return SecondResveTenYuanQuantity;
	}

	/**
	 * @param secondResveTenYuanQuantity the secondResveTenYuanQuantity to set
	 */
	public void setSecondResveTenYuanQuantity(long secondResveTenYuanQuantity) {
		SecondResveTenYuanQuantity = secondResveTenYuanQuantity;
	}

	/**
	 * @return the secondResveTenYuanMoney
	 */
	public int getSecondResveTenYuanMoney() {
		return SecondResveTenYuanMoney;
	}

	/**
	 * @param secondResveTenYuanMoney the secondResveTenYuanMoney to set
	 */
	public void setSecondResveTenYuanMoney(int secondResveTenYuanMoney) {
		SecondResveTenYuanMoney = secondResveTenYuanMoney;
	}

	/**
	 * @return the secondResveTwentyYuanQuantity
	 */
	public long getSecondResveTwentyYuanQuantity() {
		return SecondResveTwentyYuanQuantity;
	}

	/**
	 * @param secondResveTwentyYuanQuantity the secondResveTwentyYuanQuantity to set
	 */
	public void setSecondResveTwentyYuanQuantity(long secondResveTwentyYuanQuantity) {
		SecondResveTwentyYuanQuantity = secondResveTwentyYuanQuantity;
	}

	/**
	 * @return the secondResveTwentyYuanMoney
	 */
	public int getSecondResveTwentyYuanMoney() {
		return SecondResveTwentyYuanMoney;
	}

	/**
	 * @param secondResveTwentyYuanMoney the secondResveTwentyYuanMoney to set
	 */
	public void setSecondResveTwentyYuanMoney(int secondResveTwentyYuanMoney) {
		SecondResveTwentyYuanMoney = secondResveTwentyYuanMoney;
	}

	/**
	 * @return the secondResveFiftyYuanQuantity
	 */
	public long getSecondResveFiftyYuanQuantity() {
		return SecondResveFiftyYuanQuantity;
	}

	/**
	 * @param secondResveFiftyYuanQuantity the secondResveFiftyYuanQuantity to set
	 */
	public void setSecondResveFiftyYuanQuantity(long secondResveFiftyYuanQuantity) {
		SecondResveFiftyYuanQuantity = secondResveFiftyYuanQuantity;
	}

	/**
	 * @return the secondResveFiftyYuanMoney
	 */
	public int getSecondResveFiftyYuanMoney() {
		return SecondResveFiftyYuanMoney;
	}

	/**
	 * @param secondResveFiftyYuanMoney the secondResveFiftyYuanMoney to set
	 */
	public void setSecondResveFiftyYuanMoney(int secondResveFiftyYuanMoney) {
		SecondResveFiftyYuanMoney = secondResveFiftyYuanMoney;
	}

	/**
	 * @return the secondResveHundredYuanQuantity
	 */
	public long getSecondResveHundredYuanQuantity() {
		return SecondResveHundredYuanQuantity;
	}

	/**
	 * @param secondResveHundredYuanQuantity the secondResveHundredYuanQuantity to set
	 */
	public void setSecondResveHundredYuanQuantity(long secondResveHundredYuanQuantity) {
		SecondResveHundredYuanQuantity = secondResveHundredYuanQuantity;
	}

	/**
	 * @return the secondResveHundredYuanMoney
	 */
	public int getSecondResveHundredYuanMoney() {
		return SecondResveHundredYuanMoney;
	}

	/**
	 * @param secondResveHundredYuanMoney the secondResveHundredYuanMoney to set
	 */
	public void setSecondResveHundredYuanMoney(int secondResveHundredYuanMoney) {
		SecondResveHundredYuanMoney = secondResveHundredYuanMoney;
	}

	/**
	 * @return the coinOperatePos
	 */
	public short getCoinOperatePos() {
		return CoinOperatePos;
	}

	/**
	 * @param coinOperatePos the coinOperatePos to set
	 */
	public void setCoinOperatePos(short coinOperatePos) {
		CoinOperatePos = coinOperatePos;
	}

	/**
	 * @return the coinBoxID
	 */
	public long getCoinBoxID() {
		return CoinBoxID;
	}

	/**
	 * @param coinBoxID the coinBoxID to set
	 */
	public void setCoinBoxID(long coinBoxID) {
		CoinBoxID = coinBoxID;
	}

	/**
	 * @return the firstChiefFiveJiaoQuantity
	 */
	public long getFirstChiefFiveJiaoQuantity() {
		return FirstChiefFiveJiaoQuantity;
	}

	/**
	 * @param firstChiefFiveJiaoQuantity the firstChiefFiveJiaoQuantity to set
	 */
	public void setFirstChiefFiveJiaoQuantity(long firstChiefFiveJiaoQuantity) {
		FirstChiefFiveJiaoQuantity = firstChiefFiveJiaoQuantity;
	}

	/**
	 * @return the firstChiefFiveJiaoMoney
	 */
	public int getFirstChiefFiveJiaoMoney() {
		return FirstChiefFiveJiaoMoney;
	}

	/**
	 * @param firstChiefFiveJiaoMoney the firstChiefFiveJiaoMoney to set
	 */
	public void setFirstChiefFiveJiaoMoney(int firstChiefFiveJiaoMoney) {
		FirstChiefFiveJiaoMoney = firstChiefFiveJiaoMoney;
	}

	/**
	 * @return the firstChiefTenJiaoQuantity
	 */
	public long getFirstChiefTenJiaoQuantity() {
		return FirstChiefTenJiaoQuantity;
	}

	/**
	 * @param firstChiefTenJiaoQuantity the firstChiefTenJiaoQuantity to set
	 */
	public void setFirstChiefTenJiaoQuantity(long firstChiefTenJiaoQuantity) {
		FirstChiefTenJiaoQuantity = firstChiefTenJiaoQuantity;
	}

	/**
	 * @return the firstChiefTenJiaoMoney
	 */
	public int getFirstChiefTenJiaoMoney() {
		return FirstChiefTenJiaoMoney;
	}

	/**
	 * @param firstChiefTenJiaoMoney the firstChiefTenJiaoMoney to set
	 */
	public void setFirstChiefTenJiaoMoney(int firstChiefTenJiaoMoney) {
		FirstChiefTenJiaoMoney = firstChiefTenJiaoMoney;
	}

	/**
	 * @return the secondChiefFiveJiaoQuantity
	 */
	public long getSecondChiefFiveJiaoQuantity() {
		return SecondChiefFiveJiaoQuantity;
	}

	/**
	 * @param secondChiefFiveJiaoQuantity the secondChiefFiveJiaoQuantity to set
	 */
	public void setSecondChiefFiveJiaoQuantity(long secondChiefFiveJiaoQuantity) {
		SecondChiefFiveJiaoQuantity = secondChiefFiveJiaoQuantity;
	}

	/**
	 * @return the secondChiefFiveJiaoMoney
	 */
	public int getSecondChiefFiveJiaoMoney() {
		return SecondChiefFiveJiaoMoney;
	}

	/**
	 * @param secondChiefFiveJiaoMoney the secondChiefFiveJiaoMoney to set
	 */
	public void setSecondChiefFiveJiaoMoney(int secondChiefFiveJiaoMoney) {
		SecondChiefFiveJiaoMoney = secondChiefFiveJiaoMoney;
	}

	/**
	 * @return the secondChiefTenJiaoQuantity
	 */
	public long getSecondChiefTenJiaoQuantity() {
		return SecondChiefTenJiaoQuantity;
	}

	/**
	 * @param secondChiefTenJiaoQuantity the secondChiefTenJiaoQuantity to set
	 */
	public void setSecondChiefTenJiaoQuantity(long secondChiefTenJiaoQuantity) {
		SecondChiefTenJiaoQuantity = secondChiefTenJiaoQuantity;
	}

	/**
	 * @return the secondChiefTenJiaoMoney
	 */
	public int getSecondChiefTenJiaoMoney() {
		return SecondChiefTenJiaoMoney;
	}

	/**
	 * @param secondChiefTenJiaoMoney the secondChiefTenJiaoMoney to set
	 */
	public void setSecondChiefTenJiaoMoney(int secondChiefTenJiaoMoney) {
		SecondChiefTenJiaoMoney = secondChiefTenJiaoMoney;
	}

	/**
	 * @return the firstCycFiveJiaoQuantity
	 */
	public long getFirstCycFiveJiaoQuantity() {
		return FirstCycFiveJiaoQuantity;
	}

	/**
	 * @param firstCycFiveJiaoQuantity the firstCycFiveJiaoQuantity to set
	 */
	public void setFirstCycFiveJiaoQuantity(long firstCycFiveJiaoQuantity) {
		FirstCycFiveJiaoQuantity = firstCycFiveJiaoQuantity;
	}

	/**
	 * @return the firstCycFiveJiaoMoney
	 */
	public int getFirstCycFiveJiaoMoney() {
		return FirstCycFiveJiaoMoney;
	}

	/**
	 * @param firstCycFiveJiaoMoney the firstCycFiveJiaoMoney to set
	 */
	public void setFirstCycFiveJiaoMoney(int firstCycFiveJiaoMoney) {
		FirstCycFiveJiaoMoney = firstCycFiveJiaoMoney;
	}

	/**
	 * @return the firstCycTenJiaoQuantity
	 */
	public long getFirstCycTenJiaoQuantity() {
		return FirstCycTenJiaoQuantity;
	}

	/**
	 * @param firstCycTenJiaoQuantity the firstCycTenJiaoQuantity to set
	 */
	public void setFirstCycTenJiaoQuantity(long firstCycTenJiaoQuantity) {
		FirstCycTenJiaoQuantity = firstCycTenJiaoQuantity;
	}

	/**
	 * @return the firstCycTenJiaoMoney
	 */
	public int getFirstCycTenJiaoMoney() {
		return FirstCycTenJiaoMoney;
	}

	/**
	 * @param firstCycTenJiaoMoney the firstCycTenJiaoMoney to set
	 */
	public void setFirstCycTenJiaoMoney(int firstCycTenJiaoMoney) {
		FirstCycTenJiaoMoney = firstCycTenJiaoMoney;
	}

	/**
	 * @return the secondCycFiveJiaoQuantity
	 */
	public long getSecondCycFiveJiaoQuantity() {
		return SecondCycFiveJiaoQuantity;
	}

	/**
	 * @param secondCycFiveJiaoQuantity the secondCycFiveJiaoQuantity to set
	 */
	public void setSecondCycFiveJiaoQuantity(long secondCycFiveJiaoQuantity) {
		SecondCycFiveJiaoQuantity = secondCycFiveJiaoQuantity;
	}

	/**
	 * @return the secondCycFiveJiaoMoney
	 */
	public int getSecondCycFiveJiaoMoney() {
		return SecondCycFiveJiaoMoney;
	}

	/**
	 * @param secondCycFiveJiaoMoney the secondCycFiveJiaoMoney to set
	 */
	public void setSecondCycFiveJiaoMoney(int secondCycFiveJiaoMoney) {
		SecondCycFiveJiaoMoney = secondCycFiveJiaoMoney;
	}

	/**
	 * @return the secondCycTenJiaoQuantity
	 */
	public long getSecondCycTenJiaoQuantity() {
		return SecondCycTenJiaoQuantity;
	}

	/**
	 * @param secondCycTenJiaoQuantity the secondCycTenJiaoQuantity to set
	 */
	public void setSecondCycTenJiaoQuantity(long secondCycTenJiaoQuantity) {
		SecondCycTenJiaoQuantity = secondCycTenJiaoQuantity;
	}

	/**
	 * @return the secondCycTenJiaoMoney
	 */
	public int getSecondCycTenJiaoMoney() {
		return SecondCycTenJiaoMoney;
	}

	/**
	 * @param secondCycTenJiaoMoney the secondCycTenJiaoMoney to set
	 */
	public void setSecondCycTenJiaoMoney(int secondCycTenJiaoMoney) {
		SecondCycTenJiaoMoney = secondCycTenJiaoMoney;
	}

	/**
	 * @return the recycleFiveJiaoQuantity
	 */
	public long getRecycleFiveJiaoQuantity() {
		return RecycleFiveJiaoQuantity;
	}

	/**
	 * @param recycleFiveJiaoQuantity the recycleFiveJiaoQuantity to set
	 */
	public void setRecycleFiveJiaoQuantity(long recycleFiveJiaoQuantity) {
		RecycleFiveJiaoQuantity = recycleFiveJiaoQuantity;
	}

	/**
	 * @return the recycleFiveJiaoMoney
	 */
	public int getRecycleFiveJiaoMoney() {
		return RecycleFiveJiaoMoney;
	}

	/**
	 * @param recycleFiveJiaoMoney the recycleFiveJiaoMoney to set
	 */
	public void setRecycleFiveJiaoMoney(int recycleFiveJiaoMoney) {
		RecycleFiveJiaoMoney = recycleFiveJiaoMoney;
	}

	/**
	 * @return the recycleTenJiaoQuantity
	 */
	public long getRecycleTenJiaoQuantity() {
		return RecycleTenJiaoQuantity;
	}

	/**
	 * @param recycleTenJiaoQuantity the recycleTenJiaoQuantity to set
	 */
	public void setRecycleTenJiaoQuantity(long recycleTenJiaoQuantity) {
		RecycleTenJiaoQuantity = recycleTenJiaoQuantity;
	}

	/**
	 * @return the recycleTenJiaoMoney
	 */
	public int getRecycleTenJiaoMoney() {
		return RecycleTenJiaoMoney;
	}

	/**
	 * @param recycleTenJiaoMoney the recycleTenJiaoMoney to set
	 */
	public void setRecycleTenJiaoMoney(int recycleTenJiaoMoney) {
		RecycleTenJiaoMoney = recycleTenJiaoMoney;
	}

	/**
	 * @return the firstResveFiveJiaoQuantity
	 */
	public long getFirstResveFiveJiaoQuantity() {
		return FirstResveFiveJiaoQuantity;
	}

	/**
	 * @param firstResveFiveJiaoQuantity the firstResveFiveJiaoQuantity to set
	 */
	public void setFirstResveFiveJiaoQuantity(long firstResveFiveJiaoQuantity) {
		FirstResveFiveJiaoQuantity = firstResveFiveJiaoQuantity;
	}

	/**
	 * @return the firstResveFiveJiaoMoney
	 */
	public int getFirstResveFiveJiaoMoney() {
		return FirstResveFiveJiaoMoney;
	}

	/**
	 * @param firstResveFiveJiaoMoney the firstResveFiveJiaoMoney to set
	 */
	public void setFirstResveFiveJiaoMoney(int firstResveFiveJiaoMoney) {
		FirstResveFiveJiaoMoney = firstResveFiveJiaoMoney;
	}

	/**
	 * @return the firstResveTenJiaoQuantity
	 */
	public long getFirstResveTenJiaoQuantity() {
		return FirstResveTenJiaoQuantity;
	}

	/**
	 * @param firstResveTenJiaoQuantity the firstResveTenJiaoQuantity to set
	 */
	public void setFirstResveTenJiaoQuantity(long firstResveTenJiaoQuantity) {
		FirstResveTenJiaoQuantity = firstResveTenJiaoQuantity;
	}

	/**
	 * @return the firstResveTenJiaoMoney
	 */
	public int getFirstResveTenJiaoMoney() {
		return FirstResveTenJiaoMoney;
	}

	/**
	 * @param firstResveTenJiaoMoney the firstResveTenJiaoMoney to set
	 */
	public void setFirstResveTenJiaoMoney(int firstResveTenJiaoMoney) {
		FirstResveTenJiaoMoney = firstResveTenJiaoMoney;
	}

	/**
	 * @return the secondResveFiveJiaoQuantity
	 */
	public long getSecondResveFiveJiaoQuantity() {
		return SecondResveFiveJiaoQuantity;
	}

	/**
	 * @param secondResveFiveJiaoQuantity the secondResveFiveJiaoQuantity to set
	 */
	public void setSecondResveFiveJiaoQuantity(long secondResveFiveJiaoQuantity) {
		SecondResveFiveJiaoQuantity = secondResveFiveJiaoQuantity;
	}

	/**
	 * @return the secondResveFiveJiaoMoney
	 */
	public int getSecondResveFiveJiaoMoney() {
		return SecondResveFiveJiaoMoney;
	}

	/**
	 * @param secondResveFiveJiaoMoney the secondResveFiveJiaoMoney to set
	 */
	public void setSecondResveFiveJiaoMoney(int secondResveFiveJiaoMoney) {
		SecondResveFiveJiaoMoney = secondResveFiveJiaoMoney;
	}

	/**
	 * @return the secondResveTenJiaoQuantity
	 */
	public long getSecondResveTenJiaoQuantity() {
		return SecondResveTenJiaoQuantity;
	}

	/**
	 * @param secondResveTenJiaoQuantity the secondResveTenJiaoQuantity to set
	 */
	public void setSecondResveTenJiaoQuantity(long secondResveTenJiaoQuantity) {
		SecondResveTenJiaoQuantity = secondResveTenJiaoQuantity;
	}

	/**
	 * @return the secondResveTenJiaoMoney
	 */
	public int getSecondResveTenJiaoMoney() {
		return SecondResveTenJiaoMoney;
	}

	/**
	 * @param secondResveTenJiaoMoney the secondResveTenJiaoMoney to set
	 */
	public void setSecondResveTenJiaoMoney(int secondResveTenJiaoMoney) {
		SecondResveTenJiaoMoney = secondResveTenJiaoMoney;
	}

	/**
	 * @return the firstIgnoreOneYuanQuantity
	 */
	public long getFirstIgnoreOneYuanQuantity() {
		return FirstIgnoreOneYuanQuantity;
	}

	/**
	 * @param firstIgnoreOneYuanQuantity the firstIgnoreOneYuanQuantity to set
	 */
	public void setFirstIgnoreOneYuanQuantity(long firstIgnoreOneYuanQuantity) {
		FirstIgnoreOneYuanQuantity = firstIgnoreOneYuanQuantity;
	}

	/**
	 * @return the firstIgnoreOneYuanMoney
	 */
	public int getFirstIgnoreOneYuanMoney() {
		return FirstIgnoreOneYuanMoney;
	}

	/**
	 * @param firstIgnoreOneYuanMoney the firstIgnoreOneYuanMoney to set
	 */
	public void setFirstIgnoreOneYuanMoney(int firstIgnoreOneYuanMoney) {
		FirstIgnoreOneYuanMoney = firstIgnoreOneYuanMoney;
	}

	/**
	 * @return the firstIgnoreTwoYuanQuantity
	 */
	public long getFirstIgnoreTwoYuanQuantity() {
		return FirstIgnoreTwoYuanQuantity;
	}

	/**
	 * @param firstIgnoreTwoYuanQuantity the firstIgnoreTwoYuanQuantity to set
	 */
	public void setFirstIgnoreTwoYuanQuantity(long firstIgnoreTwoYuanQuantity) {
		FirstIgnoreTwoYuanQuantity = firstIgnoreTwoYuanQuantity;
	}

	/**
	 * @return the firstIgnoreTwoYuanMoney
	 */
	public int getFirstIgnoreTwoYuanMoney() {
		return FirstIgnoreTwoYuanMoney;
	}

	/**
	 * @param firstIgnoreTwoYuanMoney the firstIgnoreTwoYuanMoney to set
	 */
	public void setFirstIgnoreTwoYuanMoney(int firstIgnoreTwoYuanMoney) {
		FirstIgnoreTwoYuanMoney = firstIgnoreTwoYuanMoney;
	}

	/**
	 * @return the firstIgnoreFiveYuanQuantity
	 */
	public long getFirstIgnoreFiveYuanQuantity() {
		return FirstIgnoreFiveYuanQuantity;
	}

	/**
	 * @param firstIgnoreFiveYuanQuantity the firstIgnoreFiveYuanQuantity to set
	 */
	public void setFirstIgnoreFiveYuanQuantity(long firstIgnoreFiveYuanQuantity) {
		FirstIgnoreFiveYuanQuantity = firstIgnoreFiveYuanQuantity;
	}

	/**
	 * @return the firstIgnoreFiveYuanMoney
	 */
	public int getFirstIgnoreFiveYuanMoney() {
		return FirstIgnoreFiveYuanMoney;
	}

	/**
	 * @param firstIgnoreFiveYuanMoney the firstIgnoreFiveYuanMoney to set
	 */
	public void setFirstIgnoreFiveYuanMoney(int firstIgnoreFiveYuanMoney) {
		FirstIgnoreFiveYuanMoney = firstIgnoreFiveYuanMoney;
	}

	/**
	 * @return the firstIgnoreTenYuanQuantity
	 */
	public long getFirstIgnoreTenYuanQuantity() {
		return FirstIgnoreTenYuanQuantity;
	}

	/**
	 * @param firstIgnoreTenYuanQuantity the firstIgnoreTenYuanQuantity to set
	 */
	public void setFirstIgnoreTenYuanQuantity(long firstIgnoreTenYuanQuantity) {
		FirstIgnoreTenYuanQuantity = firstIgnoreTenYuanQuantity;
	}

	/**
	 * @return the firstIgnoreTenYuanMoney
	 */
	public int getFirstIgnoreTenYuanMoney() {
		return FirstIgnoreTenYuanMoney;
	}

	/**
	 * @param firstIgnoreTenYuanMoney the firstIgnoreTenYuanMoney to set
	 */
	public void setFirstIgnoreTenYuanMoney(int firstIgnoreTenYuanMoney) {
		FirstIgnoreTenYuanMoney = firstIgnoreTenYuanMoney;
	}

	/**
	 * @return the firstIgnoreTwentyYuanQuantity
	 */
	public long getFirstIgnoreTwentyYuanQuantity() {
		return FirstIgnoreTwentyYuanQuantity;
	}

	/**
	 * @param firstIgnoreTwentyYuanQuantity the firstIgnoreTwentyYuanQuantity to set
	 */
	public void setFirstIgnoreTwentyYuanQuantity(long firstIgnoreTwentyYuanQuantity) {
		FirstIgnoreTwentyYuanQuantity = firstIgnoreTwentyYuanQuantity;
	}

	/**
	 * @return the firstIgnoreTwentyYuanMoney
	 */
	public int getFirstIgnoreTwentyYuanMoney() {
		return FirstIgnoreTwentyYuanMoney;
	}

	/**
	 * @param firstIgnoreTwentyYuanMoney the firstIgnoreTwentyYuanMoney to set
	 */
	public void setFirstIgnoreTwentyYuanMoney(int firstIgnoreTwentyYuanMoney) {
		FirstIgnoreTwentyYuanMoney = firstIgnoreTwentyYuanMoney;
	}

	/**
	 * @return the firstIgnoreFiftyYuanQuantity
	 */
	public long getFirstIgnoreFiftyYuanQuantity() {
		return FirstIgnoreFiftyYuanQuantity;
	}

	/**
	 * @param firstIgnoreFiftyYuanQuantity the firstIgnoreFiftyYuanQuantity to set
	 */
	public void setFirstIgnoreFiftyYuanQuantity(long firstIgnoreFiftyYuanQuantity) {
		FirstIgnoreFiftyYuanQuantity = firstIgnoreFiftyYuanQuantity;
	}

	/**
	 * @return the firstIgnoreFiftyYuanMoney
	 */
	public int getFirstIgnoreFiftyYuanMoney() {
		return FirstIgnoreFiftyYuanMoney;
	}

	/**
	 * @param firstIgnoreFiftyYuanMoney the firstIgnoreFiftyYuanMoney to set
	 */
	public void setFirstIgnoreFiftyYuanMoney(int firstIgnoreFiftyYuanMoney) {
		FirstIgnoreFiftyYuanMoney = firstIgnoreFiftyYuanMoney;
	}

	/**
	 * @return the firstIgnoreHundredYuanQuantity
	 */
	public long getFirstIgnoreHundredYuanQuantity() {
		return FirstIgnoreHundredYuanQuantity;
	}

	/**
	 * @param firstIgnoreHundredYuanQuantity the firstIgnoreHundredYuanQuantity to set
	 */
	public void setFirstIgnoreHundredYuanQuantity(long firstIgnoreHundredYuanQuantity) {
		FirstIgnoreHundredYuanQuantity = firstIgnoreHundredYuanQuantity;
	}

	/**
	 * @return the firstIgnoreHundredYuanMoney
	 */
	public int getFirstIgnoreHundredYuanMoney() {
		return FirstIgnoreHundredYuanMoney;
	}

	/**
	 * @param firstIgnoreHundredYuanMoney the firstIgnoreHundredYuanMoney to set
	 */
	public void setFirstIgnoreHundredYuanMoney(int firstIgnoreHundredYuanMoney) {
		FirstIgnoreHundredYuanMoney = firstIgnoreHundredYuanMoney;
	}

	/**
	 * @return the operatorType
	 */
	public Short getOperatorType() {
		return operatorType;
	}

	/**
	 * @param operatorType the operatorType to set
	 */
	public void setOperatorType(Short operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}