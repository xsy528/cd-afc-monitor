package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.*;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.model.vo.*;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.monitor.service.MonitorService;
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

    @Autowired
    public NodeStatusController(IMetroNodeStatusService metroNodeStatusService, ModeService modeService,
                                MonitorService monitorService, TopologyService topologyService) {
        this.metroNodeStatusService = metroNodeStatusService;
        this.modeService = modeService;
        this.monitorService = monitorService;
        this.topologyService = topologyService;
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
            equStatus.setName(topologyService.getNodeText(equStatusViewItem.getNodeId().longValue()).getData());
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
                condition.getEntryMode(),null,condition.getStartTime(),
                condition.getEndTime(),condition.getPageNumber(),condition.getPageSize());

        return Result.success(tmoModeUploadInfos.map(tmoModeUploadInfo ->{
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
                condition.getEntryMode(),null,condition.getDestStationId(),condition.getBroadcastStatus(),
                condition.getBroadcastType(),condition.getStartTime(),
                condition.getEndTime(),condition.getPageNumber(),condition.getPageSize());

        return Result.success(tmoModeBroadcasts.map(tmoModeBroadcast ->{
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setName(topologyService.getNodeText(tmoModeBroadcast.getNodeId()).getData());
            modeBroadcastInfo.setSourceName(topologyService.getNodeText(tmoModeBroadcast.getStationId().longValue()).getData());
            modeBroadcastInfo.setTargetName(topologyService.getNodeText(tmoModeBroadcast.getTargetId()).getData());
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast.getModeCode())));
            modeBroadcastInfo.setModeBroadcastType(tmoModeBroadcast.getBroadcastType()==0?"手动":"自动");
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
