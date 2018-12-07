package com.insigma.afc.log.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Module")
public class ModuleConfig extends ConfigurationItem {

	private int moduleNumber;

	private String moduleName;

	public ModuleConfig(int moduleNumber, String moduleName) {
		this.moduleNumber = moduleNumber;
		this.moduleName = moduleName;
	}

	public ModuleConfig() {
	}

	public int getModuleNumber() {
		return moduleNumber;
	}

	public void setModuleNumber(int moduleNumber) {
		this.moduleNumber = moduleNumber;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
