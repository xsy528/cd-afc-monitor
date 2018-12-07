package com.insigma.acc.wz.monitor.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.monitor.entity.TmoItemStatus;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 设备状态相关Service
 */
@SuppressWarnings("unchecked")
public class WZACCMetroNodeStatusService extends Service implements IMetroNodeStatusService {

	@Override
	public TmoItemStatus getTmoItemStatus(Boolean isDay, long nodeId) {

		StringBuffer sb = new StringBuffer();
		sb.append("from TmoItemStatus t where 1=1 ");

		sb.append("and t.nodeId=? ");
		//		long t = System.currentTimeMillis();
		//		sb.append(" and " + t + " = " + t + " ");

		if (isDay) {
			Calendar calendar = new GregorianCalendar();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			sb.append("and t.modeChangeTime > '"
					+ DateTimeUtil.formatDateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss") + "'");
		}

		List<TmoItemStatus> tmoItemStatusList = null;
		try {

			//			logger.debug("************ 3 HQL:" + sb.toString() + " nodeId:" + nodeId);
			tmoItemStatusList = commonDAO.retrieveEntityListHQL(sb.toString(), -1, 0, nodeId);
		} catch (Exception e) {
			logger.error("查询设备状态异常", e);
			throw new ApplicationException("查询设备状态异常。", e);
		}
		if (tmoItemStatusList != null && !tmoItemStatusList.isEmpty()) {
			return tmoItemStatusList.get(0);
		} else {
			return null;
		}

	}

	@Override
	public boolean saveOrUpdateOnlineStatus(long nodeId, boolean itemActivity) {

		try {
			// 将该节点在库表中的在线状态改为在线，如果失败，则给客户端返回注册失败的标记
			List<TmoItemStatus> list = commonDAO.getEntityListHQL("from TmoItemStatus t where  t.nodeId=?", nodeId);
			if (list != null && !list.isEmpty()) {

				TmoItemStatus tmoItemStatus = list.get(0);
				if (tmoItemStatus.getItemStatus() == null) {
					tmoItemStatus.setItemStatus((short) 0);
				}
				tmoItemStatus.setItemActivity(itemActivity);
				tmoItemStatus.setUpdateTime(new Date());

				commonDAO.updateObj(tmoItemStatus);
			} else {
				int itemType = AFCApplication.getNodeLevel(nodeId).getStatusCode();
				short lineId = AFCApplication.getLineId(nodeId);
				int stationId = AFCApplication.getStationId(nodeId);

				TmoItemStatus tmoItemStatus = new TmoItemStatus(lineId, stationId, nodeId, (short) itemType);
				tmoItemStatus.setCurrentModeCode((short) 0);
				tmoItemStatus.setLastModeCode((short) 0);
				tmoItemStatus.setItemActivity(itemActivity);
				tmoItemStatus.setItemStatus((short) 0);
				tmoItemStatus.setUpdateTime(new Date());
				tmoItemStatus.setModeChangeTime(new Date());
				commonDAO.saveObj(tmoItemStatus);

			}
			logger.debug("saveOrUpdateOnlineStatus-更新" + Long.toHexString(nodeId) + "状态为："
					+ (itemActivity == true ? "在线" : "离线"));
			return true;
		} catch (Exception e) {
			logger.error("saveOrUpdateOnlineStatus-更新" + Long.toHexString(nodeId) + "为"
					+ (itemActivity == true ? "在线" : "离线") + "失败", e);
		}

		return false;
	}

	@Override
	public List<EquStatusViewItem> getEquStatusView(EquStatusFilterForm filterForm) {
		try {
			if (filterForm == null) {
				throw new ApplicationException("查询当前事件列表条件为空。");
			}
			Date startTime = filterForm.getStartTime();
			Date endTime = filterForm.getEndTime();
			List<Short> statusLevel = filterForm.getStatusLevelList();
			Integer pageSize = filterForm.getPageSize();
			String orderField = "updateTime";
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

			List<EquStatusViewItem> viewItemList = new ArrayList<EquStatusViewItem>();

			//			String hql = "from TmoItemStatus t where t.nodeType > ?";
			//成都无法用设备类型区分SC、LC
			String hql = "from TmoItemStatus t where t.stationId <>0";
			String hqlOrderBy = "";

			if (orderField != null && orderType != null) {
				hqlOrderBy += " order by t." + orderField + " " + orderType;
			}

			List<TmoItemStatus> deviceStatus = new ArrayList<TmoItemStatus>();

			//			deviceStatus = this.commonDAO.retrieveEntityListHQL(hql + hqlOrderBy, 0, pageSize,
			//					AFCDeviceType.LC.shortValue());
			//成都无法用设备类型区分SC、LC
			deviceStatus = this.commonDAO.retrieveEntityListHQL(hql + hqlOrderBy, 0, pageSize);

			Map<Long, TmoItemStatus> deviceMaps = new HashMap<Long, TmoItemStatus>();
			Map<Long, TmoItemStatus> stationMaps = new HashMap<Long, TmoItemStatus>();
			for (TmoItemStatus tmoItemStatus : deviceStatus) {
				//				if (AFCApplication.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() > AFCNodeLevel.SC
				//						.getStatusCode()) {
				//					deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				//				} else if (AFCApplication.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() == AFCNodeLevel.SC
				//						.getStatusCode()) {
				//					stationMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				//				}

				if (AFCApplication.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() > AFCNodeLevel.SC
						.getStatusCode()) {
					deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				} else if (AFCApplication.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() == AFCNodeLevel.SC
						.getStatusCode()) {
					stationMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				}

				//				if (tmoItemStatus.getNodeId() / 100 % 100 == 0) {
				//					stationMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				//				} else if (AFCApplication.getNodeLevel(tmoItemStatus.getNodeId()).getStatusCode() > AFCNodeLevel.SC
				//						.getStatusCode()) {
				//					deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
				//				}
			}

			//获取设备事件信息
			//listTemps该变量已不使用
			List<Object[]> listTemps = getStatusLevel();
			AFCNodeLevel nodeType = AFCApplication.getAFCNodeType();
			if (nodeType.equals(AFCNodeLevel.ACC)) {
				List<MetroLine> subNodes = (List<MetroLine>) AFCApplication.getAFCNode().getSubNodes();
				for (MetroLine metroLine : subNodes) {
					List<MetroStation> subStationNodes = metroLine.getSubNodes();
					for (MetroStation metroStation : subStationNodes) {
						List<MetroDevice> metroDeviceList = metroStation.getSubNodes();
						TmoItemStatus tmoItemStatus2 = stationMaps
								.get(Long.valueOf(metroStation.getId().getStationId() << 16));
						boolean isOnLine = false;
						if (tmoItemStatus2 != null) {
							isOnLine = tmoItemStatus2.getItemActivity();

						}
						for (MetroDevice metroDevice : metroDeviceList) {
							EquStatusViewItem temp = new EquStatusViewItem();
							if (deviceMaps.containsKey(Long.valueOf(metroDevice.getId().getDeviceId()))) {
								TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getId().getDeviceId());
								temp = getEquStatusViewItem(tmoItemStatus, listTemps, filterForm, isOnLine);
								Date updateTime = tmoItemStatus.getUpdateTime();

								if (updateTime == null || (startTime != null && updateTime.before(startTime))
										|| (endTime != null && updateTime.after(endTime))) {
									continue;
								}
								temp.setNodeId(Long.valueOf(temp.getNodeId()));
							} else if (startTime == null && endTime == null) {
								boolean deviceBo = false;
								boolean statusBo = false;
								for (Long deviceId : deviceList) {
									if (deviceId.longValue() == metroDevice.getNodeId()) {
										deviceBo = true;
										break;
									}
								}

								for (Short status : statusLevel) {
									if (status.equals(DeviceStatus.OFF_LINE)) {
										statusBo = true;
										break;
									}
								}
								if ((deviceList.size() == 0 || deviceBo) && (statusLevel.size() == 0 || statusBo)) {
									temp.setNodeId(metroDevice.getNodeId());
									temp.setStatus(DeviceStatus.OFF_LINE);
									temp.setNormalEvent("0 个(0%)");
									temp.setWarnEvent("0 个(0%)");
									temp.setAlarmEvent("0 个(100%)");
								}

							} else {
								continue;
							}
							if (temp.getNodeId() != 0) {
								for (Long nodeid : deviceList) {
									if (temp.getNodeId() == nodeid) {
										viewItemList.add(temp);
										break;
									}
								}
							}
						}

					}
				}
			}
			if (nodeType.equals(AFCNodeLevel.LC)) {
				List<MetroStation> subNodes = (List<MetroStation>) AFCApplication.getAFCNode().getSubNodes();
				for (MetroStation metroStation : subNodes) {
					List<MetroDevice> metroDeviceList = metroStation.getSubNodes();
					TmoItemStatus tmoItemStatus2 = stationMaps
							.get(Long.valueOf(metroStation.getId().getStationId() << 16));
					boolean isOnLine = false;
					if (tmoItemStatus2 != null) {
						isOnLine = tmoItemStatus2.getItemActivity();

					}
					for (MetroDevice metroDevice : metroDeviceList) {
						EquStatusViewItem temp = new EquStatusViewItem();
						if (deviceMaps.containsKey(metroDevice.getId().getDeviceId())) {
							TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getId().getDeviceId());
							temp = getEquStatusViewItem(tmoItemStatus, listTemps, filterForm, isOnLine);
							Date updateTime = tmoItemStatus.getUpdateTime();

							if (updateTime == null || (startTime != null && updateTime.before(startTime))
									|| (endTime != null && updateTime.after(endTime))) {
								continue;
							}
						} else if (startTime == null && endTime == null) {
							boolean deviceBo = false;
							boolean statusBo = false;
							for (Long deviceId : deviceList) {
								if (deviceId.longValue() == metroDevice.getNodeId()) {
									deviceBo = true;
									break;
								}
							}

							for (Short status : statusLevel) {
								if (status.equals(DeviceStatus.OFF_LINE)) {
									statusBo = true;
									break;
								}
							}
							if ((deviceList.size() == 0 || deviceBo) && (statusLevel.size() == 0 || statusBo)) {
								temp.setNodeId(metroDevice.getNodeId());
								temp.setStatus(DeviceStatus.OFF_LINE);
								temp.setNormalEvent("0 个(0%)");
								temp.setWarnEvent("0 个(0%)");
								temp.setAlarmEvent("0 个(100%)");
							}

						} else {
							continue;
						}
						if (temp.getNodeId() != 0) {
							for (Long nodeid : deviceList) {
								if (temp.getNodeId() == nodeid) {
									viewItemList.add(temp);
									break;
								}
							}
						}
					}
				}

			} else if (nodeType.equals(AFCNodeLevel.SC)) {
				List<MetroDevice> metroDeviceList = (List<MetroDevice>) AFCApplication.getAFCNode().getSubNodes();
				TmoItemStatus tmoItemStatus2 = stationMaps.get(AFCApplication.getNodeId());
				boolean isOnLine = false;
				if (tmoItemStatus2 != null) {
					isOnLine = tmoItemStatus2.getItemActivity();

				}
				for (MetroDevice metroDevice : metroDeviceList) {
					EquStatusViewItem temp = new EquStatusViewItem();
					if (deviceMaps.containsKey(metroDevice.getNodeId())) {
						TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getNodeId());
						temp = getEquStatusViewItem(tmoItemStatus, listTemps, filterForm, isOnLine);
						Date updateTime = tmoItemStatus.getUpdateTime();

						if ((startTime != null && updateTime.before(startTime))
								|| (endTime != null && updateTime.after(endTime))) {
							continue;
						}
					} else if (startTime == null && endTime == null) {
						boolean deviceBo = false;
						boolean statusBo = false;
						for (Long deviceId : deviceList) {
							if (deviceId.longValue() == metroDevice.getNodeId()) {
								deviceBo = true;
								break;
							}
						}

						for (Short status : statusLevel) {
							if (status.equals(DeviceStatus.OFF_LINE)) {
								statusBo = true;
								break;
							}
						}
						if ((deviceList.size() == 0 || deviceBo) && (statusLevel.size() == 0 || statusBo)) {
							temp.setNodeId(metroDevice.getNodeId());
							temp.setStatus(DeviceStatus.OFF_LINE);
							temp.setNormalEvent("0 个(0%)");
							temp.setWarnEvent("0 个(0%)");
							temp.setAlarmEvent("0 个(100%)");
						}

					} else {
						continue;
					}
					if (temp.getNodeId() != 0) {
						for (Long nodeid : deviceList) {
							if (temp.getNodeId() == nodeid) {
								viewItemList.add(temp);
								break;
							}
						}
					}
				}
			}

