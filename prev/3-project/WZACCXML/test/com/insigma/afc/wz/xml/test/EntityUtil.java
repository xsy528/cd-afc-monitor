/*
 * 日期：2008-10-15
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.wz.xml.test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 实体对象工具类
 * <p>
 * Ticket:
 * 
 * @author Dingluofeng
 */
public class EntityUtil {

	private static Logger logger = Logger.getLogger(EntityUtil.class);

	public static void main(String[] args) {
		String str = System.getProperty("user.dir");
		System.out.println(str);
		File file = new File("");
		file.getName();
		System.out.println(File.separatorChar + ">>" + File.separator);
		int nst = str.lastIndexOf(File.separatorChar);
		System.out.println(str.substring(-1));

		// Object entity = arrayToEntity(new Object[] {
		// "aaa", "vvv", 112L}, Address.class);
		// toStringArray(entity);

		// Person p = new Person("tom", "pwd", 12);
		// Address add1 = new Address();
		// add1.setAdd("aaa");
		// Address add2 = new Address();
		// add2.setAdd("bbb");
		// List li = new ArrayList();
		// li.add(add1);
		// li.add(add2);
		// p.setAddresses(li);
		// List<?> l = getEODFieldNames(p);
		// for (Object object : l) {
		// System.out.println(object);
		// }
	}

