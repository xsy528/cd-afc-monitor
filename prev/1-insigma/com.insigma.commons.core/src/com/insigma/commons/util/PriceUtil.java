/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 价格相关
 * 
 * @author fenghong
 */
public class PriceUtil {

	/**
	 * 把价格显示到view
	 * 
	 * @param price
	 *            price
	 * @return String String
	 */
	public static String formatPriceToView(Long price) {
		return new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
	}

	/**
	 * 转换价格
	 * 
	 * @param price
	 *            price
	 * @return Integer Integer
	 */
	public static Long formatPriceFormView(String price) {
		return new BigDecimal(price).multiply(new BigDecimal(100)).longValue();
	}

	/**
	 * 获取价格
	 * 
	 * @param ticketValue
	 *            ticketValue
	 * @param ticketNum
	 *            ticketNum
	 * @return String String
	 */
	public static String getPriceToView(String ticketValue, String ticketNum) {
		if (ticketValue == null || ticketValue.equals("")) {
			return "0";
		}
		if (ticketNum == null || ticketNum.equals("")) {
			return "0";
		}
		return new BigDecimal(ticketValue).multiply(new BigDecimal(ticketNum)).setScale(2).toString();
	}

	/**
	 * 减
	 * 
	 * @param price1
	 *            price1
	 * @param price2
	 *            price2
	 * @return String String
	 */
	public static String subtractPriceToView(String price1, String price2) {
		if (StringUtils.isEmpty(price1)) {
			price1 = "0";
		}
		if (StringUtils.isEmpty(price2)) {
			price2 = "0";
		}
		try {
			return new BigDecimal(price1).subtract(new BigDecimal(price2)).setScale(2).toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 加
	 * 
	 * @param price1
	 *            price1
	 * @param price2
	 *            price2
	 * @return String String
	 */
	public static String plusPriceToView(String price1, String price2) {
		if (StringUtils.isEmpty(price1)) {
			price1 = "0";
		}
		if (StringUtils.isEmpty(price2)) {
			price2 = "0";
		}
		try {
			return new BigDecimal(price1).add(new BigDecimal(price2)).setScale(2).toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
