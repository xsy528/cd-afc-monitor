/* 
 * 日期：2017年10月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.ChipType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 芯片类型
 * 
 * @author chenfuchun
 *
 */
@Dic(overClass = WZChipType.class)
public class WZChipType extends Definition {

	public static WZChipType instance = new WZChipType();

	public static WZChipType getInstance() {
		return instance;
	}

	@DicItem(name = "Ultra Light卡", group = "SJT", index = 1)
	public static Integer CT_ULTRALIGHT = ChipType_t.CT_ULTRALIGHT;

	@DicItem(name = "市域铁路发行的CPU卡 ", group = "CPU", index = 2)
	public static Integer CT_METROCPU = ChipType_t.CT_METROCPU;

	@DicItem(name = "一卡通卡", group = "YKT", index = 3)
	public static Integer CT_CITIZENYKT = ChipType_t.CT_CITIZENYKT;

	@DicItem(name = "手机卡", group = "MOBILE", index = 4)
	public static Integer CT_MOBILE = ChipType_t.CT_MOBILE;

}
