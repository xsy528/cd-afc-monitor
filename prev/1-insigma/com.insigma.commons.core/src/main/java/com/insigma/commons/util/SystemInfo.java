/* 
 * 日期：2010-2-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.util;

/**
 * 获取系统信息类 ，这个应该转到INSIGMA库中 Ticket:
 * 
 * @author fenghong
 */
public class SystemInfo {

	public static String getSystemName() {
		return getNameByKey("os.name");
	}

	public static String getCurrentName() {
		return getNameByKey("user.name");
	}

	public static String getJavaVersion() {
		return getNameByKey("java.version");
	}

	public static String getJavaHome() {
		return getNameByKey("java.home");
	}

	/**
	 * @param key
	 * @return
	 */
	private static String getNameByKey(String key) {
		try {
			String name = System.getProperty(key);
			return name;
		} catch (Exception e) {
			return "无法获取";
		}
	}
}
