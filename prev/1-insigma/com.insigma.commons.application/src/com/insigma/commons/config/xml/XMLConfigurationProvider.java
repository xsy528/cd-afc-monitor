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
package com.insigma.commons.config.xml;

import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.application.Application;
import com.insigma.commons.config.ConfigHelper;
import com.insigma.commons.config.Configuration;
import com.insigma.commons.config.ConfigurationItem;
import com.insigma.commons.config.IConfigurationProvider;
import com.insigma.commons.config.NetworkConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLConfigurationProvider implements IConfigurationProvider {

	private final Log log = LogFactory.getLog(XMLConfigurationProvider.class);

	private Configuration config;

	private String ApplicationPath = System.getProperty("user.dir");

	private InputStream inputStream;

	private String iniConfigFile = "config.ini";

	private IDecryptAlgorithmHandler decryptAlgorithmHandler;

	public XMLConfigurationProvider() {
		super();
		try {
			decryptAlgorithmHandler = new DefaultDecryptAlgorithmHandler();
		} catch (Exception e) {
			log.error("加载IDecryptAlgorithmHandler失败。", e);
		}
	}

	public void setConfigFile(InputStream configFile) {
		this.inputStream = configFile;
	}

	@Override
	public void loadConfiguration(Class<? extends ConfigurationItem>... externalConfigClasses) {
		loadConfiguration("Config.xml", externalConfigClasses);
	}

	@Override
	public void loadConfiguration(String configFileName, Class<? extends ConfigurationItem>... externalConfigClasses) {
		initProperties();
		StringFilterReader filterReader = null;
		// close?
		InputStream in = null;
		try {
			XStream xs = new XStream(new DomDriver());
			xs.autodetectAnnotations(true);
			xs.processAnnotations(externalConfigClasses);

			if (inputStream != null) {
				in = inputStream;
				config = (Configuration) xs.fromXML(in);
			} else {
				// String file = ConfigHelper.findAsResource(configFileName).getFile();
				in = ConfigHelper.getUserResourceAsStream(configFileName);
				filterReader = new StringFilterReader(in);
				config = (Configuration) xs.fromXML(filterReader);
			}
		} catch (Exception e) {
			log.error("读取配置文件异常。", e);
			System.exit(-1);
		} finally {
			if (filterReader != null) {
				filterReader.close();
			}

			if (config != null) {
				NetworkConfig networkConfig = Application.getConfig(NetworkConfig.class);
				if (networkConfig != null) {
					Application.setDeviceIdFormat(networkConfig.getDeviceIdFormat());
					Application.setDeviceTypeFormat(networkConfig.getDeviceTypeFormat());
					Application.setLineIdFormat(networkConfig.getLineIdFormat());
					Application.setStationIdFormat(networkConfig.getStationIdFormat());
					Application.setStationlineIdFormat(networkConfig.getStationlineIdFormat());
				}
			}
		}
	}

	private void initProperties() {
		try {
			INIConfigFile configFile = new INIConfigFile();
			InputStream in = ConfigHelper.getUserResourceAsStream(iniConfigFile);
			if (in == null) {
				log.warn("不存在文件：" + iniConfigFile + " 不需要设置系统变量。");
				return;
			}
			// String file = systemResource.getFile();
			configFile.load(in);
			Map<String, String> entrySet = configFile.getSectionValues(INIConfigFile.DEFAULT_SECTION);
			for (Entry<String, String> entry : entrySet.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key != null && !key.equals("")) {
					if (decryptAlgorithmHandler != null) {
						value = decryptAlgorithmHandler.decrypt(value);
					}
					System.setProperty(key, value);
				}
			}
		} catch (RuntimeException re) {
			log.warn("不存在文件：" + iniConfigFile + " 不需要设置系统变量。");
		} catch (Exception e) {
			log.error("读取properties配置,设置系统变量出错", e);
		}
	}

	public String getApplicationPath() {
		return ApplicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		ApplicationPath = applicationPath;
	}

	@Override
	public ConfigurationItem getConfig(Class<? extends ConfigurationItem> itemClass) {
		return config.getConfigItem(itemClass);
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	@Override
	public String getApplicationName() {
		return config.getApplicationName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConfigurationItem> T getConfigItem(T configInstance) {
		if (configInstance.getClass().isInstance(config)) {
			return (T) config;
		}
		return (T) getConfig(configInstance.getClass());
	}

	/**
	 * @param propertiesFile the propertiesFile to set
	 */
	public void setINIConfigFile(String iniConfigFile) {
		this.iniConfigFile = iniConfigFile;
	}

	/**
	 * @param decryptAlgorithmHandler the decryptAlgorithmHandler to set
	 */
	public void setDecryptAlgorithmHandler(IDecryptAlgorithmHandler decryptAlgorithmHandler) {
		this.decryptAlgorithmHandler = decryptAlgorithmHandler;
	}

	/**
	 * @return the decryptAlgorithmHandler
	 */
	public IDecryptAlgorithmHandler getDecryptAlgorithmHandler() {
		return decryptAlgorithmHandler;
	}

}
