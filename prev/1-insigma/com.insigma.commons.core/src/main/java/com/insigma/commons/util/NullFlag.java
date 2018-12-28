package com.insigma.commons.util;

/**
 * <p>
 * Title: 空值标志类
 * </p>
 * <p>
 * Description: 因为Java中的基本类型没有null值，在这里设置了int、double、long的特殊值， 作为这些基本类型的空值标志
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: insigma软件
 * </p>
 * 
 * @author jensol</a>
 * @version 1.0
 */
public class NullFlag {
	/**
	 * 
	 * 默认构造方法
	 */
	public NullFlag() {
		// ...
	}

	/** short 空值标志值：-2^16 */
	public static final short SHORTNULL = -25536;

	/** int 空值标志值：-2^31 */
	public static final int INTNULL = -2147483648;

	/** double 空值标志值：-0.0000000001 */
	public static final double DOUBLENULL = -0.0000000001d;

	/** long 空值标志值：-2^63 */
	public static final long LONGNULL = -9223372036854775808L;

	/**
	 * 判断是否与int空值标志相等
	 * 
	 * @param intPara
	 * @return
	 */
	public static boolean isIntNull(int intPara) {
		if (intPara == INTNULL)
			return true;
		else
			return false;
	}

	/**
	 * 判断输入的对象是否为空
	 * 
	 * @param Object
	 *            obj
	 * @return
	 * @author raofh
	 */
	public static boolean isObjNull(Object obj) {
		if (obj == null || "".equals(obj.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否与double空值标志相等
	 * 
	 * @param doublePara
	 * @return
	 */
	public static boolean isDoubleNull(double doublePara) {
		if (doublePara == DOUBLENULL)
			return true;
		else
			return false;
	}

	/**
	 * 判断是否与long空值标志相等
	 * 
	 * @param longPara
	 * @return
	 */
	public static boolean isLongNull(long longPara) {
		if (longPara == LONGNULL)
			return true;
		else
			return false;
	}
}