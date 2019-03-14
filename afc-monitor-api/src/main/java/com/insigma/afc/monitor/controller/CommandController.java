package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.DeviceCmdControlType;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.SendCommand;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.RegisterPingService;
import com.insigma.commons.dic.PairValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RestController
@RequestMapping("/monitor/command")
public class CommandController{

    private CommandService commandService;
    private RegisterPingService registerPingService;

    @Autowired
    public CommandController(CommandService commandService,RegisterPingService registerPingService){
        this.commandService = commandService;
        this.registerPingService = registerPingService;
    }

    @ApiOperation("获取设备控制命令列表")
    @PostMapping("/deviceControlCommandList")
    public Result<List<PairValue<Object,String>>> getDeviceControlCommandList(){
        return Result.success(DeviceCmdControlType.getInstance().getByGroup(""));
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

    @ApiOperation("查询钱箱和票箱")
    @PostMapping("/queryBox")
    public Result<List<CommandResult>> queryBox(@RequestParam Long nodeId){
        return commandService.sendQueryBoxCommand(nodeId);
    }

    @ApiOperation("通讯服务器连接状态")
    @PostMapping("/isRegisterOnline")
    public Result<Integer> isRegisterOnline(){
        return registerPingService.isRegisterOnline();
    }
}
