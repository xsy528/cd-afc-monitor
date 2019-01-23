/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.service;

import java.util.List;

import com.insigma.afc.monitor.model.entity.TstNodeStocks;
import com.insigma.afc.monitor.model.entity.TstTvmBoxStocks;
import com.insigma.commons.application.IService;

/**
 * Ticket: 
 * @author  mengyifan
 *
 */
public interface IWZMonitorService extends IService {

	/**
	 * 获取TVM设备钱箱票箱信息
	 * 
	 * @param lineId
	 *            线路
	 * @param stationId
	 *            车站
	 * @param deviceId
	 *            设备
	 * @return 票箱信息
	 */
	public List<TstTvmBoxStocks> getTVMTicketBoxStocks(long deviceId);

	/**
	 * 获取非TVM设备钱箱票箱信息
	 * 
	 * @param deviceId
	 * @return
	 */
	public List<TstNodeStocks> getTicketBoxStocks(long deviceId);

}
