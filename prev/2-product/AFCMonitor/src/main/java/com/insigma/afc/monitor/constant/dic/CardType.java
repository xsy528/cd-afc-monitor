/* 
 * 日期：2011-2-18
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 
 * Ticket: 卡系类型
 * @author  hexingyue
 *
 */
@Dic(overClass = CardType.class)
public class CardType extends Definition {

	private static CardType instance = new CardType();

	public static CardType getInstance() {
		return instance;
	}

	//一票通
	@DicItem(name = "一票通", index = 0)
	public static Short CARD_TYPE_YPT = 0;
	//一卡通
	@DicItem(name = "一卡通", index = 1)
	public static Short CARD_TYPE_YKT = 1;
	//银联闪付（待定）
	@DicItem(name = "银联闪付", index = 3)
	public static Short CARD_TYPE_BANK = 3;
	//二维码（待定）
	@DicItem(name = "二维码", index = 4)
	public static Short CARD_TYPE_QR = 4;

}
