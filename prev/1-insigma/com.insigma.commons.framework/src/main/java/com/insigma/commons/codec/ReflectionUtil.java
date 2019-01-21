/*
 * ��Ŀ项目: AFC 通用二进制编码
 * �版本: 1.0
 * ����日期: 2007-10-10
 * ����作者: 华思远�����
 * Email: huasiyuan@zdwxjd.com޹�˾
 */
package com.insigma.commons.codec;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

public class ReflectionUtil {

	public static Field[] getAllFields(Class<?> clazz) {
		Stack<Field> fieldStack = new Stack<Field>();

		if (null == clazz)
			return null;
		while (null != clazz) {
			Field[] fromFather = clazz.getDeclaredFields();
			for (int i = 0; i < fromFather.length; i++) {
				fieldStack.push(fromFather[fromFather.length - 1 - i]);
			}
			clazz = clazz.getSuperclass();
		}

		Field[] result = new Field[fieldStack.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = fieldStack.pop();
		}
		return result;

	}

	public static final int typeDispatcher(Class<?> clazz) {
		Integer result = classIntMap.get(clazz);
		if (result == null)
			result = 0x20;
		return result;
	}

	public static final boolean isPrimitiveWrapper(Class<?> clazz) {
		if (null == clazz)
			return false;
		if (null == classIntMap.get(clazz))
			return false;
		return (CASEPRIMITIVE == (classIntMap.get(clazz) & 0xF0));
	}

	private static final HashMap<Class<?>, Integer> classIntMap = new HashMap<Class<?>, Integer>();

	static {
		classIntMap.put(Byte.TYPE, 0x11);
		classIntMap.put(Byte.class, 0x11);
		classIntMap.put(Short.TYPE, 0x12);
		classIntMap.put(Short.class, 0x12);
		classIntMap.put(Character.TYPE, 0x13);
		classIntMap.put(Character.class, 0x13);
		classIntMap.put(Integer.TYPE, 0x14);
		classIntMap.put(Integer.class, 0x14);
		classIntMap.put(Long.TYPE, 0x15);
		classIntMap.put(Long.class, 0x15);
		classIntMap.put(String.class, 0x21);
		classIntMap.put(Date.class, 0x22);
	}

	public static final int CASEBYTE = 0x11;

	public static final int CASESHORT = 0x12;

	public static final int CASECHAR = 0x13;

	public static final int CASEINT = 0x14;

	public static final int CASELONG = 0x15;

	public static final int CASESTRING = 0x20;

	public static final int CASEDATE = 0x30;

	public static final int CASEPRIMITIVE = 0x10;

	public static final int CASEOTHER = 0xF0;

}
