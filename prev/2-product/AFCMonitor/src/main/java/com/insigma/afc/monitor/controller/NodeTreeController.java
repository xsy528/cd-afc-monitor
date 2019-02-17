package com.insigma.afc.monitor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.vo.NodeItem;
import com.insigma.afc.monitor.service.NodeTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ticket: 节点树接口
 *
 * @author xuzhemin
 * 2019-2-17 10:22
 */
@Api(tags="节点树服务接口")
@RestController
@RequestMapping("/node/tree")
public class NodeTreeController {

    private NodeTreeService nodeTreeService;

    @Autowired
    public NodeTreeController(NodeTreeService nodeTreeService){
        this.nodeTreeService = nodeTreeService;
    }

    @JsonView(NodeItem.editor.class)
    @ApiOperation("获取编辑树")
    @GetMapping("/editor")
    public Result<NodeItem> editorTree(){
        return nodeTreeService.getEditorNodeTree();
    }

    @JsonView(NodeItem.monitor.class)
    @ApiOperation("获取监视树")
    @GetMapping("/monitor")
    public Result<NodeItem> monitorTree(){
        return nodeTreeService.getMonitorNodeTree();
    }

    @JsonView(NodeItem.required.class)
    @ApiOperation("获取简单树")
    @GetMapping("/simple")
    public Result<NodeItem> simpleTree(){
        return nodeTreeService.getEditorNodeTree();
    }

    @JsonView(NodeItem.required.class)
    @ApiOperation("获取设备分组树")
    @GetMapping("/device")
    public Result<NodeItem> deviceTree(){
        return nodeTreeService.getNodeGroupDeviceTree();
    }

}