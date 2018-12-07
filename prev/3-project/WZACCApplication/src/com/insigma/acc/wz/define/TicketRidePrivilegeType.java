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
 * Ticket: 员工票乘坐权利
 * @author chenfuchun
 *
 */
@Dic(overClass = TicketRidePrivilegeType.class)
public class TicketRidePrivilegeType extends Definition {

	private static TicketRidePrivilegeType instance = new TicketRidePrivilegeType();

	public static TicketRidePrivilegeType getInstance() {
		return instance;
	}

	@DicItem(name = "不限制", group = "TicketRidePrivilege")
	public static Integer NO_LIMIT = 0;

	@DicItem(name = "单人使用，并限制乘车次数", group = "TicketRidePrivilege")
	public static Integer PERSON_RIDE_LIMIT = 1;

	@DicItem(name = "单人使用，并限制乘车区段", group = "TicketRidePrivilege")
	public static Integer PERSON_STATION_LIMIT = 2;

	@DicItem(name = "单人使用，并限制乘车次数和区段", group = "TicketRidePrivilege")
	public static Integer PERSON_RIDE_STATION_LIMIT = 3;

	@DicItem(name = " 限制单人本站进本站出", group = "TicketRidePrivilege")
	public static Integer PERSON_THIS_STATION_LIMIT = 4;

	@DicItem(name = "无乘车权限", group = "TicketRidePrivilege")
	public static Integer LIMITED = 5;

}
