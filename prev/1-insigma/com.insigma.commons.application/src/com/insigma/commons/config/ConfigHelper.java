package com.insigma.commons.config;

/* 
 * 日期：2010-7-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.exception.ApplicationException;

/**
 
 */
public class ConfigHelper {

	private static final Log log = LogFactory.getLog(ConfigHelper.class);

	/**
	 * 
	 */
	public static final URL locateConfig(final String path) {
		try {
			return new URL(path);
		} catch (MalformedURLException e) {
			return findAsResource(path);
		}
	}

	/**
	 * 
	 */
	public static final URL findAsResource(final String path) {
		URL url = null;

		// First, try to locate this resource through the current
		// context classloader.
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		if (contextClassLoader != null) {
			url = contextClassLoader.getResource(path);
		}
		if (url != null)
			return url;

		// Next, try to locate this resource through this class's classloader
		url = ConfigHelper.class.getClassLoader().getResource(path);
		if (url != null)
			return url;

		// Next, try to locate this resource through the system classloader
		url = ClassLoader.getSystemClassLoader().getResource(path);

		// Anywhere else we should look?
		return url;
	}

	/**
	 * @return
	 */
	public static final InputStream getConfigStream(final String path) {
		final URL url = ConfigHelper.locateConfig(path);

		if (url == null) {
			String msg = "Unable to locate config file: " + path + "。";
			log.fatal(msg);
			throw new ApplicationException(msg);
		}

		try {
			return url.openStream();
		} catch (IOException e) {
			throw new ApplicationException("Unable to open config file: " + path + "。", e);
		}
	}

	/**
	 * @return
	 */
	public static final Reader getConfigStreamReader(final String path) {
		return new InputStreamReader(getConfigStream(path));
	}

	/**
	 * 创建时间 2010-7-12 下午03:47:45<br>
	 * 描述：Loads a properties instance based on the data at the incoming config location.
	 * 
	 * @param path
	 * @return
	 */
	public static final Properties getConfigProperties(String path) {
		try {
			Properties properties = new Properties();
			properties.load(getConfigStream(path));
			return properties;
		} catch (IOException e) {
			throw new ApplicationException("Unable to load properties from specified config file: " + path + "。", e);
		}
	}

	private ConfigHelper() {
	}

	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = ConfigHelper.class.getResourceAsStream(resource);
		}
		if (stream == null) {
			stream = ConfigHelper.class.getClassLoader().getResourceAsStream(stripped);
		}
		if (stream == null) {
			throw new ApplicationException(resource + " not found。");
		}
		return stream;
	}

	public static InputStream getUserResourceAsStream(String resource) {
		boolean hasLeadingSlash = resource.startsWith("/");
		String stripped = hasLeadingSlash ? resource.substring(1) : resource;

		InputStream stream = null;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(resource);
			if (stream == null && hasLeadingSlash) {
				stream = classLoader.getResourceAsStream(stripped);
			}
		}

		if (stream == null) {
			stream = ConfigHelper.class.getClassLoader().getResourceAsStream(resource);
		}
		if (stream == null && hasLeadingSlash) {
			stream = ConfigHelper.class.getClassLoader().getResourceAsStream(stripped);
		}

		if (stream == null) {
			throw new ApplicationException(resource + " not found。");
		}

		return stream;
	}

}
