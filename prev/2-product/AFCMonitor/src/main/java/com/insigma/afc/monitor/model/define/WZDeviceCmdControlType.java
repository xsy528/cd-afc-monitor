/* 
 * 日期：2018年9月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.model.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 设备控制命令
 * @author chenfuchun
 *
 */
@Dic(overClass = WZDeviceCmdControlType.class)
public class WZDeviceCmdControlType extends Definition {

	private static WZDeviceCmdControlType instance = new WZDeviceCmdControlType();

	public static WZDeviceCmdControlType getInstance() {
		return instance;
	}

	//系统关闭
	@DicItem(name = "系统关闭", index = 0)
	public static Integer SYS_OFF = 0x01;

	//重新启动
	@DicItem(name = "重新启动", index = 1)
	public static Integer REOPEN = 0x02;

	//睡眠模式
	@DicItem(name = "睡眠模式", index = 2)
	public static Integer SLEEP_PATTERN = 0x03;

	//远程唤醒
	@DicItem(name = "远程唤醒", index = 3)
	public static Integer REMOTE_WAKENING = 0x04;

	//开始服务
	@DicItem(name = "开始服务", index = 4)
	public static Integer START_SERVICE = 0x05;

	//暂停服务
	@DicItem(name = "暂停服务", index = 5)
	public static Integer OUT_SERVICE = 0x06;

	//结束服务
	@DicItem(name = "结束服务", index = 6)
	public static Integer STOP_SERVICE = 0x07;

	//设备状态上报
	@DicItem(name = "设备状态上报", index = 7)
	public static Integer DEVICESTATUS_UPLOAD = 0x08;

	//仅进站模式
	@DicItem(name = "仅进站模式", index = 8)
	public static Integer ONLY_ENTRY = 0x11;

	//仅出站模式
	@DicItem(name = "仅出站模式", index = 9)
	public static Integer ONLY_EXIT = 0x12;

	//双向模式
	@DicItem(name = "双向模式", index = 10)
	public static Integer TWO_WAY = 0x13;

	//闸门常开
	@DicItem(name = "闸门常开", index = 11)
	public static Integer ALWAYS_OPEN = 0x14;

	//闸门常闭
	@DicItem(name = "闸门常闭", index = 12)
	public static Integer ALWAYS_CLOSE = 0x15;

	//清空硬币
	@DicItem(name = "清空硬币", index = 13)
	public static Integer CLEAN_COIN = 0x21;

	//清空纸币
	@DicItem(name = "清空纸币", index = 14)
	public static Integer CLEAN_PAPER = 0x22;

}
