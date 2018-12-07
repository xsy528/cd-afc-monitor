package com.insigma.commons.util;

/**
 * 通用校验
 */

import java.util.StringTokenizer;

public class CommonVerify {

	/**
	 * @构造器
	 */
	public CommonVerify() {

	}

	/**
	 * 判断输入的字符串是否与指定的长度相等
	 * 
	 * @param name
	 * @param length
	 * @return boolean
	 * @roseuid 3E486AD4014E
	 */
	public static boolean stringLenthVerify(String name, int length) {
		if (name.length() == length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断输入的字符串首或尾是否含有空格,如有空格，则去掉
	 * 
	 * @param name
	 * @return java.lang.String
	 * @roseuid 3E486BFE034B
	 */
	public static String stringTrimVerify(String name) {
		return name.trim();
	}

	/**
	 * 依次取出字符串的每一位,判断取出的字符的ascii码是否在'0'和'9'对应ascii之间
	 * 
	 * @param name
	 * @return boolean
	 * @roseuid 3E486D4E009B
	 */
	public static boolean numberVerify(String name) {
		boolean isNumberChar = true;
		int i = 0;
		char x;
		while (!isNumberChar || i >= name.length()) {
			x = name.charAt(i);
			if (x < 0 || x > 9)
				isNumberChar = false;

			i++;
		}
		return isNumberChar;

	}

	/**
	 * 依次取出字符串的每一位,判断取出的字符的ascii码是否在'a'和'Z'对应ascii之间
	 * 
	 * @param name
	 * @return boolean
	 * @roseuid 3E486D590051
	 */
	public static boolean charVerify(String name) {
		char achar[];
		achar = name.toCharArray();
		for (int i = 0; i < achar.length; i++) {
			if (achar[i] >= 'A' && achar[i] <= 'z') {
			} else {
				return false;
			}

		}
		return true;
	}

	/**
	 * 判断指定的字符串是否为空，返回值1，不空；0，为空
	 * 
	 * @param name
	 * @return int
	 * @roseuid 3E486D810199
	 */
	public static int nullVerify(String name) {
		if (name == null) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 得到字符串中"|"之间的内容，用于错误处理。
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String toCus(String sourceStr) {
		String[] strArr;
		strArr = getAsStringArray(sourceStr, "|");
		if (strArr.length <= 1)
			return sourceStr;
		else
			return "|" + strArr[1] + "|";
	}

	/**
	 * 将字符串转化为字符串数组
	 * 
	 * @param s_value
	 *            字符串
	 * @param delim
	 *            分隔符
	 * @return s_list 字符串数组
	 */
	private static String[] getAsStringArray(String s_value, String delim) {
		String[] s_list;
		int i = 0;

		if (s_value != null && delim != null) {
			StringTokenizer st = new StringTokenizer(s_value, delim);
			s_list = new String[st.countTokens()];
			while (st.hasMoreTokens()) {
				s_list[i] = st.nextToken();

				i++;
			}
		} else
			s_list = new String[0];
		return s_list;
	}

	/**
	 * 格式化客户端错误概要信息
	 * 
	 * @param msg
	 * @return
	 */
	public static String formatCusMsg(String msg) {
		return "|" + msg + "|";
	}

	/**
	 * 给客户端信息加入时间信息
	 * 
	 * @param msg
	 * @return
	 */
	public static String processCusMsg(String msg) {
		return TypeCast.dateTimeToString(new java.sql.Date(new java.util.Date().getTime())) + " | " + msg;
	}
}
