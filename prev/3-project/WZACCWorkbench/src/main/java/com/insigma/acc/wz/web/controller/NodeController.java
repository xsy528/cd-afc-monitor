package com.insigma.acc.wz.web.controller;

import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.service.NodeService;
import com.insigma.acc.wz.web.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class NodeController extends BaseMultiActionController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private FileService fileService;

    //路径和方法的映射
    private static Map<String,String> methodMapping = new HashMap<>();
    static {
        methodMapping.put("/node/list/editor","editorTreeList");
        methodMapping.put("/node/list/monitor","monitorTreeList");
        methodMapping.put("/node/list/simple","simpleTreeList");
        methodMapping.put("/node/list-img-urls","imageList");
    }

    public NodeController(NodeService nodeService,FileService fileService){
        super(methodMapping);
        this.nodeService = nodeService;
        this.fileService = fileService;
    }

    public void editorTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        try(PrintWriter writer = response.getWriter()){
            writer.println(JsonUtils.parseObject(nodeService.getNodeTree()));
        }
    }

    public void monitorTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        editorTreeList(request,response);
    }

    public void simpleTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        try(PrintWriter writer = response.getWriter()){
            writer.println(JsonUtils.parseObject(nodeService.getNodeTree(), NodeItem.required.class));
        }
    }

    public void imageList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        try(PrintWriter writer = response.getWriter()){
            writer.println(JsonUtils.parseObject(fileService.getResourceList()));
        }
    }

}