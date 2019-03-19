/* 
 * 日期：2019年3月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.xz.rmi;

import java.io.Serializable;

/**
 * Ticket:  参数版本查询请求
 * @author  matianming
 *
 */
public class EODVersionForm implements Serializable {

	private static final long serialVersionUID = 4769280320739764888L;

	private Long deviceId;

	private Short level;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "deviceId=" + deviceId + ", level=" + level;
	}
}
