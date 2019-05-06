package com.insigma.afc.monitor.thread;

import com.insigma.afc.log.constant.LogDefines;
import com.insigma.afc.log.service.ILogService;
import com.insigma.afc.monitor.constant.LogModuleCode;
import com.insigma.afc.monitor.constant.dic.AFCCmdResultType;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.model.entity.topology.MetroDevice;
import com.insigma.afc.monitor.model.entity.topology.MetroLine;
import com.insigma.afc.monitor.model.entity.topology.MetroNode;
import com.insigma.afc.monitor.model.entity.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-03-04 18:51
 */
public class CommandSendTask implements Callable<TmoCmdResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandSendTask.class);

    private final int cmdId;
    private final String cmdName;
    private final Object cmdArg;
    private final Short cmdType;
    private final MetroNode node;
    private ICommandService rmiCommandService;
    private ILogService logService;
    private Long userId;
    private String ip;

    public CommandSendTask(int cmdId, String cmdName, Object cmdArg, Short cmdType, MetroNode node,
                           ICommandService rmiCommandService, ILogService logService,Long userId,String ip) {
        this.cmdId = cmdId;
        this.cmdName = cmdName;
        this.cmdArg = cmdArg;
        this.cmdType = cmdType;
        this.node = node;
        this.rmiCommandService = rmiCommandService;
        this.logService = logService;
        this.userId = userId;
        this.ip = ip;
    }

    @Override
    public TmoCmdResult call() throws Exception {
        LOGGER.info("向节点" + node.name() + "发送" + cmdName + " 参数：" + cmdArg);
        int result = AFCCmdResultType.SEND_FAILED;
        String resultDesc = null;
        try {
            String userId = "0";
            com.insigma.afc.topology.MetroLine target = new com.insigma.afc.topology.MetroLine();
            switch (node.level()) {
                case LC: {
                    MetroLine metroLine = (MetroLine) node;
                    target.setLineID(metroLine.getLineID());
                    break;
                }
                case SC: {
                    MetroStation metroStation = (MetroStation) node;
                    target.setLineID(metroStation.getLineId());
                    break;
                }
                case SLE: {
                    MetroDevice metroDevice = (MetroDevice) node;
                    target.setLineID(metroDevice.getLineId());
                    break;
                }
                default:
            }
            CmdHandlerResult command = rmiCommandService.command(cmdId, userId, 0L, cmdArg, target);
            Serializable returnValue = command.returnValue;
            if (returnValue instanceof Integer) {
                result = (Integer) returnValue;
            } else if (command.isOK) {
                result = AFCCmdResultType.SEND_SUCCESSFUL;
            }
            resultDesc = command.getResultMessage();

        } catch (Exception e) {
            LOGGER.error("发送" + cmdName + "错误", e);
        }
        LOGGER.info("向节点" + node.name() + "发送" + cmdName + "  返回：" + result);
        return getResult(node, cmdName, result, cmdType, resultDesc);
    }

    private TmoCmdResult getResult(MetroNode node, String command, int result, Short cmdType, String resultDesc) {

        String resultMessageShow = "发送结果：\n";
        if (result == 0) {
            resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令发送成功。\n";
            logService.log(LogDefines.NORMAL_LOG,resultMessageShow,userId, ip, LogModuleCode.MODULE_MONITOR);
        } else {
            resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令失败。错误码：" + result + "。";
            logService.log(LogDefines.ERROR_LOG,resultMessageShow,userId,ip, LogModuleCode.MODULE_MONITOR);
        }
        LOGGER.info(resultMessageShow);

        TmoCmdResult tmoCmdResult = new TmoCmdResult();
        tmoCmdResult.setOccurTime(DateTimeUtil.getNow());
        tmoCmdResult.setCmdName(command);

        if (node instanceof MetroLine) {
            MetroLine line = (MetroLine) node;
            tmoCmdResult.setLineId(line.getLineID());
            tmoCmdResult.setStationId(0);
            tmoCmdResult.setNodeId(line.id());
            tmoCmdResult.setNodeType(AFCNodeLevel.LC.getStatusCode().shortValue());
        }

        if (node instanceof MetroStation) {
            MetroStation station = (MetroStation) node;
            tmoCmdResult.setLineId(station.getLineId());
            tmoCmdResult.setStationId(station.getStationId());
            tmoCmdResult.setNodeId(station.id());
            tmoCmdResult.setNodeType(AFCNodeLevel.SC.getStatusCode().shortValue());
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
        tmoCmdResult.setOperatorId("1");
        tmoCmdResult.setCmdResult((short) result);
        tmoCmdResult.setRemark(resultDesc);
        tmoCmdResult.setCmdType(cmdType);
        return tmoCmdResult;
    }

}
