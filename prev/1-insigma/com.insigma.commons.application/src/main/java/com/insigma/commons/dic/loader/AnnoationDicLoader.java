/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.dic.DicItemModel;
import com.insigma.commons.dic.DicModel;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.dic.loader.annotation.AnnotationReader;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AnnoationDicLoader implements DicLoader {

	private Log logger = LogFactory.getLog(getClass());

	private List<DicModel> dics = null;

	public AnnoationDicLoader() {
		dics = new ArrayList<DicModel>();
		AnnotationReader ar = new AnnotationReader(Dic.class);
		List<Class<?>> dicAnnos = ar.findAnnotations(Dic.class);
		for (Class<?> clazz : dicAnnos) {
			Dic dic = clazz.getAnnotation(Dic.class);
			DicModel dicModel = new DicModel();
			String dicname = dic.name();
			if (StringUtils.isEmpty(dicname)) {
				dicname = clazz.getName();
			}
			dicModel.setName(dicname);
			dicModel.setType(dic.type());
			dicModel.setDesc(dic.desc());
			Field[] fs = clazz.getDeclaredFields();
			for (Field field : fs) {
				DicItem dicItemAnn = field.getAnnotation(DicItem.class);
				if (dicItemAnn == null) {
					continue;
				}
				DicItemModel<Object> dicitem = new DicItemModel<Object>();
				String key = dicItemAnn.key();
				if (StringUtils.isEmpty(key)) {
					key = field.getName();
				}
				dicitem.setKey(key);

				String name = dicItemAnn.name();
				if (StringUtils.isEmpty(name)) {
					name = field.getName();
				}
				dicitem.setName(name);
				dicitem.setRef(dicItemAnn.ref());
				dicitem.setRelation(dicItemAnn.relation());
				dicitem.setDesc(dicItemAnn.desc());
				try {
					Object newInstance = null;
					if (clazz.isInterface()) {

					} else {
						newInstance = clazz.newInstance();
					}
					Class<?> type = field.getType();
					field.setAccessible(true);
					if (type.isPrimitive()) {
						if (type.isAssignableFrom(int.class)) {
							dicitem.setValue(field.getInt(newInstance));
						} else if (type.isAssignableFrom(long.class)) {
							dicitem.setValue(field.getLong(newInstance));
						} else if (type.isAssignableFrom(short.class)) {
							dicitem.setValue(field.getShort(newInstance));
						} else if (type.isAssignableFrom(char.class)) {
							dicitem.setValue(field.getChar(newInstance));
						} else if (type.isAssignableFrom(byte.class)) {
							dicitem.setValue(field.getByte(newInstance));
						} else if (type.isAssignableFrom(float.class)) {
							dicitem.setValue(field.getFloat(newInstance));
						} else if (type.isAssignableFrom(double.class)) {
							dicitem.setValue(field.getDouble(newInstance));
						} else if (type.isAssignableFrom(boolean.class)) {
							dicitem.setValue(field.getBoolean(newInstance));
						} else {
							dicitem.setValue(null);
						}
					} else {
						dicitem.setValue(field.get(newInstance));
					}
					if (dicitem.getValue() == null) {
						try {
							Method method = clazz
									.getMethod("get" + String.valueOf(field.getName().charAt(0)).toUpperCase()
											+ field.getName().substring(1));
							dicitem.setValue(method.invoke(newInstance));
						} catch (Exception e) {
							logger.error("调用方法错误", e);
						}
					}
				} catch (Exception e) {
					logger.error("调用方法错误", e);
				}

				dicModel.addItem(dicitem);
			}
			dics.add(dicModel);
		}
	}

	public List<DicModel> loadAllDic() {
		return dics;
	}
}
