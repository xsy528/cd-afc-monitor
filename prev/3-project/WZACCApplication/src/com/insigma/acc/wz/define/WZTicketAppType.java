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
 * Ticket: 票卡挂失、解挂类型
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = WZTicketAppType.class)
public class WZTicketAppType extends Definition {

	private static WZTicketAppType instance = new WZTicketAppType();

	public static WZTicketAppType getInstance() {
		return instance;
	}

	@DicItem(name = "挂失")
	public static int LOSS = 0x01;

	@DicItem(name = "解挂")
	public static int LIFTING_LOSS = 0x02;

}
