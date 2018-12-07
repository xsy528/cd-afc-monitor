/* 
 * 日期：2012-7-17
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
 * Ticket: 一卡通拒付原因代码
 * 
 * @author chenhangwen
 */
@Dic(overClass = WZYKTRefuseCauseCode.class)
public class WZYKTRefuseCauseCode extends Definition {
	private static WZYKTRefuseCauseCode instance = new WZYKTRefuseCauseCode();

	public static WZYKTRefuseCauseCode getInstance() {
		return instance;
	}

	@DicItem(name = "卡片发行方调整")
	public final static int CARD_COMPANY_ADJUST = 0x00;

	@DicItem(name = "TAC码错误")
	public final static int TAC_ERROR = 0x01;

	@DicItem(name = "数据卡号")
	public final static int INVALID_CARDID = 0x02;

	@DicItem(name = "数据重复")
	public final static int DATA_DUPLICATE = 0x03;

	@DicItem(name = "灰记录")
	public final static int GRAY_RECORD = 0x04;

	@DicItem(name = "账上金额不足")
	public final static int INSUFFICIENT_BALANCE = 0x05;

	@DicItem(name = "测试数据")
	public final static int TEST_DATA = 0x06;

	@DicItem(name = "数据非法调整拒付")
	public final static int ILLEGAL_ADJUSTMENT = 0x5C;

	@DicItem(name = "数据重复调整拒付")
	public final static int REPEAT_ADJUSTMENT = 0x5D;

	@DicItem(name = "灰记录调整拒付")
	public final static int GRAY_REOCRD_ADJUSTMENT = 0x5E;

	@DicItem(name = "其他错误")
	public final static int OTHER_ERROR = 0x5A;

}
