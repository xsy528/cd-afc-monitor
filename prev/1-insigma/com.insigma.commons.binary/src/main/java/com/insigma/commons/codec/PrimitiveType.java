/*
 * ��Ŀ项目: AFC 通用二进制编码
 * �版本: 1.0
 * ����日期: 2007-10-10
 * ����作者: 华思远�����
 * Email: huasiyuan@zdwxjd.com޹�˾
 */
package com.insigma.commons.codec;

public final class PrimitiveType {

	public static final String INT = "int";

	public static final String BYTE = "byte";

	public static final String SHORT = "short";

	public static final String CHAR = "char";

	public static final String LONG = "long";

	public static final boolean isPrimitive(String type) {
		return type.equals(INT) || type.equals(BYTE) || type.equals(SHORT) || type.equals(CHAR) || type.equals(LONG);
	}

	public static final int lengthForPrimitive(String type) {
		if (type.equals(INT))
			return 4;
		if (type.equals(BYTE))
			return 1;
		if (type.equals(SHORT))
			return 2;
		if (type.equals(CHAR))
			return 2;
		if (type.equals(LONG))
			return 8;
		return 0;
	}
};
