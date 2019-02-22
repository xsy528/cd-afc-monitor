/* 
 * 日期：2018年5月8日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州设备状态定义
 * @author chenfuchun
 *
 */
@Dic(overClass = DeviceStatus.class)
public class WZDeviceStatus {

	private static WZDeviceStatus instance = new WZDeviceStatus();

	public static WZDeviceStatus getInstance() {
		return instance;
	}

	// 设备处于正常工作模式
	@DicItem(name = "正常", group = "1")
	public static Short NORMAL = 0x00;

	// 需要引起注意的状态
	@DicItem(name = "警告", group = "1")
	public static Short WARNING = 0x01;

	// 需要人工响应解决的状态
	@DicItem(name = "报警", group = "1")
	public static Short ALARM = 0x02;

	// 检查不到设备的存在，一般是设备没有启动或设备离线
	@DicItem(name = "离线", group = "1")
	public static Short OFF_LINE = 0x03;

	// 停止服务时通讯是正常的
	@DicItem(name = "暂停服务", group = "1")
	public static Short STOP_SERVICE = 0xff;

	// 设备或是车站未启用，自定义-yang
	@DicItem(name = "未启用", group = "1")
	public static Short NO_USE = 0xf;
}
