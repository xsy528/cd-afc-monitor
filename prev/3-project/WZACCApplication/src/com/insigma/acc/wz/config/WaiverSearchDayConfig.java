/* 
 * 日期：2011-10-27
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Ticket: 模式履历计划任务生成查询天数
 * 
 * @author BOND XIE(xiebo@zdwxgd.com)
 */
@XStreamAlias("WaiverSearchDayConfig")
public class WaiverSearchDayConfig extends ConfigurationItem {

	private String waiverSearchDay;

	/**
	 * @return the waiverSearchDay
	 */
	public String getWaiverSearchDay() {
		return waiverSearchDay;
	}

	/**
	 * @param waiverSearchDay
	 *            the waiverSearchDay to set
	 */
	public void setWaiverSearchDay(String waiverSearchDay) {
		this.waiverSearchDay = waiverSearchDay;
	}

}
