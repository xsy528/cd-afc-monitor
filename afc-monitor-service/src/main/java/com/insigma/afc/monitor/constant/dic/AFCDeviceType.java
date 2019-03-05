/* 
 * 日期：2010-10-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.afc.monitor.constant.xdr.typedef.DeviceType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级设备类型 <br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Dic(overClass = AFCDeviceType.class)
public class AFCDeviceType extends Definition {

	private static AFCDeviceType instance = new AFCDeviceType();

	public static AFCDeviceType getInstance() {
		return instance;
	}

	@DicItem(name = "自动检票机", group = "SLE")
	public static Short GATE = DeviceType_t.DT_AGM;

	@DicItem(name = "自动售票机", group = "SLE")
	public static Short TVM = DeviceType_t.DT_TVM;

	@DicItem(name = "半自动售票机", group = "SLE")
	public static Short POST = DeviceType_t.DT_BOM;

	@DicItem(name = "个性化发卡机",group = "SLE")
	public static Short CUS = DeviceType_t.DT_CUS;

	@DicItem(name = "手持式验票机", group = "SLE")
	public static Short PCA = DeviceType_t.DT_PCA;

	@DicItem(name = "互联网取票机",group = "SLE")
	public static Short ITVM = DeviceType_t.DT_ITVM;

	@DicItem(name = "编码分拣机", group = "E/S")
	public static Short E_S = DeviceType_t.DT_ES;

	@DicItem(name = "服务器")
	public static Short SERVER = DeviceType_t.DT_Server;

	@DicItem(name = "工作站")
	public static Short WORKSTATION = DeviceType_t.DT_Workstation;

	@DicItem(name = "网络设备")
	public static Short NETWORK = DeviceType_t.DT_Network;

	@DicItem(name = "UPS")
	public static Short UPS = DeviceType_t.DT_UPS;

}
