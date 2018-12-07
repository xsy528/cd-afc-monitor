/* 
 * 日期：2011-11-4
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * SAM卡操作状态 领用-0; 退还-1; 注销-2 ; 登记-3;Ticket:
 * 
 * @author shenchao
 */
@Dic(overClass = WZACCSamCardOperateType.class)
public class WZACCSamCardOperateType extends Definition {

	public static WZReaderCode instance = new WZReaderCode();

	public static WZReaderCode getInstance() {
		return instance;
	}

	@DicItem(name = "登记", index = 0)
	public static Integer SAM_ENROLMENT = 0x03;

	@DicItem(name = "领用", index = 1)
	public static Integer SAM_USE = 0x00;

	@DicItem(name = "退还", index = 2)
	public static Integer SAM_RETURN = 0x01;

	@DicItem(name = "注销", index = 3)
	public static Integer SAM_DESTROY = 0x02;

}
