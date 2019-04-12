package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.DeviceCmdControlType;
import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SendCommandDTO;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.commons.dic.PairValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ticket: 命令接口
 *
 * @author xuzhemin
 * 2019-01-04:10:49
 */
@Api(tags="命令接口")
//@RestController
@RequestMapping("/monitor/command")
public class CommandController{

    private CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService){
        this.commandService = commandService;
    }

    @ApiOperation("获取设备控制命令列表")
    @PostMapping("/deviceControlCommandList")
    public Result<List<PairValue<Object,String>>> getDeviceControlCommandList(){
        return Result.success(DeviceCmdControlType.getInstance().getByGroup(""));
    }

    @ApiOperation("修改运营模式")
    @PostMapping("/changeMode")
    public Result<List<CommandResultDTO>> changeMode(@RequestBody SendCommandDTO sendCommand){
        return commandService.sendChangeModeCommand(sendCommand.getNodeIds(),sendCommand.getCommand());
    }

    @ApiOperation("模式查询")
    @PostMapping("/queryMode")
    public Result<List<CommandResultDTO>> queryMode(@RequestBody SendCommandDTO sendCommand){
        return commandService.sendModeQueryCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("时钟同步")
    @PostMapping("/timeSync")
    public Result<List<CommandResultDTO>> timeSync(@RequestBody SendCommandDTO sendCommand){
        return commandService.sendTimeSyncCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("地图同步")
    @PostMapping("/mapSync")
    public Result<List<CommandResultDTO>> mapSync(@RequestBody SendCommandDTO sendCommand){
        return commandService.sendMapSyncCommand(sendCommand.getNodeIds());
    }

    @ApiOperation("发送设备控制命令")
    @PostMapping("/nodeControl")
    public Result<List<CommandResultDTO>> nodeControl(@RequestBody SendCommandDTO sendCommand){
        return commandService.sendNodeControlCommand(sendCommand.getNodeIds(),sendCommand.getCommand().shortValue());
    }

    @ApiOperation("查询钱箱和票箱")
    @PostMapping("/queryBox")
    public Result<List<CommandResultDTO>> queryBox(@RequestParam Long nodeId){
        return commandService.sendQueryBoxCommand(nodeId);
    }

}
