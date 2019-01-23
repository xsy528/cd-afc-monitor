package com.insigma.acc.monitor.service;

import com.insigma.acc.monitor.model.dto.Result;
import com.insigma.afc.monitor.action.CommandResult;

import java.util.List;

/**
 * Ticket:命令服务
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
public interface CommandService {

    /**
     * 修改运营模式
     * @param nodeIds 车站节点id数组
     * @param command 运营模式
     * @return 执行结果
     */
    Result<List<CommandResult>> sendChangeModeCommand(List<Long> stationIds, int command);

    /**
     * 模式查询
     * @param nodeIds 车站节点id数组
     * @return 执行结果
     */
    Result<List<CommandResult>> sendModeQueryCommand(List<Long> stationIds);

    /**
     * 时钟同步
     * @param nodeIds 车站或者设备id数组
     * @return 执行结果
     */
    Result<List<CommandResult>> sendTimeSyncCommand(List<Long> nodeIds);

    /**
     * 时钟同步
     * @param nodeIds 线路id数组
     * @return 执行结果
     */
    Result<List<CommandResult>> sendMapSyncCommand(List<Long> lineIds);

    /**
     * 发送设备控制命令
     * @param nodeIds 设备id数组
     * @param command 控制命令
     * @return 执行结果
     */
    Result<List<CommandResult>> sendNodeControlCommand(List<Long> deviceIds, short command);

    /**
     * 查询钱箱票箱命令
     * @param nodeId 设备id
     * @return 执行结果
     */
    Result<List<CommandResult>> sendQueryBoxCommand(Long deviceId);

}