	/**
	 * 把数组转化为指定的实体对象
	 * 
	 * @param objs
	 * @param entity
	 */
	public static Object arrayToEntity(Object[] objs, Class<?> clazz) {
		Object entity = null;
		try {
			Constructor<?> constructor = clazz.getConstructor();
			entity = constructor.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			int i = 0;
			for (Object obj : objs) {
				setValue(entity, fields[i], obj);
				i++;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return entity;
	}

	private static void setValue(Object entity, Field field, Object value) throws Exception {
		field.setAccessible(true);

		String val = String.valueOf(value);
		if (val == null) {
			field.set(entity, val);
			return;
		}
		if (field.getType().isPrimitive()) {
			field.set(entity, Long.valueOf(val).shortValue());
			return;
		}
		if (field.getType().equals(Long.class)) {
			field.set(entity, Long.valueOf(val));
			return;
		}
		if (field.getType().equals(Integer.class)) {
			field.set(entity, Integer.valueOf(val));
			return;
		}

		if (field.getType().equals(Double.class)) {
			field.set(entity, Double.valueOf(val));
			return;
		}

		if (field.getType().equals(Short.class)) {
			field.set(entity, Short.valueOf(val));
			return;
		}

		if (field.getType().equals(String.class)) {
			field.set(entity, String.valueOf(val));
			return;
		}

		if (field.getType().equals(Date.class)) {
			Date datetime = null;
			if (val.length() != 0) {
				String temp_date = DateTimeUtil.formatDateToString((Date) value, "yyyy-MM-dd HH:mm:ss");
				datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temp_date);
			}
			field.set(entity, datetime);
			return;
		}
	}

	/**
	 * 比較兩個class類的field，獲取不一致的域名
	 * 
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static List<String> showDiffField(Class<?> clazz1, Class<?> clazz2) {
		List<String> result = new ArrayList<String>();
		Field[] f1 = clazz1.getDeclaredFields();
		Field[] f2 = clazz2.getDeclaredFields();
		for (Field field1 : f1) {
			boolean temp = false;
			for (Field field2 : f2) {
				if (field1.getName().equals(field2.getName())) {
					temp = true;
					break;
				}
			}
			if (!temp) {
				result.add(field1.getName());
			}
		}
		return result;
	}

	/**
	 * 将标准的实体对象转化为String[]
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] toStringArray(Object obj) {
		List<String> list = new ArrayList<String>();
		String[] ss = new String[] {};
		Field[] f = obj.getClass().getDeclaredFields();
		for (Field element : f) {
			// String str = element.getName();
			Object o = null;
			try {
				element.setAccessible(true);
				o = element.get(obj);
			} catch (Exception e) {
				logger.error("对象转化错误。", e);
			}
			list.add(o == null ? null : o.toString());
			// logger.info(str + " = " + o);
		}

		return list.toArray(ss);
	}

	/**
	 * 比较两个对象实体是否相等
	 * <p>
	 * (默认全比较，可以给不需要比较的域打View(compare=false))标签
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compare(Object obj1, Object obj2) {
		if (!obj1.getClass().equals(obj2.getClass())) {
			logger.info("对象不一致。");
			return false;
		}
		Class<?> c = obj1.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			// View v = field.getAnnotation(View.class);
			try {
				Object value1 = getFieldValue(obj1, field);
				Object value2 = getFieldValue(obj2, field);
				if (null == value1 && null == value2) {
					continue;
				}
				if (null == value1 && null != value2) {
					logger.fatal("存在差异的域：" + field.getName() + " [ " + value1 + "<--->" + value2 + " ] ");
					return false;
				}
				if (value1 != null && !value1.equals(value2)) {
					logger.fatal("存在差异的域：" + field.getName() + " [ " + value1 + "<--->" + value2 + " ] ");
					return false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return true;
	}

	/**
	 * 比较两个对象实体是否相等
	 * <p>
	 * (默认全比较，可以给不需要比较的域打View(compare=false))标签
	 * <p>
	 * 如果是复合类型打View(complex=true))标签
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean deepCompare(Object obj1, Object obj2) {
		// if (!obj1.getClass().equals(obj2.getClass())) {
		// logger.info("对象不一致。" + obj1.getClass() + "-->" + obj2.getClass());
		// return false;
		// }
		Class<?> c = obj1.getClass();
		// List
		if (List.class.isAssignableFrom(c)) {
			List<?> list1 = (List<?>) obj1;
			List<?> list2 = (List<?>) obj2;
			return compareList(list1, list2);
		}
		// Arrays
		if (c.isArray()) {
			if (c.getComponentType().isPrimitive()) {
				if (c.getCanonicalName().equals("int[]")) {
					return Arrays.equals((int[]) obj1, (int[]) obj2);
				} else if (c.getCanonicalName().equals("short[]")) {
					return Arrays.equals((short[]) obj1, (short[]) obj2);
				} else if (c.getCanonicalName().equals("long[]")) {
					return Arrays.equals((long[]) obj1, (long[]) obj2);
				} else if (c.getCanonicalName().equals("double[]")) {
					return Arrays.equals((double[]) obj1, (double[]) obj2);
				}
			} else {
				Object[] objs1 = (Object[]) obj1;
				Object[] objs2 = (Object[]) obj2;
				return compareArrays(objs1, objs2);
			}
		}
		// 复合对象
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			// View v = field.getAnnotation(View.class);
			Object value1 = null;
			Object value2 = null;
			try {
				value1 = getFieldValue(obj1, field);
				value2 = getFieldValue(obj2, field);
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
			Class<?> clazz = value1.getClass();
			if (List.class.isAssignableFrom(clazz) || clazz.isArray()) {
				if (!deepCompare(value1, value2)) {
					return false;
				} else {
					continue;
				}
			}
			// 基本简单类型
			if (isSimpleType(clazz)) {
				try {
					if (null == value1 && null == value2) {
						continue;
					}
					if (value1 != null && !value1.equals(value2)) {
						if (value1 instanceof Date) {
							logger.fatal(
									"存在差异的域：" + field.getName() + " [ " + DateTimeUtil.getChineseDate((Date) value1)
											+ "<--->" + DateTimeUtil.getChineseDate((Date) value2) + " ] ");
						} else {
							logger.fatal(/* v == null ? "" : v.label() + */"存在差异的域：" + field.getName() + " [ " + value1
									+ "<--->" + value2 + " ] ");
						}
						return false;
					} else {
						// logger.fatal(/* v == null ? "" : v.label() + */"不存在差异的域：" +
						// field.getName()
						// + " [ " + value1 + "<--->" + value2 + " ] ");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				logger.info(clazz.getSimpleName() + "复合类型：" + clazz);
				try {
					if (!deepCompare(value1, value2)) {
						return false;
					} else {
						continue;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	/**
	 * 创建时间 2010-7-2 下午03:20:41<br>
	 * 描述：判断是否为简单类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isSimpleType(Class<?> clazz) {
		if (clazz == Short.class || clazz == Short.TYPE || clazz == Long.class || clazz == Long.TYPE
				|| clazz == Integer.class || clazz == Integer.TYPE || clazz == Float.class || clazz == Float.TYPE
				|| clazz == Character.class || clazz == Character.TYPE || clazz == Byte.class || clazz == Byte.TYPE
				|| clazz == Date.class || clazz == java.sql.Date.class || clazz == Timestamp.class
				|| clazz == String.class || clazz == Class.class) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean compareList(List<?> list1, List<?> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			Object obj1 = list1.get(i);
			Object obj2 = list2.get(i);
			if (!deepCompare(obj1, obj2)) {
				logger.info("差异记录：" + ReflectionToStringBuilder.toString(obj1));
				logger.info("差异记录：" + ReflectionToStringBuilder.toString(obj2));
				return false;
			}
		}
		return true;
	}

	/**
	 * @param a
	 * @param a2
	 * @return
	 */
	public static boolean compareArrays(Object[] a, Object[] a2) {
		if (a == a2) {
			return true;
		}
		if (a == null || a2 == null) {
			return false;
		}

		int length = a.length;
		if (a2.length != length) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			Object o1 = a[i];
			Object o2 = a2[i];
			if (!(o1 == null ? o2 == null : deepCompare(o1, o2))) {
				return false;
			}
		}

		return true;
	}

	private static Object getFieldValue(Object obj, Field field) throws Exception {
		field.setAccessible(true);
		return field.get(obj);
	}

	@SuppressWarnings("unused")
	private static String toUpperCaseFirst(String str) {
		String result = null;
		result = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		return result;
	}

	/**
	 * 拷贝一个实体对象
	 * 
	 * @param newObj
	 *            新对象
	 * @param fromObjt
	 *            被拷贝的对象
	 */
	public static void copy(Object newObj, Object fromObjt) {
		if (newObj.getClass() != fromObjt.getClass()) {
			logger.error("被拷贝对象与拷贝对象不同类。");
			return;
		}
		try {
			Field[] fieldobject = fromObjt.getClass().getDeclaredFields();
			Field[] fieldob = newObj.getClass().getDeclaredFields();

			for (Field element : fieldobject) {
				String objectname = element.getName();
				String objDataType = element.getGenericType().toString();
				for (Field element2 : fieldob) {
					String objname = element2.getName();
					String objectDataType = element2.getGenericType().toString();
					if (objname.equals(objectname) && objDataType.equals(objectDataType)) {
						element.setAccessible(true);
						element2.setAccessible(true);
						Object objs = element.get(fromObjt);
						element2.set(newObj, objs);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
