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

    @RequestLine("GET /topology/query/getMetroAcc")
    Result<MetroACC> getMetroAcc();

    @RequestLine("GET /topology/query/getDeviceNode?deviceId={deviceId}")
    Result<MetroDevice> getDeviceNode(@Param("deviceId") Long deviceId);

    @RequestLine("GET /topology/query/getNode?nodeId={nodeId}")
    Result<MetroNode> getNode(@Param("nodeId") Long nodeId);

    @RequestLine("GET /topology/query/getNodeText?nodeId={nodeId}")
    Result<String> getNodeText(@Param("nodeId") Long nodeId);

    @RequestLine("GET /topology/query/getAllMetroLine")
    Result<List<MetroLine>> getAllMetroLine();

    @RequestLine("GET /topology/query/getMetroStationsGroupByLineId")
    Result<Map<Short,List<MetroStation>>> getMetroStationsGroupByLineId();

    @RequestLine("GET /topology/query/getMetroDevicesGroupByStationId")
    Result<Map<Integer,List<MetroDevice>>> getMetroDevicesGroupByStationId();

    @RequestLine("GET /topology/query/getMetroStationsByLineId?lineId={lineId}")
    Result<List<MetroStation>> getMetroStationsByLineId(@Param("lineId")Short lineId);

    @RequestLine("GET /topology/query/getMetroDeviceByStationId?stationId={stationId}")
    Result<List<MetroDevice>> getMetroDeviceByStationId(@Param("stationId")Integer stationId);

}
