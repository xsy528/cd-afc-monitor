package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.log.constant.LogDefines;
import com.insigma.afc.log.service.ILogService;
import com.insigma.afc.monitor.constant.LogModuleCode;
import com.insigma.afc.monitor.constant.dic.*;
import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.dao.TmoModeBroadcastDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.topology.MetroDevice;
import com.insigma.afc.monitor.model.entity.topology.MetroLine;
import com.insigma.afc.monitor.model.entity.topology.MetroNode;
import com.insigma.afc.monitor.model.entity.topology.MetroStation;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.afc.monitor.thread.CommandSendTask;
import com.insigma.afc.monitor.thread.CommandThreadPoolExecutor;
import com.insigma.afc.monitor.util.ResultUtils;
import com.insigma.afc.security.util.SecurityUtils;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.afc.xz.rmi.ModeBroadcastForm;
import com.insigma.afc.xz.rmi.ModeUpdateForm;
import com.insigma.commons.dic.PairValue;
import com.insigma.commons.model.dto.Result;
import com.insigma.commons.properties.AppProperties;
import com.insigma.commons.util.NodeIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Ticket: 命令服务实现类
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
@Service
@EnableConfigurationProperties(AppProperties.class)
public class CommandServiceImpl implements CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);

    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private int keepAliveTime = 60;
    private CommandThreadPoolExecutor threadPoolExecutor = new CommandThreadPoolExecutor(corePoolSize, maxPoolSize,
            keepAliveTime);

    private TmoCmdResultDao tmoCmdResultDao;
    private TopologyService topologyService;
    private ICommandService rmiCommandService;
    private ILogService logService;

    private TmoModeBroadcastDao tmoModeBroadcastDao;
    private AppProperties appProperties;

    @Autowired
    public CommandServiceImpl(TmoCmdResultDao tmoCmdResultDao, TopologyService topologyService,
                              ICommandService rmiCommandService,ILogService logService,
                              TmoModeBroadcastDao tmoModeBroadcastDao, AppProperties appProperties) {
        this.tmoCmdResultDao = tmoCmdResultDao;
        this.topologyService = topologyService;
        this.rmiCommandService = rmiCommandService;
        this.logService = logService;
        this.appProperties = appProperties;
        this.tmoModeBroadcastDao = tmoModeBroadcastDao;
    }

    @Override
    public Result<List<CommandResultDTO>> sendChangeModeCommand(List<Long> nodeIds, Integer mode) {

        List<MetroStation> targetIds = getStationNodeFromIds(nodeIds);
        if (targetIds == null) {
            return ResultUtils.getResult(ErrorCode.NO_NODE_SELECT);
        }

        Integer sendMode = null;
        String name = null;
        List<String> modeTypes = Arrays.asList(AFCModeCode.MODE_SIGN_NORMAL, AFCModeCode.MODE_SIGN_DESCEND,
                AFCModeCode.MODE_SIGN_BREAKDOWN, AFCModeCode.MODE_SIGN_URGENCY);
        for (String modeType : modeTypes) {
            if (sendMode != null) {
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
            return ResultUtils.getResult(ErrorCode.NO_MODE_SELECT);
        }

        name = StringUtils.defaultIfEmpty(name, "模式") + "切换命令";

        //先判断通信服务器连接是否正常
        try {
            rmiCommandService.alive();
        } catch (Exception e) {
            logService.log(LogDefines.ERROR_LOG,"发送命令失败：工作台与通信服务器离线。",
                    SecurityUtils.getUserId(),SecurityUtils.getIp(),LogModuleCode.MODULE_MONITOR);
            return ResultUtils.getResult(ErrorCode.COMMAND_SERVICE_NOT_CONNECTED);
        }
        List<Future<TmoCmdResult>> results = new ArrayList<>();
        for (MetroNode metroNode : targetIds) {
            ModeUpdateForm modeUpdateForm = new ModeUpdateForm();
            modeUpdateForm.setDeviceId(metroNode.id());
            modeUpdateForm.setModeCode(sendMode);
            results.add(send(CommandType.CMD_CHANGE_MODE, name, modeUpdateForm, metroNode,
                    AFCCmdLogType.LOG_MODE.shortValue()));
        }
        return Result.success(saveResults(results));
    }

    @Override
    public Result<List<CommandResultDTO>> sendModeBroadcastCommand(List<Long> recordIds) {
        final short sendSuccess = 1;
        final short sendFailure = 2;
        if (recordIds==null||recordIds.isEmpty()){
            return Result.success(new ArrayList<>());
        }
        //查找模式广播记录
        List<TmoModeBroadcast> tmoModeBroadcasts = tmoModeBroadcastDao.findAllById(recordIds);
        //命令执行结果
        List<CommandResultDTO> results = new ArrayList<>();

        Map<Long,Future<TmoCmdResult>> modeBroadcastResultMap = new HashMap<>();
        for (TmoModeBroadcast modeBroadcast : tmoModeBroadcasts) {
            Long lineId = modeBroadcast.getTargetId();
            //构造命令参数
            ModeBroadcastForm modeBroadcastForm = new ModeBroadcastForm();
            List<ModeBroadcastForm.ModeStation> modeStations = new ArrayList<>();
            modeBroadcastForm.setModeStationList(modeStations);
            ModeBroadcastForm.ModeStation modeStation = new ModeBroadcastForm.ModeStation();
            modeStation.setStationId(modeBroadcast.getStationId());
            modeStation.setModeCode(modeBroadcast.getModeCode().intValue());
            modeStations.add(modeStation);
            //目标节点
            MetroLine target = topologyService.getLineNode(NodeIdUtils.nodeIdStrategy.getLineId(lineId)).getData();
            String name = "模式广播["+AFCModeCode.getInstance().getModeText(modeBroadcast.getModeCode().intValue())+"]";
            //发送命令
            Future<TmoCmdResult> resultFuture = send(CommandType.CMD_BROADCAST_MODE, name, modeBroadcastForm, target,
                    AFCCmdLogType.LOG_OTHER.shortValue());
            modeBroadcastResultMap.put(modeBroadcast.getRecordId(),resultFuture);
        }
        List<TmoCmdResult> tmoCmdResults = new ArrayList<>();
        //更新补发广播结果
        for(TmoModeBroadcast modeBroadcast : tmoModeBroadcasts){
            Future<TmoCmdResult> future = modeBroadcastResultMap.get(modeBroadcast.getRecordId());
            if (future!=null){
                try {
                    TmoCmdResult tmoCmdResult = future.get();
                    if (tmoCmdResult != null) {
                        CommandResultDTO commandResult = new CommandResultDTO();
                        commandResult.setId(topologyService.getNodeText(tmoCmdResult.getNodeId()).getData());
                        commandResult.setCmdName(tmoCmdResult.getCmdName());
                        commandResult.setResult(tmoCmdResult.getCmdResult());
                        commandResult.setCmdResult(tmoCmdResult.getRemark());
                        commandResult.setOccurTime(new Date());
                        commandResult.setOperatorId(tmoCmdResult.getOperatorId());
                        commandResult.setArg(tmoCmdResult.getArg()==null?"无":tmoCmdResult.getArg().toString());
                        tmoCmdResults.add(tmoCmdResult);
                        results.add(commandResult);

                        if (commandResult.getResult() == AFCCmdResultType.SEND_SUCCESSFUL) {
                            modeBroadcast.setBroadcastStatus(sendSuccess);
                        } else {
                            modeBroadcast.setBroadcastStatus(sendFailure);
                        }
                        modeBroadcast.setModeBroadcastTime(new Date());
                    }
                } catch (ExecutionException e) {
                    logger.error("发送命令异常", e);
                } catch (InterruptedException e) {
                    logger.error("发送命令被中断", e);
                }
            }
        }
        //保存命令执行结果
        tmoCmdResultDao.saveAll(tmoCmdResults);
        //更新补发广播结果
        tmoModeBroadcastDao.saveAll(tmoModeBroadcasts);
        return Result.success(results);
    }

    @Override
    public Result<List<CommandResultDTO>> sendModeQueryCommand(List<Long> nodeIds) {
        List<MetroStation> targetIds = getStationNodeFromIds(nodeIds);
        if (targetIds == null) {
            return ResultUtils.getResult(ErrorCode.NO_NODE_SELECT);
        }
        List<Future<TmoCmdResult>> results = new ArrayList<>();
        for (MetroStation target : targetIds) {
            results.add(send(CommandType.CMD_QUERY_MODE,
                    CommandType.getInstance().getNameByValue(CommandType.CMD_QUERY_MODE), target.id(), target,
                    AFCCmdLogType.LOG_MODE.shortValue()));
        }
        return Result.success(saveResults(results));
    }

    @Override
    public Result<List<CommandResultDTO>> sendTimeSyncCommand(List<Long> nodeIds) {
        List<MetroLine> targetIds = getLineNodeFromIds(nodeIds);
        if (targetIds == null) {
            return ResultUtils.getResult(ErrorCode.NO_NODE_SELECT);
        }
        return Result.success(send(CommandType.CMD_TIME_SYNC,
                CommandType.getInstance().getNameByValue(CommandType.CMD_TIME_SYNC), null, targetIds,
                AFCCmdLogType.LOG_TIME_SYNC.shortValue()));
    }

    @Override
    public Result<List<CommandResultDTO>> sendMapSyncCommand(List<Long> lineIds) {
        List<MetroNode> ids = new ArrayList<>();
        for (Long lineId : lineIds) {
            MetroLine metroNode = topologyService.getLineNode(lineId.shortValue()).getData();
            if (metroNode != null) {
                ids.add(metroNode);
                logger.info("地图同步数据 ：" + metroNode.name());
            }
        }
        if (ids.isEmpty()) {
            return ResultUtils.getResult(ErrorCode.NO_NODE_SELECT);
        }
        CmdHandlerResult command = rmiGenerateFiles();
        if (command.isOK) {
            return Result.success(send(CommandType.CMD_SYNC_MAP, "地图同步命令", null, ids,
                    AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        return ResultUtils.getResult(ErrorCode.UNKNOW_ERROR);
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
        command = rmiCommandService.command(CommandType.CMD_EXPORT_MAP, SecurityUtils.getUserId(),
                appProperties.getNodeNo());
        return command;
    }

    @Override
    public Result<List<CommandResultDTO>> sendNodeControlCommand(List<Long> nodeIds, Short command) {
        List<MetroNode> deviceIds = getDeviceNodeFromIds(nodeIds);
        if (deviceIds == null) {
            return ResultUtils.getResult(ErrorCode.NO_NODE_SELECT);
        }

        return Result.success(send(CommandType.CMD_EQU_CTRL, "设备控制命令",
                null, deviceIds, command));
    }

    @Override
    public Result<List<CommandResultDTO>> sendQueryBoxCommand(Long nodeId) {
        MetroDevice device = topologyService.getDeviceNode(nodeId).getData();
        List<MetroNode> ids = new ArrayList<>();
        ids.add(device);
        List<CommandResultDTO> commandResults = new ArrayList<>();
        Short deviceType = device.getDeviceType();
        if (AFCDeviceType.TVM.equals(deviceType)) {
            commandResults.addAll(send(CommandType.CMD_QUERY_TVM_MONEY_TICKET_BOX, "设备钱箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
//        if (AFCDeviceType.TVM.equals(deviceType) || AFCDeviceType.POST.equals(deviceType)
//                || AFCDeviceType.GATE.equals(deviceType)) {
//            commandResults.addAll(send(CommandType.CMD_QUERY_TICKET_BOX, "设备票箱查询命令",
//                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
//        }
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
            if (metroNode != null) {
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
            if (metroNode != null) {
                targetdIds.add(metroNode);
            }
        }
        if (targetdIds.isEmpty()) {
            return null;
        }
        return targetdIds;
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
            if (metroNode != null) {
                deviceIds.add(metroNode);
            }
        }
        if (deviceIds.isEmpty()) {
            return null;
        }
        return deviceIds;
    }

    /**
     * 发送相同命令给多个节点
     *
     * @param id      命令id
     * @param name    命令名称
     * @param arg    命令参数
     * @param targets   目标节点数组
     * @param cmdType 命令类型
     * @return 命令执行结果
     */
    private List<CommandResultDTO> send(int id, String name, Object arg, List<? extends MetroNode> targets,
                                        Short cmdType) {
        if (targets == null) {
            return null;
        }
        List<Future<TmoCmdResult>> futures = new ArrayList<>();
        String ip = SecurityUtils.getIp();
        Long sourceId = appProperties.getNodeNo();
        for (MetroNode target : targets) {
            futures.add(threadPoolExecutor.submit(new CommandSendTask(id, name, arg, cmdType, target,
                    rmiCommandService,logService,SecurityUtils.getUserId(),ip,sourceId)));
        }
        return saveResults(futures);
    }

    /**
     * 发送相同命令给单个节点
     *
     * @param id      命令id
     * @param name    命令名称
     * @param arg    命令参数
     * @param target   目标节点
     * @param cmdType 命令类型
     * @return 命令执行结果
     */
    private Future<TmoCmdResult> send(int id, String name, Object arg, MetroNode target, Short cmdType) {
        if (target==null){
            return null;
        }
        String ip = SecurityUtils.getIp();
        Long sourceId = appProperties.getNodeNo();
        return threadPoolExecutor.submit(new CommandSendTask(id, name, arg, cmdType, target,
                    rmiCommandService,logService,SecurityUtils.getUserId(),ip,sourceId));
    }

    /**
     * 保存命令执行结果
     * @param futures 结果
     * @return 命令执行结果数据
     */
    private List<CommandResultDTO> saveResults(List<Future<TmoCmdResult>> futures){
        List<CommandResultDTO> results = new ArrayList<>();
        List<TmoCmdResult> tmoCmdResults = new ArrayList<>();
        for (Future<TmoCmdResult> future : futures) {
            try {
                TmoCmdResult tmoCmdResult = future.get();
                if (tmoCmdResult != null) {
                    CommandResultDTO commandResult = new CommandResultDTO();
                    commandResult.setId(topologyService.getNodeText(tmoCmdResult.getNodeId()).getData());
                    commandResult.setCmdName(tmoCmdResult.getCmdName());
                    commandResult.setResult(tmoCmdResult.getCmdResult());
                    commandResult.setCmdResult(tmoCmdResult.getRemark());
                    commandResult.setOccurTime(new Date());
                    commandResult.setOperatorId(tmoCmdResult.getOperatorId());
                    commandResult.setArg(tmoCmdResult.getArg()==null?"无":tmoCmdResult.getArg().toString());
                    tmoCmdResults.add(tmoCmdResult);
                    results.add(commandResult);
                }
            } catch (ExecutionException e) {
                logger.error("发送命令异常", e);
            } catch (InterruptedException e) {
                logger.error("发送命令被中断", e);
            }
        }
        tmoCmdResultDao.saveAll(tmoCmdResults);
        return results;
    }

    @PreDestroy
    public void destroy(){
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            try {
                if (!threadPoolExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                    threadPoolExecutor.shutdownNow();
                }
            } catch (InterruptedException e1) {
                threadPoolExecutor.shutdownNow();
            } finally {
                threadPoolExecutor = null;
            }
        }
    }

}
