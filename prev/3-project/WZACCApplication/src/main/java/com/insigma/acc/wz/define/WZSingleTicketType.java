/* 
 * 日期：2014-12-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

//import com.insigma.acc.clear.define.SingleTicketType;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 单边交易票卡小类定义
 * 
 * @author hexingyue
 */
//@Dic(overClass = SingleTicketType.class)
public class WZSingleTicketType extends Definition {

	private static WZSingleTicketType instance = new WZSingleTicketType();

	public static WZSingleTicketType getInstance() {
		return instance;
	}

	/* 单程票 */
	@DicItem(name = "普通单程票", group = "SINGLE", index = 0)
	public static Integer TF_SJT_ORDINARY = WZTicketType.TF_SJT_ORDINARY;

	@DicItem(name = "福利免费票", group = "SINGLE", index = 3)
	public static Integer TF_SJT_WELFARE = WZTicketType.TF_SJT_WELFARE;

	@DicItem(name = "5折福利票", group = "SINGLE", index = 4)
	public static Integer TF_HALF_WELFARE = WZTicketType.TF_HALF_WELFARE;

	@DicItem(name = "8折福利票", group = "SINGLE", index = 5)
	public static Integer TF_TWENTY_WELFARE = WZTicketType.TF_TWENTY_WELFARE;

	/* 出站票 */
	@DicItem(name = "付费出站票", group = "SINGLE", index = 7)
	public static Integer TF_EXIT_PAID = WZTicketType.TF_EXIT_PAID;

	@DicItem(name = "免费出站票", group = "SINGLE", index = 8)
	public static Integer TF_EXIT_FREE = WZTicketType.TF_EXIT_FREE;

}
