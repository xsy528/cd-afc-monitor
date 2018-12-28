/*
 * 日期：2011-6-1
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 人员性别类型定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = PersonGenderType.class)
public class PersonGenderType extends Definition {

	private static PersonGenderType instance = new PersonGenderType();

	public static PersonGenderType getInstance() {
		return instance;
	}

	@DicItem(name = "女")
	public static int FEMALE = 0x01;

	@DicItem(name = "男")
	public static int MALE = 0x02;

	@DicItem(name = "未知")
	public static int UNKNOWN = 0x03;
}
