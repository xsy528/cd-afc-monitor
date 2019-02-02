/**
 * 
 */
package com.insigma.commons.util;

import com.insigma.commons.util.lang.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 根据key值获取系统属性的工具类
 * 
 * @author DLF
 * 
 */
public class SystemPropertyUtil {

	/** Prefix for system property placeholders: "${" */
	public static final String PLACEHOLDER_PREFIX = "${";

	/** Suffix for system property placeholders: "}" */
	public static final String PLACEHOLDER_SUFFIX = "}";

	private static Logger logger = LoggerFactory.getLogger(SystemPropertyUtil.class);

	/**
	 * 
	 * @param key
	 * @param Value
	 */
	public static void setProperty(String key, String Value) {
		System.setProperty(key, Value);
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		return System.getProperty(key, defaultValue);
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回NULL
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key) {
		return System.getProperty(key);
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 返回long值
	 */
	public static long getPropertyForLong(String key, long defaultValue) {
		String numberStr = System.getProperty(key);
		return decodeNumber(numberStr, Long.class, defaultValue).longValue();
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 返回double值
	 */
	public static double getPropertyForDouble(String key, double defaultValue) {
		String numberStr = System.getProperty(key);
		return decodeNumber(numberStr, Double.class, defaultValue).doubleValue();
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 返回int值
	 */
	public static int getPropertyForInt(String key, int defaultValue) {
		String numberStr = System.getProperty(key);
		return decodeNumber(numberStr, Integer.class, defaultValue).intValue();
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 返回short值
	 */
	public static short getPropertyForShort(String key, short defaultValue) {
		String numberStr = System.getProperty(key);
		return decodeNumber(numberStr, Short.class, defaultValue).shortValue();
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 返回BigDecimal值
	 */
	public static BigDecimal getPropertyForBigDecimal(String key, BigDecimal defaultValue) {
		String numberStr = System.getProperty(key);
		return (BigDecimal) decodeNumber(numberStr, BigDecimal.class, defaultValue);
	}

	/**
	 * 根据key值获取系统属性 System.getProperty不存在，返回默认值 false
	 * 
	 * @param key
	 * @return 返回Boolean值
	 */
	public static Boolean getPropertyForBoolean(String key) {
		String booleanStr = System.getProperty(key);
		return decodeBoolean(booleanStr);
	}

	/**
	 * 转化numberStr值，如果numberStr为空，返回默认值 defaultValue
	 * 
	 * @param numberStr
	 * @param defaultValue
	 * @return
	 */
	private static Number decodeNumber(String numberStr, Class<? extends Number> targetClass, Number defaultValue) {
		if (numberStr == null || "".equals(numberStr)) {
			return defaultValue;
		}
		try {
			return NumberUtil.parseNumber(numberStr, targetClass);
		} catch (Exception e) {
			logger.error("Decode Number error.", e);
		}
		return defaultValue;
	}

	/**
	* 如果找不到变量不做任何替换
	* 
	 * Resolve ${...} placeholders in the given text,
	 * replacing them with corresponding system property values.
	 * @param expressionString the String to resolve
	 * @return the resolved String
	 * @see #PLACEHOLDER_PREFIX
	 * @see #PLACEHOLDER_SUFFIX
	 */
	public static String resolvePlaceholders(String expressionString) {
		return replace(expressionString, 0);
	}

	private static String replace(String expressionString, int fromIndex) {
		int start = expressionString.indexOf(PLACEHOLDER_PREFIX, fromIndex);
		int end = expressionString.indexOf(PLACEHOLDER_SUFFIX, fromIndex);
		fromIndex = end + 1;
		if (start != -1 && end != -1) {
			String key = expressionString.substring(start + 2, end);
			String replaceStr = expressionString.substring(start, end + 1);
			String valueByKey = getValueByKey(key);
			if (valueByKey != null) {
				fromIndex = start + valueByKey.length();
				expressionString = expressionString.replace(replaceStr, valueByKey);
			}
			return replace(expressionString, fromIndex);
		}
		return expressionString;
	}

	private static String getValueByKey(String key) {
		String property = System.getProperty(key);
		if (null == property) {
			property = System.getenv(key);
		}
		if (null == property) {
			logger.warn("环境变量中不存在变量：" + key + "的值，请检查是否已加载配置");
			return null;
		}
		return property;
	}

	/**
	 * 
	 * @param booleanStr
	 * @return
	 */
	public static Boolean decodeBoolean(String booleanStr) {
		if (booleanStr == null || "".equals(booleanStr)) {
			return false;
		} else {
			return Boolean.valueOf(booleanStr);
		}
	}
}
