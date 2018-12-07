package com.insigma.afc.log.config;

import java.util.List;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SystemNodes")
public class SystemNodeConfig extends ConfigurationItem {

	private List<NodeConfig> nodeConfig;

	public SystemNodeConfig() {

	}

	public SystemNodeConfig(List<NodeConfig> nodeConfig) {
		super();
		this.nodeConfig = nodeConfig;
	}

	public List<NodeConfig> getNodeConfig() {
		return nodeConfig;
	}

	public void setNodeConfig(List<NodeConfig> nodeConfig) {
		this.nodeConfig = nodeConfig;
	}

}
