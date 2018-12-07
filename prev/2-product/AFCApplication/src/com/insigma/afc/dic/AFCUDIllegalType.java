/**
 * 
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * @author shenchao
 *
 */
@Dic(overClass = AFCUDIllegalType.class)
public class AFCUDIllegalType extends Definition {

	private static AFCUDIllegalType instance = new AFCUDIllegalType();

	public static AFCUDIllegalType getInstance() {
		return instance;
	}

	@DicItem(name = "未知错误")
	public static Integer UNKNOW_INVLID = 0x00;

	@DicItem(name = "TAC码错误")
	public static Integer TAC_INVALID = 0x01;

	//	@DicItem(name = "交易重复")
	//	public static Integer TRANSACTION_DUPLICATED = 0x02;

	@DicItem(name = "线路号错误")
	public static Integer LINE_ID_INVALID = 0x03;

	@DicItem(name = "车站号错误")
	public static Integer STATION_ID_INVALID = 0x04;

	@DicItem(name = "交易时间错误")
	public static Integer TIME_INVALID = 0x05;

	@DicItem(name = "交易金额错误")
	public static Integer AMOUNT_INVALID = 0x06;

	@DicItem(name = "黑灰名单卡交易")
	public static Integer CARD_INVALID = 0x07;

	@DicItem(name = "节点编号错误")
	public static Integer NODEID_INVALID = 0x08;

	@DicItem(name = "交易重复")
	public static Integer TRANSACTION_DUPLICATE = 0x09;
}
