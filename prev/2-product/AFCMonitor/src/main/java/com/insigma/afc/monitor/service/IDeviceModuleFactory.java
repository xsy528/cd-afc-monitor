package com.insigma.afc.monitor.service;

import com.insigma.afc.topology.model.entity.MetroDevice;
import com.insigma.afc.topology.model.entity.MetroDeviceModule;

import java.util.List;

public interface IDeviceModuleFactory {

	/**
	 * 获取设备模块信息
	 * @param equipment
	 * @return
	 */
	List<MetroDeviceModule> getMetroDeviceModuleList(MetroDevice equipment);

}
