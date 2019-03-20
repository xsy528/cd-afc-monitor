package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeCmdCondition;
import com.insigma.afc.monitor.model.entity.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * Ticket: 模式服务
 * @author  邱昌进(qiuchangjin@zdwxjd.com)
 *
 */
public interface ModeService {
	/**
	 * 模式命令
	 * <pre>
	 *  对应LC，保存该LC的数据库状态，<br/>
	 *  对应SC，保存该SC的数据库状态，<br/>
	 * </pre>
	 * @param senderId
	 * @param stationId
	 * @param newModeId
	 * @param operationId
	 * @return 执行结果
	 */
	long handleModeCommand(final long senderId, long stationId, short newModeId, long operationId);

	/**
	 * 获取当前模式
	 * <pre>
	 *  从数据库中查到当前模式
	 * </pre>
	 * @param stationId
	 * @return
	 */
	TmoItemStatus getCurrentTmoItemMode(long stationId);

	/**
	 * 获取所有的模式
	 * @param stationID
	 * @return
	 */
	List<TmoItemStatus> getAllTmoItemModeList(long stationID);

	/**
	 * 只简单更新数据库中的当前模式
	 * @param nodeId
	 * @param newModeId
	 * @return
	 */
	boolean saveOrUpdateCurrentModeId(long nodeId, short newModeId, Date changeTime);

	/**
	 * 保存模式上传
	 */
	void saveModeUploadInfo(TmoModeUploadInfo tmoModeUploadInfo);

	/**
	 * 保存模式广播
	 * @param modeBroadcast
	 */
	void saveModeBroadcastInfo(TmoModeBroadcast modeBroadcast);

	/**
	 * 获取当前设备事件
	 * @param condition 查询条件
	 * @return
	 * PageHolder<TmoEquStatusCur>
	 *
	 */
	List<TmoEquStatusCur> getEquStatusList(DeviceEventCondition condition);


	/**
	 * 获取模式上传信息列表
	 *
	 * @param stationIds
	 *            车站编号
	 * @param modeCode
	 *            模式代码
	 * @param broadCastStatus
	 *            广播状态
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return Page<TmoModeUploadInfo> 模式上传列表
	 */
	Page<TmoModeUploadInfo> getModeUploadInfo(List<Integer> stationIds,Short modeCode, Short broadCastStatus,
											  Date startTime, Date endTime, int page, int pageSize);

	/**
	 * 获取模式日志信息
	 * 注意：使用此接口时，如果开始时间与结束时间相同，请将结束时间加一秒
     * 因为数据库中时间精确到了毫秒
	 * @param condition 模式日志查询条件实体类
	 * @return 分页数据
	 */
    Page<TmoCmdResult> getModeCmdSearch(ModeCmdCondition condition);

	/**
	 * 获取模式广播信息
	 * @param stationIds 车站数组
	 * @param modeCode 进入的模式
	 * @param operatorId 操作员id
	 * @param desStationId 目标车站id
	 * @param broadCastStatus 广播状态
	 * @param broadCastType 广播类型
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return 分页数据
	 */
	Page<TmoModeBroadcast> getModeBroadcastInfo(List<Integer> stationIds, Short modeCode, String operatorId,
												Integer desStationId, Short broadCastStatus, Short broadCastType,
												Date startTime, Date endTime, int page, int pageSize);

	/**
	 * 模式上传信息
	 * @param nodeId
	 * @return
	 */
	List<TmoModeUploadInfo> getModeUpload(long nodeId);

	/**
	 * 模式广播信息
	 * @return
	 */
	List<TmoModeBroadcast> getModeBroadcast();

}
