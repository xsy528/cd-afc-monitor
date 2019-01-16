package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeTreeController extends BaseMultiActionController {

    @Autowired
    private NodeService nodeService;

    static {
        methodMapping.put("/node/list/editor","editorTree");
        methodMapping.put("/node/list/monitor","monitorTree");
        methodMapping.put("/node/list/simple","simpleTree");
        methodMapping.put("/node/list/device","deviceTree");
    }

    public NodeTreeController(NodeService nodeService){
        this.nodeService = nodeService;
    }

    @JsonView(NodeItem.editor.class)
    public Result<NodeItem> editorTree(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.monitor.class)
    public Result<NodeItem> monitorTree(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> simpleTree(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> deviceTree(){
        return nodeService.getNodeSimpleTree();
    }

}