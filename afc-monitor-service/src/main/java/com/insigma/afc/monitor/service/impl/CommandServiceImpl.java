package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.*;
import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.afc.monitor.service.rmi.CmdHandlerResult;
import com.insigma.afc.monitor.service.rmi.CommandType;
import com.insigma.afc.monitor.service.rmi.ICommandService;
import com.insigma.commons.dic.PairValue;
import com.insigma.commons.util.DateTimeUtil;
import com.insigma.commons.util.NodeIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Ticket: 命令服务实现类
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
@Service
public class CommandServiceImpl implements CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);

    private List<CommandResult> results = new Vector<>();
    private CountDownLatch countDownLatch;
    private ThreadLocal<List<MetroNode>> threadLocalNodes = new ThreadLocal<>();

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

//    @Override
//    public CmdHandlerResult command(int id, String userId, Long src, Object arg, List<MetroNode> targets) {
//        if (targets==null){
//            return null;
//        }
//        StringBuilder tagStr = new StringBuilder();
//        StringBuilder argStr = new StringBuilder();
//        for (MetroNode tag:targets) {
//            tagStr.append(tag.id());
//            logger.info(tag.toString());
//        }
//        if (arg instanceof int[]) {
//            int[] a = (int[]) arg;
//            argStr.append(a[0]).append(",").append(a[1]);
//            logger.info("ID=" + id + ",参数为" + argStr + " 目标为" + tagStr);
//        } else {
//            logger.info("ID=" + id + ",参数为" + arg + " 目标为" + tagStr);
//        }
//        if (commandHandlerManager != null) {
//            try {
//                CmdHandlerResult process = commandHandlerManager.process(id, userId, src, arg, targets);
//                return process;
//            } catch (Exception e) {
//                logger.error("命令执行异常", e);
//                CmdHandlerResult result = new CmdHandlerResult();
//                result.isOK = false;
//                result.messages.add("命令执行异常：" + e.getMessage());
//                return result;
//            }
//        } else {
//            logger.error("未配置命令管理器");
//        }
//        CmdHandlerResult result = new CmdHandlerResult();
//        result.isOK = false;
//        result.messages.add("该命令未定义");
//        return result;
//    }
//
//    @Override
//    public CmdHandlerResult command(int id, String userId, Long src, Object arg, MetroNode... targets) {
//        return command(id, userId, src, arg, Arrays.asList(targets));
//    }
//
//    @Override
//    public CmdHandlerResult command(int id, String userId, Long src, MetroNode... targets) {
//        return command(id, userId, src, null, Arrays.asList(targets));
//    }
//
//    @Override
//    public CmdHandlerResult command(int id, String userId, Long src, List<MetroNode> targets) {
//        return command(id, userId, src, null, targets);
//    }
//
//    @Override
//    public void alive() {
//
//    }

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

        //TODO 需要解释
