/* 
 * 日期：2011-2-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.afc.monitor.constant.xdr.typedef.TicketFamily_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCTicketFamily.class)
public class AFCTicketFamily extends Definition {

	private static AFCTicketFamily instance = new AFCTicketFamily();

	public static AFCTicketFamily getInstance() {
		return instance;
	}

	@DicItem(name = "单程类票", index = 0)
	public static Integer SJ_TICKET = TicketFamily_t.TicketFamily_SJTUL;

	@DicItem(name = "出站类票", index = 1)
	public static Integer OUT_TICKET = TicketFamily_t.TicketFamily_SJTULOUT;

	@DicItem(name = "计程票", index = 2)
	public static Integer JCHENGP_TICKET = TicketFamily_t.TicketFamily_JCPCHENGCPU;

	@DicItem(name = "计次票", index = 3)
	public static Integer JCIP_TICKET = TicketFamily_t.TicketFamily_JCPCHICPU;

	@DicItem(name = "计时票", index = 4)
	public static Integer JSHIP_TICKET = TicketFamily_t.TicketFamily_JSPCPU;

	@DicItem(name = "公交卡", index = 5)
	public static Integer GJIC_TICKET = TicketFamily_t.TicketFamily_GJIC;

	@DicItem(name = "市民卡", index = 6)
	public static Integer SMIC_TICKET = TicketFamily_t.TicketFamily_SMIC;

	@DicItem(name = "城市交通卡", index = 7)
	public static Integer CSTIC_TICKET = TicketFamily_t.TicketFamily_CSTIC;

	@DicItem(name = "二维码电子票", index = 8)
	public static Integer QTIC_TICKET = TicketFamily_t.TicketFamily_QTIC;

	@DicItem(name = "银联卡")
	public static Integer BCIC_TICKET = TicketFamily_t.TicketFamily_BCIC;
}