package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.model.vo.Result;

public interface NodeService {

    Result<NodeItem> getNodeTree();
}
