/* 
 * 日期：2018年1月15日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

//import com.insigma.afc.opt.constant.AFCBLKTicketRegex;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州黑名单正则
 * @author chenfuchun
 *
 */
//@Dic(overClass = AFCBLKTicketRegex.class)
public class WZACCBLKTicketRegex {

	private static WZACCBLKTicketRegex instance = new WZACCBLKTicketRegex();

	public static WZACCBLKTicketRegex getInstance() {
		return instance;
	}

	@DicItem(name = "[0-9]{16}", group = "blkregex")
	public static Integer BLK_CCHS_FULL = 0x41;

	@DicItem(name = "[0-9]{16}", group = "blkregex")
	public static Integer BLK_CCHS_INCR = 0x42;

	@DicItem(name = "[0-9]{16}", group = "blkregex")
	public static Integer BLK_CCHS_RANGE = 0x43;

	@DicItem(name = "[0-9]{16}", group = "blkregex")
	public static Integer BLK_YKT_FULL = 0x44;

	@DicItem(name = "[0-9]{16}", group = "blkregex")
	public static Integer BLK_STAFF = 0x45;

}
