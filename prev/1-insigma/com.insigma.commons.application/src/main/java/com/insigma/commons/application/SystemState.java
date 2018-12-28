/* 
 * 日期：2010-9-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.application;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class SystemState {
	/**
	 * 未初始
	 */
	public static final short UN_INIT = -1;

	/**
	 * 开启
	 */
	public static final short STARTED = 1;

	/**
	 * 停止
	 */
	public static final short OUT_SERVICE = 2;

	/**
	 * 锁定
	 */
	public static final short LOCK = 4;

	/**
	 * 登陆
	 */
	public static final short LOGIN = 8;

	/**
	 * 注销
	 */
	public static final short LOGOUT = 16;

	/**
	 * 退出
	 */
	public static final int EXIT = 5;
}
