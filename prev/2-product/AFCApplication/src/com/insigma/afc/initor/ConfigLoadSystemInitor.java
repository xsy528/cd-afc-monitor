/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.config.AFCConfiguration;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.ConfigurationItem;
import com.insigma.commons.config.IConfigurationProvider;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.config.xml.XMLConfigurationProvider;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@SuppressWarnings("unchecked")
public class ConfigLoadSystemInitor extends SystemInitor {

	List<Class<? extends ConfigurationItem>> configClasses = new ArrayList<Class<? extends ConfigurationItem>>();

	private String fileName = "Config.xml";

	public ConfigLoadSystemInitor() {
		this("Config.xml");
	}

	public ConfigLoadSystemInitor(String fileName) {
		this(fileName, new XMLConfigurationProvider());
	}

	public ConfigLoadSystemInitor(String fileName, IConfigurationProvider configurationProvider) {
		if (fileName != null) {
			this.fileName = fileName;
		}
		configClasses.add(AFCConfiguration.class);
		configClasses.add(NetworkConfig.class);
		Application.setConfigProvider(configurationProvider);
	}

	@Override
	public String getName() {
		return "配置加载初始化";
	}

	@Override
	public boolean init() {
		logger.debug("读取配置文件：" + fileName);
		Class<? extends ConfigurationItem>[] cs = new Class[configClasses.size()];
		int i = 0;
		for (Class<? extends ConfigurationItem> clazz : configClasses) {
			cs[i++] = clazz;
		}
		Application.loadConfiguration(fileName, cs);
		return true;
	}

	public void loadConfiguration(Class<? extends ConfigurationItem>... externalConfigClasses) {
		if (externalConfigClasses == null) {
			return;
		}
		for (Class<? extends ConfigurationItem> class1 : externalConfigClasses) {
			configClasses.add(class1);
		}
	}
}
