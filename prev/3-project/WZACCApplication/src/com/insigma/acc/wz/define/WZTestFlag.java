/* 
 * 日期：2011-10-8
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
 * Ticket: 测试标记
 * 
 * @author BOND XIE(xiebo@zdwxgd.com)
 */
@Dic(overClass = WZTestFlag.class)
public class WZTestFlag extends Definition {
	private static WZTestFlag instance = new WZTestFlag();

	public static WZTestFlag getInstance() {
		return instance;
	}

	@DicItem(name = "非测试票")
	public static Integer NOTESTICKET = 0;

	@DicItem(name = "测试票")
	public static Integer TESTTICKET = 1;

}
