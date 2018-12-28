/*
 * 日期：2011-6-1
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 票卡退票退款处理状态定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = WZTicketRefundHandleStatus.class)
public class WZTicketRefundHandleStatus extends Definition {

	private static WZTicketRefundHandleStatus instance = new WZTicketRefundHandleStatus();

	public static WZTicketRefundHandleStatus getInstance() {
		return instance;
	}

	@DicItem(name = "未受理")
	public static int APP_NON_HANDLE = 0x00;

	@DicItem(name = "已批准")
	public static int APP_APPROVED = 0x01;

	@DicItem(name = "已拒绝")
	public static int APP_REFUSED = 0x02;

}
