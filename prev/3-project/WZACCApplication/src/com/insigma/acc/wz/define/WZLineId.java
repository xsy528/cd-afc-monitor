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
@Dic(overClass = WZLineId.class)
public class WZLineId extends Definition {
	public static WZLineId instance = new WZLineId();

	public static WZLineId getInstance() {
		return instance;
	}

	@DicItem(name = "温州S1线")
	public static Short line1 = 1;

	@DicItem(name = "温州S2线")
	public static Short line2 = 2;

	@DicItem(name = "温州S3线")
	public static Short line3 = 3;
}
