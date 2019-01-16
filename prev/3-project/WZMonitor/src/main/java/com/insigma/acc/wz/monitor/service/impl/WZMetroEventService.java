package com.insigma.acc.wz.monitor.service.impl;

import com.insigma.acc.wz.define.WZDeviceStatus;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.monitor.entity.*;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.afc.monitor.service.IMetroEventService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * 事件查询
 * 
 * @author CaiChunye
 */
@SuppressWarnings("unchecked")
public class WZMetroEventService extends Service implements IMetroEventService {

	/**
	 * 保存或更新状态表TmoItemStatus
	 * 
	 * @param itemStatus
	 *            （ 0：正常服务， 1：警告 ，2：报警 ，4：离线 ）
	 * @param nodeId
	 * @return
	 */
	public boolean saveOrUpdateItemStatus(short itemStatus, long nodeId) {
		if (commonDAO == null) {
			logger.error("ICommonDAO为空，无法更新TmoItemStatus状态。");
			return false;
		} else {
			short lineId = AFCApplication.getLineId(nodeId);
			int stationId = AFCApplication.getStationId(nodeId);
			AFCNodeLevel type = AFCApplication.getNodeLevel(nodeId);

			if (type == null) {
				return false;
			}

			short nodeType = (short) type.getStatusCode();

			try {
				List<TmoItemStatus> list = commonDAO.getEntityListHQL(
						"from TmoItemStatus t where t.nodeId=? and t.stationId=? and t.lineId=? and t.nodeType=?",
						nodeId, stationId, lineId, nodeType);

				if (list != null && !list.isEmpty()) {
					TmoItemStatus tmoItemStatus = list.get(0);

					short status = tmoItemStatus.getItemStatus();
					boolean itemActivity = tmoItemStatus.getItemActivity();

					//若上报设备状态为正常,则大状态为在线
					if (itemStatus != WZDeviceStatus.OFF_LINE.shortValue()) {
						itemActivity = true;
					} else {
						itemActivity = false;
					}

					status = itemStatus;

					//为了防止终端时钟跳变后时间不统一,这里更新时间统一为MS服务器时间
					commonDAO.executeHQLUpdate(
							"update TmoItemStatus t set t.updateTime=?, t.itemActivity=? , t.itemStatus=?"
									+ " where t.nodeId=? and t.stationId=? and t.lineId=? ",
							new Date(), itemActivity, status, nodeId, stationId, lineId);

					logger.info(String.format("更新节点状态表TmoItemStatus成功：节点状态（%d）,在线状态（%d）,线路（%d）,车站（%x）,设备（%x）", status,
							itemActivity == true ? 1 : 0, lineId, stationId, nodeId));
				} else {
					short status = 0;
					boolean itemActivity = false;// 默认离线状态

					if (itemStatus != WZDeviceStatus.OFF_LINE.shortValue()) {
						itemActivity = true;
					}

					status = itemStatus;

					TmoItemStatus tmoItemStatus = new TmoItemStatus(lineId, stationId, nodeId, nodeType, status,
							itemActivity, (short) 0, (short) 0, new Date(), new Date());

					commonDAO.saveObj(tmoItemStatus);
					logger.info(String.format("添加节点状态表TmoItemStatus成功：节点状态（%d）,在线状态（%d）,线路（%d）,车站（%x）,设备（%x）", status,
							itemActivity == true ? 1 : 0, lineId, stationId, nodeId));
				}

				if (itemStatus == WZDeviceStatus.OFF_LINE.shortValue()) {
					updateAllSubNodeStatus(nodeId, false);
				}
			} catch (Exception e) {
				logger.error("更新节点状态表TmoItemStatus失败。", e);
				return false;
			}
		}
		return true;
	}

	private void updateAllSubNodeStatus(long nodeId, boolean status) {
		short lineId = AFCApplication.getLineId(nodeId);
		int stationId = AFCApplication.getStationId(nodeId);
		AFCNodeLevel nodeType = AFCApplication.getNodeLevel(nodeId);

		if (nodeType == null) {
			return;
		}

		if (nodeType == AFCNodeLevel.LC) {
			commonDAO.executeHQLUpdate(
					"update TmoItemStatus t set t.itemActivity=? " + ", t.updateTime=? " + " where "
							+ "t.id.lineId=? and t.itemType>?",
					status, new Date(), lineId, (short) nodeType.getStatusCode());
			logger.info("更新LC下级节点（SC、SLE）状态成功！");
		} else if (nodeType == AFCNodeLevel.SC) {
			commonDAO.executeHQLUpdate(
					"update TmoItemStatus t set t.itemActivity=? " + ", t.updateTime=? " + " where "
							+ "t.id.lineId=? and t.id.stationId=? and t.itemType>?",
					status, new Date(), lineId, stationId, (short) nodeType.getStatusCode());
			logger.info("更新SC下级节点（SLE）状态成功！");
		}
	}

