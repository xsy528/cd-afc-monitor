/* 
 * 日期：2010-11-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.service;

import java.util.Date;
import java.util.List;

import com.insigma.afc.constant.DatabaseType;
import com.insigma.afc.monitor.search.ar.DeviceQueryRegisterItem;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.application.IService;

/**
 * Ticket: 设备寄存器查询
 * 
 * @author zhengshuquan
 * @date 2010-10-11
 * @description
 */
public interface IDeviceRegisterService extends IService {

	public List<DeviceQueryRegisterItem> getDeviceRegisterViewList(Integer queryPeriod, Short[] lineId,
			Integer[] stationId, Long[] debiceIds, Integer deviceType, String deviceId, Date startTime, Date endTime,
			int pageNum, int pageSize, DatabaseType dbType);

	/**
	 * 获取设备寄存器查询结果集
	 * 
	 * @param queryPeriod
	 * @param lindId
	 * @param stationId
	 * @param deviceType
	 * @param deviceId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DeviceQueryRegisterItem> getDeviceRegisterViewListByOracle(Integer queryPeriod, Short[] lineId,
			Integer[] stationId, Long[] debiceIds, Integer deviceType, String deviceId, Date startTime, Date endTime,
			int pageNum, int pageSize);

	/**
	* 获取设备寄存器查询结果集
	* 
	* @param queryPeriod
	* @param lindId
	* @param stationId
	* @param deviceType
	* @param deviceId
	* @param startTime
	* @param endTime
	* @param page
	* @param pageSize
	* @return
	*/
	public List<DeviceQueryRegisterItem> getDeviceRegisterViewListByDB2(Integer queryPeriod, Short[] lineId,
			Integer[] stationId, Long[] debiceIds, Integer deviceType, String deviceId, Date startTime, Date endTime,
			int pageNum, int pageSize);

	/**
	 * 获取设备寄存器查询总记录数
	 * 
	 * @param queryPeriod
	 * @param lindId
	 * @param stationId
	 * @param deviceType
	 * @param deviceId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int getDeviceRegisterCount(Integer queryPeriod, Short[] lineId, Integer[] stationId, Long[] debiceIds,
			Integer deviceType, String deviceId, Date startTime, Date endTime);

	/**
	 * @param device
	 * @return
	 */
	public List<Object> getArEntity(MetroDevice device, String fileName);

}
