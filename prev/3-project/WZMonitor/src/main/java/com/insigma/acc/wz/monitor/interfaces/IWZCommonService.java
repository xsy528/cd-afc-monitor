/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.interfaces;

import com.insigma.acc.monitor.wz.entity.TstNodeStocks;
import com.insigma.acc.monitor.wz.entity.TstTvmBoxStocks;
import com.insigma.commons.application.IService;

/**
 * Ticket: 
 * @author  mengyifan
 *
 */
public interface IWZCommonService extends IService {

	/***
	 * 简单保存或更新单个对象的信息
	 * 
	 * @param object
	 * @return
	 */
	void saveOrUpdateObject(Object object);

	/**
	 * 获取设备票箱信息(POST,GATE)
	 * 
	 * @param lineId
	 *            线路
	 * @param stationId
	 *            车站
	 * @param deviceId
	 *            设备
	 * @return 客流配置信息
	 */
	TstNodeStocks getTstNodeStocks(short lineId, int stationId, long deviceId);

	/**
	 * 获取TVM票箱信息
	 * 
	 * @param lineId
	 *            线路
	 * @param stationId
	 *            车站
	 * @param deviceId
	 *            设备
	 * @return 票箱信息
	 */
	TstTvmBoxStocks getTstTvmBoxStocks(short lineId, int stationId, long deviceId);

	void saveOrUpdateObjects(Object... objects);

	/**
	 * 删除票卡库存上报信息
	 * @param deviceId
	 */
	public void removeTicketStock(long deviceId);


}
