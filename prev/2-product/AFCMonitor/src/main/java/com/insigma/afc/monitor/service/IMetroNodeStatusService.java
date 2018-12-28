package com.insigma.afc.monitor.service;

import java.util.List;

import com.insigma.afc.monitor.entity.TmoItemStatus;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.application.IService;

/**
 * 节点(设备、车站)状态获取接口
 * 
 * @author CaiChunye
 */
public interface IMetroNodeStatusService extends IService {

	/**
	 * 获取车站状态统计信息
	 * 
	 * @param lineIDs
	 *            线路号数组，当参数为null时表示忽略线路条件
	 * @param stationIDs
	 *            车站号数组，当参数为null时表示忽略车站条件
	 * @return 统计信息列表
	 */
	List<StationStatustViewItem> getStationStatusView(Short[] lineIDs, Integer[] stationIDs);

	/**
	 * 根据查询条件获取设备状态显示列表
	 * 
	 * @param conditon
	 *            查询条件
	 * @return 设备状态信息列表
	 */
	List<EquStatusViewItem> getEquStatusView(EquStatusFilterForm conditon);

	/**
	 * 获取设备状态统计信息
	 * 
	 * @param stationID
	 *            车站号
	 * @return 统计信息列表
	 */
	List<EquStatusViewItem> getEquStatusViewByStationId(int stationID);

	/**
	 * 获取车站状态信息
	 * 
	 * @param nodeId
	 *            节点号
	 * @param isDay
	 *            是否取当前运营日数据
	 * @return
	 */
	TmoItemStatus getTmoItemStatus(Boolean isDay, long nodeId);

	/**
	 * 更新状态
	 * @param nodeId
	 * @param status
	 */
	public void saveItemStatus(long nodeId, short status);

	/**
	 * 更新运行模式
	 * @param nodeId
	 * @param status
	 */
	public void saveModeCode(long nodeId, String modeStr);

	/**
	 * 更新状态和运营模式
	 * @param nodeId
	 * @param status
	 * @param modeStr
	 */
	public void UpdateItemStatus(long nodeId, Short status, String modeCode);

	/**
	 * 批量更新节点的在线状态
	 * @param nodeList
	 * @param status
	 */
	public void saveBatchItemStatus(List<MetroNode> nodeList, boolean activeItem);

	/**
	 * 保存或更新指定节点的在线状态
	 * 
	 * @param lineId
	 * @param stationId
	 * @param nodeId
	 * @param status
	 *            节点状态：4-离线；5-在线
	 * @return 更新结果
	 */
	boolean saveOrUpdateOnlineStatus(long nodeId, boolean activeItem);

	/**
	 * 更新所有设备的状态TmoItemStatus
	 * 
	 * @param itemStatus
	 * @return
	 */
	public boolean updateAllItemsOnlineStatus(boolean deviceStatus);

	/**
	 * @param lineId
	 * @param stationId
	 * @param deviceId
	 * @return
	 */
	TmoItemStatus getTmoItemStatus(Short lineId, Integer stationId, Long deviceId);

	boolean saveOrUpdateOnlineStatus(short lineId, int stationId, long nodeId, boolean status);
}
