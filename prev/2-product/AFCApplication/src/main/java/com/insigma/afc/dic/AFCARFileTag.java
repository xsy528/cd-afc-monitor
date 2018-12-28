/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * AR文件类型
 * 
 * @author CaiChunye
 */
@Dic(overClass = AFCARFileTag.class)
public class AFCARFileTag extends Definition {

	private static AFCARFileTag instance = new AFCARFileTag();

	public static AFCARFileTag getInstance() {
		return instance;
	}

	// 0- 定期文件
	@DicItem(name = "定期文件")
	public static Integer INTERVAL_ARFILE = 0;

	// 1- 当前运营日结束
	@DicItem(name = "当前运营日结束")
	public static Integer OPERARION_END_ARFILE = 1;

	// 2- 当前年运营日开始
	@DicItem(name = "当前年运营日开始")
	public static Integer OPERATION_START_ARFILE = 2;

	// 3-设备/车站恢复通讯
	@DicItem(name = "设备/车站恢复通讯")
	public static Integer COMMUNICATION_RECOVER_ARFILE = 3;

	// 12-插入票箱
	@DicItem(name = "插入票箱")
	public static Integer TICKET_BOX_PLUG_IN_ARFILE = 12;

	// 13-拔出票箱
	@DicItem(name = "拔出票箱")
	public static Integer TICKET_BOX_POLL_OUT_ARFILE = 13;

	// 14- 轮班开始
	@DicItem(name = "轮班开始")
	public static Integer DUTY_START_ARFILE = 14;

	// 15-轮班结束
	@DicItem(name = "轮班结束")
	public static Integer DUTY_END_ARFILE = 15;
}
