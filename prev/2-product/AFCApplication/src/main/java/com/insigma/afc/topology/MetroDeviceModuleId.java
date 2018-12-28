package com.insigma.afc.topology;

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

	private Integer stationId;

	private Long deviceId;

	private Long moduleId;

	// Constructors

	/** default constructor */
	public MetroDeviceModuleId() {
	}

	public MetroDeviceModuleId(MetroDeviceModuleId id) {
		this.stationId = id.stationId;
		this.deviceId = id.deviceId;
		this.moduleId = id.moduleId;
	}

	/** full constructor */
	public MetroDeviceModuleId(Integer stationId, Long deviceId, Long moduleId) {
		this.stationId = stationId;
		this.deviceId = deviceId;
		this.moduleId = moduleId;
	}

	// Property accessors

	@Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0)
	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	@Column(name = "DEVICE_ID", nullable = false, scale = 0)
	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "MODULE_ID", nullable = false, scale = 0)
	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MetroDeviceModuleId))
			return false;
		MetroDeviceModuleId castOther = (MetroDeviceModuleId) other;

		return ((this.getStationId() == castOther.getStationId()) || (this.getStationId() != null
				&& castOther.getStationId() != null && this.getStationId().equals(castOther.getStationId())))
				&& ((this.getDeviceId() == castOther.getDeviceId()) || (this.getDeviceId() != null
						&& castOther.getDeviceId() != null && this.getDeviceId().equals(castOther.getDeviceId())))
				&& ((this.getModuleId() == castOther.getModuleId()) || (this.getModuleId() != null
						&& castOther.getModuleId() != null && this.getModuleId().equals(castOther.getModuleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getStationId() == null ? 0 : this.getStationId().hashCode());
		result = 37 * result + (getDeviceId() == null ? 0 : this.getDeviceId().hashCode());
		result = 37 * result + (getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

}