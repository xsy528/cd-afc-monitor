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
 * Ticket: 计量单位
 * @author chenfuchun
 *
 */
@Dic(overClass = WZUnit.class)
public class WZUnit extends Definition {

	private static WZUnit instance = new WZUnit();

	public static WZUnit getInstance() {
		return instance;
	}

	@DicItem(name = "张")
	public static Short zhang = 0;

	@DicItem(name = "套")
	public static Short tao = 1;

}
