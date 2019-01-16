package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.insigma.acc.wz.monitor.service.WZACCMetroNodeStatusService;
import com.insigma.acc.wz.monitor.service.WZModeService;
import com.insigma.acc.wz.web.exception.ErrorCode;
import com.insigma.acc.wz.web.model.vo.*;
import com.insigma.acc.wz.web.service.NodeStatusService;
import com.insigma.acc.wz.web.util.HttpUtils;
import com.insigma.acc.wz.web.util.NodeUtils;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ticket: 节点状态控制器
 *
 * @author xuzhemin
 * 2018-12-28:18:06
 */
public class NodeStatusController extends BaseMultiActionController{

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusController.class);

    static{
        methodMapping.put("/monitor/query/stationStatus","getStationStatus");
        methodMapping.put("/monitor/query/deviceStatus","getDeviceStatus");
        methodMapping.put("/monitor/query/modeUpload","getModeUpload");
        methodMapping.put("/monitor/query/modeBroadcast","getModeBroadcast");
        methodMapping.put("/monitor/query/deviceEvent","getDeviceEvent");
        methodMapping.put("/monitor/query/deviceDetail","getDeviceDetail");
        methodMapping.put("/monitor/query/boxDetail","getBoxDetail");
    }

    private WZACCMetroNodeStatusService metroNodeStatusService;
    private WZModeService modeService;
    private NodeStatusService nodeStatusService;

    @Autowired
    public NodeStatusController(WZACCMetroNodeStatusService metroNodeStatusService, WZModeService modeService,
                                NodeStatusService nodeStatusService) {
        this.metroNodeStatusService = metroNodeStatusService;
        this.modeService = modeService;
        this.nodeStatusService = nodeStatusService;
    }

    //车站状态列表
    public Result<List<StationStatus>> getStationStatus(HttpServletRequest request, HttpServletResponse response) {
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Integer> idList = new ArrayList<>();
        JsonNode nodeIds = jsonNode.get("nodeIds");
        if (nodeIds==null||!nodeIds.isArray()){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }
        for(JsonNode id:nodeIds){
            idList.add(id.intValue());
        }
        List<StationStatustViewItem> data = metroNodeStatusService
                .getStationStatusView(null,idList.toArray(new Integer[idList.size()]));
        List<StationStatus> stationStatusList = new ArrayList<>();
        for (StationStatustViewItem stationStatustViewItem:data){
            StationStatus stationStatus = new StationStatus();
            stationStatus.setAlarmEvent(stationStatustViewItem.getAlarmEvent());
            stationStatus.setNormalEvent(stationStatustViewItem.getNormalEvent());
            stationStatus.setWarnEvent(stationStatustViewItem.getWarnEvent());
            stationStatus.setOnline(stationStatustViewItem.isOnline());
            stationStatus.setUpdateTime(DateTimeUtil.formatDate(stationStatustViewItem.getUpdateTime()));

            //设置车站状态
            Short status = Integer.valueOf(stationStatustViewItem.getStatus()).shortValue();
            stationStatus.setStatus(DeviceStatus.getInstance().getNameByValue(status) + "/" + status);
            stationStatus.setName(stationStatustViewItem.name());
            //设置车站模式/编号
            Integer mode = Long.valueOf(stationStatustViewItem.getMode()).intValue();
            stationStatus.setMode(AFCModeCode.getInstance().getModeText(mode));

            stationStatusList.add(stationStatus);
        }
        return Result.success(stationStatusList);
    }

    //模式上传信息
    public Result<List<ModeUploadInfo>> getModeUpload(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        long nodeId = jsonNode.get("nodeId").longValue();
        List<TmoModeUploadInfo> tmoModeUploadInfos = nodeStatusService.getModeUpload(nodeId);
        List<ModeUploadInfo> modeUploadInfos = new ArrayList<>();
        for (TmoModeUploadInfo tmoModeUploadInfo:tmoModeUploadInfos){
            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
            modeUploadInfo.setLineName(NodeUtils.getNodeText(tmoModeUploadInfo.getLineId()));
            modeUploadInfo.setStationName(NodeUtils.getNodeText(tmoModeUploadInfo.getStationId()));
            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo.getModeCode())));
            modeUploadInfos.add(modeUploadInfo);
        }
        return Result.success(modeUploadInfos);
    }

    //模式广播信息
    public Result<List<ModeBroadcastInfo>> getModeBroadcast(HttpServletRequest request, HttpServletResponse response){
        List<TmoModeBroadcast> tmoModeBroadcasts = nodeStatusService.getModeBroadcast();
        List<ModeBroadcastInfo> modeBroadcastInfos = new ArrayList<>();
        for (TmoModeBroadcast tmoModeBroadcast:tmoModeBroadcasts){
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setName(NodeUtils.getNodeText(tmoModeBroadcast.getNodeId()));
            modeBroadcastInfo.setSourceName(NodeUtils.getNodeText(tmoModeBroadcast.getStationId()));
            modeBroadcastInfo.setTargetName(NodeUtils.getNodeText(tmoModeBroadcast.getTargetId()));
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast.getModeCode())));
            modeBroadcastInfos.add(modeBroadcastInfo);
        }
        return Result.success(modeBroadcastInfos);
    }

    //设备状态列表
    public Result<List<EquStatus>> getDeviceStatus(HttpServletRequest request, HttpServletResponse response) {
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Object> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        List<Short> statusList = new ArrayList<>();
        if (jsonNode.get("status")!=null){
            for (JsonNode id : jsonNode.get("status")) {
                statusList.add(id.shortValue());
            }
        }
        EquStatusFilterForm filterForm = new EquStatusFilterForm();
        JsonNode startTimeNode = jsonNode.get("startTime");
        if (startTimeNode!=null){
            filterForm.setStartTime(new Date(Long.valueOf(startTimeNode.textValue())));
        }
        JsonNode endTimeNode = jsonNode.get("endTime");
        if (endTimeNode!=null) {
            filterForm.setEndTime(new Date(Long.valueOf(endTimeNode.textValue())));
        }
        filterForm.setSelections(idList);
        filterForm.setStatusLevelList(statusList);
        List<EquStatusViewItem> data = metroNodeStatusService.getEquStatusView(filterForm);
        List<EquStatus> equStatusList = new ArrayList<>();
        for (EquStatusViewItem equStatusViewItem:data){
            EquStatus equStatus = new EquStatus();
            equStatus.setName(equStatusViewItem.name());
            Short status = Integer.valueOf(equStatusViewItem.getStatus()).shortValue();
            equStatus.setStatus(DeviceStatus.getInstance().getNameByValue(status) + "/" + status);
            equStatus.setOnline(equStatusViewItem.isOnline());
            equStatus.setUpdateTime(DateTimeUtil.formatDate(equStatusViewItem.getUpdateTime()));
            equStatusList.add(equStatus);
        }
        return Result.success(equStatusList);
    }

    //设备事件列表
    public Result<List<EquEvent>> getDeviceEvent(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        List<Object> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        List<Object> devTypeList = new ArrayList<>();
        if (jsonNode.get("devType")!=null){
            for (JsonNode id : jsonNode.get("devType")) {
                devTypeList.add(id.shortValue());
            }
        }
        EventFilterForm eventFilterForm = new EventFilterForm();
        eventFilterForm.setSelections(idList);
        eventFilterForm.setEquTypeList(devTypeList);

        int maxCount = jsonNode.get("maxCount")==null?100:jsonNode.get("maxCount").intValue();
        eventFilterForm.setPageSize(maxCount);

        String orderField = jsonNode.get("orderField")==null?"occurTime":jsonNode.get("orderField").textValue();
        eventFilterForm.setOrderField(orderField);

        String orderType = jsonNode.get("orderType")==null?"ASC":jsonNode.get("orderType").textValue();
        if (!(orderType.equals("ASC")||orderType.equals("AESC"))){
            orderType="ASC";
        }
        eventFilterForm.setOrderType(orderType);

        JsonNode startTimeNode = jsonNode.get("startTime");
        if (startTimeNode!=null){
            eventFilterForm.setStartTime(new Date(Long.valueOf(startTimeNode.textValue())));
        }
        JsonNode endTimeNode = jsonNode.get("endTime");
        if (endTimeNode!=null) {
            eventFilterForm.setEndTime(new Date(Long.valueOf(endTimeNode.textValue())));
        }

        List<TmoEquStatusCur> tmoEquStatusCurs = modeService.getEquStatusList(eventFilterForm,0).getDatas();
        List<EquEvent> equEvents = new ArrayList<>();
        for (TmoEquStatusCur tmoEquStatusCur:tmoEquStatusCurs){
            EquEvent equEvent = new EquEvent();
            equEvent.setApplyDevice(tmoEquStatusCur.getApplyDevice());
            equEvent.setItem(tmoEquStatusCur.getItem1());
            equEvent.setNodeName(NodeUtils.getNodeText(tmoEquStatusCur.getNodeId()));
            equEvent.setOccurTime(DateTimeUtil.formatDate(tmoEquStatusCur.getOccurTime()));
            equEvent.setStatusName(tmoEquStatusCur.getStatusName()+"/"+tmoEquStatusCur.getStatusId());
            equEvent.setStatusDesc(tmoEquStatusCur.getStatusDesc()+"/"+tmoEquStatusCur.getStatusValue());
            equEvents.add(equEvent);
        }
        return Result.success(equEvents);
    }

    public Result getDeviceDetail(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        return nodeStatusService.getDeviceDetail(jsonNode.get("nodeId").longValue());
    }

    public Result getBoxDetail(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        return nodeStatusService.getBoxDetail(jsonNode.get("nodeId").longValue());
    }

}
