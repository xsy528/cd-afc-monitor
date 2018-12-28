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
 * Ticket: 参数类型
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = AFCParameterType.class)
public class AFCParameterType extends Definition {

	private static AFCParameterType instance = new AFCParameterType();

	public static AFCParameterType getInstance() {
		return instance;
	}

	@DicItem(name = "黑名单")
	public static Integer BLK_TYPE = 2;

	@DicItem(name = "模式履历")
	public static Integer WAIVER_TYPE = 3;

}
