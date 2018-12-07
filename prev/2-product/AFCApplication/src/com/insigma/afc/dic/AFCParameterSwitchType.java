/* 
 * 日期：2011-2-16
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 参数类型切换类型
 * 
 */
@Dic(overClass = AFCParameterSwitchType.class)
public class AFCParameterSwitchType extends Definition {

	private static AFCParameterSwitchType instance = new AFCParameterSwitchType();

	public static AFCParameterSwitchType getInstance() {
		return instance;
	}

	@DicItem(name = "普通同步/导入")
	public static Integer imported = 1;

	@DicItem(name = "将来版本激活")
	public static Integer actived = 2;

	@DicItem(name = "参数上报")
	public static Integer send = 3;

	@DicItem(name = "草稿版本转换将来版本")
	public static Integer replace = 4;

}
