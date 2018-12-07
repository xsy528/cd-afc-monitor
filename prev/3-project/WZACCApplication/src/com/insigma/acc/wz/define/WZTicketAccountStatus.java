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
 * Ticket: 票卡账户状态定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = WZTicketAccountStatus.class)
public class WZTicketAccountStatus extends Definition {

	private static WZTicketAccountStatus instance = new WZTicketAccountStatus();

	public static WZTicketAccountStatus getInstance() {
		return instance;
	}

	/**
	 * 票卡在ES制卡成功后，卡账户状态为：初始化；<br>
	 * 票卡发售成功后，卡账户：正常； <br>
	 * 卡在退票或挂失批准后，卡账户:黑卡；<br>
	 * 卡退出使用：卡账户：注销； <br>
	 * 卡解挂或重新启用，卡账户：正常。
	 */
	@DicItem(name = "初始化")
	public static int INIT = 0x01;

	@DicItem(name = "正常")
	public static int NORMAL = 0x02;

	@DicItem(name = "黑卡")
	public static int BLACK = 0x03;

	@DicItem(name = "注销")
	public static int CANCEL = 0x05;

}
