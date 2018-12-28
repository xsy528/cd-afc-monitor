/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Configuration extends ConfigurationItem {

	@XmlElement(required = true)
	protected String applicationName;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@XStreamImplicit
	@XmlElementRef
	protected List<ConfigurationItem> configItems;

	@XStreamOmitField
	@XmlTransient
	protected Map<String, ConfigurationItem> configMaps;

	@XStreamOmitField
	@XmlTransient
	protected boolean isInit;

	public void addConfig(ConfigurationItem item) {
		if (configItems == null) {
			configItems = new LinkedList<ConfigurationItem>();

		}
		if (configMaps == null) {
			configMaps = new HashMap<String, ConfigurationItem>();
		}

		XStreamAlias x = item.getClass().getAnnotation(XStreamAlias.class);
		String key = item.getClass().getSimpleName();
		if (x != null) {
			key = x.value();
		}
		configItems.add(item);
		configMaps.put(key, item);
	}

	public ConfigurationItem getConfigItem(String key) {
		if (!isInit) {
			initSubConfig();
		}
		return configMaps.get(key);
	}

	public ConfigurationItem getConfigItem(Class<? extends ConfigurationItem> itemClass) {
		if (!isInit) {
			initSubConfig();
		}
		if (itemClass.isInstance(this)) {
			return this;
		}
		XStreamAlias alias = itemClass.getAnnotation(XStreamAlias.class);
		return configMaps.get(alias.value());
	}

	public void initSubConfig() {
		isInit = true;
		if (configItems == null) {
			return;
		}
		configMaps = new HashMap<String, ConfigurationItem>();
		for (ConfigurationItem iterable_element : configItems) {
			XStreamAlias x = iterable_element.getClass().getAnnotation(XStreamAlias.class);
			iterable_element.getClass().getSimpleName();
			String key = iterable_element.getClass().getSimpleName();
			if (x != null) {
				key = x.value();
			}
			configMaps.put(key, iterable_element);
		}
	}
}
