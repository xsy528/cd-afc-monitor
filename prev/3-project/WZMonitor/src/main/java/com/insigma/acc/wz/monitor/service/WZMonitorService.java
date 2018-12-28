/* 
 * 日期：2017年6月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.service;

import java.util.ArrayList;
import java.util.List;

import com.insigma.acc.monitor.wz.entity.TstNodeStocks;
import com.insigma.acc.monitor.wz.entity.TstTvmBoxStocks;
import com.insigma.commons.service.Service;

/**
 * Ticket: 
 * @author  mengyifan
 *
 */
public class WZMonitorService extends Service implements IWZMonitorService {

	/**
	 * 获取TVM钱箱票箱信息
	 * 
	 * @param lineId
	 *            线路
	 * @param stationId
	 *            车站
	 * @param deviceId
	 *            设备
	 * @return 票箱信息
	 */
	@Override
	public List<TstTvmBoxStocks> getTVMTicketBoxStocks(long deviceId) {
		List<TstTvmBoxStocks> ticketBoxList = new ArrayList();
		try {

			//			String sql = "select ticketboxid,amount,Row_Number() OVER (partition by ticketboxid ORDER BY operatetime desc,ticketboxid) rank from TFIN_TICKET_BOX_DETAIL where lineid = ? and stationid = ? and nodeid = ? ";
			String sql = "from TstTvmBoxStocks t where t.nodeId = ? ";
			ticketBoxList = commonDAO.getEntityListHQL(sql, deviceId);
			logger.info("共有" + ticketBoxList.size() + "条数据");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取钱箱票箱数据失败。", e);
		}

		return ticketBoxList;
	}

	/**
	 * 获取非TVM设备钱箱票箱信息
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public List<TstNodeStocks> getTicketBoxStocks(long deviceId) {
		//		logger.debug("条件 ****** : " + deviceId);
		List<TstNodeStocks> ticketBoxList = new ArrayList();
		try {

			String sql = "from TstNodeStocks t where t.nodeId = ? ";
			ticketBoxList = commonDAO.getEntityListHQL(sql, deviceId);
			logger.info("共有" + ticketBoxList.size() + "条数据");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取钱箱票箱数据失败。", e);
		}

		return ticketBoxList;
	}
}