	/**
	 * 保存事件，需要注意的是此方法只保存单条记录。
	 */
	@Override
	public synchronized void saveEvent(TmoEquEvent event) {
		if (event == null) {
			logger.error("设备事件为空。");
			return;
		}

		try {
			/* (1) 首先存一份到历史事件库表 */
			if (!checkAndSaveEvent(event)) {
				return;
			}
			/* (2)然后存储或更新当前事件库表 */
			String hql = "from TmoEquEventCur t where t.nodeId=? and t.tagId=?";
			List<TmoEquEventCur> tagNameEventList = commonDAO.getEntityListHQL(hql, event.getNodeId(),
					event.getTagId());

			TmoEquEventCur eventCur = null;
			if (tagNameEventList != null && !tagNameEventList.isEmpty()) {
				if (tagNameEventList.size() > 1) {
					logger.error("--------------------------------------------------------------------");
					logger.error("|");
					logger.error("|");
					logger.error("|");
					logger.error("|设备事件重复现象定位[设备事件上传报文执行保存事件时]。当前事件中存在相同事件ID[" + event.getTagId() + "]的事件["
							+ tagNameEventList.size() + "个]。flag=tinycs");
					logger.error("|");
					logger.error("|");
					logger.error("|");
					logger.error("--------------------------------------------------------------------");
				}

				/* (2.1) 如果事件已存在，则执行更新操作 */
				eventCur = tagNameEventList.get(0);

				short eventStatus = event.getEventStatus() == null ? TmoEquEvent.UN_SEND : event.getEventStatus();
				String sql = "update TMO_EQU_EVENT_CUR  t set t.TAG_VALUE=?" + " , t.OCCUR_TIME=? , t.STATUS_LEVEL="
						+ event.getStatusLevel() + " , t.EVT_DESC='" + event.getEvtDesc() + "' , t.REMARK='"
						+ event.getRemark() + "' , t.EVENT_STATUS=" + eventStatus + " where t.NODE_ID="
						+ eventCur.getNodeId() + " and t.TAG_ID='" + eventCur.getTagId() + "'";

				long newSessionId = 0;
				long currentSessionId = 0;

				String newRemark = event.getRemark();
				String currentRemark = eventCur.getRemark();

				if (newRemark != null) {
					newSessionId = Long.parseLong(newRemark, 16);
				}

				if (currentRemark != null) {
					currentSessionId = Long.parseLong(currentRemark, 16);
				}

				if (newSessionId >= currentSessionId) {
					commonDAO.execSqlUpdate(sql, event.getTagValue(),
							new Timestamp(event.getOccurTime().getTime()));

					logger.info("更新事件ID：" + Integer.toHexString(eventCur.getTagId()) + "\n" + "更新事件Value："
							+ Integer.toHexString(event.getTagValue()));
					logger.debug("该事件在当前事件表已存在相同的TagID:" + event.getTagId() + "，只需更新当前表该事件发生时间和tagValue为："
							+ event.getTagValue());
				} else {
					logger.info("上传的事件的流水号小于当前事件表中相同TagID【" + eventCur.getTagId() + "】的流水号，不需更新该事件。");
				}
			} else {
				/* (2.2) 如果事件不存在，则将事件作为一条单独记录添加到当前事件库表中 */
				eventCur = new TmoEquEventCur();
				// TODO 
				BeanUtils.copyProperties(event, eventCur);
				commonDAO.saveObj(eventCur);

				logger.info("保存新事件：" + eventCur);
			}
		} catch (Exception e) {
			throw new ApplicationException("保存事件失败。", e);
		}
	}

	/**
	* 保存历史事件表
	* 
	* @param event
	* @return
	*/
	private boolean checkAndSaveEvent(TmoEquEvent event) {
		try {
			commonDAO.saveObj(event);
		} catch (OPException e) {
			throw new ApplicationException("历史事件入库失败。", e);
		}
		return true;
	}

	/**
	 * @itemActiveStatus: MapItemFactory.ItemStatusType.OFFLINE.getStatusCode(),
	 *                    MapItemFactory.ItemStatusType.ONLINE.getStatusCode()
	 */
	public boolean updateItemActiveStatus(short itemActiveStatus) {
		boolean status = false;
		if (itemActiveStatus == 5) {
			status = true;
		}
		commonDAO.executeHQLUpdate("update TmoItemStatus t set t.itemActivity=? ", status);
		return true;
	}

