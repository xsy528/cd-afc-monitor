package com.insigma.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.converter.Convert;
import com.insigma.commons.util.lang.DateTimeUtil;
import com.insigma.commons.util.lang.StringUtil;

@SuppressWarnings("unchecked")
public class ClassUtil {

	private static Log logger = LogFactory.getLog(ClassUtil.class);

	public static Class<?> forName(String name) {
		return forName(name, HashMap.class);
	}

	public static Class<?> forName(String name, Class<?> defaultClazz) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			logger.error("ClassName:" + name + " Not Found, return the defaultClazz:" + defaultClazz);
			return defaultClazz;
		}
	}

	public static synchronized String getProperties(Object bean) {
		StringBuffer sb = new StringBuffer(512);
		int index = 0;
		Method methodsOfBean[] = bean.getClass().getMethods();
		String property = null;
		String objStr = null;
		String nameGet = null;
		String nameGetAttr = null;
		try {
			for (int i = 0; i < methodsOfBean.length; i++) {
				nameGet = methodsOfBean[i].getName().substring(0, 3);
				nameGetAttr = methodsOfBean[i].getName().substring(3);
				if ("get".equalsIgnoreCase(nameGet) && !"class".equalsIgnoreCase(nameGetAttr)
						&& !"SERVLETWRAPPER".equalsIgnoreCase(nameGetAttr.toUpperCase())
						&& !"MULTIPARTREQUESTHANDLER".equalsIgnoreCase(nameGetAttr.toUpperCase())) {
					Object args = methodsOfBean[i].invoke(bean, (Object[]) null);
					if (args != null)
						objStr = args.toString();
					else
						objStr = "null";
					property = getObj(args, objStr);
					if (index == 0) {
						sb.append(nameGetAttr.toUpperCase() + "=" + property);
						index++;
					} else {
						sb.append("," + nameGetAttr.toUpperCase() + "=" + property);
					}
				}
			}

		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		}
		return sb.toString();
	}

	private static String getObj(Object obj, String property) {
		return property;
	}

	public static synchronized void trimProperties(Object bean) {
		Method methods[] = bean.getClass().getMethods();
		String nameGet = null;
		String nameGetAttr = null;
		Class tmpTypes[] = new Class[methods.length];
		String tmpNames[] = new String[methods.length];
		Method tmpSetMethods[] = new Method[methods.length];
		int index = 0;
		for (int i = 0; i < methods.length; i++) {
			nameGet = methods[i].getName().substring(0, 3);
			nameGetAttr = methods[i].getName().substring(3);
			if ("set".equalsIgnoreCase(nameGet)) {
				tmpTypes[index] = methods[i].getParameterTypes()[0];
				tmpNames[index] = nameGetAttr;
				tmpSetMethods[index] = methods[i];
				index++;
			}
		}

		Class types[] = new Class[index];
		String names[] = new String[index];
		Method setMethods[] = new Method[index];
		for (int i = 0; i < index; i++) {
			types[i] = tmpTypes[i];
			names[i] = tmpNames[i];
			setMethods[i] = tmpSetMethods[i];
		}

		Method methodsOfFrom[] = bean.getClass().getMethods();
		Object arg = null;
		Object encoded = null;
		try {
			for (int i = 0; i < names.length; i++) {
				for (int j = 0; j < methodsOfFrom.length; j++) {
					nameGet = methodsOfFrom[j].getName().substring(0, 3);
					nameGetAttr = methodsOfFrom[j].getName().substring(3);
					if (names[i].equalsIgnoreCase(nameGetAttr) && "get".equalsIgnoreCase(nameGet)) {
						Class returnType = methodsOfFrom[j].getReturnType();
						if (returnType.equals(java.lang.String.class)) {
							arg = methodsOfFrom[j].invoke(bean, (Object[]) null);
							encoded = Convert.convert(arg != null ? ((String) arg).trim() : null, types[i]);
							setMethods[i].invoke(bean, new Object[] { encoded });
						}
					}
				}

			}

		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		}
	}

	public static String getPropertyValue(Object obj, String method) {
		return getPropertyValue(obj, method, null);
	}

	public static String getPropertyValue(Object obj, String method, Object[] paramList) {
		try {
			java.lang.reflect.Method m = obj.getClass().getMethod(method, (Class[]) null);
			Object retObj = m.invoke(obj, paramList);
			if (retObj instanceof Date) {
				return DateTimeUtil.dateToString((Date) retObj, "yyyy-mm-dd");
			} else if (retObj instanceof Long) {
				return ((Long) retObj).toString();
			} else if (retObj instanceof Double) {
				return ((Double) retObj).toString();
			} else {
				return (String) retObj;
			}

		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public static void convertEntity2HashMap(Object entity, HashMap hm) {
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				java.lang.reflect.Method m = entity.getClass().getMethod("get" + StringUtil.converFirstUpper(fieldName),
						(Class[]) null);
				Object retObj = m.invoke(entity, (Object[]) null);

				if (retObj instanceof Date) {
					hm.put(fieldName, DateTimeUtil.dateToString((Date) retObj, "yyyy-mm-dd"));
				} else {
					hm.put(fieldName, retObj);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public static void convertHashMap2Entity(HashMap hm, Object entity) {
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				java.lang.reflect.Method m = entity.getClass().getMethod("set" + StringUtil.converFirstUpper(fieldName),
						(Class[]) null);
				Object[] value = new Object[] { hm.get(fieldName) };

				m.invoke(entity, value);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
