/* 
 * 日期：2010-6-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.topology.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ResourceNameSpaceDefine;
import com.insigma.afc.entity.TsyResource;
import com.insigma.afc.service.ITsyResourceService;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import com.insigma.commons.ui.tree.TreeNode;
import com.insigma.commons.util.lang.StringUtil;

/**
 * 获取线路相关参数信息
 * 
 * @author lhz
 */
@SuppressWarnings("unchecked")
public class MetroNodeService extends Service implements IMetroNodeService {

	@Autowired(required = true)
	private ITsyResourceService resourceService;

	public List<MetroDevice> getAllMetroDevice() {
		return getMetroDevice(null, null);
	}

	public List<MetroDeviceModule> getAllMetroDeviceModule() {
		return getMetroDeviceModule(null, null);
	}

	public List<MetroLine> getAllMetroLine() {
		return getMetroLine(null);
	}

	public List<MetroStation> getAllMetroStation() {
		return getMetroStation(null, null);
	}

	public List<MetroDevice> getMetroDevice(Integer[] stationIDs, Long[] deviceIDs) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}
		String hql = "from MetroDevice t where 1=1 ";

		if (stationIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.stationId", StringUtil.array2StrOfCommaSplit(stationIDs));
		}

		if (deviceIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.deviceId", StringUtil.array2StrOfCommaSplit(deviceIDs));
		}

		hql = hql + " order by t.id.deviceId";
		try {
			return commonDAO.getEntityListHQL(hql);
		} catch (OPException e) {
			throw new ApplicationException("获取设备信息列表异常。", e);
		}
	}

	public List<MetroDeviceModule> getMetroDeviceModule(Long[] deviceIDs, Long[] deviceModuleIDs) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}
		String hql = "from MetroDeviceModule t where 1=1 ";

		if (deviceIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.deviceId", StringUtil.array2StrOfCommaSplit(deviceIDs));
		}

		if (deviceModuleIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.moduleId", StringUtil.array2StrOfCommaSplit(deviceModuleIDs));
		}

		try {
			return commonDAO.getEntityListHQL(hql);
		} catch (OPException e) {
			throw new ApplicationException("获取设备信息列表异常。", e);
		}
	}

	public List<MetroLine> getMetroLine(Short[] lineIDs) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}
		String hql = "";
		if (lineIDs == null) {
			hql = "from MetroLine t ";
		} else {
			hql = "from MetroLine t where 1=1 ";
			hql = hql + SqlRestrictions.in("t.lineID", StringUtil.array2StrOfCommaSplit(lineIDs));
		}

		hql = hql + "order by t.lineID";

		try {
			return commonDAO.getEntityListHQL(hql);
		} catch (OPException e) {
			throw new ApplicationException("获取线路信息列表异常。", e);
		} catch (Exception e) {
			throw new ApplicationException("获取线路信息列表异常。", e);
		}
	}

	public List<MetroStation> getMetroStation(Short[] lineIDs, Integer[] stationIDs) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}
		String hql = "from MetroStation t where 1=1 ";
		if (lineIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.lineId", StringUtil.array2StrOfCommaSplit(lineIDs));
		}
		if (stationIDs != null) {
			hql = hql + SqlRestrictions.in("t.id.stationId", StringUtil.array2StrOfCommaSplit(stationIDs));
		}
		// 将未启用的车站也加载到内存中-yang
		//		hql = hql + " and status = 0 ";
		hql = hql + " order by t.id.stationId";
		try {
			return commonDAO.getEntityListHQL(hql);
		} catch (OPException e) {
			throw new ApplicationException("获取车站信息列表异常。", e);
		}
	}

	public MetroACC getMetroACC() {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}
		try {
			List<MetroACC> list = commonDAO.getEntityListHQL("from MetroACC");
			if (list.isEmpty()) {
				return null;
			}
			return list.get(0);
		} catch (OPException e) {
			throw new ApplicationException("获取线路信息列表异常。", e);
		}
	}

	/**
	 * 获取树信息
	 * 
	 * @param lineIDs
	 *            线路号数组，不能为空
	 * @param stationIDs
	 *            车站号数组，当参数为null时表示忽略车站条件
	 * @return 树列表
	 */
	public List<TreeNode> getMetroTreeNode(Short[] lineIDs, Integer[] stationIDs) {
		List<TreeNode> nodeList = new ArrayList<TreeNode>();

		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return null;
		}

		List<MetroStation> stations = null;
		try {
			stations = getMetroStation(lineIDs, stationIDs);
		} catch (Exception e) {
			throw new ApplicationException("获取车站信息列表异常。", e);
		}
		if (lineIDs != null) {
			for (Short line : lineIDs) {
				List<MetroStation> list = getStationsByLineId(line, stations);
				if (!list.isEmpty()) {
					TreeNode node = new TreeNode();
					node.setText(list.get(0).getLineName());

					nodeList.add(node);

					for (MetroStation s : list) {
						TreeNode subnode = new TreeNode();
						subnode.setText(s.getStationName());
						subnode.setKey(s);
						node.addNode(subnode);
					}
				}

			}
		}

		return nodeList;
	}

	private List<MetroStation> getStationsByLineId(short lineId, List<MetroStation> stations) {
		List<MetroStation> list = new ArrayList<MetroStation>();
		for (MetroStation s : stations) {
			if (lineId == s.getId().getLineId()) {
				list.add(s);
			}
		}
		return list;
	}

	@Override
	public boolean checkMetroNode(MetroNode currentNode, MetroNode beforeNode) {
		int num = 0;
		String sql = null;
		try {
			if (currentNode.level() == AFCNodeLevel.LC) {
				MetroLine metroLine = (MetroLine) currentNode;
				MetroLine metroLineBefore = null;
				if (beforeNode != null) {
					metroLineBefore = (MetroLine) beforeNode;
				}
				//相等可更新，不等判断需保存的是否存在，阻止过度更新
				if (metroLineBefore.getLineID() != metroLine.getLineID()) {
					String hqlByLC = "from MetroLine t where t.lineID = ?";
					num = commonDAO.getEntityListHQL(hqlByLC, metroLine.getLineID()).size();
				}
			} else if (currentNode.level() == AFCNodeLevel.SC) {
				MetroStation metroStation = (MetroStation) currentNode;
				MetroStation metroStationBefore = null;
				if (beforeNode != null) {
					metroStationBefore = (MetroStation) beforeNode;
				}
				if (metroStationBefore.getId().getLineId().shortValue() != metroStation.getId().getLineId().shortValue()
						|| metroStationBefore.getId().getStationId().intValue() != metroStation.getId().getStationId()
								.intValue()) {
					String hqlBySC = "from MetroStation t where t.id.lineId = ? and t.id.stationId = ?";
					num = commonDAO.getEntityListHQL(hqlBySC, metroStation.getId().getLineId(),
							metroStation.getId().getStationId()).size();
					sql = "delete from TMETRO_STATION  where Station_Id=" + metroStationBefore.getId().getStationId();
				}
			} else if (currentNode.level() == AFCNodeLevel.SLE) {
				MetroDevice metroDevice = (MetroDevice) currentNode;
				MetroDevice metroDeviceBefore = null;
				if (beforeNode != null) {
					metroDeviceBefore = (MetroDevice) beforeNode;
				}
				if (metroDeviceBefore.getId().getLineId().shortValue() != metroDevice.getId().getLineId().shortValue()
						|| metroDeviceBefore.getId().getStationId().intValue() != metroDevice.getId().getStationId()
								.intValue()
						|| metroDeviceBefore.getId().getDeviceId().longValue() != metroDevice.getId().getDeviceId()
								.longValue()) {
					String hqlBySLE = " from MetroDevice t where t.id.lineId = ? and t.id.stationId = ? and t.id.deviceId = ?";
					num = commonDAO.getEntityListHQL(hqlBySLE, metroDevice.getId().getLineId(),
							metroDevice.getId().getStationId(), metroDevice.getId().getDeviceId()).size();
					sql = "delete from TMETRO_DEVICE  where Device_Id=" + metroDeviceBefore.getId().getDeviceId();
				}
			}
		} catch (OPException e) {
			throw new ApplicationException("查询地图信息异常。");
		}
		if (num > 0) {
			return false;
		} else {
			if (sql != null) {
				try {
					commonDAO.execSqlUpdate(sql);
				} catch (OPException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
	}

	@Override
	public void saveMetroNodes(MetroNode currentNode, MetroNode beforeNode, List<MetroNode> childNodes,
			List<MetroNode> deleteNodes, boolean updateFlag) {
		try {
			if (currentNode == null) {
				logger.warn("保存地图信息为空。");
			} else {
				saveResource(currentNode);
				AFCNodeLevel type = currentNode.level();
				switch (type) {

				// TODO
				case ACC:
					MetroACC acc = (MetroACC) currentNode;

					if (childNodes == null || childNodes.size() <= 0) {
						//不修改
						commonDAO.updateObj(acc);
					} else {
						//用在保存整个地图
						deleteChildMetroNode(type, currentNode);
						deleteResource(deleteNodes);
						for (MetroNode metroNode : childNodes) {
							if (metroNode instanceof MetroLine) {
								List<MetroStation> metroStationList = ((MetroLine) metroNode).getSubNodes();
								if (metroStationList.size() > 0) {
									commonDAO.saveObjs(metroStationList.toArray());
									for (MetroNode metroNode2 : metroStationList) {
										List<MetroDevice> metroDeviceList = ((MetroStation) metroNode2).getSubNodes();
										if (metroDeviceList.size() > 0) {
											commonDAO.saveObjs(metroDeviceList.toArray());
										}
									}
								}
							}
							if (metroNode instanceof MetroStation) {
								List<MetroDevice> metroDeviceList = ((MetroStation) metroNode).getSubNodes();
								if (metroDeviceList.size() > 0) {
									commonDAO.saveObjs(metroDeviceList.toArray());
								}
							}
						}

						for (MetroNode metroNode : childNodes) {
							saveResource(metroNode);
							if (metroNode instanceof MetroStation) {
								MetroStation m = (MetroStation) metroNode;
								m.setBackPicName(null);
							}
							commonDAO.saveObj(metroNode);
						}
						commonDAO.updateObj(currentNode);
					}

					//					if (updateFlag) {
					//						updateName(type, line.getLineName(), line.getLineID());
					//					}

					break;
				case LC:
					MetroLine line = (MetroLine) currentNode;

					if (childNodes == null || childNodes.size() <= 0) {
						//不修改LineId
						commonDAO.updateObj(line);
					} else {
						//用在保存整个地图
						deleteChildMetroNode(type, currentNode);
						deleteResource(deleteNodes);
						for (MetroNode metroNode : childNodes) {
							if (metroNode instanceof MetroStation) {
								List<MetroDevice> metroDeviceList = ((MetroStation) metroNode).getSubNodes();
								if (metroDeviceList.size() > 0) {
									commonDAO.saveObjs(metroDeviceList.toArray());
								}
							}
						}

						for (MetroNode metroNode : childNodes) {
							saveResource(metroNode);
							if (metroNode instanceof MetroStation) {
								MetroStation m = (MetroStation) metroNode;
								m.setBackPicName(null);
							}
							commonDAO.saveObj(metroNode);
						}
						commonDAO.updateObj(currentNode);
					}

					if (updateFlag) {
						updateName(type, line.getLineName(), line.getLineID());
					}
					break;
				case SC:
					MetroStation station = (MetroStation) currentNode;

					if ((childNodes == null || childNodes.size() <= 0) && beforeNode != null) {
						commonDAO.saveOrUpdateObj(currentNode);
					} else {
						//用在保存整个地图
						deleteChildMetroNode(type, currentNode);

						for (MetroNode metroNode : childNodes) {
							saveResource(metroNode);
						}

						commonDAO.saveObjs(childNodes.toArray());
						commonDAO.updateObj(currentNode);
					}

					if (updateFlag) {
						updateName(type, station.getStationName(), station.getId().getStationId());
					}
					break;
				case SLE:
					commonDAO.saveOrUpdateObj(currentNode);
					break;
				default:
					break;
				}
				resourceService.syncResouce();
			}
		} catch (OPException e) {
			throw new ApplicationException("保存地图信息异常。" + currentNode);
		}
	}

	private int deleteChildMetroNode(AFCNodeLevel type, MetroNode currentNode) {
		int resultNum = 0;
		int deleteAccNum = 0;
		int deleteLcNum = 0;
		int deleteScNum = 0;
		int deleteSleNum = 0;
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return resultNum;
		}

		String sqlAcc = "";
		String sqlLc = "";
		String sqlSc = "";
		String sqlSle = "";
		try {
			switch (type) {
			case ACC:
				// TODO ACC表与Line表的关系
				MetroACC metroACC = (MetroACC) currentNode;
				sqlAcc = "delete from TMETRO_LINE ";
				if (sqlAcc.length() > 0) {
					deleteAccNum = commonDAO.execSqlUpdate(sqlAcc);
				}
				sqlSc = "delete from TMETRO_STATION ";
				if (sqlSc.length() > 0) {
					deleteScNum = commonDAO.execSqlUpdate(sqlSc);
				}

				sqlSle = "delete from TMETRO_DEVICE ";
				if (sqlSle.length() > 0) {
					deleteSleNum = commonDAO.execSqlUpdate(sqlSle);
				}
				resultNum = deleteAccNum + deleteScNum + deleteSleNum;
				logger.debug("删除ACC中" + metroACC.getAccName() + "中所有节点，共" + resultNum + "个");
				break;

			case LC:
				MetroLine metroLine = (MetroLine) currentNode;
				sqlSc = "delete from TMETRO_STATION  where LINE_ID=" + metroLine.id();
				if (sqlSc.length() > 0) {
					deleteScNum = commonDAO.execSqlUpdate(sqlSc);
				}

				sqlSle = "delete from TMETRO_DEVICE  where LINE_ID=" + metroLine.id();
				if (sqlSle.length() > 0) {
					deleteSleNum = commonDAO.execSqlUpdate(sqlSle);
				}
				resultNum = deleteScNum + deleteSleNum;
				logger.debug("删除线路中" + metroLine.id() + "中所有节点，共" + resultNum + "个");
				break;
			case SC:
				MetroStation metroStation = (MetroStation) currentNode;
				sqlSle = "delete from TMETRO_DEVICE  where STATION_ID=" + metroStation.id();
				if (sqlSle.length() > 0) {
					deleteSleNum = commonDAO.execSqlUpdate(sqlSle);
				}
				logger.debug("删除车站" + metroStation.id() + "中所有节点，共" + deleteSleNum + "个");
				resultNum = deleteSleNum;
				break;
			default:
				break;
			}
			return resultNum;
		} catch (Exception e) {
			throw new ApplicationException("删除地图信息Item列表异常。", e);
		}
	}

	private void deleteResource(List<MetroNode> deleteNodes) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return;
		}

		try {
			String sql = "";

			for (MetroNode metroNode : deleteNodes) {
				sql = "delete from TSY_RESOURCE  where NAME_SPACE='" + ResourceNameSpaceDefine.MONITOR_EDITOR
						+ "' and NAME='" + StringUtils.substringAfter(metroNode.getPicName(), "//") + "'";
				commonDAO.execSqlUpdate(sql);
			}
		} catch (Exception e) {
			throw new ApplicationException("删除资源文件列表异常。", e);
		}
	}

	private void updateName(AFCNodeLevel type, String name, int id) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return;
		}

		switch (type) {
		case LC:
			String sql1 = "update TMETRO_STATION set LINE_NAME ='" + name + "' where LINE_ID = " + id;
			String sql2 = "update TMETRO_DEVICE set LINE_NAME ='" + name + "' where LINE_ID = " + id;
			//			String[] strLC = new String[] { sql1, sql2 };
			//			commonDAO.execBathUpdate(strLC);
			try {
				commonDAO.execSqlUpdate(sql1);
				commonDAO.execSqlUpdate(sql2);
			} catch (OPException e) {
				e.printStackTrace();
			}
			break;
		case SC:
			String sql3 = "update TMETRO_DEVICE set STATION_NAME ='" + name + "' where STATION_ID = " + id;
			//			String[] strSC = new String[] { sql3 };
			//			commonDAO.execBathUpdate(strSC);
			try {
				commonDAO.execSqlUpdate(sql3);
			} catch (OPException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @param currentNode
	 */
	private void saveResource(MetroNode currentNode) {
		if (currentNode.level() == AFCNodeLevel.SLE) {
			return;
		}
		String resouceName = currentNode.getClass().getSimpleName() + "_" + currentNode.id();
		String fileName = currentNode.getPicName();
		if (fileName == null) {
			return;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			return;
		}
		try {
			byte[] datas = FileUtils.readFileToByteArray(file);
			String ns = ResourceNameSpaceDefine.MONITOR_EDITOR;
			TsyResource res = new TsyResource(resouceName, ns, currentNode.name() + "的地图");
			res.setContents(datas);
			currentNode.setPicName(ns + "/" + resouceName);
			if (currentNode instanceof MetroStation) {
				((MetroStation) currentNode).setBackPicName(ns + "/" + resouceName);

			}
			resourceService.save(res);

		} catch (Exception e) {
			throw new ApplicationException("保存地图资源文件异常。" + currentNode, e);
		}
	}

	private int deleteMetroNodeByID(MetroNode currentNode) {
		if (commonDAO == null) {
			logger.warn("commonDAO为空，无法查询数据。请检查配置文件是否已经注入commonDAO");
			return 0;
		}
		String sql = "delete from TMETRO_STATION  where LINE_ID=" + currentNode.id();
		AFCNodeLevel type = currentNode.level();
		switch (type) {
		case SC:
			sql = "delete from TMETRO_DEVICE  where STATION_ID=" + currentNode.id();
			break;
		default:
			break;
		}
		try {
			return commonDAO.execSqlUpdate(sql);
		} catch (Exception e) {
			throw new ApplicationException("地图信息Item列表异常。", e);
		}
	}

	/**
	 * @return the resourceService
	 */
	public ITsyResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * @param resourceService the resourceService to set
	 */
	public void setResourceService(ITsyResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
