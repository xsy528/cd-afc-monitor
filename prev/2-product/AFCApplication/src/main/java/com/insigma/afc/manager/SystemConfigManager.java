/* 
 * 日期：2010-12-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.entity.TsyConfig;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.service.ICommonDAO;

/**
 * 系统配置管理<br>
 * KEY定义参见SystemConfigKey<br>
 * Ticket:
 * 
 * @author fenghong
 */
@SuppressWarnings("unchecked")
public class SystemConfigManager {

	private static final Log logger = LogFactory.getLog(SystemConfigManager.class);

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigItem(String key) {
		try {
			//TODO 需重构为在Service中封装
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			String sql = "select config_value from tsy_config where config_key=?";
			List list = commonDAO.getEntityListSQL(sql, key);
			if (list.isEmpty()) {
				logger.warn("KEY为[" + key + "]的系统配置项不存在 !");
				return null;
			}
			return list.get(0).toString();
		} catch (Exception e) {
			logger.error("获取系统配置项异常", e);
			return null;
		}
	}

	public static void setConfigItem(String key, Object value) {
		try {
			TsyConfig config = new TsyConfig();
			config.setConfigKey(key);
			config.setConfigValue(String.valueOf(value));
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			commonDAO.saveOrUpdateObj(config);
		} catch (Exception e) {
			logger.error("设置系统配置项异常", e);
			throw new ApplicationException("设置系统配置项异常。");
		}
	}

	public static boolean saveConfigItem(String key, Object value) {
		try {
			TsyConfig config = new TsyConfig();
			config.setConfigKey(key);
			config.setConfigValue(String.valueOf(value));
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			commonDAO.saveOrUpdateObj(config);
			return true;
		} catch (Exception e) {
			logger.error("设置系统配置项异常", e);
		}
		return false;
	}

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static int getConfigItemForInt(String key) {
		String value = getConfigItem(key);
		if (value == null) {
			return 0;
		} else {
			return Integer.valueOf(value);
		}
	}

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static long getConfigItemForLong(String key) {
		String value = getConfigItem(key);
		if (value == null) {
			return 0L;
		} else {
			return Long.valueOf(value);
		}
	}

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static short getConfigItemForShort(String key) {
		String value = getConfigItem(key);
		if (value == null) {
			return 0;
		} else {
			return Short.valueOf(value);
		}
	}

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getConfigItemForBoolean(String key) {
		String value = getConfigItem(key);
		if (value == null) {
			return false;
		} else {
			return Boolean.valueOf(value.trim());
		}
	}

	/**
	 * 更新线网拓扑信息到系统参数表
	 */
	public static void updateNetworkConfig() {
		try {
			NetworkConfig config = (NetworkConfig) Application.getConfig(NetworkConfig.class);
			if (config != null) {
				setConfigItem(SystemConfigKey.LINE_ID, config.getLineNo());
				logger.debug("更新[" + SystemConfigKey.LINE_ID + "]值为：" + config.getLineNo());
				setConfigItem(SystemConfigKey.STATION_ID, config.getStationNo());
				logger.debug("更新[" + SystemConfigKey.STATION_ID + "]值为：" + config.getStationNo());
				setConfigItem(SystemConfigKey.NODE_ID, config.getDeviceId());
				logger.debug("更新[" + SystemConfigKey.NODE_ID + "]值为：" + config.getDeviceId());
			} else {
				logger.warn("NetworkConfig信息为Null，不需要更新系统参数表。");
			}
		} catch (Exception e) {
			logger.warn("更新NetworkConfig信息到系统参数表失败。", e);
		}
	}

	public List<TsyConfig> getTsyConfigList() {
		try {
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			return commonDAO.getEntityListHQL("from TsyConfig");
		} catch (Exception e) {
			throw new ApplicationException("获取配置信息列表异常。", e);
		}
	}
}
