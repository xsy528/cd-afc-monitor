/* 
 * 日期：2011-2-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCTicketFamily.class)
public class AFCTicketFamily extends Definition {

	private static AFCTicketFamily instance = new AFCTicketFamily();

	public static AFCTicketFamily getInstance() {
		return instance;
	}

	@DicItem(name = "单程票", group = "Spinner")
	public static Integer SINGLE_JOURNEY_T = 1;

	@DicItem(name = "出站票", group = "Spinner")
	public static Integer EXIT_TOKEN_T = 2;

	@DicItem(name = "计程票", group = "Spinner")
	public static Integer METERED_FARE_T = 3;

	@DicItem(name = "计次票", group = "Spinner")
	public static Integer MUTIL_RIDE_T = 4;

	@DicItem(name = "月票", group = "Spinner")
	public static Integer MONTHLY_T = 5;

	@DicItem(name = "员工票", group = "Spinner")
	public static Integer STAFF_PASS_T = 6;

	@DicItem(name = "一卡通", group = "Spinner")
	public static Integer CITY_CARD_T = 7;

}