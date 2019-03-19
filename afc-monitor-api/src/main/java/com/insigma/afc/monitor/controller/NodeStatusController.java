package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.model.vo.*;
import com.insigma.afc.monitor.service.*;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Ticket: 节点状态查询
 *
 * @author xuzhemin
 * 2018-12-28 18:06
 */
@Api(tags = "节点监控查询接口")
@RestController
@RequestMapping("/monitor/query/")
public class NodeStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusController.class);

    private IMetroNodeStatusService metroNodeStatusService;
    private ModeService modeService;
    private MonitorService monitorService;
    private TopologyService topologyService;
    private CommandLogService cmdLogService;
    private DeviceEventService deviceEventService;

    @Autowired
    public NodeStatusController(IMetroNodeStatusService metroNodeStatusService, ModeService modeService,
                                MonitorService monitorService, TopologyService topologyService ,
                                CommandLogService cmdLogService,DeviceEventService deviceEventService) {
        this.metroNodeStatusService = metroNodeStatusService;
        this.modeService = modeService;
        this.monitorService = monitorService;
        this.topologyService = topologyService;
        this.cmdLogService = cmdLogService;
        this.deviceEventService = deviceEventService;
    }

    @ApiOperation("获取车站状态列表")
    @PostMapping("stationStatus")
    public Result<List<StationStatus>> getStationStatus(@Valid @RequestBody StationStatusCondition condition) {
        List<StationStatustViewItem> data = metroNodeStatusService.getStationStatusView(condition);
        List<StationStatus> stationStatusList = new ArrayList<>();
        for (StationStatustViewItem stationStatustViewItem : data) {
            StationStatus stationStatus = new StationStatus();
            stationStatus.setNodeId(stationStatustViewItem.getStationId().longValue());
            stationStatus.setAlarmEvent(stationStatustViewItem.getAlarmEvent());
            stationStatus.setNormalEvent(stationStatustViewItem.getNormalEvent());
            stationStatus.setWarnEvent(stationStatustViewItem.getWarnEvent());
            stationStatus.setOnline(stationStatustViewItem.getOnline());
            stationStatus.setUpdateTime(DateTimeUtil.formatDate(stationStatustViewItem.getUpdateTime()));
            //设置车站状态
            Short status = stationStatustViewItem.getStatus();
            stationStatus.setStatus(DeviceStatus.getInstance().getNameByValue(status) + "/" + status);
            stationStatus.setName(topologyService.getNodeText(stationStatustViewItem.getStationId().longValue())
                    .getData());
            //设置车站模式/编号
            Integer mode = stationStatustViewItem.getMode().intValue();
            stationStatus.setMode(AFCModeCode.getInstance().getModeText(mode));

            stationStatusList.add(stationStatus);
        }
        return Result.success(stationStatusList);
    }

    @ApiOperation("设备状态列表")
    @PostMapping("deviceStatus")
    public Result<List<EquStatus>> getDeviceStatus(@Valid @RequestBody DeviceStatusCondition deviceStatusSearch) {
        List<EquStatusViewItem> data = metroNodeStatusService.getEquStatusView(deviceStatusSearch);
        List<EquStatus> equStatusList = new ArrayList<>();
        for (EquStatusViewItem equStatusViewItem : data) {
            EquStatus equStatus = new EquStatus();
            Short status = Integer.valueOf(equStatusViewItem.getStatus()).shortValue();
            equStatus.setNodeId(equStatusViewItem.getNodeId());
            equStatus.setStatus(DeviceStatus.getInstance().getNameByValue(status) + "/" + status);
            equStatus.setOnline(equStatusViewItem.getOnline());
            equStatus.setUpdateTime(DateTimeUtil.formatDate(equStatusViewItem.getUpdateTime()));
            equStatus.setName(topologyService.getNodeText(equStatusViewItem.getNodeId()).getData());
            equStatusList.add(equStatus);
        }
        return Result.success(equStatusList);
    }

    @ApiOperation("模式上传信息")
    @PostMapping("modeUpload")
    public Result<List<ModeUploadInfo>> getModeUpload(@RequestParam Long nodeId) {
        List<TmoModeUploadInfo> tmoModeUploadInfos = modeService.getModeUpload(nodeId);
        List<ModeUploadInfo> modeUploadInfos = new ArrayList<>();
        for (TmoModeUploadInfo tmoModeUploadInfo : tmoModeUploadInfos) {
            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
            modeUploadInfo.setLineName(topologyService.getNodeText(tmoModeUploadInfo.getLineId().longValue())
                    .getData());
            modeUploadInfo.setStationName(topologyService.getNodeText(tmoModeUploadInfo.getStationId().longValue())
                    .getData());
            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo
                    .getModeCode())));
            modeUploadInfos.add(modeUploadInfo);
        }
        return Result.success(modeUploadInfos);
    }

    @ApiOperation("各类查询-模式上传")
    @PostMapping("modeUploadInfo")
    public Result<Page<ModeUploadInfo>> getModeUploadInfo(@RequestBody ModeUploadCondition condition) {
        Page<TmoModeUploadInfo> tmoModeUploadInfos = modeService.getModeUploadInfo(condition.getStationIds(),
                condition.getEntryMode(), null, condition.getStartTime(),
                condition.getEndTime(), condition.getPageNumber(), condition.getPageSize());

        return Result.success(tmoModeUploadInfos.map(tmoModeUploadInfo -> {
            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
            modeUploadInfo.setLineName(topologyService.getNodeText(tmoModeUploadInfo.getLineId().longValue()).getData());
            modeUploadInfo.setStationName(topologyService.getNodeText(tmoModeUploadInfo.getStationId().longValue()).getData());
            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo.getModeCode())));
            return modeUploadInfo;
        }));
    }

    @ApiOperation("各类查询-模式广播")
    @PostMapping("modeBroadcastInfo")
    public Result<Page<ModeBroadcastInfo>> getModeBroadcastInfo(@RequestBody ModeBroadcastCondition condition) {
        Page<TmoModeBroadcast> tmoModeBroadcasts = modeService.getModeBroadcastInfo(condition.getStationIds(),
                condition.getEntryMode(), null, condition.getDestStationId(), condition.getBroadcastStatus(),
                condition.getBroadcastType(), condition.getStartTime(),
                condition.getEndTime(), condition.getPageNumber(), condition.getPageSize());

        return Result.success(tmoModeBroadcasts.map(tmoModeBroadcast -> {
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setName(topologyService.getNodeText(tmoModeBroadcast.getNodeId()).getData());
            modeBroadcastInfo.setSourceName(topologyService.getNodeText(tmoModeBroadcast.getStationId().longValue()).getData());
            modeBroadcastInfo.setTargetName(topologyService.getNodeText(tmoModeBroadcast.getTargetId()).getData());
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast.getModeCode())));
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
    @PostMapping("modeCmdSearch")    //查询条件：站点，操作员ID，指令结果，开始时间，结束时间，null，指令类型，页数，页数大小
    public Result<Page<ModeCmdInfo>> getmodeCmdSearch(@RequestBody ModeCmdCondition condition) {//查询条件model
        //返回的值的集合,数据库表实体类
        Page<TmoCmdResult> tmoModeCmdInfos = modeService.getModeCmdSearch(condition);
        //显示结果： "发送时间", "操作员姓名/编号", "车站/编号", "模式名称", "发送结果/编号"
        return Result.success(tmoModeCmdInfos.map(tmoModeCmdInfo -> {
            //返回结果集合显示，显示实体类,一个代表一行
            ModeCmdInfo modeCmdInfo = new ModeCmdInfo();
            //序号，发送时间，操作员姓名/编号,车站/编号，模式名称,发送结果
            modeCmdInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeCmdInfo.getOccurTime()));
            modeCmdInfo.setOperatorId(tmoModeCmdInfo.getOperatorId());
            modeCmdInfo.setStationName(topologyService.getNodeText(tmoModeCmdInfo.getStationId().longValue()).getData());
            modeCmdInfo.setStationId(tmoModeCmdInfo.getStationId());
            modeCmdInfo.setCmdName(tmoModeCmdInfo.getCmdName());
            modeCmdInfo.setCmdResult(tmoModeCmdInfo.getCmdResult().toString());

            return modeCmdInfo;
        }));
    }

    @ApiOperation("各类查询-命令日志")
    @PostMapping("CommandLogSearch")
    public Result<Page<CommandLogInfo>> getCommandLogSearch(@RequestBody CommandLogCondition condition) {//查询条件model
        //返回的值的集合,数据库表实体类
        Page<TmoCmdResult> tmoCommandLogInfos = cmdLogService.getCommandLogSearch(condition);
        //显示结果：
        return Result.success(tmoCommandLogInfos.map(tmoCommandLogInfo -> {
            //返回结果集合显示，显示实体类
            //节点名称/编码，命令名称,操作员名称/编号，发送时间，命令结果/应答码
            CommandLogInfo commandLogInfo = new CommandLogInfo();

            commandLogInfo.setNodeName(topologyService.getNodeText(tmoCommandLogInfo.getNodeId()+0L).getData());
            commandLogInfo.setNodeId(tmoCommandLogInfo.getNodeId());
            commandLogInfo.setCmdName(tmoCommandLogInfo.getCmdName());
            commandLogInfo.setOperatotId(tmoCommandLogInfo.getOperatorId());
            commandLogInfo.setUploadTime(DateTimeUtil.formatDate(tmoCommandLogInfo.getOccurTime()));
            commandLogInfo.setCmdResult(tmoCommandLogInfo.getCmdResult().toString());

            return commandLogInfo;
        }));
    }
    @ApiOperation("各类查询-设备事件")
    @PostMapping("DeviceEventSearch")
    public Result<Page<DeviceEvent>> getDeviceEventSearch(@RequestBody DeviceEventCondition condition) {//查询条件model
        //返回的值的集合,数据库表实体类
        Page<TmoEquStatus> tmoDeviceEventInfos = deviceEventService.getDeviceEventSearch(condition);
        //显示结果：
        return Result.success(tmoDeviceEventInfos.map(tmoDeviceEventInfo -> {
            //返回结果集合显示，显示实体类
            //节点名称/节点编码,事件名称/编号，事件描述,发生时间
            DeviceEvent deviceEventInfo = new DeviceEvent();

            deviceEventInfo.setNodeName(topologyService.getNodeText(tmoDeviceEventInfo.getNodeId()+0L).getData());
            deviceEventInfo.setNodeId(tmoDeviceEventInfo.getNodeId().toString());
            deviceEventInfo.setEventName(tmoDeviceEventInfo.getStatusName());
            deviceEventInfo.setEventDesc(tmoDeviceEventInfo.getStatusDesc());
            deviceEventInfo.setOccurTime(DateTimeUtil.formatDate(tmoDeviceEventInfo.getOccurTime()));

            return deviceEventInfo;
        }));
    }


    @ApiOperation("模式广播信息")
    @PostMapping("modeBroadcast")
    public Result<List<ModeBroadcastInfo>> getModeBroadcast() {
        List<TmoModeBroadcast> tmoModeBroadcasts = modeService.getModeBroadcast();
        List<ModeBroadcastInfo> modeBroadcastInfos = new ArrayList<>();
        for (TmoModeBroadcast tmoModeBroadcast : tmoModeBroadcasts) {
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setName(topologyService.getNodeText(tmoModeBroadcast.getNodeId()).getData());
            modeBroadcastInfo.setSourceName(topologyService.getNodeText(tmoModeBroadcast.getStationId().longValue()).getData());
            modeBroadcastInfo.setTargetName(topologyService.getNodeText(tmoModeBroadcast.getTargetId()).getData());
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast.getModeCode())));
            modeBroadcastInfos.add(modeBroadcastInfo);
        }
        return Result.success(modeBroadcastInfos);
    }

    @ApiOperation("设备事件列表")
    @PostMapping("deviceEvent")
    public Result<List<EquEvent>> getDeviceEvent(@RequestBody DeviceEventCondition condition) {
        List<TmoEquStatusCur> tmoEquStatusCurs = modeService.getEquStatusList(condition);
        List<EquEvent> equEvents = new ArrayList<>();
        for (TmoEquStatusCur tmoEquStatusCur : tmoEquStatusCurs) {
            EquEvent equEvent = new EquEvent();
            equEvent.setApplyDevice(tmoEquStatusCur.getApplyDevice());
            equEvent.setItem(tmoEquStatusCur.getItem1());
            equEvent.setNodeName(topologyService.getNodeText(tmoEquStatusCur.getNodeId()).getData());
            equEvent.setOccurTime(DateTimeUtil.formatDate(tmoEquStatusCur.getOccurTime()));
            equEvent.setStatusName(tmoEquStatusCur.getStatusName() + "/" + tmoEquStatusCur.getStatusId());
            equEvent.setStatusDesc(tmoEquStatusCur.getStatusDesc() + "/" + tmoEquStatusCur.getStatusValue());
            equEvents.add(equEvent);
        }
        return Result.success(equEvents);
    }

    @ApiOperation("监视设备")
    @PostMapping("deviceDetail")
    public Result getDeviceDetail(@RequestParam Long nodeId) {
        return monitorService.getDeviceDetail(nodeId);
    }

    @ApiOperation("钱箱票箱")
    @PostMapping("boxDetail")
    public Result getBoxDetail(@RequestParam Long nodeId) {
        return monitorService.getBoxDetail(nodeId);
    }

}
