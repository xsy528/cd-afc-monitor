package com.insigma.afc.odmonitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TmoOdFlowStatsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class TmoOdFlowStatsId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Short lineId;

	private Integer stationId;

	private Date gatheringDate;

	private Integer timeIntervalId;

	private Short ticketFamily;

	// Constructors

	/** default constructor */
	public TmoOdFlowStatsId() {
	}

	/** full constructor */
	public TmoOdFlowStatsId(Short lineId, Integer stationId, Date gatheringDate, Integer timeIntervalId,
			Short ticketFamily) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.gatheringDate = gatheringDate;
		this.timeIntervalId = timeIntervalId;
		this.ticketFamily = ticketFamily;
	}

	// Property access

	@Column(name = "line_id", length = 5, nullable = false)
	public Short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "station_id", length = 11, nullable = false)
	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "gathering_date", nullable = false, length = 11)
	public Date getGatheringDate() {
		return this.gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}

	@Column(name = "time_interval_id", length = 20, nullable = false)
	public Integer getTimeIntervalId() {
		return this.timeIntervalId;
	}

	public void setTimeIntervalId(Integer timeIntervalId) {
		this.timeIntervalId = timeIntervalId;
	}

	@Column(name = "TICKET_FAMILY", length = 5, nullable = false)
	public Short getTicketFamily() {
		return this.ticketFamily;
	}

	public void setTicketFamily(Short ticketFamily) {
		this.ticketFamily = ticketFamily;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TmoOdFlowStatsId))
			return false;
		TmoOdFlowStatsId castOther = (TmoOdFlowStatsId) other;

		return ((this.getLineId() == castOther.getLineId()) || (this.getLineId() != null
				&& castOther.getLineId() != null && this.getLineId().equals(castOther.getLineId())))
				&& ((this.getStationId() == castOther.getStationId()) || (this.getStationId() != null
						&& castOther.getStationId() != null && this.getStationId().equals(castOther.getStationId())))
				&& ((this.getGatheringDate() == castOther.getGatheringDate())
						|| (this.getGatheringDate() != null && castOther.getGatheringDate() != null
								&& this.getGatheringDate().equals(castOther.getGatheringDate())))
				&& ((this.getTimeIntervalId() == castOther.getTimeIntervalId())
						|| (this.getTimeIntervalId() != null && castOther.getTimeIntervalId() != null
								&& this.getTimeIntervalId().equals(castOther.getTimeIntervalId())))
				&& ((this.getTicketFamily() == castOther.getTicketFamily())
						|| (this.getTicketFamily() != null && castOther.getTicketFamily() != null
								&& this.getTicketFamily().equals(castOther.getTicketFamily())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLineId() == null ? 0 : this.getLineId().hashCode());
		result = 37 * result + (getStationId() == null ? 0 : this.getStationId().hashCode());
		result = 37 * result + (getGatheringDate() == null ? 0 : this.getGatheringDate().hashCode());
		result = 37 * result + (getTimeIntervalId() == null ? 0 : this.getTimeIntervalId().hashCode());
		result = 37 * result + (getTicketFamily() == null ? 0 : this.getTicketFamily().hashCode());
		return result;
	}

}