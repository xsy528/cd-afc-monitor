package com.insigma.afc.monitor.service.rest;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.*;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-02-20 14:02
 */
public interface TopologyService {

    @RequestLine("GET /topology/getMetroAcc")
    Result<MetroACC> getMetroAcc();

    @RequestLine("GET /topology/getDeviceNode?deviceId={deviceId}")
    Result<MetroDevice> getDeviceNode(@Param("deviceId") Long deviceId);

    @RequestLine("GET /topology/getNode?nodeId={nodeId}")
    Result<MetroNode> getNode(@Param("nodeId") Long nodeId);

    @RequestLine("GET /topology/getNodeText?nodeId={nodeId}")
    Result<String> getNodeText(@Param("nodeId") Long nodeId);

    @RequestLine("GET /topology/getAllMetroLine")
    Result<List<MetroLine>> getAllMetroLine();

    @RequestLine("GET /topology/getMetroStationsGroupByLineId")
    Result<Map<Short,List<MetroStation>>> getMetroStationsGroupByLineId();

    @RequestLine("GET /topology/getMetroDevicesGroupByStationId")
    Result<Map<Integer,List<MetroDevice>>> getMetroDevicesGroupByStationId();

    @RequestLine("GET /topology/getMetroStationsByLineId?lineId={lineId}")
    Result<List<MetroStation>> getMetroStationsByLineId(@Param("lineId")Short lineId);

    @RequestLine("GET /topology/getMetroDeviceByStationId?stationId={stationId}")
    Result<List<MetroDevice>> getMetroDeviceByStationId(@Param("stationId")Integer stationId);

}
