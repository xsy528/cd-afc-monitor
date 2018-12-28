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
 * 
 * @author jiangxf
 */
public class ByteUtil {

	public static byte valueOf(String value) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Byte.valueOf(value);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	public static byte valueOf(String value, int radix) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Byte.valueOf(value, radix);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}

	public static byte valueOf(Object value) {
		try {
			if (value == null) {
				return 0;
			} else {
				return Byte.valueOf(value.toString());
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
	public static byte valueOf(short value) {
		return (byte) (value & 0xFF);
	}

	public static byte valueOf(Short value) {
		if (value == null) {
			return 0;
		} else {
			return (byte) (value & 0xFF);
		}
	}

	/**
	 * 将value以无符号数据转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static byte valueOf(int value) {
		return (byte) (value & 0xFF);
	}

	public static byte valueOf(Integer value) {
		if (value == null) {
			return 0;
		} else {
			return (byte) (value & 0xFF);
		}
	}

	/**
	 * 将value以无符号数据转换为byte
	 * 
	 * @param value
	 * @return
	 */
	public static byte valueOf(long value) {
		return (byte) (value & 0xFF);
	}

	public static byte valueOf(Long value) {
		if (value == null) {
			return 0;
		} else {
			return (byte) (value & 0xFF);
		}
	}

	/**
	 * 16进制表示的字符串转换为字节数组
	 *
	 * @param s 16进制表示的字符串
	 * @return byte[] 字节数组
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] b = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
			b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return b;
	}

}
