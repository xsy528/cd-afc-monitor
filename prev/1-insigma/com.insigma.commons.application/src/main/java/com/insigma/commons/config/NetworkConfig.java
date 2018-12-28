/* 
 * 日期：2010-8-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NetworkInfo")
public class NetworkConfig extends ConfigurationItem {

	private Integer lineNo = 0;

	private Integer stationNo;

	private Long nodeNo;

	private Long deviceId;

	private String deviceIdFormat = "%08x";

	private String stationlineIdFormat = "%04x";

	private String lineIdFormat = "%02x";

	private String stationIdFormat = "%02x";

	private String deviceTypeFormat = "%02x";

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

	public String getDeviceIdFormat() {
		return deviceIdFormat;
	}

	public void setDeviceIdFormat(String deviceIdFormat) {
		this.deviceIdFormat = deviceIdFormat;
	}

	public String getStationIdFormat() {
		return stationIdFormat;
	}

	public void setStationIdFormat(String stationIdFormat) {
		this.stationIdFormat = stationIdFormat;
	}

	public String getLineIdFormat() {
		return lineIdFormat;
	}

	public void setLineIdFormat(String lineIdFormat) {
		this.lineIdFormat = lineIdFormat;
	}

	public String getStationlineIdFormat() {
		return stationlineIdFormat;
	}

	public void setStationlineIdFormat(String stationlineIdFormat) {
		this.stationlineIdFormat = stationlineIdFormat;
	}

	public String getDeviceTypeFormat() {
		return deviceTypeFormat;
	}

	public void setDeviceTypeFormat(String deviceTypeFormat) {
		this.deviceTypeFormat = deviceTypeFormat;
	}

}
