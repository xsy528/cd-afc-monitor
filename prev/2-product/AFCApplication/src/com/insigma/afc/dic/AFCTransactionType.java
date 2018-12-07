/* 
 * 日期：2010-9-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级交易类型<br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Dic(overClass = AFCTransactionType.class)
public class AFCTransactionType extends Definition {
	private static AFCTransactionType instance = new AFCTransactionType();

	private AFCTransactionType() {
	}

	public static AFCTransactionType getInstance() {
		return instance;
	}

	/** 单程票发售 */
	@DicItem(name = "单程票发售")
	public static Integer SJT_SALE = 0x01;

	/** 单程票更新：超时 */
	@DicItem(name = "单程票更新：超时")
	public static Integer SJT_UPDATE_OVERSTAYING = 0x02;

	/** 单程票更新：超程 */
	@DicItem(name = "单程票更新：超程")
	public static Integer SJT_UPDATE_UNDER_FARE = 0x03;

	/** 单程票更新：进出站不匹配罚款（进站端） */
	@DicItem(name = "进出站不匹配罚款（进站端）")
	public static Integer SJT_UPDATE_NOT_EXIT = 0x04;

	/** 单程票更新：进出站不匹配罚款（出站端） */
	@DicItem(name = "进出站不匹配罚款（出站端）")
	public static Integer SJT_UPDATE_NOT_ENTRY = 0x05;

	/** 单程票退票 */
	@DicItem(name = "单程票退票")
	public static Integer SJT_RETURN = 0x06;

	/** 单程票进站 */
	@DicItem(name = "单程票进站")
	public static Integer SJT_ENTRY = 0x07;

	/** 单程票出站 */
	@DicItem(name = "单程票出站")
	public static Integer SJT_EXIT = 0x08;

	@DicItem(name = "单程票发售")
	public static Integer CPU_CARD_SALE = 0x21;

	/** 操作员班次结束 */
	@DicItem(name = "操作员班次结束")
	public static Integer SHIFT_END = 0x60;

	/** 插入票箱 */
	@DicItem(name = "插入票箱")
	public static Integer STOCK_IN = 0x61;

	/** 拔出票箱 */
	@DicItem(name = "拔出票箱")
	public static Integer STOCK_OUT = 0x62;

	/** 插入硬币钱箱 */
	@DicItem(name = "插入硬币钱箱")
	public static Integer COIN_BOX_IN = 0x63;

	/** 拔出硬币钱箱 */
	@DicItem(name = "拔出硬币钱箱")
	public static Integer COIN_BOX_OUT = 0x64;

	/** 插入纸币钱箱 */
	@DicItem(name = "插入纸币钱箱")
	public static Integer BANK_NOTE_BOX_IN = 0x65;

	/** 拔出纸币钱箱 */
	@DicItem(name = "拔出纸币钱箱")
	public static Integer BANK_NOTE_BOX_OUT = 0x66;

	/** 插入循环找零箱 */
	@DicItem(name = "插入循环找零箱")
	public static Integer DRUM_BLOCK_IN = 0x67;

	/** 拔出循环找零箱 */
	@DicItem(name = "拔出循环找零箱")
	public static Integer DRUM_BLOCK_OUT = 0x68;

	/** 操作员登陆 */
	@DicItem(name = "操作员登陆 ")
	public static Integer OPERATOR_LOGIN = 0x69;

	/** 操作员退出 */
	@DicItem(name = "操作员退出  ")
	public static Integer OPERATOR_LOGOUT = 0x6A;

	/** 插入找零钱箱 */
	@DicItem(name = "插入找零钱箱  ")
	public static Integer HOPPER_IN = 0x6B;

	/** 拔出找零钱箱 */
	@DicItem(name = "拔出找零钱箱  ")
	public static Integer HOPPER_OUT = 0x6C;

}
