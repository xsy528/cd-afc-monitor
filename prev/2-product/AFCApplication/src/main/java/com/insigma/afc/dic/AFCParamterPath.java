/* 
 * 日期：2015-5-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCParamterPath.class)
public class AFCParamterPath extends Definition {
	private static AFCParamterPath instance = new AFCParamterPath();

	public static AFCParamterPath getInstance() {
		return instance;
	}

	@DicItem(name = "cur")
	public static String curPath = "cur";

	@DicItem(name = "fur")
	public static String furPath = "fur";
}
