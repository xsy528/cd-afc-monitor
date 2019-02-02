/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.TstNodeStocks;
import com.insigma.afc.monitor.model.entity.TstTvmBoxStocks;

/**
 * Ticket: 监视钱箱票箱服务
 * @author  mengyifan
 *
 */
public interface MonitorService {

	/**
	 * 获取TVM设备钱箱票箱信息
	 *
	 * @param deviceId
	 *            设备
	 * @return 票箱信息
	 */
	TstTvmBoxStocks getTVMTicketBoxStocks(long deviceId);

	/**
	 * 获取非TVM设备钱箱票箱信息
	 * 
	 * @param deviceId
	 * @return
	 */
	TstNodeStocks getTicketBoxStocks(long deviceId);

	/**
	 * 查看钱箱票箱
	 * @param id 设备id
	 * @return
	 */
	Result getBoxDetail(long id);

	/**
	 * 监视设备信息
	 * @param id 设备id
	 * @return
	 */
	Result getDeviceDetail(long id);

}
