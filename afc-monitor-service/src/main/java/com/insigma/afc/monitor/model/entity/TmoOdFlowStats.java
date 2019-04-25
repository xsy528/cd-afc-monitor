package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Ticket:
 *
 * @author xingshaoya
 * create time: 2019-03-27 17:54
 */
@Entity
@Table(name = "TMO_OD_FLOW_STATS")
@IdClass(TmoOdFlowStatsId.class)
public class TmoOdFlowStats implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LINE_ID", length = 5, nullable = false)
    private Short lineId;

    @Id
    @Column(name = "STATION_ID", length = 11, nullable = false)
    private Integer stationId;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "GATHERING_DATE", length = 7, nullable = false)
    private Date gatheringDate;

    @Id
    @Column(name = "TIME_INTERVAL_ID", length = 19, nullable = false)
    private Integer timeIntervalId;

    @Id
    @Column(name = "TICKET_FAMILY", length = 5, nullable = false)
    private Short ticketFamily;

    @Column(name = "UP_IN_HEAD_COUNT", length = 11)
    private Integer upInHeadCount;

    @Column(name = "DOWN_IN_HEAD_COUNT", length = 11)
    private Integer downInHeadCount;

    @Column(name = "UP_OUT_HEAD_COUNT", length = 11)
    private Integer upOutHeadCount;

    @Column(name = "DOWN_OUT_HEAD_COUNT", length = 11)
    private Integer downOutHeadCount;

    @Column(name = "add_count", length = 11, nullable = false)
    private Integer addCount;

    @Column(name = "sale_count", length = 11, nullable = false)
    private Integer saleCount;

    @Column(name = "total_in", length = 11)
    private Integer totalIn;

    @Column(name = "total_out", length = 11)
    private Integer totalOut;

    @Column(name = "ref_date_time")
    private Timestamp refDateTime;

    // Constructors

    /** default constructor */
    public TmoOdFlowStats() {
    }

    /** minimal constructor */
    public TmoOdFlowStats( Integer addCount,
                          Integer saleCount) {
        this.addCount = addCount;
        this.saleCount = saleCount;
    }

    /** full constructor */
    public TmoOdFlowStats(Integer upInHeadCount,
                          Integer downInHeadCount, Integer upOutHeadCount,
                          Integer downOutHeadCount) {
        this.upInHeadCount = upInHeadCount;
        this.downInHeadCount = downInHeadCount;
        this.upOutHeadCount = upOutHeadCount;
        this.downOutHeadCount = downOutHeadCount;
    }

    /** full constructor */
    public TmoOdFlowStats(Integer upInHeadCount,
                          Integer downInHeadCount, Integer upOutHeadCount,
                          Integer downOutHeadCount, Integer addCount, Integer saleCount,
                          Integer totalIn, Integer totalOut, Timestamp refDateTime) {
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

    public TmoOdFlowStats(Integer totalIn, Integer totalOut,Integer saleCount,Integer addCount, Integer stationId) {
        this.addCount = addCount;
        this.saleCount = saleCount;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.stationId = stationId;
    }

    public TmoOdFlowStats(Integer totalIn, Integer totalOut,Integer saleCount,Integer addCount, Integer stationId,
                          Integer timeIntervalId) {
        this.addCount = addCount;
        this.saleCount = saleCount;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.stationId = stationId;
        this.timeIntervalId = timeIntervalId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getTimeIntervalId() {
        return timeIntervalId;
    }

    public void setTimeIntervalId(Integer timeIntervalId) {
        this.timeIntervalId = timeIntervalId;
    }

    public Date getGatheringDate() {
        return gatheringDate;
    }

    public void setGatheringDate(Date gatheringDate) {
        this.gatheringDate = gatheringDate;
    }


    public Short getTicketFamily() {
        return ticketFamily;
    }

    public void setTicketFamily(Short ticketFamily) {
        this.ticketFamily = ticketFamily;
    }

    public Integer getUpInHeadCount() {
        return this.upInHeadCount;
    }

    public void setUpInHeadCount(Integer upInHeadCount) {
        this.upInHeadCount = upInHeadCount;
    }

    public Integer getDownInHeadCount() {
        return this.downInHeadCount;
    }

    public void setDownInHeadCount(Integer downInHeadCount) {
        this.downInHeadCount = downInHeadCount;
    }

    public Integer getUpOutHeadCount() {
        return this.upOutHeadCount;
    }

    public void setUpOutHeadCount(Integer upOutHeadCount) {
        this.upOutHeadCount = upOutHeadCount;
    }

    public Integer getDownOutHeadCount() {
        return this.downOutHeadCount;
    }

    public void setDownOutHeadCount(Integer downOutHeadCount) {
        this.downOutHeadCount = downOutHeadCount;
    }

    public Integer getAddCount() {
        return this.addCount;
    }

    public void setAddCount(Integer addCount) {
        this.addCount = addCount;
    }

    public Integer getSaleCount() {
        return this.saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getTotalIn() {
        return this.totalIn;
    }

    public void setTotalIn(Integer totalIn) {
        this.totalIn = totalIn;
    }

    public Integer getTotalOut() {
        return this.totalOut;
    }

    public void setTotalOut(Integer totalOut) {
        this.totalOut = totalOut;
    }

    public Timestamp getRefDateTime() {
        return this.refDateTime;
    }

    public void setRefDateTime(Timestamp refDateTime) {
        this.refDateTime = refDateTime;
    }

    public Short getLineId() {
        return lineId;
    }

    public void setLineId(Short lineId) {
        this.lineId = lineId;
    }
}
