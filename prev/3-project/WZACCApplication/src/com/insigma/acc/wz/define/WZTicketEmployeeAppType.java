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
 * Ticket: 员工票申请处理类型
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = WZTicketEmployeeAppType.class)
public class WZTicketEmployeeAppType extends Definition {

	private static WZTicketEmployeeAppType instance = new WZTicketEmployeeAppType();

	public static WZTicketEmployeeAppType getInstance() {
		return instance;
	}

	@DicItem(name = "未制卡")
	public static Integer UNISSUED = 0x00;

	//	@DicItem(name = "制卡中")
	//	public static Integer SUEDING = 0x01;

	@DicItem(name = "已制卡")
	public static Integer ISSUEN = 0x01;

	//	@DicItem(name = "已打印")
	//	public static Integer ISPRINT = 0x03;

}
