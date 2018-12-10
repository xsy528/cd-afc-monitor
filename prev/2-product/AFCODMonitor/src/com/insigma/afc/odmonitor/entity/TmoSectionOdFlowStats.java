package com.insigma.afc.odmonitor.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TmoSectionOdFlowStats generated by MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMO_SECTION_OD_FLOW_STATS")
public class TmoSectionOdFlowStats implements java.io.Serializable {

	private static final long serialVersionUID = 1994886518071062439L;

	private TmoSectionOdFlowStatsId id;

	private Long upCount;

	private Long downCount;

	private Long totalCount;

	// Constructors

	/** default constructor */
	public TmoSectionOdFlowStats() {
	}

	/** full constructor */
	public TmoSectionOdFlowStats(TmoSectionOdFlowStatsId id, Long upCount, Long downCount, Long totalCount) {
		this.id = id;
		this.upCount = upCount;
		this.downCount = downCount;
		this.totalCount = totalCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "sectionId", column = @Column(name = "SECTION_ID", length = 5, nullable = false) ),
			@AttributeOverride(name = "gatheringDate", column = @Column(name = "GATHERING_DATE", length = 11, nullable = false) ),
			@AttributeOverride(name = "timeIntervalId", column = @Column(name = "TIME_INTERVAL_ID", length = 11, nullable = false) ) })
	public TmoSectionOdFlowStatsId getId() {
		return this.id;
	}

	public void setId(TmoSectionOdFlowStatsId id) {
		this.id = id;
	}

	@Column(name = "UP_COUNT", length = 20)
	public Long getUpCount() {
		return this.upCount;
	}

	public void setUpCount(Long upCount) {
		this.upCount = upCount;
	}

	@Column(name = "DOWN_COUNT", length = 20)
	public Long getDownCount() {
		return this.downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	@Column(name = "TOTAL_COUNT", length = 20)
	public Long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}