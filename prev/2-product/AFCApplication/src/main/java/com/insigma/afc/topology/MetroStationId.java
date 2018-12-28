package com.insigma.afc.topology;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.insigma.afc.topology.convertor.MetroNodeConvertor;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;

/**
 * TmetroStationId entity.
 * 
 * @author 廖红自
 */
@Embeddable
public class MetroStationId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@View(label = "线路编号", modify = false, regexp = "\\d{0,4}")
	@ColumnView(name = "线路编号", convertor = MetroNodeConvertor.class)
	private Short lineId;

	@View(label = "车站编号", regexp = "\\d{0,4}")
	@ColumnView(name = "车站编号", convertor = MetroNodeConvertor.class)
	private Integer stationId;

	// Constructors

	/** default constructor */
	public MetroStationId() {
	}

	/** full constructor */
	public MetroStationId(Short lineId, Integer stationId) {
		this.lineId = lineId;
		this.stationId = stationId;
	}

	public MetroStationId(MetroStationId id) {
		this.lineId = id.lineId;
		this.stationId = id.stationId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MetroStationId))
			return false;
		MetroStationId castOther = (MetroStationId) other;

		return ((this.getLineId() == castOther.getLineId()) || (this.getLineId() != null
				&& castOther.getLineId() != null && this.getLineId().equals(castOther.getLineId())))
				&& ((this.getStationId() == castOther.getStationId()) || (this.getStationId() != null
						&& castOther.getStationId() != null && this.getStationId().equals(castOther.getStationId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLineId() == null ? 0 : this.getLineId().hashCode());
		result = 37 * result + (getStationId() == null ? 0 : this.getStationId().hashCode());
		return result;
	}

}