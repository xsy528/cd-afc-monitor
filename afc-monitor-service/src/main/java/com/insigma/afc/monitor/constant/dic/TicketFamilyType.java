/*
 * 日期：2011-6-1
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

package com.insigma.afc.monitor.constant.dic;

import com.insigma.afc.monitor.constant.xdr.typedef.TicketFamilyType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket:票种大类定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = AFCTicketFamily.class)
public class TicketFamilyType extends Definition {

	private static TicketFamilyType instance = new TicketFamilyType();

	public static TicketFamilyType getInstance() {
		return instance;
	}

	@DicItem(name = "单程票", group = "1")
	public static Integer SJ_TICKET = TicketFamilyType_t.TF_SJT;

	@DicItem(name = "出站票", group = "1")
	public static Integer EXIT_TICKET = TicketFamilyType_t.TF_EXIT;

	@DicItem(name = "计程票", group = "1")
	public static Integer CAL_DISTANCE_TICKET = TicketFamilyType_t.TF_MILE;

	@DicItem(name = "计时票", group = "1")
	public static Integer CAL_PERIOD_TICKET = TicketFamilyType_t.TF_TIME;

	@DicItem(name = "计次票", group = "1")
	public static Integer CAL_USEDTIMES_TICKET = TicketFamilyType_t.TF_RIDE;

	@DicItem(name = "员工票", group = "1")
	public static Integer STAFF_TICKET = TicketFamilyType_t.TF_STAFF;

	@DicItem(name = "市民卡", group = "1")
	public static Integer YKT_CITIZEN = TicketFamilyType_t.TF_CITIZEN;

	@DicItem(name = "手机卡", group = "1")
	public static Integer CELLPHONE_TICKET = TicketFamilyType_t.TF_MOBILE;

	@DicItem(name = "银联卡", group = "1")
	public static Integer UNIONPAY_TICKET = TicketFamilyType_t.TF_UNIONPAY;

	@DicItem(name = "二维码", group = "1")
	public static Integer QRCODE_TICKET = TicketFamilyType_t.TF_QRCODE;

}
