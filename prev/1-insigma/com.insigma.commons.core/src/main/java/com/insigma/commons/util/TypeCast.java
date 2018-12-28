package com.insigma.commons.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.lang.Money;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * <p>
 * Title: 类型转换类
 * </p>
 * <p>
 * Description: 类型转换类，用于把String类型的数据和int、double、Date等类型的互相转换。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: insigma软件
 * </p>
 * 
 * @author jensol </a>
 * @version 1.0
 */
public class TypeCast {
	/**
	 * 把一个String类型的数据，转换成int型的。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static int stringToInt(String str, String paraName, boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.INTNULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 int 型 。");
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException("传入的参数：" + paraName + "错误，无法转换成 int 型。");
		}
	}

	/**
	 * 把一个String类型的数据转换成double型的。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static double stringToDouble(String str, String paraName, boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.DOUBLENULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 double 型。 ");
		}
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName + "错误，无法转换成 double 型 。");
		}
	}

	/**
	 * This method format a string to a java.sql.Date's object.The string must
	 * like "yyyy-MM-dd". For Example:
	 * TypeCast.stringToDate("1995-1-5","parameter name",false);
	 * 
	 * @param strDate
	 *            A String whose should be parsed.
	 * @param paraName
	 * @param isCanNull
	 *            if this parameter is false,strDate must be not null.
	 * @return
	 * @throws ApplicationException
	 */
	public static Date stringToDate(String strDate, String paraName, boolean isCanNull) throws ApplicationException {
		Date targetDate = null;
		if (strDate == null || strDate.equals("")) {
			if (isCanNull)
				return null;
			else
				throw new ApplicationException("传入的参数：" + paraName + "为空，无法转换成 Date 型。 ");
		}
		if (strDate.indexOf("-") == -1)
			throw new ApplicationException(" 传入的参数：" + paraName + "格式不对，无法转换成 Date 型。 ");
		int yyyy, MM, dd;
		try {
			yyyy = Integer.parseInt(strDate.substring(0, strDate.indexOf("-")));
			MM = Integer.parseInt(strDate.substring(strDate.indexOf("-") + 1, strDate.lastIndexOf("-")));
			dd = Integer.parseInt(strDate.substring(strDate.lastIndexOf("-") + 1, strDate.length()));
			targetDate = DateTimeUtil.getDate(yyyy, MM, dd);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName + "错误，无法转换成 Date 型 。");
		} catch (IllegalArgumentException e) {
			throw new ApplicationException(" 传入的参数：" + paraName + "错误，无法转换成 Date 型。 ");
		}
		return targetDate;
	}

	/**
	 * This method format a string to a java.sql.Date's object.The string must
	 * like "yyyy-MM-dd HH:mm:ss". For Example:
	 * TypeCast.StringToDateTime("1995-10-5 16:24:15","parameter name",false);
	 * 
	 * @param strDate
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static Date stringToDateTime(String strDate, String paraName, boolean isCanNull)
			throws ApplicationException {
		Date targetDate = null;
		if (strDate == null || strDate.equals("")) {
			if (isCanNull)
				return null;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 Date 型。 ");
		}
		DateFormat dFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			targetDate = new Date(dFor.parse(strDate).getTime());
		} catch (ParseException parseE) {
			throw new ApplicationException(" 传入的参数：" + paraName + "错误，无法转换成 Date 型。 ");
		}
		return targetDate;
	}

	/**
	 * int类型转换成String类型。
	 * 
	 * @param para
	 * @return
	 */
	public static String intToString(int para) {
		return "" + para;
	}

	/**
	 * int类型转换成Integer类型。
	 * 
	 * @param para
	 * @return
	 */
	public static Integer intToInteger(int para) {
		return Integer.getInteger("" + para);
	}

	/**
	 * 把一个double型的数据转换成一个字符串，用于显示。 按照货币表示：0为0.00； 19为19.00；24.1为24.10；
	 * 25.225为25.23。
	 * 
	 * @param para
	 * @return
	 * @modify jensol
	 */
	private static String doubleToString(double para) {
		NumberFormat nf = new DecimalFormat("##0.00");
		String strValue = nf.format(para);
		return strValue;
	}

	/**
	 * format a Date to String. the String like "yyyy-MM-dd". if the Date is
	 * null,return a empty String
	 * 
	 * @param date
	 * @return
	 */
	public static String simpleDateToString(Date date) {
		if (date == null)
			return "";
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * format a Date to String. the String like "yyyy-MM-dd HH:mm:ss" if the
	 * Date is null,return a empty String
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTimeToString(Date date) {
		if (date == null)
			return "";
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 字符串转换，如果是null，允许为空，返回""； 不允许为空，throws
	 * ApplicationException，字符串不为空，返回strSource.trim()
	 * 
	 * @param strSource
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static String stringToString(String strSource, String paraName, boolean isCanNull)
			throws ApplicationException {
		if (strSource == null) {
			if (isCanNull)
				return "";
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 String 型 。");
		}
		return strSource;
	}

	/**
	 * 字符串转换，如果是null，返回empty String
	 * 
	 * @param strSource
	 * @return
	 */
	public static String stringToString(String strSource) {
		if (strSource == null)
			return "";
		else
			return strSource;
	}

	/**
	 * 字符串转换成Integer，输入必须是
	 * 
	 * @param strSource
	 *            String
	 * @return Integer
	 */
	public static Integer stringToInteger(String strSource) {
		if (strSource == null || strSource.trim().equals(""))
			return new Integer("0");
		else {
			if (CommonVerify.numberVerify(strSource)) {
				return new Integer(strSource);
			} else {
				return new Integer("0");
			}
		}
	}

	/**
	 * 把一个String类型的数据转换成long型。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static long stringToLong(String str, String paraName, boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.LONGNULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 long 型。 ");
		}
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName + "错误，无法转换成 long 型 。");
		}
	}

	/**
	 * long型转换成String类型。
	 * 
	 * @param para
	 * @return
	 */
	public static String longToString(long para) {
		return "" + para;
	}

	/**
	 * 非金额的double类型数据转换成String类型，直接转换，不定义格式
	 * 
	 * @param para
	 * @return
	 */
	public static String notMoneyDoubleToString(double para) {
		return "" + para;
	}

	/**
	 * Integer to String
	 * 
	 * @param i
	 *            Integer
	 * @return String
	 */
	public static String integerToString(Integer i) {
		if (i == null)
			return "";
		return i.intValue() + "";
	}

	/**
	 * Integer to String
	 * 
	 * @param i
	 *            Integer
	 * @return String
	 */
	public static String bigDecimalToString(BigDecimal i) {
		if (i == null)
			return "";
		return i.toString();
	}

	/**
	 * 将输入的Integer类型的月数转化成"X年X月"格式的字符串
	 * 
	 * @param month
	 *            Integer
	 * @return String
	 */
	public static String month2YearMonth(Integer month) {
		String yearMonth = "";
		int smonth = 0;
		int year = 0;
		int rmonth = 0;
		if ((month == null) || (month.equals(new Integer("0")))) {
			return "0月";
		}
		smonth = month.intValue();
		year = smonth / 12;
		rmonth = smonth - 12 * year;
		if (year > 0) {
			yearMonth = intToString(year) + "年";
		}
		if (rmonth > 0) {
			yearMonth += intToString(rmonth) + "个月";
		}
		return yearMonth;
	}

	/**
	 * 将输入的费款所属期转化成"XXXX年XX月"格式的字符串
	 * 
	 * @param month
	 *            Integer
	 * @return String
	 */
	public static String period2YearMonth(String ym) {
		if (ym == null) {
			return "";
		} else {
			String year = ym.substring(0, 4);
			String month = ym.substring(4, 6);
			return year + "年" + month + "月";
		}
	}

	/**
	 * 把一个double型的数据转换成一个字符串，用于显示。 按照货币表示：0为0.00； 19为19.00；24.1为24.10；
	 * 25.225为25.23。
	 * 
	 * @param para
	 * @return
	 */
	public static String moneyDoubleToString(double para) {
		return doubleToString(para);
	}

	/**
	 * 将BigDecimal转化为Money。
	 * 
	 * @param money
	 *            BigDecimal
	 * @return Money
	 * @author raofh
	 */
	public static Money bigDecimal2Money(BigDecimal money) {
		if (money == null) {
			return Money.ZERO;
		} else if (money.intValue() < 0) {
			return Money.ZERO;
		} else {
			return new Money(money);
		}
	}

	/**
	 * 将输入的参数转化为非空(null)的字符串
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 * @author raofh
	 */
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 将输入的参数转化为非空(null)的字符串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String array2String(String[] array, String separator) {
		StringBuffer ret = new StringBuffer("");
		if (array == null || array.length <= 0) {
			return "";
		}
		for (int i = 0; i < array.length; i++) {
			ret.append(separator).append(array[i]);
		}
		return ret.substring(separator.length());
	}

	/**
	 * 返回Money值
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static Money objToMoney(Object obj) {
		if (obj == null) {
			return Money.ZERO;
		} else {
			try {
				if (obj instanceof BigDecimal) {
					return new Money((BigDecimal) obj);
				} else if (obj instanceof String) {
					return new Money(new BigDecimal((String) obj));
				} else {
					return Money.ZERO;
				}
			} catch (Exception e) {
				return Money.ZERO;
			}
		}
	}

	/**
	 * 返回BigDecimal值
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static BigDecimal objToBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0);
		} else {
			try {
				if (obj instanceof BigDecimal) {
					return (BigDecimal) obj;
				} else if (obj instanceof String) {
					return new BigDecimal((String) obj);
				} else {
					return BigDecimal.valueOf(0);
				}
			} catch (Exception e) {
				return BigDecimal.valueOf(0);
			}
		}
	}

	/**
	 * short类型转换成String类型。
	 * 
	 * @param para
	 * @return
	 */
	public static String shortToString(short para) {
		return "" + para;
	}

	/**
	 * 把一个String类型的数据，转换成short型的。如果转换失败，抛出AppException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws AppException
	 */
	public static short stringToshort(String str, String paraName, boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.SHORTNULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 short 型。 ");
		}
		try {
			return Short.parseShort(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 short 型。 ");
		}
	}

	/**
	 * 把一个String类型的数据，转换成short型的。如果转换失败，抛出AppException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws AppException
	 */
	public static Short stringToShort(String str, String paraName, boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return null;
			else
				throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 short 型。 ");
		}
		try {
			return Short.valueOf(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName + "为空，无法转换成 short 型。 ");
		}
	}
}