/* 
 * 日期：2011-7-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 支付方式 Ticket:
 * 
 * @author 陈海锋
 */
@Dic(overClass = AFCPayType.class)
public class AFCPayType extends Definition {

	private static AFCPayType instance = new AFCPayType();

	public static AFCPayType getInstance() {
		return instance;
	}

	@DicItem(name = "现金")
	public static Integer MONEY = 0;

	@DicItem(name = "储值票")
	public static Integer STORE_CARD = 1;

	@DicItem(name = "银行卡")
	public static Integer BANK_CARD = 2;

	@DicItem(name = "二维码") //待定 先暂时使用
	public static Integer QR_CODE_YC = 3;

}
