package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.insigma.acc.wz.define.WZDeviceCmdControlType;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.CommandService;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.util.HttpUtils;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.action.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-04:10:49
 */
public class CommandController extends BaseMultiActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandController.class);

    static{
        methodMapping.put("/monitor/deviceControlCommandList","getDeviceControlCommandList");
        methodMapping.put("/monitor/queryMode","queryMode");
        methodMapping.put("/monitor/changeMode","changeMode");
        methodMapping.put("/monitor/timeSync","timeSync");
        methodMapping.put("/monitor/nodeControl","nodeControl");
        methodMapping.put("/monitor/refresh","refresh");
    }

    private CommandService commandService;
    private FileService fileService;

    @Autowired
    public CommandController(CommandService commandService,FileService fileService){
        this.commandService = commandService;
        this.fileService = fileService;
    }

    //获取设备控制命令列表
    public Result<Map> getDeviceControlCommandList(){
        Map<String,Object> commandMap = new HashMap<>();
        WZDeviceCmdControlType.getInstance().dicItecEntryMap.forEach((k,v)-> commandMap.put(v.dicitem.name(),v.value));
        return Result.success(commandMap);
    }

    //修改运营模式
    public Result<List<CommandResult>> changeMode(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Long> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        int command = jsonNode.get("command").intValue();
        return commandService.sendChangeModeCommand(idList,command);
    }

    //模式查询
    public Result<List<CommandResult>> queryMode(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Long> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        return commandService.sendModeQueryCommand(idList);
    }

    //时钟同步
    public Result<List<CommandResult>> timeSync(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
            List<Long> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")) {
                idList.add(id.longValue());
            }
        return commandService.sendTimeSyncCommand(idList);
    }

    //发送设备控制命令
    public Result<List<CommandResult>> nodeControl(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Long> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        short command = jsonNode.get("command").shortValue();
        return commandService.sendNodeControlCommand(idList,command);
    }

    //刷新数据库资源
    public Result<String> refresh(){
        AFCApplication.refresh();
        fileService.synResources();
        return Result.success();
    }
}
