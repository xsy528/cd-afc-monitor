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
public class ShortUtil {

	public static short valueOf(String value) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Short.valueOf(value);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	public static short valueOf(String value, int radix) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Short.valueOf(value, radix);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	public static short valueOf(Object value) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Short.valueOf(value.toString());
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 将value以无符号数据转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static short valueOf(short value) {
		return (short) (value & 0xFF);
	}

	/**
	 * 将value以无符号数据转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static short valueOf(int value) {
		return (short) (value & 0xFFFF);
	}

	/**
	 * 将value以无符号数据转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static short valueOf(long value) {
		return (short) (value & 0xFFFF);
	}

}
