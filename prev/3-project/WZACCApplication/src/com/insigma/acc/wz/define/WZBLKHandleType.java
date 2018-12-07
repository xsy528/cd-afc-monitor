/* 
 * 日期：2018年3月12日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

//import com.insigma.afc.opt.constant.AFCBLKProcessFlag;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 黑名单处理方式
 * @author chenfuchun
 *
 */
//@Dic(overClass = AFCBLKProcessFlag.class)
public class WZBLKHandleType {

	private static WZBLKHandleType instance = new WZBLKHandleType();

	public static WZBLKHandleType getInstance() {
		return instance;
	}

	@DicItem(name = "不拒绝")
	public static int NORMAL = 0x00;

	@DicItem(name = "仅在出站时拒绝")
	public static int EXIT_REFUSE = 0x01;

	@DicItem(name = "在进站和出站时拒绝")
	public static int ENTRY_EXIT_REFUSE = 0x02;

	@DicItem(name = "仅在出站时拒绝，锁卡并上送交易")
	public static int EXIT_REFUSE_LOCK = 0x03;

	@DicItem(name = "在进站和出站时拒绝，锁卡并上送交易")
	public static int ENTRY_EXIT_REFUSE_LOCK = 0x04;

}
