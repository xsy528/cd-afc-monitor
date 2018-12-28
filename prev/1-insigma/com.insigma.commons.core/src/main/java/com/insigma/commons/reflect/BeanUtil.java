/*
 * 日期：Jan 10, 2008
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.commons.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.util.lang.NumberUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BeanUtil {

	private static final Logger logger = Logger.getLogger(BeanUtil.class);

	private static final Pattern W3CDATE = Pattern.compile(
			//data
			"^(\\d{4})" + //YYYY1
					"(?:" + "\\-(\\d{1,2})" + //MM2
					"(?:" + "\\-(\\d{1,2})" + //DD3
	//time
	"(?:" + "\\s*(\\d{2})\\:(\\d{2})" + //hour:4,minutes:5
					"(?:\\:(\\d{2}(\\.\\d+)?))?" + //seconds//6
					")?" + ")?" + ")?$");

	public static String toString(Object data) {
		if (data == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Class clazz = data.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		int index = 0;
		for (Field field : declaredFields) {
			if (Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			String name = field.getName();
			Object value = getValue(data, field);
			if (index != 0) {
				sb.append(",");
			}
			if (value != null && isUserDefinedClass(value.getClass())) {
				sb.append(value.getClass().getSimpleName());
				sb.append(":");
				sb.append(toString(value));
			} else {
				sb.append(name);
				sb.append(":");
				sb.append(value);
				index++;
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static Object getValue(Method method, Object value) {
		try {
			return method.invoke(value, new Object[] {});
		} catch (Exception e) {
			throw new ApplicationException(value.getClass().getName() + "获取值错误。", e);
		}
	}

	public static Object getValue(Object data, Field field) {
		if (Modifier.isFinal(field.getModifiers()))
			return null;
		field.setAccessible(true);
		try {
			return field.get(data);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return getValue(data, field.getName());
	}

	public static Object getValue(Object data, String fieldName) {
		if (data == null) {
			return null;
		}
		String getMethod = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method;
		Object value = null;
		try {
			method = data.getClass().getMethod(getMethod);
			value = method.invoke(data);
		} catch (Exception e) {
			throw new ApplicationException(value.getClass().getName() + "获取值错误。", e);
		}
		return value;
	}

	public static Object getBooleanValue(Object data, String fieldName) {
		String getMethod = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method;
		Object value = null;
		try {
			method = data.getClass().getMethod(getMethod);
			value = method.invoke(data);
		} catch (Exception e) {
			throw new ApplicationException(value.getClass().getName() + "获取值错误。", e);
		}
		return value;
	}

	public static void initValue(Object data, Field field, Object value) {
		// 初始化
		if (null == value) {
			try {
				value = field.getType().newInstance();
				setValue(data, field, value);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 应用场景 可以支持不同类间的塞值
	 * @param data 目标对象
	 * @param fieldName 字段名
	 * @param value 设值
	 */
	public static void setValue(Object data, String fieldName, Object value) {
		try {

			Field field = getDeclaredField(data, fieldName);

			if (field == null) {
				throw new ApplicationException(String.format("对象[%s]未找到字段[%s]。", data.getClass(), fieldName));

			}

			//			Field field = data.getClass().getDeclaredField(fieldName);

			setValue(data, field, value);
		} catch (Exception e) {
			logger.error("设置值错误", e);
			throw new ApplicationException(
					String.format("设置对象[%s]的字段[%s]的值为[%s]时异常。", data.getClass(), fieldName, value), e);

		}

	}

	/**
	 * 获取对象Field(含父类)
	 * @param data 子类对象
	 * @param fieldName 字段名称
	 * @return 
	 */

	private static Field getDeclaredField(Object data, String fieldName) {

		Field field = null;

		Class<?> clazz = data.getClass();

		//向上循环找到父类对象
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				//这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
				//如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  

			}
		}

		return null;
	}

	/**
	 * 
	 * 应用场景 若目标和源不同类则会打印exception，软件会继续调用setValue_，不同类间值传递不建议使用
	 * 
	 * @param data 目标对象
	 * @param field 源field
	 * @param value 设值
	 */
	public static void setValue(Object data, Field field, Object value) {
		if (Modifier.isFinal(field.getModifiers()))
			return;
		try {
			field.setAccessible(true);
			if (!isUserDefinedClass(field.getType())) {//非用户自定对象才转化
				value = convert(value, field.getType());
			}
			field.set(data, value);
			return;
		} catch (Exception e) {
			logger.error("", e);
		}

		setValue_(data, field, value);
	}

	@Deprecated
	private static void setValue_(Object data, Field field, Object value) {
		if (Modifier.isFinal(field.getModifiers()))
			return;
		try {

			String fieldName = field.getName();
			String setMethod = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

			Method method = data.getClass().getMethod(setMethod, field.getType());

			if (null == value) {
				if (isUserDefinedClass(field.getType())) {
					method.invoke(data, value);
				}
				return;
			}
			if (value instanceof Date && field.getType().isAssignableFrom(Date.class)) {
				method.invoke(data, value);
				return;
			}

			Object newValue = convert(value, field.getType());
			if (newValue != value) {
				method.invoke(data, newValue);
				return;
			}
			if (!isUserDefinedClass(field.getType()) || !isUserDefinedClass(value.getClass())) {
				method.invoke(data, value);
				return;
			}

			// Object
			Class<? extends Object> clazz = value.getClass();
			Field[] fields = clazz.getDeclaredFields();
			Object objValue = clazz.newInstance();
			for (Field subfield : fields) {
				setValue(objValue, subfield, getValue(value, subfield));
			}
			method.invoke(data, objValue);

		} catch (Exception e) {
			throw new ApplicationException("执行setMethod异常。", e);
		}
	}

	public final static boolean isBaseType(Class<?> clazz) {
		// isPrimitive
		if (clazz.isPrimitive()) {
			return true;
		}

		// Wrap Number
		if ((null != clazz.getSuperclass()) && Number.class.isAssignableFrom(clazz.getSuperclass())) {
			return true;
		}

		// 日期
		if ((Date.class == clazz) || Date.class.isAssignableFrom(clazz)) {
			return true;
		}
		// 字符串
		if (String.class == clazz) {
			return true;
		}
		return false;
	}

	public final static boolean isUserDefinedClass(Class<?> clazz) {
		// isPrimitive
		if (clazz.isPrimitive()) {
			return false;
		}

		// Wrap Number
		if ((null != clazz.getSuperclass()) && Number.class.isAssignableFrom(clazz.getSuperclass())) {
			return false;
		}

		// 日期
		if ((Date.class == clazz) || Date.class.isAssignableFrom(clazz)) {
			return false;
		}
		// 字符串
		if (String.class == clazz) {
			return false;
		}

		// 数组
		if (clazz.isArray()) {
			return false;
		}
		// Collection
		if (Collection.class.isAssignableFrom(clazz)) {
			return false;
		}
		// Map
		if (Map.class.isAssignableFrom(clazz)) {
			return false;
		}
		//枚举
		if (clazz.isEnum()) {
			return false;
		}

		return true;
	}

	public final static Class<? extends Object> toWrapper(Class<? extends Object> type) {
		if (type.isPrimitive()) {
			if (Byte.TYPE == type) {
				return Byte.class;
			} else if (Short.TYPE == type) {
				return Short.class;
			} else if (Integer.TYPE == type) {
				return Integer.class;
			} else if (Long.TYPE == type) {
				return Long.class;
			} else if (Float.TYPE == type) {
				return Float.class;
			} else if (Double.TYPE == type) {
				return Double.class;
			} else if (Character.TYPE == type) {
				return Character.class;
			} else if (Boolean.TYPE == type) {
				return Boolean.class;
			}
		}
		return type;
	}

	/**
	 * 类型转化;(只支持基本类型的转化，Number，String，List，Array，Date系列 的类型转化)
	 * 
	 * @param value
	 *            原始值
	 * @param targetType
	 *            目标类型
	 * @return
	 */
	public final static <T> T convert(Object value, Class<T> targetType) {

		if (value != null && targetType.isAssignableFrom(value.getClass())) {
			return (T) value;
		}

		boolean isPrimitive = targetType.isPrimitive();
		if (!isPrimitive && value == null) {//不是基本类型 value为Null，则返回Null
			return null;
		} else if (!isPrimitive && targetType.equals(value.getClass())) {//不是基本类型 value类型等于目标类型，则强制转换
			return (T) value;
		}
		targetType = (Class<T>) toWrapper(targetType);
		if (Number.class.isAssignableFrom(targetType)) {//number
			if (isPrimitive && value == null) {
				value = 0;
			}

			if (value instanceof Number) {
				Number number = (Number) value;
				if (targetType.equals(Byte.class)) {
					return (T) Byte.valueOf(number.byteValue());
				} else if (targetType.equals(Short.class)) {
					return (T) Short.valueOf(number.shortValue());
				} else if (targetType.equals(Integer.class)) {
					return (T) Integer.valueOf(number.intValue());
				} else if (targetType.equals(Long.class)) {
					return (T) Long.valueOf(number.longValue());
				} else if (targetType.equals(BigInteger.class)) {
					return (T) BigInteger.valueOf(number.longValue());
				} else if (targetType.equals(Float.class)) {
					return (T) Float.valueOf(number.floatValue());
				} else if (targetType.equals(Double.class)) {
					return (T) Double.valueOf(number.doubleValue());
				}
			}

			return (T) NumberUtil.parseNumber(value.toString(), targetType);
		} else if (Date.class.isAssignableFrom(targetType)) { // date
			try {
				if (value instanceof String) {
					final Date parseStringToDate = parseW3Date(value.toString());
					return targetType.getConstructor(long.class).newInstance(parseStringToDate.getTime());
				} else if (Date.class.isAssignableFrom(value.getClass())) {
					return targetType.getConstructor(long.class).newInstance(((Date) value).getTime());
				} else if (Number.class.isAssignableFrom(value.getClass())) {
					return targetType.getConstructor(long.class).newInstance(((Number) value).longValue());
				} else {
					return (T) value;
				}
			} catch (Exception e) {
				throw new ApplicationException(value.getClass().getName() + "获取值错误。", e);
			}

		} else if (targetType.equals(String.class)) { //string
			return (T) value.toString();
		} else if (targetType.isArray()) {//array
			Class<?> componentType = targetType.getComponentType();
			if (componentType == null) {
				componentType = Object.class;
			}
			return (T) convertToArray(value, componentType);
		} else if (List.class.isAssignableFrom(targetType)) {//list
			Class<?> componentType = Object.class;
			return (T) convertToList(value, componentType);
		} else if (Class.class.equals(targetType)) {//Class
			return (T) ClassUtil.forName(value.toString(), null);
		} else {
			return (T) value;
		}
	}

	/**
	 * parse Date from String
	 * @param string
	 * @return
	 */
	private final static Date parseW3Date(String string) {
		if (string == null) {
			return null;
		}
		Matcher m = W3CDATE.matcher(string.trim());
		if (m.find()) {
			Calendar ca = Calendar.getInstance();
			ca.clear();
			ca.set(Calendar.YEAR, Integer.parseInt(m.group(1)));// year
			String month = m.group(2);
			if (month != null) {
				ca.set(Calendar.MONTH, Integer.parseInt(month) - 1);
				String date = m.group(3);
				if (date != null) {
					ca.set(Calendar.DATE, Integer.parseInt(date));
					String hour = m.group(4);
					if (hour != null) {
						String minutes = m.group(5);
						ca.set(Calendar.HOUR, Integer.parseInt(hour));
						ca.set(Calendar.MINUTE, Integer.parseInt(minutes));
						String seconds = m.group(6);
						if (seconds == null) {
						} else if (seconds.length() > 2) {
							float f = Float.parseFloat(seconds);
							ca.set(Calendar.SECOND, (int) f);
							ca.set(Calendar.MILLISECOND, ((int) f * 1000) % 1000);
						} else {
							ca.set(Calendar.SECOND, Integer.parseInt(seconds));
						}
					}
				}
			}
			return ca.getTime();
		}
		return null;
	}

	/**
	 * 把value值转化为类型为targetType的数组对象
	 * @param value
	 * @param targetType
	 * @return
	 */
	public final static <T> T[] convertToArray(Object value, Class<T> componentType) {
		if (value == null) {
			return null;
		} else if (value.getClass().isArray()) {
			Object[] arrays = (Object[]) value;
			Object result = Array.newInstance(componentType, arrays.length);
			for (int i = 0, len = arrays.length; i < len; i++) {
				Array.set(result, i, convert(arrays[i], componentType));
			}
			return (T[]) result;
		} else if (List.class.isAssignableFrom(value.getClass())) {
			List<Object> arrays = (List<Object>) value;
			Object result = Array.newInstance(componentType, arrays.size());
			for (int i = 0, len = arrays.size(); i < len; i++) {
				Array.set(result, i, convert(arrays.get(i), componentType));
			}
			return (T[]) result;
		} else {
			throw new ApplicationException("未知类型转化[componentType:" + componentType + ",value:" + value + "]");
		}
	}

	/**
	 * 把value值转化为类型为componentType的List对象
	 * @param value
	 * @param componentType
	 * @return
	 */
	public final static <T> List<T> convertToList(Object value, Class<T> componentType) {
		if (value == null) {
			return null;
		} else {
			if (value.getClass().isArray()) {
				Object[] arrays = (Object[]) value;
				List result = new ArrayList();
				for (int i = 0, len = arrays.length; i < len; i++) {
					result.add(convert(arrays[i], componentType));
				}
				return result;
			} else if (List.class.isAssignableFrom(value.getClass())) {
				List<Object> arrays = (List<Object>) value;
				List result = new ArrayList();
				for (int i = 0, len = arrays.size(); i < len; i++) {
					result.add(convert(arrays.get(i), componentType));
				}
				return result;
			} else {
				throw new ApplicationException("未知类型转化[componentType:" + componentType + ",value:" + value + "]。");
			}
		}
	}

	public final static <Bean> Bean cloneObject(Bean object) throws InstantiationException, IllegalAccessException {
		Bean result = null;

		if (null == object || !isUserDefinedClass(object.getClass())) {
			return object;
		}

		result = (Bean) object.getClass().newInstance();
		List<Field> fields = new ArrayList<Field>();

		List<Field> subFields = Arrays.asList(object.getClass().getDeclaredFields());

		fields.addAll(subFields);
		Class superClass = object.getClass().getSuperclass();
		if (null != superClass) {
			List<Field> superFields = Arrays.asList(superClass.getDeclaredFields());
			if (null != superFields) {
				fields.addAll(superFields);
			}
		}
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers())) {
				continue;
			}

			field.setAccessible(true);
			Object value = field.get(object);
			if (Modifier.isVolatile(field.getModifiers())) {
				field.set(result, value);
				continue;
			}
			if (!isUserDefinedClass(field.getType())) {
				Object clone = null;
				if (null != value) {
					if (List.class.isAssignableFrom(field.getType())) {
						clone = cloneList((List) value);
					} else if (field.getType().isArray()) {
						clone = cloneArrays((Object[]) value);
					} else if (Map.class.isAssignableFrom(field.getType())) {
						clone = cloneMap((Map) value);
					} else {
						clone = value;
					}
					field.set(result, clone);
				}
			} else {
				if (null != value) {
					Object clone = cloneObject(value);
					field.set(result, clone);
				}
			}
		}

		return result;
	}

	/**
	 * 克隆对象
	 * Id标签属性列不进行拷贝
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public final static <Bean> Bean cloneObjectForNoId(Bean object)
			throws InstantiationException, IllegalAccessException {
		Bean result = null;

		if (null == object || !isUserDefinedClass(object.getClass())) {
			return object;
		}

		result = (Bean) object.getClass().newInstance();
		List<Field> fields = new ArrayList<Field>();

		List<Field> subFields = Arrays.asList(object.getClass().getDeclaredFields());

		fields.addAll(subFields);
		Class superClass = object.getClass().getSuperclass();
		if (null != superClass) {
			List<Field> superFields = Arrays.asList(superClass.getDeclaredFields());
			if (null != superFields) {
				fields.addAll(superFields);
			}
		}
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers())) {
				continue;
			}

			field.setAccessible(true);
			Object value = field.get(object);
			if (Modifier.isVolatile(field.getModifiers())) {
				field.set(result, value);
				continue;
			}

			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				field.set(result, 0L);
				continue;
			}
			if (!isUserDefinedClass(field.getType())) {
				Object clone = null;
				if (null != value) {
					if (List.class.isAssignableFrom(field.getType())) {
						clone = cloneListNoId((List) value);
					} else if (field.getType().isArray()) {
						clone = cloneArrays((Object[]) value);
					} else if (Map.class.isAssignableFrom(field.getType())) {
						clone = cloneMap((Map) value);
					} else {
						clone = value;
					}
					field.set(result, clone);
				}
			} else {
				if (null != value) {
					Object clone = cloneObject(value);
					field.set(result, clone);
				}
			}
		}

		return result;
	}

	/**
	 * 克隆List
	 * Id标签属性列不进行拷贝
	 * 
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static List<?> cloneListNoId(List<?> list) throws InstantiationException, IllegalAccessException {
		List result = new ArrayList();

		for (Object from : list) {
			if (isUserDefinedClass(from.getClass())) {
				Object to = cloneObjectForNoId(from);
				result.add(to);
			} else {
				Object object = from;
				if (!from.getClass().isPrimitive()) {
					object = convert(from, from.getClass());
				}
				result.add(object);
			}
		}

		return result;
	}

	/**
	 * 克隆List
	 * 
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static List<?> cloneList(List<?> list) throws InstantiationException, IllegalAccessException {
		List result = new ArrayList();

		for (Object from : list) {
			if (isUserDefinedClass(from.getClass())) {
				Object to = cloneObject(from);
				result.add(to);
			} else {
				Object object = from;
				if (!from.getClass().isPrimitive()) {
					object = convert(from, from.getClass());
				}
				result.add(object);
			}
		}

		return result;
	}

	public static Object cloneArrays(Object[] arrays) throws InstantiationException, IllegalAccessException {

		// 构建数组
		Class arrayClass = arrays.getClass().getComponentType();
		int arraySize = arrays.length;
		Object saveArray = Array.newInstance(arrayClass, arraySize);
		for (int i = 0; i < arraySize; i++) {
			if (isUserDefinedClass(arrayClass)) {
				Object object = cloneObject(arrays[i]);
				Array.set(saveArray, i, object);
			} else {
				if (arrayClass.isPrimitive()) {
					Object object = arrays[i];
					Array.set(saveArray, i, object);
				} else {
					Object object = convert(arrays[i], arrayClass);
					Array.set(saveArray, i, object);
				}
			}
		}

		return saveArray;
	}

	public static Object cloneMap(Map map) throws InstantiationException, IllegalAccessException {
		int arraySize = map.size();
		Map<Object, Object> newMap = new HashMap<Object, Object>(arraySize);
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			Object object = null;
			Class<?> valueClass = value.getClass();
			if (isUserDefinedClass(valueClass)) {
				object = cloneObject(value);
			} else {
				if (Map.class.isAssignableFrom(valueClass)) {
					object = cloneMap((Map) value);
				} else if (List.class.isAssignableFrom(valueClass)) {
					object = cloneList((List<?>) value);
				} else if (valueClass.isArray()) {
					object = cloneArrays((Object[]) value);
				} else {
					object = value;
				}
			}
			newMap.put(key, object);
		}

		return newMap;
	}

	public static Object newInstanceObject(Class type) throws InstantiationException, IllegalAccessException {
		Object result = null;
		if (String.class.isAssignableFrom(type)) {
			result = "";
		}
		if (BeanUtil.isUserDefinedClass(type)) {
			result = type.newInstance();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				Object subObject = newInstanceObject(field.getType());
				if (null != subObject) {
					Object value = BeanUtil.getValue(result, field);
					//shenchao 20131224 增加默认值
					if (value != null && value instanceof Number && ((Number) value).longValue() > 0L) {
						BeanUtil.setValue(result, field, value);
					} else if (value != null && value instanceof String && !value.toString().equals("")) {
						//字符串 hexingxing 20150424
						BeanUtil.setValue(result, field, value);
					} else {
						BeanUtil.setValue(result, field, subObject);
					}
				}
			}
		} else if (Collection.class.isAssignableFrom(type)) {// Collection
			return new ArrayList();
		} else if (Map.class.isAssignableFrom(type)) {// Map
			return new HashMap();
		} else if (Date.class.isAssignableFrom(type)) {
			return convert(new Date(), type);
		} else {
			Class<? extends Object> targetType = toWrapper(type);
			if (Number.class.isAssignableFrom(targetType)) {//number
				return NumberUtil.parseNumber("0", targetType);
			} else if (String.class.isAssignableFrom(targetType)) {
				return "";
			}
		}

		return result;

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

		}
		return entity;
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

			}
			list.add(o == null ? null : o.toString());
			// logger.info(str + " = " + o);
		}

		return list.toArray(ss);
	}

	/**
	 * 将from中的数据复制到to中
	 * @param from
	 * @param to
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	public static boolean copyObject(Object from, Object to)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if (null == from || null == to || isBaseType(from.getClass()) || !from.getClass().equals(to.getClass())) {
			return false;
		}

		List<Field> fields = new ArrayList<Field>();

		List<Field> subFields = Arrays.asList(from.getClass().getDeclaredFields());

		fields.addAll(subFields);
		Class superClass = from.getClass().getSuperclass();
		if (null != superClass) {
			List<Field> superFields = Arrays.asList(superClass.getDeclaredFields());
			if (null != superFields) {
				fields.addAll(superFields);
			}
		}
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers())) {
				continue;
			}

			field.setAccessible(true);
			Object fromValue = field.get(from);
			Object toValue = field.get(to);

			if (null == fromValue) {
				field.set(to, fromValue);
				continue;
			}

			if (fromValue.equals(toValue)) {
				continue;
			}

			if (toValue == null) {
				toValue = newInstanceObject(field.getType());
				field.set(to, toValue);
			}

			if (Modifier.isVolatile(field.getModifiers())) {
				field.set(to, fromValue);
				continue;
			}

			if (!isUserDefinedClass(field.getType())) {
				Object clone = null;

				if (List.class.isAssignableFrom(field.getType())) {
					copyList((List) fromValue, (List) toValue);
				} else if (field.getType().isArray()) {
					copyArrays((Object[]) fromValue, (Object[]) toValue);
				} else if (Map.class.isAssignableFrom(field.getType())) {
					copyMap((Map) fromValue, (Map) toValue);
				} else {
					clone = fromValue;
					field.set(to, clone);
				}

			} else {
				copyObject(fromValue, toValue);
			}
		}

		return true;
	}

	public static void copyMap(Map fromMap, Map toMap) throws InstantiationException, IllegalAccessException {
		for (Object key : fromMap.keySet()) {
			Object fromValue = fromMap.get(key);
			Object toValue = toMap.get(key);
			Class<?> valueClass = fromValue.getClass();
			if (null == toValue) {
				toValue = newInstanceObject(valueClass);
				toMap.put(key, toValue);
			}

			if (isUserDefinedClass(valueClass)) {
				if (!fromValue.equals(toValue)) {
					copyObject(fromValue, toValue);
				}
			} else {
				if (Map.class.isAssignableFrom(valueClass)) {
					copyMap((Map) fromValue, (Map) toValue);
				} else if (List.class.isAssignableFrom(valueClass)) {
					copyList((List<?>) fromValue, (List<?>) toValue);
				} else if (valueClass.isArray()) {
					copyArrays((Object[]) fromValue, (Object[]) toValue);
				} else {
					Object object = fromValue;
					toMap.put(key, object);
				}
			}
		}

		for (Object key : toMap.keySet()) {
			Object fromValue = fromMap.get(key);
			if (null == fromValue) {
				toMap.remove(key);
			}
		}
	}

	public static boolean copyArrays(Object[] fromArrays, Object[] toArrays)
			throws InstantiationException, IllegalAccessException {

		// 构建数组
		Class arrayClass = fromArrays.getClass().getComponentType();
		int arraySize = fromArrays.length;
		for (int i = 0; i < arraySize; i++) {
			if (isUserDefinedClass(arrayClass)) {
				if (!fromArrays[i].equals(toArrays[i])) {
					copyObject(fromArrays[i], toArrays[i]);
				}

			} else {
				if (arrayClass.isPrimitive()) {
					Object object = fromArrays[i];
					Array.set(toArrays, i, object);
				} else {
					Object object = convert(fromArrays[i], arrayClass);
					Array.set(toArrays, i, object);
				}
			}
		}

		return true;
	}

	public static boolean copyList(List fromList, List toList)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if (null == fromList) {
			return false;
		}

		if (fromList.equals(toList)) {
			return true;
		}

		if (null == toList) {
			toList = (List) newInstanceObject(fromList.getClass());
		}

		int fromSize = fromList.size();
		int toSize = toList.size();

		if (fromSize > toSize) {
			Class objClass = fromList.get(0).getClass();

			for (int i = 0; i < (fromSize - toSize); i++) {
				Object obj = newInstanceObject(objClass);
				toList.add(obj);
			}
		} else {
			for (int i = 0; i < (toSize - fromSize); i++) {
				toList.remove(toList.size() - 1);
			}
		}

		for (int i = 0; i < fromList.size(); i++) {
			Object from = fromList.get(i);
			Object to = toList.get(i);
			if (!from.equals(to)) {
				if (isUserDefinedClass(from.getClass())) {
					copyObject(from, to);
				} else {
					Object object = from;
					if (!from.getClass().isPrimitive()) {
						object = convert(from, from.getClass());
					}
					toList.set(i, object);
				}
			}

		}

		return true;
	}

	public static void main(String[] args) {
		try {
			a();
		} catch (Exception e) {
			System.out.println(11);
			// TODO: handle exception
		}
	}

	private static void a() {
		try {
			String s = null;
			s.isEmpty();

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(1);
	}
}
