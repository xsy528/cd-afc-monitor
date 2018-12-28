/**
 * 
 */
package com.insigma.commons.ui.anotation.data;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.insigma.commons.io.FileUtil;
import com.insigma.commons.log.Log;
import com.insigma.commons.log.Logs;
import com.insigma.commons.ui.form.BeanEditorTableModel;

/**
 * @author DLF
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AnnotationOverrideManager {

	private static final Log logger = Logs.getLog(AnnotationOverrideManager.class);

	public static String JSON_DATA_PATH = "jsondata";

	private static Map<String, Map<String, Object>> annotationOverrides = new HashMap<String, Map<String, Object>>();

	/**
	 * //查找是否有覆写的Form信息，不存在返回Null
	 * @param data
	 * @return
	 */
	public final static Map lookupOverride(Class<?> dataClass) {
		if (dataClass == null) {
			return null;
		}
		String simpleName = dataClass.getSimpleName();
		if (annotationOverrides.containsKey(simpleName)) {
			return annotationOverrides.get(simpleName);
		}
		return parse(simpleName);
	}

	public final static Map lookupOverride(Class<?> dataClass, String suffix) {
		if (dataClass == null) {
			return null;
		}
		String simpleName = null;
		if (suffix != null && !"".equals(suffix.trim())) {
			simpleName = dataClass.getSimpleName() + "_" + suffix;
		} else {
			simpleName = dataClass.getSimpleName();
		}
		if (annotationOverrides.containsKey(simpleName)) {
			return annotationOverrides.get(simpleName);
		}
		return parse(simpleName);
	}

	/**
	 * @param dataClass
	 */
	private static Map parse(String simpleName) {
		//		String simpleName = dataClass.getSimpleName();
		try {
			URL res = findAsResource(JSON_DATA_PATH + "/" + simpleName + ".json");
			if (res == null) {
				return null;
			}
			String readFileToString = FileUtil.readFileToString(new File(res.getFile()), "utf8");
			if (readFileToString != null) {
				// jfq, todo
				return  new HashMap();
				//Map formMap = JSONDecoder.decode(readFileToString, HashMap.class);
				//annotationOverrides.put(simpleName, formMap);
				//return formMap;
			}
		} catch (Exception e) {
			logger.error("获取类：" + simpleName + "的覆盖配置文件失败。", e);
		}
		return null;
	}

	private static final URL findAsResource(final String path) {
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
		url = BeanEditorTableModel.class.getClassLoader().getResource(path);
		if (url != null)
			return url;

		// Next, try to locate this resource through the system classloader
		url = ClassLoader.getSystemClassLoader().getResource(path);

		// Anywhere else we should look?
		return url;
	}

}
