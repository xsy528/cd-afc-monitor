package com.insigma.afc.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 模式相关的Service
 * 
 * @author CaiChunye
 */
@SuppressWarnings("unchecked")
public class ModeService extends Service implements IModeService {

	/**
	 * 获取模式上传信息列表
	 * 
	 * @param lindId
	 *            线路编号
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
	 * @return List<TmoModeUploadInfo> 模式上传列表
	 */
	@Override
	public List<TmoModeUploadInfo> getModeUploadInfoList(Short lindId, Integer[] stationIds, Long[] deviceIds,
			Short modeCode, Short broadCastStatus, Date startTime, Date endTime, int page, int pageSize) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from TmoModeUploadInfo t where 1=1 ");
		if (lindId != null) {
			sb.append("and t.lineId=? ");
			args.add(lindId);
		}
		if (stationIds != null) {
			sb.append(SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationIds)));
		}
		if (deviceIds != null) {
			sb.append(SqlRestrictions.in("t.nodeId", StringHelper.array2StrOfCommaSplit(deviceIds)));
		}
		if (modeCode != null) {
			sb.append("and t.modeCode=? ");
			args.add(modeCode);
		}
		if (broadCastStatus != null) {
			sb.append("and t.broadcastStatus=? ");
			args.add(broadCastStatus);
		}
		if (startTime != null) {
			sb.append("and t.modeUploadTime>=? ");
			args.add(startTime);
		}
		if (endTime != null) {
			sb.append("and t.modeUploadTime<=? ");
			args.add(endTime);
		}
		sb.append("order by t.modeUploadTime desc,t.modeCode asc");
		List<TmoModeUploadInfo> modeUploadInfoList = null;
		try {
			modeUploadInfoList = commonDAO.retrieveEntityListHQL(sb.toString(), page, pageSize, args.toArray());
		} catch (OPException e) {
			logger.error("查询模式上传信息列表异常", e);
			throw new ApplicationException("查询模式上传信息列表异常。", e);
		}
		return modeUploadInfoList;
	}

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
	@Override
	public int getMaxofModeUploadInfo(Short lindId, Integer[] stationId, Short modeCode, Short broadCastStatus,
			Date startTime, Date endTime) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from TmoModeUploadInfo t where 1=1 ");
		if (lindId != null) {
			sb.append("and t.lineId=? ");
			args.add(lindId);
		}
		if (stationId != null) {
			sb.append(SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationId)));
		}
		if (modeCode != null) {
			sb.append("and t.modeCode=? ");
			args.add(modeCode);
		}
		if (broadCastStatus != null) {
			sb.append("and t.broadcastStatus=? ");
			args.add(broadCastStatus);
		}
		if (startTime != null) {
			sb.append("and t.modeUploadTime>=? ");
			args.add(startTime);
		}
		if (endTime != null) {
			sb.append("and t.modeUploadTime<=? ");
			args.add(endTime);
		}
		List list = null;
		try {
			list = commonDAO.getEntityListHQL(sb.toString(), args.toArray());
		} catch (OPException e) {
			logger.error("查询模式上传信息列表异常", e);
			throw new ApplicationException("查询模式上传信息列表异常。", e);
		}
		return Integer.valueOf(list.get(0).toString());
	}

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
	@Override
	public List<TmoModeBroadcast> getModeBroadcastList(Short lindId, Integer[] stationIds, Short modeCode,
			String operator, Short destLineId, Short broadCastStatus, Short broadCastType, Date startTime, Date endTime,
			int page, int pageSize) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from TmoModeBroadcast t where 1=1 ");
		if (lindId != null) {
			sb.append("and t.lineId=? ");
			args.add(lindId);
		}
		if (stationIds != null) {
			sb.append(SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationIds)));
		}
		if (modeCode != null) {
			sb.append("and t.modeCode=? ");
			args.add(modeCode);
		}
		if (operator != null) {
			sb.append("and t.operatorId=? ");
			args.add(operator);
		}
		if (startTime != null) {
			sb.append("and t.modeEffectTime>=? ");
			args.add(startTime);
		}
		if (endTime != null) {
			sb.append("and t.modeEffectTime<=? ");
			args.add(endTime);
		}
		if (destLineId != null) {
			sb.append("and t.targetId=? ");
			args.add(AFCApplication.getNodeId(destLineId));
		}
		if (broadCastStatus != null) {
			sb.append("and t.broadcastStatus=? ");
			args.add(broadCastStatus);
		}
		if (broadCastType != null) {
			sb.append("and t.broadcastType=? ");
			args.add(broadCastType);
		}

		// sb.append("order by t.broadcastStatus, t.broadcastType, t.modeBroadcastTime desc");
		sb.append("order by  t.modeEffectTime desc,t.modeBroadcastTime desc,t.targetId");
		List<TmoModeBroadcast> modeBroadcastList = null;
		try {
			modeBroadcastList = commonDAO.retrieveEntityListHQL(sb.toString(), page, pageSize, args.toArray());
		} catch (OPException e) {
			logger.error("查询模式广播信息列表异常", e);
			throw new ApplicationException("查询模式广播信息列表异常", e);
		}
		return modeBroadcastList;
	}

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
	@Override
	public List<TmoModeBroadcastViewItem> getTmoBroadcastViewItemList(Short lindId, Integer[] stationIds,
			Short modeCode, String operator, Short destStationId, Short broadCastStatus, Short broadCastType,
			Date startTime, Date endTime, int page, int pageSize) {
		// 查询语句
		List<TmoModeBroadcast> list = getModeBroadcastList(lindId, stationIds, modeCode, operator, destStationId,
				broadCastStatus, broadCastType, startTime, endTime, page, pageSize);

		List<TmoModeBroadcastViewItem> modeBroadcastViewItemList = null;
		if (list != null && !list.isEmpty()) {
			modeBroadcastViewItemList = new ArrayList<TmoModeBroadcastViewItem>();
			int index = 1 + page * pageSize;
			for (TmoModeBroadcast tmoModeBroadcast : list) {
				int stationID = tmoModeBroadcast.getStationId();
				MetroStation metroStation = getMetroStationByStationId(stationID);
				TmoModeBroadcastViewItem modeUploadInfoView = new TmoModeBroadcastViewItem();
				modeUploadInfoView.setId(tmoModeBroadcast.getRecordId());
				modeUploadInfoView.setRecordId(String.valueOf(index++));
				modeUploadInfoView.setOperatorId(AFCApplication.getUserNameByUserId(tmoModeBroadcast.getOperatorId()));
				modeUploadInfoView.setModeUploadTime(
						DateTimeUtil.formatDate(tmoModeBroadcast.getModeEffectTime(), "yyyy-MM-dd HH:mm:ss"));

				if (metroStation != null) {
					modeUploadInfoView.setLineName(metroStation.getLineName() + "/"
							+ String.format("0x%02x", metroStation.getId().getLineId()));
					modeUploadInfoView.setStationName(metroStation.getStationName() + "/"
							+ String.format("0x%04x", metroStation.getId().getStationId()));
				} else {
					modeUploadInfoView.setLineName("未知");
					modeUploadInfoView.setStationName("未知");
				}

				Map<Object, String> modeCodeMap = AFCModeCode.getInstance().getCodeMap();
				if (modeCodeMap != null) {
					Short modecode = tmoModeBroadcast.getModeCode();
					if (modeCodeMap.containsKey(modecode.intValue())) {
						modeUploadInfoView.setModeCode(modeCodeMap.get(modecode.intValue()) + "/"
								+ String.format("0x%02x", tmoModeBroadcast.getModeCode()));
					} else {
						modeUploadInfoView.setModeCode(String.format("0x%02x", tmoModeBroadcast.getModeCode()));
					}
				} else {
					modeUploadInfoView.setModeCode(String.format("0x%02x", tmoModeBroadcast.getModeCode()));
				}

				modeUploadInfoView.setModeBroadcastTime(
						DateTimeUtil.formatDate(tmoModeBroadcast.getModeBroadcastTime(), "yyyy-MM-dd HH:mm:ss"));

				if (tmoModeBroadcast.getBroadcastType() == 0) {
					modeUploadInfoView.setBroadcastType("自动");
				} else if (tmoModeBroadcast.getBroadcastType() == 1) {
					modeUploadInfoView.setBroadcastType("手动");
				}

				if (tmoModeBroadcast.getBroadcastStatus() == 0) {
					modeUploadInfoView.setBroadcastStatus("未确认");
				} else if (tmoModeBroadcast.getBroadcastStatus() == 1) {
					modeUploadInfoView.setBroadcastStatus("成功");
				} else if (tmoModeBroadcast.getBroadcastStatus() == 2) {
					modeUploadInfoView.setBroadcastStatus("失败");
				}

				if (tmoModeBroadcast.getTargetId() != null) {
					long tempTargetId = tmoModeBroadcast.getTargetId();

					if ((tempTargetId & 0xff000000) != 0) {
						tempTargetId = tempTargetId >> 16;

					}
					modeUploadInfoView.setTargetId(
							getStationName((int) tempTargetId) + "/" + String.format("0x%02x", tempTargetId));
				}
				modeBroadcastViewItemList.add(modeUploadInfoView);
			}
		}
		return modeBroadcastViewItemList;
	}

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
	@Override
	public int getMaxOfModeBroadcast(Short lindId, Integer[] stationIds, Short modeCode, String operator,
			Short destStationId, Short broadCastStatus, Short broadCastType, Date startTime, Date endTime) {

		List<TmoModeBroadcastViewItem> list = this.getTmoBroadcastViewItemList(lindId, stationIds, modeCode, operator,
				destStationId, broadCastStatus, broadCastType, startTime, endTime, -1, -1);

		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getAlreadyBroadcastItems(java.lang.String)
	 */
	@Override
	public List<TmoModeBroadcastViewItem> getAlreadyBroadcastItems(String stations) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getMaxofModeCmdLog(java.lang.Short[], java.lang.Integer[], java.lang.Long, java.util.Date, java.util.Date)
	 */
	@Override
	public int getMaxofModeCmdLog(Short[] lineIds, Integer[] stationIds, Long operatorId, Date startTime,
			Date endTime) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeCmdLog(java.lang.Short[], java.lang.Integer[], java.lang.Long, java.util.Date, java.util.Date, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Object> getModeCmdLog(Short[] lineIds, Integer[] stationIds, Long operatorId, Date startTime,
			Date endTime, Integer currentPage, Integer pageSize) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeInfo()
	 */
	@Override
	public List<TmoModeBroadcast> getModeInfo() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeBroadcast(java.lang.Boolean)
	 */
	@Override
	public List<TmoModeBroadcast> getModeBroadcast(Boolean onlyDescend) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeBroadcast(java.lang.Boolean, java.lang.Short[], java.lang.Integer[])
	 */
	@Override
	public List<TmoModeBroadcast> getModeBroadcast(Boolean onlyDescend, Short[] lines, Integer[] stations) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeBroadcast(java.lang.Boolean, long)
	 */
	@Override
	public List<TmoModeBroadcast> getModeBroadcast(Boolean isDay, long nodeId) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IModeService#getModeBroadcast(java.lang.Boolean, long, java.lang.Short[], java.lang.Integer[])
	 */
	@Override
	public List<TmoModeBroadcast> getModeBroadcast(Boolean isDay, long nodeId, Short[] lines, Integer[] stations) {
		return null;
	}

	/**
	* 获取车站信息
	* 
	* @param stationID
	* @return
	*/
	public MetroStation getMetroStationByStationId(int stationID) {
		String hql = "from MetroStation t where t.id.stationId = ?";
		List<MetroStation> metroStationList = null;
		try {
			metroStationList = commonDAO.getEntityListHQL(hql, stationID);
		} catch (OPException e) {
			logger.error("获取车站信息异常", e);
		}
		if (metroStationList != null && !metroStationList.isEmpty()) {
			MetroStation metroStation = metroStationList.get(0);
			return metroStation;
		} else {
			return null;
		}
	}

	/**
	* 获取车站名称
	* 
	* @param stationID
	* @return
	*/
	private String getStationName(Integer stationId) {
		String hql = "from MetroStation t where t.id.stationId = ?";
		List<MetroStation> metroStationList = null;
		try {
			metroStationList = commonDAO.getEntityListHQL(hql, stationId.intValue());
		} catch (OPException e) {
			logger.error("获取线路信息异常", e);
		}
		if (metroStationList != null && !metroStationList.isEmpty()) {
			MetroStation metroStation = metroStationList.get(0);
			return metroStation.getStationName();
		} else {
			return null;
		}
	}

	/**
	* 获取线路信息
	* 
	* @param lineID
	* @return
	*/
	private String getLineName(Long lineId) {
		String hql = "from MetroLine t where t.lineID = ?";
		List<MetroLine> metroLineList = null;
		try {
			metroLineList = commonDAO.getEntityListHQL(hql, lineId.shortValue());
		} catch (OPException e) {
			logger.error("获取线路信息异常", e);
		}
		if (metroLineList != null && !metroLineList.isEmpty()) {
			MetroLine metroLine = metroLineList.get(0);
			return metroLine.getLineName();
		} else {
			return null;
		}
	}

	/**
	 * @param lineID
	 * @return
	 */
	public MetroLine getDestMetroLine(Long lineId) {
		String hql = "from MetroLine t where t.lineID = ?";
		List<MetroLine> metroLineList = null;
		try {
			metroLineList = commonDAO.getEntityListHQL(hql, lineId.shortValue());
		} catch (OPException e) {
			logger.error("获取线路信息异常", e);
		}
		if (metroLineList != null && !metroLineList.isEmpty()) {
			MetroLine metroLine = metroLineList.get(0);
			return metroLine;
		} else {
			return null;
		}
	}
}
