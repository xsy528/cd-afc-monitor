package com.insigma.acc.monitor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.acc.monitor.model.dto.NodeItem;
import com.insigma.acc.monitor.model.dto.Result;
import com.insigma.acc.monitor.service.NodeTreeService;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeTreeController extends BaseMultiActionController {

    @Autowired
    private NodeTreeService nodeTreeService;

    static {
        methodMapping.put("/node/list/editor","editorTree");
        methodMapping.put("/node/list/monitor","monitorTree");
        methodMapping.put("/node/list/simple","simpleTree");
        methodMapping.put("/node/list/device","deviceTree");
    }

    public NodeTreeController(NodeTreeService nodeTreeService){
        this.nodeTreeService = nodeTreeService;
    }

    @JsonView(NodeItem.editor.class)
    public Result<NodeItem> editorTree(){
        return nodeTreeService.getEditorNodeTree();
    }

    @JsonView(NodeItem.monitor.class)
    public Result<NodeItem> monitorTree(){
        return nodeTreeService.getMonitorNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> simpleTree(){
        return nodeTreeService.getEditorNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> deviceTree(){
        return nodeTreeService.getNodeGroupDeviceTree();
    }

}