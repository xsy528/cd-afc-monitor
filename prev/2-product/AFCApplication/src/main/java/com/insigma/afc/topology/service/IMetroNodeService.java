/* 
 * 日期：2010-4-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.topology.service;

import com.insigma.afc.topology.*;
import com.insigma.commons.application.IService;
import com.insigma.commons.op.OPException;

import java.util.List;

/**
 * 客流数据获取接口
 * 
 * @author lhz
 */
public interface IMetroNodeService extends IService {

	/**
	 * 获取所有线路参数信息
	 * 
	 * @return 线路列表
	 */
	List<MetroLine> getAllMetroLine();

	/**
	 * 获取线路参数信息
	 * 
	 * @param lineIDs
	 *            线路号数组，当参数为null时表示获取所有的线路信息
	 * @return 线路列表
	 */
	List<MetroLine> getMetroLine(Short[] lineIDs);

	/**
	 * 获取车站参数信息
	 * 
	 * @param lineIDs
	 *            线路号数组，当参数为null时表示忽略线路条件
	 * @param stationIDs
	 *            车站号数组，当参数为null时表示忽略车站条件
	 * @return 车站列表
	 */
	List<MetroStation> getMetroStation(Short[] lineIDs, Integer[] stationIDs);

	/**
	 * 获取线路所有车站
	 * @param lineId
	 * @return
	 */
	List<MetroStation> getMetroStation(Short lineId);

	/**
	 * 获取设备参数信息 *
	 * 
	 * @param stationIDs
	 *            车站号数组，当参数为null时表示忽略车站条件
	 * @param deviceIDs
	 *            设备号数组，当参数为null时表示忽略设备条件
	 * @return 设备列表
	 */
	List<MetroDevice> getMetroDevice(Integer[] stationIDs, Long[] deviceIDs);

	/**
	 * 获取车站设备
	 * @return 设备列表
	 */
	List<MetroDevice> getMetroDevice(Integer stationID);

	/**
	 * 获取设备模块参数信息
	 * 
	 * @param deviceIDs
	 *            设备号数组，当参数为null时表示忽略设备条件
	 * @param deviceModuleIDs
	 *            设备模块号数组，当参数为null时表示忽略设备模块条件
	 * @return 设备模块列表
	 */
	List<MetroDeviceModule> getMetroDeviceModule(Long[] deviceIDs, Long[] deviceModuleIDs);

	/**
	 * 获取ACC参数信息
	 * 
	 * @return 设备模块列表
	 */
	MetroACC getMetroACC();

	/**
	 * 保存节点
	 */
	void saveMetroNodes(MetroNode metroNode,Long oldNodeId);

	/**
	 * 删除节点
	 * @param nodeId 节点id
	 */
	void delete(long nodeId) throws OPException;

	/**
	 * 判断节点是否存在
	 * @param metroNode
	 * @return
	 */
	boolean exists(MetroNode metroNode);

	/**
	 * 保存资源对象
	 * @param currentNode
	 */
	void saveResource(MetroNode currentNode);

}
