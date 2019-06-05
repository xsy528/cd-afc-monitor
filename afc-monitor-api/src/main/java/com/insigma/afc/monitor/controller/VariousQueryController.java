/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.vo.*;
import com.insigma.afc.monitor.service.CommandLogService;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.DeviceEventService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/10 11:08
 */
@Api(tags = "各类查询接口")
//@RestController
@RequestMapping("/monitor/query/")
public class VariousQueryController {

    private CommandLogService cmdLogService;
    private DeviceEventService deviceEventService;
    private ModeService modeService;
    private CommandService commandService;

    @ApiOperation("各类查询-模式上传")
    @PostMapping("modeUploadInfo")
    public Result<Page<ModeUploadInfo>> getModeUploadInfo(@RequestBody ModeUploadCondition condition) {
        return Result.success(modeService.getModeUploadInfo(condition));
    }

    @ApiOperation("各类查询-模式广播")
    @PostMapping("modeBroadcastInfo")
    public Result<Page<ModeBroadcastInfo>> getModeBroadcastInfo(@RequestBody ModeBroadcastCondition condition) {
        return Result.success(modeService.getModeBroadcastInfo(condition));
    }

    @ApiOperation("各类查询-模式日志")
    @PostMapping("modeCmdSearch")
    public Result<Page<ModeCmdInfo>> getmodeCmdSearch(@RequestBody ModeCmdCondition condition) {
        return Result.success(modeService.getModeCmdSearch(condition));
    }

    @ApiOperation("各类查询-命令日志")
    @PostMapping("CommandLogSearch")
    public Result<Page<CommandLogInfo>> getCommandLogSearch(@RequestBody CommandLogCondition condition) {
        return Result.success(cmdLogService.getCommandLogSearch(condition));
    }

    @ApiOperation("各类查询-设备事件")
    @PostMapping("DeviceEventSearch")
    public Result<Page<DeviceEvent>> getDeviceEventSearch(@RequestBody EquEventCondition condition) {
        return Result.success(deviceEventService.getDeviceEventSearch(condition));
    }

    @ApiOperation("重发广播命令")
    @PostMapping("/resendModeBroadcast")
    public Result<List<CommandResultDTO>> resendModeBroadcast(@RequestBody List<Long> recordIds){
        return commandService.sendModeBroadcastCommand(recordIds);
    }

    @Autowired
    public void setCmdLogService(CommandLogService cmdLogService) {
        this.cmdLogService = cmdLogService;
    }

    @Autowired
    public void setDeviceEventService(DeviceEventService deviceEventService) {
        this.deviceEventService = deviceEventService;
    }

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    @Autowired
    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }
}
