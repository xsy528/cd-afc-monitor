/* 
 * 日期：2017年10月12日
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
 * Ticket: 温州票卡小类型定义
 * 
 * @author chenfuchun
 *
 */
@Dic(overClass = WZTicketType.class)
public class WZTicketType extends Definition {

	private static WZTicketType instance = new WZTicketType();

	public static WZTicketType getInstance() {
		return instance;
	}

	/* 单程票 */
	@DicItem(name = "普通单程票", group = "TF_SJT", index = 0)
	public static Integer TF_SJT_ORDINARY = 1;

	@DicItem(name = "纪念单程票", group = "TF_SJT", index = 1)
	public static Integer TF_SJT_MEMORIAL = 2;

	@DicItem(name = "预赋值单程票", group = "TF_SJT", index = 2)
	public static Integer TF_SJT_PREPAID = 3;

	@DicItem(name = "福利免费票", group = "TF_SJT", index = 3)
	public static Integer TF_SJT_WELFARE = 4;

	@DicItem(name = "一日票", group = "TF_SJT", index = 4)
	public static Integer TF_SJT_ONEDAY = 5;

	@DicItem(name = "三日票", group = "TF_SJT", index = 5)
	public static Integer TF_SJT_THREEDAY = 6;

	@DicItem(name = "五折福利票", group = "TF_SJT", index = 6)
	public static Integer TF_HALF_WELFARE = 7;

	@DicItem(name = "八折福利票", group = "TF_SJT", index = 7)
	public static Integer TF_TWENTY_WELFARE = 8;

	/* 出站票 */
	@DicItem(name = "付费出站票", group = "TF_EXIT", index = 8)
	public static Integer TF_EXIT_PAID = 0x0E;

	@DicItem(name = "免费出站票", group = "TF_EXIT", index = 9)
	public static Integer TF_EXIT_FREE = 0x0F;

	/* 计程票 */
	@DicItem(name = "普通非记名计程票", group = "TF_MILE", index = 10)
	public static Integer TF_MILE_ORDINARY_ANONYMOUS = 0x10;

	@DicItem(name = "普通记名计程票", group = "TF_MILE", index = 11)
	public static Integer TF_MILE_ORDINARY_NOANONYMOUS = 0x11;

	/* 计时票 */
	//	@DicItem(name = "普通计时票", group = "TF_TIME", index = 9)
	//	public static Integer TF_TIME_ORDINARY = 0x20;

	@DicItem(name = "纪念计时票", group = "TF_TIME", index = 12)
	public static Integer TF_TIME_MEMORIAL = 0x20;

	/* 计次票 */
	@DicItem(name = "月票", group = "TF_RIDE", index = 13)
	public static Integer TF_RIDE_ORDINARY = 0x30;

	@DicItem(name = "纪念计次票", group = "TF_RIDE", index = 14)
	public static Integer TF_RIDE_MEMORIAL = 0x36;

	/* 员工票 */
	@DicItem(name = "员工工作票", group = "TF_STAFF", index = 15)
	public static Integer TF_STAFF_ORDINARY = 0x50;

	@DicItem(name = "记名临时工作票", group = "TF_STAFF", index = 16)
	public static Integer TF_STAFF_TEMPREGISTER = 0x60;

	@DicItem(name = "不记名临时工作票", group = "TF_STAFF", index = 17)
	public static Integer TF_STAFF_TEMPANONYMOUS = 0x61;

	@DicItem(name = "委外工作卡", group = "TF_STAFF", index = 18)
	public static Integer TF_OUTSOURCE = 0x62;

	@DicItem(name = "限次工作月票", group = "TF_STAFF", index = 19)
	public static Integer TF_MONTHLYTICKET = 0x63;

	@DicItem(name = "车站工作票", group = "TF_STAFF", index = 19)
	public static Integer TF_STATIONTICKET = 0x70;

	@DicItem(name = "市民卡", group = "TF_CITIZEN", index = 18)
	public static Integer TF_CITIZEN_SMK = 0xD0;

	@DicItem(name = "银联卡", group = "TF_UNIONPAY", index = 19)
	public static Integer TF_UNIONPAY_BANK = 0x80;

	@DicItem(name = "二维码单程票", group = "TF_QRCODE", index = 20)
	public static Integer TF_QRCODE_SINGLE = 0x90;

}
