/* 
 * 日期：2011-8-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 读卡器代码 Ticket:
 * 
 * @author BOND XIE(xiebo@zdwxgd.com)
 */
@Dic(overClass = WZReaderCode.class)
public class WZReaderCode extends Definition {

	public static WZReaderCode instance = new WZReaderCode();

	public static WZReaderCode getInstance() {
		return instance;
	}

	@DicItem(name = "读卡器1")
	public static Integer READER_CODE_ONE = 0x01;

	@DicItem(name = "读卡器2")
	public static Integer READER_CODE_TWO = 0x02;

}