	@Override
	public PageHolder<TmoEquEventHistory> getEventHistoryList(EventFilterForm eventSearchCondition, int indexPage) {
		return null;
	}

	@Override
	public List<TmoEquEventHistory> getDeviceEventList(Short[] lineIds, Integer[] stationIds, Long[] deviceIds,
                                                       Short[] eventLevs, Date startTime, Date endTime, Integer pageNum, Integer pageSize) {
		return null;
	}

	@Override
	public PageHolder<TmoEquEventCur> getEventCurList(EventFilterForm eventSearchCondition, int indexPage) {
		return null;
	}

	@Override
	public List<TmoEquEventCur> getUnnormalStatus(long nodeId) {
		return null;
	}

	@Override
	public List<TmoEquEventCur> getDeviceModuleEvent(long nodeId) {
		return null;
	}

	@Override
	public PageHolder<TmoEquEvent> getEventList(EventFilterForm filterForm, int pageIndex) {
		if (filterForm == null) {
			throw new ApplicationException("查询当前事件列表条件为空。");
		}
		Date startTime = filterForm.getStartTime();
		Date endTime = filterForm.getEndTime();
		List<Short> equType = filterForm.getEquTypeList();
		List<Short> eventLevel = filterForm.getEventLevelList();
		Integer pageSize = filterForm.getPageSize();
		String orderField = filterForm.getOrderField();
		String orderType = filterForm.getOrderType();
		List<Long> deviceList = new ArrayList<Long>();
		Long[] deviceIds = null;
		List<Object> selections = filterForm.getSelections();
		if (selections != null) {
			for (Object o : selections) {
				if (o instanceof MetroNode) {
					final MetroNode metroNode = (MetroNode) o;
					long nodeId = AFCApplication.getNodeId(metroNode.id());
					deviceList.add(nodeId);
				}
			}
			deviceIds = deviceList.toArray(new Long[deviceList.size()]);
		}
		Calendar startCalendar = Calendar.getInstance();
		// 设置默认的查询开始时间
		if (startTime == null) {
			startCalendar.set(Calendar.YEAR, 1970);
			startTime = startCalendar.getTime();
		}
		// 设置默认的查询结束时间
		if (endTime == null) {
			Date date = new Date();
			startCalendar.setTime(date);
			startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) + 1);
			endTime = startCalendar.getTime();
		}
		String hql = "from TmoEquEvent event where 1=1 ";
		String hqlNum = "select count(*) ";
		String hqlOrderBy = "";

		if (equType != null && !equType.isEmpty()) {
			hql += SqlRestrictions.in("event.nodeType", StringHelper.array2StrOfCommaSplit(equType.toArray()));
		}

		if (deviceIds != null && deviceIds.length >= 0) {
			hql += SqlRestrictions.in("event.nodeId", StringHelper.array2StrOfCommaSplit(deviceIds));
		}

		List<String> levelList = new ArrayList<String>();
		if (eventLevel != null) {
			for (Object event : eventLevel) {
				if (event != null) {
					levelList.add("" + event);
				}
			}
		}
		if (!levelList.isEmpty()) {
			String[] level = new String[levelList.size()];
			levelList.toArray(level);
			hql += SqlRestrictions.in("event.statusLevel", StringHelper.array2StrOfCommaSplit(level));
		}
		hql += " and event.occurTime>=? and event.occurTime<=? ";

		List<TmoEquEvent> results = new ArrayList<TmoEquEvent>();

		int num = 0;
		if (orderField != null && orderType != null) {
			hqlOrderBy += " order by event." + orderField + " " + orderType;
		}
		try {
			results = commonDAO.retrieveEntityListHQL(hql + hqlOrderBy, pageIndex, pageSize, startTime, endTime);
			num = ((Number) commonDAO.retrieveEntityListHQL(hqlNum + hql, 0, pageSize, startTime, endTime).get(0))
					.intValue();
		} catch (OPException e) {
			e.printStackTrace();
			logger.error("查询当前事件列表异常", e);
			throw new ApplicationException("查询当前事件列表异常。", e);
		}

		PageHolder<TmoEquEvent> pageHolder = new PageHolder<TmoEquEvent>(pageIndex, pageSize, num, results);

		return pageHolder;
	}

	@Override
	public List<Object> getEquEventEntity(Long nodeId, Short eventId) {
		return null;
	}

	@Override
	public int getMaxByDeviceEventList(Short[] lineIds, Integer[] stationIds, Long[] deviceIds, Short[] eventLevs,
			Date startTime, Date endTime) {
		return 0;
	}

	@Override
	public List<Object[]> getDeviceComponentInfo(Long nodeId) {
		return null;
	}

	@Override
	public boolean saveEquEvent(List<TmoEquEvent> event) {
		return false;
	}

	@Override
	public boolean saveOrUpdateEquEvent(List<TmoEquEventCur> events, long nodeId) {
		return false;
	}

	@Override
	public boolean saveOrUpdateModuleEvent(List<TmoEquEventCur> events, long nodeId) {
		return false;
	}

	@Override
	public void cleanModuleEventCur(Long nodeId) {
	}

	@Override
	public boolean saveEquStatus(List<TmoEquStatus> status) {
		try {
			if (commonDAO == null) {
				logger.error("ICommonDAO为空，无法更新设备状态信息");
				return false;
			} else {
				for (TmoEquStatus tmoEquStatus : status) {
					//					commonDAO.saveOrUpdateObj(tmoEquStatus);
					commonDAO.saveObj(tmoEquStatus);
				}
			}
			logger.debug("保存或更新设备状态历史表成功");
			return true;
		} catch (Exception e) {
			throw new ApplicationException("更新TMO_EQU_STATUS失败", e);
		}
	}

	@Override
	public PageHolder<TmoEquStatusCur> getEquStatusList(EquStatusFilterForm filterForm, int pageIndex) {
		if (filterForm == null) {
			throw new ApplicationException("查询当前事件列表条件为空。");
		}
		Date startTime = filterForm.getStartTime();
		Date endTime = filterForm.getEndTime();
		List<Short> equType = filterForm.getEquTypeList();
		Integer pageSize = filterForm.getPageSize();
		String orderField = filterForm.getOrderField();
		String orderType = filterForm.getOrderType();
		List<Long> deviceList = new ArrayList<Long>();
		Long[] deviceIds = null;
		List<Object> selections = filterForm.getSelections();
		if (selections != null) {
			for (Object o : selections) {
				if (o instanceof MetroNode) {
					final MetroNode metroNode = (MetroNode) o;
					long nodeId = AFCApplication.getNodeId(metroNode.id());
					deviceList.add(nodeId);
				}
			}
			deviceIds = deviceList.toArray(new Long[deviceList.size()]);
		}
		Calendar startCalendar = Calendar.getInstance();
		// 设置默认的查询开始时间
		if (startTime == null) {
			startCalendar.set(Calendar.YEAR, 1970);
			startTime = startCalendar.getTime();
		}
		// 设置默认的查询结束时间
		if (endTime == null) {
			Date date = new Date();
			startCalendar.setTime(date);
			startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) + 1);
			endTime = startCalendar.getTime();
		}
		String hql = "from TmoEquStatusCur t where 1=1 ";
		String hqlNum = "select count(*) ";
		String hqlOrderBy = "";

		if (equType != null && !equType.isEmpty()) {
			hql += SqlRestrictions.in("t.nodeType", StringHelper.array2StrOfCommaSplit(equType.toArray()));
		}

		if (deviceIds != null && deviceIds.length >= 0) {
			hql += SqlRestrictions.in("t.nodeId", StringHelper.array2StrOfCommaSplit(deviceIds));
		}

		hql += " and t.occurTime>=? and t.occurTime<=? ";

		List<TmoEquStatusCur> results = new ArrayList<TmoEquStatusCur>();

		int num = 0;
		//		if (orderField != null && orderType != null) {
		//			hqlOrderBy += " order by t." + orderField + " " + orderType;
		//		}
		//优化排序-yang16.01.20
		hqlOrderBy += "order by t.item1 desc ,t.statusId";
		try {
			results = commonDAO.retrieveEntityListHQL(hql + hqlOrderBy, pageIndex, pageSize, startTime, endTime);
			num = ((Number) commonDAO.retrieveEntityListHQL(hqlNum + hql, 0, pageSize, startTime, endTime).get(0))
					.intValue();
		} catch (OPException e) {
			e.printStackTrace();
			logger.error("查询当前事件列表异常", e);
			throw new ApplicationException("查询当前事件列表异常。", e);
		}

		PageHolder<TmoEquStatusCur> pageHolder = new PageHolder<TmoEquStatusCur>(pageIndex, pageSize, num, results);

		return pageHolder;
	}

	@Override
	public PageHolder<TmoEquStatus> getEquStatusListHis(EquStatusFilterForm filterForm, int pageIndex) {
		return null;
	}

	@Override
	public List<TmoEquStatusCur> getEquStatusEntity(Long nodeId, Short statusId) {
		String hql = "from TmoEquStatusCur t where 1=1 ";

		List<Object> paramList = new ArrayList<Object>();
		if (nodeId != null) {
			hql = hql + "and t.nodeId =? ";
			paramList.add(nodeId.longValue());
		}
		if (statusId != null) {
			hql = hql + "and t.statusId =? ";
			paramList.add(statusId.shortValue());
		}

		hql = hql + "order by t.deviceId,t.occurTime desc";

		try {
			return commonDAO.getEntityListHQL(hql, paramList.toArray());
		} catch (Exception ex) {
			logger.error("获取设备状态信息失败。", ex);
		}

		return null;
	}

	@Override
	public boolean updateEquStatus(long recordId, Short statusValue, String statusDesc, Timestamp currentTime,
			Short statusLevel) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "update TMO_EQU_STATUS_CUR set SYS_TIME=?";
		paramList.add(new Date());
		if (statusValue != null) {
			sql = sql + " ,STATUS_VALUE=?";
			paramList.add(statusValue);
		}
		if (statusDesc != null) {
			sql = sql + " ,STATUS_DESC=?";
			paramList.add(statusDesc);
		}
		if (currentTime != null) {
			sql = sql + " ,OCCUR_TIME=?";
			paramList.add(currentTime);
		}
		if (statusLevel != null) {
			sql = sql + " ,ITEM1=?";
			paramList.add(statusLevel);
		}
		sql = sql + " where RECORD_ID=?";
		paramList.add(recordId);
		try {
			commonDAO.execSqlUpdate(sql, paramList.toArray());
		} catch (OPException e) {

			logger.error("更新设备当前状态表失败", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveEquStatusCur(List<TmoEquStatusCur> status) {
		try {
			if (commonDAO == null) {
				logger.error("ICommonDAO为空，无法更新设备状态信息");
				return false;
			} else {
				commonDAO.saveObjs(status.toArray());
			}
		} catch (Exception e) {
			logger.error("保存设备当前状态表失败", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveOrUpdateEquStatus(List<TmoEquStatusCur> status, long nodeId) {
		return false;
	}

	public void loadDeviceStatusFromDB() {
		//step1:初始化一个用于存储各节点状态信息的map
		Map<Long, Map<Short, Object[]>> deviceStatusMap = new HashMap<Long, Map<Short, Object[]>>();
		List<MetroNode> subNodes = (List<MetroNode>) AFCApplication.getAFCNode().getSubNodes();
		getDeviceNode(AFCApplication.getAFCNode().getNodeId(), subNodes, deviceStatusMap);
		//step2：从数据库查询节点状态信息

		try {
			List<TmoEquStatusCur> equStatusEntityList = getEquStatusEntity(null, null);
			for (TmoEquStatusCur equStatus : equStatusEntityList) {
				if (deviceStatusMap.containsKey(equStatus.getNodeId())) {
					Object[] statusValue = new Object[2];
					statusValue[0] = equStatus.getStatusValue();
					statusValue[1] = equStatus.getRecordId();
					deviceStatusMap.get(equStatus.getNodeId()).put(equStatus.getStatusId(), statusValue);
				}
			}
			Application.setData(ApplicationKey.STATUS_DB_KEY, deviceStatusMap);
		} catch (Exception e) {
			logger.error("查询数据库节点状态失败", e);
		}
	}

	//加载设备节点信息
	private static void getDeviceNode(long nodeID, List<MetroNode> subNodes,
			Map<Long, Map<Short, Object[]>> deviceStatusMap) {
		for (MetroNode nodeId : subNodes) {
			if (nodeId.getNodeId() == nodeID) {
				continue;
			} else if (nodeId instanceof MetroLine) {
				List<MetroNode> nodes = (List<MetroNode>) nodeId.getSubNodes();
				getDeviceNode(nodeId.getNodeId(), nodes, deviceStatusMap);
			} else if (nodeId instanceof MetroStation) {
				List<MetroNode> nodes = (List<MetroNode>) nodeId.getSubNodes();
				getDeviceNode(nodeId.getNodeId(), nodes, deviceStatusMap);
			} else {
				Map<Short, Object[]> statusDetailMap = new HashMap<Short, Object[]>();
				deviceStatusMap.put(nodeId.id(), statusDetailMap);
			}
		}
	}

}
