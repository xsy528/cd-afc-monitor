package com.insigma.acc.monitor.service;

import com.insigma.acc.monitor.model.dto.NodeItem;
import com.insigma.acc.monitor.model.dto.Result;

/**
 * 节点服务
 * author:xuzhemin
 */
public interface NodeTreeService {

    /**
     * 节点管理树
     * @return 包含是否启用的节点树
     */
    Result<NodeItem> getEditorNodeTree();

    /**
     * 监控节点树
     * @return 包含当前状态的节点树
     */
    Result<NodeItem> getMonitorNodeTree();

    /**
     * 生成简单树，设备会根据类型分组
     * @return 节点树
     */
    Result<NodeItem> getNodeGroupDeviceTree();
}
