package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCCmdLogType;
import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.constant.dic.XZCommandType;
import com.insigma.afc.monitor.constant.dic.XZDeviceType;
import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.afc.monitor.thread.CommandSendTask;
import com.insigma.afc.monitor.thread.CommandThreadPoolExecutor;
import com.insigma.commons.dic.PairValue;
import com.insigma.commons.util.DateTimeUtil;
import com.insigma.ms.rmi.CmdHandlerResult;
import com.insigma.ms.rmi.CommandType;
import com.insigma.ms.rmi.ICommandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Result<List<CommandResult>> sendChangeModeCommand(List<Long> nodeIds, int command) {

        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        int[] arg = new int[]{-1, -1};
        String name = "";
        for (PairValue<Object, String> pv : AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL)) {
            if (Integer.parseInt(pv.getKey().toString()) == command) {
                arg[0] = command;
                name = pv.getValue();
                break;
            }
        }
        if (arg[0] == -1) {
            for (PairValue<Object, String> pv : AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND)) {
                if (Integer.parseInt(pv.getKey().toString()) == command) {
                    arg[0] = command;
                    name = pv.getValue();
                    break;
                }
            }
        }
        if (arg[0] == -1) {
            for (PairValue<Object, String> pv : AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN)) {
                if (Integer.parseInt(pv.getKey().toString()) == command) {
                    arg[0] = command;
                    name = pv.getValue();
                    break;
                }
            }
        }
        if (arg[1] == -1) {
            for (PairValue<Object, String> pv : AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY)) {
                if (Integer.parseInt(pv.getKey().toString()) == command) {
                    arg[1] = command;
                    name = pv.getValue();
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
        return Result.success(send(CommandType.CMD_CHANGE_MODE, name, arg, stationIds,
                AFCCmdLogType.LOG_MODE.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendModeQueryCommand(List<Long> nodeIds) {
        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }
        return Result.success(send(CommandType.CMD_QUERY_MODE, "模式查询命令",
                null, stationIds, AFCCmdLogType.LOG_MODE.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendTimeSyncCommand(List<Long> nodeIds) {
        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }
        return Result.success(send(CommandType.CMD_TIME_SYNC, "时间同步命令",
                null, stationIds, AFCCmdLogType.LOG_TIME_SYNC.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendMapSyncCommand(List<Long> lineIds) {
        CmdHandlerResult command = null;
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
        command = rmiGenerateFiles();
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
    public Result<List<CommandResult>> sendNodeControlCommand(List<Long> nodeIds, short command) {
        List<MetroNode> deviceIds = getDeviceNodeFromIds(nodeIds);
        if (deviceIds == null) {
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        return Result.success(send(XZCommandType.COM_SLE_CONTROL_CMD, "设备控制命令",
                null, deviceIds, command));
    }

    @Override
    public Result<List<CommandResult>> sendQueryBoxCommand(Long nodeId) {
        MetroDevice device = topologyService.getDeviceNode(nodeId).getData();
        List<MetroNode> ids = new ArrayList<>();
        ids.add(device);
        List<CommandResult> commandResults = new ArrayList<>();
        if (device.getDeviceType() == XZDeviceType.TVM) {
            commandResults.addAll(send(CommandType.CMD_QUERY_MONEY_BOX, "设备钱箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        if (device.getDeviceType() == XZDeviceType.TVM
                || device.getDeviceType() == XZDeviceType.POST
                || device.getDeviceType() == XZDeviceType.ENG
                || device.getDeviceType() == XZDeviceType.EXG
                || device.getDeviceType() == XZDeviceType.REG) {
            commandResults.addAll(send(XZCommandType.CMD_QUERY_TICKET_BOX, "设备票箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        return Result.success(commandResults);
    }

    /**
     * 从传过来的节点id中获取车站节点
     *
     * @param nodeIds 节点id数组
     * @return 车站节点数组
     */
    private List<MetroNode> getStationNodeFromIds(List<Long> nodeIds) {
        if (nodeIds == null || nodeIds.isEmpty()) {
            return null;
        }
        // 只留下车站节点
        List<MetroNode> stationIds = new ArrayList<>();
        for (Long id : nodeIds) {
            MetroStation metroNode = topologyService.getStationNode(id.intValue()).getData();
            if (metroNode!=null) {
                stationIds.add(metroNode);
            }
        }
        if (stationIds.isEmpty()) {
            return null;
        }
        return stationIds;
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

    private List<CommandResult> send(int id, String name, Object arg, List<MetroNode> nodes, short cmdType) {
        if (nodes == null) {
            return null;
        }
        List<Future<TmoCmdResult>> futures = new ArrayList<>();
        for (MetroNode node : nodes) {
            futures.add(threadPoolExecutor.submit(new CommandSendTask(id, name, arg, cmdType,node,rmiCommandService)));
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
                    commandResult.setOccurTime(DateTimeUtil.formatCurrentDateToString("yyyy-MM-dd HH:mm:ss"));
                    if (arg != null) {
                        commandResult.setArg(arg.toString());
                    } else {
                        commandResult.setArg("无");
                    }
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
