/* 
 * 日期：2011-9-27
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/*
 * AUTHOR shenchao
 */
@Dic(overClass = WZNormalOrAbnormality.class)
public class WZNormalOrAbnormality extends Definition {

	public static WZNormalOrAbnormality instance = new WZNormalOrAbnormality();

	public static WZNormalOrAbnormality getInstance() {
		return instance;
	}

	@DicItem(name = "正常")
	public static Integer NORMAL_STATUS = 0x00;

	@DicItem(name = "异常")
	public static Integer ABNORMAL_STATUS = 0x01;
}
