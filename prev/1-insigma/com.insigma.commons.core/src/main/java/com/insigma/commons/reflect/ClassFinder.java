/*
 * 日期：May 14, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.collections.map.LRUMap;

/**
 * Ticket:
 * 
 * @author Dingluofeng
 */
public class ClassFinder {

	private static boolean isJarFile;

	@SuppressWarnings("unchecked")
	private static Map<Object, List<Class>> clazzCache = Collections.synchronizedMap(new LRUMap(100));

	/**
	 * 描述：这个方法在classpath 下面搜索所有继承或实现superClazz的包名为packageName.*的类
	 * <p>
	 * 目前不找子包下的类
	 * 
	 * @param packageName
	 * @param superClazz
	 *            当该值为null时不做继承关系检查
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<Class> findConcreteClassesInClasspath(String packageName, Class superClazz)
			throws ClassNotFoundException, IOException {
		if (packageName == null) {
			return Collections.emptyList();
		}
		if (superClazz == null) {
			superClazz = Object.class;
		}
		Object key = makeKey(packageName, superClazz);
		List<Class> result = clazzCache.get(key);
		if (result == null) {
			result = findClasses(packageName, superClazz);
			clazzCache.put(key, result);
		}
		return result;

	}

	/**
	 * 成员packageName和clazz需要保证不为null.
	 */
	private static class CacheKey {

		final String packageName;

		@SuppressWarnings("unchecked")
		final Class clazz;

		@SuppressWarnings("unchecked")
		public CacheKey(String packageName, Class clazz) {
			this.packageName = packageName;
			this.clazz = clazz;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * clazz.hashCode() + packageName.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			return clazz.equals(other.clazz) && packageName.equals(other.packageName);
		}
	}

	@SuppressWarnings("unchecked")
	private static Object makeKey(String packageName, Class superClazz) {
		return new CacheKey(packageName, superClazz);
	}

	/**
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static List<Class> findClasses(String packageName, Class superClazz) throws IOException {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		List<Class> result = new LinkedList<Class>();
		List<File> list = null;
		try {
			list = getFilesList(loader, packageName);
		} catch (IOException e) {
			throw new IOException();
		}
		for (File file : list) {
			Class<?> someClass = null;
			String fileName = null;
			try {
				fileName = file.getName().substring(0, file.getName().lastIndexOf(".class"));
				someClass = loader.loadClass(packageName + "." + fileName);
			} catch (ClassNotFoundException e) {
				// logger.info("类" + fileName + "找不到，可能属于子包下的文件。");
				continue;
				// throw new ClassNotFoundException();
			}
			if ((superClazz.isAssignableFrom(someClass)) && !Modifier.isAbstract(someClass.getModifiers())
					&& !Modifier.isInterface(someClass.getModifiers())) {
				result.add(someClass);
			}
		}
		return result;
	}

	/**
	 * 获取文件指定package及子包下的所有class文件
	 * 
	 * @param packageName
	 * @return
	 * @throws IOException
	 */
	private static List<File> getFilesList(ClassLoader loader, String packageName) throws IOException {
		Enumeration<URL> urls = null;
		String packagePath = packageName.replaceAll("\\.", "/");
		try {
			urls = loader.getResources(packagePath);
		} catch (IOException e) {
			throw new IOException();
		}
		List<File> fileList = new ArrayList<File>();
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String strUrl = "/" + url.toExternalForm();
			strUrl = getFile(strUrl);
			if (isJarFile) {
				fileList = getJarFiles(strUrl, packagePath);
			} else {
				fileList = getFiles(strUrl);
			}
		}
		return fileList;

	}

	/**
	 * 从普通目录文件里取得制定目录下的class文件文件
	 * 
	 * @param strUrl
	 * @return
	 */
	private static List<File> getFiles(String strUrl) {
		File file = new File(strUrl);
		List<File> fileList = new ArrayList<File>();
		for (File f : file.listFiles()) {
			if (!f.isDirectory() && f.getName().endsWith(".class")) {
				fileList.add(f);
			}
		}
		return fileList;
	}

	/**
	 * 根据给定的URL名找到相应的文件名
	 * 
	 * @param strUrl
	 * @return
	 */
	private static String getFile(String strUrl) {
		if (strUrl.indexOf("jar:") != -1) {
			strUrl = strUrl.substring(10);
			int index = strUrl.indexOf(".jar");
			strUrl = strUrl.substring(0, index + 4);
			isJarFile = true;
		} else {
			// use in the develop process,
			// in the run time the class must in the jar
			strUrl = strUrl.substring(6);
			isJarFile = false;
		}
		return strUrl;
	}

	/**
	 * 从jar文件里取得制定包下的class文件文件
	 * 
	 * @param jarFileName
	 * @param str2
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static List<File> getJarFiles(String jarFileName, String packageName) throws IOException {
		List<File> fileList = new ArrayList<File>();
		try {
			// extracts just sizes only.
			ZipFile zf = new ZipFile("/" + jarFileName);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				if (ze.getName().startsWith(packageName) && ze.getName().endsWith(".class")) {
					fileList.add(new File(ze.getName()));
				}
			}
			zf.close();
		} catch (IOException e) {
			throw new IOException();
		}
		return fileList;
	}
}
