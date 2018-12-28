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
@Dic(overClass = AFCStatusType.class)
public class AFCStatusType extends Definition {

	private static AFCStatusType instance = new AFCStatusType();

	public static AFCStatusType getInstance() {
		return instance;
	}

	@DicItem(name = "当前版本", group = "status", index = 1)
	public static Integer CURRENTVERSION = 1;

	@DicItem(name = "历史版本", group = "status", index = 2)
	public static Integer HISTORYVERSION = 4;

}
