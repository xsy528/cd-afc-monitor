package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.DeviceStatusCondition;
import com.insigma.afc.monitor.model.dto.EquStatusViewItem;
import com.insigma.afc.monitor.model.dto.StationStatustViewItem;
import com.insigma.afc.monitor.model.dto.condition.StationStatusCondition;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;

import java.util.List;

/**
 * 节点(设备、车站)状态获取接口
 * 
 * @author CaiChunye
 */
public interface IMetroNodeStatusService {

	/**
	 * 获取车站状态统计信息
	 * 
	 * @param condition
	 *            查询条件
	 * @return 统计信息列表
	 */
	List<StationStatustViewItem> getStationStatusView(StationStatusCondition condition);

	/**
	 * 获取所有线路状态
	 * @return 线路状态
	 */
	List<TmoItemStatus> getLineTmoItemStatus();

	/**
	 * 根据查询条件获取设备状态显示列表
	 * 
	 * @param conditon
	 *            查询条件
	 * @return 设备状态信息列表
	 */
	List<EquStatusViewItem> getEquStatusView(DeviceStatusCondition conditon);

	/**
	 * 获取车站状态信息
	 * 
	 * @param nodeId
	 *            节点号
	 * @param isDay
	 *            是否取当前运营日数据
	 * @return 节点状态
	 */
	TmoItemStatus getTmoItemStatus(Boolean isDay, long nodeId);

	/**
	 * @param lineId 线路id
	 * @param stationId 车站id
	 * @param deviceId 设备id
	 * @return 节点状态
	 */
	TmoItemStatus getTmoItemStatus(Short lineId, Integer stationId, Long deviceId);
}
