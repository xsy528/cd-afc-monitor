package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.NodeData;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.service.IMetroNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-10 14:47
 */
@Api(tags = "节点管理服务接口")
@RestController
@RequestMapping("/node")
public class NodeManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeManagerController.class);

    private IMetroNodeService metroNodeService;

    @Autowired
    public NodeManagerController(IMetroNodeService metroNodeService) {
        this.metroNodeService = metroNodeService;
    }

    @ApiOperation("创建节点")
    @PostMapping("/create")
    public Result create(@RequestPart MultipartFile img, @RequestBody NodeData data){
        return metroNodeService.create(data,img);
    }

    @ApiOperation("修改节点")
    @PostMapping("/update")
    public Result update(@RequestPart MultipartFile img, @RequestBody NodeData data){
        return metroNodeService.update(data,img);
    }

    @ApiOperation("查询节点详情")
    @GetMapping("/query")
    public Result query(@RequestParam Long nodeId){
        return metroNodeService.query(nodeId);
    }

    @ApiOperation("删除节点")
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long nodeId){
        return metroNodeService.delete(nodeId);
    }
}
