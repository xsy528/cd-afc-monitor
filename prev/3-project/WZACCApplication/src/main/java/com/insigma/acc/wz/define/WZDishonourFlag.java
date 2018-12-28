/* 
 * 日期：2018-8-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州调整及拒付标记
 * 
 * @author chenhangwen
 */

@Dic(overClass = WZDishonourFlag.class)
public class WZDishonourFlag extends Definition {
	private static WZDishonourFlag instance = new WZDishonourFlag();

	public static WZDishonourFlag getInstance() {
		return instance;
	}

	@DicItem(name = "调整")
	public static Integer ADJUSTMENT = 0x03;

	@DicItem(name = "拒付")
	public static Integer DISHONOUR = 0x04;

}
