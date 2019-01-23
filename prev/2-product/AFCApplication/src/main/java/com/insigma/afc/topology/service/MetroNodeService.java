/* 
 * 日期：2010-6-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.topology.service;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ResourceNameSpaceDefine;
import com.insigma.afc.entity.TsyResource;
import com.insigma.afc.service.ITsyResourceService;
import com.insigma.afc.topology.*;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.exception.DAOException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取线路相关参数信息
 * 
 * @author lhz
 */
@SuppressWarnings("unchecked")
public class MetroNodeService extends Service implements IMetroNodeService {

	@Autowired(required = true)
	private ITsyResourceService resourceService;

	public List<MetroLine> getAllMetroLine() {
		return getMetroLine(null);
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

	@Override
	public List<MetroDevice> getMetroDevice(Integer stationID) {
		return getMetroDevice(new Integer[]{stationID},null);
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

	@Override
	public List<MetroStation> getMetroStation(Short lineId) {
		return getMetroStation(new Short[]{lineId},null);
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
	public boolean exists(MetroNode metroNode){
		int num = 0;
		AFCNodeLevel afcNodeLevel = metroNode.level();
		switch (afcNodeLevel){
			case LC:{
				MetroLine metroLine = (MetroLine)metroNode;
				String hqlByLC = "from MetroLine t where t.lineID = ?";
				try {
					num = commonDAO.getEntityListHQL(hqlByLC, metroLine.getLineID()).size();
				} catch (OPException e) {
					throw new DAOException("查询线路表失败",e);
				}
				break;
			}
			case SC:{
				MetroStation metroStation = (MetroStation) metroNode;
				String hqlBySC = "from MetroStation t where t.id.lineId = ? and t.id.stationId = ?";
				try {
					num = commonDAO.getEntityListHQL(hqlBySC, metroStation.getId().getLineId(),
							metroStation.getId().getStationId()).size();
				} catch (OPException e) {
					throw new DAOException("查询车站表失败",e);
				}
				break;
			}
			case SLE:{
				MetroDevice metroDevice = (MetroDevice) metroNode;
				String hqlBySLE = " from MetroDevice t where t.id.lineId = ? and t.id.stationId = ? and t.id.deviceId = ?";
				try {
					num = commonDAO.getEntityListHQL(hqlBySLE, metroDevice.getId().getLineId(),
							metroDevice.getId().getStationId(), metroDevice.getId().getDeviceId()).size();
				} catch (OPException e) {
					throw new DAOException("查询车站表失败",e);
				}
				break;
			}
		}
		if (num==0){
			return false;
		}
		return true;
	}

	@Override
	public void saveMetroNodes(MetroNode metroNode,Long oldNodeId) {
		AFCNodeLevel level = metroNode.level();
		if (oldNodeId!=null){
			//删除旧节点
			delete(level,oldNodeId);
		}
		switch (level){
			case ACC:{
				//保存ACC节点
				break;
			}
			case LC:{
				//保存资源
				saveResource(metroNode);
				//保存节点
				commonDAO.saveOrUpdateObj(metroNode);
				if (oldNodeId==null) {
					break;
				}
				//插入新节点
				MetroLine metroLine = (MetroLine)metroNode;
				short newLineId = metroLine.getLineID();
				//车站节点
				List<MetroStation> metroStations = ((MetroLine)metroNode).getSubNodes();
				if (metroStations==null||metroStations.isEmpty()) {
					break;
				}
				for (MetroStation metroStation:metroStations){
					//修改车站节点id
					//原id+(新lineId-旧lineId)*16^2
					int stationId = metroStation.getId().getStationId();
					MetroStationId metroStationId = metroStation.getId();
					metroStationId.setStationId(stationId+(newLineId-oldNodeId.shortValue())*16*16);
					metroStationId.setLineId(newLineId);
					metroStation.setLineName(metroLine.getLineName());
					commonDAO.saveOrUpdateObj(metroStation);
					//设备节点
					List<MetroDevice> metroDevices = metroStation.getSubNodes();
					if (metroDevices==null||metroDevices.isEmpty()){
						break;
					}
					for (MetroDevice metroDevice:metroDevices){
						long deviceId = metroDevice.getId().getDeviceId();
						MetroDeviceId metroDeviceId = metroDevice.getId();
						metroDeviceId.setLineId(newLineId);
						metroDeviceId.setStationId(metroStationId.getStationId());
						//原id+(新lineId-旧lineId)*16^6
						metroDeviceId.setDeviceId(deviceId+(newLineId-oldNodeId)*16*16*16*16*16*16);
						metroDevice.setLineName(metroLine.getLineName());
						commonDAO.saveOrUpdateObj(metroDevice);
					}
				}
				break;
			}
			case SC:{
				//保存资源
				saveResource(metroNode);
				//保存节点
				commonDAO.saveOrUpdateObj(metroNode);
				if (oldNodeId==null){
					break;
				}
				MetroStation metroStation = (MetroStation)metroNode;
				int newStationId = metroStation.getId().getStationId();
				//设备节点
				List<MetroDevice> metroDevices = metroStation.getSubNodes();
				if (metroDevices==null||metroDevices.isEmpty()){
					break;
				}
				for (MetroDevice metroDevice:metroDevices){
					MetroDeviceId metroDeviceId = metroDevice.getId();
					long deviceId = metroDeviceId.getDeviceId();
					metroDeviceId.setStationId(newStationId);
					//原id+(新lineId-旧lineId)*16^4
					metroDeviceId.setDeviceId(deviceId+(newStationId-oldNodeId)*16*16*16*16);
					metroDevice.setStationName(metroStation.getStationName());
					commonDAO.saveOrUpdateObj(metroDevice);
				}
				break;
			}
			case SLE:{
				//保存节点
				commonDAO.saveOrUpdateObj(metroNode);
			}
		}
	}

	@Override
	public void delete(long nodeId) {
		delete(AFCApplication.getNode(nodeId).level(),nodeId);
	}

	private int delete(AFCNodeLevel type, long nodeId) {
		int resultNum = 0;
		int deleteLcNum;
		int deleteScNum;
		int deleteSleNum;
		int one;
		try {
			switch (type) {
				case ACC:
					deleteLcNum = commonDAO.execSqlUpdate("delete from TMETRO_LINE ");
					deleteScNum = commonDAO.execSqlUpdate("delete from TMETRO_STATION ");
					deleteSleNum = commonDAO.execSqlUpdate("delete from TMETRO_DEVICE ");
					resultNum = deleteLcNum + deleteScNum + deleteSleNum;
					logger.debug("删除线网id=" +nodeId+ "中所有节点，共" + resultNum + "个");
					break;
				case LC:
					deleteScNum = commonDAO.execSqlUpdate("delete from TMETRO_STATION  where LINE_ID=?",nodeId);
					deleteSleNum = commonDAO.execSqlUpdate("delete from TMETRO_DEVICE  where LINE_ID=?",nodeId);
					one = commonDAO.execSqlUpdate("DELETE FROM TMETRO_LINE WHERE LINE_ID=?",nodeId);
					resultNum = deleteScNum + deleteSleNum + one;
					logger.debug("删除线路id=" + nodeId + "中所有节点，共" + resultNum + "个");
					break;
				case SC:
					deleteSleNum = commonDAO.execSqlUpdate("delete from TMETRO_DEVICE  where STATION_ID=?",nodeId);
					one = commonDAO.execSqlUpdate("DELETE FROM TMETRO_STATION WHERE STATION_ID=?",nodeId);
					resultNum = deleteSleNum + one;
					logger.debug("删除车站id=" + nodeId + "中所有节点，共" + resultNum + "个");
					break;
				case SLE:
					one = commonDAO.execSqlUpdate("DELETE FROM TMETRO_DEVICE WHERE DEVICE_ID=?",nodeId);
					logger.debug("删除设备id=" + nodeId + "中所有节点，共" + one + "个");
					break;
				default:
					break;
			}
			return resultNum;
		} catch (Exception e) {
			throw new ApplicationException("删除子节点异常。", e);
		}
	}

	/**
	 * @param currentNode
	 */
	@Override
	public void saveResource(MetroNode currentNode) {
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

	/**
	 * @param resourceService the resourceService to set
	 */
	public void setResourceService(ITsyResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
