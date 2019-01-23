/* 
 * 日期：2010-10-14
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.AbstarctModeService;
import com.insigma.afc.monitor.service.IWZModeService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.model.dto.EventFilterForm;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.application.Application;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Ticket: <br/>
 */
public class WZModeServiceImpl extends AbstarctModeService implements IWZModeService {

	public long handleModeCommand(final long senderId, long stationId, short newModeId, long operationId) {

		if (saveOrUpdateCurrentModeId(senderId, newModeId, new Date())) {
			// 保存成功
			return 0L;
		} else {
			// 保存不成功
			return 1L;
		}
	}

	//    @SuppressWarnings("unchecked")
	//    public List<TmoModeUploadInfo> getAbnormalTmoModeUploadInfo() {
	//        String hql = "from TmoModeUploadInfo where modeCode<>0";
	//        try {
	//            return commonDAO.getEntityListHQL(hql);
	//        } catch (OPException e) {
	//            throw new ApplicationException("获取非正常模式车站模式列表失败。", e);
	//        }
	//    }

	//    /**
	//     * 1. SC将该模式通知中的节点状态入库,模式通知表入库 <br/>
	//     * 2. 通知到本车站SLE（SLE不在线，忽略），同时通知LCC;
	//     */
	//    public long handleModeNotify(ModeStatusUpload_Req_t req) {
	//        long deviceId = req.getSource();
	//        Timestamp onTime = req.getModeOccurTime();
	//        short modeId = req.getModeCode();
	//        short lineId = routeAddressUtil.getLineIdByDeviceId(deviceId);
	//        int stationId = routeAddressUtil.getStationIdByDeviceId(deviceId);
	//        // long nodeId = routeAddressUtil.getNodeIdByDeviceId(deviceId);
	//        saveOrUplateCurrentModeId(deviceId, modeId, onTime);
	//        TmoModeUploadInfo tmoModeUploadInfo = new TmoModeUploadInfo();
	//        tmoModeUploadInfo.setLineId(lineId);
	//        tmoModeUploadInfo.setStationId(stationId);
	//        tmoModeUploadInfo.setModeCode(modeId);
	//        tmoModeUploadInfo.setModeUploadTime(onTime);
	//        tmoModeUploadInfo.setBroadcastStatus((short) 0);
	//        try {
	//            saveModeUploadInfo(tmoModeUploadInfo);
	//        } catch (Exception e) {
	//            logger.error("保存模式信息异常", e);
	//        }
	//
	//        return 0;
	//    }
	//
	//    public List<TmoItemStatus> getTmoItemMode(int lindId) {
	//        String hql = "from TmoItemStatus t where t.id.lineId=?";
	//        try {
	//            return commonDAO.getEntityListHQL(hql, (short) lindId);
	//        } catch (OPException e) {
	//            throw new ApplicationException("获取线路" + lindId + "下车站模式列表失败。", e);
	//        }
	//    }
	//
	public List<TmoItemStatus> getTmoItemMode(long nodeId) {
		List<TmoItemStatus> rstList = null;
		short lineId = AFCApplication.getLineId(nodeId);
		int stationId = AFCApplication.getStationId(nodeId);

		List<Object> paramList = new ArrayList<Object>();

		String hql = "from TmoItemStatus t where 1=1 ";

		if (lineId != 0) {
			hql = hql + " and t.id.lineId=?";
			paramList.add(lineId);
		}

		if (stationId != 0) {
			hql = hql + " and t.id.stationId=?";
			paramList.add(stationId);
		}

		try {
			rstList = commonDAO.getEntityListHQL(hql, paramList.toArray());
		} catch (OPException e) {
			throw new ApplicationException("获取节点：" + nodeId + "下所有子节点模式列表失败。", e);
		}

		return rstList;
	}

