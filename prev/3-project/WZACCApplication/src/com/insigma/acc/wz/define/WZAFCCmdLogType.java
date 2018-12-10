/* 
 * 日期：2017年7月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 命令日志分类
 * @author  wangzezhi
 *
 */
@Dic(overClass = AFCCmdLogType.class)
public class WZAFCCmdLogType {

	@DicItem(name = "通讯注册日志")
	public static Integer LOG_MESSAGE = 0;

	@DicItem(name = "设备命令日志")
	public static Integer LOG_DEVICE_CMD = 1;

	@DicItem(name = "设备监控日志")
	public static Integer LOG_DEVICE_MONITOR = 2;

	@DicItem(name = "设备操作日志")
	public static Integer LOG_DEVICE_OPERATE = 3;

	@DicItem(name = "文件通知日志")
	public static Integer LOG_FILE_INFORM = 4;

	@DicItem(name = "模式切换命令日志")
	public static Integer LOG_MODE = 5;

	@DicItem(name = "其它模式命令日志")
	public static Integer LOG_OTHER_MODE = 52;

	@DicItem(name = "票卡库存日志")
	public static Integer LOG_TICKETE_DIR = 6;

	@DicItem(name = "BOM日志")
	public static Integer LOG_BOM = 7;

	@DicItem(name = "票卡查询日志")
	public static Integer LOG_TICKETE_SEARCH = 8;

	@DicItem(name = "其他命令日志")
	public static Integer LOG_OTHER = 9;

	// 
	@DicItem(name = "时钟同步命令日志")
	public static Integer LOG_TIME_SYNC = 10;
}