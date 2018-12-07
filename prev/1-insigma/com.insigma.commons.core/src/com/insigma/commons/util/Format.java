package com.insigma.commons.util;

/**
 * <p>
 * Title: insigma信息管理系统
 * </p>
 * <p>
 * Description: 格式化输出数字类
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
import java.text.DecimalFormat;

public class Format {

	private static DecimalFormat amountFormat = new DecimalFormat("##0.##");

	private static DecimalFormat moneyFormat = new DecimalFormat("##0.00");

	private static DecimalFormat amountFormat_d = new DecimalFormat("###,##0.##");

	private static DecimalFormat moneyFormat_d = new DecimalFormat("###,##0.00");

	/**
	 * 默认构造方法
	 * 
	 */
	public Format() {
		// ...
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @return String
	 */
	public static String amount(String num) {
		return amount(num, false);
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @return String
	 */
	public static String amount(Double num) {
		return amount(num, false);
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String amount(String num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : amountFormat.format(Double.valueOf(num));
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String amount(Double num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : amountFormat.format(num);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @return String
	 */
	public static String money(String num) {
		return money(num, false);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @return String
	 */
	public static String money(Double num) {
		return money(num, false);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String money(String num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : moneyFormat.format(Double.valueOf(num));
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String money(Double num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : moneyFormat.format(num);
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @return String
	 */
	public static String numfmt(String num, int digits) {
		return numfmt(num, digits, false);
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @param showZero
	 * @return String
	 */
	public static String numfmt(String num, int digits, boolean showZero) {
		return showNullZero(num, showZero) ? "" : changeAmountPattern(Double.valueOf(num), digits, "####0");
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @return String
	 */
	public static String amount2(String num) {
		return amount2(num, false);
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @return
	 */
	public static String amount2(Double num) {
		return amount2(num, false);
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String amount2(String num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : amountFormat_d.format(Double.valueOf(num));
	}

	/**
	 * 返回一个String类型的数量值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String amount2(Double num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : amountFormat_d.format(num);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @return String
	 */
	public static String money2(String num) {
		return money2(num, false);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @return String
	 */
	public static String money2(Double num) {
		return money2(num, false);
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String money2(String num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : moneyFormat_d.format(Double.valueOf(num));
	}

	/**
	 * 返回一个String类型的价格值
	 * 
	 * @param num
	 * @param showZero
	 * @return String
	 */
	public static String money2(Double num, boolean showZero) {
		return showNullZero(num, showZero) ? "" : moneyFormat_d.format(num);
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @return String
	 */
	public static String numfmt2(String num, int digits) {
		return numfmt2(num, digits, false);
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @return String
	 */
	public static String numfmt2(Double num, int digits) {
		return numfmt2(num, digits, false);
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @param showZero
	 * @return String
	 */
	public static String numfmt2(String num, int digits, boolean showZero) {
		return showNullZero(num, showZero) ? "" : changeAmountPattern(Double.valueOf(num), digits, "##,##0");
	}

	/**
	 * 返回一个String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @param showZero
	 * @return String
	 */
	public static String numfmt2(Double num, int digits, boolean showZero) {
		return showNullZero(num, showZero) ? "" : changeAmountPattern(num, digits, "##,##0");
	}

	/**
	 * 返回一个改变后的String类型的值
	 * 
	 * @param num
	 * @param digits
	 * @param ptn
	 * @return String
	 */
	private static String changeAmountPattern(Double num, int digits, String ptn) {
		String fraction = "";
		for (int i = 0; i < digits; i++) {
			fraction = fraction + "0";
		}
		if (!fraction.equalsIgnoreCase("")) {
			ptn = ptn + "." + fraction;
		}
		return (new DecimalFormat(ptn)).format(num);
	}

	/**
	 * 判断当String num为空时，返回一个boolean型值
	 * 
	 * @param num
	 * @param showZero
	 * @return boolean
	 */
	private static boolean showNullZero(String num, boolean showZero) {
		if (num == null || num.equals("")) {
			return true;
		}
		Double value = Double.valueOf(num);
		return !showZero && value.compareTo(Double.valueOf("0")) == 0;
	}

	/**
	 * 判断当Double num为空时，返回一个boolean型值
	 * 
	 * @param num
	 * @param showZero
	 * @return boolean
	 */
	private static boolean showNullZero(Double num, boolean showZero) {
		if (num == null || num.equals("")) {
			return true;
		}
		Double value = num;
		return !showZero && value.compareTo(Double.valueOf("0")) == 0;
	}
}