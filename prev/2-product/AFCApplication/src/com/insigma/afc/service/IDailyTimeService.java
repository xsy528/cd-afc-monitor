/* 
 * 日期：2011-2-15
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.service;

import java.util.Date;

import com.insigma.commons.application.IService;

public interface IDailyTimeService extends IService {

	public static final String YPT_CC = "YPT_CC";

	public static final String YKT_CC = "YKT_CC";

	public static final String BANK_CC = "BANK_CC";

	public static final String QR_CC = "QR_CC";

	String getYPTSettTransDetailTableName();

	String getYKTSettTransDetailTableName();

	Date getYPTSettDay();

	Date getYKTSettDay();

	Date getBANKSettDay();

	Date getQRSettDay();

	Date getYPTBusiDay();

	Date getYKTBusiDay();

	java.sql.Date getBusDay();

	/**
	 * 获取当前结算日：tsy_config表中结算日加1
	 * @param taskGroup
	 * @return
	 */
	Date getCurrSettDay(String taskGroup);

	/**
	 * 获取最近结算日：tsy_config表中结算日
	 * @param taskGroup
	 * @return
	 */
	java.sql.Date getLastSettDay(String taskGroup);

	/**
	 * 获取上一个运营日
	 * @param taskGroup
	 * @return
	 */
	java.sql.Date getLastBusiDay();

	String getBusiStartTime();
}
