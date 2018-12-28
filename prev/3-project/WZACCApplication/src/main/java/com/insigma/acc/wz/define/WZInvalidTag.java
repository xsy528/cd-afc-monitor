/* 
 * 日期：2018年4月13日
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
 * Ticket: 有效标记
 * @author chenfuchun
 *
 */
@Dic(overClass = WZInvalidTag.class)
public class WZInvalidTag extends Definition {

	private static WZInvalidTag instance = new WZInvalidTag();

	public static WZInvalidTag getInstance() {
		return instance;
	}

	@DicItem(name = "无效")
	public static Short invalid = 0;

	@DicItem(name = "有效")
	public static Short valid = 1;

}
