/* 
 * 日期：2018年9月18日
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
 * Ticket: 任务标识
 * @author chenfuchun
 *
 */
@Dic(overClass = ACCESTaskFlag.class)
public class ACCESTaskFlag extends Definition {

	private static ACCESTaskFlag instance = new ACCESTaskFlag();

	public static ACCESTaskFlag getInstance() {
		return instance;
	}

	@DicItem(name = "储值票初始化")
	public static Short svtInit = 0;

	@DicItem(name = "储值票个性化")
	public static Short svtPersonal = 1;

}
