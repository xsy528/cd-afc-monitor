/* 
 * 日期：2018年4月25日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.constant;

/**
 * Ticket: 参数版本性质（0：非读写器上版本；1：读写器上版本；2：所有版本）
 * @author chenfuchun
 *
 */
public class WZVersionQueryType {

	public static final Integer EOD_FROM_SYSTEM = 0;

	public static final Integer EOD_FROM_READER = 1;

	public static final Integer EOD_FROM_SYSTEM_AND_READER = 2;

}
