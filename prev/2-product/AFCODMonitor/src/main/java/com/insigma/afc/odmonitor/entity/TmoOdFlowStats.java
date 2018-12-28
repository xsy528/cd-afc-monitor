package com.insigma.afc.odmonitor.entity;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TmoOdFlowStats entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tmo_od_flow_stats")
public class TmoOdFlowStats implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private TmoOdFlowStatsId id;

	private Integer upInHeadCount;

	private Integer downInHeadCount;

	private Integer upOutHeadCount;

	private Integer downOutHeadCount;

	private Integer addCount;

	private Integer saleCount;

	private Integer totalIn;

	private Integer totalOut;

	private Timestamp refDateTime;

	// Constructors

	/** default constructor */
	public TmoOdFlowStats() {
	}

	/** minimal constructor */
	public TmoOdFlowStats(TmoOdFlowStatsId id, Integer addCount, Integer saleCount) {
		this.id = id;
		this.addCount = addCount;
		this.saleCount = saleCount;
	}

	/** full constructor */
	public TmoOdFlowStats(TmoOdFlowStatsId id, Integer upInHeadCount, Integer downInHeadCount, Integer upOutHeadCount,
			Integer downOutHeadCount) {
		this.id = id;
		this.upInHeadCount = upInHeadCount;
		this.downInHeadCount = downInHeadCount;
		this.upOutHeadCount = upOutHeadCount;
		this.downOutHeadCount = downOutHeadCount;
	}

	/** full constructor */
	public TmoOdFlowStats(TmoOdFlowStatsId id, Integer upInHeadCount, Integer downInHeadCount, Integer upOutHeadCount,
			Integer downOutHeadCount, Integer addCount, Integer saleCount, Integer totalIn, Integer totalOut,
			Timestamp refDateTime) {
		this.id = id;
		this.upInHeadCount = upInHeadCount;
		this.downInHeadCount = downInHeadCount;
		this.upOutHeadCount = upOutHeadCount;
		this.downOutHeadCount = downOutHeadCount;
		this.addCount = addCount;
		this.saleCount = saleCount;
		this.totalIn = totalIn;
		this.totalOut = totalOut;
		this.refDateTime = refDateTime;
	}

	// Property access
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "lineId", column = @Column(name = "line_id", length = 5, nullable = false) ),
			@AttributeOverride(name = "stationId", column = @Column(name = "station_id", length = 11, nullable = false) ),
			@AttributeOverride(name = "gatheringDate", column = @Column(name = "gathering_date", nullable = false, length = 11) ),
			@AttributeOverride(name = "timeIntervalId", column = @Column(name = "time_interval_id", length = 20, nullable = false) ),
			@AttributeOverride(name = "ticketFamily", column = @Column(name = "TICKET_FAMILY", length = 5, nullable = false) ) })
	public TmoOdFlowStatsId getId() {
		return this.id;
	}

	public void setId(TmoOdFlowStatsId id) {
		this.id = id;
	}

	@Column(name = "UP_IN_HEAD_COUNT", length = 11)
	public Integer getUpInHeadCount() {
		return this.upInHeadCount;
	}

	public void setUpInHeadCount(Integer upInHeadCount) {
		this.upInHeadCount = upInHeadCount;
	}

	@Column(name = "DOWN_IN_HEAD_COUNT", length = 11)
	public Integer getDownInHeadCount() {
		return this.downInHeadCount;
	}

	public void setDownInHeadCount(Integer downInHeadCount) {
		this.downInHeadCount = downInHeadCount;
	}

	@Column(name = "UP_OUT_HEAD_COUNT", length = 11)
	public Integer getUpOutHeadCount() {
		return this.upOutHeadCount;
	}

	public void setUpOutHeadCount(Integer upOutHeadCount) {
		this.upOutHeadCount = upOutHeadCount;
	}

	@Column(name = "DOWN_OUT_HEAD_COUNT", length = 11)
	public Integer getDownOutHeadCount() {
		return this.downOutHeadCount;
	}

	public void setDownOutHeadCount(Integer downOutHeadCount) {
		this.downOutHeadCount = downOutHeadCount;
	}

	@Column(name = "add_count", length = 11, nullable = false)
	public Integer getAddCount() {
		return this.addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	@Column(name = "sale_count", length = 11, nullable = false)
	public Integer getSaleCount() {
		return this.saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	@Column(name = "total_in", length = 11)
	public Integer getTotalIn() {
		return this.totalIn;
	}

	public void setTotalIn(Integer totalIn) {
		this.totalIn = totalIn;
	}

	@Column(name = "total_out", length = 11)
	public Integer getTotalOut() {
		return this.totalOut;
	}

	public void setTotalOut(Integer totalOut) {
		this.totalOut = totalOut;
	}

	@Column(name = "ref_date_time")
	public Timestamp getRefDateTime() {
		return this.refDateTime;
	}

	public void setRefDateTime(Timestamp refDateTime) {
		this.refDateTime = refDateTime;
	}

}