/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.topology;

/**
 * 设备状态实体
 * 
 * @author caichunye
 *
 */
public class MetroDeviceStatus {

	private Short lineId;

	private Integer stationId;

	private Long deviceId;

	private int status;

	public Short getLineId() {
		return lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
