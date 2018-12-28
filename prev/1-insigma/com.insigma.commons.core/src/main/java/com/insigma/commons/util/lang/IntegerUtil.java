/* 
 * 日期：2007-11-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.util.lang;

import java.io.IOException;

/**
 * Ticket:
 * 
 * @author jiangxf
 */
public class IntegerUtil {

	/**
	 * 截取字符川的首字并转化为int
	 * 
	 * @return
	 */
	public static int parseInt(String str) {
		int num = -1;
		String[] ss = str.split("-");
		if (ss.length < 1) {
			throw new RuntimeException();
		} else {
			String first = ss[0];
			num = Integer.valueOf(first);
		}
		return num;
	}

	public static int valueOf(Object value) {
		if (value == null) {
			return 0;
		} else {
			return Integer.valueOf(value.toString());
		}

	}

	public static int valueOf(Object value, int radix) {
		if (value == null) {
			return 0;
		} else {
			return Integer.valueOf(value.toString(), radix);
		}

	}

	/**
	 * 将value以无符号数据转换为integer
	 * 
	 * @param value
	 * @return
	 */
	public static int valueOf(byte value) {
		return ((int) value & 0xFF);
	}

	/**
	 * 将value以无符号数据转换为integer
	 * 
	 * @param value
	 * @return
	 */
	public static int valueOf(short value) {
		return ((int) value & 0xFFFF);
	}

	/**
	 * 将value以无符号数据转换为integer
	 * 
	 * @param value
	 * @return
	 */
	public static int valueOf(long value) {
		return ((int) value & 0xFFFFFFFF);
	}

	public static byte[] toArray(int valule) {
		byte[] data = new byte[4];
		return data;
	}

	public static byte[] toArray(int valule, Endian endian) {

		byte[] data = new byte[4];
		return data;
	}

	public static int valueOf(byte[] data) {
		return 0;
	}

	/**
	 * 把字节数组转换为int
	 * 
	 * @param data
	 * @param endian
	 * @return
	 */
	public static int valueOf(byte[] data, Endian endian) {
		if (endian == Endian.BIG) {
			return bytes2Int(data, 0, data.length, true);
		} else {
			return bytes2Int(data, 0, data.length, false);
		}
	}

	/**
	 * 两个Integer类型相加，返回Integer
	 * 
	 * @param paraA
	 *            Integer
	 * @param paraB
	 *            Integer
	 * @return Integer
	 */
	public static Integer addInteger(Integer paraA, Integer paraB) {
		if (paraA == null)
			paraA = new Integer(0);
		if (paraB == null)
			paraB = new Integer(0);
		int ret = paraA.intValue() + paraB.intValue();
		return new Integer(ret);
	}

	/**
	 * 两个Integer类型相减(paraA - paraB),返回Integer
	 * 
	 * @param paraA
	 *            Integer
	 * @param paraB
	 *            Integer
	 * @return Integer
	 */
	public static Integer subInteger(Integer paraA, Integer paraB) {
		if (paraA == null)
			paraA = new Integer(0);
		if (paraB == null)
			paraB = new Integer(0);
		int ret = paraA.intValue() - paraB.intValue();
		return new Integer(ret);
	}

	/**
	 * 两个Integer类型相减(paraA * paraB),返回Integer
	 * 
	 * @param paraA
	 *            Integer
	 * @param paraB
	 *            Integer
	 * @return Integer
	 */
	public static Integer multInteger(Integer paraA, Integer paraB) {
		if (paraA == null)
			paraA = new Integer(0);
		if (paraB == null)
			paraB = new Integer(0);
		int ret = paraA.intValue() * paraB.intValue();
		return new Integer(ret);
	}

	/**
	 * 两个Integer类型相减(paraA / paraB),返回Integer
	 * 
	 * @param paraA
	 *            Integer
	 * @param paraB
	 *            Integer
	 * @return Integer
	 */
	public static Integer divInteger(Integer paraA, Integer paraB) {
		if (paraA == null)
			paraA = new Integer(0);
		if (paraB == null)
			paraB = new Integer(0);
		int ret = paraA.intValue() / paraB.intValue();
		return new Integer(ret);
	}

	/**
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public static byte[] intToByte(int i) {
		byte[] b = new byte[4];

		int maskB1 = 0xff;
		int maskB2 = 0xff00;
		int maskB3 = 0xff0000;
		int maskB4 = 0xff000000;

		b[0] = (byte) ((i & maskB4) >>> 24);
		b[1] = (byte) ((i & maskB3) >>> 16);
		b[2] = (byte) ((i & maskB2) >>> 8);
		b[3] = (byte) (i & maskB1);
		return b;
	}

	/**
	 * 将字节数组转为 Java 中的 int 数值
	 * 
	 * @param bys
	 *            字节数组
	 * @param start
	 *            需要转换的起始索引点
	 * @param len
	 *            需要转换的字节长度
	 * @param isBigEndian
	 *            (Little-Endian 表示高位字节在高位索引中,Big-Endian 表示高位字节在低位索引中) 是否是 BE（true -- BE 序，false --
	 *            LE 序）
	 * @return
	 */
	public static int bytes2Int(byte[] bys, int start, int len, boolean isBigEndian) {
		int n = 0;
		for (int i = start, k = start + len % (Integer.SIZE / Byte.SIZE + 1); i < k; i++) {
			n |= (bys[i] & 0xff) << ((isBigEndian ? (k - i - 1) : i) * Byte.SIZE);
		}
		return n;
	}
}
