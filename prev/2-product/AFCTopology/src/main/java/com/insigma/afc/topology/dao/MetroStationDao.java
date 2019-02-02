package com.insigma.afc.topology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.insigma.afc.topology.model.entity.MetroStation;
import com.insigma.afc.topology.model.entity.MetroStationId;

import java.util.List;
import java.util.Optional;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:13:59
 */
@Repository
public interface MetroStationDao extends JpaRepository<MetroStation, MetroStationId> {

    Optional<MetroStation> findByStationId(Integer stationId);

    List<MetroStation> findByLineId(Short lineId);

    @Modifying
    @Query("UPDATE MetroStation SET lineName=:lineName WHERE lineId=:lineId")
    void updateLineNameByLineId(@Param("lineName") String lineName, @Param("lineId") Short lineId);

    void deleteByLineId(Short lindId);
}
