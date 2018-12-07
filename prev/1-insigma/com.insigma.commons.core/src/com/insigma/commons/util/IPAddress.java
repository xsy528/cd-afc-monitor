/*
 * 日期：2010-8-3 上午10:20:36
 * 描述：预留
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * CHANGELOG
 */
package com.insigma.commons.util;

import java.util.Arrays;

/**
 * Ticket #85 <br>
 * 
 * @author Lin Y.Z
 * @date 2010-8-3
 */
public class IPAddress {

	/**
	 *将127.0.0.1形式的IP地址转换成十进制整数
	 */
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		String[] ipStrArray = strIp.split("\\.");
		if (ipStrArray.length != 4) {
			throw new IllegalArgumentException("ip地址格式有误。" + strIp + " array:" + Arrays.toString(ipStrArray));
		}
		ip[0] = Long.parseLong(ipStrArray[0]);
		ip[1] = Long.parseLong(ipStrArray[1]);
		ip[2] = Long.parseLong(ipStrArray[2]);
		ip[3] = Long.parseLong(ipStrArray[3]);
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 将十进制整数形式转换成127.0.0.1形式的ip地址
	 */
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}
}
