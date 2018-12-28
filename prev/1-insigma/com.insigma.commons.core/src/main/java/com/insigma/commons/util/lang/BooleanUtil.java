/* 
 * 日期：Dec 3, 2007
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.util.lang;

/**
 * Ticket: 
 * @author  jiangxf
 *
 */
public class BooleanUtil {

	public static boolean valueOf(byte value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(Byte value) {
		if (value == null || value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(short value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(Short value) {
		if (value == null || value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(int value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(Integer value) {
		if (value == null || value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(long value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean valueOf(Long value) {
		if (value == null || value == 0) {
			return false;
		} else {
			return true;
		}
	}

}
