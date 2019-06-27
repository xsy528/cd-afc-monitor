package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.commons.model.dto.Result;

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
     * @param stationIds 车站节点id数组
     * @param mode 运营模式
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendChangeModeCommand(List<Long> stationIds, Integer mode);

    /**
     * 重发模式广播
     * @param recordIds 广播结果id数组
     * @return 广播结果
     */
    Result<List<CommandResultDTO>> sendModeBroadcastCommand(List<Long> recordIds);

    /**
     * 模式查询
     * @param stationIds 车站节点id数组
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendModeQueryCommand(List<Long> stationIds);

    /**
     * 时钟同步
     * @param nodeIds 车站或者设备id数组
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendTimeSyncCommand(List<Long> nodeIds);

    /**
     * 时钟同步
     * @param lineIds 线路id数组
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendMapSyncCommand(List<Long> lineIds);

    /**
     * 发送设备控制命令
     * @param deviceIds 设备id数组
     * @param command 控制命令
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendNodeControlCommand(List<Long> deviceIds, Short command);

    /**
     * 查询钱箱票箱命令
     * @param deviceId 设备id
     * @return 执行结果
     */
    Result<List<CommandResultDTO>> sendQueryBoxCommand(Long deviceId);

    /**
     * 查询设备状态
     * @param deviceIds 设备id数组
     * @return 设备状态
     */
    Result<List<CommandResultDTO>>  sendQueryDeviceCommand(List<Long> deviceIds);
}
