package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCCmdResultType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.define.WZCommandType;
import com.insigma.afc.monitor.model.define.WZDeviceType;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.service.CommandService;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.service.CommonDAO;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CommandServiceImpl implements CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);

    protected ICommandService commandService;
    private ICommonDAO commonDAO;

    private List<CommandResult> results = new Vector<>();
    private CountDownLatch countDownLatch;
    private List<MetroNode> nodes;

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

        name = StringUtils.defaultIfEmpty(name,"模式")+"切换命令";

        //先判断通信服务器连接是否正常
        try {
            commandService.alive();
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
    public Result<List<CommandResult>> sendModeQueryCommand(List<Long> nodeIds){
        List<MetroNode> stationIds = getStationNodeFromIds(nodeIds);
        if (stationIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        return Result.success(send(CommandType.CMD_QUERY_MODE, "模式查询命令",
                null, stationIds, AFCCmdLogType.LOG_MODE.shortValue()));
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

        return Result.success(send(CommandType.CMD_TIME_SYNC, "时间同步命令",
                null, stationIds, AFCCmdLogType.LOG_TIME_SYNC.shortValue()));
    }

    @Override
    public Result<List<CommandResult>> sendMapSyncCommand(List<Long> lineIds) {
        CmdHandlerResult command = null;
        List<MetroNode> ids = new ArrayList<>();
        for (Long lineId:lineIds){
            MetroNode metroNode = AFCApplication.getNode(lineId);
            if (metroNode.level()==AFCNodeLevel.LC){
                ids.add(metroNode);
                logger.info("地图同步数据 ：" + metroNode.name());
            }
        }
        if (ids.isEmpty()){
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

        ICommandService commandService = null;
        CmdHandlerResult command = new CmdHandlerResult();
        try {
            commandService = Application.getService(ICommandService.class);
            commandService.alive();
        } catch (Exception e) {
            //			MessageForm.Message("错误信息", "发送地图同步失败：工作台与通信服务器离线。", SWT.ICON_ERROR);
            command.messages.add("发送地图同步失败：工作台与通信服务器离线。" + e.getMessage());
            command.isOK = false;
            return command;
        }
        //TODO 需要获取当前用户id
        String userId = "0";
        command = commandService.command(CommandType.CMD_EXPORT_MAP, userId, 1l);
        return command;
    }

    @Override
    public Result<List<CommandResult>> sendNodeControlCommand(List<Long> nodeIds, short command){
        List<MetroNode> deviceIds = getDeviceNodeFromIds(nodeIds);
        if (deviceIds==null){
            return Result.error(ErrorCode.NO_NODE_SELECT);
        }

        return Result.success(send(WZCommandType.COM_SLE_CONTROL_CMD, "设备控制命令",
                null, deviceIds, command));
    }

    @Override
    public Result<List<CommandResult>> sendQueryBoxCommand(Long nodeId){
        MetroNode metroNode = AFCApplication.getNode(nodeId);
        if (metroNode==null||metroNode.level()!=AFCNodeLevel.SLE){
            return Result.error(ErrorCode.NODE_NOT_EXISTS);
        }
        MetroDevice device = (MetroDevice)metroNode;
        List<MetroNode> ids = new ArrayList<>();
        ids.add(device);
        List<CommandResult> commandResults = new ArrayList<>();
        if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
                || device.getDeviceType() == WZDeviceType.TSM.shortValue()) {
            commandResults.addAll(send(CommandType.CMD_QUERY_MONEY_BOX, "设备钱箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        if (device.getDeviceType() == WZDeviceType.TVM.shortValue()
                || device.getDeviceType() == WZDeviceType.POST.shortValue()
                || device.getDeviceType() == WZDeviceType.ENG.shortValue()
                || device.getDeviceType() == WZDeviceType.EXG.shortValue()
                || device.getDeviceType() == WZDeviceType.REG.shortValue()) {
            commandResults.addAll(send(WZCommandType.CMD_QUERY_TICKET_BOX, "设备票箱查询命令",
                    null, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue()));
        }
        return Result.success(commandResults);
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

    public List<CommandResult> send(final int id, final String name, final Object arg,
                                    final List<MetroNode> nodes, final short cmdType) {
        if (nodes==null||countDownLatch!=null){
            return null;
        }
        this.nodes = nodes;
        results.clear();
        countDownLatch = new CountDownLatch(nodes.size());
        for (MetroNode node : nodes) {
            CommandThread thread = new CommandThread(id, name, arg, node, cmdType);
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("",e);
        }
        countDownLatch=null;
        return results = sortResults(results);
    }

    public class CommandThread extends EnhancedThread {

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
        public void execute() {
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
                CmdHandlerResult command = commandService.command(id, userId,
                        AFCApplication.getAFCNode().id(), arg, node);
                Serializable returnValue = command.returnValue;
                if (returnValue != null && returnValue instanceof Integer) {
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

    public List<CommandResult> sortResults(final List<CommandResult> results) {
        List<CommandResult> orderResulsts = new ArrayList<CommandResult>();

        if (nodes == null || results == null) {
            return orderResulsts;
        }

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < results.size(); j++) {
                String id = nodes.get(i).name() + "/0x" + String.format("%08x", nodes.get(i).getNodeId());
                if (id.equals(results.get(j).getId())) {
                    orderResulsts.add(results.get(j));
                    // break;
                }
            }
        }

        return orderResulsts;
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
            short lineID = line.getLineID();
            tmoCmdResult.setLineId(lineID);
            tmoCmdResult.setStationId(0);
            tmoCmdResult.setNodeId(AFCApplication.getNodeId(lineID));
            tmoCmdResult.setNodeType(AFCDeviceType.LC);
        }

        if (node instanceof MetroStation) {
            MetroStation station = (MetroStation) node;
            tmoCmdResult.setLineId(station.getId().getLineId());
            Integer stationId = station.getId().getStationId();
            tmoCmdResult.setStationId(stationId);
            tmoCmdResult.setNodeId(AFCApplication.getNodeId(stationId));
            tmoCmdResult.setNodeType(AFCDeviceType.SC);
        }

        if (node instanceof MetroDevice) {
            MetroDevice device = (MetroDevice) node;
            tmoCmdResult.setLineId(device.getId().getLineId());
            tmoCmdResult.setStationId(device.getId().getStationId());
            tmoCmdResult.setNodeId(device.getId().getDeviceId());
            tmoCmdResult.setNodeType(device.getDeviceType());
        }

        tmoCmdResult.setUploadStatus((short) 0);
        //操作员id
        //tmoCmdResult.setOperatorId(Application.getUser().getUserID());
        tmoCmdResult.setCmdResult((short) result);
        tmoCmdResult.setRemark(resultDesc);
        tmoCmdResult.setCmdType(cmdType);
        try {
            commonDAO.saveObj(tmoCmdResult);
        } catch (Exception e) {
            logger.error("保存命令日志异常", e);
        }

        CommandResult resultitem = new CommandResult();
        resultitem.setId(node.name() + "/0x" + String.format("%08x", node.getNodeId()));
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

    public void setCommandService(ICommandService iCommandService){
        this.commandService = iCommandService;
    }

    public void setCommonDAO(CommonDAO commonDAO){
        this.commonDAO = commonDAO;
    }
}
