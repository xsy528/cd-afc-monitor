/* 
 * 日期：2011-3-1
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket:模式日志字典类
 *
 * @author xingshaoya
 * create time: 2019-04-02 11:08
 */
@Dic(overClass = AFCCmdLogType.class)
public class AFCCmdLogType extends Definition {
	private static AFCCmdLogType instance = new AFCCmdLogType();

	public static AFCCmdLogType getInstance() {
		return instance;
	}

	@DicItem(name = "模式日志")
	public static Integer LOG_MODE = 0;

	@DicItem(name = "设备命令日志")
	public static Integer LOG_DEVICE_CMD = 1;

	@DicItem(name = "时钟同步命令日志")
	public static Integer LOG_TIME_SYNC = 2;

	@DicItem(name = "其他命令日志")
	public static Integer LOG_OTHER = 3;

}
