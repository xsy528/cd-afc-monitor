/**
 * iFrameWork 框架
 * 
 * @component     应用程序框架
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.application;

import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.config.Configuration;
import com.insigma.commons.config.ConfigurationItem;
import com.insigma.commons.config.IConfigurationProvider;
import com.insigma.commons.config.xml.XMLConfigurationProvider;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.spring.BeanFactoryUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Application {

	private static Log logger = LogFactory.getLog(Application.class);

	private static HashMap<Object, Object> values = new HashMap<Object, Object>();

	private static IConfigurationProvider configProvider;

	private static Configuration config;

	private static Map<String, IService> services = new HashMap<String, IService>();

	private static Map<String, Object> classBeans = new HashMap<String, Object>();

	private static Map<Long, String> imagePaths = new HashMap<Long, String>();

	//shenchao 节点编号String.format
	private static String deviceIdFormat = "%08x";

	private static String stationlineIdFormat = "%04x";

	private static String lineIdFormat = "%02x";

	private static String stationIdFormat = "%02x";

	private static String deviceTypeFormat = "%02x";

	//当前活动shell
	private static org.eclipse.swt.widgets.Shell shell = null;

	public static org.eclipse.swt.widgets.Shell getShell() {
		return shell;
	}

	public static void setShell(org.eclipse.swt.widgets.Shell shell) {
		Application.shell = shell;
	}

	public static Object getData(int key) {
		String keys = String.valueOf(key);
		return values.get(keys);
	}

	public static void setData(int key, Object value) {
		String keys = String.valueOf(key);
		if (values.containsKey(keys)) {
			logger.warn("已经包含KEY为[ " + keys + " ]的数据, 重新加载");
			values.remove(keys);
		}
		values.put(keys, value);
	}

	public static Object getData(String key) {
		return values.get(key);
	}

	public static void setData(String key, Object value) {
		values.put(key, value);
	}

	public static <T> T getClassByName(String beanName) {
		if (classBeans.containsKey(beanName)) {
			return (T) classBeans.get(beanName);
		}
		return (T) BeanFactoryUtil.getBean(beanName);
	}

	public static <T> T getClassBean(Class<T> cls) throws ServiceException {
		if (classBeans.containsKey(cls.getName())) {
			return (T) classBeans.get(cls.getName());
		}
		String[] beans = BeanFactoryUtil.getApplicationContext().getBeanNamesForType(cls);

		if (beans == null || beans.length <= 0) {
			throw new ServiceException("Service Class " + cls.getName() + " is not found");
		}
		if (beans.length > 1) {
			throw new ServiceException(
					"There are " + beans.length + " " + cls.getName() + " implements in application context。");
		}
		return (T) BeanFactoryUtil.getBean(beans[0]);
	}

	public static <T extends IService> T getService(Class<T> cls) throws ServiceException {
		if (services.containsKey(cls.getName())) {
			return (T) services.get(cls.getName());
		} else {
			String[] beans = BeanFactoryUtil.getApplicationContext().getBeanNamesForType(cls);
			if (beans == null || beans.length <= 0) {
				throw new ServiceException("Service Class " + cls.getName() + " is not found");
			}
			if (beans.length > 1) {
				throw new ServiceException(
						"There are " + beans.length + " " + cls.getName() + " implements in application context。");
			}
			return (T) BeanFactoryUtil.getBean(beans[0]);
		}

	}

	public static void setConfigProvider(IConfigurationProvider configProvider) {
		Application.configProvider = configProvider;
	}

	public static <T extends ConfigurationItem> T getConfig(Class<T> cls) {
		return (T) configProvider.getConfig(cls);
	}

	public static Configuration getConfig() {
		return config;
	}

	public static void setConfig(Configuration config) {
		Application.config = config;
	}

	public static void loadConfiguration(String configFileName,
			Class<? extends ConfigurationItem>... externalConfigClasses) {
		configProvider.loadConfiguration(configFileName, externalConfigClasses);
	}

	public static Object getObject(String key) {
		if (values.containsKey(key)) {
			return values.get(key);
		}
		return Application.getData(key);
	}

	public static void setObject(String key, Object object) {
		values.put(key, object);
	}

	public static String getImagePath(long key) {
		String imagePath = null;
		imagePath = imagePaths.get(key);
		return imagePath;
	}

	//key--nodeid Map状态  key--id tree状态
	public static void addImagePath(long key, String imagePath) {
		if (imagePaths.get(key) != null) {
			imagePaths.remove(key);
		}
		imagePaths.put(key, imagePath);
	}

	public static String getDeviceIdFormat() {
		return deviceIdFormat;
	}

	public static void setDeviceIdFormat(String deviceIdFormat) {
		Application.deviceIdFormat = deviceIdFormat;
	}

	public static String getStationlineIdFormat() {
		return stationlineIdFormat;
	}

	public static void setStationlineIdFormat(String stationlineIdFormat) {
		Application.stationlineIdFormat = stationlineIdFormat;
	}

	public static String getLineIdFormat() {
		return lineIdFormat;
	}

	public static void setLineIdFormat(String lineIdFormat) {
		Application.lineIdFormat = lineIdFormat;
	}

	public static String getStationIdFormat() {
		return stationIdFormat;
	}

	public static void setStationIdFormat(String stationIdFormat) {
		Application.stationIdFormat = stationIdFormat;
	}

	public static String getDeviceTypeFormat() {
		return deviceTypeFormat;
	}

	public static void setDeviceTypeFormat(String deviceTypeFormat) {
		Application.deviceTypeFormat = deviceTypeFormat;
	}

}
