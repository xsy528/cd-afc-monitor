package com.insigma.afc.monitor.service;

import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.commons.application.IService;

/**
 * 模式相关的Service
 * 
 * @author CaiChunye
 */
public interface IModeService extends IService {
	/**
	 * 获取模式上传信息列表
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
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
	 * @return List<TmoModeUploadInfo> 模式上传列表
	 */
	public List<TmoModeUploadInfo> getModeUploadInfoList(Short lindId, Integer[] stationIds, Long[] deviceIds,
			Short modeCode, Short broadCastStatus, Date startTime, Date endTime, int page, int pageSize);

	/**
	 * 获取模式上传信息总条数
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
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
	 * @return List<TmoModeUploadInfo> 模式上传列表
	 */
	public int getMaxofModeUploadInfo(Short lindId, Integer[] stationId, Short modeCode, Short broadCastStatus,
			Date startTime, Date endTime);

	/**
	 * 获取模式广播信息
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param modeCode
	 *            模式代码
	 * @param operator
	 *            操作员编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return List<TmoModeBroadcast>
	 */
	public List<TmoModeBroadcast> getModeBroadcastList(Short lindId, Integer[] stationIds, Short modeCode,
			String operator, Short destLineId, Short broadCastStatus, Short broadCastType, Date startTime, Date endTime,
			int page, int pageSize);

	/**
	 * 获取模式广播信息
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param modeCode
	 *            模式代码
	 * @param operator
	 *            操作员编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return List<TmoModeBroadcastViewItem>
	 */
	public List<TmoModeBroadcastViewItem> getTmoBroadcastViewItemList(Short lindId, Integer[] stationIds,
			Short modeCode, String operator, Short destLineId, Short broadCastStatus, Short broadCastType,
			Date startTime, Date endTime, int page, int pageSize);

	/**
	 * 获取模式广播信息总条数
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param modeCode
	 *            模式代码
	 * @param operator
	 *            操作员编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return int 获取模式广播信息总条数
	 */
	public int getMaxOfModeBroadcast(Short lindId, Integer[] stationIds, Short modeCode, String operator,
			Short destLineId, Short broadCastStatus, Short broadCastType, Date startTime, Date endTime);

	public List<TmoModeBroadcastViewItem> getAlreadyBroadcastItems(String stations);

	/**
	 * 获取模式命令总数
	 * 
	 * @param stationIds
	 *            如果stationIds为null 或 stationIds.length <= 0则表示查找整个线路车站
	 * @param operatorId
	 *            可以为null
	 * @param startTime
	 *            可以为null
	 * @param endTime
	 *            可以为null
	 * @return List
	 */
	public int getMaxofModeCmdLog(Short[] lineIds, Integer[] stationIds, Long operatorId, Date startTime, Date endTime);

	/**
	 * 获取模式命令信息
	 * 
	 * @param stationIds
	 *            如果stationIds为null 或 stationIds.length <= 0则表示查找整个线路车站
	 * @param operatorId
	 *            可以为null
	 * @param startTime
	 *            可以为null
	 * @param endTime
	 *            可以为null
	 * @param currentPage
	 *            可以为null
	 * @param pageSize
	 *            可以为null
	 * @return List
	 */
	public List<Object> getModeCmdLog(Short[] lineIds, Integer[] stationIds, Long operatorId, Date startTime,
			Date endTime, Integer currentPage, Integer pageSize);

	/**
	 * 获取模式广播信息
	 * 
	 * @param modeInfos
	 *            模式广播信息
	 */
	public List<TmoModeBroadcast> getModeInfo();

	/**
	 * 获取模式广播信息 
	 * @param onlyDescend  null：所有信息信息 true:降级模式信息 false:正常模式信息 
	 * @return
	 */
	public List<TmoModeBroadcast> getModeBroadcast(Boolean onlyDescend);

	public List<TmoModeBroadcast> getModeBroadcast(Boolean onlyDescend, Short[] lines, Integer[] stations);

	public List<TmoModeBroadcast> getModeBroadcast(Boolean isDay, long nodeId);

	public List<TmoModeBroadcast> getModeBroadcast(Boolean isDay, long nodeId, Short[] lines, Integer[] stations);

}
