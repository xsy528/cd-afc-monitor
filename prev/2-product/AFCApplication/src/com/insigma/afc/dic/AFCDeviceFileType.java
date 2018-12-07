/* 
 * 日期：2010-10-28
 * 描述（预留） 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级设备文件类型 <br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Dic(overClass = AFCDeviceFileType.class)
public class AFCDeviceFileType extends Definition {

	private static AFCDeviceFileType instance = new AFCDeviceFileType();

	public static AFCDeviceFileType getInstance() {
		return instance;
	}

	/**
	 * 单程票交易文件标记 S:0X01
	 */
	@DicItem(name = "S", desc = "单程票交易文件")
	public static Integer singleTicket = 0x1;

	/**
	 * 储值票交易文件标记 v:0X02
	 */
	@DicItem(name = "V", desc = "储值票交易文件")
	public static Integer StoredTicket = 0x2;

	/**
	 * 一卡通交易文件标记 Y:0X03
	 */
	@DicItem(name = "Y", desc = "一卡通交易文件")
	public static Integer YKTTicket = 0x3;

	/**
	 * 寄存器文件标记 R:0X04
	 */
	@DicItem(name = "R", desc = "寄存器交易文件")
	public static Integer Register = 0x4;

	/**
	 * 寄存器文件标记 R:0X06
	 */
	@DicItem(name = "T", desc = "事件文件")
	public static Integer EVENT = 0x6;

	/**
	 * 赋值单程票发售汇总文件 t:0x23
	 */
	@DicItem(name = "PrepaySaleStat", desc = "赋值单程票发售汇总文件")
	public static final Integer PreSingleTicketSaleFile = 0x23;

	/**
	 * 库存文件 :0xA0
	 */
	@DicItem(name = "StockSnapshot", desc = "库存文件")
	public static final Integer StockFile = 0xa0;

	/**
	 * 收益文件
	 */
	@DicItem(name = "A", desc = "收益文件 ")
	public static Integer Fin = 0x60;

	/**
	 * 交易文件0x40
	 */
	@DicItem(name = "U", desc = "交易文件 ")
	public static Integer UDFile = 0x70;

}
