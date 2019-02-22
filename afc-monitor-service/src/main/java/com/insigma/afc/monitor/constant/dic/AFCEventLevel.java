/* 
 * 日期：2010-11-10
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 事件等级
 * 
 * @author 陈海锋
 */
@Dic(overClass = AFCEventLevel.class)
public class AFCEventLevel extends Definition {

	private static AFCEventLevel instance = new AFCEventLevel();

	public static AFCEventLevel getInstance() {
		return instance;
	}

	@DicItem(name = "正常")
	public static Integer NORMAL = 0;

	@DicItem(name = "警告")
	public static Integer WARNING = 1;

	@DicItem(name = "报警")
	public static Integer ALARM = 2;

}
