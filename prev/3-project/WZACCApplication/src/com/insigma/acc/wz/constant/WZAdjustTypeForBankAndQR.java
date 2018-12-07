/* 
 * 日期：2017年11月23日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.constant;

/**
 * Ticket: 互联网票调整类型
 * @author  chenhangwen
 *
 */
public class WZAdjustTypeForBankAndQR {

	/**
	 * 强制扣款系统接受
	 */
	public static final short INTER_ADJUST_ACCEPT_FORCE_PAY = 1;

	/**
	 * 超过30天强制清分
	 */
	public static final short INTER_ADJUST_ACCEPT_OVERDUE = 2;

	/**
	 * app扣款系统接受
	 */
	public static final short INTER_ADJUST_ACCEPT_DREAMWORKS = 3;

	/**
	 * 系统拒付
	 */
	public static final short INTER_ADJUST_SYS_REJECT = 4;

	/**
	 * 可疑交易
	 */
	public static final short INTER_ADJUST_DOUBT = 5;

	/**
	 * 手动调整接受
	 */
	public static final short INTER_ADJUST_MANUAL_ACCEPT = 6;

	/**
	 * 手动调整拒付
	 */
	public static final short INTER_ADJUST_MANUAL_REJECT = 7;

}
