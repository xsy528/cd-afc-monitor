/* 
 * 日期：2010-12-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 监控部件编码
 * 
 * @author 陈海锋
 */
@Dic(overClass = AFCMonitorMoudleCode.class)
public class AFCMonitorMoudleCode extends Definition {

	private static AFCMonitorMoudleCode instance = new AFCMonitorMoudleCode();

	public static AFCMonitorMoudleCode getInstance() {
		return instance;
	}

	@DicItem(name = "纸币处理单元")
	public static Integer BNA = 0x1200;

	@DicItem(name = "硬币处理单元")
	public static Integer CHS = 0x1300;

	@DicItem(name = "读写器1")
	public static Integer CS1 = 0x0600;

	@DicItem(name = "读写器2")
	public static Integer CS2 = 0x0700;

	@DicItem(name = "银行卡读写器")
	public static Integer CS3 = 0x0800;

	@DicItem(name = "票卡发售模块")
	public static Integer TIM = 0x1400;

	@DicItem(name = "票卡回收模块")
	public static Integer CTN = 0x1500;

	@DicItem(name = "参数")
	public static Integer EOD = 0x0300;

	@DicItem(name = "主控单元")
	public static Integer ECU = 0x0500;

	@DicItem(name = "打印机1")
	public static Integer RPU1 = 0x0900;

	@DicItem(name = "打印机2")
	public static Integer RPU2 = 0x1000;

	@DicItem(name = "维修门")
	public static Integer RDR = 0x1100;
}
