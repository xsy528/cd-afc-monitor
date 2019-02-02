package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-28:18:55
 */
@Repository
public interface TmoItemStatusDao extends JpaRepository<TmoItemStatus, Long>, JpaSpecificationExecutor<TmoItemStatus> {

    TmoItemStatus findByNodeIdAndModeChangeTimeAfter(Long nodeId,Date modeChangeTime);

    List<TmoItemStatus> findByStationIdAndNodeType(Integer stationId, Short nodeType);

    TmoItemStatus findTopByLineIdAndStationIdAndNodeId(Short lineId, Integer stationId, Long nodeId);

    List<TmoItemStatus> findByLineIdAndStationIdAndNodeId(Short lineId, Integer stationId, Long nodeId);

    List<TmoItemStatus> findByLineIdAndStationIdAndNodeType(Short lineId, Integer stationId, Short nodeType);

    List<TmoItemStatus> findByNodeTypeGreaterThanOrderByNodeIdAsc(Short nodeType);

    List<TmoItemStatus> findByNodeTypeAndStationIdNotOrderByNodeIdAsc(Short nodeType,Integer stationId);

    @Modifying
    @Query("UPDATE TmoItemStatus SET itemActivity=:itemActivity,updateTime=:updateTime WHERE nodeType>:nodeType")
    void updateACC(@Param("itemActivity") Boolean itemActivity, @Param("updateTime") Date updateTime,
                   @Param("nodeType") Short nodeType);

    @Modifying
    @Query("UPDATE TmoItemStatus SET itemActivity=:itemActivity,updateTime=:updateTime WHERE lineId=:lineId and " +
            "nodeType>:nodeType")
    void updateLC(@Param("itemActivity") Boolean itemActivity, @Param("updateTime") Date updateTime,
                  @Param("lineId") Short lineId, @Param("nodeType") Short nodeType);

    @Modifying
    @Query("UPDATE TmoItemStatus SET itemActivity=:itemActivity,updateTime=:updateTime WHERE lineId=:lineId and " +
            "stationId=:stationId AND nodeType>:nodeType")
    void updateSC(@Param("itemActivity") Boolean itemActivity, @Param("updateTime") Date updateTime,
                  @Param("lineId") Short lineId, @Param("stationId") Integer stationId,
                  @Param("nodeType") Short nodeType);
}
