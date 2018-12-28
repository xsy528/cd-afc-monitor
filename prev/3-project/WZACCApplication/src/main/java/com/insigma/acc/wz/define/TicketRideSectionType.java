/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州员工票乘坐区段编码
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = TicketRideSectionType.class)
public class TicketRideSectionType extends Definition {

	private static TicketRideSectionType instance = new TicketRideSectionType();

	public static TicketRideSectionType getInstance() {
		return instance;
	}

	@DicItem(name = "不限制", group = "TicketRideSection")
	public static Integer NO_LIMIT = 0;

	@DicItem(name = "限制", group = "TicketRideSection")
	public static Integer LIMIT = 1;

}
