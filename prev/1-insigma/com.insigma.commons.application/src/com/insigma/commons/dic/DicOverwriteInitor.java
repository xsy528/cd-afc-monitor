/* 
 * 日期：2010-11-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.application.Application;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.dic.loader.IDicClassListProvider;
import com.insigma.commons.dic.loader.annotation.ClasspathAnnotationScanProvider;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DicOverwriteInitor {
	private static final Log log = LogFactory.getLog(DicOverwriteInitor.class);

	public static void init() {
		IDicClassListProvider dicProvider = null;
		log.debug("尝试从application中查找字典的初始化列表...");
		try {
			dicProvider = (IDicClassListProvider) Application.getClassBean(IDicClassListProvider.class);
			log.debug("尝试从application中查找字典的初始化列表成功.");
		} catch (Exception e) {
			log.debug("尝试从application中查找字典的初始化列表失败####，采用默认的扫描classpath的字典初始化...");
			dicProvider = new ClasspathAnnotationScanProvider();
		}
		init(dicProvider);
	}

	@SuppressWarnings("unchecked")
	public static void init(IDicClassListProvider dicProvider) {
		List<Class<?>> dicAnnos = dicProvider.getDicClassList();
		log.debug(String.format("共得到%d个字典项：%s", dicAnnos.size(), dicAnnos));

		Map<Class<?>, List<Class<?>>> map = new HashMap<Class<?>, List<Class<?>>>();
		for (Class<?> projectClazz : dicAnnos) {
			Dic dic = projectClazz.getAnnotation(Dic.class);
			if (dic == null) {
				continue;
			}
			Class<? extends Definition> dicClazz = dic.overClass();

			if (map.containsKey(dicClazz)) {
				List<Class<?>> list = map.get(dicClazz);
				list.add(projectClazz);
			} else {
				List<Class<?>> list = new ArrayList<Class<?>>();
				list.add(projectClazz);
				map.put(dicClazz, list);
			}
		}

		for (Class<?> dicClazz : map.keySet()) {
			List<Class<?>> list = map.get(dicClazz);
			if (list.size() > 2) {
				log.error("多个项目要覆盖产品字典:" + dicClazz + ",忽略，项目覆盖类为：" + list);
				continue;
			}
			Class<?> projectClazz = null;
			if (list.size() == 1) {
				// log.error("没有项目要覆盖产品字典:" + dicClazz + ",初始化产品字典本身。");
				projectClazz = dicClazz;
			}
			for (Class<?> clazz : list) {
				if (!clazz.equals(dicClazz)) {
					projectClazz = clazz;
					break;
				}
			}
			log.debug("项目字典[" + projectClazz + "],覆盖产品字典[" + dicClazz + "].");
			if (projectClazz == null) {
				continue;
			}

			try {

				Dic dic = projectClazz.getAnnotation(Dic.class);
				Class<? extends Definition> productClazz = dic.overClass();
				Object instance = null;
				try {
					Method getInstance = productClazz.getMethod("getInstance");
					instance = getInstance.invoke(null);
				} catch (Exception e) {
					log.error(String.format("%s没有定义单例方法：getInstance(),无法进行覆盖操作，忽略。", productClazz));
					continue;
				}
				Map<String, DicitemEntry> dicItecEntryMap = (Map<String, DicitemEntry>) Definition.class
						.getDeclaredField("dicItecEntryMap").get(instance);

				dicItecEntryMap.clear();
				if (!projectClazz.equals(productClazz)) {
					// 如果不是自己，先设null
					for (Field productField : productClazz.getFields()) {
						// 先把所有的产品字典值设置为null
						if (Modifier.isStatic(productField.getModifiers())
								&& !Modifier.isFinal(productField.getModifiers())) {
							productField.set(null, null);
						}
					}
				}

				Field[] fields = projectClazz.getFields();
				for (Field projectField : fields) {
					String fname = projectField.getName();
					try {
						if (fname.equals("instance")) {
							continue;
						}
						if (!projectField.getType().isPrimitive()
								&& !Number.class.isAssignableFrom(projectField.getType())
								&& !String.class.equals(projectField.getType())) {
							//当前支持数字类型和字符串类型
							continue;
						}
						Object projectValue = projectField.get(null);
						DicItem dicitem = projectField.getAnnotation(DicItem.class);
						if (dicitem != null) {
							dicItecEntryMap.put(fname, new DicitemEntry(fname, dicitem, projectValue));
						}

						Field productField = productClazz.getField(fname);
						if (Modifier.isFinal(productField.getModifiers())) {
							log.error(String.format("======字典类：[%s]中的字段:[%s]为final，无法覆盖，请检查=====",
									productClazz.getName(), productField.getName()));
							continue;
						}
						if (!projectField.getType().equals(productField.getType())) {
							String format = String.format(
									"======字典类：产品类[%s]中的字段:[%s]类型和项目类[%s]中的字段:[%s]类型不一致，无法覆盖，请检查=====",
									productClazz.getName(), productField.getName(), projectClazz.getName(),
									projectField.getName());
							log.error(format);
							System.err.println(format);
							continue;
						}
						// 覆盖产品级的常量值
						productField.setAccessible(true);
						productField.set(null, projectValue);
					} catch (SecurityException e) {
						log.error("安全问题.", e);
					} catch (NoSuchFieldException e) {
						// log.debug(projectField + "在产品类:" +
						// productClazz.getName() + "中不存在。");
						continue;
					} catch (Exception e) {
						log.error(String.format("======处理项目字典类：[%s]的字段:[%s]时异常,要覆盖的产品字典为:[%s]=====",
								projectClazz.getName(), fname, productClazz.getName()), e);
					}
				}
				Object[] codeList = new Object[dicItecEntryMap.size()];
				String[] nameList = new String[dicItecEntryMap.size()];
				ArrayList<DicitemEntry> values = new ArrayList<DicitemEntry>(dicItecEntryMap.values());
				Collections.sort(values, DicitemEntry.DICCMPR);

				int i = 0;
				for (DicitemEntry v : values) {
					codeList[i] = v.value;
					nameList[i++] = v.dicitem.name();
				}
				Field nlistF = Definition.class.getDeclaredField("nameList");
				nlistF.setAccessible(true);
				Field cListF = Definition.class.getDeclaredField("codeList");
				cListF.setAccessible(true);
				nlistF.set(instance, nameList);
				cListF.set(instance, codeList);
			} catch (Exception e1) {
				log.error("字典初始化异常.", e1);
			}
		}
	}

}
