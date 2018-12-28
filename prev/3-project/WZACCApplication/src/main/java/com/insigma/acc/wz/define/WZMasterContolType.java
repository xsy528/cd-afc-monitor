/* 
 * 日期：2018年4月2日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 主控参数类型
 * @author chenfuchun
 *
 */
@Dic(overClass = WZMasterContolType.class)
public class WZMasterContolType extends Definition {

	private static WZMasterContolType instance = new WZMasterContolType();

	public static WZMasterContolType getInstance() {
		return instance;
	}

	@DicItem(name = "EOD参数控制文件", index = 1)
	public static final Integer FILE_EOD_CONTROL = FileHeaderTag_t.FILE_EOD_CONTROL;

	@DicItem(name = "AFC运营主控文件", index = 2)
	public static final Integer FILE_AFC_MAIN_CONTROL = FileHeaderTag_t.FILE_AFC_MAIN_CONTROL;

}
