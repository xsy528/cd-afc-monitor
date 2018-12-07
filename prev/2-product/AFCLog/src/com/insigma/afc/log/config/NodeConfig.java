package com.insigma.afc.log.config;

import java.util.List;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Node")
public class NodeConfig extends ConfigurationItem {

	private String nodeName;

	private List<ModuleConfig> moduleConfigList;

	private List<ModuleConfig> fileServerConfigList;

	public NodeConfig() {

	}

	public NodeConfig(String nodeName, List<ModuleConfig> moduleConfigList, List<ModuleConfig> fileServerConfigList) {
		super();
		this.nodeName = nodeName;
		this.moduleConfigList = moduleConfigList;
		this.fileServerConfigList = fileServerConfigList;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<ModuleConfig> getModuleConfigList() {
		return moduleConfigList;
	}

	public void setModuleConfigList(List<ModuleConfig> moduleConfigList) {
		this.moduleConfigList = moduleConfigList;
	}

	public List<ModuleConfig> getFileServerConfigList() {
		return fileServerConfigList;
	}

	public void setFileServerConfigList(List<ModuleConfig> fileServerConfigList) {
		this.fileServerConfigList = fileServerConfigList;
	}

}
