/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.model.entity.TmoEquStatus;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.model.vo.*;
import com.insigma.afc.monitor.service.CommandLogService;
import com.insigma.afc.monitor.service.DeviceEventService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.model.dto.Result;
import com.insigma.commons.util.DateTimeUtil;
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
@RestController
@RequestMapping("/monitor/query/")
public class VariousQueryController {

    private CommandLogService cmdLogService;
    private DeviceEventService deviceEventService;
    private TopologyService topologyService;
    private ModeService modeService;

    @ApiOperation("各类查询-模式上传")
    @PostMapping("modeUploadInfo")
    public Result<Page<ModeUploadInfo>> getModeUploadInfo(@RequestBody ModeUploadCondition condition) {
        Page<TmoModeUploadInfo> tmoModeUploadInfos = modeService.getModeUploadInfo(condition.getStationIds(),
                condition.getEntryMode(), null, condition.getStartTime(),
                condition.getEndTime(), condition.getPageNumber(), condition.getPageSize());

        return Result.success(tmoModeUploadInfos.map(tmoModeUploadInfo -> {
            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
            modeUploadInfo.setLineName(topologyService.getNodeText(tmoModeUploadInfo.getLineId().longValue()).getData
                    ());
            modeUploadInfo.setStationName(topologyService.getNodeText(tmoModeUploadInfo.getStationId().longValue())
                    .getData());
            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo
                    .getModeCode())));
            return modeUploadInfo;
        }));
    }

    @ApiOperation("各类查询-模式广播")
    @PostMapping("modeBroadcastInfo")
    public Result<Page<ModeBroadcastInfo>> getModeBroadcastInfo(@RequestBody ModeBroadcastCondition condition) {
        Page<TmoModeBroadcast> tmoModeBroadcasts = modeService.getModeBroadcastInfo(condition);

        return Result.success(tmoModeBroadcasts.map(tmoModeBroadcast -> {
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setRecordId(tmoModeBroadcast.getRecordId());
            modeBroadcastInfo.setName(topologyService.getNodeText(tmoModeBroadcast.getNodeId()).getData());
            modeBroadcastInfo.setSourceName(topologyService.getNodeText(tmoModeBroadcast.getStationId().longValue())
                    .getData());
            modeBroadcastInfo.setTargetName(topologyService.getNodeText(tmoModeBroadcast.getTargetId()).getData());
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast
                    .getModeCode())));
            modeBroadcastInfo.setModeBroadcastType(tmoModeBroadcast.getBroadcastType() == 0 ? "手动" : "自动");
            modeBroadcastInfo.setModeEffectTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeEffectTime()));
            if (tmoModeBroadcast.getBroadcastStatus() == 0) {
                modeBroadcastInfo.setModeBroadcastStatus("未确认");
            } else if (tmoModeBroadcast.getBroadcastStatus() == 1) {
                modeBroadcastInfo.setModeBroadcastStatus("成功");
            } else if (tmoModeBroadcast.getBroadcastStatus() == 2) {
                modeBroadcastInfo.setModeBroadcastStatus("失败");
            }
            modeBroadcastInfo.setOperatorId(tmoModeBroadcast.getOperatorId());
            return modeBroadcastInfo;
        }));
    }

    @ApiOperation("各类查询-模式日志")
    @PostMapping("modeCmdSearch")
    public Result<Page<ModeCmdInfo>> getmodeCmdSearch(@RequestBody ModeCmdCondition condition) {
        //返回的值的集合,数据库表实体类
        Page<TmoCmdResult> tmoModeCmdInfos = modeService.getModeCmdSearch(condition);
        //显示结果： "发送时间", "操作员姓名/编号", "车站/编号", "模式名称", "发送结果/编号"
        return Result.success(tmoModeCmdInfos.map(tmoModeCmdInfo -> {
            //返回结果集合显示，显示实体类,一个代表一行
            ModeCmdInfo modeCmdInfo = new ModeCmdInfo();
            //序号，发送时间，操作员姓名/编号,车站/编号，模式名称,发送结果
            modeCmdInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeCmdInfo.getOccurTime()));
            modeCmdInfo.setOperatorId(tmoModeCmdInfo.getOperatorId());
            modeCmdInfo.setStationName(topologyService.getNodeText(tmoModeCmdInfo.getStationId().longValue()).getData
                    ());
            modeCmdInfo.setStationId(tmoModeCmdInfo.getStationId());
            modeCmdInfo.setCmdName(tmoModeCmdInfo.getCmdName());
            modeCmdInfo.setCmdResult(tmoModeCmdInfo.getCmdResult().toString());

            return modeCmdInfo;
        }));
    }

    @ApiOperation("各类查询-命令日志")
    @PostMapping("CommandLogSearch")
    public Result<Page<CommandLogInfo>> getCommandLogSearch(@RequestBody CommandLogCondition condition) {
        //返回的值的集合,数据库表实体类
        Page<TmoCmdResult> tmoCommandLogInfos = cmdLogService.getCommandLogSearch(condition);
        //显示结果：
        return Result.success(tmoCommandLogInfos.map(tmoCommandLogInfo -> {
            //返回结果集合显示，显示实体类
            //节点名称/编码，命令名称,操作员名称/编号，发送时间，命令结果/应答码
            CommandLogInfo commandLogInfo = new CommandLogInfo();

            commandLogInfo.setNodeName(topologyService.getNodeText(tmoCommandLogInfo.getNodeId()).getData());
            commandLogInfo.setNodeId(tmoCommandLogInfo.getNodeId());
            commandLogInfo.setCmdName(tmoCommandLogInfo.getCmdName());
            commandLogInfo.setOperatorId(tmoCommandLogInfo.getOperatorId());
            commandLogInfo.setUploadTime(DateTimeUtil.formatDate(tmoCommandLogInfo.getOccurTime()));
            commandLogInfo.setCmdResult(tmoCommandLogInfo.getCmdResult().toString());

            return commandLogInfo;
        }));
    }

    @ApiOperation("各类查询-设备事件")
    @PostMapping("DeviceEventSearch")
    public Result<Page<DeviceEvent>> getDeviceEventSearch(@RequestBody EquEventCondition condition) {
        //返回的值的集合,数据库表实体类
        Page<TmoEquStatus> tmoDeviceEventInfos = deviceEventService.getDeviceEventSearch(condition);
        //显示结果：
        return Result.success(tmoDeviceEventInfos.map(tmoDeviceEventInfo -> {
            //返回结果集合显示，显示实体类
            //节点名称/节点编码,事件名称/编号，事件描述,发生时间
            DeviceEvent deviceEventInfo = new DeviceEvent();

            deviceEventInfo.setNodeName(topologyService.getNodeText(tmoDeviceEventInfo.getNodeId()).getData());
            deviceEventInfo.setNodeId(tmoDeviceEventInfo.getNodeId().toString());
            deviceEventInfo.setEventName(tmoDeviceEventInfo.getStatusName());
            deviceEventInfo.setEventDesc(tmoDeviceEventInfo.getStatusDesc());
            deviceEventInfo.setOccurTime(DateTimeUtil.formatDate(tmoDeviceEventInfo.getOccurTime()));

            return deviceEventInfo;
        }));
    }

    @ApiOperation("重发广播命令")
    @PostMapping("/resendModeBroadcast")
    public Result<List<CommandResultDTO>> resendModeBroadcast(@RequestBody List<Long> recoredIds){
        return Result.success(modeService.resendModeBroadcast(recoredIds));
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
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }
}
