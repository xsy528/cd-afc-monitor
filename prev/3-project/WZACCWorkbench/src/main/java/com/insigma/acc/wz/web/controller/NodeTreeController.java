package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeTreeController extends BaseMultiActionController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private FileService fileService;

    static {
        methodMapping.put("/node/list/editor","editorTreeList");
        methodMapping.put("/node/list/monitor","monitorTreeList");
        methodMapping.put("/node/list/simple","simpleTreeList");
        methodMapping.put("/node/list/simpleDeviceGroup","simpleDeviceGroupTreeList");
        methodMapping.put("/node/list-img-urls","imageList");
    }

    public NodeTreeController(NodeService nodeService, FileService fileService){
        this.nodeService = nodeService;
        this.fileService = fileService;
    }

    @JsonView(NodeItem.editor.class)
    public Result<NodeItem> editorTreeList(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.monitor.class)
    public Result<NodeItem> monitorTreeList(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> simpleTreeList(){
        return nodeService.getNodeTree();
    }

    @JsonView(NodeItem.required.class)
    public Result<NodeItem> simpleDeviceGroupTreeList(){
        return nodeService.getNodeSimpleTree();
    }

    public Result<String[]> imageList(){
        return fileService.getResourceList();
    }

}