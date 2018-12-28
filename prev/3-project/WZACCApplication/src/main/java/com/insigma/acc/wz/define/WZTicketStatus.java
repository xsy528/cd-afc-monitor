/* 
 * 日期：2013-11-12
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
 * Ticket: 票卡状态
 * 
 * @author BOND XIE(xiebo@zdwxgd.com)
 */
@Dic(overClass = WZTicketStatus.class)
public class WZTicketStatus extends Definition {

	private static WZTicketStatus instance = new WZTicketStatus();

	public static WZTicketStatus getInstance() {
		return instance;
	}

	@DicItem(name = "未初始化")
	public static Integer NOINIT = 0;

	@DicItem(name = "初始化")
	public static Integer INIT = 1;

	@DicItem(name = "售卖")
	public static Integer SALE = 2;

	@DicItem(name = "退票")
	public static Integer ReFUND = 3;

	@DicItem(name = "黑名单")
	public static Integer BLACK = 4;

	@DicItem(name = "挂失")
	public static Integer LOST = 5;
}
