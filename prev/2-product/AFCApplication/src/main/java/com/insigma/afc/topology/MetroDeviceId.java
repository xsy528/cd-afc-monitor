package com.insigma.afc.topology;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.insigma.afc.topology.convertor.MetroNodeConvertor;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;

/**
 * TmetroDeviceId entity.
 * 
 * @author 廖红自
 */
@Embeddable
public class MetroDeviceId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@View(label = "线路编号", regexp = "\\d{0,4}", modify = false)
	@ColumnView(name = "线路编号", convertor = MetroNodeConvertor.class)
	private Short lineId;

	@View(label = "车站编号", regexp = "\\d{0,4}", modify = false)
	@ColumnView(name = "线路编号", convertor = MetroNodeConvertor.class)
	private Integer stationId;

	@View(label = "设备编号", regexp = "[0-9]{0,9}")
	@ColumnView(name = "线路编号", convertor = MetroNodeConvertor.class)
	//	, format = "%03d"
	private Long deviceId;

	// Constructors

	/** default constructor */
	public MetroDeviceId() {
	}

	/** full constructor */
	public MetroDeviceId(Short lineId, Integer stationId, Long deviceId) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.deviceId = deviceId;
	}

	/** full constructor */
	public MetroDeviceId(MetroStationId sid, Long deviceId) {
		this(sid.getLineId(), sid.getStationId(), deviceId);
	}

	public MetroDeviceId(MetroDeviceId id) {
		this.lineId = id.lineId;
		this.stationId = id.stationId;
		this.deviceId = id.deviceId;
	}

	// Property accessors

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof MetroDeviceId)) {
			return false;
		}
		MetroDeviceId castOther = (MetroDeviceId) other;

		return ((this.getLineId() == castOther.getLineId()) || (this.getLineId() != null
				&& castOther.getLineId() != null && this.getLineId().equals(castOther.getLineId())))
				&& ((this.getStationId() == castOther.getStationId()) || (this.getStationId() != null
						&& castOther.getStationId() != null && this.getStationId().equals(castOther.getStationId())))
				&& ((this.getDeviceId() == castOther.getDeviceId()) || (this.getDeviceId() != null
						&& castOther.getDeviceId() != null && this.getDeviceId().equals(castOther.getDeviceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLineId() == null ? 0 : this.getLineId().hashCode());
		result = 37 * result + (getStationId() == null ? 0 : this.getStationId().hashCode());
		result = 37 * result + (getDeviceId() == null ? 0 : this.getDeviceId().hashCode());
		return result;
	}

}