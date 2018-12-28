package com.insigma.acc.wz.interfaces;

import java.io.File;

import com.insigma.afc.monitor.map.DeviceInfoItem;
import com.insigma.commons.application.IService;

/**
 * 创建时间 2011-1-7 下午07:28:20 <br>
 * 描述: 车站地图操作Service<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public interface IStationMapOperateService extends IService {

	/**
	 * 获取数据库中的车站地图信息
	 */
	DeviceInfoItem getDeviceInfoItem(Integer[] stationIds);

	/**
	 * 根据线路ID获取车站地图信息
	 * 
	 * @param lineId
	 * @return
	 */
	//StationDeviceNodeConfig_t getDeviceInfoItem(short lineId);

	/**
	 * 车站地图文件生成
	 * 
	 * @return
	 */
	boolean createStationMapFile(DeviceInfoItem deviceInfoItem, String fileName);

	/**
	 * 解析车站地图信息文件并入库
	 */
	boolean parseStationMapFile2DB(File file);

}
