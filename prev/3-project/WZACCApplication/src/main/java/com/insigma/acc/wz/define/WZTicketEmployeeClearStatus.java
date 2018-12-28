/* 
 * 日期：2018年5月29日
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
 * Ticket: 员工票申请记录处理标志
 * @author chenfuchun
 *
 */
@Dic(overClass = WZTicketEmployeeClearStatus.class)
public class WZTicketEmployeeClearStatus extends Definition {

	private static WZTicketEmployeeClearStatus instance = new WZTicketEmployeeClearStatus();

	public static WZTicketEmployeeClearStatus getInstance() {
		return instance;
	}

	@DicItem(name = "清除")
	public static int CLEAR = 0x00;

	@DicItem(name = "添加")
	public static int ADD = 0x01;

}