//        if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
//            int i = 0;
//            for (; i < stationIds.size(); i++) {
//                MetroNode node = stationIds.get(i);
//                if (node instanceof MetroStation) {
//                    break;
//                }
//            }
//            if (i < stationIds.size()) {
//                stationIds.remove(i);
//            }
//        }

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

    private List<CommandResult> send(final int id, final String name, final Object arg,
                                    final List<MetroNode> nodes, final short cmdType) {
        if (nodes == null || countDownLatch != null) {
            return null;
        }
        threadLocalNodes.set(nodes);
        results.clear();
        countDownLatch = new CountDownLatch(nodes.size());
        for (MetroNode node : nodes) {
            CommandThread thread = new CommandThread(id, name, arg, node, cmdType);
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        countDownLatch = null;
        return results = sortResults(results);
    }

    public class CommandThread extends Thread{

        private final int id;

        private final String name;

        private final Object arg;

        private final MetroNode node;

        private final short cmdType;

        CommandThread(final int id, final String name, final Object arg,
                      final MetroNode node, final short cmdType) {
            super("命令发送线程");
            this.id = id;
            this.name = name;
            this.arg = arg;
            this.node = node;
            this.cmdType = cmdType;
        }

        @Override
        public void run() {
            if (arg instanceof int[]) {
                int[] a = (int[]) arg;
                logger.info("向节点" + node.name() + "发送" + name + " 参数：" + a[0] + "," + a[1]);
            } else {
                logger.info("向节点" + node.name() + "发送" + name + " 参数：" + arg);
            }
            int result = AFCCmdResultType.SEND_FAILED;
            String resultDesc = null;
            try {
                String userId = "0";
                //AFCApplication.getAFCNode().id(),
                CmdHandlerResult command = rmiCommandService.command(id, userId, 0L, arg, node);
                Serializable returnValue = command.returnValue;
                if (returnValue instanceof Integer) {
                    result = (Integer) returnValue;
                } else if (command.isOK) {
                    result = AFCCmdResultType.SEND_SUCCESSFUL;
                }
                resultDesc = command.getResultMessage();
            } catch (Exception e) {
                logger.error("发送" + name + "错误", e);
            }
            logger.info("向节点" + node.name() + "发送" + name + "  返回：" + result);
            results.add(save(node, name, arg, result, cmdType, resultDesc));
            countDownLatch.countDown();
        }
    }

    private List<CommandResult> sortResults(final List<CommandResult> results) {
        List<CommandResult> orderResults = new ArrayList<>();

        List<MetroNode> nodes = threadLocalNodes.get();
        if (threadLocalNodes.get() == null || results == null) {
            return orderResults;
        }

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < results.size(); j++) {
                String id = nodes.get(i).name() + "/0x" + String.format("%08x", nodes.get(i).id());
                if (id.equals(results.get(j).getId())) {
                    orderResults.add(results.get(j));
                    // break;
                }
            }
        }

        return orderResults;
    }

    public CommandResult save(final MetroNode node, String command, final Object arg, final int result,
                              final short cmdType, final String resultDesc) {

        String resultMessageShow = "发送结果：\n\n";

        if (result == 0) {
            resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令发送成功。\n";
//            if (this.logService != null) {
//                logService.doBizLog(resultMessageShow);
//            }
        } else {
            resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令失败。错误码：" + result + "。";
//            if (this.logService != null) {
//                try {
//                    logService.doBizErrorLog(resultMessageShow);
//                } catch (Exception e) {
//                    logger.error("发送命令保存日志失败", e);
//                }
//            }
        }

        TmoCmdResult tmoCmdResult = new TmoCmdResult();
        tmoCmdResult.setOccurTime(DateTimeUtil.getNow());
        tmoCmdResult.setCmdName(command);

        if (node instanceof MetroLine) {
            MetroLine line = (MetroLine) node;
            tmoCmdResult.setLineId(line.getLineID());
            tmoCmdResult.setStationId(0);
            tmoCmdResult.setNodeId(NodeIdUtils.nodeIdStrategy.getNodeNo(line.id()));
            tmoCmdResult.setNodeType(AFCDeviceType.LC);
        }

        if (node instanceof MetroStation) {
            MetroStation station = (MetroStation) node;
            tmoCmdResult.setLineId(station.getLineId());
            tmoCmdResult.setStationId(station.getStationId());
            tmoCmdResult.setNodeId(NodeIdUtils.nodeIdStrategy.getNodeNo(station.id()));
            tmoCmdResult.setNodeType(AFCDeviceType.SC);
        }

        if (node instanceof MetroDevice) {
            MetroDevice device = (MetroDevice) node;
            tmoCmdResult.setLineId(device.getLineId());
            tmoCmdResult.setStationId(device.getStationId());
            tmoCmdResult.setNodeId(device.getDeviceId());
            tmoCmdResult.setNodeType(device.getDeviceType());
        }

        tmoCmdResult.setUploadStatus((short) 0);
        //操作员id
        //tmoCmdResult.setOperatorId(Application.getUser().getUserID());
        tmoCmdResult.setCmdResult((short) result);
        tmoCmdResult.setRemark(resultDesc);
        tmoCmdResult.setCmdType(cmdType);
        tmoCmdResultDao.save(tmoCmdResult);

        CommandResult resultitem = new CommandResult();
        resultitem.setId(node.name() + "/0x" + String.format("%08x", node.id()));
        resultitem.setCmdName(tmoCmdResult.getCmdName());
        resultitem.setResult((short) result);
        resultitem.setCmdResult(tmoCmdResult.getRemark());
        resultitem.setOccurTime(DateTimeUtil.formatCurrentDateToString("yyyy-MM-dd HH:mm:ss"));
        if (arg != null) {
            resultitem.setArg(arg.toString());
        } else {
            resultitem.setArg("无");
        }
        return resultitem;
    }

}
