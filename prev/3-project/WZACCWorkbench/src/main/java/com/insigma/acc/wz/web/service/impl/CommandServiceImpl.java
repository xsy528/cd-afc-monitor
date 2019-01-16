package com.insigma.acc.wz.web.service.impl;

import com.insigma.acc.wz.define.WZCommandType;
import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.web.exception.ErrorCode;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.CommandService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.action.CommandResult;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.service.ILogService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Ticket: 命令服务实现类
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
public class CommandServiceImpl extends CommandActionHandler implements CommandService {

    private WZBaseLogModule logModule = new WZBaseLogModule();
    private ILogService synLogService = logModule.getLogService(WZACCLogModuleCode.MODULE_MONITOR + "");

    @Override
    public Result<List<CommandResult>> sendChangeModeCommand(List<Long> nodeIds, int command){

        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        int[] arg = new int[]{-1,-1};
        String name = "";
        for(PairValue<Object,String> pv:AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL)){
            if (Integer.parseInt(pv.getKey().toString())==command){
                arg[0]=command;
                name=pv.getValue();
                break;
            }
        }
        if (arg[0]==-1){
            for(PairValue<Object,String> pv:AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND)){
                if (Integer.parseInt(pv.getKey().toString())==command){
                    arg[0]=command;
                    name=pv.getValue();
                    break;
                }
            }
        }
        if (arg[0]==-1){
            for(PairValue<Object,String> pv:AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN)){
                if (Integer.parseInt(pv.getKey().toString())==command){
                    arg[0]=command;
                    name=pv.getValue();
                    break;
                }
            }
        }
        if (arg[1]==-1){
            for(PairValue<Object,String> pv:AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY)){
                if (Integer.parseInt(pv.getKey().toString())==command){
                    arg[1]=command;
                    name=pv.getValue();
                    break;
                }
            }
        }

        if (-1 == arg[0]) {
            return Result.error(ErrorCode.NO_MODE_SELECT);
        }

        if (-2 == arg[0]) {
            return Result.error(ErrorCode.MODE_NOT_EXISTS);
        }

//        if (!authority()) {
//            return Result.error(ErrorCode.NOPERMISSION);
//        }

        name = StringUtils.defaultIfEmpty(name,"模式")+"切换命令";

        List<CommandResult> commandResults;
        //先判断通信服务器连接是否正常
        try {
            commandService.alive();
        } catch (Exception e) {
            if (logService != null) {
                logService.doBizErrorLog("发送命令失败：工作台与通信服务器离线。", e);
            }
            return Result.error(ErrorCode.COMMAND_SERVICE_NOT_CONNECTED);
        }
        commandResults = send(null,CommandType.CMD_CHANGE_MODE, name, arg, stationIds,
                AFCCmdLogType.LOG_MODE.shortValue());
        if (commandResults==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }else{
            return Result.success(commandResults);
        }
    }

    @Override
    public Result<List<CommandResult>> sendModeQueryCommand(List<Long> nodeIds){
        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

//        if (!authority()) {
//            return;
//        }

        List<CommandResult> commandResults = send(null, CommandType.CMD_QUERY_MODE, "模式查询命令",
                null, stationIds, AFCCmdLogType.LOG_MODE.shortValue());
        if (commandResults==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }else{
            return Result.success(commandResults);
        }
    }

    @Override
    public Result<List<CommandResult>> sendTimeSyncCommand(List<Long> nodeIds){
        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        //TODO 需要解释
        if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
            int i = 0;
            for (; i < stationIds.size(); i++) {
                MetroNode node = stationIds.get(i);
                if (node instanceof MetroStation) {
                    break;
                }
            }
            if (i < stationIds.size()) {
                stationIds.remove(i);
            }
        }

//        if (!authority()) {
//            return;
//        }

        List<CommandResult> commandResults = send(null, CommandType.CMD_TIME_SYNC, "时间同步命令",
                null, stationIds, AFCCmdLogType.LOG_TIME_SYNC.shortValue());
        if (commandResults==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }else{
            return Result.success(commandResults);
        }
    }

    @Override
    public Result<List<CommandResult>> sendNodeControlCommand(List<Long> nodeIds, short command){
        List<MetroNode> deviceIds = getDeviceNodeFromIds(nodeIds);
        if (deviceIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        List<CommandResult> commandResults = send(null, WZCommandType.COM_SLE_CONTROL_CMD, "设备控制命令",
                null, deviceIds, command);
        if (commandResults==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }else{
            return Result.success(commandResults);
        }
    }

    /**
     *  从传过来的节点id中获取车站节点
     * @param nodeIds 节点id数组
     * @return 车站节点数组
     */
    public List<MetroNode> getStationNodeFromIds(List<Long> nodeIds){
        if (nodeIds==null||nodeIds.isEmpty()){
            return null;
        }
        // 只留下车站节点
        List<MetroNode> stationIds = new ArrayList<>();
        for (Long id:nodeIds){
            MetroNode metroNode = AFCApplication.getNode(id);
            if (metroNode instanceof MetroStation) {
                stationIds.add(metroNode);
            }
        }
        if (stationIds == null || stationIds.isEmpty()) {
            return null;
        }
        return stationIds;
    }

    /**
     * 从id中获取设备id
     * @param nodeIds id数组
     * @return 设备节点数组
     */
    public List<MetroNode> getDeviceNodeFromIds(List<Long> nodeIds){
        if (nodeIds==null||nodeIds.isEmpty()){
            return null;
        }
        // 只留下设备节点
        List<MetroNode> deviceIds = new ArrayList<>();
        for (Long id:nodeIds){
            MetroNode metroNode = AFCApplication.getNode(id);
            if (metroNode instanceof MetroDevice) {
                deviceIds.add(metroNode);
            }
        }
        if (deviceIds == null || deviceIds.isEmpty()) {
            return null;
        }
        return deviceIds;
    }

}
