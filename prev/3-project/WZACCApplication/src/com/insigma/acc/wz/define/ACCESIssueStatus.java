/* 
 * 日期：2018年9月18日
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
 * Ticket: 发卡状态
 * @author chenfuchun
 *
 */
@Dic(overClass = ACCESIssueStatus.class)
public class ACCESIssueStatus extends Definition {

	private static ACCESIssueStatus instance = new ACCESIssueStatus();

	public static ACCESIssueStatus getInstance() {
		return instance;
	}

	@DicItem(name = "未处理")
	public static Short undeal = 0;

	@DicItem(name = "已处理")
	public static Short deal = 1;

}
