/*
 * $Source: $
 * $Revision: $
 * $Date: $
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service;

import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.commons.application.IService;

/**
 * 命令日志相关的Service
 * 
 * @author wangxinhao
 */
public interface ICommandLogService extends IService {
	/**
	 * 获取命令日志列表
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param operatorId
	 *            操作员编号
	 * @param cmdResult
	 *            发送结果
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return List<TmoCmdResult> 命令日志列表
	 */
	public List<TmoCmdResult> getCommandLogList(Short[] lindId, Integer[] stationId, Long[] nodeId, String operatorId,
			Integer cmdResult, Date startTime, Date endTime, Boolean successResult, Short cmdType, int page,
			int pageSize);

	/**
	 * 获取命令日志信息列表
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param operatorId
	 *            操作员编号
	 * @param cmdResult
	 *            发送结果
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return CommandLogViewItem 命令日志列表
	 */
	public int getMaxofCommandLog(Short[] lindId, Integer[] stationId, Long[] nodeId, String operatorId,
			Integer cmdResult, Date startTime, Date endTime, Boolean successResult, Short cmdType);

}
