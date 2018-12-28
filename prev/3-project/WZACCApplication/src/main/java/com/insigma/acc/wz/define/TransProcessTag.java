/* 
 * 日期：2017年10月31日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: 交易处理情况标记
 * @author chenfuchun
 *
 */
public class TransProcessTag {

	@View(label = "未处理", type = "processTag")
	public static final Integer UNPROCESSED = 0;

	@View(label = "已处理", type = "processTag")
	public static final Integer PROCESSED = 1;

}
