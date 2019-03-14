package com.insigma.afc.monitor.thread;

import com.insigma.afc.monitor.constant.dic.AFCCmdResultType;
import com.insigma.afc.monitor.model.dto.CommandResult;
import com.insigma.afc.monitor.model.entity.*;
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
    private final short cmdType;
    private final MetroNode node;
    private ICommandService rmiCommandService;

    public CommandSendTask(int cmdId, String cmdName, Object cmdArg, short cmdType, MetroNode node,
                    ICommandService rmiCommandService) {
        this.cmdId = cmdId;
        this.cmdName = cmdName;
        this.cmdArg = cmdArg;
        this.cmdType = cmdType;
        this.node = node;
        this.rmiCommandService = rmiCommandService;
    }

    @Override
    public TmoCmdResult call() throws Exception{
        if (cmdArg instanceof int[]) {
            int[] a = (int[]) cmdArg;
            LOGGER.info("向节点" + node.name() + "发送" + cmdName + " 参数：" + a[0] + "," + a[1]);
        } else {
            LOGGER.info("向节点" + node.name() + "发送" + cmdName + " 参数：" + cmdArg);
        }
        int result = AFCCmdResultType.SEND_FAILED;
        String resultDesc = null;
        try {
            String userId = "0";
            com.insigma.afc.topology.MetroNode metroNode = null;
            switch (node.level()){
                case ACC:{
                    metroNode = new com.insigma.afc.topology.MetroACC();
                    ((com.insigma.afc.topology.MetroACC) metroNode).setAccID((short)0);
                    break;
                }
                case LC:{
                    metroNode = new com.insigma.afc.topology.MetroLine();
                    MetroLine metroLine = (MetroLine)node;
                    ((com.insigma.afc.topology.MetroLine)metroNode).setLineID(metroLine.getLineID());
                    break;
                }
                case SC:{
                    MetroStation metroStation = (MetroStation)node;
                    metroNode = new com.insigma.afc.topology.MetroStation();
                    ((com.insigma.afc.topology.MetroStation) metroNode)
                            .setId(new com.insigma.afc.topology.MetroStationId(metroStation.getLineId(),
                                    metroStation.getStationId()));
                    break;
                }
                case SLE:{
                    MetroDevice metroDevice = (MetroDevice)node;
                    metroNode = new com.insigma.afc.topology.MetroDevice();
                    ((com.insigma.afc.topology.MetroDevice) metroNode)
                            .setId(new com.insigma.afc.topology.MetroDeviceId(metroDevice.getLineId(),
                                    metroDevice.getStationId(),metroDevice.getDeviceId()));
                    break;
                }
                default:
            }
            CmdHandlerResult command = rmiCommandService.command(cmdId, userId, 0L, cmdArg, metroNode);
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
        return getResult(node, cmdName, cmdArg, result, cmdType, resultDesc);
    }

    private TmoCmdResult getResult(final MetroNode node, String command, final Object arg, final int result,
                              final short cmdType, final String resultDesc) {

        String resultMessageShow = "发送结果：\n";

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
