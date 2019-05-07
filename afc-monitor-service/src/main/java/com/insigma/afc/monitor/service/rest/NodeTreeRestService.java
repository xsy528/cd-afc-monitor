package com.insigma.afc.monitor.service.rest;

import com.insigma.afc.monitor.model.dto.NodeItem;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.model.dto.Result;
import feign.Param;
import feign.RequestLine;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/19 16:01
 */
public interface NodeTreeRestService {

    @RequestLine("POST /topology/tree/monitor?level={level}")
    Result<NodeItem> monitorTree(@Param("level") AFCNodeLevel level);
}
