package com.insigma.afc.topology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.insigma.afc.topology.model.entity.MetroDevice;
import com.insigma.afc.topology.model.entity.MetroDeviceId;

import java.util.List;
import java.util.Optional;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:14:00
 */
@Repository
public interface MetroDeviceDao extends JpaRepository<MetroDevice, MetroDeviceId> {

    Optional<MetroDevice> findByDeviceId(Long deviceId);

    List<MetroDevice> findByLineId(Short deviceId);

    List<MetroDevice> findByStationId(Integer deviceId);

    @Modifying
    @Query("UPDATE MetroDevice SET lineName=:lineName WHERE lineId=:lineId")
    void updateLineNameByLineId(@Param("lineName")String lineName,@Param("lineId")Short lineId);

    @Modifying
    @Query("UPDATE MetroDevice SET stationName=:stationName WHERE stationId=:stationId")
    void updateStationNameByStationId(@Param("stationName")String stationName,@Param("stationId")Integer stationId);

    void deleteByLineId(Short lineId);

    void deleteByStationId(Integer stationId);

}
