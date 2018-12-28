package com.insigma.acc.wz.admin;

import com.insigma.afc.config.AFCConfiguration;
import com.insigma.afc.initor.SystemInitor;
import com.insigma.afc.log.config.AFCNodeConfig;
import com.insigma.afc.log.config.ModuleConfig;
import com.insigma.afc.log.config.NodeConfig;
import com.insigma.afc.log.config.SystemNodeConfig;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.config.xml.XMLConfigurationProvider;

/**
 * 
 * Ticket: 数据管理初始化
 * @author  yangyang
 *
 */
public class WZAdminInitor extends SystemInitor {

	@Override
	public String getName() {
		return "数据管理初始化";
	}

	@Override
	public boolean init() {
		XMLConfigurationProvider configurationProvider = new XMLConfigurationProvider();
		configurationProvider.loadConfiguration("config/LogModuleConfig.xml", AFCNodeConfig.class,
				SystemNodeConfig.class, NodeConfig.class, ModuleConfig.class);
		AFCNodeConfig afcNodeConfig = (AFCNodeConfig) configurationProvider.getConfig(AFCNodeConfig.class);
		SystemNodeConfig systemNodeConfig = (SystemNodeConfig) afcNodeConfig.getConfigItem(SystemNodeConfig.class);
		Application.setData("systemNodeConfig", systemNodeConfig);

		AFCConfiguration afcConfig = Application.getConfig(AFCConfiguration.class);
		NetworkConfig netWorkConfig = (NetworkConfig) afcConfig.getConfigItem(NetworkConfig.class);
		long nodeId = netWorkConfig.getNodeNo();
		Application.setData("nodeId", nodeId);
		if (systemNodeConfig.getNodeConfig() != null) {
			for (NodeConfig nodeConfig : systemNodeConfig.getNodeConfig()) {
				if (nodeConfig.getModuleConfigList() != null) {
					for (ModuleConfig moduleConfig : nodeConfig.getModuleConfigList()) {
						Application.setData(moduleConfig.getModuleNumber(), moduleConfig.getModuleName());
					}
				}
			}
		}
		return true;
	}

}
