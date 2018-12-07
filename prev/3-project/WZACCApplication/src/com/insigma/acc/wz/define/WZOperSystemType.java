/*
 * 日期：2010-10-13
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.DeviceType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 设备类型定义
 * @author hexingyue
 */
@Dic(overClass = WZOperSystemType.class)
public class WZOperSystemType extends Definition {

	private static WZOperSystemType instance = new WZOperSystemType();

	public static WZOperSystemType getInstance() {
		return instance;
	}

	@DicItem(name = "SC服务器", index = 1)
	public static Integer SC = DeviceType_t.DT_SC_SERVER;

	@DicItem(name = "MC服务器", index = 2)
	public static Integer MC = DeviceType_t.DT_MC_SERVER;

	//	@DicItem(name = "进站闸机", index = 3)
	//	public static Integer ENG = DeviceType_t.DT_ENG;
	//
	//	@DicItem(name = "出站闸机", index = 4)
	//	public static Integer EXG = DeviceType_t.DT_EXG;

	@DicItem(name = "双向检票机", index = 5)
	public static Integer REG = DeviceType_t.DT_RG;

	@DicItem(name = "半自动售票机", index = 6)
	public static Integer POST = DeviceType_t.DT_BOM;

	@DicItem(name = "自动售票机", index = 7)
	public static Integer TVM = DeviceType_t.DT_TVM;

	@DicItem(name = "查询充值机", index = 8)
	public static Integer TSM = DeviceType_t.DT_ISM;

	@DicItem(name = "便携式验检票机", index = 9)
	public static Integer PCA = DeviceType_t.DT_PCA;

	@DicItem(name = "票卡编码分拣机", index = 10)
	public static Integer ES = DeviceType_t.DT_ES;

}
