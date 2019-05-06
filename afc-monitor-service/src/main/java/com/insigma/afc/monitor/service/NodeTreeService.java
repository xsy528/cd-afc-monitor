package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.NodeItem;
import com.insigma.commons.model.dto.Result;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/19 15:17
 */
public interface NodeTreeService {

    /**
     * 获取监控树
     * @return 监控树
     */
    Result<NodeItem> getMonitorTree();
}
