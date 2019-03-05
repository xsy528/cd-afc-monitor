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

	public static AFCDeviceSubType getInstance() {
		return instance;
	}

	@DicItem(name="卡式编码分拣机")
	public static Short DTSub_CardES = DeviceSubType_t.DTSub_CardES;

	@DicItem(name="筹码式编码分拣机")
	public static Short DTSub_TokenES = DeviceSubType_t.DTSub_TokenES;

	@DicItem(name="TVM(按键式)")
	public static Short DTSub_TvmButton = DeviceSubType_t.DTSub_TvmButton;

	@DicItem(name = "TVM(触摸式)")
	public static Short DTSub_TvmTouch = DeviceSubType_t.DTSub_TvmTouch;

	@DicItem(name="BOM(非付费区)")
	public static Short DTSub_PaidBom = DeviceSubType_t.DTSub_PaidBom;

	@DicItem(name="BOM(付费区)")
	public static Short DTSub_UnpaidBom = DeviceSubType_t.DTSub_UnpaidBom;

	@DicItem(name = "进站闸机", index = 1)
	public static Short DEVICE_GATE_ENTRY = DeviceSubType_t.DTSub_EnG;

	@DicItem(name = "出站闸机", index = 2)
	public static Short DEVICE_GATE_EXIT = DeviceSubType_t.DTSub_ExG;

	@DicItem(name = "双向闸机", index = 3)
	public static Short DEVICE_GATE_BOTH = DeviceSubType_t.DTSub_DoubleAgm;

	@DicItem(name="个性化票卡机")
	public static Short DTSub_CUS = DeviceSubType_t.DTSub_CUS;

	/**
	 * 预留
	 */
	@DicItem
	public static Short DTSub_TCM = DeviceSubType_t.DTSub_TCM;

	@DicItem(name="手持式验票机")
	public static Short DTSub_PCA = DeviceSubType_t.DTSub_PCA;

	@DicItem(name="互联网取票机")
	public static Short DTSub_ITVM = DeviceSubType_t.DTSub_ITVM;

	@DicItem(name="服务器")
	public static Short DTSub_Server = DeviceSubType_t.DTSub_Server;

	@DicItem(name="工作站")
	public static Short DTSub_Workstation = DeviceSubType_t.DTSub_Workstation;

	@DicItem(name="交换机")
	public static Short DTSub_Swither = DeviceSubType_t.DTSub_Swither;

	@DicItem(name="路由器")
	public static Short DTSub_Router = DeviceSubType_t.DTSub_Router;

	@DicItem(name="防火墙")
	public static Short DTSub_Firewall = DeviceSubType_t.DTSub_Firewall;

	@DicItem(name="不间断电源")
	public static Short DTSub_UPS = DeviceSubType_t.DTSub_UPS;
}
