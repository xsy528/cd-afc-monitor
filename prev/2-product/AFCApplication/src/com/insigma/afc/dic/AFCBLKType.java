/* 
 * 日期：2010-11-10
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 版本状态
 * 
 * @author shenchao
 */
@Dic(overClass = AFCBLKType.class)
public class AFCBLKType extends Definition {

	private static AFCBLKType instance = new AFCBLKType();

	public static AFCBLKType getInstance() {
		return instance;
	}

	// ACCEOD(0), LCEOD(1), BLK(2), WAIVER(3);
	@DicItem(name = "甬城通M1卡黑名单", group = "blkfiletype")
	public static Integer BLK_YCT_M1 = 0x01;

	@DicItem(name = "甬城通CPU卡黑名单", group = "blkfiletype")
	public static Integer BLK_YCT_CPU = 0x02;

	@DicItem(name = "一票通全量黑名单", group = "blkfiletype")
	public static Integer BLK_CCHS_FULL = 0x03;

	@DicItem(name = "一票通增量黑名单", group = "blkfiletype")
	public static Integer BLK_CCHS_INCR = 0x04;

}
