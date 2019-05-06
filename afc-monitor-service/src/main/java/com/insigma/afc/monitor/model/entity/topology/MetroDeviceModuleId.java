package com.insigma.afc.monitor.model.entity.topology;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * metroDeviceModuleId entity.
 * 
 * @author 廖红自
 */
@Embeddable
public class MetroDeviceModuleId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public MetroDeviceModuleId(){

	}

	public MetroDeviceModuleId(Integer stationId, Long deviceId, Long moduleId) {
		this.stationId = stationId;
		this.deviceId = deviceId;
		this.moduleId = moduleId;
	}

	@Column(name = "STATION_ID", nullable = false, precision = 11)
	private Integer stationId;

	@Column(name = "DEVICE_ID", nullable = false)
	private Long deviceId;

	@Column(name = "MODULE_ID", nullable = false)
	private Long moduleId;

	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

}