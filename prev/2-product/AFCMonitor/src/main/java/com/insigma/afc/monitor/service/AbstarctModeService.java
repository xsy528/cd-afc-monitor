/* 
 * 日期：2010-10-14
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.ICommonDAO;

/**
 * Ticket: 模式Service接口抽象实现类<br/>
 * 
 * @author yaoyue
 */
public abstract class AbstarctModeService implements IWZModeService {
	protected Log logger = LogFactory.getLog(getClass());

	protected ICommonDAO commonDAO;

	private final boolean onLineStatus = true;

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	//	/**
	//	 * 只简单更新数据库中的当前模式
	//	 * 
	//	 * @param nodeId
	//	 * @param newModeId
	//	 * @return
	//	 */
	//	public boolean saveOrUplateCurrentModeId(long nodeId, short newModeId, Date changeTime) {
	//		short lineId = AFCApplication.getLineId();
	//		int stationId = 0;
	//		short itemType = AFCApplication.getNodeType(nodeId);
	//
	//		RouteAddressUtil addressUtil = new RouteAddressUtil();
	//		if (addressUtil.getNodeLevel(nodeId).getStatusCode() >= AFCNodeLevel.SC.getStatusCode()) {
	//			stationId = AFCApplication.getStationId(nodeId);
	//		}
	//		IMetroNodeStatusService modeNodeStatusService = null;
	//		try {
	//			modeNodeStatusService = (IMetroNodeStatusService) Application.getService(IMetroNodeStatusService.class);
	//		} catch (Exception e) {
	//			logger.error("IMetroNodeStatusService加载失败", e);
	//		}
	//		TmoItemStatus tmoItemStatus = modeNodeStatusService.getTmoItemStatus(false, nodeId);
	//		if (tmoItemStatus == null) {
	//			logger.warn("接收到上传车站模式更新信息，新增该节点。对应的节点：0x" + nodeId + "状态信息为空");
	//			tmoItemStatus = new TmoItemStatus(lineId, stationId, nodeId, itemType);
	//			tmoItemStatus.setUpdateTime(new Date());
	//			//收到设备状态报文默认该设备在线
	//			tmoItemStatus.setItemActivity(onLineStatus);
	//			try {
	//				commonDAO.saveObj(tmoItemStatus);
	//			} catch (OPException e) {
	//				logger.error("接收到上传车站模式更新信息，更新节点状态表异常", e);
	//			}
	//		}
	//
	//		Short lastModeCode = tmoItemStatus.getCurrentModeCode();
	//		if (tmoItemStatus.getCurrentModeCode() != null && newModeId == tmoItemStatus.getCurrentModeCode()) {
	//			logger.debug(String.format("当前模式%d和新模式%d相同，无需更新", tmoItemStatus.getCurrentModeCode(), newModeId));
	//			logger.debug("tmoItemStatus : " + ReflectionToStringBuilder.toString(tmoItemStatus));
	//			return false;
	//		}
	//
	//		tmoItemStatus.setLastModeCode(lastModeCode);
	//		tmoItemStatus.setCurrentModeCode(newModeId);
	//		tmoItemStatus.setModeChangeTime(changeTime);
	//		try {
	//			logger.debug("更新节点状态信息 : " + ReflectionToStringBuilder.toString(tmoItemStatus));
	//			commonDAO.updateObj(tmoItemStatus);
	//		} catch (OPException e) {
	//			logger.error("接收到上传车站模式更新信息，更新节点状态表异常", e);
	//		}
	//
	//		return true;
	//	}

	public TmoItemStatus getCurrentTmoItemMode(long nodeId) {
		QueryFilter filters = new QueryFilter();
		filters.propertyFilter("lineId", AFCApplication.getLineId(nodeId));
		filters.propertyFilter("stationId", AFCApplication.getStationId(nodeId));
		filters.propertyFilter("nodeId", nodeId);
		try {
			List<TmoItemStatus> datas = commonDAO.fetchListByFilter(filters, TmoItemStatus.class);
			if (datas.size() > 0) {
				return datas.get(0);
			}
		} catch (OPException e) {
			logger.error("查询车站：" + nodeId + " 当前模式失败", e);
		}
		return null;
	}

	public List<TmoItemStatus> getAllTmoItemModeList(long stationID) {
		QueryFilter filters = new QueryFilter();
		filters.propertyFilter("lineId", AFCApplication.getLineId());
		filters.propertyFilter("stationId", AFCApplication.getStationId(stationID));
		filters.propertyFilter("nodeId", AFCApplication.getNodeId(stationID));
		try {
			List<TmoItemStatus> datas = commonDAO.fetchListByFilter(filters, TmoItemStatus.class);
			return datas;
		} catch (OPException e) {
			logger.error("查询车站：" + stationID + " 当前所有设备模式失败", e);
			throw new ApplicationException("查询车站：" + stationID + " 当前所有设备模式失败。", e);
		}
	}

	//	/*
	//	 * (non-Javadoc)
	//	 * @see
	//	 * com.insigma.afc.suzhou.messageserver.service.ISZModeService#handleModeBroadcast(java.util
	//	 * .List)
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public long handleModeBroadcast(List<TmoModeBroadcast> modeBroadcasts) {
	//		// 先更新所有车站的模式为正常模式
	//		try {
	//			commonDAO.executeHQLUpdate("update TmoModeBroadcast set modeCode=0, modeBroadcastTime=?", new Timestamp(
	//					System.currentTimeMillis()));
	//
	//			for (TmoModeBroadcast tmoModeBroadcast : modeBroadcasts) {
	//				List<TmoModeBroadcast> entityListHQL = commonDAO.getEntityListHQL(
	//						"from TmoModeBroadcast where lineId=? and stationId=? ", tmoModeBroadcast.getLineId(),
	//						tmoModeBroadcast.getStationId());
	//				TmoModeBroadcast mdeBroadcast = null;
	//				if (entityListHQL != null && !entityListHQL.isEmpty()) {
	//					if (entityListHQL.size() > 1) {
	//						commonDAO.executeHQLUpdate("delete from TmoModeBroadcast where lineId=? and stationId=? ",
	//								tmoModeBroadcast.getLineId(), tmoModeBroadcast.getStationId());
	//						mdeBroadcast = tmoModeBroadcast;
	//					} else {
	//						mdeBroadcast = entityListHQL.get(0);
	//						mdeBroadcast.setModeCode(tmoModeBroadcast.getModeCode());
	//						mdeBroadcast.setModeBroadcastTime(tmoModeBroadcast.getModeBroadcastTime());
	//					}
	//				} else {
	//					mdeBroadcast = tmoModeBroadcast;
	//				}
	//				commonDAO.saveOrUpdateObj(mdeBroadcast);
	//			}
	//		} catch (OPException e) {
	//			throw new ApplicationException("保存模式广播信息失败。", e);
	//		}
	//		return 0;
	//	}
	//
	public void saveModeUploadInfo(TmoModeUploadInfo tmoModeUploadInfo) {
		try {
			commonDAO.saveObj(tmoModeUploadInfo);
		} catch (Exception e) {
			logger.error("保存模式上传信息异常", e);
			throw new ApplicationException("保存模式上传信息异常", e);
		}
	}

	public void saveOrUpdateModeBroadcastInfo(TmoModeBroadcast modeBroadcast) {
		try {
			commonDAO.saveOrUpdateObj(modeBroadcast);
		} catch (Exception e) {
			logger.error("保存更新模式广播信息异常", e);
			throw new ApplicationException("保存更新模式广播信息异常", e);
		}
	}

}
