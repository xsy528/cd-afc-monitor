package com.insigma.afc.monitor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.NodeItem;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.condition.MonitorTreeCondition;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.model.vo.*;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.monitor.service.MonitorService;
import com.insigma.afc.monitor.service.NodeTreeService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.model.dto.Result;
import com.insigma.commons.util.DateTimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Ticket: 节点状态查询
 *
 * @author xuzhemin
 * 2018-12-28 18:06
 */
@Api(tags = "节点监控接口")
@RestController
@RequestMapping("/monitor/query/")
public class NodeStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusController.class);

    private IMetroNodeStatusService metroNodeStatusService;
    private ModeService modeService;
    private MonitorService monitorService;
    private TopologyService topologyService;
    private NodeTreeService nodeTreeService;

    @ApiOperation("获取车站状态列表")
    @PostMapping("stationStatus")
    public Result<List<StationStatus>> getStationStatus(@Valid @RequestBody StationStatusCondition condition) {
        List<StationStatustViewItem> data = metroNodeStatusService.getStationStatusView(condition);

        Set<Long> ids = new HashSet<>();
        Map<Long,String> textMap = null;
        if (!data.isEmpty()){
            for (StationStatustViewItem s:data){
                ids.add(s.getStationId().longValue());
            }
            textMap = topologyService.getNodeTexts(ids).getData();
        }

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
            stationStatus.setName(textMap.get(stationStatustViewItem.getStationId().longValue()));
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

        Set<Long> ids = new HashSet<>();
        Map<Long,String> textMap = null;
        if (!data.isEmpty()){
            for (EquStatusViewItem s:data){
                ids.add(s.getNodeId());
            }
            textMap = topologyService.getNodeTexts(ids).getData();
        }
        List<EquStatus> equStatusList = new ArrayList<>();
        for (EquStatusViewItem equStatusViewItem : data) {
            EquStatus equStatus = new EquStatus();
            Short status = Integer.valueOf(equStatusViewItem.getStatus()).shortValue();
            equStatus.setNodeId(equStatusViewItem.getNodeId());
            equStatus.setStatus(DeviceStatus.getInstance().getNameByValue(status) + "/" + status);
            equStatus.setOnline(equStatusViewItem.getOnline());
            equStatus.setUpdateTime(DateTimeUtil.formatDate(equStatusViewItem.getUpdateTime()));
            equStatus.setName(textMap.get(equStatusViewItem.getNodeId()));
            equStatusList.add(equStatus);
        }
        return Result.success(equStatusList);
    }

//    @ApiOperation("模式上传信息")
//    @PostMapping("modeUpload")
//    public Result<List<ModeUploadInfo>> getModeUpload(@RequestParam Long nodeId) {
//        List<TmoModeUploadInfo> tmoModeUploadInfos = modeService.getModeUpload(nodeId);
//        List<ModeUploadInfo> modeUploadInfos = new ArrayList<>();
//        for (TmoModeUploadInfo tmoModeUploadInfo : tmoModeUploadInfos) {
//            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
//            modeUploadInfo.setLineName(topologyService.getNodeText(tmoModeUploadInfo.getLineId().longValue())
//                    .getData());
//            modeUploadInfo.setStationName(topologyService.getNodeText(tmoModeUploadInfo.getStationId().longValue())
//                    .getData());
//            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
//            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo
//                    .getModeCode())));
//            modeUploadInfos.add(modeUploadInfo);
//        }
//        return Result.success(modeUploadInfos);
//    }

//    @ApiOperation("模式广播信息")
//    @PostMapping("modeBroadcast")
//    public Result<List<ModeBroadcastInfo>> getModeBroadcast() {
//        List<TmoModeBroadcast> tmoModeBroadcasts = modeService.getModeBroadcast();
//        List<ModeBroadcastInfo> modeBroadcastInfos = new ArrayList<>();
//        for (TmoModeBroadcast tmoModeBroadcast : tmoModeBroadcasts) {
//            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
//            modeBroadcastInfo.setName(topologyService.getNodeText(tmoModeBroadcast.getNodeId()).getData());
//            modeBroadcastInfo.setSourceName(topologyService.getNodeText(tmoModeBroadcast.getStationId().longValue())
//                    .getData());
//            modeBroadcastInfo.setTargetName(topologyService.getNodeText(tmoModeBroadcast.getTargetId()).getData());
//            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
//            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast
//                    .getModeCode())));
//            modeBroadcastInfos.add(modeBroadcastInfo);
//        }
//        return Result.success(modeBroadcastInfos);
//    }

//    @ApiOperation("设备事件列表")
//    @PostMapping("deviceEvent")
//    public Result<List<EquEvent>> getDeviceEvent(@RequestBody DeviceEventCondition condition) {
//        List<TmoEquStatusCur> tmoEquStatusCurs = modeService.getEquStatusList(condition);
//        List<EquEvent> equEvents = new ArrayList<>();
//        for (TmoEquStatusCur tmoEquStatusCur : tmoEquStatusCurs) {
//            EquEvent equEvent = new EquEvent();
//            equEvent.setApplyDevice(tmoEquStatusCur.getApplyDevice());
//            equEvent.setItem(tmoEquStatusCur.getItem1());
//            equEvent.setNodeName(topologyService.getNodeText(tmoEquStatusCur.getNodeId()).getData());
//            equEvent.setOccurTime(DateTimeUtil.formatDate(tmoEquStatusCur.getOccurTime()));
//            equEvent.setStatusName(tmoEquStatusCur.getStatusName() + "/" + tmoEquStatusCur.getStatusId());
//            equEvent.setStatusDesc(tmoEquStatusCur.getStatusDesc() + "/" + tmoEquStatusCur.getStatusValue());
//            equEvents.add(equEvent);
//        }
//        return Result.success(equEvents);
//    }

//    @ApiOperation("监视设备")
//    @PostMapping("deviceDetail")
//    public Result getDeviceDetail(@RequestParam Long nodeId) {
//        return monitorService.getDeviceDetail(nodeId);
//    }

//    @ApiOperation("钱箱票箱")
//    @PostMapping("boxDetail")
//    public Result getBoxDetail(@RequestParam Long nodeId) {
//        return monitorService.getBoxDetail(nodeId);
//    }

    @ApiOperation("获取监控树")
    @PostMapping("tree")
    @JsonView(NodeItem.monitor.class)
    public Result<NodeItem> getMonitorTree(@RequestBody MonitorTreeCondition condition) {
        return nodeTreeService.getMonitorTree(condition);
    }

    @Autowired
    public void setMetroNodeStatusService(IMetroNodeStatusService metroNodeStatusService) {
        this.metroNodeStatusService = metroNodeStatusService;
    }

    @Autowired
    public void setModeService(ModeService modeService) {
        this.modeService = modeService;
    }

    @Autowired
    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    @Autowired
    public void setNodeTreeService(NodeTreeService nodeTreeService) {
        this.nodeTreeService = nodeTreeService;
    }

}
