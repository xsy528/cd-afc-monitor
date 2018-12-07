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
 * Ticket: 票卡挂失申请状态定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = WZTicketAppHandleStatus.class)
public class WZTicketAppHandleStatus extends Definition {

	private static WZTicketAppHandleStatus instance = new WZTicketAppHandleStatus();

	public static WZTicketAppHandleStatus getInstance() {
		return instance;
	}

	@DicItem(name = "未审查")
	public static int APP_NON_HANDLE = 0x00;

	@DicItem(name = "合法申请")
	public static int APP_VALID = 0x01;

	@DicItem(name = "非法申请")
	public static int APP_INVALID = 0x02;

}
