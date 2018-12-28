package com.insigma.afc.monitor.service;

import com.insigma.commons.application.IService;

public interface IVersionInfoService extends IService {

	/**
	 * 取得EOD参数信息
	 * 
	 * @param lineId
	 * @param stationId
	 * @param deviceId
	 * @return
	 */
	public Object[] getTapEodStationVersionInquirys(short lineId, int stationId, long deviceId);
}