			return viewItemList;
		} catch (OPException e) {
			e.printStackTrace();
		}
		return null;
	}

	private EquStatusViewItem getEquStatusViewItem(TmoItemStatus tmoItemStatus, List<Object[]> listTemps,
			EquStatusFilterForm filterForm, boolean fatherStatus) {
		EquStatusViewItem temp = new EquStatusViewItem();
		List<Short> statusLevel = filterForm.getStatusLevelList();
		long normal = 0;
		long warning = 0;
		long alarm = 0;
		int normalRate = 0;
		int warningRate = 0;
		int alarmRate = 0;

		Number onLine = (Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
		boolean hasStatus = true;
		boolean hasOff = true;

		if (statusLevel.size() > 0) {
			hasOff = false;
			for (Short staus : statusLevel) {
				if (staus.shortValue() == DeviceStatus.OFF_LINE) {
					hasOff = true;
					break;
				}
			}
		}

		if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity() && onLine != null
				&& onLine.intValue() == 0 && fatherStatus) {
			if (null != tmoItemStatus.getItemStatus()) {

				if (statusLevel.size() > 0) {
					hasStatus = false;
					for (Short staus : statusLevel) {
						if (tmoItemStatus.getItemStatus().shortValue() == staus.shortValue()) {
							hasStatus = true;
							break;
						}
					}
				}

				temp.setStatus(tmoItemStatus.getItemStatus());
			} else {
				hasStatus = false;
				temp.setStatus(DeviceStatus.OFF_LINE.intValue());

			}
		} else {
			hasStatus = false;
			temp.setStatus(DeviceStatus.OFF_LINE.intValue());

		}

		temp.setNodeId(tmoItemStatus.getNodeId());
		temp.setUpdateTime(tmoItemStatus.getUpdateTime());
		if (temp.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
			temp.setOnline(tmoItemStatus.getItemActivity());

		} else {
			temp.setOnline(false);
		}

		//		for (Object[] obj : listTemps) {
		//			if (temp.getNodeId() == (Long) obj[0]) {
		//				if ((Short) obj[1] == AFCEventLevel.WARNING.shortValue()) {
		//					warning = (Long) obj[2];
		//				} else if ((Short) obj[1] == AFCEventLevel.ALARM.shortValue()) {
		//					alarm = (Long) obj[2];
		//				} else {
		//					normal = (Long) obj[2];
		//				}
		//			}
		//		}
		long total = normal + warning + alarm;
		if (total <= 0) {
			// 防止分母为0
			total = 1;
		}
		normalRate = (int) (normal * 100.0 / total + 0.5);
		warningRate = (int) (warning * 100.0 / total + 0.5);
		alarmRate = 100 - normalRate - warningRate;

		temp.setNormalEvent(normal + " 个(" + normalRate + "%)");
		temp.setWarnEvent(warning + " 个(" + warningRate + "%)");
		temp.setAlarmEvent(alarm + " 个(" + alarmRate + "%)");

		if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
			List<MetroDevice> subNodes = (List<MetroDevice>) AFCApplication.getAFCNode().getSubNodes();
			for (MetroDevice metroDevice : subNodes) {
				if (metroDevice.getNodeId() == tmoItemStatus.getNodeId()) {
					metroDevice.putInfo(temp);
				}
			}
		} else if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.LC)) {
			MetroDevice metroDevice = (MetroDevice) AFCApplication.getNode(tmoItemStatus.getNodeId());
			metroDevice.putInfo(temp);
		} else if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.ACC)) {
			MetroDevice metroDevice = (MetroDevice) AFCApplication.getNode(tmoItemStatus.getNodeId());
			metroDevice.putInfo(temp);
		}

		if ((hasOff && temp.getStatus() == DeviceStatus.OFF_LINE.intValue()) || hasStatus) {
			return temp;
		}
		return new EquStatusViewItem();
	}

	//该方法返回值不使用
	private List<Object[]> getStatusLevel() {
		String hqlEqu = "select event.nodeId ,event.statusLevel, count(event.statusLevel) from TmoEquEventCur event"
				+ " group by event.nodeId ,event.statusLevel";
		List<Object[]> list = null;
		//		try {
		//			list = commonDAO.getEntityListHQL(hqlEqu);
		//		} catch (OPException e) {
		//			logger.error("事件统计异常", e);
		//			return null;
		//		}
		return list;
	}

	@Override
	public List<EquStatusViewItem> getEquStatusViewByStationId(int stationID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StationStatustViewItem> getStationStatusView(Short[] lineIDs, Integer[] stationIDs) {
		try {
			Number onLine = (Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);

			Integer alarmNum = (Integer) Application.getData(SystemConfigKey.ALARM_THRESHHOLD);
			if (alarmNum == null) {
				alarmNum = 0;
			}
			Integer warningNum = (Integer) Application.getData(SystemConfigKey.WARNING_THRESHHOLD);
			if (warningNum == null) {
				warningNum = 0;
			}

			Map<Integer, StationStatustViewItem> allResult = new HashMap<Integer, StationStatustViewItem>();
			List<StationStatustViewItem> result = new ArrayList<StationStatustViewItem>();

			// TODO 
			List<TmoItemStatus> stationStatus = this.commonDAO.getEntityListHQL(
					"from TmoItemStatus where nodeType=? and stationId <> 0 order by nodeId asc",
					Short.valueOf(AFCNodeLevel.SC.getStatusCode() + ""));
			List<TmoItemStatus> deviceStatus = this.commonDAO.getEntityListHQL(
					"from TmoItemStatus where nodeType>? order by nodeId asc",
					Short.valueOf(AFCNodeLevel.SC.getStatusCode() + ""));
			Map<Long, TmoItemStatus> stationMaps = new HashMap<Long, TmoItemStatus>();
			for (TmoItemStatus tmoItemStatus : stationStatus) {
				stationMaps.put(Long.valueOf(tmoItemStatus.getStationId()), tmoItemStatus);
			}
			Map<Long, TmoItemStatus> deviceMaps = new HashMap<Long, TmoItemStatus>();
			for (TmoItemStatus tmoItemStatus : deviceStatus) {
				deviceMaps.put(tmoItemStatus.getNodeId(), tmoItemStatus);
			}

			MetroNode afcNode = AFCApplication.getAFCNode();
			if (stationStatus.size() > 0) {
				if (afcNode.level().equals(AFCNodeLevel.ACC)) {
					MetroACC acc = (MetroACC) afcNode;
					List<MetroLine> subNodes = acc.getSubNodes();
					for (MetroLine metroLine : subNodes) {
						List<MetroStation> subStationNodes = metroLine.getSubNodes();
						for (MetroStation metroStation : subStationNodes) {
							int normalEvent = 0;
							int warnEvent = 0;
							int alarmEvent = 0;
							//							metroStation.getId().setStationId(Integer.valueOf(
							//									Integer.toHexString(metroStation.getId().getStationId())));
							Integer stationId = metroStation.getId().getStationId();
							Short lineId = metroStation.getId().getLineId();
							StationStatustViewItem viewItem = new StationStatustViewItem();
							viewItem.setLineId(lineId);
							viewItem.setStationId(stationId);
							viewItem.setNodeId(metroStation.getNodeId());
							viewItem.setNodeType(metroStation.getNodeType());
							if (stationMaps.containsKey(Long.valueOf(metroStation.getId().getStationId()))) {
								TmoItemStatus tmoItemStatus = stationMaps
										.get(Long.valueOf(metroStation.getId().getStationId()));
								viewItem.setMode(getcurrentmode(tmoItemStatus));
								viewItem.setUpdateTime(tmoItemStatus.getModeChangeTime());
								if (onLine != null && onLine.intValue() == 0) {
									try {
										viewItem.setOnline(tmoItemStatus.getItemActivity());
									} catch (Exception e) {
										logger.error("获取节点在离线状态异常", e);

									}
								} else {
									viewItem.setOnline(false);
								}
								if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity()
										&& onLine != null && onLine.intValue() == 0) {
									viewItem.setStatus(DeviceStatus.NORMAL.intValue());
								} else {
									viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
								}
							} else {
								//viewItem.setMode(0); 0代表正常模式
								viewItem.setMode(-1);
								viewItem.setUpdateTime(null);
								viewItem.setOnline(false);
								viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
							}
							for (MetroDevice metroDevice : metroStation.getSubNodes()) {
								EquStatusViewItem equViewItem = new EquStatusViewItem();
								equViewItem.setLineId(lineId);
								equViewItem.setStationId(stationId);
								equViewItem.setNodeId(metroDevice.getNodeId());
								if (deviceMaps.containsKey(metroDevice.getNodeId()) && onLine != null
										&& onLine.intValue() == 0) {
									TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getNodeId());
									equViewItem.setOnline(tmoItemStatus.getItemActivity());
									if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity()
											&& null != tmoItemStatus.getItemStatus()) {
										equViewItem.setStatus(tmoItemStatus.getItemStatus());
										//启用设备才纳入报警，警告的设备数的计算
										if (tmoItemStatus.getItemStatus().equals(DeviceStatus.NORMAL)
												&& metroDevice.getStatus() == 0) {
											++normalEvent;
										} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.WARNING)
												&& metroDevice.getStatus() == 0) {
											++warnEvent;
										} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.ALARM)
												&& metroDevice.getStatus() == 0) {
											++alarmEvent;
										} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.STOP_SERVICE)
												&& metroDevice.getStatus() == 0) {
											++alarmEvent;
										}
									} else if (metroDevice.getStatus() == 0) {
										++alarmEvent;
									}
								} else if (metroDevice.getStatus() == 0) {
									++alarmEvent;
								}
							}
							viewItem.setNormalEvent(normalEvent);
							viewItem.setWarnEvent(warnEvent);
							viewItem.setAlarmEvent(alarmEvent);
							//					viewItem.setAlarmEvent(metroStation.getSubNodes().size() - normalEvent - warnEvent);
							if (alarmEvent < alarmNum && alarmEvent >= warningNum
									&& viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
								viewItem.setStatus(DeviceStatus.WARNING.intValue());
							} else if (alarmEvent >= alarmNum
									&& viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
								viewItem.setStatus(DeviceStatus.ALARM.intValue());
							}

							//如果车站未启用,将车站的状态设置为未启用-yang
							if (metroStation.getStatus() != 0) {
								viewItem.setStatus(DeviceStatus.NO_USE.intValue());
							}
							metroStation.putInfo(viewItem);
							allResult.put(stationId, viewItem);
						}
						//根据传进来的ID筛选出需要显示的车站
						for (MetroStation metroStation : subStationNodes) {
							Integer stationId = metroStation.getId().getStationId();
							if (stationIDs != null) {
								for (Integer sid : stationIDs) {
									if (sid.equals(stationId)) {
										result.add(allResult.get(stationId));
									}
								}
							} else {
								result.add(allResult.get(stationId));
							}
						}

					}

				} else if (afcNode.level().equals(AFCNodeLevel.LC)) {
					MetroLine line = (MetroLine) afcNode;
					List<MetroStation> subNodes = line.getSubNodes();
					for (MetroStation metroStation : subNodes) {
						int normalEvent = 0;
						int warnEvent = 0;
						int alarmEvent = 0;

						Integer stationId = metroStation.getId().getStationId();
						Short lineId = metroStation.getId().getLineId();
						StationStatustViewItem viewItem = new StationStatustViewItem();
						viewItem.setLineId(lineId);
						viewItem.setStationId(stationId);
						viewItem.setNodeId(metroStation.getNodeId());
						viewItem.setNodeType(metroStation.getNodeType());
						if (stationMaps.containsKey(metroStation.getNodeId())) {
							TmoItemStatus tmoItemStatus = stationMaps.get(metroStation.getNodeId());
							viewItem.setMode(getcurrentmode(tmoItemStatus));
							viewItem.setUpdateTime(tmoItemStatus.getModeChangeTime());
							if (onLine != null && onLine.intValue() == 0) {
								try {
									viewItem.setOnline(tmoItemStatus.getItemActivity());
								} catch (Exception e) {
									logger.error("获取节点在离线状态异常", e);

								}
							} else {
								viewItem.setOnline(false);
							}
							if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity()
									&& onLine != null && onLine.intValue() == 0) {
								viewItem.setStatus(DeviceStatus.NORMAL.intValue());
							} else {
								viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
							}
						} else {
							//viewItem.setMode(0); 0代表正常模式
							viewItem.setMode(-1);
							viewItem.setUpdateTime(null);
							viewItem.setOnline(false);
							viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
						}
						for (MetroDevice metroDevice : metroStation.getSubNodes()) {
							EquStatusViewItem equViewItem = new EquStatusViewItem();
							equViewItem.setLineId(lineId);
							equViewItem.setStationId(stationId);
							equViewItem.setNodeId(metroDevice.getNodeId());
							if (deviceMaps.containsKey(metroDevice.getNodeId()) && onLine != null
									&& onLine.intValue() == 0) {
								TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getNodeId());
								equViewItem.setOnline(tmoItemStatus.getItemActivity());
								if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity()
										&& null != tmoItemStatus.getItemStatus()) {
									equViewItem.setStatus(tmoItemStatus.getItemStatus());
									//启用设备才纳入报警，警告的设备数的计算
									if (tmoItemStatus.getItemStatus().equals(DeviceStatus.NORMAL)
											&& metroDevice.getStatus() == 0) {
										++normalEvent;
									} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.WARNING)
											&& metroDevice.getStatus() == 0) {
										++warnEvent;
									} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.ALARM)
											&& metroDevice.getStatus() == 0) {
										++alarmEvent;
									} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.STOP_SERVICE)
											&& metroDevice.getStatus() == 0) {
										++alarmEvent;
									}
								} else if (metroDevice.getStatus() == 0) {
									++alarmEvent;
								}
							} else if (metroDevice.getStatus() == 0) {
								++alarmEvent;
							}
						}
						viewItem.setNormalEvent(normalEvent);
						viewItem.setWarnEvent(warnEvent);
						viewItem.setAlarmEvent(alarmEvent);
						//					viewItem.setAlarmEvent(metroStation.getSubNodes().size() - normalEvent - warnEvent);
						if (alarmEvent < alarmNum && alarmEvent >= warningNum
								&& viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
							viewItem.setStatus(DeviceStatus.WARNING.intValue());
						} else if (alarmEvent >= alarmNum && viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
							viewItem.setStatus(DeviceStatus.ALARM.intValue());
						}

						//如果车站未启用,将车站的状态设置为未启用-yang
						if (metroStation.getStatus() != 0) {
							viewItem.setStatus(DeviceStatus.NO_USE.intValue());
						}
						metroStation.putInfo(viewItem);
						allResult.put(stationId, viewItem);
					}
					//根据传进来的ID筛选出需要显示的车站
					for (MetroStation metroStation : subNodes) {
						Integer stationId = metroStation.getId().getStationId();
						if (stationIDs != null) {
							for (Integer sid : stationIDs) {
								if (sid.equals(stationId)) {
									result.add(allResult.get(stationId));
								}
							}
						} else {
							result.add(allResult.get(stationId));
						}
					}
				} else if (afcNode.level().equals(AFCNodeLevel.SC)) {

					MetroStation station = (MetroStation) afcNode;
					int normalEvent = 0;
					int warnEvent = 0;
					int alarmEvent = 0;

					Integer stationId = station.getStationId();
					Short lineId = station.getLineId();
					StationStatustViewItem viewItem = new StationStatustViewItem();
					viewItem.setLineId(lineId);
					viewItem.setStationId(stationId);
					viewItem.setNodeId(station.getNodeId());
					viewItem.setNodeType(station.getNodeType());
					if (stationMaps.containsKey(station.getNodeId())) {
						TmoItemStatus tmoItemStatus = stationMaps.get(station.getNodeId());
						viewItem.setMode(getcurrentmode(tmoItemStatus));
						viewItem.setUpdateTime(tmoItemStatus.getModeChangeTime());
						if (onLine != null && onLine.intValue() == 0) {
							viewItem.setOnline(tmoItemStatus.getItemActivity());
						} else {
							viewItem.setOnline(false);
						}
						if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity() && onLine != null
								&& onLine.intValue() == 0) {
							viewItem.setStatus(DeviceStatus.NORMAL.intValue());
						} else {
							viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
						}
					} else {
						viewItem.setMode(-1);
						viewItem.setUpdateTime(null);
						viewItem.setOnline(false);
						viewItem.setStatus(DeviceStatus.OFF_LINE.intValue());
					}
					for (MetroDevice metroDevice : station.getSubNodes()) {
						EquStatusViewItem equViewItem = new EquStatusViewItem();
						equViewItem.setLineId(lineId);
						equViewItem.setStationId(stationId);
						equViewItem.setNodeId(metroDevice.getNodeId());
						if (deviceMaps.containsKey(metroDevice.getNodeId()) && onLine != null
								&& onLine.intValue() == 0) {
							TmoItemStatus tmoItemStatus = deviceMaps.get(metroDevice.getNodeId());
							equViewItem.setOnline(tmoItemStatus.getItemActivity());
							//启用设备才纳入报警，警告的设备数的计算
							if (null != tmoItemStatus.getItemActivity() && tmoItemStatus.getItemActivity()
									&& null != tmoItemStatus.getItemStatus()) {
								equViewItem.setStatus(tmoItemStatus.getItemStatus());
								if (tmoItemStatus.getItemStatus().equals(DeviceStatus.NORMAL)
										&& metroDevice.getStatus() == 0) {
									++normalEvent;
								} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.WARNING)
										&& metroDevice.getStatus() == 0) {
									++warnEvent;
								} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.ALARM)
										&& metroDevice.getStatus() == 0) {
									++alarmEvent;
								} else if (tmoItemStatus.getItemStatus().equals(DeviceStatus.STOP_SERVICE)
										&& metroDevice.getStatus() == 0) {
									++alarmEvent;
								}
							} else if (metroDevice.getStatus() == 0) {
								++alarmEvent;
							}
						} else if (metroDevice.getStatus() == 0) {
							++alarmEvent;
						}
					}
					viewItem.setNormalEvent(normalEvent);
					viewItem.setWarnEvent(warnEvent);
					//int alarmNew = station.getSubNodes().size() - normalEvent - warnEvent;
					//viewItem.setAlarmEvent(alarmNew);
					viewItem.setAlarmEvent(alarmEvent);
					if (alarmEvent < alarmNum && alarmEvent >= warningNum
							&& viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
						viewItem.setStatus(DeviceStatus.WARNING.intValue());
					} else if (alarmEvent >= alarmNum && viewItem.getStatus() != DeviceStatus.OFF_LINE.intValue()) {
						viewItem.setStatus(DeviceStatus.ALARM.intValue());
					}
					station.putInfo(viewItem);
					allResult.put(stationId, viewItem);
					result.add(allResult.get(stationId));
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//	@Override
	//	public void saveDeviceStatus(final List<TmoEquEventCur> events, final long nodeId, final Short itemStatus) {
	//
	//		saveOrUpdateModuleEvent(events, nodeId);
	//		saveOrUpdateItemStatus(itemStatus, nodeId);
	//
	//	}

	/**
	 * 保存或更新状态表TmoItemStatus
	 * 
	 * @param itemStatus
	 * @param nodeId
	 *            4个字节
	 * @return
	 */
	@Override
	public void saveItemStatus(long nodeId, short itemStatus) {
		// 0：正常服务 1：警告 2.报警 4：离线  ff：停止服务 || 5：SC收到SLE 2001报文 告知LC该设备在线 6:SC收到SLE 2001报文 告知LC该设备离线 ，状态5和6不保存在数据库
		short updateStatus = itemStatus;
		//		boolean itemActivity = false;

		//		// 254在线，255离线
		//		if (updateStatus != (short) 255) {
		//			itemActivity = true;
		//		}
		//		//更新NodeStatusMap
		//		PingListener.setNodeStatusMap(nodeId, itemActivity);
		//
		//		if (updateStatus == 254 || updateStatus == 255) {
		//			return;
		//		}

		if (commonDAO == null) {
			logger.error("ICommonDAO为空，无法更新TmoItemStatus状态");
			return;
		}

		try {
			// 将该节点在库表中的在线状态改为在线，如果失败，则给客户端返回注册失败的标记
			List<TmoItemStatus> list = commonDAO.getEntityListHQL("from TmoItemStatus t where t.nodeId=? ", nodeId);
			// update
			if (list != null && !list.isEmpty()) {

				TmoItemStatus tmoItemStatus = list.get(0);
				if (tmoItemStatus.getItemStatus() != null && updateStatus == tmoItemStatus.getItemStatus()) {
					logger.debug("数据库状态：" + tmoItemStatus.getItemStatus() + " 需要更新状态为：" + updateStatus + " 不需要更新。");
					return;
				}
				logger.debug("节点：" + String.format(Application.getDeviceIdFormat(), nodeId) + " 更新状态" + " itemStatus="
						+ updateStatus);

				commonDAO.execSqlUpdate("update TMO_ITEM_STATUS t set t.UPDATE_TIME = ? , t.ITEM_STATUS = ?"
						+ " where t.DEVICE_ID = ? ", new Date(), updateStatus, nodeId);

				logger.debug("更新节点状态表TmoItemStatus成功，设备状态为：" + itemStatus);
			} else {
				// save
				short nodeType = AFCApplication.getNodeType(nodeId);
				short lineId = AFCApplication.getLineId(nodeId);
				int stationId = AFCApplication.getStationId(nodeId);

				TmoItemStatus tmoItemStatus = new TmoItemStatus(lineId, stationId, nodeId, nodeType);

				tmoItemStatus.setUpdateTime(new Date());
				tmoItemStatus.setItemStatus(updateStatus);
				commonDAO.saveObj(tmoItemStatus);
				logger.debug(String.format(
						"########保存节点[" + Application.getDeviceIdFormat() + "]状态 itemStatus=" + updateStatus, nodeId));
			}
		} catch (Exception e) {
			logger.error("更新节点状态表TmoItemStatus失败", e);
		}

	}

	/**
	 * 更新设备运行模式TmoItemStatus
	 * 
	 * @param modeCode
	 * @param nodeId
	 *            4个字节
	 * @return
	 */
	@Override
	public void saveModeCode(long nodeId, String modeCode) {
		if (commonDAO == null) {
			logger.error("ICommonDAO为空，无法更新TmoItemStatus状态");
			return;
		}

		try {
			String sql = "select count(*) from TMO_ITEM_STATUS where DEVICE_ID = ?";
			List<Object> list = commonDAO.execSqlQuery(sql.toString(), nodeId);
			// update
			if (list != null && !list.isEmpty() && Integer.valueOf(list.get(0).toString()) > 0) {
				commonDAO.execSqlUpdate(
						"update TMO_ITEM_STATUS t set t.UPDATE_TIME = ?, t.CURRENT_MODE_CODE=? where t.DEVICE_ID = ?",
						new Date(), modeCode, nodeId);
				logger.debug("更新设备运行模式TmoItemStatus成功");
			}
		} catch (Exception e) {
			logger.error("更新设备运行模式TmoItemStatus失败", e);
		}

	}

	/**
	 * 更新状态和运营模式
	 * @param nodeId
	 * @param status
	 * @param modeStr
	 */
	@Override
	public void UpdateItemStatus(long nodeId, Short status, String modeCode) {

		if (commonDAO == null) {
			logger.error("ICommonDAO为空，无法更新TmoItemStatus状态");
			return;
		}

		try {
			String sql = "select count(*) from TMO_ITEM_STATUS where DEVICE_ID = ?";
			List<Object> list = commonDAO.execSqlQuery(sql.toString(), nodeId);
			// update
			if (list != null && !list.isEmpty() && Integer.valueOf(list.get(0).toString()) > 0) {
				List<Object> args = new ArrayList<Object>();
				String updateSql = "update TMO_ITEM_STATUS t set t.UPDATE_TIME = ?";
				args.add(new Date());
				if (status != null) {
					updateSql += ",t.ITEM_STATUS = ?";
					args.add(status);
				}
				if (modeCode != null) {
					updateSql += ", t.CURRENT_MODE_CODE=?";
					args.add(modeCode);
				}
				updateSql += " where t.DEVICE_ID = ?";
				args.add(nodeId);
				commonDAO.execSqlUpdate(updateSql.toString(), args.toArray());
				logger.debug("更新设备运行模式TmoItemStatus成功");
			}
		} catch (Exception e) {
			logger.error("更新设备运行模式TmoItemStatus失败", e);
		}

	}

	@Override
	public void saveBatchItemStatus(List<MetroNode> nodeList, boolean activeItem) {
		try {
			TmoItemStatus[] statuses = new TmoItemStatus[nodeList.size()];
			int i = 0;
			for (MetroNode node : nodeList) {
				TmoItemStatus tmoItemStatus = getTmoItemStatus(false, node.getNodeId());
				if (tmoItemStatus != null) {
					continue;
				}
				TmoItemStatus status = new TmoItemStatus();
				status.setNodeId(node.getNodeId());
				status.setLineId(AFCApplication.getLineId(node.getNodeId()));
				status.setStationId(AFCApplication.getStationId(node.getNodeId()));
				status.setNodeType(AFCApplication.getNodeType(node.getNodeId()));
				status.setItemActivity(activeItem);
				status.setUpdateTime(new Date());
				statuses[i++] = status;
			}
			//保存下层节点的状态
			this.commonDAO.saveAndUpdateObjs(statuses);
			//更新所有其余节点的状态
			updateAllItemsOnlineStatus(activeItem);
			logger.error("saveBatchItemStatus-更新节点状态为：" + (activeItem == true ? "在线" : "离线"));

		} catch (OPException e) {
			logger.error("saveBatchItemStatus-更新节点的" + (activeItem == true ? "在线" : "离线") + "状态失败", e);
		}
	}

	@Override
	public boolean updateAllItemsOnlineStatus(boolean itemActiveStatus) {
		try {
			commonDAO.execSqlUpdate("update TMO_ITEM_STATUS t set t.UPDATE_TIME = ?,t.ITEM_ACTIVITY = ? ", new Date(),
					itemActiveStatus);
			logger.info("updateAllItemsOnlineStatus-更新所有节点的状态为：" + (itemActiveStatus == true ? "在线" : "离线"));
		} catch (OPException e) {
			logger.error("updateAllItemsOnlineStatus-更新所有节点的" + (itemActiveStatus == true ? "在线" : "离线") + "状态失败", e);
		}
		return true;
	}

	// updateStatus 5：SC收到SLE 2001报文 告知LC该设备在线
	//	private void saveOrUpdateItemStatus(long nodeId) {
	//
	//		boolean itemActivity = true;
	//
	//		if (commonDAO == null) {
	//			logger.error("ICommonDAO为空，无法更新TmoItemStatus状态");
	//			return;
	//		}
	//		try {
	//			List<TmoItemStatus> list = commonDAO.getEntityListHQL("from TmoItemStatus t where t.nodeId=? ", nodeId);
	//
	//			if (list != null && !list.isEmpty()) {
	//
	//				TmoItemStatus tmoItemStatus = list.get(0);
	//				if (itemActivity == tmoItemStatus.getItemActivity()) {
	//					logger.debug("节点状态：" + tmoItemStatus.getItemActivity() + " 不需要更新");
	//					return;
	//				}
	//				logger.debug("节点：" + String.format(Application.getDeviceIdFormat(), nodeId) + " 更新状态："
	//						+ " itemActivity = " + itemActivity);
	//
	//				commonDAO.execSqlUpdate(
	//						"update TMO_ITEM_STATUS t set t.UPDATE_TIME=?, t.ITEM_ACTIVITY=? " + " where t.DEVICE_ID=? ",
	//						new Date(), itemActivity, nodeId);
	//				logger.debug("更新节点状态表TmoItemStatus成功");
	//			} else {
	//				// save
	//				short nodeType = AFCApplication.getNodeType(nodeId);
	//				short lineId = AFCApplication.getLineId(nodeId);
	//				int stationId = AFCApplication.getStationId(nodeId);
	//
	//				TmoItemStatus tmoItemStatus = new TmoItemStatus(lineId, stationId, nodeId, nodeType);
	//
	//				tmoItemStatus.setUpdateTime(new Date());
	//				tmoItemStatus.setItemActivity(itemActivity);
	//				tmoItemStatus.setUpdateTime(new Date());
	//				commonDAO.saveObj(tmoItemStatus);
	//				logger.debug("保存状态itemActivity=" + itemActivity);
	//			}
	//		} catch (OPException e) {
	//		}
	//	}

	//取车站当前处于的模式编号
	private long getcurrentmode(TmoItemStatus tmoItemStatus) {

		Short currentModeCode = tmoItemStatus.getCurrentModeCode();

		if (currentModeCode == null) {
			currentModeCode = 0;
		}

		return Long.valueOf(currentModeCode);
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IMetroNodeStatusService#getTmoItemStatus(java.lang.Short, java.lang.Integer, java.lang.Long)
	 */
	@Override
	public TmoItemStatus getTmoItemStatus(Short lineId, Integer stationId, Long deviceId) {
		AFCNodeLevel nodeType = AFCApplication.getNodeLevel(deviceId);

		if (nodeType == null) {
			return null;
		}

		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from TmoItemStatus t where 1=1 ");
		if (lineId != null) {
			sb.append("and t.lineId=? ");
			args.add(lineId);
		}
		if (stationId != null) {
			sb.append("and t.stationId=? ");
			args.add(stationId);
		}

		if (deviceId != null) {
			sb.append("and t.nodeId=? ");
			args.add(deviceId);
		}

		if (nodeType != null) {
			sb.append("and t.nodeType=? ");
			args.add((short) nodeType.getStatusCode());
		}

		sb.append("order by t.itemActivity DESC"); // 按节点在线状态降序排列，如果同时有在线和离线状态记录，则优先显示在线状态。

		List<TmoItemStatus> tmoItemStatusList = null;
		try {
			tmoItemStatusList = commonDAO.getEntityListHQL(sb.toString(), args.toArray());
		} catch (OPException e) {
			logger.error("查询模式上传信息列表异常", e);
			throw new ApplicationException("查询模式上传信息列表异常", e);
		}
		if (tmoItemStatusList != null && !tmoItemStatusList.isEmpty()) {
			logger.error("******成功获取模式上传信息列表数据");
			return tmoItemStatusList.get(0);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.monitor.service.IMetroNodeStatusService#saveOrUpdateOnlineStatus(short, int, long, boolean)
	 */
	@Override
	public boolean saveOrUpdateOnlineStatus(short lineId, int stationId, long nodeId, boolean status) {
		return false;
	}
}
