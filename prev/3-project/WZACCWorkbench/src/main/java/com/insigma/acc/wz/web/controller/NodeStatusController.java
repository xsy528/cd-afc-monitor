package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.insigma.acc.wz.monitor.service.WZACCMetroNodeStatusService;
import com.insigma.acc.wz.monitor.service.WZModeService;
import com.insigma.acc.wz.web.exception.ErrorCode;
import com.insigma.acc.wz.web.model.vo.*;
import com.insigma.acc.wz.web.service.NodeStatusService;
import com.insigma.acc.wz.web.util.JsonUtils;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Ticket: 节点状态控制器
 *
 * @author xuzhemin
 * 2018-12-28:18:06
 */
public class NodeStatusController extends BaseMultiActionController{

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusController.class);

    static Map<String,String> methodMapping = new HashMap<>();
    static{
        methodMapping.put("/monitor/query/stationStatus","getStationStatus");
        methodMapping.put("/monitor/query/deviceStatus","getDeviceStatus");
        methodMapping.put("/monitor/query/modeUpload","getModeUpload");
        methodMapping.put("/monitor/query/modeBroadcast","getModeBroadcast");
        methodMapping.put("/monitor/query/deviceEvent","getDeviceEvent");
        methodMapping.put("/monitor/query/deviceStatusType","getDeviceStatusType");
    }

    private WZACCMetroNodeStatusService metroNodeStatusService;
    private WZModeService modeService;
    private NodeStatusService nodeStatusService;

    @Autowired
    public NodeStatusController(WZACCMetroNodeStatusService metroNodeStatusService, WZModeService modeService,
                                NodeStatusService nodeStatusService) {
        super(methodMapping);
        this.metroNodeStatusService = metroNodeStatusService;
        this.modeService = modeService;
        this.nodeStatusService = nodeStatusService;
    }

    //车站状态列表
    public void getStationStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");

        JsonNode jsonNode = getBody(request);
        try(PrintWriter writer = response.getWriter()){
            if (jsonNode==null){
                writer.println(JsonUtils.parseObject(Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND)));
                return;
            }
            List<Integer> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")){
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
            writer.println(JsonUtils.parseObject(Result.success(stationStatusList)));
        }
    }

    //设备状态类型列表
    public void getDeviceStatusType(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        try(PrintWriter printWriter=response.getWriter()) {
            printWriter.println(JsonUtils.parseObject(Result.success(DeviceStatus.getInstance().getByGroup("1"))));
        }catch (IOException e){
            LOGGER.error("",e);
        }
    }

    //模式上传信息
    public void getModeUpload(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        JsonNode jsonNode = getBody(request);
        long nodeId = jsonNode.get("nodeId")==null?-1:jsonNode.get("nodeId").longValue();
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
        try(PrintWriter printWriter=response.getWriter()){
             printWriter.println(JsonUtils.parseObject(Result.success(modeUploadInfos)));
        }catch (IOException e){
            LOGGER.error("",e);
        }
    }

    //模式广播信息
    public void getModeBroadcast(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
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
        try(PrintWriter printWriter=response.getWriter()){
            printWriter.println(JsonUtils.parseObject(Result.success(modeBroadcastInfos)));
        }catch (IOException e){
            LOGGER.error("",e);
        }
    }

    //设备状态列表
    public void getDeviceStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");

        JsonNode jsonNode = getBody(request);
        try(PrintWriter writer = response.getWriter()){
            if (jsonNode == null) {
                writer.write(JsonUtils.parseObject(Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND)));
                return;
            }
            List<Object> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")){
                idList.add(id.longValue());
            }
            List<Short> statusList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("status")){
                statusList.add(id.shortValue());
            }
            Date startTime = new Date(Long.valueOf(jsonNode.get("startTime").textValue()));
            Date endTime = new Date(Long.valueOf(jsonNode.get("endTime").textValue()));
            EquStatusFilterForm filterForm = new EquStatusFilterForm();
            filterForm.setStartTime(startTime);
            filterForm.setEndTime(endTime);
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
            String json = JsonUtils.parseObject(Result.success(equStatusList));
            writer.println(json);
        }
    }

    //设备事件列表
    public void getDeviceEvent(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        JsonNode jsonNode = getBody(request);
        try(PrintWriter writer = response.getWriter()) {
            if (jsonNode == null) {
                writer.write(JsonUtils.parseObject(Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND)));
                return;
            }
            EventFilterForm eventFilterForm = new EventFilterForm();
            List<Object> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")){
                idList.add(id.longValue());
            }
            List<Short> devTypeList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("devType")){
                devTypeList.add(id.shortValue());
            }
            eventFilterForm.setSelections(idList);
            eventFilterForm.setEquTypeList(devTypeList);

            int maxCount = jsonNode.get("maxCount")==null?100:jsonNode.get("maxCount").intValue();
            eventFilterForm.setPageSize(maxCount);

            String orderField = jsonNode.get("orderField")==null?"OCCUR_TIME":jsonNode.get("orderField").textValue();
            eventFilterForm.setOrderField(orderField);

            String orderType = jsonNode.get("orderType")==null?"ASC":jsonNode.get("orderType").textValue();
            if (!(orderType.equals("ASC")||orderType.equals("AESC"))){
                orderType="ASC";
            }
            eventFilterForm.setOrderType(orderType);

            Date startTime = new Date(Long.valueOf(jsonNode.get("startTime").textValue()));
            Date endTime = new Date(Long.valueOf(jsonNode.get("endTime").textValue()));
            eventFilterForm.setStartTime(startTime);
            eventFilterForm.setEndTime(endTime);

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
            writer.println(JsonUtils.parseObject(Result.success(equEvents)));
        }catch (IOException e){
            LOGGER.error("",e);
        }

    }

    private JsonNode getBody(HttpServletRequest request){
        JsonNode jsonNode = null;
        try(InputStream inputStream = request.getInputStream()){
            jsonNode = JsonUtils.generateObject(inputStream);
        }catch (IOException e){
            LOGGER.error("从请求获取jsonbody异常",e);
        }
        return jsonNode;
    }

}
