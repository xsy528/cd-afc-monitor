package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;

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
