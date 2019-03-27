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
    Page<Object[]> findAllBarByConditon(Date gatheringDate, int startTime,
                                        int endTime, List<Integer> stationId,
                                        short ticketFamily, Pageable pageable);

//    List<Object[]> findAllPieByConditon();

//    List<Object[]> findAllSeriesByCondition();
}
