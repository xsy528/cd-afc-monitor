/* 
 * 日期：Nov 30, 2007
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.reflect;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * Ticket: 225
 * 
 * @author 华思远 描述: 通过object的某个域进行排序
 * 
 */
public class ReflectionComparator implements Comparator<Object> {

	private boolean nullFirst = true;

	private String fieldName;

	/**
	 * @param fieldName
	 *            需要排序的域名,默认是null放在最前面
	 * 
	 */
	public ReflectionComparator(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	/**
	 * @param nullFirst
	 *            null 是否放在最前
	 * @param fieldName
	 *            需要排序的域名
	 */
	public ReflectionComparator(String fieldName, boolean nullFirst) {
		super();
		this.nullFirst = nullFirst;
		this.fieldName = fieldName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	// TODO 实现 通过object的某个域进行object 值比较
	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) throws UncomparableException {
		if (o1 == null || o2 == null)
			throw new UncomparableException();
		try {
			Field o1field = null;
			Field o2field = null;
			Field[] o1Field = o1.getClass().getDeclaredFields();
			Field[] o2Field = o2.getClass().getDeclaredFields();

			for (Field f : o1Field) {
				if (f.getName().equals(fieldName)) {
					o1field = f;
				}
			}
			for (Field f : o2Field) {
				if (f.getName().equals(fieldName)) {
					o2field = f;
				}
			}
			o1field.setAccessible(true);
			o2field.setAccessible(true);

			if (o1field.get(o1) == null && o2field.get(o2) == null) {
				return 0;
			}
			if (o1field.get(o1) == null) {
				return nullFirst ? 1 : -1;
			}
			if (o2field.get(o2) == null) {
				return nullFirst ? -1 : 1;
			}

			if (o1field.get(o1) instanceof Comparable && o2field.get(o2) instanceof Comparable) {
				Comparable oComp = (Comparable) o1field.get(o1);
				return oComp.compareTo(o2field.get(o2));
			} else {
				throw new UncomparableException();
			}
		} catch (Exception e) {
			throw new UncomparableException(e);
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isNullFirst() {
		return nullFirst;
	}

	public void setNullFirst(boolean nullFirst) {
		this.nullFirst = nullFirst;
	}

}
