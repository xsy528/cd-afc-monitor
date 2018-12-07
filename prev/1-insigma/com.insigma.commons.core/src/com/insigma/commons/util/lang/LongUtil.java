/* 
 * 日期：2007-11-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.util.lang;

/**
 * Ticket:
 * 
 * @author jiangxf
 */
public class LongUtil {

	public boolean isNaN(Object value) {
		try {
			Long.valueOf(value.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static long valueOf(String value) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	public static long valueOf(Object value) {
		try {
			if (value == null) {
				return 0L;
			} else {
				return Long.valueOf(value.toString());
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 将value以无符号数据转换为long
	 * 
	 * @param value
	 * @return
	 */
	public static long valueOf(byte value) {
		return ((long) value & 0xFFL);
	}

	/**
	 * 将value以无符号数据转换为long
	 * 
	 * @param value
	 * @return
	 */
	public static long valueOf(short value) {
		return ((long) value & 0xFFFFL);
	}

	/**
	 * 将value以无符号数据转换为long
	 * 
	 * @param value
	 * @return
	 */
	public static long valueOf(int value) {
		return ((long) value & 0xFFFFFFFFL);
	}

	/**
	 * 转换字节数组为long
	 * @param data
	 * @param offset
	 * @return
	 */
	public static long byteArrayToLong(byte[] data, int offset) {
		long r = data[offset++];
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		r = (r << 8) | ((long) (data[offset++]) & 0xff);
		return r;
	}
}
