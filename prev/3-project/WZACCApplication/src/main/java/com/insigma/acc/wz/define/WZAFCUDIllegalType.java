/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.afc.dic.AFCUDIllegalType;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 
 * Ticket: 可疑类型码
 * @author  hexingyue
 *
 */
@Dic(overClass = AFCUDIllegalType.class)
public class WZAFCUDIllegalType extends Definition {

	private static WZAFCUDIllegalType instance = new WZAFCUDIllegalType();

	public static WZAFCUDIllegalType getInstance() {
		return instance;
	}

	@DicItem(name = "正常", index = 0)
	public static Integer NORMAL = 0x00;

	@DicItem(name = "无效的交易类型", index = 1)
	public static Integer TRANS_TYPE_INVALID = 0x01;

	@DicItem(name = "无效的服务商", index = 2)
	public static Integer SERVICE_AGENT_INVALID = 0x02;

	@DicItem(name = "无效的交易发生线路", index = 3)
	public static Integer LINE_ID_INVALID = 0x03;

	@DicItem(name = "无效的交易发生车站", index = 4)
	public static Integer STATION_ID_INVALID = 0x04;

	@DicItem(name = "出站交易无效的进站车站", index = 5)
	public static Integer ENTRYSTATION_ID_INVALID = 0x05;

	@DicItem(name = "无效的金额数据", index = 6)
	public static Integer AMOUNT_INVALID = 0x06;

	@DicItem(name = "无效的交易文件建立时间", index = 7)
	public static Integer TIME_INVALID = 0x07;

	@DicItem(name = "超前的交易发生时间", index = 8)
	public static Integer TIME_EXPIRED = 0x08;

	@DicItem(name = "无效的交易发起设备SAM号", index = 9)
	public static Integer SAM_CARD_INVALID = 0x09;

	@DicItem(name = "无效的交易格式版本", index = 10)
	public static Integer TRANS_VERSION_INVALID = 0x0A;

	@DicItem(name = "无效的车票类型", index = 11)
	public static Integer TICKET_TYPE_INVALID = 0x0B;

	@DicItem(name = "无效的进站线路", index = 12)
	public static Integer ENTRY_LINE_INVALID = 0x0C;

	@DicItem(name = "无效的进站车站", index = 13)
	public static Integer ENTRY_STATION_INVALID = 0x0D;

	@DicItem(name = "无效的出站线路", index = 14)
	public static Integer EXIT_LINE_INVALID = 0x0E;

	@DicItem(name = "无效的出站车站", index = 15)
	public static Integer EXIT_STATION_INVALID = 0x0F;

	@DicItem(name = "无效的售票终点线路", index = 16)
	public static Integer TICKET_SOLD_LINE_INVALID = 0x10;

	@DicItem(name = "无效的售票终点车站", index = 17)
	public static Integer TICKET_SOLD_STATION_INVALID = 0x11;

	@DicItem(name = "过期交易", index = 18)
	public static Integer TRANS_EXPIRED = 0x12;

	@DicItem(name = "TAC码错误", index = 19)
	public static Integer TAC_INVALID = 0x13;

	@DicItem(name = "黑名单票卡", index = 20)
	public static Integer CARD_INVALID = 0x14;

	@DicItem(name = "余额越界", index = 21)
	public static Integer BALANCE_BEYOND = 0x15;

	@DicItem(name = "非法的加值交易次数", index = 22)
	public static Integer ADD_TIME_VALUE_INVALID = 0x16;

	@DicItem(name = "非法的上一次充值设备", index = 23)
	public static Integer LAST_ADD_DEVICE_INVALID = 0x17;

	@DicItem(name = "测试票交易", index = 24)
	public static Integer TEST_TICKET = 0x18;

	@DicItem(name = "错误的发行商编号", index = 25)
	public static Integer VERDOR_ID_INVALID = 0x19;

	@DicItem(name = "暂不结算（主要考虑旅游票、计时票、委外工作卡还未到结算日）", index = 26)
	public static Integer NOT_CLEAR = 0x1A;

	@DicItem(name = "未知", index = 27)
	public static Integer UNKNOW_INVLID = 0xff;

	//自定义
	@DicItem(name = "无效的交易发生设备", index = 28)
	public static Integer DEVICE_ID_INVALID = 0x1B;

	@DicItem(name = "交易重复", index = 29)
	public static Integer TRANSACTION_DUPLICATE = 0x1C;
}
