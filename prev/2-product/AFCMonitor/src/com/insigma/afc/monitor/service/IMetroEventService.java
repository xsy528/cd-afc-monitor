package com.insigma.afc.monitor.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.entity.TmoEquEvent;
import com.insigma.afc.monitor.entity.TmoEquEventCur;
import com.insigma.afc.monitor.entity.TmoEquEventHistory;
import com.insigma.afc.monitor.entity.TmoEquStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.commons.application.IService;
import com.insigma.commons.collection.PageHolder;

/**
 * 事件相关的Service
 * 
 * @author CaiChunye
 */
public interface IMetroEventService extends IService {
	/**
	 * 获取历史事件列表
	 * 
	 * @param eventSearchCondition
	 *            查询条件
	 * @return
	 */
	PageHolder<TmoEquEventHistory> getEventHistoryList(EventFilterForm eventSearchCondition, int indexPage);

	/**
	 * 取得历史设备事件列表
	 * 
	 * @param lineIds
	 * @param stationIds
	 * @param deviceIds
	 * @param eventLevs
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<TmoEquEventHistory> getDeviceEventList(Short[] lineIds, Integer[] stationIds, Long[] deviceIds,
			Short[] eventLevs, Date startTime, Date endTime, Integer pageNum, Integer pageSize);

	/**
	 * 获取当前事件列表
	 * 
	 * @param eventSearchCondition
	 *            查询条件
	 * @return
	 */

	/**
	 * 保存事件
	 * 
	 * @param event
	 */
	public void saveEvent(TmoEquEvent event);

	PageHolder<TmoEquEventCur> getEventCurList(EventFilterForm eventSearchCondition, int indexPage);

	/**
	 * 查询设备各个部件的异常状态
	 * 
	 * @param deviceId
	 * @return
	 */
	List<TmoEquEventCur> getUnnormalStatus(long nodeId);

	/**
	 * 查询设备各个部件的状态
	 * 
	 * @param deviceId
	 * @return
	 */
	public List<TmoEquEventCur> getDeviceModuleEvent(long nodeId);

	/**
	 * 获取历史事件列表
	 * 
	 * @param eventSearchCondition
	 *            查询条件
	 * @return
	 */
	PageHolder<TmoEquEvent> getEventList(EventFilterForm eventSearchCondition, int indexPage);

	/**
	 * 获取历史事件列表
	 * 
	 * @param eventSearchCondition
	 *            查询条件
	 * @return
	 */

	public List<Object> getEquEventEntity(Long nodeId, Short eventId);

	/**
	 * 取得历史设备事件列表个数
	 * 
	 * @param lineIds
	 * @param stationIds
	 * @param deviceIds
	 * @param eventLevs
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getMaxByDeviceEventList(Short[] lineIds, Integer[] stationIds, Long[] deviceIds, Short[] eventLevs,
			Date startTime, Date endTime);

	/**
	 * 获取部件信息
	 * 
	 * @param nodeId
	 *            nodeId
	 * @return List (componentType:部件类型，statusLevel:事件级别)
	 */
	public List<Object[]> getDeviceComponentInfo(Long nodeId);

	/**
	 * 保存事件
	 * 
	 * @param event
	 */
	public boolean saveEquEvent(List<TmoEquEvent> event);

	/**
	 * 保存或更新设备事件表
	 * 
	 * @param events
	 * @return
	 */
	public boolean saveOrUpdateEquEvent(List<TmoEquEventCur> events, long nodeId);

	/**
	 * 保存或更新部件事件当前表
	 * 
	 * @param events
	 * @return
	 */
	public boolean saveOrUpdateModuleEvent(List<TmoEquEventCur> events, long nodeId);

	/**
	 * 清理TmoEquEventCur表数据
	 * 
	 * @param nodeId
	 * @return
	 */
	void cleanModuleEventCur(Long nodeId);

	/**
	 * 保存设备状态
	 * 
	 * @param status
	 * @return
	 */
	public boolean saveEquStatus(List<TmoEquStatus> status);

	/**
	 * 获取设备当前状态表
	 * 
	 * @param status
	 * @return
	 */

	public PageHolder<TmoEquStatusCur> getEquStatusList(EquStatusFilterForm filterForm, int pageIndex);

	/**
	 * 获取设备历史状态表
	 * 
	 * @param status
	 * @return
	 */

	public PageHolder<TmoEquStatus> getEquStatusListHis(EquStatusFilterForm filterForm, int pageIndex);

	/**
	 * 获取TmoEquStatusCur表数据
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<TmoEquStatusCur> getEquStatusEntity(Long nodeId, Short statusId);

	/**
	 * 保存或更新设备状态表
	 * 
	 * @param status
	 * @return
	 */
	public boolean saveOrUpdateEquStatus(List<TmoEquStatusCur> status, long nodeId);

	/**
	 * 更新设备当前状态表
	 * 
	 * @param recordId
	 * @return
	 */
	public boolean updateEquStatus(long recordId, Short statusValue, String statusDesc, Timestamp currentTime,
			Short statusLevel);

	/**
	 * 保存设备当前状态表
	 * 
	 * @param recordId
	 * @return
	 */
	public boolean saveEquStatusCur(List<TmoEquStatusCur> status);

	/**
	 * 保存或更新状态表TmoItemStatus
	 * 
	 * @param deviceStatus
	 * @param deviceId
	 */
	public boolean saveOrUpdateItemStatus(short deviceStatus, long deviceId);

	boolean updateItemActiveStatus(short itemActiveStatus);

	void loadDeviceStatusFromDB();

}
