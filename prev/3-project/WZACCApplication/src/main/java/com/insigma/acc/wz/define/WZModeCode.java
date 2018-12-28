/* 
 * 日期：2017年7月10日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.afc.dic.AFCModeCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州运营模式常量定义
 * @author  wangzezhi
 *
 */
@Dic(overClass = AFCModeCode.class)
public class WZModeCode {
	@DicItem(name = "正常模式", group = AFCModeCode.MODE_SIGN_NORMAL, index = 0)
	public final static Integer NORMAL_MODE_CODE = 0x00;

	@DicItem(name = "关闭模式", group = AFCModeCode.MODE_SIGN_NORMAL, index = 1)
	public final static Integer TRAIN_SHUTDOWN_MODE_CODE = 0x01;

	@DicItem(name = "进站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND, index = 2)
	public final static Integer ENTER_NO_CHECK_MODE_CODE = 0x02;

	@DicItem(name = "出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND, index = 3)
	public final static Integer EXIT_NO_CHECK_MODE_CODE = 0x03;

	@DicItem(name = "时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND, index = 4)
	public final static Integer TIME_NO_CHECK_MODE_CODE = 0x04;

	@DicItem(name = "日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND, index = 5)
	public final static Integer DAY_NO_CHECK_MODE_CODE = 0x05;

	@DicItem(name = "车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND, index = 6)
	public final static Integer FEE_FREE_NO_CHECK_MODE_CODE = 0x06;

	@DicItem(name = "列车故障模式", group = AFCModeCode.MODE_SIGN_BREAKDOWN, index = 7)
	public final static Integer TRAIN_BREAK_DOWN_MODE_CODE = 0x07;

	@DicItem(name = "紧急运营模式", group = AFCModeCode.MODE_SIGN_URGENCY, index = 8)
	public final static Integer START_URGENCY_MODE_CODE = 0x08;

	//	 以下为自定义类型，用于监控时模式切换命令下发窗口
	@DicItem(name = "解除紧急运营模式", group = AFCModeCode.MODE_SIGN_URGENCY)
	public final static Integer END_URGENCY_MODE_CODE = 0x15;

}
