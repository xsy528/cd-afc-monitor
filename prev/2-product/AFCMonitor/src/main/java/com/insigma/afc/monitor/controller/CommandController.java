package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.WZDeviceCmdControlType;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SendCommand;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket: 命令接口
 *
 * @author xuzhemin
 * 2019-01-04:10:49
 */
@Api(tags="命令接口")
@RestController
@RequestMapping("/monitor/command")
public class CommandController{

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandController.class);

    private CommandService commandService;
    private FileService fileService;

    @Autowired
    public CommandController(CommandService commandService,FileService fileService){
        this.commandService = commandService;
        this.fileService = fileService;
    }

    @ApiOperation("获取设备控制命令列表")
    @GetMapping("/deviceControlCommandList")
    public Result<Map> getDeviceControlCommandList(){
        Map<String,Object> commandMap = new HashMap<>();
        WZDeviceCmdControlType.getInstance().dicItecEntryMap.forEach((k,v)-> commandMap.put(v.dicitem.name(),v.value));
        return Result.success(commandMap);
    }

    @ApiOperation("修改运营模式")
    @PostMapping("/changeMode")
    public Result<List<CommandResult>> changeMode(@RequestBody SendCommand sendCommand){
        return commandService.sendChangeModeCommand(sendCommand.getNodeIds(),sendCommand.getCommand());
    }

    @ApiOperation("模式查询")
    @PostMapping("/queryMode")
    public Result<List<CommandResult>> queryMode(@RequestBody SendCommand sendCommand){
        return commandService.sendModeQueryCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("时钟同步")
    @PostMapping("/timeSync")
    public Result<List<CommandResult>> timeSync(@RequestBody SendCommand sendCommand){
        return commandService.sendTimeSyncCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("地图同步")
    @PostMapping("/mapSync")
    public Result<List<CommandResult>> mapSync(@RequestBody SendCommand sendCommand){
        return commandService.sendMapSyncCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("发送设备控制命令")
    @PostMapping("/nodeControl")
    public Result<List<CommandResult>> nodeControl(@RequestBody SendCommand sendCommand){
        return commandService.sendNodeControlCommand(sendCommand.getNodeIds(),sendCommand.getCommand().shortValue());
    }

    @ApiOperation("刷新数据库资源")
    @PostMapping("/refresh")
    public Result<String> refresh(){
        fileService.synResources();
        return Result.success();
    }

    @ApiOperation("查询钱箱和票箱")
    @PostMapping("/queryBox")
    public Result<List<CommandResult>> queryBox(@RequestParam Long nodeId){
        return commandService.sendQueryBoxCommand(nodeId);
    }
}
