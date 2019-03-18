package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.*;
import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.afc.monitor.thread.CommandSendTask;
import com.insigma.afc.monitor.thread.CommandThreadPoolExecutor;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.xz.rmi.ModeUpdateForm;
import com.insigma.commons.dic.PairValue;
import com.insigma.afc.workbench.rmi.ICommandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Ticket: 命令服务实现类
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
@Service
public class CommandServiceImpl implements CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);

    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private int keepAliveTime = 60;
    private CommandThreadPoolExecutor threadPoolExecutor = new CommandThreadPoolExecutor(corePoolSize,maxPoolSize,
            keepAliveTime);

    private TmoCmdResultDao tmoCmdResultDao;
    private TopologyService topologyService;
    private ICommandService rmiCommandService;

    @Autowired
    public CommandServiceImpl(TmoCmdResultDao tmoCmdResultDao, TopologyService topologyService,
                              ICommandService rmiCommandService) {
        this.tmoCmdResultDao = tmoCmdResultDao;
        this.topologyService = topologyService;
        this.rmiCommandService = rmiCommandService;
    }

    @Override
    public Result<List<CommandResult>> sendChangeModeCommand(List<Long> nodeIds, Integer mode) {

        List<MetroStation> targetIds = getStationNodeFromIds(nodeIds);
        if (targetIds==null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        Integer sendMode = null;
        String name = null;
        List<String> modeTypes = Arrays.asList(AFCModeCode.MODE_SIGN_NORMAL,AFCModeCode.MODE_SIGN_DESCEND,
                AFCModeCode.MODE_SIGN_BREAKDOWN,AFCModeCode.MODE_SIGN_URGENCY);
        for (String modeType:modeTypes){
            if (sendMode!=null){
                break;
            }
            for (PairValue<Object, String> pv : AFCModeCode.getInstance().getByGroup(modeType)) {
                if (pv.getKey().equals(mode)) {
                    sendMode = mode;
                    name = pv.getValue();
                    break;
                }
            }
        }

        if (sendMode == null) {
            return Result.error(ErrorCode.NO_MODE_SELECT);
        }

        name = StringUtils.defaultIfEmpty(name, "模式") + "切换命令";

        //先判断通信服务器连接是否正常
        try {
            rmiCommandService.alive();
        } catch (Exception e) {
//            if (logService != null) {
//                logService.doBizErrorLog("发送命令失败：工作台与通信服务器离线。", e);
//            }
            return Result.error(ErrorCode.COMMAND_SERVICE_NOT_CONNECTED);
        }
        Map<Long,Object> modeUpdateForms = new HashMap<>();
        for (MetroNode metroNode:targetIds){
            ModeUpdateForm modeUpdateForm = new ModeUpdateForm();
            modeUpdateForm.setDeviceId(metroNode.id());
            modeUpdateForm.setModeCode(sendMode);
            modeUpdateForms.put(metroNode.id(),modeUpdateForm);
        }
        return Result.success(send(CommandType.CMD_MODE_UPDATE, name, modeUpdateForms,targetIds,
                AFCCmdLogType.LOG_MODE.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendModeQueryCommand(List<Long> nodeIds) {
        List<MetroStation> targetIds = getStationNodeFromIds(nodeIds);
        if (targetIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }
        Map<Long,Object> args = new HashMap<>();
        for (MetroNode metroNode:targetIds){
            args.put(metroNode.id(),metroNode.id());
        }
        return Result.success(send(CommandType.CMD_MODE_QUERY,
                CommandType.getInstance().getNameByValue(CommandType.CMD_MODE_QUERY), args, targetIds,
                AFCCmdLogType.LOG_MODE.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendTimeSyncCommand(List<Long> nodeIds) {
        List<MetroLine> targetIds = getLineNodeFromIds(nodeIds);
        if (targetIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }
        return Result.success(send(CommandType.CMD_TIME_SYNC,
                CommandType.getInstance().getNameByValue(CommandType.CMD_TIME_SYNC), null, targetIds,
                AFCCmdLogType.LOG_TIME_SYNC.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendMapSyncCommand(List<Long> lineIds) {
        List<MetroNode> ids = new ArrayList<>();
        for (Long lineId : lineIds) {
            MetroLine metroNode = topologyService.getLineNode(lineId.shortValue()).getData();
            if (metroNode!=null) {
                ids.add(metroNode);
                logger.info("地图同步数据 ：" + metroNode.name());
            }
        }
        if (ids.isEmpty()) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }
        CmdHandlerResult command = rmiGenerateFiles();
        if (command.isOK) {
            return Result.success(send(CommandType.CMD_SYNC_MAP, "地图同步命令", null, ids,
                    AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        return Result.error(ErrorCode.UNKNOW_ERROR);
    }

    private CmdHandlerResult rmiGenerateFiles() {

        CmdHandlerResult command = new CmdHandlerResult();
        try {
            rmiCommandService.alive();
        } catch (Exception e) {
            command.messages.add("发送地图同步失败：工作台与通信服务器离线。" + e.getMessage());
            command.isOK = false;
            return command;
        }
        //TODO 需要获取当前用户id
        String userId = "0";
        command = rmiCommandService.command(CommandType.CMD_EXPORT_MAP, userId, 1L);
        return command;
    }

    @Override
    public Result<List<CommandResult>> sendNodeControlCommand(List<Long> nodeIds, Short command) {
        List<MetroNode> deviceIds = getDeviceNodeFromIds(nodeIds);
        if (deviceIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        return Result.success(send(CommandType.COM_SLE_CONTROL_CMD, "设备控制命令",
                null, deviceIds, command));
    }

    @Override
    public Result<List<CommandResult>> sendQueryBoxCommand(Long nodeId) {
        MetroDevice device = topologyService.getDeviceNode(nodeId).getData();
        List<MetroNode> ids = new ArrayList<>();
        ids.add(device);
        List<CommandResult> commandResults = new ArrayList<>();
        Short deviceType = device.getDeviceType();
        if (AFCDeviceType.TVM.equals(deviceType)) {
            commandResults.addAll(send(CommandType.CMD_QUERY_MONEY_BOX, "设备钱箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        if (AFCDeviceType.TVM.equals(deviceType) || AFCDeviceType.POST.equals(deviceType)
                || AFCDeviceType.GATE.equals(deviceType)) {
            commandResults.addAll(send(CommandType.CMD_QUERY_TICKET_BOX, "设备票箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        return Result.success(commandResults);
    }

    /**
     * 从传过来的节点id中获取目标节点
     *
     * @param nodeIds 节点id数组
     * @return 目标节点数组
     */
    private List<MetroStation> getStationNodeFromIds(List<Long> nodeIds) {
        if (nodeIds == null || nodeIds.isEmpty()) {
            return null;
        }
        // 只留下目标节点
        List<MetroStation> targetdIds = new ArrayList<>();
        for (Long id : nodeIds) {
            MetroStation metroNode = topologyService.getStationNode(id.intValue()).getData();
            if (metroNode!=null) {
                targetdIds.add(metroNode);
            }
        }
        if (targetdIds.isEmpty()) {
            return null;
        }
        return targetdIds;
    }

    /**
     * 从传过来的节点id中获取线路节点
     *
     * @param nodeIds 节点id数组
     * @return 线路节点数组
     */
    private List<MetroLine> getLineNodeFromIds(List<Long> nodeIds) {
        if (nodeIds == null || nodeIds.isEmpty()) {
            return null;
        }
        // 只留下目标节点
        List<MetroLine> targetdIds = new ArrayList<>();
        for (Long id : nodeIds) {
            MetroLine metroNode = topologyService.getLineNode(id.shortValue()).getData();
            if (metroNode!=null) {
                targetdIds.add(metroNode);
            }
        }
        if (targetdIds.isEmpty()) {
            return null;
        }
        return targetdIds;
    }


    /**
     * 从车站节点中获取线路节点
     * @param metroStations 车站节点
     * @return 线路节点
     */
    private List<MetroLine> getLineNodeFromStations(List<MetroStation> metroStations) {
        if (metroStations == null || metroStations.isEmpty()) {
            return null;
        }
        // 只留下目标节点
        Set<Short> targetdIds = new HashSet<>();
        for (MetroStation station : metroStations) {
            targetdIds.add(station.getLineId());
        }
        List<MetroLine> metroLines = new ArrayList<>();
        for (Short lineId:targetdIds){
            MetroLine metroNode = topologyService.getLineNode(lineId).getData();
            if (metroNode!=null) {
                metroLines.add(metroNode);
            }
        }
        if (metroLines.isEmpty()) {
            return null;
        }
        return metroLines;
    }

    /**
     * 从id中获取设备id
     *
     * @param nodeIds id数组
     * @return 设备节点数组
     */
    private List<MetroNode> getDeviceNodeFromIds(List<Long> nodeIds) {
        if (nodeIds == null || nodeIds.isEmpty()) {
            return null;
        }
        // 只留下设备节点
        List<MetroNode> deviceIds = new ArrayList<>();
        for (Long id : nodeIds) {
            MetroDevice metroNode = topologyService.getDeviceNode(id).getData();
            if (metroNode!=null) {
                deviceIds.add(metroNode);
            }
        }
        if (deviceIds.isEmpty()) {
            return null;
        }
        return deviceIds;
    }

    /**
     * 发送命令
     * @param id 命令id
     * @param name 命令名称
     * @param args 命令参数
     * @param nodes 目标节点
     * @param cmdType 命令类型
     * @return 命令执行结果
     */
    private List<CommandResult> send(int id, String name, Map<Long,Object> args, List<? extends MetroNode> nodes,
                                     Short cmdType) {
        if (nodes == null) {
            return null;
        }
        List<Future<TmoCmdResult>> futures = new ArrayList<>();
        for (MetroNode node : nodes) {
            Object arg = null;
            if (args!=null){
                arg = args.get(node.id());
            }
            futures.add(threadPoolExecutor.submit(new CommandSendTask(id, name, arg,
                    cmdType,node,rmiCommandService)));
        }
        List<CommandResult> results = new ArrayList<>();
        List<TmoCmdResult> tmoCmdResults = new ArrayList<>();
        for (Future<TmoCmdResult> future:futures){
            try {
                TmoCmdResult tmoCmdResult = future.get();
                if (tmoCmdResult!=null){
                    CommandResult commandResult = new CommandResult();
                    commandResult.setId(topologyService.getNodeText(tmoCmdResult.getNodeId()).getData());
                    commandResult.setCmdName(tmoCmdResult.getCmdName());
                    commandResult.setResult(tmoCmdResult.getCmdResult());
                    commandResult.setCmdResult(tmoCmdResult.getRemark());
                    commandResult.setOccurTime(new Date());
                    commandResult.setOperatorId(tmoCmdResult.getOperatorId());
                    tmoCmdResults.add(tmoCmdResult);
                    results.add(commandResult);
                }
            } catch (ExecutionException e) {
                logger.error("发送命令异常",e);
            } catch (InterruptedException e) {
                logger.error("发送命令被中断",e);
            }
        }
        tmoCmdResultDao.saveAll(tmoCmdResults);
        return results;
    }

}
