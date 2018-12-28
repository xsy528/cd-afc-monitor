/* 
 * 日期：2010-9-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.form;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Ticket: 车站模式列表
 * @author  wangzezhi
 *
 */
public class WZModeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int stationId;

	private int modeCode;

	private Date modeOccurTime;

	public WZModeInfo() {

	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getModeCode() {
		return modeCode;
	}

	public void setModeCode(int modeCode) {
		this.modeCode = modeCode;
	}

	public Date getModeOccurTime() {
		return modeOccurTime;
	}

	public void setModeOccurTime(Date modeOccurTime) {
		this.modeOccurTime = modeOccurTime;
	}

}
