package com.insigma.commons.util;

/**
 * <p>
 * Title: insigma信息管理系统
 * </p>
 * <p>
 * Description: 应用程序编码类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: insigma软件
 * </p>
 *
 * @author jensol
 * @version 1.0
 */

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 把字节转换为字符串
	 * 
	 * @param str
	 * @return String
	 * 
	 */
	public static String MD5(String str) {
		try {
			return MD5(str.getBytes("UTF8"));
		} catch (Exception e) {
			return null;
		}
	}

	public static String MD5(File file) {
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			return MD5(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static String MD5(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++)
				result = result + Integer.toHexString(0xff & s[i] | 0xffffff00).substring(6);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] MD5byte(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte s[] = md.digest();
			return s;
		} catch (Exception e) {
			return null;
		}
	}
}