	//    public List<TmoItemStatus> getDeviceTmoItemMode(short lindID, int stationID) {
	//        String hql = "from TmoItemStatus t where t.id.lineId=? and t.id.stationId=? and t.itemType=2";
	//        try {
	//            return commonDAO.getEntityListHQL(hql, lindID, stationID);
	//        } catch (OPException e) {
	//            throw new ApplicationException("获取线路" + lindID + "下车站模式列表失败。", e);
	//        }
	//    }

	//    public List<TapTpEodVersionInquiry_t> getTpEodVersionInquiryList(long nodeID) {
	//
	//        String hql = "from TapTpEodVersionInquiry_t where nodeId=?";
	//        try {
	//            return commonDAO.getEntityListHQL(hql, nodeID);
	//        } catch (OPException e) {
	//            throw new ApplicationException("获取TP版本信息异常", e);
	//        }
	//    }

	public void updateAllSubNodeStatus(long nodeId, boolean status) {
		short lineId = AFCApplication.getLineId(nodeId);
		int stationId = AFCApplication.getStationId(nodeId);
		AFCNodeLevel nodeType = AFCApplication.getNodeLevel(nodeId);

		AFCNodeLevel type = AFCApplication.getNodeLevel(nodeId);

		if (type == null) {
			return;
		}

		if (nodeType == AFCNodeLevel.ACC) {
			commonDAO.executeHQLUpdate(
					"update TmoItemStatus t set t.itemActivity=? " + ", t.updateTime=? " + " where " + "t.nodeType>?",
					status, new Date(), (short) nodeType.getStatusCode());
			logger.info("更新ACC下级节点（LC、SC、SLE）状态成功！");
		} else if (nodeType == AFCNodeLevel.LC) {
			commonDAO.executeHQLUpdate(
					"update TmoItemStatus t set t.itemActivity=? " + ", t.updateTime=? " + " where "
							+ "t.lineId=? and t.nodeType>?",
					status, new Date(), lineId, (short) nodeType.getStatusCode());
			logger.info("更新LC下级节点（SC、SLE）状态成功！");
		} else if (nodeType == AFCNodeLevel.SC) {
			commonDAO.executeHQLUpdate(
					"update TmoItemStatus t set t.itemActivity=? " + ", t.updateTime=? " + " where "
							+ "t.lineId=? and t.stationId=? and t.nodeType>?",
					status, new Date(), lineId, stationId, (short) nodeType.getStatusCode());
			logger.info("更新SC下级节点（SLE）状态成功！");
		}
	}

	//    public void updateTmoItemStatus(long nodeId, short status) {
	//        short lineId = routeAddressUtil.getLineIdByDeviceId(nodeId);
	//        int stationId = routeAddressUtil.getShortStationIdByDeviceId(nodeId);
	//        long deviceId = routeAddressUtil.getSLEIdByDeviceId(nodeId);
	//
	//        if (checkExist(lineId, stationId, deviceId)) {
	//            commonDAO.executeHQLUpdate("update TmoItemStatus t set t.itemActivity=? "
	//                    + ", t.updateTime=? " + " where "
	//                    + "t.id.lineId=? and t.id.stationId=? and t.id.deviceId=?", status, new Date(),
	//                    lineId, stationId, deviceId);
	//            HZAppCommonUtil.redPrint(String
	//                    .format("更新设备节点状态：【节点/0x%8x】【status/%d】", nodeId, status));
	//            logger.debug(String.format("更新设备节点状态：【节点/0x%8x】【status/%d】", nodeId, status));
	//        } else {
	//            TmoItemStatus tmoItemStatus = new TmoItemStatus(new TmoItemStatusId(lineId, stationId,
	//                    deviceId), (short) 0, status, (short) 0, (short) 0, new Date(),
	//                    (short) routeAddressUtil.getAFCNodeType(nodeId).getStatusCode());
	//
	//            tmoItemStatus.setUpdateTime(new Date());
	//            commonDAO.saveOrUpdateObj(tmoItemStatus);
	//            logger.info(String.format(
	//                    "添加节点状态表TmoItemStatus成功：节点状态（%d）,在线状态（%d）,线路（%d）,车站（%d）,设备（%x）", status,
	//                    status, lineId, stationId, deviceId));
	//        }
	//    }

