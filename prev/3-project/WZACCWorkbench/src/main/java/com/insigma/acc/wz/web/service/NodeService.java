package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.model.vo.Result;

/**
 * 节点服务
 * author:xuzhemin
 */
public interface NodeService {

    /**
     * 生产节点树
     * @return 包含节点树的结果
     */
    Result<NodeItem> getNodeTree();

    /**
     * 生成简单树，设备会根据类型分组
     * @return 节点树
     */
    Result<NodeItem> getNodeSimpleTree();
}
