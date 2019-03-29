package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoOdFlowStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Ticket:客流查询Dao
 *
 * @author: xingshaoya
 * create time: 2019-03-22 17:05
 */
@Repository
public interface PassengerDao extends JpaRepository<TmoOdFlowStats,Integer>,
        JpaSpecificationExecutor<TmoOdFlowStats> {

    @Query(value = " select station_id,sum(total_in),sum (total_out),sum (sale_count),sum(add_count) " +
            "from TMO_OD_FLOW_STATS " +
            "where gathering_date=?1 and time_interval_id>=?2 and time_interval_id<= ?3 and " +
            "station_id in (?4) and (ticket_family =?5 or ticket_family is null )" +
            "group by station_id order by station_id",nativeQuery = true)
    Page<Object[]> findAllBarAndPieByConditon(Date gatheringDate, int startTime,
                                        int endTime, List<Long> stationId,
                                        short ticketFamily, Pageable pageable);

    @Query(value = "select t.stationId,t.timeIntervalId," +
            "sum(t.totalIn),sum (t.totalOut),sum (t.saleCount),sum(t.addCount) " +
        "from TmoOdFlowStats t where" +
        " t.gatheringDate=?1 and " +
            "    t.timeIntervalId<=?2 and t.timeIntervalId>=?3 " +
        "and t.stationId in(?4) and (t.ticketFamily =?5 or t.ticketFamily is null ) " +
        "group by t.stationId ,t.timeIntervalId " +
            "order by t.stationId ,t.timeIntervalId ")
    Page<Object[]> findAllSeriesBySeriesCondition(Date gatheringDate, long startTime,
                                            long endTime, List<Long> stationId,
                                            short ticketFamily, Pageable pageable);


    @Query(value = " select station_id,'全部票种',sum(total_in),sum (total_out),sum (sale_count),sum(add_count) " +
            "from TMO_OD_FLOW_STATS " +
            "where gathering_date=?1 and time_interval_id>=?2 and time_interval_id<=?3 and " +
            "station_id in (?4) " +
            "group by station_id " +
            "order by station_id",nativeQuery = true)
    Page<Object[]> findAllByCondition(Date gatheringDate, int startTime,
                                      int endTime, List<Long> stationId
                                      , Pageable pageable);

    @Query(value = " select station_id,ticket_family,sum(total_in),sum (total_out),sum (sale_count),sum(add_count) " +
            "from TMO_OD_FLOW_STATS " +
            "where gathering_date=?1 and time_interval_id>=?2 and time_interval_id<=?3 and " +
            "station_id in (?4) " +
            "group by station_id,ticket_family " +
            "order by station_id,ticket_family",nativeQuery = true)
    Page<Object[]> findAllByCondition2(Date gatheringDate, int startTime,
                                      int endTime, List<Long> stationId, Pageable pageable);

    @Query(value = " select t.lineId,'全部票种',sum(t.totalIn),sum (t.totalOut),sum (t.saleCount),sum(t.addCount) " +
            "from TmoOdFlowStats t " +
            "where t.gatheringDate=?1 and t.timeIntervalId<=?2 and t.timeIntervalId>=?3  " +
            "group by t.lineId " +
            "order by t.lineId")
    Page<Object[]> findAllTotalOD(Date gatheringDate, Long startTime,
                                  Long endTime, Pageable pageable);

    @Query(value = "select t.lineId,t.ticketFamily,sum(t.totalIn),sum (t.totalOut),sum (t.saleCount),sum(t.addCount) " +
            " from TmoOdFlowStats t " +
            "  where t.gatheringDate=?1 and t.timeIntervalId<=?2 and t.timeIntervalId>=?3  " +
            "  group by t.lineId ,t.ticketFamily" +
            "  order by t.lineId ,t.ticketFamily")
    Page<Object[]> findAllTotalOD2(Date gatheringDate, int startTime,
                                  int endTime, Pageable pageable);
}
