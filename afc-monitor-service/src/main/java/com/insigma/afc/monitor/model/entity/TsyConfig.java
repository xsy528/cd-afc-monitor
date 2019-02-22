package com.insigma.afc.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 配置信息实体
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TSY_CONFIG")
public class TsyConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 配置项-KEY */
	@Id
	@Column(name = "CONFIG_KEY", length = 32)
	private String configKey;

	/** 配置项-value */
	@Column(name = "CONFIG_VALUE", length = 64)
	private String configValue;

	/** 备注 */
	@Column(name = "REMARK", length = 300)
	private String remark;

	public TsyConfig() {
	}

	public TsyConfig(String configKey,String configValue) {
		this.configKey = configKey;
		this.configValue = configValue;
	}

	@Override
	public String toString() {
		return "configKey:" + configKey + "  configValue:" + configValue;
	}


	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
