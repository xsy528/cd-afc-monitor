/* 
 * 日期：2011-3-7
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.afc.monitor.constant.xdr.typedef.DeviceSubType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 设备子类型 Ticket:
 * 
 * @author 陈海锋
 */
@Dic(overClass = AFCDeviceSubType.class)
public class AFCDeviceSubType extends Definition {

	private static AFCDeviceSubType instance = new AFCDeviceSubType();
	{

	}
	public static AFCDeviceSubType getInstance() {
		return instance;
	}



	@DicItem(name = "双向宽通道闸机", index = 4)
	public static Short DEVICE_GATE_WIDE = 24;


	@DicItem(name = "进站闸机", key = "ENG", group = "SLE", index = 1)
	public static Short DEVICE_GATE_ENTRY = DeviceSubType_t.DTSub_EnG;

	@DicItem(name = "出站闸机", key = "EXG", group = "SLE", index = 2)
	public static Short DEVICE_GATE_EXIT = DeviceSubType_t.DTSub_ExG;

	@DicItem(name = "双向闸机", key = "REG", group = "SLE", index = 3)
	public static Short DEVICE_GATE_BOTH = DeviceSubType_t.DTSub_DoubleAgm;

	//	@DicItem(name = "双向宽通道闸机", key = "REG", group = "SLE", index = 4)
	//	public static Short DEVICE_GATE_WIDE = 24;
}
