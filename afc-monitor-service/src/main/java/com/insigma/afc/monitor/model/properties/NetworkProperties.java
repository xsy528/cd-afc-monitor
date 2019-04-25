/* 
 * 日期：2010-8-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ticket: app配置
 * @author xuzhemin
 */
@ConfigurationProperties(prefix = "info.app")
public class NetworkProperties {

	private Integer lineNo = 0;

	private Integer stationNo;

	private Long nodeNo;

	private Long deviceId;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public Integer getStationNo() {
		return stationNo;
	}

	public void setStationNo(Integer stationNo) {
		this.stationNo = stationNo;
	}

	public Long getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(Long nodeNo) {
		this.nodeNo = nodeNo;
	}

}
