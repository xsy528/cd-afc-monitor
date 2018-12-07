/* 
 * 日期：2010-4-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.topology.service;

import java.util.List;

import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.IService;
import com.insigma.commons.ui.tree.TreeNode;

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
	 * 获取所有车站参数信息
	 * 
	 * @return 车站列表
	 */
	List<MetroStation> getAllMetroStation();

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
	 * 获取所有设备参数信息
	 * 
	 * @return 设备列表
	 */
	List<MetroDevice> getAllMetroDevice();

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
	 * 获取设备模块参数信息 *
	 * 
	 * @return 设备模块列表
	 */
	List<MetroDeviceModule> getAllMetroDeviceModule();

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
	 * 获取树信息
	 * 
	 * @param stationIDs
	 *            车站号数组，当参数为null时表示忽略车站条件
	 * @param deviceIDs
	 *            设备号数组，当参数为null时表示忽略设备条件
	 * @return 树列表
	 */
	List<TreeNode> getMetroTreeNode(Short[] lineIDs, Integer[] stationIDs);

	void saveMetroNodes(MetroNode currentNode, MetroNode beforeNode, List<MetroNode> childNodes,
			List<MetroNode> deleteNodes, boolean updateFlag);

	/**
	 * @param currentNode
	 * @param beforeNode
	 * @return 当更新或者添加未重复节点时为true
	 */
	boolean checkMetroNode(MetroNode currentNode, MetroNode beforeNode);

}
