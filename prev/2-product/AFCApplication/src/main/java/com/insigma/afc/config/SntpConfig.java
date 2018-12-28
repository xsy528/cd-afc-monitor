/* 
 * 日期：2008-12-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * SNTP配置
 * 
 * @author 廖红自
 */
@XStreamAlias("SntpConfig")
public class SntpConfig extends ConfigurationItem {
	private String ntpServerIp;

	private Integer ntpPort = 123;

	public String getNtpServerIp() {
		return ntpServerIp;
	}

	public void setNtpServerIp(String ntpServerIp) {
		this.ntpServerIp = ntpServerIp;
	}

	public Integer getNtpPort() {
		return ntpPort;
	}

	public void setNtpPort(Integer ntpPort) {
		this.ntpPort = ntpPort;
	}
}
