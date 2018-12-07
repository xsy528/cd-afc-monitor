package com.insigma.afc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 配置信息实体
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TSY_CONFIG")
public class TsyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 配置项-KEY */
	private String configKey;

	/** 配置项-value */
	private String configValue;

	/** 备注 */
	private String remark;

	public TsyConfig() {
	}

	public TsyConfig(String configKey) {
		this.configKey = configKey;
	}

	@Override
	public String toString() {
		return "configKey:" + configKey + "  configValue:" + configValue;
	}

	@Id
	@Column(name = "CONFIG_KEY", length = 32)
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	@Column(name = "CONFIG_VALUE", length = 64)
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	@Column(name = "REMARK", length = 300)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