	private boolean checkExist(short lineId, int stationId, long deviceId) {
		String hql = "from TmoItemStatus t where t.id.lineId=? and t.id.stationId=? and t.id.deviceId=?";
		try {
			List list = commonDAO.getEntityListHQL(hql, lineId, stationId, deviceId);
			return (list != null && list.size() >= 1);
		} catch (OPException e) {
			throw new ApplicationException("获取节点[" + deviceId + "]车站模式列表失败。", e);
		}
	}

	//    public void initStationTmoItemStatus(long nodeId, short status) {
	//        short lineId = routeAddressUtil.getLineIdByDeviceId(nodeId);
	//        int stationId = routeAddressUtil.getShortStationIdByDeviceId(nodeId);
	//        long deviceId = routeAddressUtil.getSLEIdByDeviceId(nodeId);
	//
	//        try {
	//            if (!checkExist(lineId, stationId, deviceId)) {
	//                TmoItemStatus tmoItemStatus = new TmoItemStatus(new TmoItemStatusId(lineId,
	//                        stationId, deviceId), (short) 0, status, (short) 0, (short) 0, new Date(),
	//                        (short) routeAddressUtil.getAFCNodeType(nodeId).getStatusCode());
	//
	//                tmoItemStatus.setUpdateTime(new Date());
	//                commonDAO.saveObj(tmoItemStatus);
	//                logger.info(String.format(
	//                        "车站节点状态表TmoItemStatus初始化成功：节点状态（%d）,在线状态（%d）,线路（%d）,车站（%d）,设备（%x）", status,
	//                        status, lineId, stationId, deviceId));
	//            }
	//        } catch (OPException e) {
	//            e.printStackTrace();
	//        }
	//    }

	//    public void updateTmoItemStatusMode(long nodeId, short mode) {
	//        short lineId = routeAddressUtil.getLineIdByDeviceId(nodeId);
	//        int stationId = routeAddressUtil.getShortStationIdByDeviceId(nodeId);
	//        long deviceId = routeAddressUtil.getSLEIdByDeviceId(nodeId);
	//
	//        commonDAO.executeHQLUpdate("update TmoItemStatus t set t.currentModeCode=? "
	//                + ", t.updateTime=? " + " where "
	//                + "t.id.lineId=? and t.id.stationId=? and t.id.deviceId=?", mode, new Date(),
	//                lineId, stationId, deviceId);
	//
	//        HZAppCommonUtil.redPrint(String.format("更新车站模式状态：【节点/0x%8x】【status/%d】", nodeId, mode));
	//    }

	public List<TmoEquEventCur> getDeviceEventList(Short[] lineIds, Integer[] stationIds, Long[] deviceIds,
                                                   Short[] eventIds, Date startTime, Date endTime, Integer currentPage, Integer pageSize) {
		List<TmoEquEventCur> list = null;
		try {
			String hql = "from TmoEquEventCur t where t.occurTime>=? and t.occurTime<=?   and t.eventStatus= "
					+ TmoEquEvent.UN_SEND + " ";

			if (lineIds != null && lineIds.length >= 0) {
				hql += SqlRestrictions.in("t.lineId", StringHelper.array2StrOfCommaSplit(lineIds));
			}

			if (stationIds != null && stationIds.length >= 0) {
				hql += SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationIds));
			}

			if (deviceIds != null && deviceIds.length >= 0) {
				hql += SqlRestrictions.in("t.nodeId", StringHelper.array2StrOfCommaSplit(deviceIds));
			}

			if (eventIds != null && eventIds.length >= 0) {
				hql += SqlRestrictions.in("t.tagId", StringHelper.array2StrOfCommaSplit(eventIds));
			}

			Calendar startCalendar = Calendar.getInstance();

