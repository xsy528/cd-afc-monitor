/*
 * 日期：2010-10-13
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.DeviceType_t;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 设备类型定义
 * @author yao
 */
@Dic(overClass = AFCDeviceType.class)
public class WZDeviceType {

	private static WZDeviceType instance = new WZDeviceType();

	public static WZDeviceType getInstance() {
		return instance;
	}

	@DicItem(name = "清分中心设备", key = "ACC")
	public static Short CCHS = 0x01;

	@DicItem(name = "线路中心设备", key = "LC")
	public static Short LC = 0x02;

	@DicItem(name = "车站中心设备", key = "SC")
	public static Short SC = 0x03;

	@DicItem(name = "进站闸机", key = "ENG", group = "SLE", index = 4)
	public static Short ENG = DeviceType_t.DT_ENG;

	@DicItem(name = "出站闸机", key = "EXG", group = "SLE", index = 5)
	public static Short EXG = DeviceType_t.DT_EXG;

	@DicItem(name = "双向闸机", key = "REG", group = "SLE", index = 6)
	public static Short REG = DeviceType_t.DT_RG;

	@DicItem(name = "半自动售票机", key = "BOM", group = "SLE", index = 1)
	public static Short POST = DeviceType_t.DT_BOM;

	@DicItem(name = "自动售票机", key = "TVM", group = "SLE", index = 2)
	public static Short TVM = DeviceType_t.DT_TVM;

	@DicItem(name = "查询充值机", key = "ISM", group = "SLE", index = 9)
	public static Short TSM = DeviceType_t.DT_ISM;

	@DicItem(name = "手持式验票机", key = "PCA", group = "SLE", index = 8)
	public static Short PCA = DeviceType_t.DT_PCA;

	@DicItem(name = "编码分拣机", key = "ES")
	public static Short ES = DeviceType_t.DT_ES;

	//为了字典类覆盖
	@DicItem(name = "自动加值机")
	public static Short AVM = 0x05;

	@DicItem(name = "自动查询机")
	public static Short TCM = 0x0b;
}
