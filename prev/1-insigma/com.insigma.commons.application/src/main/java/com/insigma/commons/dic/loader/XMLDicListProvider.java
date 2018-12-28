/* 
 * 日期：2011-1-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.loader.annotation.AnnotationReader;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class XMLDicListProvider implements IDicClassListProvider {
	/**
	 * 
	 */
	private static final String PACKAGE = "package:";

	private static final int PACKAGE_LENGTH = PACKAGE.length();

	private static final Log logger = LogFactory.getLog(XMLDicListProvider.class);

	List<String> locations;

	public XMLDicListProvider(List<String> classNames) {
		if (classNames != null) {
			this.locations = classNames;
		}
	}

	public List<Class<?>> getDicClassList() {
		if (locations == null) {
			return new ArrayList<Class<?>>();
		}
		List<String> packages = new ArrayList<String>();

		List<Class<?>> list = new ArrayList<Class<?>>();
		for (String clazzName : locations) {
			if (clazzName.startsWith(PACKAGE)) {
				packages.add(String.format("classpath*:%s", clazzName.substring(PACKAGE_LENGTH)));
				continue;
			}
			try {
				list.add(Class.forName(clazzName));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("类：[" + clazzName + "]无法找到，请检查配置...", e);
			}
		}
		int classcount = list.size();
		if (packages.size() > 0) {
			String[] locations = new String[packages.size()];
			AnnotationReader reader = new AnnotationReader(packages.toArray(locations), Dic.class);
			List<Class<?>> plist = reader.findAnnotations(Dic.class);
			if (plist != null) {
				for (Class<?> pc : plist) {
					if (!list.contains(pc)) {
						list.add(pc);
					}
				}
			}
		}
		int pgcount = list.size() - classcount;
		logger.debug(String.format("配置%d个class，包中%d个class", classcount, pgcount));
		return list;
	}

	/**
	 * @return the classNames
	 */
	public List<String> getClassNameList() {
		return locations;
	}

	/**
	 * @param classNames
	 *            the classNames to set
	 */
	public void setClassNameList(List<String> classNames) {
		this.locations = classNames;
	}

}