			if (startTime == null) {
				startCalendar.set(Calendar.YEAR, 1970);
				startTime = startCalendar.getTime();
			}
			if (endTime == null) {
				Date date = new Date();
				startCalendar.setTime(date);
				startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) + 1);
				endTime = startCalendar.getTime();
			}

			hql += " order by t.occurTime desc";

			if (currentPage != null) {
				return commonDAO.retrieveEntityListHQL(hql, currentPage, pageSize, startTime, endTime);
			}

			list = this.commonDAO.getEntityListHQL(hql, startTime, endTime);
		} catch (OPException ex) {
			throw new ApplicationException("根据设备事件级别得到车站设备事件列表失败。", ex);
		}
		return list;
	}

	public void saveOrUpdate(Object... objects) {
		if (objects.length < 1) {
			return;
		}
		try {
			commonDAO.saveAndUpdateObjs(objects);
		} catch (OPException e) {
			throw new ApplicationException("保存对象：" + objects[0].getClass().getSimpleName() + "失败。", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#handleModeBroadcast(java.util.List)
	 */
	@Override
	public long handleModeBroadcast(List<TmoModeBroadcast> modeBroadcasts) {
		return 0;
	}

	/**
	 * 只简单更新数据库中的当前模式
	 * 
	 * @param nodeId
	 * @param newModeId
	 * @return
	 */
	@Override
	public boolean saveOrUpdateCurrentModeId(long nodeId, short newModeId, Date changeTime) {
		short lineId = AFCApplication.getLineId(nodeId);
		int stationId = AFCApplication.getStationId(nodeId);
		AFCNodeLevel nodeType = AFCApplication.getNodeLevel(nodeId);

		if (nodeType == null) {
			return false;
		}

		IMetroNodeStatusService modeNodeStatusService = null;
		try {
			modeNodeStatusService = (IMetroNodeStatusService) Application.getService(IMetroNodeStatusService.class);
		} catch (Exception e) {
			logger.error("获取IMetroNodeStatusService失败。", e);
		}

		TmoItemStatus tmoItemStatus = modeNodeStatusService.getTmoItemStatus(lineId, stationId, nodeId);
		if (tmoItemStatus == null) {
			logger.warn("接收到上传车站模式更新信息，新增该节点。对应的节点：0x" + Long.toHexString(nodeId) + "状态信息为空。");
			tmoItemStatus = new TmoItemStatus();
			tmoItemStatus.setLineId(lineId);
			tmoItemStatus.setStationId(stationId);
			tmoItemStatus.setNodeId(nodeId);
			tmoItemStatus.setNodeType((short) nodeType.getStatusCode());
			tmoItemStatus.setCurrentModeCode(newModeId);
			tmoItemStatus.setModeChangeTime(changeTime);
			tmoItemStatus.setUpdateTime(new Date());

			try {
				commonDAO.saveObj(tmoItemStatus);
			} catch (OPException e) {
				logger.error("接收到上传车站模式更新信息，更新节点状态表异常", e);
			}

			return true;
		}

		short lastModeCode = tmoItemStatus.getCurrentModeCode();

		if (newModeId == tmoItemStatus.getCurrentModeCode()) {
			return true;
		}

		//		if (newModeId == AFCModeCode.END_URGENCY_MODE_CODE) {
		//			// 解除紧急模式更新回上次的模式
		//			newModeId = tmoItemStatus.getLastModeCode();
		//			if (lastModeCode != AFCModeCode.START_URGENCY_MODE_CODE) {
		//				// 如果当前非紧急模式，收到解除紧急模式，直接返回
		//				return true;
		//			}
		//		}

		tmoItemStatus.setLastModeCode(lastModeCode);
		tmoItemStatus.setCurrentModeCode(newModeId);
		tmoItemStatus.setModeChangeTime(changeTime);
		try {
			commonDAO.updateObj(tmoItemStatus);
		} catch (OPException e) {
			logger.error("接收到上传车站模式更新信息，更新节点状态表异常", e);
			return false;
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getModeSearchList(java.lang.Short, java.lang.Short, java.util.Date, java.util.Date, int, int)
	 */
	@Override
	public List<Object> getModeSearchList(Short lineId, Short stationId, Date beginTime, Date endTime, int pageNum,
			int pageSize) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getModeSearchCount(java.lang.Short, java.lang.Short, java.util.Date, java.util.Date)
	 */
	@Override
	public int getModeSearchCount(Short lineId, Short stationId, Date benginTime, Date endTime) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getMetroNodeList()
	 */
	@Override
	public List<Object[]> getMetroNodeList() {
		List<Object[]> list = null;

		String sql = "select t.LINEID, t.LINENAME, t.STATIONID,t.STATIONNAME from V_METRO_NODE t where 1=1 ";

		try {
			list = commonDAO.execSqlQuery(sql);
		} catch (OPException e) {
			logger.debug("获取线路车站节点信息数据失败。");
		}

		return list;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#saveOrUpdateOnlineStatus(short, int, long, short)
	 */
	@Override
	public boolean saveOrUpdateOnlineStatus(short lineId, int stationId, long nodeId, short status) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getAbnormalTmoModeUploadInfo()
	 */
	@Override
	public List<TmoModeUploadInfo> getAbnormalTmoModeUploadInfo() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getTmoItemMode(int)
	 */
	@Override
	public List<TmoItemStatus> getTmoItemMode(int stationId) {
		String hql = "from TmoItemStatus t where t.stationId <> ? and t.nodeType = " + AFCNodeLevel.SC.getStatusCode();
		try {
			return commonDAO.getEntityListHQL(hql, stationId);
		} catch (OPException e) {
			throw new ApplicationException("获取" + Integer.toHexString(stationId) + "站模式列表失败。", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getDeviceTmoItemMode(short, int)
	 */
	@Override
	public List<TmoItemStatus> getDeviceTmoItemMode(short lindID, int stationID) {
		String hql = "from TmoItemStatus t where t.lineId=? and t.stationId=? and t.nodeType=2";
		try {
			return commonDAO.getEntityListHQL(hql, lindID, stationID);
		} catch (OPException e) {
			throw new ApplicationException("获取线路" + lindID + "下车站模式列表失败。", e);
		}
	}

	@Override
	public PageHolder<TmoEquStatus> getEventList(EventFilterForm filterForm, int pageIndex) {
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
		String hql = "from TmoEquStatus event where 1=1 ";
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

		List<TmoEquStatus> results = new ArrayList<TmoEquStatus>();

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

		PageHolder<TmoEquStatus> pageHolder = new PageHolder<TmoEquStatus>(pageIndex, pageSize, num, results);

		return pageHolder;
	}

	@Override
	public PageHolder<TmoEquStatusCur> getEquStatusList(EventFilterForm filterForm, int pageIndex) {
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
				}else if(o instanceof Long){
					deviceList.add(Long.parseLong(o.toString()));
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
		String hql = "from TmoEquStatusCur event where 1=1 ";
		String hqlNum = "select count(*) ";
		String hqlOrderBy = "";

		if (equType != null && !equType.isEmpty()) {
			hql += SqlRestrictions.in("event.deviceType", StringHelper.array2StrOfCommaSplit(equType.toArray()));
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

		List<TmoEquStatusCur> results = new ArrayList<TmoEquStatusCur>();

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

		PageHolder<TmoEquStatusCur> pageHolder = new PageHolder<TmoEquStatusCur>(pageIndex, pageSize, num, results);

		return pageHolder;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IWZModeService#getCurTmoItemMode(int)
	 */
	@Override
	public List<TmoItemStatus> getCurTmoItemMode(int stationId) {
		String hql = "from TmoItemStatus t where t.stationId =? and t.nodeType = " + AFCNodeLevel.SC.getStatusCode();
		try {
			return commonDAO.getEntityListHQL(hql, stationId);
		} catch (OPException e) {
			throw new ApplicationException("获取" + Integer.toHexString(stationId) + "站模式列表失败。", e);
		}
	}
}
