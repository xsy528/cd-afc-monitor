/* 
 * 日期：2018年5月29日
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
 * Ticket: 员工票允许权利
 * @author chenfuchun
 *
 */
@Dic(overClass = TicketPrivilegeType.class)
public class TicketPrivilegeType extends Definition {

	private static TicketPrivilegeType instance = new TicketPrivilegeType();

	public static TicketPrivilegeType getInstance() {
		return instance;
	}

	@DicItem(name = "允许线路", group = "TicketPrivilege")
	public static Integer BASED_LINE = 1;

	@DicItem(name = "允许车站", group = "TicketPrivilege")
	public static Integer BASED_STATION = 2;

	@DicItem(name = "允许设备类型", group = "TicketPrivilege")
	public static Integer BASED_DEVICE_TYPE = 3;

}
