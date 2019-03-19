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
 * Ticket:  模式更新请求
 * @author  matianming
 *
 */
public class ModeUpdateForm implements Serializable {

	private static final long serialVersionUID = 5281396410401421962L;

	private Long deviceId;

	private Integer modeCode;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getModeCode() {
		return modeCode;
	}

	public void setModeCode(Integer modeCode) {
		this.modeCode = modeCode;
	}

	@Override
	public String toString() {
		return "deviceId=" + deviceId + ", modeCode=" + modeCode;
	}
}
