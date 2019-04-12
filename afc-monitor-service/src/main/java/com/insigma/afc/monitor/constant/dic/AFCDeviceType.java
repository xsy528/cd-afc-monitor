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

	@DicItem(name = "清分中心设备", key = "CCHS")
	public static Short CCHS = DeviceType_t.DT_System;

	@DicItem(name = "线路中心设备", key = "LC")
	public static Short LC = DeviceType_t.DT_System;

	@DicItem(name = "车站中心设备", key = "SC")
	public static Short SC = DeviceType_t.DT_System;

	@DicItem(name = "编码分拣机", key = "ES")
	public static Short ES = DeviceType_t.DT_ES;

	@DicItem(name = "半自动售票机", key = "BOM", group = "SLE", index = 1)
	public static Short POST = DeviceType_t.DT_BOM;

	@DicItem(name = "自动售票机", key = "TVM", group = "SLE", index = 2)
	public static Short TVM = DeviceType_t.DT_TVM;

	@DicItem(name = "自动查询机", key = "TCM", group = "SLE", index = 3)
	public static Short TCM = DeviceType_t.DT_TCM;

	@DicItem(name = "手持式验票机", key = "PCA", group = "SLE", index = 8)
	public static Short PCA = DeviceType_t.DT_PCA;

	@DicItem(name = "进站闸机", key = "ENG", group = "SLE", index = 4)
	public static Short ENG = DeviceType_t.DT_ENG;

	@DicItem(name = "出站闸机", key = "EXG", group = "SLE", index = 5)
	public static Short EXG = DeviceType_t.DT_EXG;

	@DicItem(name = "双向闸机", key = "TWG", group = "SLE", index = 6)
	public static Short RG = DeviceType_t.DT_TWG;

	@DicItem(name = "出站闸机，无票卡回收机构", key = "SEXG", group = "SLE", index = 6)
	public static Short SEXG = DeviceType_t.DT_SEXG;

	@DicItem(name = "双向闸机", key = "MAGM", group = "SLE", index = 7)
	public static Short MAGM = DeviceType_t.DT_MAGM;

	@DicItem(name = "移动闸机，无票卡回收机构", key = "SMAGM", group = "SLE", index = 8)
	public static Short SMAGM = DeviceType_t.DT_SMAGM;

	@DicItem(name = "移动进站闸机", key = "MNG", group = "SLE", index = 9)
	public static Short MNG = DeviceType_t.DT_MNG;

	@DicItem(name = "移动出站闸机", key = "MXG", group = "SLE", index = 10)
	public static Short MXG = DeviceType_t.DT_MXG;

	@DicItem(name = "移动BOM", key = "MBOM", group = "SLE", index = 11)
	public static Short MBOM = DeviceType_t.DT_MBOM;

	@DicItem(name = "TVM（无现金模块）", key = "STVM", group = "SLE", index = 12)
	public static Short STVM = DeviceType_t.DT_STVM;

	@DicItem(name = "自助票务处理终端", key = "STM", group = "SLE", index = 12)
	public static Short STM = DeviceType_t.DT_STM;

	@DicItem(name = "天府通/日次票机", key = "TFM", group = "SLE", index = 13)
	public static Short TFM = DeviceType_t.DT_TFM;

	@DicItem(name = "有轨POS机", key = "POS", group = "SLE", index = 14)
	public static Short POS = DeviceType_t.DT_POS;

	@DicItem(name = "有轨PCA机", key = "TPCA", group = "SLE", index = 15)
	public static Short TPCA = DeviceType_t.DT_TPCA;

	@DicItem(name = "柜台式半自动售票机", key = "CBOM", group = "SLE", index = 16)
	public static Short CBOM = DeviceType_t.DT_CBOM;

	@DicItem(name = "密钥终端", key = "KEY")
	public static Short KEY = DeviceType_t.DT_Key;

	@DicItem(name = "管理终端", key = "MANAGEMENT")
	public static Short MANAGEMENT = DeviceType_t.DT_Management;

}
