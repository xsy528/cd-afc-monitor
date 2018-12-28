/* 
 * 日期：2018年10月19日
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
 * Ticket: 持卡人类型标识
 * @author  matianming
 *
 */
@Dic(overClass = HolderTypeFlagType.class)
public class HolderTypeFlagType extends Definition{

	private static HolderTypeFlagType instance = new HolderTypeFlagType();

	public static HolderTypeFlagType getInstance() {
		return instance;
	}

	/**
	 * 市域铁路员工
	 */
	@DicItem(name = "市域铁路员工", group = "HolderTypeFlagType")
	public static Integer  STAFF= 1;
	/**
	 * 普通乘客
	 */
	@DicItem(name = "普通乘客", group = "HolderTypeFlagType")
	public static Integer CUSTOMER= 0;
}
