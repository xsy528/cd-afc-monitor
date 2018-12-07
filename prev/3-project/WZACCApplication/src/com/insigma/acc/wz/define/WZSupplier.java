/* 
 * 日期：2018年3月30日
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
 * Ticket: 
 * @author  hexingyue
 *
 */
@Dic(overClass = WZSupplier.class)
public class WZSupplier extends Definition {
	public static WZSupplier instance = new WZSupplier();

	public static WZSupplier getInstance() {
		return instance;
	}

	@DicItem(name = "浙江浙大网新众合轨道交通有限公司",desc = "网新")
	public static Integer UNITTEC = 0;

	@DicItem(name = "上海普天邮通科技股份有限公司",desc = "邮通")
	public static Integer POTEVIO = 1;
}
