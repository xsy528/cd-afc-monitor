package com.insigma.afc.monitor.service;


import com.insigma.afc.monitor.model.entity.topology.MetroDevice;
import com.insigma.afc.monitor.model.entity.topology.MetroDeviceModule;

import java.util.List;

public interface IDeviceModuleFactory {

	/**
	 * 获取设备模块信息
	 * @param equipment 设备
	 * @return 设备模块列表
	 */
	List<MetroDeviceModule> getMetroDeviceModuleList(MetroDevice equipment);

}
