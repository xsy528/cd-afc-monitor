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

public interface IConfigurationProvider {

	public String getApplicationName();

	public <T extends ConfigurationItem> T getConfigItem(T configInstance);

	public void loadConfiguration(Class<? extends ConfigurationItem>... externalConfigClasses);

	public void loadConfiguration(String configFileName, Class<? extends ConfigurationItem>... externalConfigClasses);

	public ConfigurationItem getConfig(Class<? extends ConfigurationItem> cls);

	// public void setINIConfigFile(String iniConfigFile);

}
