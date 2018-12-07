/*
 * 日期：2018-8-23
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 操作员权限定义
 * @author chenhangwen
 */
@Dic(overClass = WZOperCompetenceType.class)
public class WZOperCompetenceType extends Definition {

	private static WZOperCompetenceType instance = new WZOperCompetenceType();

	public static WZOperCompetenceType getInstance() {
		return instance;
	}

	@DicItem(name = "班次解锁权限", index = 1)
	public static short Unlock = 0b01;

	@DicItem(name = "审核权限", index = 2)
	public static short Check = 0b10;

}
