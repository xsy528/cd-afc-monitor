/* 
 * 日期：2017年6月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 操作员操作类型定义
 * @author  mengyifan
 *
 */
@Dic(overClass = WZOperationType.class)
public class WZOperationType extends Definition {

	private static WZOperationType instance = new WZOperationType();

	public static WZOperationType getInstance() {
		return instance;
	}

	@DicItem(name = "登录")
	public static int LOGIN = 0x00;

	@DicItem(name = "签退")
	public static int LOGOUT = 0x01;

	@DicItem(name = "轮班暂停")
	public static int TIMEOUT = 0x02;

	@DicItem(name = "自动签退")
	public static int AUTO_LOGOUT = 0x03;

	@DicItem(name = "轮班暂停恢复")
	public static int RESUME = 0x04;

	@DicItem(name = "票箱装入")
	public static int TICKET_BOX_IN = 0x05;

	@DicItem(name = "票箱取出")
	public static int TICKET_BOX_OUT = 0x06;

	@DicItem(name = "纸币钱箱装入")
	public static int CASH_BOX_IN = 0x07;

	@DicItem(name = "纸币钱箱取出")
	public static int CASH_BOX_OUT = 0x08;

	@DicItem(name = "硬币钱箱装入")
	public static int COIN_BOX_IN = 0x09;

	@DicItem(name = "硬币钱箱取出")
	public static int COIN_BOX_OUT = 0x0A;

	@DicItem(name = "硬币清空")
	public static int COIN_BOX_CLEAR = 0x0B;

	@DicItem(name = "设备钱箱数据上传")
	public static int CASH_BOX_UPLOAD = 0x0C;

	@DicItem(name = "纸币清空")
	public static int CASH_BOX_CLEAR = 0x0D;

}
