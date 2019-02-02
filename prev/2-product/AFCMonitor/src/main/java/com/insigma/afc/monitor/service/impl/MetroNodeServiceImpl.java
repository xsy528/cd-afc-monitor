/* 
 * 日期：2010-6-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.NodeData;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.TsyResource;
import com.insigma.afc.monitor.service.IMetroNodeService;
import com.insigma.afc.monitor.service.ITsyResourceService;
import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.dao.MetroACCDao;
import com.insigma.afc.topology.dao.MetroDeviceDao;
import com.insigma.afc.topology.dao.MetroLineDao;
import com.insigma.afc.topology.dao.MetroStationDao;
import com.insigma.afc.topology.model.entity.*;
import com.insigma.afc.topology.util.NodeIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 节点管理服务实现类
 * 
 * @author xuzhemin
 */
@Service
public class MetroNodeServiceImpl implements IMetroNodeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetroNodeServiceImpl.class);

	private MetroACCDao metroACCDao;
	private MetroLineDao metroLineDao;
	private MetroStationDao metroStationDao;
	private MetroDeviceDao metroDeviceDao;

	private ITsyResourceService resourceService;

	private static final String NAMESPACE = "/monitor/map/";

	@Autowired
	public MetroNodeServiceImpl(MetroACCDao metroACCDao,MetroLineDao metroLineDao,MetroStationDao metroStationDao,
								MetroDeviceDao metroDeviceDao,ITsyResourceService resourceService){
		this.metroACCDao = metroACCDao;
		this.metroLineDao = metroLineDao;
		this.metroStationDao = metroStationDao;
		this.metroDeviceDao = metroDeviceDao;
		this.resourceService = resourceService;
	}

	@Override
	@Transactional
	public Result<MetroNode> create(NodeData nodeData, MultipartFile img) {
		AFCNodeLocation newImageLocation = new AFCNodeLocation(30, 30, 0);
		AFCNodeLocation newTextLocation = new AFCNodeLocation(30, 30, 0);
		switch (nodeData.getNodeType()){
			case ACC:{
				//ACC节点不允许创建
				break;
			}
			case LC:{
				NodeData.Node node = nodeData.getNode();
				short id = node.getId();
				if (metroLineDao.existsById(id)) {
					return Result.error(ErrorCode.NODE_EXISTS);
				}
				MetroLine metroLine = new MetroLine();
				metroLine.setLineID(id);
				metroLine.setLineName(node.getName());
				metroLine.setStatus(node.getStatus());
				metroLine.setIpAddress(node.getIp());
				metroLine.setImageLocation(newImageLocation);
				metroLine.setTextLocation(newTextLocation);
				//保存底图
				if (img==null){
					return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
				}
				try {
					metroLine.setPicName(saveResource(metroLine,img.getBytes()));
				} catch (IOException e) {
					return Result.error(ErrorCode.READ_PARAMETER_ERROR);
				}
				//保存线路节点
				return Result.success(metroLineDao.save(metroLine));
			}
			case SC:{
				NodeData.Node node = nodeData.getNode();
				short id = node.getId();
				Optional<MetroLine> metroLineOptional = metroLineDao.findById(node.getPid().shortValue());
				//检查线路是否存在
				if(!metroLineOptional.isPresent()){
					return Result.error(ErrorCode.NODE_EXISTS);
				}
				MetroLine metroLine = metroLineOptional.get();
				//创建车站节点
				MetroStation metroStation = new MetroStation();
				//计算车站id
				metroStation.setLineId(metroLine.getLineID());
				String hexStr = new BigInteger(String.valueOf(node.getPid()*100+id),16).toString();
				metroStation.setStationId(Integer.valueOf(hexStr));
				MetroStationId primaryKey = new MetroStationId(metroStation.getLineId(),metroStation.getStationId());
				//判断是否已经存在

				if (metroStationDao.existsById(primaryKey)) {
					return Result.error(ErrorCode.NODE_EXISTS);
				}
				metroStation.setImageLocation(newImageLocation);
				metroStation.setTextLocation(newTextLocation);
				metroStation.setStationName(node.getName());
				metroStation.setLineName(metroLine.getLineName());

				//保存底图
				if (img==null){
					return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
				}
				String resouceName = MetroStation.class.getSimpleName() + "_" + metroLine.id();
				TsyResource res = new TsyResource(resouceName, NAMESPACE, metroStation.name() + "的地图");
				try {
					res.setContents(img.getBytes());
				} catch (IOException e) {
					return Result.error(ErrorCode.READ_PARAMETER_ERROR);
				}
				resourceService.save(res);

				//保存车站节点
				String picName = NAMESPACE + "/" + resouceName;
				metroStation.setBackPicName(picName);
				metroStation.setPicName(picName);
				metroStation.setIpAddress(node.getIp());
				metroStation.setStatus(node.getStatus());
				return Result.success(metroStationDao.save(metroStation));
			}
			case SLE:{
				NodeData.Node node = nodeData.getNode();
				short id = node.getId();
				//所属车站
				Optional<MetroStation> metroStationOptional = metroStationDao.findByStationId(node.getPid().intValue());
				//车站不存在
				if (!metroStationOptional.isPresent()) {
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroStation metroStation = metroStationOptional.get();
				//所属线路
				MetroLine metroLine = metroLineDao.findById(metroStation.getLineId()).get();

				//计算设备id
				Integer stationId16 = Integer.valueOf(Integer.toHexString(metroStation.getStationId()));
				Integer deviceType16 = Integer.valueOf(Integer.toHexString(node.getDeviceType()));
				long deviceId = stationId16 * 10000 + deviceType16 * 100 + id;
				BigInteger srch1 = new BigInteger(String.valueOf(deviceId), 16);

				MetroDevice metroDevice = new MetroDevice();
				metroDevice.setLineId(metroLine.getLineID());
				metroDevice.setStationId(metroStation.getStationId());
				metroDevice.setDeviceId(Long.valueOf(srch1.toString()));

				MetroDeviceId primaryKey = new MetroDeviceId(metroDevice.getLineId(),metroDevice.getStationId(),
						metroDevice.getDeviceId());
				//设备是否存在
				if (metroDeviceDao.existsById(primaryKey)) {
					return Result.error(ErrorCode.NODE_EXISTS);
				}

				metroDevice.setImageLocation(newImageLocation);
				metroDevice.setTextLocation(newTextLocation);
				metroDevice.setStationName(metroStation.getStationName());
				metroDevice.setLineName(metroLine.getLineName());
				metroDevice.setDeviceName(node.getName());
				metroDevice.setIpAddress(node.getIp());
				metroDevice.setLogicAddress(node.getLogicAddr());
				metroDevice.setStatus(node.getStatus());
				metroDevice.setDeviceType(node.getDeviceType());
				return Result.success(metroDeviceDao.save(metroDevice));
			}
			default:{

			}
		}
		return Result.error(ErrorCode.INVALID_ARGUMENT);
	}

	private String saveResource(MetroNode metroNode,byte[] imgData){
		String resouceName = MetroStation.class.getSimpleName() + "_" + metroNode.id();
		TsyResource res = new TsyResource(resouceName, NAMESPACE, metroNode.name() + "的地图");
		res.setContents(imgData);
		resourceService.save(res);
		return NAMESPACE + "/" + resouceName;
	}

	@Override
	@Transactional
	public Result<MetroNode> update(NodeData nodeData, MultipartFile img) {
		NodeData.Node node = nodeData.getNode();
		AFCNodeLocation newImageLocation = new AFCNodeLocation(node.getIconX(), node.getIconY(), node.getIconAngle());
		AFCNodeLocation newTextLocation = new AFCNodeLocation(node.getTextX(), node.getTextY(), node.getTextAngle());
		switch (nodeData.getNodeType()){
			case ACC:{
				MetroACC metroACC = metroACCDao.getOne((short)0);
				metroACC.setAccName(node.getName());
				metroACC.setIpAddress(node.getIp());
				if (img != null) {
					try {
						metroACC.setPicName(saveResource(metroACC,img.getBytes()));
					} catch (IOException e) {
						return Result.error(ErrorCode.READ_PARAMETER_ERROR);
					}
				}
				metroACCDao.save(metroACC);
				break;
			}
			case LC:{
				Long oldNodeId = node.getNodeId();
				Short newLineId = node.getId();

				Optional<MetroLine> metroLineOptional = metroLineDao.findById(oldNodeId.shortValue());
				if (!metroLineOptional.isPresent()){
					//原线路不存在
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroLine metroLine = metroLineOptional.get();
				if (!metroLine.getLineID().equals(newLineId)&&metroLineDao.existsById(newLineId)){
					//新线路已经存在
					return Result.error(ErrorCode.NODE_EXISTS);
				}
				//id未变，名称改变
				if (metroLine.getLineID().equals(newLineId)&&!metroLine.getLineName().equals(node.getName())){
					//修改子节点的线路名称
					metroStationDao.updateLineNameByLineId(node.getName(),newLineId);
					metroDeviceDao.updateLineNameByLineId(node.getName(),newLineId);
				}
				//如果id改变。需要同时修改子节点
				if (!metroLine.getLineID().equals(newLineId)){
					//查找车站和设备节点
					List<MetroStation> stations = metroStationDao.findByLineId(oldNodeId.shortValue());
					List<MetroDevice> devices = metroDeviceDao.findByLineId(oldNodeId.shortValue());

					//车站设置新的id和线路名称
					for (MetroStation station:stations){
						station.setLineId(newLineId);
						station.setLineName(node.getName());
						station.setStationId(station.getStationId()+(newLineId-oldNodeId.intValue())*16*16);
					}
					metroStationDao.saveAll(stations);
					//设备设置新的id和线路名称
					for (MetroDevice device:devices){
						device.setLineId(newLineId);
						device.setLineName(node.getName());
						device.setStationId(device.getStationId()+(newLineId-oldNodeId.intValue())*16*16);
						device.setDeviceId(device.getDeviceId()+(newLineId-oldNodeId.intValue())*16*16*16*16*16*16);
					}
					metroDeviceDao.saveAll(devices);
					//删除旧线路节点
					metroLineDao.deleteById(oldNodeId.shortValue());
					//删除旧车站节点
					metroStationDao.deleteByLineId(oldNodeId.shortValue());
					//删除旧设备节点
					metroDeviceDao.deleteByLineId(oldNodeId.shortValue());

					metroLine.setLineID(newLineId);
				}

				//更新线路节点
				metroLine.setLineName(node.getName());
				metroLine.setStatus(node.getStatus());
				metroLine.setIpAddress(node.getIp());
				metroLine.setImageLocation(newImageLocation);
				metroLine.setTextLocation(newTextLocation);
				//底图需要更新
				if (img!=null){
					try {
						metroLine.setPicName(saveResource(metroLine,img.getBytes()));
					} catch (IOException e) {
						return Result.error(ErrorCode.READ_PARAMETER_ERROR);
					}
				}
				return Result.success(metroLineDao.save(metroLine));
			}
			case SC:{
				//车站编号
				short id = node.getId();
				Long oldNodeId = node.getNodeId();
				Optional<MetroStation> stationOptional = metroStationDao.findByStationId(node.getNodeId().intValue());
				//检查原车站是否存在
				if(!stationOptional.isPresent()){
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroStation metroStation = stationOptional.get();
				//计算车站id
				String hexStr = new BigInteger(String.valueOf(metroStation.getLineId()*100+id),16).toString();
				int newStationId = Integer.valueOf(hexStr);
				MetroStationId newPrimaryKey = new MetroStationId(metroStation.getLineId(),newStationId);
				//id未变，名称改变
				if (newStationId==metroStation.getStationId()&&!metroStation.getStationName().equals(node.getName())){
					//改变设备的线路名称
					metroDeviceDao.updateStationNameByStationId(node.getName(),metroStation.getStationId());
				}
				//id改变，需要修改设备节点
				if (newStationId!=metroStation.getStationId()){
					//判断是否已经存在
					if (metroStationDao.existsById(newPrimaryKey)) {
						return Result.error(ErrorCode.NODE_EXISTS);
					}
					//查找子设备节点
					List<MetroDevice> devices = metroDeviceDao.findByStationId(oldNodeId.intValue());
					for (MetroDevice device:devices){
						device.setStationId(newStationId);
						device.setStationName(node.getName());
						device.setDeviceId(device.getDeviceId()+(newStationId-oldNodeId.intValue())*16*16*16*16);
					}
					metroDeviceDao.saveAll(devices);
					//删除旧车站节点
					metroStationDao.deleteById(new MetroStationId(metroStation.getLineId(),metroStation.getStationId()));
					//删除旧设备节点
					metroDeviceDao.deleteByStationId(metroStation.getStationId());
					metroStation.setStationId(newStationId);
				}

				//更新车站节点
				metroStation.setImageLocation(newImageLocation);
				metroStation.setTextLocation(newTextLocation);
				metroStation.setStationName(node.getName());
				metroStation.setIpAddress(node.getIp());
				metroStation.setStatus(node.getStatus());
				//更新底图
				if (img!=null){
					try {
						String picName = saveResource(metroStation,img.getBytes());
						metroStation.setPicName(picName);
						metroStation.setBackPicName(picName);
					} catch (IOException e) {
						return Result.error(ErrorCode.READ_PARAMETER_ERROR);
					}
				}
				return Result.success(metroStationDao.save(metroStation));
			}
			case SLE:{
				//设备编号
				short id = node.getId();
				Optional<MetroDevice> deviceOptional = metroDeviceDao.findByDeviceId(node.getNodeId());
				//判断节点是否存在
				if (!deviceOptional.isPresent()){
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroDevice metroDevice = deviceOptional.get();
				//计算设备id
				Integer stationId16 = Integer.valueOf(Integer.toHexString(metroDevice.getStationId()));
				Integer deviceType16 = Integer.valueOf(Integer.toHexString(node.getDeviceType()));
				long deviceId = stationId16 * 10000 + deviceType16 * 100 + id;
				BigInteger srch1 = new BigInteger(String.valueOf(deviceId), 16);
				long newDeviceId = Long.valueOf(srch1.toString());
				metroDevice.setDeviceId(newDeviceId);
				MetroDeviceId primaryKey = new MetroDeviceId(metroDevice.getLineId(),metroDevice.getStationId(),
						metroDevice.getDeviceId());
				if (newDeviceId!=metroDevice.getDeviceId()) {
					//新设备id是否存在
					if (metroDeviceDao.existsById(primaryKey)) {
						return Result.error(ErrorCode.NODE_EXISTS);
					}
					//删除旧设备
					metroDeviceDao.deleteById(new MetroDeviceId(metroDevice.getLineId(),metroDevice.getStationId(),
							metroDevice.getDeviceId()));
				}
				//更新节点
				metroDevice.setImageLocation(newImageLocation);
				metroDevice.setTextLocation(newTextLocation);
				metroDevice.setDeviceName(node.getName());
				metroDevice.setIpAddress(node.getIp());
				metroDevice.setLogicAddress(node.getLogicAddr());
				metroDevice.setStatus(node.getStatus());
				metroDevice.setDeviceType(node.getDeviceType());
				return Result.success(metroDeviceDao.save(metroDevice));
			}
			default:{

			}
		}
		return Result.error(ErrorCode.INVALID_ARGUMENT);
	}

	@Override
	public Result query(Long nodeId) {
		AFCNodeLevel level = NodeIdUtils.getNodeLevel(nodeId);
		Map<String, Object> node = new HashMap<>();
		switch (level) {
			case ACC: {
				Optional<MetroACC> metroACCOptional = metroACCDao.findById(nodeId.shortValue());
				if (!metroACCOptional.isPresent()) {
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroACC metroACC = metroACCOptional.get();
				node.put("name", metroACC.getAccName());
				node.put("ip", metroACC.getIpAddress());
				node.put("img", metroACC.getPicName());
				break;
			}
			case LC: {
				Optional<MetroLine> metroLineOptional = metroLineDao.findById(nodeId.shortValue());
				if (!metroLineOptional.isPresent()) {
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroLine metroLine = metroLineOptional.get();
				node.put("name", metroLine.getLineName());
				node.put("ip", metroLine.getIpAddress());
				node.put("img", metroLine.getPicName());
				node.put("status", metroLine.getStatus());
				node.put("id", metroLine.getLineID());
				node.put("nodeId", metroLine.getLineID());
				break;
			}
			case SC: {
				Optional<MetroStation> metroStationOptional = metroStationDao.findByStationId(nodeId.intValue());
				if (!metroStationOptional.isPresent()) {
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroStation metroStation = metroStationOptional.get();
				node.put("name", metroStation.getStationName());
				node.put("ip", metroStation.getIpAddress());
				node.put("img", metroStation.getPicName());
				node.put("status", metroStation.getStatus());
				node.put("id", Integer.valueOf(Integer.toHexString(metroStation.getStationId())) % 100);
				node.put("lineId", metroStation.getLineId());
				node.put("lineName", metroStation.getLineName());
				node.put("nodeId", metroStation.getStationId());
				break;
			}
			case SLE: {
				Optional<MetroDevice> metroDeviceOptional = metroDeviceDao.findByDeviceId(nodeId);
				if (!metroDeviceOptional.isPresent()) {
					return Result.error(ErrorCode.NODE_NOT_EXISTS);
				}
				MetroDevice metroDevice = metroDeviceOptional.get();
				node.put("name", metroDevice.getDeviceName());
				node.put("ip", metroDevice.getIpAddress());
				node.put("deviceType", metroDevice.getDeviceType());
				node.put("logicAddr", metroDevice.getLogicAddress());
				node.put("status", metroDevice.getStatus());
				node.put("id", Long.valueOf(Long.toHexString(metroDevice.getDeviceId())) % 100);
				node.put("lineId", metroDevice.getLineId());
				node.put("lineName", metroDevice.getLineName());
				node.put("stationId", Integer.valueOf(Integer.toHexString(metroDevice.getStationId())));
				node.put("stationName", metroDevice.getStationName());
				node.put("nodeId", metroDevice.getDeviceId());
				break;
			}
			default:
		}
		return Result.success(node);
	}

	@Override
	public Result delete(Long nodeId) {
		return null;
	}
}
