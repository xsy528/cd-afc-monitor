package com.insigma.afc.monitor.service;

import java.util.List;

import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.commons.application.IService;

public interface IDeviceModuleFactory extends IService {

	List<MetroDeviceModule> getMetroDeviceModuleList(MetroDevice equipment);

}
